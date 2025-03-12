package backend;

import java.io.Serializable;

public class Order implements Serializable {
    private int id;
    private int user_id;
    private String order_datetime;
    private String status;
    private double sum;
    private String currency_unit;
    public Order(int user_id, String order_datetime, double sum, String currency_unit) {
        this.user_id = user_id;
        this.order_datetime = order_datetime;
        this.sum = sum;
        this.currency_unit = currency_unit;
    }
    public void setOrder_datetime(String order_datetime) {
        this.order_datetime = order_datetime;
    }
    public void setSum(double sum) {
        this.sum = sum;
    }
    public void setCurrency_unit(String currency_unit) {
        this.currency_unit = currency_unit;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getUser_id() {
        return user_id;
    }
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    public String getOrder_datetime() {
        return order_datetime;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public double getSum() {
        return sum;
    }
    public String getCurrency_unit() {
        return currency_unit;
    }
    public String toString() {
        return "Заказ [id=" + id + ", user_id=" + user_id + ", order_datetime="+order_datetime+", status="+status+", "+sum+" "+currency_unit+"]";
    }
}
