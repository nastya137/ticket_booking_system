package frontend;

import backend.Route;

import javax.swing.*;
import java.awt.*;


public class RouteInfo extends JPanel {
    public RouteInfo(Route route) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JPanel routeInfo = new JPanel();
        JLabel routeInfoLabel = new JLabel(route.getStarts_at()+"\n"+route.getRoute_from_name()+", "+route.getStationFromName()+"\n"+
                route.getEnds_at()+"\n"+route.getRoute_to_name()+", "+route.getStationToName()+"\n"+"Цена билета: "+route.getPrice_adult());
        routeInfo.add(routeInfoLabel);
        this.add(routeInfo);
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }
}
