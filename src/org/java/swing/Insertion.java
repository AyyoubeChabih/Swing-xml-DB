package org.java.swing;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.java.parsers.Table;

public class Insertion extends JDialog{

	public Insertion(String[] cn, Table table, TablesOperations tab) {
		JPanel container = new JPanel();
		container.setLayout(new BoxLayout(container,BoxLayout.Y_AXIS));
		for (int i = 0; i < cn.length; i++) {
			JLabel lbNomCol = new JLabel(cn[i]+" : ");
			JTextField tf = new JTextField(10);
			container.add(lbNomCol);
			container.add(tf);
		}
		JButton btnSubmit = new JButton("Valider");
		btnSubmit.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		
		btnSubmit.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				String[] data = new String[cn.length];
				int j = 1;
				for (int i = 0; i < cn.length; i++) {
					JTextField t1 = (JTextField)container.getComponent(j);
					j += 2;
					data[i] = t1.getText();
				}
				if(table.insert(data)) {
			    	tab.getDataTable().setModel(tab.refresh());
			    	tab.styleSelect(tab.getDataTable());
				}else {
					JOptionPane.showMessageDialog(tab, "Les données ne sont pas valid !!");
				}
			}
		});
		
		container.add(btnSubmit);
		JScrollPane containerSp = new JScrollPane(container);
		
		setContentPane(containerSp);
		createFrame((JDialog)tab);
	}

	public void createFrame(JDialog tab) {
		setModal(true);
		setTitle("Création des colonnes");
		pack();
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(tab);
		setVisible(true);
	}
	
}
