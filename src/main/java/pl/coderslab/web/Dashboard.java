package pl.coderslab.web;


import pl.coderslab.dao.*;
import pl.coderslab.model.*;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/dashboard")
public class Dashboard extends HttpServlet {

    PlanDao planDao = new PlanDao();
    RecipeDao recipeDao = new RecipeDao();
    AdminDao adminDao = new AdminDao();
    DetailsCurrentPlanDao detailsCurrentPlanDao = new DetailsCurrentPlanDao();
    PlanIdDao planIdDao = new PlanIdDao();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        String admin = (String) session.getAttribute("email");
        String adminId = session.getAttribute("adminId").toString();
        int id = Integer.parseInt(adminId);

        int plansNumber = amountPlans(planDao.findAll(), id);
        int recipesNumber = amountRecipes(recipeDao.findAll(), id);

        int planId = planDao.findLastId(id);
        List<DetailsCurrentPlan> currentPlan = detailsCurrentPlanDao.detailsPlan(planId);
        String namePlan = detailsCurrentPlanDao.namePlan(planId);


        session.setAttribute("plansNumber", plansNumber);
        session.setAttribute("recipesNumber", recipesNumber);
        session.setAttribute("currentPlan", currentPlan);
        session.setAttribute("namePlan", namePlan);


        getServletContext().getRequestDispatcher("/dashboard.jsp")
                .forward(request, response);


    }

    static public int amountPlans(List<Plan> planList, int id) {
        int number = 0;
        for (Plan element : planList) {
            if (id == element.getAdmin_id()) {
                number++;
            }
        }
        return number;
    }

    static public int amountRecipes(List<Recipe> recipeList, int id) {
        int number = 0;
        for (Recipe element : recipeList) {
            if (id == element.getAdmin_id()) {
                number++;
            }
        }
        return number;


    }
}
