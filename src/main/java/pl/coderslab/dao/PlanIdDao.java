package pl.coderslab.dao;

import pl.coderslab.model.PlanId;
import pl.coderslab.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlanIdDao {

    private static final String RECENT_PLAN_QUERY = "SELECT day_name.name as day_name, meal_name,  recipe.name as recipe_name, recipe.description as recipe_description, plan_id\n" +
            "FROM `recipe_plan`\n" +
            "         JOIN day_name on day_name.id=day_name_id\n" +
            "         JOIN recipe on recipe.id=recipe_id WHERE\n" +
            "        recipe_plan.plan_id =  (SELECT MAX(id) from plan WHERE admin_id = ?) -- zamiast 1 należy wstawić id użytkownika (tabela admins) --\n" +
            "ORDER by day_name.display_order, recipe_plan.display_order;";

    private static final String CREATE_PLAN_ID_QUERY = "INSERT INTO recipe_plan (recipe_id,  meal_name, display_order, day_name_id, plan_id) VALUES\n" +
            "(?,?,?,?,?);";

    public static List<PlanId> readCurrentPlan(Integer adminId) {
        List<PlanId> planList = new ArrayList<PlanId>();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(RECENT_PLAN_QUERY)) {

            statement.setInt(1, adminId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    PlanId planToAdd = new PlanId();
                    planToAdd.setDay_name(resultSet.getString("day_name"));
                    planToAdd.setMeal_name(resultSet.getString("meal_name"));
                    planToAdd.setRecipe_name(resultSet.getString("recipe_name"));
                    planToAdd.setRecipe_description(resultSet.getString("recipe_description"));
                    planToAdd.setPlan_id(resultSet.getInt("plan_id"));
                    planList.add(planToAdd);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return planList;
    }

    public static List<PlanId> readCurrentPlanById(Integer Id) {
        List<PlanId> planList = new ArrayList<PlanId>();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(RECENT_PLAN_QUERY)) {

            statement.setInt(1, Id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    PlanId planToAdd = new PlanId();
                    planToAdd.setDay_name(resultSet.getString("day_name"));
                    planToAdd.setMeal_name(resultSet.getString("meal_name"));
                    planToAdd.setRecipe_name(resultSet.getString("recipe_name"));
                    planToAdd.setRecipe_description(resultSet.getString("recipe_description"));
                    planToAdd.setPlan_id(resultSet.getInt("plan_id"));
                    planList.add(planToAdd);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return planList;
    }

    public PlanId create(PlanId planId) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement insertStm = connection.prepareStatement(CREATE_PLAN_ID_QUERY,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            insertStm.setInt(1, planId.getRecipe_id());
            insertStm.setString(2, planId.getMeal_name());
            insertStm.setInt(3, planId.getDisplay_order());
            insertStm.setInt(4, planId.getDay_name_id());
            insertStm.setInt(5, planId.getPlan_id());

            int result = insertStm.executeUpdate();

            if (result != 1) {
                throw new RuntimeException("Execute update returned " + result);
            }
            try (ResultSet generatedKeys = insertStm.getGeneratedKeys()) {
                if (generatedKeys.first()) {
                    planId.setId(generatedKeys.getInt(1));
                    return planId;
                } else {
                    throw new RuntimeException("Generated key was not found");
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}

