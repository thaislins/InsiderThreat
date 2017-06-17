package br.imd.profile;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map.Entry;

import br.imd.filedata.Device;
import br.imd.filedata.FileRead;
import br.imd.filedata.HTTP;
import br.imd.filedata.Logon;

public class Main {

	private FileRead readerLDAP;
	private FileRead readerDevice;
	private FileRead readerLogon;
	private FileRead readerHttp;
	static final String DEVICE_FILE = System.getProperty("user.dir") + "/files/device-reduced.csv";
	static final String HTTP_FILE = System.getProperty("user.dir") + "/files/http-reduced.csv";
	static final String LDAP_FILE = System.getProperty("user.dir") + "/files/ldap.csv";
	static final String LOGON_FILE = System.getProperty("user.dir") + "/files/logon-reduced.csv";

	public void printProfile(String userID) {

		UserProfile userprofile = Database.users.get(userID);

		System.out.println("User Information:");
		System.out.println("Name:" + userprofile.getEmployee_name());
		System.out.println("Id:" + userprofile.getUser_id());
		System.out.println("Domain:" + userprofile.getDomain());
		System.out.println("E-mail:" + userprofile.getEmail());
		System.out.println("Role:" + userprofile.getRole());

		System.out.println("\nDevices for:" + userprofile.getEmployee_name());
		for (String pcKey : userprofile.getDevices().keySet()) {
			System.out.println("Device Id:" + pcKey);

		}

		for (Entry<String, PC> pc : userprofile.getDevices().entrySet()) {
			System.out.println("\nDevice Activies for:" + pc.getKey());

			for (Device device : pc.getValue().getDeviceActivity()) {
				System.out.println("Activity: " + device.getActivity());
			}
			for (Logon logon : pc.getValue().getLogonActivity()) {
				System.out.println("Activity: " + logon.getActivity());
			}
			for (HTTP http : pc.getValue().getHttpActivity()) {
				System.out.println("Activity: " + http.getActivity() + " Atributte: " + http.getUrl());
			}

		}
	}

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

	public static void main(String args[]) throws ParseException {
		Main t = new Main();
		t.readFiles();
		t.printProfile("ACD0647");
		// DateFilter date = new DateFilter();
		//
		// DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		// Date date1 = df.parse("01/04/2009 13:16:32");
		// Date date2 = df.parse("01/06/2011 07:26:39");
		//
		// UserProfile user = date.chooseDate(date1, date2, "ACD0647");
		// // date.printProfile(user);
	}
}
