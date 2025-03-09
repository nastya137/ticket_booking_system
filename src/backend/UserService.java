package backend;

import java.io.Serializable;
import java.util.List;

//Класс для обмена данными
public class UserService implements Serializable {

    private ClientCommand command;
    private  int id;
    private  String from;
    private  String to;
    private  String type;
    private  String time;
    private Passenger passenger;
    private String login;
    private User user;
    private List<Passenger> passengers;
    private List<Route> routes;
    private List<Ticket> tickets;
    private Route route;
    private int route_id;

    public UserService() {}
    public UserService(ClientCommand command) {
        this.command = command;
    }
    public ClientCommand Command() {
        return command;
    }
    public void sendId (int id) {
        this.id = id;
    }
    public int recieveId (){
        return id;
    }
    public void sendRouteId (int id) {
        this.route_id = id;
    }
    public int recieveRouteId (){
        return route_id;
    }
    public void sendFrom (String from) {
        this.from = from;
    }
    public String recieveFrom () {
        return from;
    }
    public void sendTo (String to) {
        this.to = to;
    }
    public String recieveTo () {
        return to;
    }
    public void sendType (String type) {
        this.type = type;
    }
    public String recieveType () {
        return type;
    }
    public void sendTime (String time) {
        this.time = time;
    }
    public String recieveTime () {
        return time;
    }
    public void sendPassenger (Passenger passenger) {
        this.passenger = passenger;
    }
    public Passenger recievePassenger () {
        return passenger;
    }
    public void sendPassengersList (List<Passenger> passengers) {
        this.passengers = passengers;
    }
    public List<Passenger> recievePassengersList () {
        return passengers;
    }
    public void sendRouteList (List<Route> routes) {
        this.routes = routes;
    }
    public List<Route> recieveRouteList () {
        return routes;
    }
    public void sendTicketList (List<Ticket> tickets) {
        this.tickets = tickets;
    }
    public List<Ticket> recieveTicketList () {
        return tickets;
    }
    public User recieveUser() {
        return user;
    }
    public void sendUser (User user) {
        this.user = user;
    }
    public String recieveLogin () {
        return login;
    }
    public void sendLogin (String login) {
        this.login = login;
    }
    public void sendRoute (Route route) {
        this.route = route;
    }
    public Route recieveRoute () {
        return route;
    }
}
