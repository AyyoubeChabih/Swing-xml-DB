package org.java.dom;

import java.util.Hashtable;
import java.util.Vector;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MDSaxParser extends DefaultHandler{
	
	private Hashtable<String, String> columns;
	private String tag,key,value;
	public MDSaxParser(String source) {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setValidating(true);
		try {
			SAXParser parser = factory.newSAXParser();
			parser.parse(source, this);
		} catch (Exception e) {
			System.out.println("erreur "+e.getMessage());
		}
	}
	
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		tag = qName;
		if(qName.equals("table")) {
			columns = new Hashtable<>();
		}
	}
	
	public void characters(char[] ch, int start, int length) throws SAXException {
		if("name".equals(tag)) {
			key = new String(ch, start, length);
		}else if("type".equals(tag)) {
			value = new String(ch, start, length);
		}
		
	}
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if(qName.equals("column")) {
			columns.put(key, value);
		}
		tag = null;
	}

	public Hashtable<String, String> getColumns() {
		return columns;
	}
	

}
