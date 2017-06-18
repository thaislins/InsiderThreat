package br.imd.anomalies;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Map.Entry;

import br.imd.filedata.Device;
import br.imd.filedata.HTTP;
import br.imd.filedata.Logon;
import br.imd.profile.Database;
import br.imd.profile.Main;
import br.imd.profile.PC;
import br.imd.profile.UserProfile;

public class Threats {

	private Calendar cal;
	private int hour;
	private ArrayList<Double> arrAverage;
	private ArrayList<Double> stdDeviation;

	public Threats() {
		arrAverage = new ArrayList<>(Collections.nCopies(24, 0.0));
		stdDeviation = new ArrayList<>(Collections.nCopies(24, 0.0));
	}

	public void getActivityCount() {

		for (UserProfile userprofile : Database.users.values()) {

			for (Entry<String, PC> pc : userprofile.getDevices().entrySet()) {

				for (Device device : pc.getValue().getDeviceActivity()) {
					cal = Calendar.getInstance();
					cal.setTime(device.getDate());
					int hour = cal.get(Calendar.HOUR_OF_DAY);
					userprofile.getArrActivities().set(hour, userprofile.getArrActivities().get(hour) + 1);
				}
				for (Logon logon : pc.getValue().getLogonActivity()) {
					cal = Calendar.getInstance();
					cal.setTime(logon.getDate());
					int hour = cal.get(Calendar.HOUR_OF_DAY);
					userprofile.getArrActivities().set(hour, userprofile.getArrActivities().get(hour) + 1);
				}
				for (HTTP http : pc.getValue().getHttpActivity()) {
					cal = Calendar.getInstance();
					cal.setTime(http.getDate());
					int hour = cal.get(Calendar.HOUR_OF_DAY);
					userprofile.getArrActivities().set(hour, userprofile.getArrActivities().get(hour) + 1);
				}
			}
		}
	}

	public void detect() {

		double totalAverage = 0.0;
		double totalDesvio = 0.0;

		for (UserProfile userprofile : Database.users.values()) {

			// Soma contagem de acessos por hora
			for (int i = 0; i < userprofile.getArrActivities().size(); i++) {
				arrAverage.set(i, userprofile.getArrActivities().get(i) + arrAverage.get(i));

			}
		}

		// Calcula média da contagem de acessos
		for (int i = 9; i < arrAverage.size(); i++) {
			arrAverage.set(i, arrAverage.get(i) / Database.users.size());
		}

		for (int i = 9; i < arrAverage.size(); i++) {
			System.out.println(arrAverage.get(i));
		}

		// Calcula média das médias de acessos
		for (int i = 9; i < arrAverage.size(); i++) {
			totalAverage += arrAverage.get(i);
		}
		totalAverage /= arrAverage.size();

		System.out.println(Database.users.size());
		System.out.println(totalAverage);

		// Calcula Desvios
		for (int i = 0; i < stdDeviation.size(); i++) {
			stdDeviation.set(i, arrAverage.get(i) - totalAverage);
		}

		// Soma Quadrados
		for (int i = 0; i < stdDeviation.size(); i++) {
			totalDesvio += Math.pow(stdDeviation.get(i), 2);
		}
		totalDesvio = Math.sqrt(totalDesvio);
		totalDesvio /= stdDeviation.size();
		System.out.println(totalDesvio);

	}

	public static void main(String args[]) {

		Main t = new Main();
		t.readLDAP();
		t.readFiles("Device");
		t.readFiles("Logon");
		t.readFiles("Http");

		Threats t1 = new Threats();

		t1.getActivityCount();
		t1.detect();
	}
}
