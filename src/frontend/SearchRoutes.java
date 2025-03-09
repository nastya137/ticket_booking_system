package frontend;

import backend.*;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;


class RouteLabel extends JPanel {//Содержит начальную информацию о рейсе
    public RouteLabel(Route route) throws ParseException {
        super(new GridLayout(3, 2));
        JLabel time_start = new JLabel(route.getStarts_at());
        this.add(time_start);
        JLabel time_end = new JLabel(route.getEnds_at());
        this.add(time_end);
        JLabel station_from = new JLabel(route.getStationFromName());
        this.add(station_from);
        JLabel station_to = new JLabel(route.getStationToName());
        this.add(station_to);
        JLabel price = new JLabel(route.getPrice_adult()+" "+route.getCurrencyUnit());
        this.add(price);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = format.parse(route.getStarts_at());
        System.out.println(date);
        if (date.compareTo(new Date()) > 0) {
        JButton buy = new JButton("Купить билет");
        buy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("buy");
            }
        });
        this.add(buy);
        }
        this.setSize(800, 80);
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }
}



public class SearchRoutes extends JPanel {
    private JTextField city_from;
    private JTextField city_to;
    private JFormattedTextField from_date;
    private JFormattedTextField from_time;
    private JComboBox type;
    private java.util.List<Route> routes;

    public SearchRoutes(ClientApplication app) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JPanel upperPanel = new JPanel(new GridLayout(2, 6));
        final JPanel RoutePanel = new JPanel();
        routes = new ArrayList<>();
        JLabel fromLabel = new JLabel("Откуда:");
        upperPanel.add(fromLabel);
        JLabel toLabel = new JLabel("Куда");
        upperPanel.add(toLabel);
        JLabel date = new JLabel("Дата отправления");
        upperPanel.add(date);
        JLabel time = new JLabel("Время отправления");
        upperPanel.add(time);
        JLabel typeLabel = new JLabel("Тип транспорта");
        upperPanel.add(typeLabel);
        JButton search = new JButton("Найти рейсы");
        upperPanel.add(search, BorderLayout.NORTH);
        city_from = new JTextField();
        upperPanel.add(city_from);
        city_to = new JTextField();
        upperPanel.add(city_to);
        //Поля даты и времени - отформатированные
        from_date = new JFormattedTextField();
        try {
            MaskFormatter dateFormatter = new MaskFormatter("##.##.####");
            dateFormatter.setPlaceholderCharacter('_');
            from_date = new JFormattedTextField(dateFormatter);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        upperPanel.add(from_date);
        from_time = new JFormattedTextField();
        try {
            MaskFormatter dateFormatter = new MaskFormatter("##:##:##");
            dateFormatter.setPlaceholderCharacter('_');
            from_time = new JFormattedTextField(dateFormatter);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        upperPanel.add(from_time);
        String[] types = {"Автобус", "Самолёт", "Поезд", "Микс"};
        type = new JComboBox(types);
        upperPanel.add(type, BorderLayout.SOUTH);
        final JScrollPane scrollPane = new JScrollPane(RoutePanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        RoutePanel.setLayout(new BoxLayout(RoutePanel, BoxLayout.Y_AXIS));
        RoutePanel.setSize(800, 350);
        scrollPane.getViewport().add(RoutePanel);

        search.addActionListener(e -> {
            if (!routes.isEmpty()) {
                routes.clear();
            }
            String timeStr = from_date.getText().substring(6)+"-"+from_date.getText().substring(3,5)+"-"+from_date.getText().substring(0,2);
            UserService request = new UserService(ClientCommand.GET_ROUTES_BY_LOCATIONS);
            request.sendFrom(city_from.getText());
            request.sendTo(city_to.getText());
            String t = (String)type.getSelectedItem();
            switch(t){
                case "Автобус":
                    request.sendType("B");
                    break;
                case "Самолёт":
                    request.sendType("A");
                    break;
                case "Поезд":
                    request.sendType("T");
                    break;
                default:
                    request.sendType(null);
                    break;
            }
            if ((from_date != null)&&(from_time!=null)&&(from_date.getText() != "__.__.____")&&(from_time.getText() != "__:__:__")) {
                request.sendTime(timeStr+" "+from_time.getText());

            }
            System.out.println(request.recieveType());
            UserService response = app.talkToServer(request);
            routes = response.recieveRouteList();
            RoutePanel.removeAll();
            if (!routes.isEmpty()) {
                for (Route route : routes) {
                    RouteLabel routeLabel = null;
                    try {
                        routeLabel = new RouteLabel(route);
                        System.out.println(route.toString());
                    } catch (ParseException ex) {
                        throw new RuntimeException(ex);
                    }
                    routeLabel.setMaximumSize(routeLabel.getSize());
                    routeLabel.setMinimumSize(routeLabel.getSize());
                    RoutePanel.add(routeLabel);
                }
            }
            else {
                JLabel no_routes = new JLabel("Рейсы в выбранном направлении не найдены");
                RoutePanel.add(no_routes);
            }
            RoutePanel.revalidate();
        });

        this.add(upperPanel, BorderLayout.NORTH);
        this.add(RoutePanel, BorderLayout.SOUTH);
        JSplitPane sl = new JSplitPane(SwingConstants.HORIZONTAL, upperPanel, new JScrollPane(RoutePanel));
        this.add(sl);
    }

}
