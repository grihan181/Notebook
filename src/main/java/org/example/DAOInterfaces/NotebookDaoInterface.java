package org.example.DAOInterfaces;


import org.example.modelClasses.Notebooks;
import org.example.modelClasses.Users;

import java.util.ArrayList;

public interface NotebookDaoInterface {

    Users checkUser(String username, String password);

    ArrayList<Notebooks> select(Users user);

    Notebooks selectOne(long id, Users user);

    int selectOne(String usename, String email);

    <T> void insert(T t);

    void update(Notebooks notebook);

    void delete(long id, Users user);

    ArrayList<Notebooks> search(Users user, String row, String current);
}