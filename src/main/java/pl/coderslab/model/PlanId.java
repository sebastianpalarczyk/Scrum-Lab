package pl.coderslab.model;

public class PlanId extends Recipe {

    private int id;
    private String day_name;
    private String meal_name;
    private String recipe_name;
    private String recipe_description;
    private int plan_id;
    private int display_order;
    private int recipe_id;
    private int day_name_id;

//    public PlanId(int id, int id2, String mealName, int i, int mealNumber, int planId) {
//    }

    public String toStringPlanId() {
        return day_name + " " + meal_name + " " + recipe_name + " " + recipe_description + " " + plan_id;
    }

    public PlanId() {
    }


//    public PlanId(String day_name, String meal_name, String recipe_name, int plan_id) {
//        this.day_name = day_name;
//        this.meal_name = meal_name;
//        this.recipe_name = recipe_name;
//        this.plan_id = plan_id;
//    }


    public PlanId(int recipe_id, String meal_name, int display_order, int day_name_id, int plan_id) {
        this.meal_name = meal_name;
        this.plan_id = plan_id;
        this.display_order = display_order;
        this.recipe_id = recipe_id;
        this.day_name_id = day_name_id;
    }

    public String getDay_name() {
        return day_name;
    }

    public void setDay_name(String day_name) {
        this.day_name = day_name;
    }

    public String getMeal_name() {
        return meal_name;
    }

    public void setMeal_name(String meal_name) {
        this.meal_name = meal_name;
    }

    public String getRecipe_name() {
        return recipe_name;
    }

    public void setRecipe_name(String recipe_name) {
        this.recipe_name = recipe_name;
    }

    public String getRecipe_description() {
        return recipe_description;
    }

    public void setRecipe_description(String recipe_description) {
        this.recipe_description = recipe_description;
    }

    public int getPlan_id() {
        return plan_id;
    }

    public void setPlan_id(int plan_id) {
        this.plan_id = plan_id;
    }

    public int getDisplay_order() {
        return display_order;
    }

    public void setDisplay_order(int display_order) {
        this.display_order = display_order;
    }

    public int getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(int recipe_id) {
        this.recipe_id = recipe_id;
    }

    public int getDay_name_id() {
        return day_name_id;
    }

    public void setDay_name_id(int day_name_id) {
        this.day_name_id = day_name_id;
    }
}
