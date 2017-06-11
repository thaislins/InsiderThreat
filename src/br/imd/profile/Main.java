package br.imd.profile;

import java.io.IOException;
import java.text.ParseException;

import br.imd.filedata.FileRead;

public class Main {

	private FileRead readerLDAP;
	private FileRead readerDevice;
	private FileRead readerLogon;
	private FileRead readerHttp;
	static final String DEVICE_FILE = System.getProperty("user.dir") + "/files/device-reduced.csv";
	static final String HTTP_FILE = System.getProperty("user.dir") + "/files/http-reduced.csv";
	static final String LDAP_FILE = System.getProperty("user.dir") + "/files/ldap.csv";
	static final String LOGON_FILE = System.getProperty("user.dir") + "/files/logon-reduced.csv";

	public void readFiles() {
		readerLDAP = new FileRead(LDAP_FILE);
		readerDevice = new FileRead(DEVICE_FILE);
		readerLogon = new FileRead(LOGON_FILE);
		readerHttp = new FileRead(HTTP_FILE);

		try {
			readerLDAP.read();
			readerDevice.read();
			readerLogon.read();
			readerHttp.read();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		Main t = new Main();
		t.readFiles();
	}
}
