package pl.coderslab.web;

import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.Recipe;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/pageRecipes")
public class PageRecipes extends HttpServlet {
    private RecipeDao recipeDao = new RecipeDao();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<Recipe> recipeList = recipeDao.findAll();
        session.setAttribute("recipeList", recipeList);

        //response.getWriter().println(adminId+" "+userRecipeList.toString());
        getServletContext().getRequestDispatcher("/pageRecipes.jsp")
                .forward(request, response);
    }
}
