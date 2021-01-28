package pl.coderslab.model;

public class Admin {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private int superAdmin;
    private boolean enable;

    public Admin() {

    }

    public Admin(String email) {
        this.email = email;
    }

    public String toStringAdmin() {
        return id + " " + firstName + " " + lastName + " " + email + " " + password + " " + superAdmin + " " + enable;
    }

    public Admin(int id,
                 String firstName,
                 String lastName,
                 String email,
                 String password,
                 int superAdmin,
                 boolean enable) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.superAdmin = superAdmin;
        this.enable = enable;
    }

    public Admin(String firstName,
                 String lastName,
                 String email,
                 String password,
                 int superAdmin,
                 boolean enable) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.superAdmin = superAdmin;
        this.enable = enable;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getSuperAdmin() {
        return superAdmin;
    }

    public boolean getEnable() {
        return enable;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSuperAdmin(int superAdmin) {
        this.superAdmin = superAdmin;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
