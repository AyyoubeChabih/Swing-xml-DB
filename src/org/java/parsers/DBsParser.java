package org.java.parsers;

import java.io.File;
import java.util.Vector;


//hada li gaydoz 3la Base donyat (GENERATOR OF DATABASES)
public class DBsParser {
	
	private File parent;
	private Vector<DB> DBs;
	private String path;
	public DBsParser(String path) {
		this.path = path;
		parent = new File(path);
		DBs = new Vector<>();
		
		if(parent.list() != null)
			for (String file : parent.list()) {
				DBs.add(new DB(path+"/"+file));
			}
		
	}
	
	public DB createDB(String name) {
		if(getDB(name) == null) {
			
			DB db = new DB(path+"/"+name);
			DB meta = new DB("MetaData/"+name);
			if(db.mkdir() && meta.mkdir()) {
				DBs.add(db);
				return db;
			}
			System.out.println("Sorry, Some problems happened");
			return null;
		}
		
		System.out.println("DB already exists");
		return null;
	}
	
	public String[] getDBs(){
		String[] dbs = new String[DBs.size()];
		int i=0;
		for (DB db : DBs) {
			dbs[i] = db.getName();
			i++;
		}
		return dbs;
	}
	
	public boolean removeDB(DB db) {
		File meta = new File("MetaData/"+db.getName());
		if(!db.removeAllTables())return false;
		if(db.delete() && meta.delete()) {
			DBs.remove(db);
			System.out.println("db deleted");
			return true;
		}
		System.out.println("error happened");
		return false;
	}
	
	public DB getDB(String name) {
		
		for (DB db : DBs) {
			if(db.getName().equals(name)) {
				return db;
			}
		}
		return null;
	}
}
