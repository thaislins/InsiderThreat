package br.imd.anomalies;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Map.Entry;

import br.imd.filedata.Device;
import br.imd.filedata.HTTP;
import br.imd.filedata.Logon;
import br.imd.profile.Database;
import br.imd.profile.PC;
import br.imd.profile.UserProfile;

/**
 * Classe que realiza a detecção de usuários anômalos
 */
public class Threats {

	private ArrayList<Double> arrAverage;
	private ArrayList<Double> arrDeviation;
	private ArrayList<UserProfile> arrSuspects;
	private Calendar cal;
	private double totalAverage;
	private double totalDeviation;

	/**
	 * Construtor Padrão
	 */
	public Threats() {
		arrAverage = new ArrayList<>(Collections.nCopies(24, 0.0));
		arrDeviation = new ArrayList<>(Collections.nCopies(24, 0.0));
		arrSuspects = new ArrayList<>();
		totalAverage = 0.0;
		totalDeviation = 0.0;
	}

	/**
	 * Realiza a conta do número de atividades de cada usuário para cada hora do
	 * dia e as adiciona a um array de atividades que é atributo da classe
	 * UserProfile
	 */
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

	/**
	 * Define o que é uma atividade suspeita para cada usuário. O critério
	 * estabelecido foi realizar a média dos acessos de cada hora e após isso
	 * pegar a média geral das horas, realizando-se um cálculo de desvio padrão
	 * com esses valores
	 */
	public void defineThreat() {

		for (UserProfile userprofile : Database.users.values()) {

			// Soma contagem de acessos por hora
			for (int i = 0; i < userprofile.getArrActivities().size(); i++) {
				arrAverage.set(i, userprofile.getArrActivities().get(i) + arrAverage.get(i));

			}
		}

		// Calcula média da contagem de acessos
		for (int i = 0; i < arrAverage.size(); i++) {
			arrAverage.set(i, arrAverage.get(i) / Database.users.size());
		}

		// Calcula média das médias de acessos
		for (int i = 9; i < arrAverage.size(); i++) {
			totalAverage += arrAverage.get(i);
		}
		totalAverage /= arrAverage.size();

		// Calcula Desvios
		for (int i = 0; i < arrDeviation.size(); i++) {
			arrDeviation.set(i, arrAverage.get(i) - totalAverage);
		}

		// // Soma Quadrados
		for (int i = 0; i < arrDeviation.size(); i++) {
			totalDeviation += Math.pow(arrDeviation.get(i), 2);
		}
		totalDeviation /= arrDeviation.size();
		totalDeviation = Math.sqrt(totalDeviation);
	}

	/**
	 * Calcula a média de acessos de cada usuário e após isso verifica quais
	 * usuários possuem uma média de acesso maior do que a soma da média geral
	 * de acessos com o desvio padrão de acessos, em caso positivo esses
	 * usuários serão considerados suspeitos/ameaças. Os usuários suspeitos
	 * serão imprimidos em um arquivo
	 */
	public void detect() throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter(System.getProperty("user.dir") + "/logs/threats.txt");

		for (UserProfile userprofile : Database.users.values()) {
			for (int i = 0; i < 24; i++) {
				userprofile.setAverage(userprofile.getAverage() + userprofile.getArrActivities().get(i));
			}
			userprofile.setAverage(userprofile.getAverage() / 24);

			if (userprofile.getAverage() > totalAverage + totalDeviation) {
				arrSuspects.add(userprofile);
			}
		}

		writer.println("Number of Suspected Users:" + arrSuspects.size());

		for (int i = 0; i < arrSuspects.size(); i++) {
			writer.println("\nName: " + arrSuspects.get(i).getEmployee_name());
			writer.println("Id: " + arrSuspects.get(i).getUser_id());
			writer.println("Domain: " + arrSuspects.get(i).getDomain());
			writer.println("E-mail: " + arrSuspects.get(i).getEmail());
			writer.println("Role: " + arrSuspects.get(i).getRole());
			writer.println("Average activity count: " + arrSuspects.get(i).getAverage());
		}
		System.out.println("Printed to file");
		writer.close();
	}
}
