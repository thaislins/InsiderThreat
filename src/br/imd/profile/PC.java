package br.imd.profile;

import java.util.ArrayList;

import br.imd.filedata.Device;
import br.imd.filedata.HTTP;
import br.imd.filedata.Logon;

public class PC {

	/*
	 * Array para cada tipo de arquivo lido já que todos contém informação sobre
	 * algum dispositivo (PC) e suas respectivas atividades e atributos
	 */
	private ArrayList<Device> deviceActivity;
	private ArrayList<Logon> logonActivity;
	private ArrayList<HTTP> httpActivity;

	public PC() {
		deviceActivity = new ArrayList<>();
		logonActivity = new ArrayList<>();
		httpActivity = new ArrayList<>();
	}

	public ArrayList<Device> getDeviceActivity() {
		return deviceActivity;
	}

	public void setDeviceActivity(ArrayList<Device> deviceActivity) {
		this.deviceActivity = deviceActivity;
	}

	public ArrayList<HTTP> getHttpActivity() {
		return httpActivity;
	}

	public void setHttpActivity(ArrayList<HTTP> httpActivity) {
		this.httpActivity = httpActivity;
	}

	public ArrayList<Logon> getLogonActivity() {
		return logonActivity;
	}

	public void setLogonActivity(ArrayList<Logon> logonActivity) {
		this.logonActivity = logonActivity;
	}
}
