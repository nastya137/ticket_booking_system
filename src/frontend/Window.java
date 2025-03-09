package frontend;

import backend.ClientApplication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Window extends JFrame {
    private JPanel options;
    private JPanel screen;
    private SearchRoutes searchRoutes;
    private RegistrationForm registrationForm;
    private LoginForm loginForm;
    public Window(ClientApplication app) {
        JButton routes = new JButton("Поиск рейсов");
        JButton story = new JButton("История заказов");
        JButton user = new JButton("Настройки");
        JPanel options = new JPanel();
        options.setLayout(new BoxLayout(options, BoxLayout.Y_AXIS));
        options.add(routes);
        options.add(story);
        options.add(user);
        CardLayout card = new CardLayout();
        screen = new JPanel(card);
        searchRoutes = new SearchRoutes(app);
        registrationForm = new RegistrationForm(app);
        loginForm = new LoginForm(app);
        screen.add(searchRoutes, "Поиск рейсов");
        screen.add(registrationForm, "Регистрация");
        screen.add(loginForm, "Вход в систему");
        routes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                card.show(screen, "Поиск рейсов");
            }
        });
        user.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                card.show(screen, "Вход в систему");
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

    public void itemStateChanged(ItemEvent event)
    {
        CardLayout layout = (CardLayout)(screen.getLayout());
        layout.show(screen, (String)event.getItem());
    }
}
