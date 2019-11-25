package org.java.main;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;

import org.java.parsers.DB;
import org.java.parsers.DBsParser;
import org.java.parsers.Table;
import org.xml.sax.SAXException;

public class engine {

	public engine() {
		try {
			test1();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void test1() throws SAXException {
		DBsParser dbP = new DBsParser("databases");
		DB  db=dbP.createDB("instance");
		
		Hashtable<String, String> hm = new Hashtable<>();
		  hm.put("100","Amit");  
		  hm.put("102","Ravi");  
		  hm.put("101","Vijay");  
		  hm.put("103","Rahul"); 
		try {
			try {
				db.createTable("movies", hm);
			} catch (TransformerConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException | ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

	public static void main(String[] args) {
		new engine();
	}

}
