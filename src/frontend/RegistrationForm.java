package frontend;

import backend.ClientApplication;
import backend.ClientCommand;
import backend.User;
import backend.UserService;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class RegistrationForm extends JPanel {
    private static final long serialVersionUID = 1L;
    private JTextField login;;
    private JTextField email;
    private JTextField phone;
    private JPasswordField password;
    private JPasswordField conpassword;
    private JButton btnNewButton;
    private JLabel log;

    public String getErrorMessage(){
        String messageText = "";
        if (login.getText() == null||login.getText().equals("")){
            messageText += "Имя пользователя не может быть пустым\n";
        }
        if (email.getText() == null||email.getText().equals("")){
            messageText += "Поле 'Email' должно быть заполнено!\n";
        }
        if (!email.getText().matches("^[\\w-\\.]+@[\\w-]+(\\.[\\w-]+)*\\.[a-z]{2,}$")){
            messageText += "Введён недействительный электронный адрес!\n";
        }
        if ((!(phone.getText().equals("")))&&(!phone.getText().matches("[0-9]+"))){
            messageText += "Поле 'Номер телефона' может содержать только цифры!\n";
        }
        String x = new String(password.getPassword());
        String y = new String(conpassword.getPassword());
        if (!x.equals(y)){
            messageText += "Пароли не совпадают!\n";
        }
        return messageText;
    }

    public RegistrationForm(ClientApplication app) {
        setLocation(100, 100);
        setSize(500, 400);
        JPanel p = new JPanel(new GridLayout(6, 0, 5, 12));
        login = new JTextField(" ", 15);
        email = new JTextField(" ", 15);
        phone = new JTextField(" ", 15);
        password = new JPasswordField(" ", 15);
        conpassword = new JPasswordField(" ", 15);

        JLabel llogin = new JLabel("Имя пользователя (логин)");
        p.add(llogin, BorderLayout.WEST);
        p.add(login, BorderLayout.EAST);

        JLabel lemail = new JLabel("Email");
        p.add(lemail, BorderLayout.WEST);
        p.add(email, BorderLayout.EAST);

        JLabel lphone = new JLabel("Номер телефона");
        p.add(lphone, BorderLayout.WEST);
        p.add(phone, BorderLayout.EAST);

        JLabel lpassword = new JLabel("Пароль");
        p.add(lpassword, BorderLayout.WEST);
        p.add(password, BorderLayout.EAST);

        JLabel lconpassword = new JLabel("Повторите пароль");
        p.add(lconpassword, BorderLayout.WEST);
        p.add(conpassword, BorderLayout.EAST);

        btnNewButton = new JButton("Регистрация");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String text = "";
                text = getErrorMessage();
                if (text.equals("")) {
                    User user = new User(login.getText(), email.getText(), phone.getText(), new String(password.getPassword()));
                    UserService request = new UserService(ClientCommand.FIND_USER_BY_LOGIN);
                    request.sendUser(user);
                    UserService response = app.talkToServer(request);
                    if (response == null) {
                        UserService newUser = new UserService(ClientCommand.NEW_USER);
                        newUser.sendUser(user);
                        UserService new_response = app.talkToServer(newUser);
                        if (new_response == null) {
                            text = "Ошибка соединения: не удалось зарегистрировать нового пользователя";
                        }
                        else {
                            text = "Регистрация прошла успешно";
                        }
                    }
                    else {
                        text = "Пользователь с таким логином уже существует, выберите другой логин";
                    }
                }
                JOptionPane.showMessageDialog(null, text);
            }
        });
        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
        p.add(btnNewButton, BorderLayout.SOUTH);
        JPanel flow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        flow.add(p);
        this.add(flow, BorderLayout.NORTH);
        setVisible(true);
    }
    public void printLog(String text) {
        log.setText(text);
    }
}