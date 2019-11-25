package org.java.parsers;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Hashtable;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.java.dom.MDSaxParser;
import org.java.dom.TableBuilder;
import org.java.dom.TransformerXsl;
import org.java.dom.ValidatorXsd;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class Table extends File{

	
	private String name;
	private Element[] elements;
	private Hashtable<String, String> columns;
	private Node root;
	private Document document;
	
	public Table(String name,String path) {
		super(path+"/"+name);
		this.name = name.replace(".xml", "");
		MDSaxParser md = new MDSaxParser("MetaData/"+getParentFile().getName()+"/"+name);
		columns = md.getColumns();
	}
	public void _init_DOM() {
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder;
		try {
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
			try {
				document = documentBuilder.parse(this);
				root = document.getDocumentElement();
				NodeList nl = root.getChildNodes();
				elements = new Element[getLenght()];
				for (int i = 0; i < elements.length; i++) {
					if(nl.item(i).getNodeType() == Node.ELEMENT_NODE)
						elements[i] = (Element) nl.item(i);
				}
			} catch (SAXException | IOException e) {
				e.printStackTrace();
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	public int getLenght(){
		int lenght = 0;
		NodeList nl = root.getChildNodes();
		for (int i = 0; i < nl.getLength(); i++) {
			if(nl.item(i).getNodeType() == Node.ELEMENT_NODE)
				lenght++;
		}
		return lenght;
	}
	
	public String getName() {
		return name;
	}

	public void showColumns() {
		for(Map.Entry m:columns.entrySet()) {
			System.out.println(m.getKey());
			System.out.println(m.getValue());
		}
	}
	
	public String[][] getColumns(){
		String[][] cols = new String[columns.size()][2];
		int i=0;
		for(Map.Entry m:columns.entrySet()) {
			cols[i][0] = m.getKey().toString();
			cols[i][1] = m.getValue().toString();
			i++;
		}
		return cols;
	}
	
	public String[] getColumnsNames(){
		String[] cols = new String[columns.size()];
		int i=0;
		for(Map.Entry m:columns.entrySet()) {
			cols[i] = m.getKey().toString();
			i++;
		}
		return cols;
	}
	
	public boolean insert(String ...columnsData) {
		
		TransformerXsl tr = new TransformerXsl(new StreamSource("resources/createTable.xsl"));
		StreamResult src = new StreamResult(new StringWriter());
		tr.transform(new StreamSource("MetaData/"+getParentFile().getName()+"/"+name+".xml"), src);
		
		System.out.println(src.getWriter().toString());
		
		InputSource source = new InputSource(new StringReader(src.getWriter().toString()));
		TableBuilder tb = new TableBuilder(source);
		
		
		for (int i = 0; i < columnsData.length; i++) {
			tb.insertData(columnsData[i]);
		}
		
        DOMSource domSource = new DOMSource(tb.getDocument());
        
        File schemaFile = new File("MetaData/"+getParentFile().getName()+"/"+name+".xsd");
		ValidatorXsd validator = new ValidatorXsd(schemaFile);
        if(!validator.validate(domSource)) return false;
        
        _init_DOM();
        DOMSource sourceFin = new DOMSource(tb.insert(document));
        
        
        TransformerXsl tr2 = new TransformerXsl();
        tr2.transform(sourceFin, new StreamResult(getAbsolutePath()));
        return true;
	}
	
	public void removeElement(int index){
		
		root.removeChild(elements[index]);
		DOMSource source = new DOMSource(document);
		TransformerXsl tr = new TransformerXsl();
        tr.transform(source, new StreamResult(getAbsolutePath()));
		
		_init_DOM();
	}
	
	
	public boolean delete() {
		File meta = new File("MetaData/"+getParentFile().getName());
		File files[] = meta.listFiles();
		for (File file : files) {
			if(file.getName().contains(getName()))
				if(!file.delete())return false;
		}
		if(!super.delete())return false;
		return true;
	}

	public String[][] getValues(){
		String[][] data = new String[elements.length][columns.size()];
		for (int i = 0; i < elements.length; i++) {
			for (int j = 0; j < columns.size(); j++) {
				data[i][j] = elements[i].getChildNodes().item(j).getFirstChild().getNodeValue();
			}
		}
		return data;
	}
	
}
