package backend;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            DataBaseHandler dbHandler = DataBaseHandler.getInstance();
           // backend.Passenger passenger = new backend.Passenger("Иванов", "Иван", "Иванович","111111", "11 11", "1900-09-19", true);
           // dbHandler.addPassenger(passenger);
            /*List<backend.Passenger> passengers = dbHandler.getAllProducts();
            for (backend.Passenger p : passengers) {
                System.out.println(p.toString());
            }*/
            /*List<backend.Route> routes = dbHandler.getRoutesByLocations("Краснодар", "Сочи");
            for (backend.Route r : routes) {
                System.out.println(r.toString());
            }*/
            /*List<backend.Ticket> tickets = dbHandler.getTicketsByRoute(3);
            for (backend.Ticket ticket : tickets) {
                System.out.println(ticket);
            }*/

            List<Route> routes = dbHandler.getRoutesByTime("Краснодар", "Сочи", "2025-03-26 08:00:00","T");
            for (backend.Route r : routes) {
                System.out.println(r.toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}