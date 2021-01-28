package pl.coderslab.web;

import pl.coderslab.dao.PlanDao;
import pl.coderslab.dao.PlanIdDao;
import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.Plan;
import pl.coderslab.model.PlanId;
import pl.coderslab.model.Recipe;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/appRecipeDetails")
public class RecipeDetails extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Recipe recipe = RecipeDao.read(id);
            HttpSession session = request.getSession();
            session.setAttribute("recipe", recipe);
            response.sendRedirect("/appRecipeDetails.jsp");
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
