package org.java.dom;

import java.io.IOException;
import java.io.StringReader;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class MetaDataBuilder {
	
	private String path;
	private Document document;
	private Element root;
	public MetaDataBuilder(String table,String db) {
		path="MetaData/"+db+"/"+table+".xml";
		
		Source xml = new StreamSource(new StringReader("<table></table>"));
		TransformerXsl tr = new TransformerXsl();
		tr.transform((StreamSource) xml, new StreamResult(path));
		
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder;
		
		try {
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
			try {
			document = documentBuilder.parse(path);
			root = document.getDocumentElement();
			setAtt(table);
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	public void setAtt(String name) {
		Attr col_name = document.createAttribute("name");
		col_name.setValue(name);
		root.setAttributeNode(col_name);
	}
	
	public void newCol(Vector<Element> elements) {
		Element column = document.createElement("column");
		for (Element element : elements) {
			column.appendChild(element);
		}
		root.appendChild(column);
	}
	public Element newColChild(String name,String text) {
		Element colN = document.createElement(name);
		colN.appendChild(document.createTextNode(text));
		return colN;
	}
	
	public void save() {
		TransformerXsl xsl = new TransformerXsl();
		DOMSource source = new DOMSource(document);
		xsl.transform(source, new StreamResult(path));
	}

	public String getPath() {
		return path;
	}

}
