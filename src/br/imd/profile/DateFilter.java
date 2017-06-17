package br.imd.profile;

import java.util.Date;
import java.util.Map.Entry;

import br.imd.filedata.Device;
import br.imd.filedata.HTTP;
import br.imd.filedata.Logon;

public class DateFilter {

	/*
	 * Cria um perfil de usuário de acordo com um intervalo de tempo definido
	 * pelo usário do programa, portanto filtra as informações já pertencentes
	 * ness perfil para as datas selecionadas
	 */
	public UserProfile choosePeriod(Date date1, Date date2, String userID) {

		UserProfile user = Database.users.get(userID);
		UserProfile userprofile = new UserProfile(user.getEmployee_name(), user.getUser_id(), user.getDomain(),
				user.getEmail(), user.getRole());

		for (PC pcs : user.getDevices().values()) {
			for (Device device : pcs.getDeviceActivity()) {
				if (date1.compareTo(device.getDate()) <= 0 && date2.compareTo(device.getDate()) >= 0) {
					if (!userprofile.getDevices().containsKey(device.getId())) {
						userprofile.getDevices().put(device.getId(), new PC());
					}
					userprofile.getDevices().get(device.getId()).getDeviceActivity().add(device);
				}
			}

			for (Logon logon : pcs.getLogonActivity()) {
				if (date1.compareTo(logon.getDate()) <= 0 && date2.compareTo(logon.getDate()) >= 0) {
					if (!userprofile.getDevices().containsKey(logon.getId())) {
						userprofile.getDevices().put(logon.getId(), new PC());
					}
					userprofile.getDevices().get(logon.getId()).getLogonActivity().add(logon);
				}
			}

			for (HTTP http : pcs.getHttpActivity()) {
				if (date1.compareTo(http.getDate()) <= 0 && date2.compareTo(http.getDate()) >= 0) {
					if (!userprofile.getDevices().containsKey(http.getId())) {
						userprofile.getDevices().put(http.getId(), new PC());
					}
					userprofile.getDevices().get(http.getId()).getHttpActivity().add(http);
				}
			}
		}
		userprofile.setFiltered(true);
		Database.users.put("f_" + userprofile.getUser_id(), userprofile);
		return userprofile;
	}

	/*
	 * Imprime perfil de usuário de acordo com um objeto de perfil e usuário
	 */
	public void printProfile(UserProfile userprofile) {
		System.out.println("User Information:");
		System.out.println("Name:" + userprofile.getEmployee_name());
		System.out.println("Id:" + userprofile.getUser_id());
		System.out.println("Domain:" + userprofile.getDomain());
		System.out.println("E-mail:" + userprofile.getEmail());
		System.out.println("Role:" + userprofile.getRole());

		System.out.println("\nDevices for:" + userprofile.getUser_id());
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
}
