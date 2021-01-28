package pl.coderslab.model;

public class Recipe extends DayName{
    private int id;
    private String name;
    private String ingredients;
    private String description;
    private String created;
    private String updated;
    private int preparation_time;
    private String preparation;
    private int admin_id;

    public Recipe() {
    }

    public Recipe(int id, String name, String ingredients, String description, String created,
                  String updated, int preparation_time, String preparation, int admin_id) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.description = description;
        this.created = created;
        this.updated = updated;
        this.preparation_time = preparation_time;
        this.preparation = preparation;
        this.admin_id = admin_id;
    }

    public Recipe(String name, String ingredients, String description, String created, String updated, int preparation_time, String preparation, int admin_id) {
        this.name = name;
        this.ingredients = ingredients;
        this.description = description;
        this.created = created;
        this.updated = updated;
        this.preparation_time = preparation_time;
        this.preparation = preparation;
        this.admin_id = admin_id;
    }


    public Recipe(String name, String ingredients, String description, String created, int preparation_time, String preparation, int admin_id) {
        this.name = name;
        this.ingredients = ingredients;
        this.description = description;
        this.created = created;
        this.preparation_time = preparation_time;
        this.preparation = preparation;
        this.admin_id = admin_id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getDescription() {
        return description;
    }

    public String getCreated() {
        return created;
    }

    public String getUpdated() {
        return updated;
    }

    public int getPreparation_time() {
        return preparation_time;
    }

    public String getPreparation() {
        return preparation;
    }

    public int getAdmin_id() {
        return admin_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public void setPreparation_time(int preparation_time) {
        this.preparation_time = preparation_time;
    }

    public void setPreparation(String preparation) {
        this.preparation = preparation;
    }

    public void setAdmin_id(int admin_id) {
        this.admin_id = admin_id;
    }

    public String toString() {
        return id + " " + name + " " + ingredients + " " + description + " " + created + " " + updated + " " + preparation_time + " " + preparation + " " + admin_id;
    }

}
