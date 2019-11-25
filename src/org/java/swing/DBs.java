package org.java.swing;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.tree.DefaultMutableTreeNode;

import org.java.parsers.DB;
import org.java.parsers.DBsParser;

public class DBs extends JFrame{
	private JScrollPane dbsSp;
	
	public DBs()
	{
		FileBrowser fb = new FileBrowser();
		fb.run();
		JTree dbs = fb.getJTree();
		JLabel lblDbName = new JLabel("Nom base données : ");
		JTextField tfDbName = new JTextField(10);
		JButton btnCreateDb = new JButton("Créer");
		
		dbsSp = new JScrollPane();
		dbsSp.setViewportView(dbs);
		
		DBsParser dbP = new DBsParser("databases");
		
		JTable dbsTables = new JTable();
		
		style(dbsTables);
		
		JFrame parent = this;
		
		btnCreateDb.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(!tfDbName.getText().equals("")) {
					DB database = dbP.createDB(tfDbName.getText());
					dbsTables.setModel(refresh());
					style(dbsTables);
					new Tables(parent,database);
				}
			}
		});
		
		dbsTables.addMouseListener(new MouseAdapter() {
			  public void mouseClicked(MouseEvent e) {
			      JTable target = (JTable)e.getSource();
			      int row = target.getSelectedRow();
			      int column = target.getSelectedColumn();
			      String db = target.getValueAt(row, 0).toString();
			      DB database = dbP.getDB(db);
			      if(column == 1) {
						int reteur = JOptionPane.showConfirmDialog(parent, "Supprimer la base de données ?","Supprission", JOptionPane.YES_NO_OPTION);
						if(reteur == 0) {
					    	  dbP.removeDB(database);
					    	  dbsTables.setModel(refresh());
					    	  style(dbsTables);
						}
			      }else {
			    	  new Tables(parent,database);
			      }
			  }
			});
		
		JScrollPane TableDbsSp = new JScrollPane();
		TableDbsSp.setViewportView(dbsTables);
		
		JPanel formDb = new JPanel();
		formDb.setLayout(new FlowLayout());
		formDb.add(lblDbName);
		formDb.add(tfDbName);
		formDb.add(btnCreateDb);
		
		JPanel rightSidePane = new JPanel();
		rightSidePane.setLayout(new BorderLayout());
		
		rightSidePane.add("North",formDb);
		rightSidePane.add("Center",TableDbsSp);
		
		JPanel container = new JPanel();
		GridLayout gl_container = new GridLayout(1,2);
		container.setLayout(gl_container);
		
		container.add(dbsSp);
		container.add(rightSidePane);
		
		setContentPane(container);
		
		createFrame();
	}

	public void createFrame() {
		setTitle("Création de base de données");
		setSize(800,300);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public DefaultTableModel refresh() {
		DBsParser dbP = new DBsParser("databases");
		String[] d = dbP.getDBs();

		Object[][] databases = new Object[d.length][2];
		
		for (int i = 0; i < d.length; i++) {
			databases[i][0] = d[i];
			databases[i][1] = "Supprimer";
		}
		
		FileBrowser fb = new FileBrowser();
		fb.run();
		JTree dbs = fb.getJTree();
		dbsSp.setViewportView(dbs);
		
		return new DefaultTableModel(databases,new String[]{"Nom de la base de données","Action"});
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
}
