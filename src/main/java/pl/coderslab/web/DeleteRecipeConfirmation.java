package pl.coderslab.web;

import pl.coderslab.model.Recipe;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/DeleteRecipeConfirmation")
public class DeleteRecipeConfirmation extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String idString = request.getParameter("id");
            if (idString != null) {
                int id = Integer.parseInt(idString);
                HttpSession session = request.getSession();
                session.setAttribute("idToDelete", id);


                response.sendRedirect("/deleteRecipeConfirmation.jsp");
            } else response.sendRedirect("/recipes");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}