package br.imd.filedata;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class FileRead {

	private HashMap<String, ArrayList<Data>> deviceInfo;
	private ArrayList<String> fileInfo;
	private Data data;
	private String csvFile;
	private BufferedReader reader;
	private String line;
	private String csvSplitBy;
	private String[] info;
	static final String DEVICE_FILE = System.getProperty("user.dir") + "/files/device-reduced.csv";
	static final String HTTP_FILE = System.getProperty("user.dir") + "/files/http-reduced.csv";
	static final String LDAP_FILE = System.getProperty("user.dir") + "/files/ldap.csv";
	static final String LOGON_FILE = System.getProperty("user.dir") + "/files/logon-reduced.csv";

	public HashMap<String, ArrayList<Data>> getDeviceInfo() {
		return deviceInfo;
	}

	public void setDeviceInfo(HashMap<String, ArrayList<Data>> deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public FileRead(String csvFile) {
		deviceInfo = new HashMap<>();
		fileInfo = new ArrayList<>();
		this.csvFile = csvFile;
		reader = null;
		line = null;
		csvSplitBy = ",";
		info = null;
		data = null;
	}

	public Data dataType(ArrayList<String> str) throws ParseException {
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		if (csvFile.equals(DEVICE_FILE))
			return new Device(str.get(0), df.parse(str.get(1)), str.get(2), str.get(3), str.get(4));
		else if (csvFile.equals(HTTP_FILE))
			return new HTTP(str.get(0), df.parse(str.get(1)), str.get(2), str.get(3), str.get(4));
		else if (csvFile.equals(LDAP_FILE))
			return new LDAP(str.get(0), str.get(1), str.get(2), str.get(3), str.get(4));
		else
			return new Logon(str.get(0), df.parse(str.get(1)), str.get(2), str.get(3), str.get(4));
	}

	public void read() throws IOException, ParseException {
		reader = new BufferedReader(new FileReader(csvFile));
		String firstLine = reader.readLine();
		String key;

		while ((line = reader.readLine()) != null) {

			fileInfo = new ArrayList<>(Arrays.asList(line.split(csvSplitBy)));
			data = dataType(fileInfo);

			if (data instanceof LDAP)
				key = fileInfo.get(1);
			else
				key = fileInfo.get(2).replace("DTAA/", "");

			if (!deviceInfo.containsKey(key)) {
				deviceInfo.put(key, new ArrayList<Data>());
			}
			deviceInfo.get(key).add(data);
		}
		System.out.println(deviceInfo.get("RES0962").get(0).getDate());
	}
}
