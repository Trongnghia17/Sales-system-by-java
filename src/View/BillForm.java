package View;
import dao.BillDao;
import dao.BillDaoImpl;
import dao.CustomerDao;
import dao.CustomerDaoImpl;
import dao.ProductDao;
import dao.ProductDaoImpl;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Bill;
import model.Customer;
import model.Product;
public class BillForm extends javax.swing.JFrame {
 private JTextField quantityField, priceField, productIdField, buyDateField, customerIdField;
   public BillForm(BillDao billDao, ProductDao productDao, CustomerDao customerDao) {
        setTitle("Nhập thông tin hóa đơn");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));
        panel.add(new JLabel("Số lượng:"));
        quantityField = new JTextField();
        panel.add(quantityField);
        panel.add(new JLabel("Giá:"));
        priceField = new JTextField();
        panel.add(priceField);
        panel.add(new JLabel("ID sản phẩm:"));
        productIdField = new JTextField();
        panel.add(productIdField);
        panel.add(new JLabel("Ngày mua (dd/MM/yyyy):"));
        buyDateField = new JTextField();
        panel.add(buyDateField);
        panel.add(new JLabel("ID khách hàng:"));
        customerIdField = new JTextField();
        panel.add(customerIdField);
        JButton addButton = new JButton("Thêm hóa đơn");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Gọi phương thức thêm hóa đơn ở đây
                    addBill();
                } catch (SQLException ex) {
                    Logger.getLogger(BillForm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panel.add(addButton);
        setLocationRelativeTo(null);
        add(panel);
        setVisible(true);
    }
    private void addBill() throws SQLException {
        BillDaoImpl billDao = new BillDaoImpl();
        Bill bill = new Bill();
        bill.setQuantity(Integer.parseInt(quantityField.getText()));
        bill.setPrice(Double.parseDouble(priceField.getText()));
        Product product = new Product();
        product.setId(Integer.parseInt(productIdField.getText()));
        bill.setProduct(product);
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date buyDate = dateFormat.parse(buyDateField.getText());
            bill.setBuyDate(buyDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Customer customer = new Customer();
        customer.setId(Integer.parseInt(customerIdField.getText()));
        bill.setCustomer(customer);
            billDao.add(bill);
            JOptionPane.showMessageDialog(this, "Hóa đơn đã được thêm thành công!");
        
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
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                BillDao billDao = new BillDaoImpl();
                ProductDao productDao = new ProductDaoImpl();
                CustomerDao customerDao = new CustomerDaoImpl();
                new BillForm(billDao,productDao,customerDao);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
