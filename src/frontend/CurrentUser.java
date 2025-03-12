package frontend;

import backend.ClientApplication;
import backend.User;

import javax.swing.*;

public class CurrentUser extends JPanel {
    private JButton edit;
    private JButton logout;
    private User user;
    public CurrentUser(ClientApplication app) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.user = app.getCurrentUser();
        JLabel login = new JLabel(user!=null?user.getLogin():"");
        JLabel email = new JLabel(user!=null?user.getEmail():"");
        JLabel phone = new JLabel(user!=null?user.getPhone():"");
        this.add(login);
        this.add(email);
        this.add(phone);
        edit = new JButton("Редактировать данные");
        logout = new JButton("Выход из учётной записи");
        logout.addActionListener(e -> {
            user = null;
            app.setCurrentUser(null);
            app.gui.itemStateChanged("Вход в систему");
        });
        this.add(edit);
        this.add(logout);
    }
}
