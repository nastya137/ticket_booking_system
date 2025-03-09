package backend;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Server {

    private static List<Integer> reservedRouteIDs;

    //Устанавливает подключение к БД
    private static Connection accessDatabase() throws SQLException {
        Connection connection = DriverManager.getConnection(("jdbc:sqlite:C:/Users/User/Desktop/transport.db"), "", "");
        System.out.println("Доступ к БД transport.db...");
        return connection;
    }

    //Извлекает данные из БД
    public synchronized static ArrayList<ArrayList<String>> databasePull(String sqlCommand) {
        ArrayList<ArrayList<String>> fetchedData = new ArrayList<ArrayList<String>>();
        long logTime = java.lang.System.currentTimeMillis();
        int logItems = 0;

        try (Connection conn = accessDatabase();
             PreparedStatement prep = conn.prepareStatement(sqlCommand)) {
            ResultSet dataLine = prep.executeQuery();
            int columns = dataLine.getMetaData().getColumnCount()+1;
            while (dataLine.next()) {
                ArrayList<String> row = new ArrayList<>();
                for (int column=1;column<columns;column++) {
                    row.add(dataLine.getString(column));
                }
                fetchedData.add(row);
                logItems++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(System.class.getName()).log(Level.SEVERE, null, ex);
        }

        java.lang.System.out.println("Done. Fetched "+logItems+" items in "+(java.lang.System.currentTimeMillis()-logTime)+"ms!");
        return fetchedData;
    }

    //Ввод новых данных в БД
    public synchronized static void databasePush(String sqlCommand) {
        try (Connection conn = accessDatabase(); // auto close the connection object after try
             PreparedStatement prep = conn.prepareStatement(sqlCommand)) {
            prep.execute();
        } catch (SQLException ex) {
            Logger.getLogger(System.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Запуск сервера, создание потоков
    @SuppressWarnings("InfiniteLoopStatement")
    private static void runServer() {
        java.lang.System.out.println("backend.Server: Launched System.");
        reservedRouteIDs = new ArrayList<>();

        try (ServerSocket serverSocket = new ServerSocket(2000)) {
            //noinspection InfiniteLoopStatement
            while (true) {
                java.lang.System.out.println("backend.Server: Ready for Clients.");
                try {
                    Socket socket = serverSocket.accept();
                    ServerThread clientThread = new ServerThread(socket);
                    Thread connectionThread = new Thread(clientThread);
                    connectionThread.start();
                } catch (IOException ex) {
                    java.lang.System.out.println("ERROR: backend.Server Failed to Connect to a Client!!!");
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(System.class.getName()).log(Level.SEVERE, null, ex);
            java.lang.System.out.println("backend.Server: Halted System.");
        }
    }


    public synchronized static List<Integer> returnReservedRouteIDs() {return reservedRouteIDs;}

    public synchronized static void reserveRouteID(int id) {reservedRouteIDs.add(id);}



    public static void main(@SuppressWarnings("CStyleArrayDeclaration") String args[]) {
        runServer();
    }
}
