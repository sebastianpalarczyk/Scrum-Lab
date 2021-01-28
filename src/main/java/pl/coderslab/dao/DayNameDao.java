package pl.coderslab.dao;

import pl.coderslab.exception.NotFoundException;
import pl.coderslab.model.DayName;
import pl.coderslab.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DayNameDao {
    private static final String FIND_ALL_DAY_NAME_QUERY = "SELECT * FROM day_name;";
    private static final String READ_DAY_NAME_QUERY = "SELECT * from day_name where name = ?;";
    private static final String CREATE_DAY_NAME_QUERY = "INSERT INTO day_name(id, name, display_order) VALUES (?,?,?);";
    private static final String DELETE_DAY_NAME_QUERY = "DELETE FROM day_name where id = ?;";
    private static final String UPDATE_DAY_NAME_QUERY = "UPDATE    day_name SET display_order = ?  WHERE  name = ?;";
    private static final String FIND_RECENT = "SELECT * FROM day_name ORDER BY id LIMIT ?";

    public DayName read(String name) {
        DayName dayName = new DayName();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(READ_DAY_NAME_QUERY)
        ) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    dayName.setId(resultSet.getInt("id"));
                    dayName.setDayName(resultSet.getString("name"));
                    dayName.setOrder(resultSet.getInt("display_order"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dayName;
    }

    public List<DayName> findAll() {
        List<DayName> dayNamesList = new ArrayList<DayName>();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_DAY_NAME_QUERY);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                DayName dayNameToAdd = new DayName();
                dayNameToAdd.setId(resultSet.getInt("id"));
                dayNameToAdd.setDayName(resultSet.getString("name"));
                dayNameToAdd.setOrder(resultSet.getInt("display_order"));
                dayNamesList.add(dayNameToAdd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dayNamesList;
    }

    public DayName create(DayName dayName) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement insertStm = connection.prepareStatement(CREATE_DAY_NAME_QUERY,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            insertStm.setInt(1, dayName.getId());
            insertStm.setString(2, dayName.getDayName());
            insertStm.setInt(3, dayName.getOrder());
            int result = insertStm.executeUpdate();
            if (result != 1) {
                throw new RuntimeException("Execute update returned " + result);
            }
            try (ResultSet generatedKeys = insertStm.getGeneratedKeys()) {
                if (generatedKeys.first()) {
                    dayName.setId(generatedKeys.getInt(1));
                    return dayName;
                } else {
                    throw new RuntimeException("Generated key was not found");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void delete(Integer id) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_DAY_NAME_QUERY)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            boolean deleted = statement.execute();
            if (!deleted) {
                throw new NotFoundException("Day not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(DayName dayName) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_DAY_NAME_QUERY)) {
            statement.setString(2, dayName.getDayName());
            statement.setInt(1, dayName.getOrder());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<DayName> findRecent(int size) {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement statement =
                     conn.prepareStatement((FIND_RECENT))) {
            int idx = 0;
            statement.setInt(++idx, size);
            ResultSet rs = statement.executeQuery();
            List<DayName> dayNamesList = new ArrayList<>();
            while (rs.next()) {
                DayName dayName = new DayName(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("display_order"));
                dayNamesList.add(dayName);
            }
            return dayNamesList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException("Cannot find recent due do: ", e);
        }
    }

    public String toStringList(List<DayName> list) {
        String row = "";
        String result = "";
        for (int i = 0; i < list.size(); i++) {
            DayName dayName = list.get(i);
            String id = String.valueOf(dayName.getId());
            String name = dayName.getDayName();
            String order = String.valueOf(dayName.getOrder());
            row = id + " " + name + " " + order + "\n";
            result += row;
        }
        return result;
    }
}