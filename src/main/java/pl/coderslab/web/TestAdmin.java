package pl.coderslab.web;

import pl.coderslab.dao.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/testAdmin")
public class TestAdmin extends HttpServlet {


    private AdminDao adminDao = new AdminDao();
    private PlanDao planDao = new PlanDao();
    private RecipeDao recipeDao = new RecipeDao();
    private PlanIdDao planIdDao = new PlanIdDao();
    private DayNameDao dayNameDao = new DayNameDao();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            //response.getWriter().println(recipeDao.toStringList(recipeDao.findAll()));
//            String str = adminDao.read(1).toStringAdmin();
//            String str1 = adminDao.readEmail("arkadiusz.jozwiak@coderslab.pl").toStringAdmin();
//            response.getWriter().println(str);
//            response.getWriter().println(str1);
//            Date date = new Date();
            dayNameDao.delete(8);
            response.getWriter().println();
            //dayNameDao.create(new DayName(1,"Poniedzialek"));
//            response.getWriter().println(date);
//            response.getWriter().println(recipeDao.findRecipeId(1,"Przepis 12"));
            //response.getWriter().println(planDao.toStringList(planDao.findAll()));
            //response.getWriter().println(adminDao.toStringList(adminDao.findAll()));

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}