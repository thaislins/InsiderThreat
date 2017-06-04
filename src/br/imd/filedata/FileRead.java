package br.imd.filedata;

import java.io.BufferedReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FileRead {
	
	private HashMap<String,Data> deviceInfo;
	private ArrayList<String> fileInfo;
	private String csvFile;
	private BufferedReader reader;
	private String line;
	private String csvSplitBy;
	private String[] info;
	private Data data;
	
	public FileRead(String csvFile) {
		deviceInfo = new HashMap<>();
		fileInfo = new ArrayList<>();
		this.csvFile = csvFile;
		reader = null;
		line = "";
		csvSplitBy = ",";
		info = null;
		data = null;
		
	}
	
	public Data dataType(ArrayList<String> str) {		
		if(csvFile.equals("/home/thaislins/Documents/LP2/Eclipse Projects/InsiderThreat/files/device-reduced.csv"))
			return new Device(str.get(0),str.get(1),str.get(2),str.get(3),str.get(4));
		else if(csvFile.equals("/home/thaislins/Documents/LP2/Eclipse Projects/InsiderThreat/files/http-reduced.csv"))
			return new HTTP(str.get(0),str.get(1),str.get(2),str.get(3),str.get(4));
		else if (csvFile.equals("/home/thaislins/Documents/LP2/Eclipse Projects/InsiderThreat/files/ldap.csv"))
			return new LDAP(str.get(0),str.get(1),str.get(2),str.get(3),str.get(4));
		else 
			return new Logon(str.get(0),str.get(1),str.get(2),str.get(3),str.get(4));
	}
	
	
	public void read() throws IOException {
		reader = new BufferedReader(new FileReader(csvFile));
		ArrayList<Data> inputData = new ArrayList<>();
        String firstline = reader.readLine();
        String key;
        
        while ((line = reader.readLine()) != null) {

        	info = line.split(csvSplitBy);
        	
			for(String str : info) {
        		fileInfo.add(str);
			}
        	
        	data = dataType(fileInfo);
        	
        	if(data instanceof LDAP)
        		key = info[1];
        	else 
        		key = info[2].substring(5,12);
        		
        	//System.out.println(data.getId());
        	deviceInfo.put(key,data);
        	
        }
        
         System.out.println(deviceInfo.get("RES0962").getId());
	}

	public static void main(String args[]) {
		
		FileRead reader = new FileRead("/home/thaislins/Documents/LP2/Eclipse Projects/InsiderThreat/files/device-reduced.csv");
		try {
			reader.read();
        	
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}


