package View;
import dao.CategoryDao;
import dao.CategoryDaoImpl;
import dao.ProductDao;
import dao.ProductDaoImpl;
import dao.SupplierDao;
import dao.SupplierDaoImpl;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.table.DefaultTableModel;
import model.Category;
import model.Product;
import model.Supplier;
public class ProductGUI extends javax.swing.JFrame {
    private ProductDao productDao;
    private CategoryDao categoryDao;
    private SupplierDao supplierDao;
    private JTextField searchField;
    private JTextArea resultArea;
    private JTable dataTable;
    private DefaultTableModel tableModel;
    public ProductGUI(ProductDao productDao, CategoryDao categoryDao, SupplierDao supplierDao) {
        this.productDao = productDao;
        this.categoryDao = categoryDao;
        this.supplierDao = supplierDao;
        initializeComponents();
        setTitle("Hệ Thống Quản Lý Sản Phẩm");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    private void initializeComponents() {
        searchField = new JTextField(20);
        resultArea = new JTextArea(10, 30);
        JButton addButton = new JButton("Thêm Sản Phẩm");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addProduct();
            }
        });
        JButton updateButton = new JButton("Sửa Sản Phẩm");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateProduct();
            }
        });
        JButton deleteButton = new JButton("Xóa Sản Phẩm");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteProduct();
            }
        });
        JButton searchButton = new JButton("Tìm kiếm theo Tên");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchByName();
            }
        });
        tableModel = new DefaultTableModel(
                new String[]{"ID", "Tên", "Số lượng", "Giá", "Loại sp", "Nhà cung cấp"}, 0);
        dataTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(dataTable);
        JPanel panel = new JPanel();
        panel.add(addButton);
        panel.add(updateButton);
        panel.add(deleteButton);
        panel.add(new JLabel("Tìm kiếm theo Tên: "));
        panel.add(searchField);
        panel.add(searchButton);
        panel.add(tableScrollPane);
        add(panel);
    }
    private void addProduct() {
    try {
        // Hiển thị hộp thoại nhập thông tin sản phẩm
        String name = JOptionPane.showInputDialog("Nhập tên sản phẩm:");
        String quantityStr = JOptionPane.showInputDialog("Nhập số lượng:");
        String priceStr = JOptionPane.showInputDialog("Nhập giá:");
        // Chuyển đổi chuỗi thành kiểu số
        int quantity = Integer.parseInt(quantityStr);
        double price = Double.parseDouble(priceStr);
        // Lấy danh sách ID của danh mục và nhà cung cấp từ DAO
        List<Integer> categoryIds = getCategoryIdsFromDao();
        List<Integer> supplierIds = getSupplierIdsFromDao();
        // Hiển thị danh sách ID của danh mục và nhà cung cấp để chọn
        int selectedCategoryId = Integer.parseInt(JOptionPane.showInputDialog(
                null,
                "Chọn ID danh mục:",
                "Chọn ID danh mục",
                JOptionPane.QUESTION_MESSAGE,
                null,
                categoryIds.toArray(),
                categoryIds.get(0)  // Giá trị mặc định
        ).toString());
        int selectedSupplierId = Integer.parseInt(JOptionPane.showInputDialog(
                null,
                "Chọn ID nhà cung cấp:",
                "Chọn ID nhà cung cấp",
                JOptionPane.QUESTION_MESSAGE,
                null,
                supplierIds.toArray(),
                supplierIds.get(0)  // Giá trị mặc định
        ).toString());
        // Tạo đối tượng Product
        Product newProduct = new Product();
        newProduct.setName(name);
        newProduct.setQuantity(quantity);
        newProduct.setPrice(price);
        // Tạo đối tượng Category chỉ với ID
        Category category = new Category();
        category.setId(selectedCategoryId);
        newProduct.setCategory(category);
        // Kiểm tra xem selectedSupplierId có phải là null hay không trước khi tạo đối tượng Supplier
        if (selectedSupplierId != 0) {
            Supplier supplier = new Supplier();
            supplier.setId(selectedSupplierId);
            newProduct.setSupplier(supplier);
        }
        // Gọi phương thức DAO để thêm sản phẩm
        productDao.add(newProduct);
        // Lấy thông tin của sản phẩm vừa thêm và hiển thị trong resultArea
        Product addedProduct = productDao.getOne(newProduct.getId());
        // Cập nhật resultArea với thông tin sản phẩm vừa thêm
        resultArea.setText("Sản phẩm đã được thêm thành công!\n");
        resultArea.append("ID: " + addedProduct.getId() + "\n");
        resultArea.append("Tên: " + addedProduct.getName() + "\n");
        resultArea.append("Số lượng: " + addedProduct.getQuantity() + "\n");
        resultArea.append("Giá: " + addedProduct.getPrice() + "\n");
        resultArea.append("ID Danh mục: " + addedProduct.getCategory().getId() + "\n");
        // Kiểm tra xem Supplier có tồn tại hay không trước khi gọi phương thức getId()
        if (addedProduct.getSupplier() != null) {
            resultArea.append("ID Nhà cung cấp: " + addedProduct.getSupplier().getId() + "\n");
        }
        resultArea.append("------------------------------\n");
    } catch (Exception ex) {
        // Xử lý ngoại lệ, cập nhật kết quả với thông báo lỗi
        resultArea.setText("Lỗi khi thêm sản phẩm: " + ex.getMessage());
    }
}
    private void updateProduct() {
    try {
        // Hiển thị hộp thoại để nhập ID sản phẩm cần sửa
        String productIdStr = JOptionPane.showInputDialog("Nhập ID sản phẩm cần sửa:");
        int productId = Integer.parseInt(productIdStr);
        // Gọi DAO để lấy thông tin sản phẩm cần sửa
        Product existingProduct = productDao.getOne(productId);
        // Hiển thị hộp thoại để nhập thông tin mới của sản phẩm
        String newName = JOptionPane.showInputDialog("Nhập tên mới:");
        String newQuantityStr = JOptionPane.showInputDialog("Nhập số lượng mới:");
        String newPriceStr = JOptionPane.showInputDialog("Nhập giá mới:");
        // Cập nhật thông tin sản phẩm
        existingProduct.setName(newName);
        existingProduct.setQuantity(Integer.parseInt(newQuantityStr));
        existingProduct.setPrice(Double.parseDouble(newPriceStr));
        // Hiển thị danh sách các danh mục và nhà cung cấp để chọn
        List<Integer> categoryIds = getCategoryIdsFromDao();
        List<Integer> supplierIds = getSupplierIdsFromDao();
        // Hiển thị danh sách ID của danh mục và nhà cung cấp để chọn
        int selectedCategoryId = Integer.parseInt(JOptionPane.showInputDialog(
                null,
                "Chọn ID danh mục:",
                "Chọn ID danh mục",
                JOptionPane.QUESTION_MESSAGE,
                null,
                categoryIds.toArray(),
                existingProduct.getCategory().getId()  // Giá trị mặc định
        ).toString());
        int selectedSupplierId = existingProduct.getSupplier() != null
                ? existingProduct.getSupplier().getId()
                : 0;
        selectedSupplierId = Integer.parseInt(JOptionPane.showInputDialog(
                null,
                "Chọn ID nhà cung cấp:",
                "Chọn ID nhà cung cấp",
                JOptionPane.QUESTION_MESSAGE,
                null,
                supplierIds.toArray(),
                selectedSupplierId  // Giá trị mặc định
        ).toString());
        // Tạo đối tượng Category chỉ với ID
        Category category = new Category();
        category.setId(selectedCategoryId);
        existingProduct.setCategory(category);
        // Kiểm tra xem selectedSupplierId có phải là null hay không trước khi tạo đối tượng Supplier
        if (selectedSupplierId != 0) {
            Supplier supplier = new Supplier();
            supplier.setId(selectedSupplierId);
            existingProduct.setSupplier(supplier);
        } else {
            existingProduct.setSupplier(null);
        }
        // Gọi DAO để cập nhật thông tin sản phẩm
        productDao.update(existingProduct);
        // Hiển thị thông báo thành công
        resultArea.setText("Sản phẩm đã được cập nhật thành công!\n");
    } catch (Exception ex) {
        // Xử lý ngoại lệ, cập nhật kết quả với thông báo lỗi
        resultArea.setText("Lỗi khi cập nhật sản phẩm: " + ex.getMessage());
    }
}
    private void deleteProduct() {
    try {
        // Hiển thị hộp thoại để nhập ID sản phẩm cần xóa
        String productIdStr = JOptionPane.showInputDialog("Nhập ID sản phẩm cần xóa:");
        int productId = Integer.parseInt(productIdStr);
        // Gọi DAO để xóa sản phẩm
        productDao.delete(productId);
        // Hiển thị thông báo thành công
        resultArea.setText("Sản phẩm đã được xóa thành công!\n");
    } catch (Exception ex) {
        // Xử lý ngoại lệ, cập nhật kết quả với thông báo lỗi
        resultArea.setText("Lỗi khi xóa sản phẩm: " + ex.getMessage());
    }
}
    private void searchByName() {
        try {
            String searchKeyword = searchField.getText();
            List<Product> searchResults = productDao.searchByName(searchKeyword);

            displaySearchResults(searchResults);
        } catch (Exception ex) {
            resultArea.setText("Lỗi khi tìm kiếm theo tên: " + ex.getMessage());
        }
    }
    private void displaySearchResults(List<Product> searchResults) {
        resultArea.setText("");
        tableModel.setRowCount(0);
        if (searchResults.isEmpty()) {
            resultArea.append("Không tìm thấy sản phẩm phù hợp.");
        } else {
            for (Product product : searchResults) {
                resultArea.append("ID: " + product.getId() + "\n");
                resultArea.append("Tên: " + product.getName() + "\n");
                resultArea.append("Số lượng: " + product.getQuantity() + "\n");
                resultArea.append("Giá: " + product.getPrice() + "\n");
                resultArea.append("Danh mục: " + product.getCategory().getName() + "\n");
                resultArea.append("Nhà cung cấp: " +
                        ((product.getSupplier() != null) ? product.getSupplier().getName() : "") + "\n");
                resultArea.append("------------------------------\n");
                Object[] rowData = {
                        product.getId(),
                        product.getName(),
                        product.getQuantity(),
                        product.getPrice(),
                        product.getCategory().getName(),
                        (product.getSupplier() != null) ? product.getSupplier().getName() : ""
                };
                tableModel.addRow(rowData);
            }
        }
    }
    private List<Integer> getCategoryIdsFromDao() {
        try {
            List<Category> categories = categoryDao.getAll();
            return categories.stream().map(Category::getId).collect(Collectors.toList());
        } catch (Exception ex) {
            ex.printStackTrace();
            return List.of();
        }
    }
    private List<Integer> getSupplierIdsFromDao() {
        try {
            List<Supplier> suppliers = supplierDao.getAll();
            return suppliers.stream().map(Supplier::getId).collect(Collectors.toList());
        } catch (Exception ex) {
            ex.printStackTrace();
            return List.of();
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jFrame1 = new javax.swing.JFrame();

        jMenuItem1.setText("jMenuItem1");

        jMenu1.setText("jMenu1");

        jMenu2.setText("jMenu2");

        jMenuItem2.setText("jMenuItem2");

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 686, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 327, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
       SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ProductDao productDao = new ProductDaoImpl();
                CategoryDao categoryDao = new CategoryDaoImpl();  
                SupplierDao supplierDao = new SupplierDaoImpl();  

                new ProductGUI(productDao, categoryDao, supplierDao);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFrame jFrame1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    // End of variables declaration//GEN-END:variables
}
