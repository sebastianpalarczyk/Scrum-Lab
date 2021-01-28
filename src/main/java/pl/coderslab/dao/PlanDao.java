package pl.coderslab.dao;

import pl.coderslab.exception.NotFoundException;
import pl.coderslab.model.Plan;
import pl.coderslab.utils.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlanDao {
    private static final String CREATE_PLAN_QUERY = "INSERT INTO plan(name, description, created,admin_id) VALUES (?,?,?,?);";
    private static final String DELETE_PLAN_QUERY = "DELETE FROM plan where id = ?;";
    private static final String READ_PLAN_QUERY = "SELECT * FROM plan where id = ?;";
    private static final String READ_PLAN_ID_QUERY = "SELECT * FROM plan where admin_id = ? and name = ?;";
    private static final String UPDATE_PLAN_QUERY = "UPDATE plan SET name = ?, description = ? where id = ?;";
    private static final String FIND_ALL_PLAN_QUERY = "SELECT * FROM plan;";
    private static final String FIND_USER_PLANS_QUERY = "SELECT * FROM plan WHERE admin_id =? ;";
    private static final String FIND_LAST_PLAN_QUERY = "SELECT id FROM plan WHERE id = (SELECT MAX(id) from plan WHERE admin_id = ?);";

    public Plan create(Plan plan) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(CREATE_PLAN_QUERY, Statement.RETURN_GENERATED_KEYS);
            int idx = 0;
            statement.setString(++idx, plan.getName());
            statement.setString(++idx, plan.getDescription());
            statement.setString(++idx, plan.getCreated());
            statement.setInt(++idx, plan.getAdmin_id());
            int result = statement.executeUpdate();
            if (result != 1) {
                throw new RuntimeException("Execute update returned " + result);
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.first()) {
                    plan.setId(generatedKeys.getInt(1));
                    return plan;
                }
                else {
                    throw new RuntimeException("Generated key was not found");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Plan read(int id) {
        Plan plan = new Plan();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(READ_PLAN_QUERY)
        ) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    plan.setId(resultSet.getInt("id"));
                    plan.setName(resultSet.getString("name"));
                    plan.setDescription(resultSet.getString("description"));
                    plan.setCreated(resultSet.getString("created"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return plan;


    }

    public static Plan findPlanId(Integer adminId, String name) {
        Plan plan = new Plan();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(READ_PLAN_ID_QUERY)) {
            statement.setInt(1, adminId);
            statement.setString(2, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    plan.setId(resultSet.getInt("id"));
                    plan.setName(resultSet.getString("name"));
                    plan.setDescription(resultSet.getString("description"));
                    plan.setCreated(resultSet.getString("created"));
                    plan.setAdmin_id(resultSet.getInt("admin_id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return plan;
    }

    public List<Plan> readUserPlans(int adminId) {
        List<Plan> userPlanList = new ArrayList<Plan>();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_USER_PLANS_QUERY)
        ) {
            statement.setInt(1, adminId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Plan planToAdd = new Plan();
                    planToAdd.setId(resultSet.getInt("id"));
                    planToAdd.setName(resultSet.getString("name"));
                    planToAdd.setDescription(resultSet.getString("description"));
                    planToAdd.setCreated(resultSet.getString("created"));
                    planToAdd.setAdmin_id(resultSet.getInt("admin_id"));
                    userPlanList.add(planToAdd);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userPlanList;
    }

    public List<Plan> findAll() {
        List<Plan> planList = new ArrayList<Plan>();
        try (Connection connection = DbUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_ALL_PLAN_QUERY);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Plan planToAdd = new Plan();
                planToAdd.setId(resultSet.getInt("id"));
                planToAdd.setName(resultSet.getString("name"));
                planToAdd.setDescription(resultSet.getString("description"));
                planToAdd.setCreated(resultSet.getString("created"));
                planToAdd.setAdmin_id(resultSet.getInt("admin_id"));
                planList.add(planToAdd);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return planList;
    }

    public int findLastId(int adminId) {
        int lastId = 0;
        try (Connection connection = DbUtil.getConnection();

             PreparedStatement statement = connection.prepareStatement(FIND_LAST_PLAN_QUERY)) {
            statement.setInt(1, adminId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                lastId += resultSet.getInt("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lastId;
    }

    public void update(Plan plan) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_PLAN_QUERY)) {
            statement.setInt(3, plan.getId());
            statement.setString(1, plan.getName());
            statement.setString(2, plan.getDescription());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void delete(Integer id) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_PLAN_QUERY)) {
            statement.setInt(1, id);
            statement.executeUpdate();

            boolean deleted = statement.execute();
            if (!deleted) {
                throw new NotFoundException("Plan not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String toStringList(List<Plan> list) {
        String row = "";
        String result = "";
        for (int i = 0; i < list.size(); i++) {
            Plan plan = list.get(i);
            String id = String.valueOf(plan.getId());
            String name = plan.getName();
            String description = plan.getDescription();
            String created = plan.getCreated();
            row = id + " " + name + " " + description + " " + created + "\n";
            result += row;
        }
        return result;
    }

}