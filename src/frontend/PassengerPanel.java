package frontend;

import backend.ClientCommand;
import backend.Passenger;
import backend.Ticket;
import backend.UserService;

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
    private JComboBox ticket;
    private java.util.List<Ticket> ticketList;
    private String[] places;
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
        JLabel placeLabel = new JLabel("Место");
        passengerPanel.add(passengerGenderLabel);
        passengerPanel.add(passengerSerialNumberLabel);
        passengerPanel.add(PassportNumberLabel);
        passengerPanel.add(placeLabel);
        String[] types = {"", "Мужской", "Женский"};
        places = routeInfo.getTickets();
        passengerGender = new JComboBox(types);
        PassportSerialNumber = new JTextField();
        PassportNumber = new JTextField();
        ticket = new JComboBox(routeInfo.getTickets());
        ticket.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                UserService userService = new UserService(ClientCommand.SELECT_TICKET);

            }
        });

        passengerPanel.add(passengerGender);
        passengerPanel.add(PassportSerialNumber);
        passengerPanel.add(PassportNumber);
        passengerPanel.add(ticket);
        this.add(passengerPanel);
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.setSize(800, 160);
    }

    //Проверка заполнения полей
    private boolean isNotEmpty(String str){
        return !(str == null || str.trim().isEmpty());
    }
    public boolean fieldsNotInitial(){
        if(isNotEmpty(passengerFirstName.getText())&&isNotEmpty(passengerLastName.getText())&&isNotEmpty(passengerBirthDate.getText())&&isNotEmpty(PassportSerialNumber.getText())&&isNotEmpty(PassportNumber.getText())&&(passengerGender.getSelectedItem()!="")&&(ticket.getSelectedItem()!="")){
            return true;
        }
        else return false;
    }

    public void setPlaces(String[] places) {
        this.places = places;
    }
    public Ticket getTicket(){
        for (Ticket ticket : ticketList) {
            if ()
        }
    }

    public Passenger createPassenger(){
        Passenger passenger = new Passenger(passengerFirstName.getText(), passengerLastName.getText(), (isNotEmpty(passengerMiddleName.getText())?passengerMiddleName.getText():""), PassportNumber.getText(), PassportSerialNumber.getText(), passengerBirthDate.getText(), (passengerGender.getSelectedItem() == "Мужской"));
        return passenger;
    }
}
