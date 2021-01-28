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
import java.time.LocalDateTime;

@WebServlet("/app-edit-recipe")
public class EditRecipe extends HttpServlet {

    RecipeDao recipeDao = new RecipeDao();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Recipe recipe1 = (Recipe) session.getAttribute("recipe");
        int id = recipe1.getId();
        response.getWriter().println(id);
        String updated = String.valueOf(LocalDateTime.now());
        response.getWriter().println(updated);
        int admin_id = recipe1.getAdmin_id();
        String created = recipe1.getCreated();
        String recipeName = request.getParameter("recipe_name");
        String description = request.getParameter("description");
        String preparation = request.getParameter("preparation");
        int preparationTime = Integer.parseInt(request.getParameter("preparation_time"));
        String ingredients = request.getParameter("ingredients");
        Recipe recipe = new Recipe(id, recipeName, ingredients, description, created, updated,
                preparationTime, preparation, admin_id);

        recipeDao.update(recipe);
        recipeDao.read(id);
        session.setAttribute("recipe", recipe);

        getServletContext().getRequestDispatcher("/editRecipe.jsp")
                .forward(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        int id = Integer.parseInt(request.getParameter("id"));

        Recipe recipe = recipeDao.read(id);
        session.setAttribute("recipe", recipe);

        getServletContext().getRequestDispatcher("/editRecipe.jsp")
                .forward(request, response);
    }
}
