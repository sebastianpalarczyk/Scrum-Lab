package pl.coderslab.model;

public class DayName {

    private int id;
    private String name;
    private int order;

    public String toString() {
        return id + " " + name + " " + order;
    }

    public DayName() {

    }

    public DayName(int id, String dayName, int order) {
        this.id = id;
        this.name = dayName;
        this.order = order;
    }

    public DayName(int order, String dayName) {
        this.order = order;
        this.name = dayName;
    }

    public int getId() {
        return id;
    }

    public String getDayName() {
        return name;
    }

    public int getOrder() {
        return order;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDayName(String dayName) {
        this.name = dayName;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}

