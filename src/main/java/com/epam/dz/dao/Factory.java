package com.epam.dz.dao;

import java.sql.SQLException;

/** Фабрика объектов для работы с базой данных */

public interface Factory<Context>{

    public interface DaoCreator<Context> {
        public GenericDao create(Context context);
    }
    /** Возвращает подключение к базе данных */
    public Context getContext() throws DaoException, ClassNotFoundException, InterruptedException;

    /** Возвращает объект для управления персистентным состоянием объекта */
    public GenericDao getDao( Class dtoClass) throws DaoException;

    public void releaseContext() throws SQLException;
}