package Repository;

import Database.DBCustomConnector;
import models.Users;

import java.sql.*;
import java.util.ArrayList;

public class UsersRepository {

    private Connection connection;
    private String tableName;
    public UsersRepository(String tableName) {

        this.connection = DBCustomConnector.getConnection();
    }





}
