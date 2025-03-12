package backend;

import java.io.Serializable;

public class Ticket implements Serializable {
    private static final long serialVersionUID = -6125269480840690582L;
    private int id;
    private int route_id;
    private int seat_id;
    private int passenger_id;
    private String date_time;
    private String status;
    private String seat_number;
    //additional for presentation lvl
    private Passenger passenger;
    //additional for train
    private String carriage_type;
    private int carriage_number;
    private Boolean is_upper;
    public Ticket(int id, int route_id, int seat_id, String date_time, String status, String seat_number) {
        this.id = id;
        this.route_id = route_id;
        this.seat_id = seat_id;
        this.passenger_id = 0;
        this.date_time = date_time;
        this.status = status;
        this.passenger = null;
        this.seat_number = seat_number;
        this.carriage_type = null;
        this.carriage_number = 0;
    }
    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }
    public void setSeat_number(String seat_number) {
        this.seat_number = seat_number;
    }
    public void setCarriage_type(String carriage_type) {
        this.carriage_type = carriage_type;
    }
    public void setCarriage_number(int carriage_number) {
        this.carriage_number = carriage_number;
    }
    public void setIs_upper(Boolean is_upper) {
        this.is_upper = is_upper;
    }
    public int getSeat_id (){
        return seat_id;
    }

    @Override
    public String toString() {
        if (carriage_type != null) {
            return (carriage_type=="2"?"Плацкарт":"Купе") + " " + (carriage_number) + " вагон " + this.seat_number+" место ("+((this.is_upper)?"верхняя":"нижняя")+" полка)";
        }
        else {
        return "Место №"+this.seat_number;
        }
    }
}
