import backend.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderLabel extends JPanel {
    private JLabel label;
    private JButton cancelButton;
    private Order order;
    public OrderLabel(Order order, Route route, ClientApplication app) throws ParseException {
        super(new GridLayout(3, 2));
        this.order = order;
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
            JButton cancel = new JButton("Отменить");
            cancel.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    UserService request = new UserService(ClientCommand.CANCEL_ORDER);
                    request.sendOrder(order);
                    UserService response = app.talkToServer(request);


                }
            });
            this.add(cancel);
        }
        this.setSize(800, 80);
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }
    }

