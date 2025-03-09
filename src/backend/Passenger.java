package backend;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Passenger implements Serializable {
    private int id;
    private String first_name;
    private String last_name;
    private String middle_name;
    private String passport_number;
    private String passport_series;
    private String birth_date;
    private boolean gender;
    private int user_id;
    // Конструктор
    public Passenger(String first_name, String last_name, String middle_name, String passport_number, String passport_series, String birth_date, boolean gender) {
        //this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.middle_name = middle_name;
        this.passport_number = passport_number;
        this.passport_series = passport_series;
        this.birth_date = birth_date;
        this.gender = gender;
    }
    public int getId() {
        return id;
    }
    public String getFirst_name() {
        return first_name;
    }
    public String getLast_name() {
        return last_name;
    }
    public String getMiddle_name() {
        return middle_name;
    }
    public String getPassport_number() {
        return passport_number;
    }

    public String getPassport_series() {
        return passport_series;
    }

    public String getBirth_date() {
        return birth_date;
    }
    public boolean getGender() {
        return gender;
    }
    public int getUser_id() {
        return user_id;
    }
    public void setUser_id(int id) {
        this.user_id = id;
    }
    public int getAge() throws ParseException {
        Date today = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date bdate = formatter.parse(this.getBirth_date());
        return (int)(bdate.getTime() - today.getTime()) / 1000;
    }

    @Override
    public String toString() {
        return String.format(" %s %s %s",
                this.first_name, this.last_name, this.middle_name);
    }
}
