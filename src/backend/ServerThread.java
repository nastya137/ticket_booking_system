package backend;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ServerThread implements Runnable{
    //region THREAD VARIABLES
    private Socket socket;
    private static int connectionCount = 0;
    private int connectionNumber;
    private ObjectInputStream packetInputStream;
    private ObjectOutputStream packetOutputStream;

    public ServerThread() {}

    public ServerThread(Socket socket) throws IOException {
        this.socket = socket;

        packetOutputStream = new ObjectOutputStream(socket.getOutputStream());
        packetInputStream = new ObjectInputStream(socket.getInputStream());

        connectionCount++;
        connectionNumber = connectionCount;
        println("Connection " + connectionNumber + " Established.");
    }



    //Вывод сообщения от сервера
    private void println(String message) {
        java.lang.System.out.println("backend.Server (thread " + connectionNumber + "): " + message);
    }


    //Получение списка пассажиров, добавленных пользователем, id которого известен
    public List<Passenger> getPassengersAddedByUser(int user_id) {
            ArrayList<ArrayList<String>> databaseRaw;
            ArrayList<Passenger> passengers = new ArrayList<>();
            databaseRaw = Server.databasePull("SELECT * FROM passengers WHERE user_added_by_id = '" + user_id + "'");

            for (int i = 0; i<databaseRaw.size(); i++) {
                passengers.add(new Passenger(
                        databaseRaw.get(i).get(0),
                        databaseRaw.get(i).get(1),
                        databaseRaw.get(i).get(2),
                        databaseRaw.get(i).get(3),
                        databaseRaw.get(i).get(4),
                        databaseRaw.get(i).get(5),
                        Boolean.valueOf(databaseRaw.get(i).get(6))));
            }
            return passengers;

    }

    //Получает рейсы между населёнными пунктами
    //Предусмотреть просроченные рейсы!!!!!!!!!!
    public List<Route> getRoutesByLocations(String from, String to, String vehicle_type) {
        ArrayList<ArrayList<String>> databaseRaw;
        ArrayList<Route> routes = new ArrayList<>();
        String request = "SELECT " +
                "routes.id AS c1, (SELECT points.name FROM points WHERE points.id = routes.station_from_id) AS c2, (SELECT points.name FROM points WHERE points.id = routes.station_to_id) AS c11, " +
                "routes.start_at AS c4, routes.stop_at as c6, routes.has_stops AS c7, " +
                "company.name AS c8, vehicles.type AS c9, routes.price_adult AS c10, routes.currency_unit AS c11 " +
                "FROM routes JOIN points AS points ON routes.station_from_id = points.id JOIN points AS p ON routes.station_to_id = p.id "+
                "JOIN localities AS localities ON points.locality_id = localities.locality_id " +
                "JOIN company AS company ON routes.company_id = company.company_id " +
                "JOIN vehicles AS vehicles ON routes.vehicle_id = vehicles.id " +
                "WHERE routes.station_from_id IN (SELECT points.id FROM points WHERE points.locality_id = (SELECT locality_id FROM localities WHERE name = '" + from + "'"+
                ")) AND station_to_id IN (SELECT id FROM points WHERE locality_id = (SELECT locality_id FROM localities WHERE name = '" + to + "'"+"))";
        if (vehicle_type!=null) {
            request += " AND vehicles.type = '" + vehicle_type + "'";
        }
        databaseRaw = Server.databasePull(request);
        System.out.println("Запрос выполнен");
        for (int i = 0; i<databaseRaw.size(); i++) {
            Route route = new Route(
                    Integer.parseInt(databaseRaw.get(i).get(0)),
                    databaseRaw.get(i).get(1),
                    databaseRaw.get(i).get(2),
                    databaseRaw.get(i).get(3),
                    databaseRaw.get(i).get(4),
                    (databaseRaw.get(i).get(5)),        //Boolean.parseBoolean(databaseRaw.get(i).get(5)),
                    databaseRaw.get(i).get(6),
                    databaseRaw.get(i).get(7));
                route.setPrice_adult(Double.parseDouble(databaseRaw.get(i).get(8)));
                route.setCurrencyUnit(databaseRaw.get(i).get(9));
                routes.add(route);
            }
            return routes;


    }

    //Получает список рейсов по пунктам назначения и ближайших по времени отправления
    //Предусмотреть просроченные рейсы!!!!!!!!!!
    public List<Route> getRoutesByTime(String from, String to, String dtime, String vehicle_type) {
        ArrayList<ArrayList<String>> databaseRaw;
        ArrayList<Route> routes = new ArrayList<>();
        String request = "SELECT " +
                "routes.id AS c1, (SELECT points.name FROM points WHERE points.id = routes.station_from_id) AS c2, (SELECT points.name FROM points WHERE points.id = routes.station_to_id) AS c11, " +
                "routes.start_at AS c4, routes.stop_at as c5, routes.has_stops AS c6, " +
                "company.name AS c7, vehicles.type AS c8, routes.price_adult AS c9, routes.currency_unit AS c10 " +
                "FROM routes JOIN points AS points ON routes.station_from_id = points.id JOIN points AS p ON routes.station_to_id = p.id "+
                "JOIN localities AS localities ON points.locality_id = localities.locality_id " +
                "JOIN company AS company ON routes.company_id = company.company_id " +
                "JOIN vehicles AS vehicles ON routes.vehicle_id = vehicles.id " +
                "WHERE routes.station_from_id IN (SELECT points.id FROM points WHERE points.locality_id = (SELECT locality_id FROM localities WHERE name = '" + from + "'"+
                ")) AND station_to_id IN (SELECT id FROM points WHERE locality_id = (SELECT locality_id FROM localities WHERE name = '" + to + "'"+"))";
        if (vehicle_type!=null) {
            request += " AND vehicles.type = '" + vehicle_type + "'";
        }
        request+=" ORDER BY ABS(ROUND((JULIANDAY('"+dtime+"') - JULIANDAY(routes.start_at)) * 86400))";
        databaseRaw = Server.databasePull(request);
        System.out.println("Запрос выполнен");
        for (int i = 0; i<databaseRaw.size(); i++) {
            Route route = new Route(
                        Integer.parseInt(databaseRaw.get(i).get(0)),
                        databaseRaw.get(i).get(1),
                        databaseRaw.get(i).get(2),
                        databaseRaw.get(i).get(3),
                        databaseRaw.get(i).get(4),
                        databaseRaw.get(i).get(5),
                        databaseRaw.get(i).get(6),
                        databaseRaw.get(i).get(7));
            route.setPrice_adult(Double.parseDouble(databaseRaw.get(i).get(8)));
            route.setCurrencyUnit(databaseRaw.get(i).get(9));
            //if (route.hasStops()){
                ArrayList<RouteStop> stops = new ArrayList<>();
                ArrayList<ArrayList<String>> stops_data;
                stops_data = Server.databasePull("SELECT points.name, points.address, route_stops.start_at, route_stops.duration  " +
                        "FROM points JOIN route_stops ON points.id = route_stops.point_id WHERE route_stops.route_id = "+Integer.toString(route.getId()));
                for (int j = 0; j<stops_data.size(); j++){
                    stops.add(new RouteStop(
                            stops_data.get(j).get(0),
                            stops_data.get(j).get(1),
                            stops_data.get(j).get(2),
                            stops_data.get(j).get(3)
                    ));
                }
                route.setRoute_stops(stops);
            //}
            routes.add(route);
            }
            return routes;
    }

    public Route getRouteInfo(Route route) throws IOException {
        Route r = route;
        ArrayList<ArrayList<String>> databaseRaw;
        databaseRaw = Server.databasePull("SELECT vehicles.number, vehicles.model, vehicles.brand, routes.price_child, (SELECT points.address from points where points.id = routes.station_from_id), " +
                "(SELECT points.address from points where points.id = routes.station_to_id), company.contact, (SELECT points.contact from points where points.id = routes.station_from_id), " +
                "(SELECT points.contact from points where points.id = routes.station_to_id) " +
                "FROM routes " +
                "Join points ON points.id = routes.station_from_id " +
                "Join points as p ON p.id = routes.station_to_id " +
                "JOIN company ON company.company_id = routes.company_id " +
                "JOIN vehicles ON vehicles.id = routes.vehicle_id " +
                "where routes.id = "+route.getId());
        System.out.println(route.getId()+" Получены данные рейса");
        for (ArrayList<String> strings : databaseRaw) {
            for (String string : strings) {
                System.out.println(string);
            }
        }
        route.setVehicle_number(databaseRaw.get(0).get(0));
        route.setVehicle_model(databaseRaw.get(0).get(1)==null?"":databaseRaw.get(0).get(1));
        route.setVehicle_brand(databaseRaw.get(0).get(2)==null?"":databaseRaw.get(0).get(2));
        route.setPrice_child(Double.parseDouble(databaseRaw.getFirst().get(3)));
        route.setRoute_from_address(databaseRaw.getFirst().get(4));
        route.setRoute_to_address(databaseRaw.getFirst().get(5));
        route.setCompany_contact(databaseRaw.get(0).get(6));
        route.setStation_from_contact(databaseRaw.get(0).get(7));
        route.setStation_to_contact(databaseRaw.get(0).get(8));
        return route;
    }

    // SELECT vehicles.number, vehicles.model, vehicles.brand, routes.price_child, (SELECT points.address from points where points.id = routes.station_from_id),
    //(SELECT points.address from points where points.id = routes.station_to_id), company.contact, (SELECT points.contact from points where points.id = routes.station_from_id),
    //(SELECT points.contact from points where points.id = routes.station_to_id)
    //FROM routes
    //Join points ON points.id = routes.station_from_id
    //Join points as p ON p.id = routes.station_to_id
    //JOIN company ON company.company_id = routes.company_id
    //JOIN vehicles ON vehicles.id = routes.vehicle_id


    //Получает свободные билеты на рейс
    public List<Ticket> getTicketsByRoute(int route_id) {
        ArrayList<ArrayList<String>> databaseRaw;
        ArrayList<Ticket> tickets = new ArrayList<>();
        databaseRaw = Server.databasePull("SELECT tickets.id as tid, tickets.route_id as trid, " +
                    "tickets.seat_id as tsid, tickets.passenger_id as tpid, " +
                    "tickets.order_datetime as tdt, tickets.status as tst, " +
                    "vehicle_seats.seat_number as vsn, vehicle_seats.carriage_number as vcn, " +
                    "vehicle_seats.carriage_type as v_ct, vehicle_seats.is_upper as viu " +
                    "FROM tickets as tickets JOIN vehicle_seats as vehicle_seats ON tickets.seat_id = vehicle_seats.id "
                    + "WHERE tickets.route_id = "+ route_id +" AND passenger_id IS NULL"
            );
        for (int i = 0; i<databaseRaw.size(); i++) {
                Ticket t = new Ticket(
                        Integer.parseInt(databaseRaw.get(i).get(0)),
                        Integer.parseInt(databaseRaw.get(i).get(1)),
                        Integer.parseInt(databaseRaw.get(i).get(2)),
                        databaseRaw.get(i).get(3),
                        databaseRaw.get(i).get(4),
                        databaseRaw.get(i).get(5));
                t.setCarriage_number(Integer.parseInt(databaseRaw.get(i).get(6)));
                t.setCarriage_type(databaseRaw.get(i).get(7));
                t.setIs_upper(Boolean.parseBoolean(databaseRaw.get(i).get(8)));
                tickets.add(t);

            }
            return tickets;

    }

    //Добавляет нового пассажира
    //Также реализовать с кодом user_id!!!
    public void addPassenger(Passenger passenger) {
        Server.databasePush("INSERT INTO passengers(`first_name`, `last_name`, `middle_name`, `birth_date`, `sex`, `document_series`, `document_number`) " +
                "VALUES("+passenger.getFirst_name()+", "+passenger.getLast_name()+", "+passenger.getMiddle_name()+", "+passenger.getBirth_date()+", "+(passenger.getGender() ? "1" : "0")+", "+passenger.getPassport_series()+", "+passenger.getPassport_number()+")");
    }

    //проверяет, есть ли пользователь с таким логином
    public User findByLogin(String login) {
        ArrayList<ArrayList<String>> databaseRaw;
        databaseRaw = Server.databasePull("SELECT * FROM users WHERE login = '" + login + "'"
        );

            if (databaseRaw.isEmpty()) {
                User user = new User(
                        databaseRaw.get(0).get(1),
                        databaseRaw.get(0).get(2),
                        databaseRaw.get(0).get(3),
                        databaseRaw.get(0).get(4));
                user.setId(Integer.parseInt(databaseRaw.get(0).get(0)));
                return user;
            }
            else return null;
    }

    //Сохраняет нового пользователя
    public void saveNewUser(User user) {
        ArrayList<ArrayList<String>> databaseRaw;
        databaseRaw = Server.databasePull("INSERT INTO users(`login`, `password`, `email`, `phone_number`) " +
                        "VALUES("+user.getLogin()+", "+user.getPassword()+", "+user.getEmail()+", "+user.getPhone()+")");
    }


    //Запуск процесса
    @Override
    public void run() {
        try {
            println("Waiting For Client...");
            UserService message;
            while ((message = (UserService) packetInputStream.readObject()) != null) {
                println("Received " + message + " From Client.");
                UserService response = new UserService();

                if (message.Command() == ClientCommand.GET_PASSENGERS_ADDED_BY_USER) {
                    response.sendPassengersList(getPassengersAddedByUser(message.recieveId()));
                }
                else if (message.Command() == ClientCommand.GET_ROUTES_BY_LOCATIONS) {
                    if (message.recieveTime() != null) {
                        response.sendRouteList(getRoutesByTime(message.recieveFrom(), message.recieveTo(), message.recieveTime(), message.recieveType()));
                    }
                    else {
                        response.sendRouteList(getRoutesByLocations(message.recieveFrom(), message.recieveTo(), message.recieveType()));
                    }
                }else if (message.Command() == ClientCommand.GET_PASSENGERS_ADDED_BY_USER) {
                    response.sendRoute(getRouteInfo(message.recieveRoute()));//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                }
                else if (message.Command() == ClientCommand.GET_TICKETS) {
                response.sendTicketList(getTicketsByRoute(message.recieveRouteId()));
                }
                else if (message.Command() == ClientCommand.ADD_PASSENGER) {
                response.sendPassenger(message.recievePassenger());
                }
                else if (message.Command() == ClientCommand.FIND_USER_BY_LOGIN) {
                response.sendUser(message.recieveUser());
                }
                else if (message.Command() == ClientCommand.NEW_USER) {
                response.sendUser(message.recieveUser());
                }
                else if (message.Command() == ClientCommand.GET_ROUTE_INFO){
                    response.sendRoute(getRouteInfo(message.recieveRoute()));
                }
                println("Sent " + response + " To Client.");
                packetOutputStream.writeObject(response);
            }
        } catch (IOException e) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, e);
            println("Halted Thread.");
        } catch (ClassNotFoundException e) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, e);
            java.lang.System.out.println("ERROR: Serialized Object Class Can Not be Found!!!");
        } finally {
            try {
                println("Lost Connection to Client.");
                socket.close();
            } catch (IOException e) {
                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }
    //endregion
}
