package pl.coderslab.dao;

import pl.coderslab.model.DetailsCurrentPlan;
import pl.coderslab.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DetailsCurrentPlanDao {

    private static final String RECIPE_DESCRIPTION_QUERY = "SELECT day_name.name as day_name, meal_name, recipe.name as recipe_name, recipe.description as recipe_description\n" +
            "FROM `recipe_plan`\n" +
            "JOIN day_name on day_name.id=day_name_id\n" +
            "JOIN recipe on recipe.id=recipe_id WHERE plan_id = ? -- zamiast 6 należy wstawić id planu do pobrania --\n" +
            "ORDER by day_name.display_order, recipe_plan.display_order;";

    private static final String PLAN_NAME_QUERY = "SELECT name FROM plan where id = ?;";

    public List<DetailsCurrentPlan> detailsPlan(Integer planId) {
        List<DetailsCurrentPlan> planList = new ArrayList<DetailsCurrentPlan>();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(RECIPE_DESCRIPTION_QUERY)) {

            statement.setInt(1, planId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    DetailsCurrentPlan planToAdd = new DetailsCurrentPlan();
                    planToAdd.setDay_name(resultSet.getString("day_name"));
                    planToAdd.setMeal_name(resultSet.getString("meal_name"));
                    planToAdd.setRecipe_name(resultSet.getString("recipe_name"));
                    planToAdd.setRecipe_description(resultSet.getString("recipe_description"));
                    planList.add(planToAdd);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return planList;
    }

    public String toStringListCurrentPlan(List<DetailsCurrentPlan> list) {
        String row = "";
        String result = "";
        for (int i = 0; i < list.size(); i++) {
            DetailsCurrentPlan detailsCurrentPlan = list.get(i);
            String day_name = detailsCurrentPlan.getDay_name();
            String meal_name = detailsCurrentPlan.getMeal_name();
            String recipe_name = detailsCurrentPlan.getRecipe_name();
            String recipe_description = detailsCurrentPlan.getRecipe_description();

            row = day_name + " " + meal_name + " " + recipe_name + " " + recipe_description + "\n";
            result += row;
        }
        return result;
    }

    public String namePlan(Integer planId) {
        String name = new String();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(PLAN_NAME_QUERY)) {

            statement.setInt(1, planId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {

                    name = resultSet.getString("name");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return name;
    }

}
