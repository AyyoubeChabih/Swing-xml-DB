package org.java.swing;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import org.java.parsers.DB;
import org.java.parsers.DBsParser;
import org.java.parsers.Table;

public class Tables extends JDialog implements ActionListener {

	private JFrame parent;
	private JTextField tfTableName;
	private JTextField tfNbCols;
	private DB database;
	private JTable listeTables;
	
	public Tables(JFrame parent, DB database) {
		
		this.database = database;
		this.parent = parent;
		
		JLabel lblTableName = new JLabel("Nom table : ");
		tfTableName = new JTextField(10);
		JLabel lblNbColumns = new JLabel("Nombre de colonnes : ");
		tfNbCols = new JTextField(5);
		JButton btnCreateTable = new JButton("Créer");
		btnCreateTable.addActionListener(this);
		
		JLabel lblListeTable = new JLabel("Liste des tables : ");
		listeTables = new JTable();
		
		style(listeTables);
		
		JDialog p = this;
		
		listeTables.addMouseListener(new MouseAdapter() {
			  public void mouseClicked(MouseEvent e) {
			      JTable target = (JTable)e.getSource();
			      int row = target.getSelectedRow();
			      int column = target.getSelectedColumn();
			      String tab = target.getValueAt(row, 0).toString();
			      Table table = database.getTable(tab);
			      if(column == 1) {
						int reteur = JOptionPane.showConfirmDialog(p, "Supprimer la table ?","Supprission", JOptionPane.YES_NO_OPTION);
						if(reteur == 0) {
				    	  database.removeTable(table);
				    	  listeTables.setModel(refresh());
				    	  style(listeTables);
						}
			      }else {
			    	  new TablesOperations(p,table);
			      }
			  }
			});
		
		JScrollPane TableTablesSp = new JScrollPane();
		TableTablesSp.setViewportView(listeTables);
		
		JPanel top = new JPanel();
		top.setLayout(new FlowLayout());
		top.add(lblTableName);
		top.add(tfTableName);
		top.add(lblNbColumns);
		top.add(tfNbCols);
		top.add(btnCreateTable);
		
		JPanel bottom = new JPanel();
		bottom.setLayout(new BorderLayout());
		bottom.add("North",lblListeTable);
		bottom.add("Center",TableTablesSp);
		
		JPanel container = new JPanel();
		container.setLayout(new BorderLayout());
		container.add("North",top);
		container.add("Center",bottom);
		
		setContentPane(container);
		
		
		createFrame();
	}
	
	public void createFrame() {
		setModal(true);
		setTitle("Création des tables");
		setSize(800,300);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(parent);
		setVisible(true);
	}
	
	
	private int getColumnsNumber()
	{
		int n = 0;
		try
		{
			n = Integer.parseInt(tfNbCols.getText());
		}catch(Exception e) {}
		return n;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int colnbr = getColumnsNumber();
		if(colnbr != 0)
		{
			String tableName = tfTableName.getText().toString();
			new Columns(this,colnbr,tableName,database,this);
		}
	}
	
	public DefaultTableModel refresh() {
		
		String[] t = database.getTables();

		Object[][] tables = new Object[t.length][2];
		
		for (int i = 0; i < t.length; i++) {
			tables[i][0] = t[i];
			tables[i][1] = "Supprimer";
		}
		
		return new DefaultTableModel(tables,new String[]{"Nom du table","Action"});
	}
	
	void style(JTable dbsTables) {
		dbsTables.setModel(refresh());
		TableColumn column = dbsTables.getColumnModel().getColumn(1);
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setHorizontalAlignment(SwingConstants.CENTER);
		dbsTables.setDefaultRenderer(Object.class, renderer);
		DefaultTableCellRenderer red = new DefaultTableCellRenderer();
		red.setHorizontalAlignment(SwingConstants.CENTER);
		red.setForeground(Color.red);
		column.setCellRenderer(red);
	}
	
	public JTable getListeTables() {
		return listeTables;
	}
	
}
