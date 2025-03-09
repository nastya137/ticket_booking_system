package frontend;

import backend.ClientApplication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class TabbedPaneInterface extends JFrame {
    int i = 0;
    private SearchRoutes searchPanel;
    private JPanel userPanel;
    public TabbedPaneInterface() {//ClientApplication client
        super("Покупка билетов");
        JTabbedPane tabbedPane = new JTabbedPane();
        Font font = new Font("Verdana", Font.PLAIN, 10);
        tabbedPane.setFont(font);
        //searchPanel = new SearchRoutes();
        userPanel = new JPanel();
        JPanel content = new JPanel();
        content.setLayout(new BorderLayout());

        JPanel buttons = new JPanel();
        content.add(buttons, BorderLayout.NORTH);

        JButton add = new JButton("Добавить");
        add.setFont(font);
        add.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tabbedPane.addTab("Вкладка " + i++, new JPanel());
            }
        });
        buttons.add(add);

        JButton remove = new JButton("Удалить");
        remove.setFont(font);
        remove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int select = tabbedPane.getSelectedIndex();
                if (select >= 0) {
                    tabbedPane.removeTabAt(select);
                }
            }
        });
        buttons.add(remove);

        content.add(tabbedPane, BorderLayout.CENTER);

        getContentPane().add(content);

        setPreferredSize(new Dimension(260, 220));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame.setDefaultLookAndFeelDecorated(true);
                new TabbedPaneInterface();
            }
        });
    }
}
