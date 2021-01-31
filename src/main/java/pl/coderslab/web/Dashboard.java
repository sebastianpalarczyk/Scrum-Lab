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
    DetailsCurrentPlanDao detailsCurrentPlanDao = new DetailsCurrentPlanDao();


    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        String adminId = session.getAttribute("adminId").toString();
        int id = Integer.parseInt(adminId);

        int plansNumber = amountPlans(planDao.findAll(), id);
        int recipesNumber = amountRecipes(recipeDao.findAll(), id);

        int planId = planDao.findLastId(id);
        List<DetailsCurrentPlan> currentPlan = detailsCurrentPlanDao.detailsPlan(planId);
        String namePlan = detailsCurrentPlanDao.namePlan(planId);
        List<Recipe> recipes = recipeDao.findAllAdmin(id);
        List<DetailsCurrentPlan> currentPlanList = addRecipeIdToCurrentPlan(currentPlan,recipes);

        session.setAttribute("plansNumber", plansNumber);
        session.setAttribute("recipesNumber", recipesNumber);
        session.setAttribute("currentPlan", currentPlanList);
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

    public List<DetailsCurrentPlan> addRecipeIdToCurrentPlan(List<DetailsCurrentPlan> currentPlanList, List<Recipe> recipeList){
        for(int i=0; i<currentPlanList.size(); i++){
            for(int j=0; j<recipeList.size(); j++ ) {
                if (currentPlanList.get(i).getRecipe_name().equals(recipeList.get(j).getName())) {
                    currentPlanList.get(i).setId(recipeList.get(j).getId());
                }
            }
        }
        return currentPlanList;
    }

}


