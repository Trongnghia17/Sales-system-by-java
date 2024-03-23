/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

import dao.BillDao;
import dao.BillDaoImpl;
import dao.CategoryDao;
import dao.CategoryDaoImpl;
import dao.CustomerDao;
import dao.CustomerDaoImpl;
import dao.ProductDao;
import dao.ProductDaoImpl;
import dao.SupplierDao;
import dao.SupplierDaoImpl;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
public class MainGUI extends javax.swing.JFrame {
    public MainGUI() {
        setTitle("Quản lý cửa hàng");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JButton productButton = new JButton("Quản lý sản phẩm");
        JButton categoryButton = new JButton("Quản lý loại sản phẩm");
        JButton customerButton = new JButton("Quản lý khách hàng");
        JButton supplierButton = new JButton("Quản lý nhà cung cấp");
        JButton billButton = new JButton("Mua hàng");
        productButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openProductSection();
            }
        });
        categoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openCategorySection();
            }
        });
        customerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openCustomerSection();
            }
        });
        supplierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openSupplierSection();
            }
        });
        billButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openBillSection();
            }
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(productButton);
        buttonPanel.add(categoryButton);
        buttonPanel.add(customerButton);
        buttonPanel.add(supplierButton);
        buttonPanel.add(billButton);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(buttonPanel, BorderLayout.CENTER);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    private void openProductSection() {
        ProductDao productDao = new ProductDaoImpl();
        CategoryDao categoryDao = new CategoryDaoImpl();
        SupplierDao supplierDao = new SupplierDaoImpl();
        ProductGUI productGUI = new ProductGUI(productDao, categoryDao, supplierDao);
        productGUI.setVisible(true);
    }
     private void openBillSection() {
        BillDao billDao = new BillDaoImpl();
        ProductDao productDao = new ProductDaoImpl();
        CustomerDao customerDao = new CustomerDaoImpl();
        BillForm billForm = new BillForm(billDao, productDao, customerDao);
        billForm.setVisible(true);
    }
    private void openCategorySection() {
        CategoryDao categoryDao = new CategoryDaoImpl();
        CategoryGUI categoryGUI = new CategoryGUI(categoryDao);
        categoryGUI.setVisible(true);
    }
    private void openCustomerSection() {
        CustomerDao customerDao = new CustomerDaoImpl();
        CustomerGUI customerGUI = new CustomerGUI(customerDao);
        customerGUI.setVisible(true);
    }
    private void openSupplierSection() {
        SupplierDao supplierDao = new SupplierDaoImpl();
        SupplierGUI supplierGUI = new SupplierGUI(supplierDao);
        supplierGUI.setVisible(true);
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
                new MainGUI();
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
