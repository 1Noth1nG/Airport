package com.epam.dz.dao;
import java.io.Serializable;
import java.util.List;

/**
 * Унифицированный интерфейс управления персистентным состоянием объектов
 * @param <T> тип объекта персистенции
 * @param <PK> тип первичного ключа
 */
public interface GenericDao<T extends Identified<PK>, PK extends Serializable> {

    /** Создает новую запись и соответствующий ей объект */
    public T create() throws DaoException;

    /** Создает новую запись, соответствующую объекту object */
    public T persist(T object)  throws DaoException;

    /** Возвращает объект соответствующий записи с первичным ключом key или null */
    public T getByPK(PK key) throws DaoException;

    /** Сохраняет состояние объекта group в базе данных */
    public void update(T object) throws DaoException;

    /** Удаляет запись об объекте из базы данных */
    public void delete(T object) throws DaoException;

    /** Возвращает список объектов соответствующих всем записям в базе данных */
    public List<T> getAll() throws DaoException;}