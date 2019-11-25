package org.java.dom;

import java.io.IOException;
import java.util.Hashtable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class TableBuilder {
	
	private Document document;
	private Element root;
	private Node current;
	
	public TableBuilder(InputSource src) {
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder;
		try {
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
			try {
				document = documentBuilder.parse(src);
				root = document.getDocumentElement();
				current = root.getFirstChild();
				findElement();
			} catch (SAXException | IOException e) {
				e.printStackTrace();
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	
	public Document getDocument() {
		return document;
	}

	public void findElement(){
		while(current.getNodeType() != Node.ELEMENT_NODE) {
			current = current.getNextSibling();
		}
	}
	
	public void insertData(String data) {
		Text text = document.createTextNode(data);
		current.appendChild(text);
		current = current.getNextSibling();
		if(current != null)
			findElement();
	}
	public Document insert(Document doc) {
		Node rt = doc.getDocumentElement();
		System.out.println(rt.getNodeName());
		Node parent = doc.createElement(root.getNodeName());
		current = root.getFirstChild();
		while(current != null) {
			findElement();
			Node child = doc.createElement(current.getNodeName());
			child.appendChild(doc.createTextNode(current.getTextContent()));
			parent.appendChild(child);
			current = current.getNextSibling();
		}
		rt.appendChild(parent);
		return doc;
	}
	
	

	public Element getRoot() {
		return root;
	}

}
