package org.java.dom;

import java.io.File;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

public class ValidatorXsd{
	
	private Validator validator;
	public ValidatorXsd(File schemaFile) {
		SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema;
		try {
			schema = factory.newSchema(schemaFile);
			validator = schema.newValidator();
		} catch (SAXException e) {
			e.printStackTrace();
		}
	}
	
	public boolean validate(Source source) {
		boolean isValid = true;
		try {
			validator.validate(source);
		} catch (SAXException | IOException e) {
			System.out.println("not valid");
			isValid = false;
		}
		return isValid;
	}
}
