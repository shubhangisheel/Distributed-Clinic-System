package com.dds.sms.sharedfiles;

import java.rmi.Remote;
import java.rmi.RemoteException;

/*ClinicServerInterface for client and server*/
public interface ClinicServerInterface extends Remote{

	public boolean managerAuthentication(String managerID) throws RemoteException;
	
	public boolean createDRecord (String fName, String lName, String add, String phn, String spclztn, String loc)throws RemoteException;
	
	public boolean	createNRecord (String fName, String lName, String desig,String stat_Date, String stat)throws RemoteException;
	
	public boolean editRecord (String recordID, String fieldName, String newValue)throws RemoteException;
	
	public int getCount(String recordType)throws RemoteException;
	
	public boolean transferRecord(String managerID,String recordID, String location)throws RemoteException;
}
