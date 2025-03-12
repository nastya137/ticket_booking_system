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
    private JButton btnRegister;
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

    btnNewButton = new JButton("Вход");
        btnNewButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            String text = "";
            if (!(login.getText() == null || login.getText().trim().isEmpty())&&!(password.getText() == null || password.getText().trim().isEmpty())) {
                User user = new User(login.getText(), new String(password.getPassword()));
                UserService request = new UserService(ClientCommand.FIND_USER_BY_LOGIN);
                request.sendUser(user);
                UserService response = app.talkToServer(request);
                if ((response.recieveUser() != null)&&(( response.recieveUser().getLogin().equals(login.getText()) )&&( response.recieveUser().getPassword().equals(password.getText()) ))) {
                    app.setCurrentUser(response.recieveUser());
                    System.out.println((response.recieveUser().getLogin())+" "+response.recieveUser().getPassword());
                    JOptionPane.showMessageDialog(null, "Вход выполнен успешно!");
                    app.gui.itemStateChanged("Текущий пользователь");
                }
                else {
                    JOptionPane.showMessageDialog(null, "Пользователь не найден. Проверьте правильность ввода пароля и логина. Также Вы можете создать новую учётную запись.");
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "Введите логин и пароль!");
            }

        }
    });
        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
        p.add(btnNewButton, BorderLayout.SOUTH);
        btnRegister = new JButton("Нет учётной записи? Зарегистрироваться");
        btnRegister.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                app.gui.itemStateChanged("Регистрация");
            }
        });
        p.add(btnRegister, BorderLayout.EAST);
    JPanel flow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        flow.add(p);
        this.add(flow, BorderLayout.NORTH);
    setVisible(true);
}
}
