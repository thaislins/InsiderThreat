package br.imd.profile;

import br.imd.filedata.Device;
import br.imd.filedata.HTTP;
import br.imd.filedata.LDAP;
import br.imd.filedata.Logon;

public class ProfileBuilder {

	public void addToDatabase(LDAP ldap) {
		UserProfile userprofile = new UserProfile(ldap.getEmployee_name(), ldap.getUser_id(), ldap.getDomain(),
				ldap.getEmail(), ldap.getRole());
		Database.users.put(userprofile.getId(), userprofile);
	}

	public void addToDatabase(Device device) {
		if (Database.users.containsKey(device.getUser())) {
			UserProfile userprofile = Database.users.get(device.getUser());
			if (!userprofile.getDevices().containsKey(device.getId())) {
				userprofile.getDevices().put(device.getId(), new PC());
			}
			userprofile.getDevices().get(device.getId()).getDeviceActivity().add(device);
		}
	}

	public void addToDatabase(Logon logon) {
		if (Database.users.containsKey(logon.getUser())) {
			UserProfile userprofile = Database.users.get(logon.getUser());
			if (!userprofile.getDevices().containsKey(logon.getId())) {
				userprofile.getDevices().put(logon.getId(), new PC());
			}
			userprofile.getDevices().get(logon.getId()).getLogonActivity().add(logon);
		}
	}

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
