package com.epam.dz.action;

import com.epam.dz.dao.DaoFactory;
import com.epam.dz.dao.UserDao;
import com.epam.dz.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class Login implements Action {
    private final static String USERNAME = "username";
    private final static String PASSWORD = "password";
    // private static final String ROLES = "roles";
    private ActionResult welcome = new ActionResult("welcome", true);
    private ActionResult login = new ActionResult("login");
    private ActionResult adminForm = new ActionResult("adminform", true);


    @Override
    public ActionResult execute(HttpServletRequest req)  {
        DaoFactory daoFactory=DaoFactory.getInstance();
        String username = req.getParameter(USERNAME);
        String password = req.getParameter(PASSWORD);
        UserDao userDao = (UserDao) daoFactory.getDao(User.class);
        User user = userDao.findByCredentials(username, password);
        if (user == null) {
            return login;
        }
        if (user.getRole().equals("ADMIN")) {
            return adminForm;
        }
        HttpSession session = req.getSession();
        session.setAttribute("user", user);
        return welcome;
    }
}
