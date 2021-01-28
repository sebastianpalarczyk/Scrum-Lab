package pl.coderslab.web;

import pl.coderslab.dao.DayNameDao;
import pl.coderslab.dao.PlanDao;
import pl.coderslab.dao.PlanIdDao;
import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.DayName;
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

@WebServlet("/app/recipe/plan/add")
public class ScheduleMealRecipe extends HttpServlet {
    private PlanDao planDao = new PlanDao();
    private RecipeDao recipeDao = new RecipeDao();
    private DayNameDao dayNameDao = new DayNameDao();
    private PlanIdDao planIdDao = new PlanIdDao();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        int adminId = (int) session.getAttribute("adminId");
        String planName = request.getParameter("choosePlan");
        String mealName = request.getParameter("name");
        String recipe = request.getParameter("recipe");
        String dayName = request.getParameter("day");
        Recipe adminRecipe = recipeDao.findRecipeId(adminId, recipe);
        int planId = PlanDao.findPlanId(adminId, planName).getId();
        DayName nameDay = dayNameDao.read(dayName);
        PlanId recipePlan = new PlanId(adminRecipe.getId(),mealName,nameDay.getOrder(),nameDay.getId(),planId);
        planIdDao.create(recipePlan);
        response.sendRedirect("/app/recipe/plan/add");
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        int adminId = 0;
        if (session.getAttribute("adminId") != null) {
            adminId = (int) session.getAttribute("adminId");
        }
        List<Plan> userPlanList = planDao.readUserPlans(adminId);
        List<Recipe> userRecipeList = recipeDao.findAllAdmin(adminId);
        List<DayName> dayNameList = dayNameDao.findAll();

        session.setAttribute("planList", userPlanList);
        session.setAttribute("recipeList", userRecipeList);
        session.setAttribute("dayList", dayNameList);
        getServletContext().getRequestDispatcher("/scheduleMealRecipe.jsp")
                .forward(request, response);
    }
}
