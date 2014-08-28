package com.epam.dz.action;
import com.epam.dz.dao.DaoException;


import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

public interface Action {
    ActionResult execute(HttpServletRequest request) throws ActionException, DaoException, InterruptedException, ClassNotFoundException, SQLException;

}