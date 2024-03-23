package View;
import dao.SupplierDao;
import dao.SupplierDaoImpl;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import model.Supplier;
public class SupplierGUI extends javax.swing.JFrame {
    private SupplierDao supplierDao;
    private JTextArea resultTextArea;
    public SupplierGUI(SupplierDao supplierDao) {
        this.supplierDao = supplierDao;
        setTitle("Quản lý Nhà Cung Cấp");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLayout(new FlowLayout());
        // Tạo các thành phần
        JButton addButton = new JButton("Thêm Nhà Cung Cấp");
        JButton updateButton = new JButton("Sửa Nhà Cung Cấp");
        JButton deleteButton = new JButton("Xóa Nhà Cung Cấp");
        resultTextArea = new JTextArea();
        resultTextArea.setEditable(false);
        // Thêm nghe sự kiện
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    themNhaCungCap();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(SupplierGUI.this, "Lỗi khi thêm Nhà Cung Cấp: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    suaNhaCungCap();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(SupplierGUI.this, "Lỗi khi sửa Nhà Cung Cấp: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    xoaNhaCungCap();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(SupplierGUI.this, "Lỗi khi xóa Nhà Cung Cấp: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        // Thêm các thành phần vào frame
        add(addButton);
        add(updateButton);
        add(deleteButton);
        add(resultTextArea);
        setLocationRelativeTo(null);
        // Hiển thị frame
        setVisible(true);
    }
    private void themNhaCungCap() throws SQLException {
        String ten = JOptionPane.showInputDialog(this, "Nhập tên Nhà Cung Cấp:");
        if (ten != null && !ten.isEmpty()) {
            Supplier supplier = new Supplier();
            supplier.setName(ten);
            try {
                supplierDao.add(supplier);
                resultTextArea.setText("Đã thêm Nhà Cung Cấp thành công. ID: " + supplier.getId() + ", Tên: " + supplier.getName());
            } catch (SQLException ex) {
                resultTextArea.setText("Lỗi khi thêm Nhà Cung Cấp: " + ex.getMessage());
            }
        } else {
            resultTextArea.setText("Không nhập tên Nhà Cung Cấp.");
        }
    }
    private void suaNhaCungCap() throws SQLException {
        String idStr = JOptionPane.showInputDialog(this, "Nhập ID Nhà Cung Cấp cần sửa:");
        if (idStr != null && !idStr.isEmpty()) {
            try {
                int id = Integer.parseInt(idStr);
                Supplier existingSupplier = supplierDao.getOne(id);
                if (existingSupplier != null) {
                    String tenMoi = JOptionPane.showInputDialog(this, "Nhập tên mới cho Nhà Cung Cấp:", existingSupplier.getName());

                    if (tenMoi != null && !tenMoi.isEmpty()) {
                        existingSupplier.setName(tenMoi);
                        supplierDao.update(existingSupplier);
                        resultTextArea.setText("Đã cập nhật Nhà Cung Cấp thành công. ID: " + existingSupplier.getId() + ", Tên mới: " + existingSupplier.getName());
                    } else {
                        resultTextArea.setText("Không nhập tên mới cho Nhà Cung Cấp.");
                    }
                } else {
                    resultTextArea.setText("Không tồn tại Nhà Cung Cấp với ID: " + id);
                }
            } catch (NumberFormatException e) {
                resultTextArea.setText("ID không hợp lệ.");
            }
        } else {
            resultTextArea.setText("Không nhập ID Nhà Cung Cấp.");
        }
    }
    private void xoaNhaCungCap() throws SQLException {
        String idStr = JOptionPane.showInputDialog(this, "Nhập ID Nhà Cung Cấp cần xóa:");
        if (idStr != null && !idStr.isEmpty()) {
            try {
                int id = Integer.parseInt(idStr);
                Supplier existingSupplier = supplierDao.getOne(id);

                if (existingSupplier != null) {
                    supplierDao.delete(id);
                    resultTextArea.setText("Đã xóa Nhà Cung Cấp thành công. ID: " + existingSupplier.getId() + ", Tên: " + existingSupplier.getName());
                } else {
                    resultTextArea.setText("Không tồn tại Nhà Cung Cấp với ID: " + id);
                }
            } catch (NumberFormatException e) {
                resultTextArea.setText("ID không hợp lệ.");
            }
        } else {
            resultTextArea.setText("Không nhập ID Nhà Cung Cấp.");
        }
    }
    private void xemTatCaNhaCungCap() {
        StringBuilder ketQua = new StringBuilder("Tất Cả Nhà Cung Cấp:\n");
        for (Supplier supplier : supplierDao.getAll()) {
            ketQua.append("ID: ").append(supplier.getId()).append(", Tên: ").append(supplier.getName()).append("\n");
        }
        resultTextArea.setText(ketQua.toString());
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
        // Tạo một thể hiện của SupplierDao (giả sử bạn đã triển khai lớp JDBCConnection)
        SupplierDao supplierDao = new SupplierDaoImpl();
        // Tạo một thể hiện của SupplierGUI với SupplierDao
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SupplierGUI(supplierDao);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
