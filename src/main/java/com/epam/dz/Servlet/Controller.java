package com.epam.dz.Servlet;

import com.epam.dz.action.Action;
import com.epam.dz.action.ActionException;
import com.epam.dz.action.ActionFactory;
import com.epam.dz.action.ActionResult;
import com.epam.dz.dao.DaoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;


public class Controller extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String actionName = request.getMethod() + request.getPathInfo();
        LOGGER.info(" Экшиннэйм приходящий в фабрику: {}", actionName);
        Action action = ActionFactory.getAction(actionName);
        if (action == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "URL WAS NOT FOUNDED");
            LOGGER.info("Это путь контекстный в null: {}", request.getContextPath());
            return;
        }

        ActionResult result = null;
        try {
            result = action.execute(request);
            if (result.isRedirection()) {
                response.sendRedirect(request.getContextPath() + "/" + result.getView());
                LOGGER.info("ContextPath with View page {},{},{}", request.getContextPath(), "/", result.getView());

                return;
            }
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/" + result.getView() + ".jsp");
            requestDispatcher.forward(request, response);
            LOGGER.info("Путь дисптетчеру, если запрос не отправляется на экшен: {},{},{}", "/WEB-INF/", result.getView(), ".jsp");

        } catch (ActionException e) {
//            response.sendError(500, e.getMessage());
//            e.printStackTrace();
            throw new ServletException(e);


        } catch (InterruptedException e) {

            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

}
