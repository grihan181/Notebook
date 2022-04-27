package org.example.NotebookClasses;

import org.apache.log4j.Logger;
import org.example.connection.ConnectionPool;

import javax.servlet.http.HttpServletRequest;
import java.sql.*;
import java.util.ArrayList;

public class NotebookDB {
    private static NotebookDB INSTANCE;
    final static Logger logger = Logger.getLogger(NotebookDB.class);
    private NotebookDB() { }

    public static synchronized NotebookDB getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new NotebookDB();
        }
        return INSTANCE;
    }


    public ArrayList<Notebook> select(long userID, HttpServletRequest req) {
        ArrayList<Notebook> notebooks = new ArrayList<>();
        Connection connection = ConnectionPool.getInstance().getConnection();

        try {
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM NOTEBOOKS WHERE USERS_ID = " + userID +
                    " ORDER BY IMPORTANT DESC, CREATED_WHEN DESC;");

            while (rs.next()) {
                long id = rs.getLong("ID");
                String name = rs.getString("NAME");
                String notes = rs.getString("NOTES");
                boolean important = rs.getBoolean("IMPORTANT");
                String createdWhen = changeFormatCreatedWhen(rs.getString("CREATED_WHEN"));
                String reminded = rs.getString("REMINDER");
                Notebook notebook = new Notebook(id, name, notes,
                        important, createdWhen, reminded, userID);
                notebooks.add(notebook);
            }
            ConnectionPool.closeConnection(connection);
        } catch (Exception e) {
            logger.error(e);
        }
        return notebooks;
    }
    public Notebook selectOne(long id, long userID) {
        Connection connection = ConnectionPool.getInstance().getConnection();
        Notebook notebook = null;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM NOTEBOOKS WHERE ID = ? AND USERS_ID = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.setLong(2, userID);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                String name = rs.getString("NAME");
                String notes = rs.getString("NOTES");
                boolean important = rs.getBoolean("IMPORTANT");
                String createdWhen = changeFormatCreatedWhen(rs.getString("CREATED_WHEN"));
                String reminded = rs.getString("REMINDER");
                 notebook = new Notebook(id, name, notes,
                        important, createdWhen, reminded, userID);
            }
            ConnectionPool.closeConnection(connection);
        } catch (Exception e) {
            logger.error(e);
        }
        return notebook;
    }

    public void insert(Notebook notebook) {
        Connection connection = ConnectionPool.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO NOTEBOOKS " +
                            "(NAME, NOTES, IMPORTANT, CREATED_WHEN, REMINDER, USERS_ID)" +
                            "VALUES (?, ?, ?, CURRENT_TIMESTAMP, ?, ?);");

            preparedStatement.setString(1, notebook.getName());
            preparedStatement.setString(2, notebook.getNotes());
            preparedStatement.setBoolean(3, notebook.isImportant());
            preparedStatement.setString(4, notebook.getReminder());
            preparedStatement.setLong(5, notebook.getUsersID());

            preparedStatement.executeUpdate();
            ConnectionPool.closeConnection(connection);
        } catch (Exception e) {
            logger.error(e);
        }
    }
    public void update(Notebook notebook) {
        Connection connection = ConnectionPool.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE NOTEBOOKS SET NAME = ?, NOTES = ?, IMPORTANT = ?," +
                            " CREATED_WHEN = CURRENT_TIMESTAMP, REMINDER = ? " +
                            "WHERE ID = ? AND USERS_ID = ?;");
            preparedStatement.setString(1, notebook.getName());
            preparedStatement.setString(2, notebook.getNotes());
            preparedStatement.setBoolean(3, notebook.isImportant());
            preparedStatement.setString(4, notebook.getReminder());
            preparedStatement.setLong(5, notebook.getId());
            preparedStatement.setLong(6, notebook.getUsersID());

            preparedStatement.executeUpdate();
            ConnectionPool.closeConnection(connection);
        } catch (Exception e) {
            logger.error(e);
        }
    }
    public void delete(long id, long userID) {
        Connection connection = ConnectionPool.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "DELETE FROM NOTEBOOKS WHERE ID = ? AND USERS_ID = ?;");
            preparedStatement.setLong(1, id);
            preparedStatement.setLong(2, userID);
            preparedStatement.executeUpdate();

            ConnectionPool.closeConnection(connection);
        } catch (Exception e) {
            logger.error(e);
        }
    }
    public ArrayList<Notebook> search(long userID, HttpServletRequest req, String row, String current) {
        Connection connection = ConnectionPool.getInstance().getConnection();
        ArrayList<Notebook> notebooks = new ArrayList<>();

        if (row.equals("NAME") || row.equals("NOTES") || row.equals("CREATED_WHEN")) {
            try {
                Statement stat = connection.createStatement();

                ResultSet rs = stat.executeQuery("SELECT * FROM NOTEBOOKS WHERE " +
                        "(LOWER(" + row + ") LIKE LOWER('%" + current + "%'))" +
                        " AND (USERS_ID = " + userID + ") ORDER BY IMPORTANT DESC, CREATED_WHEN DESC;;");

                while (rs.next()) {
                    long id = rs.getLong("ID");
                    String name = rs.getString("NAME");
                    String notes = rs.getString("NOTES");
                    boolean important = rs.getBoolean("IMPORTANT");
                    String createdWhen = changeFormatCreatedWhen(rs.getString("CREATED_WHEN"));
                    String reminded = rs.getString("REMINDER");
                    Notebook notebook = new Notebook(id, name, notes,
                            important, createdWhen, reminded, userID);
                    notebooks.add(notebook);

                }
            } catch (Exception e) {
                logger.error(e);
            }
        } else if (row.equals("REMINDER")) {
            try {
                Statement stat = connection.createStatement();

                ResultSet rs = stat.executeQuery("SELECT * FROM NOTEBOOKS WHERE " +
                        "(" + row + " IS " + current + ")" +
                        " AND (USERS_ID = " + userID + ") ORDER BY IMPORTANT DESC, CREATED_WHEN DESC;");

                while (rs.next()) {
                    long id = rs.getLong("ID");
                    String name = rs.getString("NAME");
                    String notes = rs.getString("NOTES");
                    boolean important = rs.getBoolean("IMPORTANT");
                    String createdWhen = changeFormatCreatedWhen(rs.getString("CREATED_WHEN"));
                    String reminded = rs.getString("REMINDER");
                    Notebook notebook = new Notebook(id, name, notes,
                            important, createdWhen, reminded, userID);
                    notebooks.add(notebook);

                }
            } catch (Exception e) {
                logger.error(e);
            }
        } else if (row.equals("IMPORTANT")) {
            try {
                Statement stat = connection.createStatement();

                ResultSet rs = stat.executeQuery("SELECT * FROM NOTEBOOKS WHERE " +
                        "(" + row + " IS " + current + ")" +
                        " AND (USERS_ID = " + userID + ") ORDER BY CREATED_WHEN DESC;");

                while (rs.next()) {
                    long id = rs.getLong("ID");
                    String name = rs.getString("NAME");
                    String notes = rs.getString("NOTES");
                    boolean important = rs.getBoolean("IMPORTANT");
                    String createdWhen = changeFormatCreatedWhen(rs.getString("CREATED_WHEN"));
                    String reminded = rs.getString("REMINDER");
                    Notebook notebook = new Notebook(id, name, notes,
                            important, createdWhen, reminded, userID);
                    notebooks.add(notebook);

                }
            } catch (Exception e) {
                logger.error(e);
            }
        }
        try {
            ConnectionPool.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notebooks;
    }
    private String changeFormatCreatedWhen(String createdWhenBefoteSplit) {
        String[] createdWhenSplit = createdWhenBefoteSplit.split(":");
        StringBuilder createdWhen = new StringBuilder();
        int count = 0;
        for(String s : createdWhenSplit) {
            if (count < 2) {
                createdWhen.append(s);
                if(count == 0) {
                    createdWhen.append(":");
                }
                count++;
            }
        }
        return createdWhen.toString();
    }
}
