package org.java.swing;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LabeledTextFields extends JPanel {

	private JTextField nomCol;
	private JComboBox types;
	
	public LabeledTextFields() 
	{
		setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel l1 = new JLabel("Nom colonne : ");
		add(l1);
		nomCol = new JTextField(10);
		add(nomCol);
		JLabel l2 = new JLabel("Type colonne : ");
		add(l2);
		String[] petStrings = { "number", "string", "date"};

		types = new JComboBox(petStrings);
		add(types);
	}
	
	
	public void setColumnNameValue(String value)
	{
		JTextField t1 = (JTextField)getComponent(1);
		t1.setText(value);
	}
	
	public String getColumnNameValue()
	{
		JTextField t1 = (JTextField)getComponent(1);
		return t1.getText();
	}
	
	
	public String getColumnTypeValue()
	{
		return types.getSelectedItem().toString();
	}

}
