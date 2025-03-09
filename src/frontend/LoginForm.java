package frontend;

import backend.ClientApplication;
import backend.ClientCommand;
import backend.User;
import backend.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginForm extends JPanel {
    private JTextField login;;
    private JPasswordField password;
    private JButton btnNewButton;
    public LoginForm(ClientApplication app) {
    setLocation(100, 100);
    setSize(500, 400);
    JPanel p = new JPanel(new GridLayout(6, 0, 5, 12));
    login = new JTextField(" ", 15);
    password = new JPasswordField(" ", 15);

    JLabel llogin = new JLabel("Имя пользователя (логин)");
        p.add(llogin, BorderLayout.WEST);
        p.add(login, BorderLayout.EAST);

    JLabel lpassword = new JLabel("Пароль");
        p.add(lpassword, BorderLayout.WEST);
        p.add(password, BorderLayout.EAST);

    btnNewButton = new JButton("Регистрация");
        btnNewButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            System.out.println("ok");
        }
    });
        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
        p.add(btnNewButton, BorderLayout.SOUTH);
    JPanel flow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        flow.add(p);
        this.add(flow, BorderLayout.NORTH);
    setVisible(true);
}
}
