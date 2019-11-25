package org.java.parsers;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Node;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class TablesSAXParser extends DefaultHandler{
	
	private Document document;
	public TablesSAXParser(String source) {
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			try {
				document = documentBuilder.parse(source);
			} catch (SAXException | IOException e) {
				e.printStackTrace();
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		};
	}
	public Document getDocument() {
		return document;
	}
	
	public Element getElement(String name) {
		Element root = document.getDocumentElement();
		NodeList list = root.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node item = (Node) list.item(i);
			if(item.getNodeName().equals("table"))
				if(findItem(item,name))return (Element) item;
		}
		return null;
	}
	public boolean findItem(Node item,String name) {
		NodeList children = item.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node childe = (Node) children.item(i);
			if(childe.getNodeName().equals("name"))
				if(childe.getFirstChild().getNodeValue().equals(name))
					return true;
		}
		return false;
	}
	

}
