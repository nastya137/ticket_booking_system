package backend;

import org.sqlite.JDBC;

import java.sql.*;
import java.util.*;

public class DataBaseHandler {
    private static final String CON_STR = "jdbc:sqlite:C:/Users/User/Desktop/transport.db";
    private static DataBaseHandler instance = null;

    public static synchronized DataBaseHandler getInstance() throws SQLException {
        if (instance == null)
            instance = new DataBaseHandler();
        return instance;
    }

    private Connection connection;

    private DataBaseHandler() throws SQLException {
        DriverManager.registerDriver(new JDBC());
        this.connection = DriverManager.getConnection(CON_STR);
    }

    public List<Passenger> getPassengersAddedByUser(int user_id) {
        try (Statement statement = this.connection.createStatement()) {
            List<Passenger> passengers = new ArrayList<Passenger>();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM passengers WHERE user_added_by_id = '" + user_id + "'");
            while (resultSet.next()) {
                passengers.add(new Passenger(
                        //resultSet.getInt("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("middle_name"),
                        resultSet.getString("birth_date"),
                        resultSet.getString("document_number"),
                        resultSet.getString("document_series"),
                        resultSet.getBoolean("sex")));
            }
            return passengers;

        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
    public List<Route> getRoutesByLocations(String from, String to, String vehicle_type) {
        try (Statement statement = this.connection.createStatement()) {
            List<Route> routes = new ArrayList<Route>();
            ResultSet resultSet = statement.executeQuery("SELECT " +
                    "routes.id AS c1, points.name AS c2, " +
                    "localities.name AS c3, " +
                    "routes.start_at AS c4, routes.stop_at as c6, routes.has_stops AS c7, " +
                    "company.name AS c8, vehicles.type AS c9 " +
                    "FROM routes JOIN points AS points ON routes.station_from_id = points.id " + //AND r.station_to_id = points.id
                    "JOIN localities AS localities ON points.locality_id = localities.locality_id " +
                    "JOIN company AS company ON routes.company_id = company.company_id " +
                    "JOIN vehicles AS vehicles ON routes.vehicle_id = vehicles.id " +
                    "WHERE routes.station_from_id IN (SELECT points.id FROM points WHERE points.locality_id = (SELECT locality_id FROM localities WHERE name = '" + from + "'"+
                    ")) AND station_to_id IN (SELECT id FROM points WHERE locality_id = (SELECT locality_id FROM localities WHERE name = '" + to + "'"+")) AND vehicles.type = '" + vehicle_type+ "'"
            );
           while (resultSet.next()) {
               /* routes.add(new Route(
                        resultSet.getInt("c1"),
                        resultSet.getString("c2"),
                        resultSet.getString("c3"),
                        resultSet.getString("c4"),
                        resultSet.getString("c6"),
                        resultSet.getString("c7"),
                        resultSet.getString("c8"),
                        resultSet.getString("c9")));*/
            }
            return routes;

        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
    public List<Route> getRoutesByTime(String from, String to, String dtime, String vehicle_type) {
        try (Statement statement = this.connection.createStatement()) {
            List<Route> routes = new ArrayList<Route>();
            ResultSet resultSet = statement.executeQuery("SELECT " +
                    "routes.id AS c1, (SELECT points.name FROM points WHERE points.id = routes.station_from_id) AS c2, (SELECT points.name FROM points WHERE points.id = routes.station_to_id) AS c11, " +
                    "routes.start_at AS c4, routes.stop_at as c6, routes.has_stops AS c7, " +
                    "company.name AS c8, vehicles.type AS c9 " +
                    "FROM routes JOIN points AS points ON routes.station_from_id = points.id JOIN points AS p ON routes.station_to_id = p.id "+
                    "JOIN localities AS localities ON points.locality_id = localities.locality_id " +
                    "JOIN company AS company ON routes.company_id = company.company_id " +
                    "JOIN vehicles AS vehicles ON routes.vehicle_id = vehicles.id " +
                    "WHERE routes.station_from_id IN (SELECT points.id FROM points WHERE points.locality_id = (SELECT locality_id FROM localities WHERE name = '" + from + "'"+
                    ")) AND station_to_id IN (SELECT id FROM points WHERE locality_id = (SELECT locality_id FROM localities WHERE name = '" + to + "'"+")) AND " +
                    "vehicles.type = '" + vehicle_type + "' " +
                    "ORDER BY ABS(ROUND((JULIANDAY('"+dtime+"') - JULIANDAY(routes.start_at)) * 86400))"
            );
            while (resultSet.next()) {
                /*routes.add(new Route(
                        resultSet.getInt("c1"),
                        resultSet.getString("c2"),
                        resultSet.getString("c11"),
                        resultSet.getString("c4"),
                        resultSet.getString("c6"),
                        resultSet.getString("c7"),
                        resultSet.getString("c8"),
                        resultSet.getString("c9")));*/
            }
            return routes;

        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }

    }

    public List<Ticket> getTicketsByRoute(int route_id) {
        try (Statement statement = this.connection.createStatement()) {
            List<Ticket> tickets = new ArrayList<Ticket>();
            ResultSet resultSet = statement.executeQuery("SELECT tickets.id as tid, tickets.route_id as trid, " +
                    "tickets.seat_id as tsid, tickets.passenger_id as tpid, " +
                    "tickets.order_datetime as tdt, tickets.status as tst, " +
                    "vehicle_seats.seat_number as vsn, vehicle_seats.carriage_number as vcn, " +
                    "vehicle_seats.carriage_type as v_ct, vehicle_seats.is_upper as viu " +
                    "FROM tickets as tickets JOIN vehicle_seats as vehicle_seats ON tickets.seat_id = vehicle_seats.id "
                    + "WHERE tickets.route_id = "+ route_id +" AND passenger_id IS NULL"
            );
            while (resultSet.next()) {
                Ticket t = new Ticket(
                        resultSet.getInt("tid"),
                        resultSet.getInt("trid"),
                        resultSet.getInt("tsid"),
                        resultSet.getString("tdt"),
                        resultSet.getString("tst"),
                        resultSet.getString("vsn"));
                t.setCarriage_number(resultSet.getInt("vcn"));
                t.setCarriage_type(resultSet.getString("v_ct"));
                t.setIs_upper(resultSet.getBoolean("viu"));
                tickets.add(t);

            }
            return tickets;

        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }

    }
    public Boolean addPassenger(Passenger product) {
        try (PreparedStatement statement = this.connection.prepareStatement(
                "INSERT INTO passengers(`first_name`, `last_name`, `middle_name`, `birth_date`, `sex`, `document_series`, `document_number`) " +
                        "VALUES(?, ?, ?, ?, ?, ?, ?)")) {
            statement.setObject(1, product.getFirst_name());
            statement.setObject(2, product.getLast_name());
            statement.setObject(3, product.getMiddle_name());
            statement.setObject(4, product.getBirth_date());
            statement.setObject(5, product.getGender());
            statement.setObject(6, product.getPassport_series());
            statement.setObject(7, product.getPassport_number());
            statement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public User findByLogin(String login) {
        try (Statement statement = this.connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users WHERE login = '" + login + "'");
            if (resultSet.next()) {
                User user = new User(
                        resultSet.getString("login"),
                        resultSet.getString("password"),
                        resultSet.getString("email"),
                        resultSet.getString("phone_number"));
                user.setId(resultSet.getInt("id"));
                return user;
            }
           else return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Boolean saveNewUser(User user) {
        try (PreparedStatement statement = this.connection.prepareStatement(
                "INSERT INTO users(`login`, `password`, `email`, `phone_number`) " +
                        "VALUES(?, ?, ?, ?)")) {
            statement.setObject(1, user.getLogin());
            statement.setObject(2, user.getPassword());
            statement.setObject(3, user.getEmail());
            statement.setObject(4, user.getPhone());
            statement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
