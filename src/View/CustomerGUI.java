package View;
import dao.CustomerDao;
import dao.CustomerDaoImpl;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import model.Customer;
public class CustomerGUI extends javax.swing.JFrame {
  private CustomerDao customerDao;
    private JButton addButton;
    private JButton deleteButton;
    private JButton updateButton;
    public CustomerGUI(CustomerDao customerDao) {
        this.customerDao = customerDao;
        setTitle("Quản lý thông tin khách hàng");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents1();
        addComponents();
        addListeners();
        setLocationRelativeTo(null);
    }
    private void initComponents1() {
        addButton = new JButton("Thêm thông tin khách hàng");
        deleteButton = new JButton("Xóa thông tin khách hàng");
        updateButton = new JButton("Sửa thông tin khách hàng");
    }
    private void addComponents() {
        JPanel panel = new JPanel();
        panel.add(addButton);
        panel.add(updateButton);
        panel.add(deleteButton);
        add(panel);
    }
    private void addListeners() {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Hiển thị hộp thoại để nhập tên và số điện thoại
                String customerName = JOptionPane.showInputDialog(CustomerGUI.this, "Nhập tên khách hàng:");
                if (customerName != null) {
                    String phoneText = JOptionPane.showInputDialog(CustomerGUI.this, "Nhập số điện thoại:");
                    if (phoneText != null && !phoneText.isEmpty()) {
                        try {
                            int phone = Integer.parseInt(phoneText);
                            Customer customer = new Customer();
                            customer.setName(customerName);
                            customer.setPhone(phone);

                            customerDao.add(customer);
                            JOptionPane.showMessageDialog(CustomerGUI.this, "Thêm thành công");
                        } catch (NumberFormatException | SQLException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(CustomerGUI.this, "Lỗi khi thêm khách hàng");
                        }
                    } else {
                        JOptionPane.showMessageDialog(CustomerGUI.this, "Vui lòng nhập số điện thoại");
                    }
                } else {
                    JOptionPane.showMessageDialog(CustomerGUI.this, "Vui lòng nhập tên khách hàng");
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String customerIdStr = JOptionPane.showInputDialog(CustomerGUI.this, "Nhập ID khách hàng cần xóa:");

                if (customerIdStr != null && !customerIdStr.isEmpty()) {
                    try {
                        int customerId = Integer.parseInt(customerIdStr);
                        customerDao.delete(customerId);
                        JOptionPane.showMessageDialog(CustomerGUI.this, "Xóa thành công");
                    } catch (NumberFormatException | SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(CustomerGUI.this, "Lỗi khi xóa khách hàng");
                    }
                } else {
                    JOptionPane.showMessageDialog(CustomerGUI.this, "Vui lòng nhập ID khách hàng cần xóa");
                }
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String customerIdStr = JOptionPane.showInputDialog(CustomerGUI.this, "Nhập ID khách hàng cần sửa:");

                if (customerIdStr != null && !customerIdStr.isEmpty()) {
                    try {
                        int customerId = Integer.parseInt(customerIdStr);
                        Customer existingCustomer = customerDao.getOne(customerId);

                        if (existingCustomer != null) {
                            String newName = JOptionPane.showInputDialog(CustomerGUI.this, "Nhập tên mới:");
                            String newPhoneText = JOptionPane.showInputDialog(CustomerGUI.this, "Nhập SĐT mới:");

                            if (newName != null && newPhoneText != null && !newName.isEmpty() && !newPhoneText.isEmpty()) {
                                int newPhone = Integer.parseInt(newPhoneText);
                                existingCustomer.setName(newName);
                                existingCustomer.setPhone(newPhone);
                                customerDao.update(existingCustomer);
                                JOptionPane.showMessageDialog(CustomerGUI.this, "Sửa thành công");
                            } else {
                                JOptionPane.showMessageDialog(CustomerGUI.this, "Vui lòng nhập đầy đủ thông tin");
                            }
                        } else {
                            JOptionPane.showMessageDialog(CustomerGUI.this, "Không tìm thấy khách hàng với ID đã nhập");
                        }
                    } catch (NumberFormatException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(CustomerGUI.this, "Lỗi khi sửa khách hàng");
                    }
                } else {
                    JOptionPane.showMessageDialog(CustomerGUI.this, "Vui lòng nhập ID khách hàng cần sửa");
                }
            }
        });
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    public static void main(String args[]) {
        CustomerDao customerDao = new CustomerDaoImpl();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CustomerGUI(customerDao).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
