package br.imd.profile;

import java.util.Date;

import br.imd.filedata.Device;
import br.imd.filedata.HTTP;
import br.imd.filedata.Logon;

/**
 * Classe responsável pela filtragem da data de acorco com período estabelecido
 * pelo usuário
 */
public class DateFilter {

	/**
	 * Cria um perfil de usuário de acordo com um intervalo de tempo definido
	 * pelo usário do programa, portanto filtra as informações já pertencentes
	 * ness perfil para as datas selecionadas e adiciona isso a database de
	 * usuários com uma identificação de usuário
	 * 
	 * @param date1
	 * @param date2
	 * @param userId
	 * @return userprofile
	 */
	public UserProfile choosePeriod(Date date1, Date date2, String userId) {

		UserProfile user = Database.users.get(userId);
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
		userprofile.dateString(date1, date2);
		Database.users.put("f_" + userprofile.getUser_id(), userprofile);
		return userprofile;
	}

}
