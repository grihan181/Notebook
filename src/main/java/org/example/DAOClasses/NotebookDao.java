package org.example.DAOClasses;

import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.apache.log4j.Logger;
import org.example.DAOInterfaces.NotebookDaoInterface;
import org.example.NotebookClasses.Notebook;
import org.example.NotebookClasses.NotebookDB;
import org.example.connection.ConnectionPool;
import org.example.connection.HibernateUtil;
import org.example.modelClasses.Notebooks;
import org.example.modelClasses.Users;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NotebookDao implements NotebookDaoInterface {
    final static Logger logger = Logger.getLogger(NotebookDao.class);
    private static NotebookDao INSTANCE;
    private NotebookDao() { }

    public static synchronized NotebookDao getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new NotebookDao();
        }
        return INSTANCE;
    }

    @Override
    public Users checkUser(String username, String password) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        String hql = "FROM Users u where u.username = :username AND " +
                "u.password = :password";
        Query query = session.createQuery(hql);
        query.setParameter("username", username).setParameter("password", password);
        List<Users> users = query.getResultList();

        session.close();
        if (users.size() == 0) {
            return null;
        } else {
            Users result = null;
            for (Users u : users) {
                result = u;
            }
            return result;
        }
    }

    @Override
    public ArrayList<Notebooks> select(Users user) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        String hql = "FROM Notebooks n where n.user = :user" +
                " ORDER BY n.important DESC, n.createdWhen DESC";
        Query query = session.createQuery(hql);
        query.setParameter("user", user);
        List<Notebooks> notebooks = query.getResultList();

        session.close();
        if(notebooks.size() == 0) {
            return null;
        } else {
            return new ArrayList<>(notebooks);
        }
    }

    @Override
    public Notebooks selectOne(long id, Users user) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        String hql = "FROM Notebooks n where n.id = :id" +
                " AND  n.user = :user";
        Query query = session.createQuery(hql);
        query.setParameter("id", id);
        query.setParameter("user", user);
        List<Notebooks> notebooks = query.getResultList();

        session.close();
        if(notebooks.size() == 0) {
            return null;
        } else {
            Notebooks result = null;
            for (Notebooks u : notebooks) {
                result = u;
            }
            return result;
        }
    }
    @Override
    public int selectOne(String usename, String email) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        String hql = "FROM Users u where u.username = :username" +
                " OR  u.email = :email";
        Query query = session.createQuery(hql);
        query.setParameter("username", usename);
        query.setParameter("email", email);
        List<Users> notebooks = query.getResultList();

        session.close();
        return notebooks.size();
    }

    @Override
    public<T> void insert(T t) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            session.save(t);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            logger.error(e);
        }
        session.close();
    }


    @Override
    public void update(Notebooks notebook) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
        session.update(notebook);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            logger.error(e);
        }
        session.close();
    }

    @Override
    public void delete(long id, Users user) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            String hql = "delete from Notebooks n where n.id = :id" +
                    " AND  n.user = :user";
            Query query = session.createQuery(hql);
            query.setParameter("id", id);
            query.setParameter("user", user);
            logger.info(query.executeUpdate());
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            logger.error(e);
        }
        session.close();
    }

    @Override
    public ArrayList<Notebooks> search(Users user, String row, String current) {
        ArrayList<Notebooks> result = new ArrayList<>();
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        if (row.equals("name") || row.equals("notes") || row.equals("createdWhen")) {
            try {
                String hql;
                if(row.equals("createdWhen")) {
                     hql = "FROM Notebooks n where " +
                            "n." + row + " like ('%" + current + "%')" +
                            " AND (n.user = :user) ORDER BY n.important DESC, n.createdWhen DESC";
                } else {
                     hql = "FROM Notebooks n where " +
                            "lower(n." + row + ") like lower('%" + current + "%')" +
                            " AND (n.user = :user) ORDER BY n.important DESC, n.createdWhen DESC";
                }

                Query query = session.createQuery(hql);
                query.setParameter("user", user);
                List<Notebooks> notebooks = query.getResultList();

                if(notebooks.size() == 0) {
                    return null;
                } else {
                    result.addAll(notebooks);
                    return result;
                }
            } catch (Exception e) {
                logger.error(e);
            }
        } else if (row.equals("reminder")) {
            try {
                String hql = "FROM Notebooks n where " +
                        "(n."+ row +" is "+ current +")" +
                        " AND (n.user = :user) ORDER BY n.important DESC, n.createdWhen DESC";

                Query query = session.createQuery(hql);
                query.setParameter("user", user);
                List<Notebooks> notebooks = query.getResultList();

                if(notebooks.size() == 0) {
                    return null;
                } else {
                    result.addAll(notebooks);
                    return result;
                }
            } catch (Exception e) {
                logger.error(e);
            }
        } else if (row.equals("important")) {
            try {
                String hql = "FROM Notebooks n where " +
                        "(n."+ row +" = "+ current +")" +
                        " AND (n.user = :user) ORDER BY n.createdWhen DESC";

                Query query = session.createQuery(hql);
                query.setParameter("user", user);
                List<Notebooks> notebooks = query.getResultList();

                if(notebooks.size() == 0) {
                    return null;
                } else {
                    result.addAll(notebooks);
                    return result;
                }
            } catch (Exception e) {
                logger.error(e);
            }
        }
        return result;
    }
}
