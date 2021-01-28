package pl.coderslab.dao;

import pl.coderslab.exception.NotFoundException;

import pl.coderslab.model.Admin;
import pl.coderslab.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class AdminDao {

    private static final String CREATE_ADMIN_QUERY = "INSERT INTO admins(first_name,last_name,email" +
            ",password, superadmin, enable) VALUES (?,?,?,?,?,?);";
    private static final String DELETE_ADMIN_QUERY = "DELETE FROM admins where id = ?;";
    private static final String FIND_ALL_ADMINS_QUERY = "SELECT * FROM admins;";
    private static final String READ_ADMIN_QUERY = "SELECT * from admins where id = ?;";
    private static final String UPDATE_ADMIN_QUERY = "UPDATE	admins SET first_name = ? , last_name = ?, email = ?, " +
            "password = ?, superadmin = ?, enable = ? WHERE	id = ?;";
    private static final String FIND_RECENT =
            "SELECT * FROM admins ORDER BY id LIMIT ?";
    private static final String READ_ADMIN_EMAIL_QUERY = "SELECT * from admins where email = ?;";
    private static final String CHECK_USER_FROM_ADMINS_QUERY = "SELECT * from admins where email = ? and password = ?";

    public Admin read(Integer adminId) {
        Admin admin = new Admin();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(READ_ADMIN_QUERY)
        ) {
            statement.setInt(1, adminId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    admin.setId(resultSet.getInt("id"));
                    admin.setFirstName(resultSet.getString("first_name"));
                    admin.setLastName(resultSet.getString("last_name"));
                    admin.setEmail(resultSet.getString("email"));
                    admin.setPassword(resultSet.getString("password"));
                    admin.setSuperAdmin(resultSet.getInt("superadmin"));
                    admin.setEnable(resultSet.getBoolean("enable"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return admin;

    }


    public static List<Admin> findAll() {
        List<Admin> adminsList = new ArrayList<Admin>();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_ADMINS_QUERY);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Admin adminToAdd = new Admin();
                adminToAdd.setId(resultSet.getInt("id"));
                adminToAdd.setFirstName(resultSet.getString("first_name"));
                adminToAdd.setLastName(resultSet.getString("last_name"));
                adminToAdd.setEmail(resultSet.getString("email"));
                adminToAdd.setPassword(resultSet.getString("password"));
                adminToAdd.setSuperAdmin(resultSet.getInt("superadmin"));
                adminToAdd.setEnable(resultSet.getBoolean("enable"));
                adminsList.add(adminToAdd);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return adminsList;

    }

    public Admin create(Admin admin) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement insertStm = connection.prepareStatement(CREATE_ADMIN_QUERY,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            insertStm.setString(1, admin.getFirstName());
            insertStm.setString(2, admin.getLastName());
            insertStm.setString(3, admin.getEmail());
            insertStm.setString(4, admin.getPassword());
            insertStm.setInt(5, admin.getSuperAdmin());
            insertStm.setBoolean(6, admin.getEnable());
            int result = insertStm.executeUpdate();

            if (result != 1) {
                throw new RuntimeException("Execute update returned " + result);
            }

            try (ResultSet generatedKeys = insertStm.getGeneratedKeys()) {
                if (generatedKeys.first()) {
                    admin.setId(generatedKeys.getInt(1));
                    return admin;
                } else {
                    throw new RuntimeException("Generated key was not found");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void delete(Integer adminId) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_ADMIN_QUERY)) {
            statement.setInt(1, adminId);
            statement.executeUpdate();

            boolean deleted = statement.execute();
            if (!deleted) {
                throw new NotFoundException("User not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(Admin admin) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_ADMIN_QUERY)) {
            statement.setInt(7, admin.getId());
            statement.setString(1, admin.getFirstName());
            statement.setString(2, admin.getLastName());
            statement.setString(3, admin.getEmail());
            statement.setString(4, admin.getPassword());
            statement.setInt(5, admin.getSuperAdmin());
            statement.setBoolean(6, admin.getEnable());


            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List<Admin> addToAdmin(Admin admin, List<Admin> adminsList) {
        List<Admin> addAdminsList = adminsList;
        if (addAdminsList == null) {
            addAdminsList = new ArrayList<>();
        }
        addAdminsList.add(admin);
        return addAdminsList;
    }

    public String toStringList(List<Admin> list) {
        String row = "";
        String result = "";
        for (int i = 0; i < list.size(); i++) {
            Admin admin = list.get(i);
            String id = String.valueOf(admin.getId());
            String firstName = admin.getFirstName();
            String lastName = admin.getLastName();
            String email = admin.getEmail();
            String password = admin.getPassword();
            String superAdmin = String.valueOf(admin.getSuperAdmin());
            String enable = String.valueOf(admin.getEnable());
            row = id + " " + firstName + " " + lastName + " " + email + " " + password +
                    " " + superAdmin + " " + enable + "\n";
            result += row;
        }
        return result;
    }

    public static boolean checkLogin(String email, String password, List<Admin> adminsList) {
        boolean result = false;
        String checkEmail = "";
        String checkPassword = "";
        for (int i = 0; i < adminsList.size(); i++) {
            Admin admin = adminsList.get(i);
            checkEmail = admin.getEmail();
            checkPassword = admin.getPassword();
            if (checkEmail.equals(email) && checkPassword.equals(password)) {
                result = true;
            }
        }
        return result;
    }

    public List<Admin> findRecent(int size) {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement statement =
                     conn.prepareStatement((FIND_RECENT))) {
            int idx = 0;
            statement.setInt(++idx, size);
            ResultSet rs = statement.executeQuery();
            List<Admin> adminsList = new ArrayList<>();
            while (rs.next()) {
                Admin admin = new Admin(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getInt("superadmin"),
                        rs.getBoolean("enable"));
                adminsList.add(admin);
            }
            return adminsList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException("Cannot find recent due do: ", e);
        }
    }

    public static Admin checkLogin2(String email, String password) {
        Admin admin = new Admin();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(CHECK_USER_FROM_ADMINS_QUERY)
        ) {
            statement.setString(1, email);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    admin.setId(resultSet.getInt("id"));
                    admin.setFirstName(resultSet.getString("first_name"));
                    admin.setLastName(resultSet.getString("last_name"));
                    admin.setEmail(resultSet.getString("email"));
                    admin.setPassword(resultSet.getString("password"));
                    admin.setSuperAdmin(resultSet.getInt("superadmin"));
                    admin.setEnable(resultSet.getBoolean("enable"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return admin;

    }

    public Admin readEmail(String adminEmail) {
        Admin admin = new Admin();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(READ_ADMIN_EMAIL_QUERY)
        ) {
            statement.setString(1, adminEmail);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    admin.setId(resultSet.getInt("id"));
                    admin.setFirstName(resultSet.getString("first_name"));
                    admin.setLastName(resultSet.getString("last_name"));
                    admin.setEmail(resultSet.getString("email"));
                    admin.setPassword(resultSet.getString("password"));
                    admin.setSuperAdmin(resultSet.getInt("superadmin"));
                    admin.setEnable(resultSet.getBoolean("enable"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return admin;

    }
}

