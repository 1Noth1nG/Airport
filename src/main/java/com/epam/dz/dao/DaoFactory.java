package com.epam.dz.dao;
import com.epam.dz.connection.ConnectionPool;
import com.epam.dz.entity.User;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class DaoFactory implements Factory<Connection> {
    private Map<Class, Factory.DaoCreator> creators;
    private static DaoFactory instance = new DaoFactory();
    public static DaoFactory getInstance(){
        instance.getContext();
        return instance;
    }
    private Connection connection;
    @Override
    public Connection getContext() {
        ConnectionPool.init();
        ConnectionPool pool = getPool();
        Connection connection = pool.takeConnection();
        this.connection=connection;
        return connection;

    }

    private ConnectionPool getPool() {
        return ConnectionPool.getInstance();
    }

    @Override
    public GenericDao getDao(Class dtoClass)  {
        Factory.DaoCreator creator = creators.get(dtoClass);
        if (creator == null) {
            try {
                throw new DaoException("Dao object for " + dtoClass + " not found.");

            } catch (DaoException e) {
                throw new RuntimeException(e);
            }
        }
        return creator.create(connection);
    }

    @Override
    public void releaseContext()  {
        ConnectionPool pool=getPool();
        pool.releaseConnection(connection);

        ConnectionPool.dispose();
    }

    public DaoFactory() {

        creators = new HashMap<Class, DaoCreator>();

        creators.put(User.class, new DaoCreator<Connection>() {
            @Override
            public UserDao create(Connection connection) {
                return new UserDao(connection);
            }
        });


    }
}

