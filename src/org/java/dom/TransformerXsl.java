package org.java.dom;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class TransformerXsl{
	
	Transformer transformer;
	public TransformerXsl(StreamSource xsl) {
		TransformerFactory tf = TransformerFactory.newInstance();
		try {
			transformer = tf.newTransformer(xsl);
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		}
	}
	public TransformerXsl() {
		TransformerFactory tf = TransformerFactory.newInstance();
		try {
			transformer = tf.newTransformer();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	public void transform(Source xmlSource,StreamResult outputTarget) {
		try {
			transformer.transform(xmlSource, outputTarget);
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
