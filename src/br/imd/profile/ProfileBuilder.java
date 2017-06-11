package br.imd.profile;

import br.imd.filedata.Device;
import br.imd.filedata.HTTP;
import br.imd.filedata.LDAP;
import br.imd.filedata.Logon;

public class ProfileBuilder {

	private PC pcs;

	public void addToDatabase(LDAP ldap) {
		Database.users.put(ldap.getId(), (UserProfile) ldap);
	}

	public void addToDatabase(Device device) {
		if (Database.users.containsKey(device.getUser())) {
			UserProfile userprofile = Database.users.get(device.getUser());

			if (Database.users.get(device.getUser()).getDevices().containsKey(device.getId())) {
				pcs = Database.users.get(device.getUser()).getDevices().get(device.getId());
			} else {
				Database.users.get(device.getUser()).getDevices().put(device.getId(), pcs);
			}

		}

	}

	public void addToDatabase(Logon logon) {

	}

	public void addToDatabase(HTTP http) {

	}
}
