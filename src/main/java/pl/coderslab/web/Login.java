package pl.coderslab.web;

import pl.coderslab.dao.AdminDao;
import pl.coderslab.model.Admin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/login")
public class Login extends HttpServlet {


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        try {
            Admin loggedUser = AdminDao.checkLogin2(email, password);
            if (loggedUser.getEmail() != null) {
                HttpSession session = request.getSession();
                session.setAttribute("email", loggedUser.getEmail());
                session.setAttribute("adminId", loggedUser.getId());
                session.setAttribute("adminName", loggedUser.getFirstName());
                response.sendRedirect("/dashboard");
            } else {
                response.sendRedirect("/login");
            }
        } catch (Exception e) {
            e.printStackTrace();


        }
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/login.jsp")
                .forward(request, response);


    }
}

