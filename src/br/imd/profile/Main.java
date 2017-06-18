package br.imd.profile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Map.Entry;

import br.imd.filedata.Device;
import br.imd.filedata.FileRead;
import br.imd.filedata.HTTP;
import br.imd.filedata.Logon;
import br.imd.gui.UserVisualization;

/**
 * Classe responsável pelo funcionamento do programa
 */
public class Main {

	private FileRead readerLDAP;
	private FileRead readerFile;
	private PrintWriter writer;
	static final String DEVICE_FILE = System.getProperty("user.dir") + "/files/device.csv";
	static final String HTTP_FILE = System.getProperty("user.dir") + "/files/http-reduced.csv";
	static final String LDAP_FILE = System.getProperty("user.dir") + "/files/ldap.csv";
	static final String LOGON_FILE = System.getProperty("user.dir") + "/files/logon-reduced.csv";

	/**
	 * Função que realiza a impressão de informações do usuário selecionado em
	 * um arquivo
	 * 
	 * @param userId
	 */
	public void printProfile(String userId) throws FileNotFoundException, UnsupportedEncodingException {

		PrintWriter writer = new PrintWriter(System.getProperty("user.dir") + "/users/" + userId + ".txt", "UTF-8");

		UserProfile userprofile = Database.users.get(userId);

		writer.println("User Information:");
		writer.println("Name:" + userprofile.getEmployee_name());
		writer.println("Id:" + userprofile.getUser_id());
		writer.println("Domain:" + userprofile.getDomain());
		writer.println("E-mail:" + userprofile.getEmail());
		writer.println("Role:" + userprofile.getRole());

		writer.println("\nDevices for:" + userprofile.getEmployee_name());
		for (String pcKey : userprofile.getDevices().keySet()) {
			writer.println("Device Id:" + pcKey);

		}

		for (Entry<String, PC> pc : userprofile.getDevices().entrySet()) {
			writer.println("\nDevice Activies for:" + pc.getKey());

			for (Device device : pc.getValue().getDeviceActivity()) {
				writer.println("Activity: " + device.getActivity());
			}
			for (Logon logon : pc.getValue().getLogonActivity()) {
				writer.println("Activity: " + logon.getActivity());
			}
			for (HTTP http : pc.getValue().getHttpActivity()) {
				writer.println("Activity: " + http.getActivity() + " Atributte: " + http.getUrl());
			}

		}
		writer.close();
	}

	/**
	 * Função que leitura de arquivo de usuários
	 */
	public void readLDAP() {
		readerLDAP = new FileRead(LDAP_FILE);
		try {
			readerLDAP.read();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Função que realiza leitura de arquivos do programa
	 * 
	 * @param file
	 */
	public void readFiles(String file) {

		if (file.equals("Device")) {
			readerFile = new FileRead(DEVICE_FILE);
		} else if (file.equals("Logon")) {
			readerFile = new FileRead(LOGON_FILE);
		} else {
			readerFile = new FileRead(HTTP_FILE);
		}

		try {
			readerFile.read();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Função que realiza operação do programa
	 */
	public static void main(String args[]) {
		UserVisualization frame = new UserVisualization();
		frame.setVisible(true);
	}
}
