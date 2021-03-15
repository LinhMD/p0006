/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p0006.gui;

import p0006.dao.CategoryDAO;
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
public class CategoryPanel extends javax.swing.JPanel {

    public static final List<Category> CATEGORIES = new Vector<>();

    static {
        try {
            CATEGORIES.addAll(new CategoryDAO().getAllCategory());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    private boolean isForNew = true;

    /**
     * Creates new form CategoryPanel
     */
    public CategoryPanel() {
        initComponents();
        loadTable();
        btnAddNew.addActionListener(this::addNewClick);
        btnSave.addActionListener(this::btnSaveClick);
        btnDelete.addActionListener(this::btnDeleteClick);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tableClick();
            }
        });
        disableAll();
    }

    private void disableAll(){
        this.txtID.setEnabled(false);
        this.txtName.setEnabled(false);
        this.txtDescription.setEnabled(false);
    }

    public void loadTable(){
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        model.setColumnIdentifiers(Category.DATA_HEADER);
        CATEGORIES.forEach(category -> model.addRow(category.toVector()));
        table.setModel(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void setSaveStatus(boolean isForNew){
        this.isForNew = isForNew;
        this.txtID.setEnabled(isForNew);
        this.txtName.setEnabled(true);
        this.txtDescription.setEnabled(true);
    }

    public void tableClick(){
        int row = table.getSelectedRow();
        if(row != -1){
            Category category = (Category) table.getModel().getValueAt(row, 1);
            this.display(category);
            this.setSaveStatus(false);
        }
    }

    private void display(Category category){
        if(category == null) return;

        this.txtID.setText(category.getId());
        this.txtName.setText(category.getName());
        this.txtDescription.setText(category.getDescription());
    }

    private Category getCategory(){
        String id = this.txtID.getText().trim();
        String name = this.txtName.getText().trim();
        String description = this.txtDescription.getText().trim();

        Category category = new Category(id, name, description);
        Violations<Category> violations = new Violations<>();
        violations.evaluate(category);
        if(violations.isError()){
            JOptionPane.showMessageDialog(null, violations.toString());
            return null;
        }else {
            return category;
        }
    }

    public void addNewClick(ActionEvent event) {
        this.txtID.requestFocus();
        this.setSaveStatus(true);
        this.display(new Category());
    }

    public void btnSaveClick(ActionEvent event){
        if(this.isForNew){
            saveNew();
        }else {
            saveUpdate();
        }
    }

    private void saveUpdate() {
        Category category = this.getCategory();
        if(category != null){
            CategoryDAO dao = new CategoryDAO();
            try {
                if(dao.updateCategory(category)){
                    CATEGORIES.get(CATEGORIES.indexOf(category)).update(category);
                    JOptionPane.showMessageDialog(null, "Update category " + category.getId() + " successfully!");
                }else{
                    JOptionPane.showMessageDialog(null, "Update category " + category.getId() + " failed!");
                }
            } catch (Exception  e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
            this.loadTable();
            this.setSaveStatus(false);
        }
    }

    private void saveNew() {
        Category category = this.getCategory();
        if(category != null){
            CategoryDAO dao = new CategoryDAO();
            try {
                if(dao.insertCategory(category)){
                    CATEGORIES.add(category);
                    JOptionPane.showMessageDialog(null, "Insert category " + category.getId() + " successfully!");
                }else{
                    JOptionPane.showMessageDialog(null, "Insert category " + category.getId() + " failed!");
                }
            } catch (Exception  e) {
                if(e.getMessage().contains("duplicate"))
                    JOptionPane.showMessageDialog(null, "Category ID duplicate!!!");
                else
                    JOptionPane.showMessageDialog(null, e.getMessage());
            }
            this.loadTable();
            this.setSaveStatus(false);
        }
    }

    public void btnDeleteClick(ActionEvent event){
        String id = this.txtID.getText().trim();
        int confirm = JOptionPane.showConfirmDialog(this, "Do you want to delete category " + id + "?");
        if(confirm != JOptionPane.YES_OPTION) return;

        List<Product> products = ProductPanel.PRODUCTS;
        if(products.stream().noneMatch(p -> p.getId().equalsIgnoreCase(id))){
            CategoryDAO dao = new CategoryDAO();
            try {
                if(dao.deleteCategory(id)){
                    CATEGORIES.remove(new Category(id));
                    JOptionPane.showMessageDialog(null, "Delete category successfully!!");
                    this.loadTable();
                    this.display(new Category());
                    this.disableAll();
                }else
                    JOptionPane.showMessageDialog(null, "Delete category failed!!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Delete Category failed some product still have this category!");
            }
        }else
            JOptionPane.showMessageDialog(null, "Delete Category failed some product still have this category!");

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        txtName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnAddNew = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDescription = new javax.swing.JTextArea();

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.setColumnSelectionAllowed(false);
        table.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(table);
        table.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);


        jLabel1.setText("Category ID:");

        txtID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIDActionPerformed(evt);
            }
        });

        jLabel2.setText("Category Name:");

        jLabel3.setText("Description:");

        btnAddNew.setText("Add New");

        btnSave.setText("Save");


        btnDelete.setText("Delete");


        txtDescription.setColumns(20);
        txtDescription.setRows(10);
        jScrollPane2.setViewportView(txtDescription);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 489, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(txtID))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(txtName, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(190, 190, 190))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAddNew)
                        .addGap(18, 18, 18)
                        .addComponent(btnSave)
                        .addGap(18, 18, 18)
                        .addComponent(btnDelete)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAddNew)
                            .addComponent(btnSave)
                            .addComponent(btnDelete)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents



    private void txtIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIDActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddNew;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnSave;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable table;
    private javax.swing.JTextArea txtDescription;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtName;
    // End of variables declaration//GEN-END:variables
}
