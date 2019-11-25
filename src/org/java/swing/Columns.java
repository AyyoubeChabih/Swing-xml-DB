package org.java.swing;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.java.parsers.DB;
import org.java.parsers.Table;

public class Columns extends JDialog implements ActionListener {

	private JDialog parent;
	private int colsNbr;
	private JPanel container;
	private DB database;
	private String tableName;
	private Tables tb;
	
	public Columns(JDialog parent,int colsNbr, String tableName,DB database, Tables tb) {
		this.database = database;
		this.parent = parent;
		this.colsNbr = colsNbr;
		this.tableName = tableName;
		this.tb = tb;
		container = new JPanel();
		container.setLayout(new BoxLayout(container,BoxLayout.Y_AXIS));
		
		LabeledTextFields[] ltf = new LabeledTextFields[colsNbr];
		
		for(int i=0;i<colsNbr;i++)
		{
			ltf[i] = new LabeledTextFields();
			container.add(ltf[i]);
		}
		
		JButton btnSubmit = new JButton("Valider");
		btnSubmit.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnSubmit.addActionListener(this);
		container.add(btnSubmit);
		
		JScrollPane containerSp = new JScrollPane(container);
		
		setContentPane(containerSp);
		createFrame();
	}
	
	public void createFrame() {
		setModal(true);
		setTitle("Création des colonnes");
		pack();
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(parent);
		setVisible(true);
	}
	
	public boolean isValidForm()
	{
		for(int i=0;i<getComponentCount();i++)
		{
			try
			{
				LabeledTextFields ltf = (LabeledTextFields) getComponent(i);
				System.out.println("column : "+ltf.getColumnNameValue());
				if(ltf.getColumnNameValue().equals("") || ltf.getColumnTypeValue().equals(""))
				{
					return false;
				}
			}catch(Exception e)
			{
				continue;
			}
		}
		return true;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(isValidForm())
		{
			String[][] cols = new String[colsNbr][2];
			
			for (int i = 0; i < colsNbr; i++) {
				LabeledTextFields t1 = (LabeledTextFields)container.getComponent(i);
				cols[i][0] = t1.getColumnNameValue();
				cols[i][1] = t1.getColumnTypeValue();
			}
			Table t = database.createTable(tableName, cols);
			if(t != null) {
				JOptionPane.showMessageDialog(this, "Table Crée avec Succes");
				tb.getListeTables().setModel(tb.refresh());
				tb.style(tb.getListeTables());
				dispose();
			}else {
				JOptionPane.showMessageDialog(this, "Erreur lors de la création de la table");
			}
		}
	}

	
	
}
