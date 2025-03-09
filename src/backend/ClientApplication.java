package backend;

import frontend.RegistrationForm;
import frontend.SearchRoutes;
import frontend.Window;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class ClientApplication implements Serializable {
    private Socket socket;
    private ObjectOutputStream packetOutputStream;
    private ObjectInputStream packetInputStream;
    private User currentUser;
    private Window gui;
    //Конструктор приложения
    private ClientApplication() {}

    //Подключение к серверу
    private boolean connectServer() {
        disconnectServer();
        System.out.println("Connecting to server...");
        try {
            socket = new Socket("127.0.0.1", 2000);//локальный сервер
            packetOutputStream = new ObjectOutputStream(socket.getOutputStream());
            packetInputStream = new ObjectInputStream(socket.getInputStream());
            ///guiWindow.printLog(MessageType.STATUS,"backend.Server Connected");
            System.out.println("Successfully connected to server");
            return true;
        } catch (IOException ex) {
            Logger.getLogger(ClientApplication.class.getName()).log(Level.SEVERE, null, ex);
            ///guiWindow.printLog(MessageType.ERROR,ex.toString());
            System.out.println("Failed to connect to server "+ex.toString());
            return false;
        }
    }


    //Отсоединение от сервера
    public void disconnectServer() {
        if (socket != null) {
            System.out.println("Disconnecting from server...");
            try {
                socket.close();
            } catch (IOException ex) {
                Logger.getLogger(ClientApplication.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                socket = null;
            }
        }
    }


    //Отправка и получение данных сервера
    public UserService talkToServer(UserService container) {
        UserService recievedContainer = null;
        //Отправка данных
        System.out.println("Sending to server...");
        try {
            packetOutputStream.writeObject(container);
        } catch (IOException e) {
            Logger.getLogger(ClientApplication.class.getName()).log(Level.SEVERE, null, e);
        }
        //Получение данных
        System.out.println("Waiting for server...");
        try {
            recievedContainer = (UserService) packetInputStream.readObject();
            System.out.println("Received Response");
        } catch (IOException e) {
            System.out.println("(IOException) " + e);
            Logger.getLogger(ClientApplication.class.getName()).log(Level.SEVERE, null, e);
        } catch (ClassNotFoundException e) {
            System.out.println("(ClassNotFoundException) " + e);
            Logger.getLogger(ClientApplication.class.getName()).log(Level.SEVERE, null, e);
        }
        return recievedContainer;
    }

    //установить текущего пользователя
    public void setCurrentUser(User currentUser) {
        if (currentUser != null&&this.currentUser==null) {
            this.currentUser = currentUser;
        }
    }


    //Запуск приложения
    @SuppressWarnings("CStyleArrayDeclaration")
    public static void main(String args[]) {
        ClientApplication clientApplication = new ClientApplication();

        clientApplication.gui = new Window(clientApplication);
        clientApplication.gui.setDefaultCloseOperation( EXIT_ON_CLOSE );

        while (!clientApplication.connectServer()) {
            System.out.println("Connection Failed, Retrying in 3...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Connection Failed, Retrying in 2...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Connection Failed, Retrying in 1...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Connected");

    }
}
