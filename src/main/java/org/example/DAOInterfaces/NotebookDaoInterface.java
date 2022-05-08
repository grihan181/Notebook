package org.example.DAOInterfaces;


import org.example.modelClasses.Notebooks;
import org.example.modelClasses.Users;

import java.util.ArrayList;

public interface NotebookDaoInterface {

    public Users checkUser(String username, String password);

    public ArrayList<Notebooks> select(Users user);

    public Notebooks selectOne(long id, Users user);

    public int selectOne(String usename, String email);

    public<T> void insert(T t);

    public  void update(Notebooks notebook);

    public void delete(long id, Users user);

    public ArrayList<Notebooks> search(Users user, String row, String current);
}