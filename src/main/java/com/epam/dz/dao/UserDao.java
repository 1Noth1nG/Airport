package com.epam.dz.dao;

import com.epam.dz.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class UserDao extends AbstractJDBCDao<User,Integer> {

    Connection connection;
    public UserDao(Connection connection) {
        super(connection);
        this.connection=connection;
    }

    public User findByCredentials(String username, String password){
        User user = getUserByUsername(username);
        if (!user.getUsername().equals(username) || !user.getPassword().equals(password)) return null;

        return user;
    }

    public User getUserByUsername(String username)  {
        List<User> list;
        String sql = getSelectQuery();
        sql += " WHERE LOGIN = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);

            if (list == null || list.size() == 0) {
                try {
                    throw new DaoException("Record with PK = " + username + " not found.");
                } catch (DaoException e) {
                    throw new RuntimeException(e);
                }
            }
            if (list.size() > 1) {
                try {
                    throw new DaoException("Received more than one record.");
                } catch (DaoException e) {
                    throw new RuntimeException(e);
                }
            }
            return list.iterator().next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
    @Override
    public String getSelectQuery() {
        return "SELECT ID,LOGIN,PASSWORD,ROLE FROM USER";
    }

    @Override
    public String getSelectQueryForRange() {
        return "SELECT ID,LOGIN,PASSWORD,ROLE FROM USER ORDER BY ID LIMIT ? OFFSET ?;";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO USER(LOGIN,PASSWORD,ROLE) \n" +
                "VALUES (?, ?, ?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE USER \n" +
                "SET LOGIN = ?, PASSWORD = ?, ROLE = ? \n" +
                "WHERE ID = ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM USER WHERE ID= ?;";
    }

    @Override
    public List<User> parseResultSet(ResultSet rs) {
        LinkedList<User> result = new LinkedList<User>();
        try {
            while (rs.next()) {
                PersistUser persistUser = new PersistUser();
                persistUser.setId(rs.getInt("ID"));
                persistUser.setUsername(rs.getString("LOGIN"));
                persistUser.setPassword(rs.getString("PASSWORD"));
                persistUser.setRole(rs.getString("ROLE"));

                result.add(persistUser);
            }
        } catch (Exception e) {
            try {
                throw new DaoException(e);
            } catch (DaoException e1) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, User object)  {
        try {
            statement.setString(1,object.getUsername());
            statement.setString(2, object.getPassword());
            statement.setString(3, object.getRole());



        }catch (Exception e){
            try {
                throw new DaoException(e);
            } catch (DaoException e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, User object){
        try {
            statement.setString(1,object.getUsername());
            statement.setString(2,object.getPassword());
            statement.setString(3, object.getRole());
            statement.setInt(4, object.getId());

        }catch (SQLException e){
            try {
                throw new DaoException(e);
            } catch (DaoException e1) {
                throw new RuntimeException(e);
            }

        }
    }

    @Override
    public User create()  {
        User user=new User();
        user.setUsername("неопред");
        user.setPassword("неопр");
        user.setRole("неопред");

        return persist(user);
    }

    private class PersistUser extends User {
        @Override
        public void setId(int id) {
            super.setId(id);
        }
    }
}
