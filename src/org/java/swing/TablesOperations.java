package org.java.swing;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import org.java.parsers.Table;

public class TablesOperations extends JDialog {

	private JDialog parent;
	private Table table;
	private JTable tableData;
	
	public TablesOperations(JDialog parent, Table table) {
		this.parent = parent;
		this.table = table;
		JDialog par = this;
		TablesOperations ttt = this;
		
		JPanel container = new JPanel();
		
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		
		JPanel descPan = new JPanel();
		descPan.setLayout(new BorderLayout());
		
		JTable tableInfos = new JTable();
		
		String[][] c = table.getColumns();
		
		Collections.reverse(Arrays.asList(c));
		
		tableInfos.setModel(new DefaultTableModel(c,new String[] {"Nom colonne","Type colonne"}));
		style(tableInfos);
		
		JScrollPane tableInfosSp = new JScrollPane(tableInfos);
		
		descPan.add("Center",tableInfosSp);
		
		JPanel selectPan = new JPanel();
		selectPan.setLayout(new BorderLayout());
		
		tableData = new JTable();
		
		tableData.setModel(refresh());
		styleSelect(tableData);
		
		tableData.addMouseListener(new MouseAdapter() {
			  public void mouseClicked(MouseEvent e) {
			      JTable target = (JTable)e.getSource();
			      int row = target.getSelectedRow();
			      int column = target.getSelectedColumn();
			      String tab = target.getValueAt(row, 0).toString();
			      if(column == getCols().length-1) {
					int reteur = JOptionPane.showConfirmDialog(par, "Supprimer la ligne ?","Supprission", JOptionPane.YES_NO_OPTION);
					if(reteur == 0) {
				    	  table._init_DOM();
				    	  table.removeElement(row);
				    	  tableData.setModel(refresh());
				    	  styleSelect(tableData);
					}
			      }
			  }
			});
		
		 // insertion 
		
		String[] cn = table.getColumnsNames();
		Collections.reverse(Arrays.asList(cn));
		JPanel top = new JPanel();
		JButton insert = new JButton("Insérer une ligne");
		insert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Insertion(cn,table,ttt);
			}
		});
		top.add(insert);
		
		JScrollPane tableDataSp = new JScrollPane(tableData);
		
		selectPan.add("North",top);
		selectPan.add("Center",tableDataSp);
		
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.add("DESC",descPan);
		tabbedPane.add("SELECT",selectPan);
		container.add(tabbedPane);
		
		setContentPane(container);
		createFrame();
	}
	
	public void createFrame() {
		setModal(true);
		setTitle("Operation sur table");
		setSize(800,300);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(parent);
		setVisible(true);
	}
	
	void style(JTable table) {
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setHorizontalAlignment(SwingConstants.CENTER);
		table.setDefaultRenderer(Object.class, renderer);
	}
	
	void styleSelect(JTable table) {
		table.setModel(refresh());
		style(table);
		TableColumn column = table.getColumnModel().getColumn(getCols().length-1);
		DefaultTableCellRenderer red = new DefaultTableCellRenderer();
		red.setHorizontalAlignment(SwingConstants.CENTER);
		red.setForeground(Color.red);
		column.setCellRenderer(red);
	}
	
	public DefaultTableModel refresh() {
		
		table._init_DOM();
		String[][] d = table.getValues();
		String[] cn = table.getColumnsNames();
		
		Object[][] da = new Object[d.length][getCols().length];
		
		for (int i = 0; i < d.length; i++) {
			for (int j = 0; j < cn.length; j++) {
				da[i][j] = d[i][j];
				da[i][j+1] = "Supprimer";
			}
		}
		
		Collections.reverse(Arrays.asList(da));
		
		return new DefaultTableModel(da,getCols());
	}
	
	public String[] getCols() {
		String[] cls = table.getColumnsNames();
		String[] cols = new String[cls.length+1];
		Collections.reverse(Arrays.asList(cls));
		for (int i = 0; i < table.getColumnsNames().length; i++) {
			cols[i] = cls[i];
		}
		cols[table.getColumnsNames().length] = "Action";
		return cols;
	}
	
	public JTable getDataTable(){
		return tableData;
	}
}
