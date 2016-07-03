package com.dds.sms.main;

import com.dds.sms.client.ManagerClient;

public class MainClient {

	public static void main(String[] args) {

		/*Creating Manager Client object to call its getMenu()*/
		ManagerClient a = new ManagerClient() ;
		a.getMenu();
	}

}
