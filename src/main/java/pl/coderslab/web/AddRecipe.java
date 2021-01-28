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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet("/app/recipe/add")
public class AddRecipe extends HttpServlet {
    private RecipeDao recipeDao = new RecipeDao();

    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/app-add-recipe.jsp")
                .forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        int adminId = 0;
        if (session.getAttribute("adminId") != null) {
            adminId = (int) session.getAttribute("adminId");
        }
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String ingredients = request.getParameter("ingredients");
        String preparation = request.getParameter("preparation");
        int preparation_time = Integer.parseInt(request.getParameter("preparation_time"));
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String created = myDateObj.format(myFormatObj);
        if (name != null) {
            Recipe recipe = new Recipe(name, ingredients, description, created, created, preparation_time, preparation, adminId);
            recipeDao.create(recipe);

        }

        response.sendRedirect("/recipes");


    }
}

