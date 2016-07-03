package com.dds.sms.main;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import com.dds.sms.server.ClinicServer;

public class MainClinic {

	public static void main(String[] args) {

		/*Creating objects of ClinicServer for 3 locations: Montreal (MTL), Laval (LVL) and DDO*/
		ClinicServer MTLserver;
		ClinicServer LVLserver;
		ClinicServer DDOserver;

		/*Creating Registry and assigning to respective objects*/
		try {
			LocateRegistry.createRegistry(5002) ;

			MTLserver = new ClinicServer("MTL",5002, 2525);
			LVLserver = new ClinicServer("LVL",5002, 3030);
			DDOserver = new ClinicServer("DDO",5002, 3535);

			/*Creating threads for above objects and starting*/
			Thread mtlThread = new Thread(MTLserver);
			Thread lvlThread = new Thread(LVLserver);
			Thread ddoThread = new Thread(DDOserver);

			mtlThread.start();
			lvlThread.start();
			ddoThread.start();

		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
