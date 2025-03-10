package frontend;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

public class PassengerPanel extends JPanel {
    private JTextField passengerFirstName;
    private JTextField passengerLastName;
    private JTextField passengerMiddleName;
    private JFormattedTextField passengerBirthDate;
    private JComboBox passengerGender;
    private JTextField PassportSerialNumber;
    private JTextField PassportNumber;
    public PassengerPanel(RouteInfo routeInfo) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JButton deletePassengerButton = new JButton("-");
        deletePassengerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                routeInfo.deletePassengerPanel(PassengerPanel.this);
            }
        });
        JPanel buttonPanel = new JPanel();
        JLabel requiredFields = new JLabel("Поля, отмеченные '*', обязательны для заполнения");
        buttonPanel.add(requiredFields, BorderLayout.WEST);
        buttonPanel.add(deletePassengerButton, BorderLayout.EAST);
        this.add(buttonPanel);
        JPanel passengerPanel = new JPanel();
        passengerPanel.setLayout(new GridLayout(4,4));
        JLabel passengerFirstNameLabel = new JLabel("Имя");
        JLabel passengerLastNameLabel = new JLabel("Фамилия");
        JLabel passengerMiddleNameLabel = new JLabel("Отчество");
        JLabel passengerBirthDateLabel = new JLabel("Дата рождения");
        passengerPanel.add(passengerFirstNameLabel);
        passengerPanel.add(passengerLastNameLabel);
        passengerPanel.add(passengerMiddleNameLabel);
        passengerPanel.add(passengerBirthDateLabel);
        passengerFirstName = new JTextField();
        passengerLastName = new JTextField();
        passengerMiddleName = new JTextField();
        passengerBirthDate = new JFormattedTextField();
        try {
            MaskFormatter dateFormatter = new MaskFormatter("##.##.####");
            dateFormatter.setPlaceholderCharacter('_');
            passengerBirthDate = new JFormattedTextField(dateFormatter);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        passengerPanel.add(passengerFirstName);
        passengerPanel.add(passengerLastName);
        passengerPanel.add(passengerMiddleName);
        passengerPanel.add(passengerBirthDate);
        JLabel passengerGenderLabel = new JLabel("Пол");
        JLabel passengerSerialNumberLabel = new JLabel("Серия паспорта");
        JLabel PassportNumberLabel = new JLabel("Номер паспорта");
        passengerPanel.add(passengerGenderLabel);
        passengerPanel.add(passengerSerialNumberLabel);
        passengerPanel.add(PassportNumberLabel);
        passengerPanel.add(new JLabel(""));
        String[] types = {"", "Мужской", "Женский"};
        passengerGender = new JComboBox(types);
        PassportSerialNumber = new JTextField();
        PassportNumber = new JTextField();
        passengerPanel.add(passengerGender);
        passengerPanel.add(PassportSerialNumber);
        passengerPanel.add(PassportNumber);
        this.add(passengerPanel);
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.setSize(800, 160);
    }

}
