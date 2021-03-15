/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p0006.gui;

import p0006.dao.ProductDAO;
import p0006.dto.Category;
import p0006.dto.Product;
import p0006.ultil.Violations;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author USER
 */
public class ProductPanel extends javax.swing.JPanel {
    public static final List<Product> PRODUCTS = new Vector<>();


    static {
        try {
            Thread.sleep(1000);
            PRODUCTS.addAll(new ProductDAO().getAllProduct());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    private int lastSelectedRow = -1;

    private boolean isForNew = false;

    /**
     * Creates new form ProductPanel
     */
    public ProductPanel() {
        initComponents();
        this.table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tableClick();
            }
        });
        this.btnNew.addActionListener(this::btnAddNewClick);
        this.btnSave.addActionListener(this::btnSaveClick);
        this.btnDelete.addActionListener(this::btnDeleteClick);
        refresh();
        this.disableALl();
    }

    private void disableALl(){
        this.txtID.setEnabled(false);
        this.txtName.setEnabled(false);
        this.txtQuantity.setEnabled(false);
        this.txtUnit.setEnabled(false);
        this.txtPrice.setEnabled(false);
        this.cbxCategory.setEnabled(false);
    }

    private void setSaveState(boolean state){
        this.isForNew = state;
        this.txtID.setEnabled(state);
        this.txtName.setEnabled(true);
        this.txtQuantity.setEnabled(true);
        this.txtUnit.setEnabled(true);
        this.txtPrice.setEnabled(true);
        this.cbxCategory.setEnabled(true);
    }

    public void refresh(){
        loadTable();
        DefaultComboBoxModel<Object> model = new DefaultComboBoxModel<>();
        CategoryPanel.CATEGORIES.forEach(model::addElement);
        this.cbxCategory.setModel(model);
        if(lastSelectedRow >= 0)
            display(PRODUCTS.get(lastSelectedRow));
    }

    public void loadTable(){
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        model.setColumnIdentifiers(Product.DATA_HEADER);
        PRODUCTS.forEach(product -> model.addRow(product.toVector()));
        table.setModel(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void tableClick() {

        int row = table.getSelectedRow();
        if(row != -1){
            lastSelectedRow = row;
            Product product = (Product) table.getModel().getValueAt(row, 0);
            this.display(product);
            this.setSaveState(false);
        }
    }

    private void display(Product product) {
        if(product == null) return;

        txtID.setText(product.getId());
        txtName.setText(product.getName());
        txtPrice.setText(product.getPrice() + "");
        txtUnit.setText(product.getUnit());
        txtQuantity.setText(product.getQuantity() + "");

        cbxCategory.setSelectedItem(product.getCategory());
    }

    private Product getProduct(){
        int quantity;
        double price;
        try{
            quantity = Integer.parseInt(this.txtQuantity.getText().trim());
        }catch (NumberFormatException ex){
            JOptionPane.showMessageDialog(null, "Quantity must be  an integer!");
            return null;
        }
        try{
            price = Double.parseDouble(this.txtPrice.getText().trim());
        }catch (NumberFormatException ex){
            JOptionPane.showMessageDialog(null, "Price must be a double!");
            return null;
        }
        String id = this.txtID.getText().trim();
        String name = this.txtName.getText().trim();
        String unit = this.txtUnit.getText().trim();

        Category category = (Category) this.cbxCategory.getModel().getSelectedItem();

        Product product = new Product(id, name, category, unit, quantity, price);
        Violations<Product> violations = new Violations<>();
        violations.evaluate(product);

        if (!violations.isError())
            return violations.getEvaluateObject();
        else
            JOptionPane.showMessageDialog(null, violations.toString());
        return null;
    }

    public void btnDeleteClick(ActionEvent event){
        String id = this.txtID.getText().trim();
        int confirm = JOptionPane.showConfirmDialog(this, "Do you want to delete product " + id + "?");
        if(confirm != JOptionPane.YES_OPTION) return;
        ProductDAO dao = new ProductDAO();
        try {
            if(dao.deleteProduct(id)){
                PRODUCTS.remove(new Product(id));
                JOptionPane.showMessageDialog(null, "Delete product successfully!!");
                this.loadTable();
                this.disableALl();
                this.display(new Product());
            }else
                JOptionPane.showMessageDialog(null, "Delete product failed!!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Delete product failed!!");
        }
    }

    public void btnAddNewClick(ActionEvent event){
        this.txtID.requestFocus();
        this.setSaveState(true);
        this.display(new Product());
    }

    public void btnSaveClick(ActionEvent event){
        if(this.isForNew){
            saveNew();
        }else {
            saveUpdate();
        }
    }

    private void saveUpdate() {
        Product product = this.getProduct();
        if(product != null){
            ProductDAO dao = new ProductDAO();
            try {
                if(dao.updateProduct(product)){
                    PRODUCTS.set(PRODUCTS.indexOf(product), product);
                    this.loadTable();
                    JOptionPane.showMessageDialog(null, "Update product successfully!!!");
                }else
                    JOptionPane.showMessageDialog(null, "Update product failed!!!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Update product failed!!!");
            }
        }
    }

    private void saveNew() {
        Product product = this.getProduct();
        if(product != null){
            ProductDAO dao = new ProductDAO();
            try {
                if(dao.insertProduct(product)){
                    PRODUCTS.add(product);
                    this.loadTable();
                    JOptionPane.showMessageDialog(null, "Insert product successfully!!!");
                }else
                    JOptionPane.showMessageDialog(null, "Insert product failed!!!");
            } catch (Exception e) {
                if(e.getMessage().contains("duplicate"))
                    JOptionPane.showMessageDialog(null, "Product ID duplicate!!!");
                else
                    JOptionPane.showMessageDialog(null, "Insert product failed!!!");
            }
        }
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollBar1 = new javax.swing.JScrollBar();
        jScollBar = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtUnit = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtQuantity = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtPrice = new javax.swing.JTextField();
        btnNew = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        cbxCategory = new javax.swing.JComboBox<>();

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "null", "null"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.setColumnSelectionAllowed(true);
        table.getTableHeader().setReorderingAllowed(false);
        jScollBar.setViewportView(table);
        table.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        jLabel1.setText("Product ID");

        jLabel2.setText("Name");

        jLabel3.setText("Category");

        jLabel4.setText("Unit");

        jLabel5.setText("Quantity");

        jLabel6.setText("Price");

        btnNew.setText("Add New");

        btnSave.setText("Save");

        btnDelete.setText("Delete");

        cbxCategory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScollBar, javax.swing.GroupLayout.PREFERRED_SIZE, 528, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtID))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtName))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtUnit))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtQuantity))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtPrice))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cbxCategory, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnNew)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSave)
                        .addGap(18, 18, 18)
                        .addComponent(btnDelete)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtUnit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnNew)
                            .addComponent(btnSave)
                            .addComponent(btnDelete))
                        .addGap(0, 30, Short.MAX_VALUE))
                    .addComponent(jScollBar, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents





    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox<Object> cbxCategory;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScollBar;
    private javax.swing.JScrollBar jScrollBar1;
    private javax.swing.JTable table;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPrice;
    private javax.swing.JTextField txtQuantity;
    private javax.swing.JTextField txtUnit;
    // End of variables declaration//GEN-END:variables
}
