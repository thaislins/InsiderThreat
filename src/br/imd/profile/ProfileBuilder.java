package br.imd.profile;

import br.imd.filedata.Device;
import br.imd.filedata.HTTP;
import br.imd.filedata.LDAP;
import br.imd.filedata.Logon;

public class ProfileBuilder {

	/*
	 * Adiciona informações lidas do arquivo LDAP ao HashMap "users" sendo a
	 * chave o Id do usuário e o valor um objeto de Perfil de Usuário
	 * 
	 */
	public void addToDatabase(LDAP ldap) {
		UserProfile userprofile = new UserProfile(ldap.getEmployee_name(), ldap.getUser_id(), ldap.getDomain(),
				ldap.getEmail(), ldap.getRole());
		Database.users.put(userprofile.getUser_id(), userprofile);
	}

	/*
	 * Verifica se o HashMap de usuários contém o usuário procurado de acordo
	 * com a chave do Device. Caso exista pegam-se as informações acerca desse
	 * perfil, após isso verifica se há um dispositivo com a chave procurada
	 * dentro da estrutura e insere um novo despositivo constituído de
	 * informações da atividade de Connected-Disconnected
	 */
	public void addToDatabase(Device device) {
		if (Database.users.containsKey(device.getUser())) {
			UserProfile userprofile = Database.users.get(device.getUser());
			if (!userprofile.getDevices().containsKey(device.getId())) {
				userprofile.getDevices().put(device.getId(), new PC());
			}
			userprofile.getDevices().get(device.getId()).getDeviceActivity().add(device);
		}
	}

	/*
	 * Verifica se o HashMap de usuários contém o usuário procurado de acordo
	 * com a chave do Logon. Caso exista pegam-se as informações acerca desse
	 * perfil, após isso verifica se há um dispositivo com a chave procurada
	 * dentro da estrutura e insere um novo despositivo constituído de
	 * informações da atividade de Logon-Logoff
	 */
	public void addToDatabase(Logon logon) {
		if (Database.users.containsKey(logon.getUser())) {
			UserProfile userprofile = Database.users.get(logon.getUser());
			if (!userprofile.getDevices().containsKey(logon.getId())) {
				userprofile.getDevices().put(logon.getId(), new PC());
			}
			userprofile.getDevices().get(logon.getId()).getLogonActivity().add(logon);
		}
	}

	/*
	 * Verifica se o HashMap de usuários contém o usuário procurado de acordo
	 * com a chave do HTTP. Caso exista pegam-se as informações acerca desse
	 * perfil, após isso verifica se há um dispositivo com a chave procurada
	 * dentro da estrutura e insere um novo despositivo constituído de
	 * informações dos atributos (as urls acessadas) de HTTP
	 */
	public void addToDatabase(HTTP http) {
		if (Database.users.containsKey(http.getUser())) {
			UserProfile userprofile = Database.users.get(http.getUser());
			if (!userprofile.getDevices().containsKey(http.getId())) {
				userprofile.getDevices().put(http.getId(), new PC());
			}
			userprofile.getDevices().get(http.getId()).getHttpActivity().add(http);
		}
	}
}
