package frontend;

import backend.ClientApplication;
import backend.Route;

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Window extends JFrame {
    private JPanel options;
    private JPanel screen;
    private SearchRoutes searchRoutes;
    private RegistrationForm registrationForm;
    private LoginForm loginForm;
    private java.util.List<RouteInfo> info;
    private CardLayout card;
    private CurrentUser currentUser;
    public Window(ClientApplication app) {
        JButton routes = new JButton("Поиск рейсов");
        JButton story = new JButton("История заказов");
        JButton user = new JButton("Настройки");
        JPanel options = new JPanel();
        options.setLayout(new BoxLayout(options, BoxLayout.Y_AXIS));
        options.add(routes);
        options.add(story);
        options.add(user);
        info = new ArrayList<RouteInfo>();
        card = new CardLayout();
        screen = new JPanel(card);
        searchRoutes = new SearchRoutes(app);
        registrationForm = new RegistrationForm(app);
        loginForm = new LoginForm(app);

        currentUser = new CurrentUser(app);
        screen.add(searchRoutes, "Поиск рейсов");
        screen.add(registrationForm, "Регистрация");
        screen.add(loginForm, "Вход в систему");
        screen.add(currentUser, "Текущий пользователь");
        routes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                card.show(screen, "Поиск рейсов");
            }
        });
        user.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (app.getCurrentUser()!=null)
                    card.show(screen, "Текущий пользователь");
                else card.show(screen, "Вход в систему");
            }
        });
        story.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (app.getCurrentUser()!=null){}
                   // card.show(screen, "Текущий пользователь");
                else card.show(screen, "Вход в систему");
            }
        });
        this.add(options);
        this.add(screen);
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, options, screen);
        this.add(split);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void itemStateChanged(String item)
    {
        CardLayout layout = (CardLayout)(screen.getLayout());
        layout.show(screen, item);
    }
    public void setRoute(Route route, ClientApplication app) {
        RouteInfo routeInfo = new RouteInfo(app, route);
        info.add(routeInfo);
        screen.add(routeInfo, ("Информация о рейсе "+route.getId()));
        card.show(screen, "Информация о рейсе "+route.getId());
    }
}
