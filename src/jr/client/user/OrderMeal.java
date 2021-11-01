package jr.client.user;

import jr.jdbc.DataUtils;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;

public class OrderMeal {
    private JPanel root;
    private JTable dishesTable;
    private JTextField searchTextField;
    private JButton carButton;
    private JButton resetButton;
    private JComboBox<String> classComboBox;
    private static Client client;

    public static void main(String[] args) {
        client = new Client();

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("OrderMeal");
            frame.setContentPane(new OrderMeal().root);

            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    super.windowClosing(e);
                    client.logout();
                }
            });

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(700, 600);
            frame.setVisible(false);

            readIdAndIP readIdAndIP = new readIdAndIP(client,frame);
            readIdAndIP.setLocationRelativeTo(null);
            readIdAndIP.pack();
            readIdAndIP.setTitle("初始化");
            readIdAndIP.setVisible(true);
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        //DataUtils dataUtils = new DataUtils();

        CustomerDishesJTable customerDishesJTable = new CustomerDishesJTable();
        dishesTable = customerDishesJTable.getTable();

        //搜索框,分类下拉框
        searchTextField = new JTextField();
        classComboBox = new JComboBox<>(DataUtils.allClass());

        searchTextField.addActionListener(e -> customerDishesJTable.newFilter(searchTextField.getText()));

        classComboBox.addActionListener(e -> customerDishesJTable.newFilter((String) Objects.requireNonNull(classComboBox.getSelectedItem())));

        //重置按钮
        resetButton = new JButton();
        resetButton.addActionListener(e -> customerDishesJTable.reset());

        //购物车
        carButton = new JButton();
        carButton.addActionListener(e -> {
            customerDishesJTable.newFilter("");
            classComboBox.setSelectedItem("全部");
            ShoppingCar sC = new ShoppingCar(customerDishesJTable, client);
            sC.setLocationRelativeTo(searchTextField);
            sC.setTitle("购物车");
            sC.pack();
            sC.setVisible(true);
        });
    }
}
