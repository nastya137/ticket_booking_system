package backend;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Route implements Serializable {
    private static final long serialVersionUID = 2240724479495358994L;
    //информация из БД для создания экземпляра
    private int route_id;
    private String station_from_name;
    private String station_to_name;
    private String starts_at;
    private String ends_at;
    private double price_adult;
    private double price_child;
    private String has_stops;
    private String company_name;
    private String vehicle_type;
    private String currency_unit;

    //дополнительная информация, которая будет получена при запросе данных о рейсе
    private String route_from_name;
    private String route_to_name;
    private String vehicle_number;
    private String vehicle_model;
    private String vehicle_brand;
    private String route_from_address;
    private String route_to_address;
    private String company_contact;
    private String station_from_contact;
    private String station_to_contact;
    private List<RouteStop> route_stops;
    private List<Ticket> tickets;

    public Route(int id, String station_from, String station_to, String starts_at, String ends_at, String has_stops, String company_name, String vehicle_type) {
        this.route_id = id;
        this.station_from_name = station_from;
        this.station_to_name = station_to;
        this.starts_at = starts_at;
        this.ends_at = ends_at;
        this.has_stops = has_stops;
        this.company_name = company_name;
        this.vehicle_type = vehicle_type;
        this.route_stops = new ArrayList<>();
    }

    public String getCompany_name() {
        return company_name;
    }
    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }
    public void setRoute_from(String route_from) {
        this.route_from_name = route_from;
    }
    public String getStationFromName(){
        return station_from_name;
    }
    public String getStationToName(){
        return station_to_name;
    }
    public double getPrice_adult(){
        return price_adult;
    }
    public String getStarts_at(){
        return starts_at;
    }
    public String getEnds_at(){
        return ends_at;
    }
    public void setStation_to_name(String station_to_name) {
        this.station_to_name = station_to_name;
    }
    public void setPrice_child(double price_child) {
        this.price_child = price_child;
    }
    public Double getPrice_child() {
        return price_child;
    }
    public void setVehicle_number(String vehicle_number) {
        this.vehicle_number = vehicle_number;
    }
    public String getVehicle_number() {
        return vehicle_number;
    }
    public void setVehicle_model(String vehicle_model) {
        this.vehicle_model = vehicle_model;
    }
    public String getVehicle_model() {
        return vehicle_model;
    }
    public void setVehicle_brand(String vehicle_brand) {
        this.vehicle_brand = vehicle_brand;
    }
    public String getVehicle_brand() {
        return vehicle_brand;
    }
    public void setRoute_from_address(String route_from_address) {
        this.route_from_address = route_from_address;
    }
    public String getRoute_from_address() {
        return route_from_address;
    }
    public void setRoute_to_address(String route_to_address) {
        this.route_to_address = route_to_address;
    }
    public String getRoute_to_address() {
        return route_to_address;
    }
    public String getCompany_contact() {
        return company_contact;
    }
    public void setCompany_contact(String company_contact) {
        this.company_contact = company_contact;
    }
    public String getStation_from_contact() {
        return station_from_contact;
    }
    public void setStation_from_contact(String station_from_contact) {
        this.station_from_contact = station_from_contact;
    }
    public String getStation_to_contact() {
        return station_to_contact;
    }
    public void setStation_to_contact(String station_to_contact) {
        this.station_to_contact = station_to_contact;
    }
    public void setPrice_adult(double price_adult){
        this.price_adult = price_adult;
    }
    public String getVehicleType(){
        if (vehicle_type == "B"){
            return "Автобус";
        }
        else if (vehicle_type == "T"){
            return "Поезд";
        }
        else if (vehicle_type == "A"){
            return "Самолёт";
        }
        return "";
    }
    public void setRoute_to_name(String route_to_name) {
        this.route_to_name = route_to_name;
    }
    public String getRoute_from_name(){
        return route_from_name;
    }
    public String getRoute_to_name(){
        return route_to_name;
    }
    public List<RouteStop> getRoute_stops(){
        return route_stops;
    }
    public void setRoute_stops(List<RouteStop> route_stops) {
        this.route_stops = route_stops;
    }
    public String hasStops(){
        return has_stops;
    }
    public int getId(){
        return route_id;
    }
    public void setCurrencyUnit(String currency_unit){
        this.currency_unit = currency_unit;
    }
    public String getCurrencyUnit(){
        return  this.currency_unit;
    }
    public void setTickets(List<Ticket> tickets){
        this.tickets = tickets;
    }
    public List<Ticket> getTickets(){
        return tickets;
    }
    @Override
    public String toString() {
        String s = this.route_id+" "+this.station_from_name+" "+this.station_to_name+" "+this.route_from_name+" "+this.route_to_name+" "+this.starts_at+" "+this.ends_at+" "+this.has_stops+" "+this.company_name+" "
                +this.vehicle_type + " "+this.currency_unit+" "+this.price_adult;
         if (this.hasStops().indexOf('1')!=-1){
            for (RouteStop stop : this.route_stops) {
                s += "\n"+stop.toString();
            }
        }
         else s+=" 123";
        return s;
    }
}
