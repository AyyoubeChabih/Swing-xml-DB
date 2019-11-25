package org.java.parsers;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.java.dom.MetaDataBuilder;
import org.java.dom.TransformerXsl;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.w3c.dom.UserDataHandler;
import org.xml.sax.SAXException;

public class DB extends File{
	
	private Vector<Table> tables;
	
	public DB(String name) {
		super(name);
		tables = new Vector<>();
		if(this.list() != null) {
			for (String table : this.list()) {
				tables.add(new Table(table,this.getAbsolutePath()));
			}
		}
	}

	public String[] getTables(){
		String[] tbs = new String[tables.size()];
		int i=0;
		for (Table table : tables) {
			tbs[i] = table.getName();
			i++;
		}
		return tbs;
	}
	
	public Table getTable(String name) {
		for (Table table : tables) {
			if(table.getName().equals(name)) {
				return table;
			}
		}
		return null;
	}
	//CREAT TABLE
	public Table createTable(String name, String[][] columns) {
		if(getTable(name) == null) {
			
			
			//CREATE METADATA
			MetaDataBuilder meta = new MetaDataBuilder(name, getName());
			
			for (int i = 0; i < columns.length; i++) {
				Vector<Element> vec = new Vector<>();
				vec.add(meta.newColChild("name",columns[i][0]));
				vec.add(meta.newColChild("type",columns[i][1]));
				meta.newCol(vec);
			}
			meta.save();
			//CREATE XSD
			TransformerXsl xsl = new TransformerXsl(new StreamSource("resources/createConstraints.xsl"));
			xsl.transform(new StreamSource(meta.getPath()), new StreamResult("MetaData/"+getName()+"/"+name+".xsd"));
			//PROCUDE TO CREATE TABLE
			Table table = new Table(name+".xml",getAbsolutePath());
				try {
					if(table.createNewFile()) {
						tables.add(table);
						
						Source xml = new StreamSource(new StringReader("<"+name+"s></"+name+"s>"));
						TransformerXsl tr = new TransformerXsl();
						tr.transform((StreamSource) xml, new StreamResult(table.getAbsolutePath()));
						
						System.out.println("table Created");
						return table;
					}
					System.out.println("table not created");
					return null;
				} catch (IOException e) {
					e.printStackTrace();
				};
		}
		System.out.println("table Allredy exists");
		return null;
	}
	
	
	public boolean removeTable(Table table) {
		if(table.delete()) {
			tables.remove(table);
			System.out.println("table"+table.getName()+" was deleted");
			return true;
		}
		System.out.println("error happened");
		return false;
	}
	
	public boolean removeAllTables() {
		for (Table table : tables) {
			if(!table.delete()) {
				return false;
			}
		}
		return true;
	}

}
