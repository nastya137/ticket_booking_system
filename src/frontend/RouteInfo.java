package frontend;

import backend.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class RouteInfo extends JPanel {
    private java.util.List<Passenger> passengerList;
    private JPanel passengers;
    private double price;
    public RouteInfo(ClientApplication app) {
        price = 0.0;
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    }
    public void setRoute(Route route) {
        this.removeAll();
        passengerList = new ArrayList<>();
        //Основная информация о рейсе
        JPanel routeInfo = new JPanel();
        routeInfo.setLayout(new BoxLayout(routeInfo, BoxLayout.Y_AXIS));
        JLabel routeFromLabel = new JLabel("Отправление: "+route.getStarts_at()+", "+route.getStationFromName()+" "+route.getRoute_from_address());
        routeInfo.add(routeFromLabel);
        JLabel routeToLabel = new JLabel("Прибытие: "+route.getEnds_at()+", "+route.getStationToName()+" "+route.getRoute_to_address());
        routeInfo.add(routeToLabel);
        JLabel priceLabel = new JLabel("Цена билета: "+route.getPrice_adult()+" "+route.getCurrencyUnit()+" (взрослый)");
        routeInfo.add(priceLabel);
        JLabel vehicleLabel = new JLabel(route.getVehicleType()+" "+(route.getVehicle_model()!=null?route.getVehicle_model():"")+" "+(route.getVehicle_brand()!=null?route.getVehicle_brand():"")+" "+route.getVehicle_number());
        routeInfo.add(vehicleLabel);
        JLabel contactsLabel = new JLabel("<html>Контакты: <br>Компания-перевозчик: "+route.getCompany_name()+" "+route.getCompany_contact()+
                "<br> Станция отправления: "+route.getStation_to_contact()+"<br> Станция прибытия: "+route.getStation_from_contact()+"</html>");

        routeInfo.add(contactsLabel);
        String stops ="<html>Остановки: ";
        if (route.getRoute_stops()!=null) {
            for (RouteStop stop : route.getRoute_stops()) {
                stops+="<br>"+stop.toString();
            }
        }
        else {
            stops+="нет";
        }
        stops+="</html>";
        JLabel stopLabel = new JLabel(stops);
        routeInfo.add(stopLabel);


        //Покупка билетов
        JPanel buy = new JPanel();
        JPanel upper = new JPanel();
        JLabel price = new JLabel("Цена: 0 "+ route.getCurrencyUnit());
        JButton addPassenger = new JButton("+");
        addPassenger.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Добавить пассажира
                PassengerPanel p = new PassengerPanel(RouteInfo.this);
                p.setVisible(true);
                p.setMaximumSize(p.getSize());
                p.setMinimumSize(p.getSize());
                passengers.add(p);
                passengers.revalidate();
            }
        });
        upper.add(price, BorderLayout.WEST);
        upper.add(addPassenger, BorderLayout.EAST);

        buy.setLayout(new BoxLayout(buy, BoxLayout.Y_AXIS));
        JLabel email = new JLabel("Email: ");
        buy.add(upper);
        buy.add(email, BorderLayout.WEST);
        JTextField emailField = new JTextField();
        buy.add(emailField);
        JLabel phone = new JLabel("Номер телефона: ");
        buy.add(phone);
        JTextField phoneField = new JTextField();
        buy.add(phoneField);
        JPanel buttons = new JPanel();
        JButton reserveButton = new JButton("Бронировать");
        reserveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //бронь

            }
        });
        JButton buyButton = new JButton("Купить");
        buyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //покупка
            }
        });
        buttons.add(reserveButton);
        buttons.add(buyButton);
        buy.add(buttons);

        //Область для добавления пассажиров
        passengers = new JPanel();
        passengers.setLayout(new BoxLayout(passengers, BoxLayout.Y_AXIS));
        final JScrollPane scrollPane = new JScrollPane(passengers, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getViewport().add(passengers);

        passengers.setSize(800, 350);
        JSplitPane sp1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JScrollPane(passengers), buy);
        JSplitPane sp0 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, routeInfo, sp1);
        sp1.setResizeWeight(0.9999);
        this.add(sp0);
        routeInfo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    public void deletePassengerPanel (PassengerPanel p) {
        passengers.remove(p);
        passengers.revalidate();
    }
}
