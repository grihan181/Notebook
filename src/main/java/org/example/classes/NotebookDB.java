package org.example.classes;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class NotebookDB {
    private static Connection connection;

    public static ArrayList<Notebook> select(long userID, HttpServletRequest req) {
        ArrayList<Notebook> notebooks = new ArrayList<>();
        connection = (Connection)req.getServletContext().getAttribute("dbConnection");

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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return notebooks;
    }
    public static Notebook selectOne(long id, long userID) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return notebook;
    }

    public static void insert(Notebook notebook) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void update(Notebook notebook) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void delete(long id, long userID) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "DELETE FROM NOTEBOOKS WHERE ID = ? AND USERS_ID = ?;");
            preparedStatement.setLong(1, id);
            preparedStatement.setLong(2, userID);
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static ArrayList<Notebook> search(long userID, HttpServletRequest req, String row, String current) {

        ArrayList<Notebook> notebooks = new ArrayList<>();
        connection = (Connection) req.getServletContext().getAttribute("dbConnection");
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
                e.printStackTrace();
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
                e.printStackTrace();
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
                e.printStackTrace();
            }
        }
        return notebooks;
    }
    private static String changeFormatCreatedWhen(String createdWhenBefoteSplit) {
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
