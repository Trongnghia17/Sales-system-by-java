package View;
import dao.CategoryDao;
import dao.CategoryDaoImpl;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import model.Category;
public class CategoryGUI extends javax.swing.JFrame {
    private CategoryDao categoryDao;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    public CategoryGUI(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
        setTitle("Quản lý loại sản phẩm");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents1();
        addComponents();
        addListeners();
        setLocationRelativeTo(null);
    }
    private void initComponents1() {
        addButton = new JButton("Thêm loại sản phẩm");
        updateButton = new JButton("Sửa loại sản phẩm"); // Khởi tạo nút sửa
        deleteButton = new JButton("Xóa loại sản phẩm");
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
                String categoryName = JOptionPane.showInputDialog(CategoryGUI.this, "Nhập tên loại sản phẩm:");

                if (categoryName != null && !categoryName.isEmpty()) {
                    Category category = new Category();
                    category.setName(categoryName);
                    try {
                        categoryDao.add(category);
                        JOptionPane.showMessageDialog(CategoryGUI.this, "Thêm thành công");
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(CategoryGUI.this, "Lỗi khi thêm loại sản phẩm");
                    }
                } else {
                    JOptionPane.showMessageDialog(CategoryGUI.this, "Vui lòng nhập tên loại sản phẩm");
                }
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String categoryIdStr = JOptionPane.showInputDialog(CategoryGUI.this, "Nhập ID loại sản phẩm cần sửa:");

                if (categoryIdStr != null && !categoryIdStr.isEmpty()) {
                    try {
                        int categoryId = Integer.parseInt(categoryIdStr);
                        Category existingCategory = categoryDao.getOne(categoryId);
                        if (existingCategory != null) {
                            String newName = JOptionPane.showInputDialog(CategoryGUI.this, "Nhập tên mới:");
                            if (newName != null && !newName.isEmpty()) {
                                existingCategory.setName(newName);
                                categoryDao.update(existingCategory);
                                JOptionPane.showMessageDialog(CategoryGUI.this, "Sửa thành công");
                            } else {
                                JOptionPane.showMessageDialog(CategoryGUI.this, "Vui lòng nhập tên mới");
                            }
                        } else {
                            JOptionPane.showMessageDialog(CategoryGUI.this, "Không tìm thấy loại sản phẩm với ID đã nhập");
                        }
                    } catch (NumberFormatException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(CategoryGUI.this, "Lỗi khi sửa loại sản phẩm");
                    }
                } else {
                    JOptionPane.showMessageDialog(CategoryGUI.this, "Vui lòng nhập ID loại sản phẩm cần sửa");
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String categoryIdStr = JOptionPane.showInputDialog(CategoryGUI.this, "Nhập ID loại sản phẩm cần xóa:");

                if (categoryIdStr != null && !categoryIdStr.isEmpty()) {
                    try {
                        int categoryId = Integer.parseInt(categoryIdStr);
                        categoryDao.delete(categoryId);
                        JOptionPane.showMessageDialog(CategoryGUI.this, "Xóa thành công");
                    } catch (NumberFormatException | SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(CategoryGUI.this, "Lỗi khi xóa loại sản phẩm");
                    }
                } else {
                    JOptionPane.showMessageDialog(CategoryGUI.this, "Vui lòng nhập ID loại sản phẩm cần xóa");
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
        CategoryDao categoryDao = new CategoryDaoImpl();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CategoryGUI(categoryDao).setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
