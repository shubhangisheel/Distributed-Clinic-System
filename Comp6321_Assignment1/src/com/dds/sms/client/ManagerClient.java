package com.dds.sms.client;

import com.dds.sms.sharedfiles.*;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Calendar;

public class ManagerClient {

	private FileWriter logFile;
	static int count;

	public ManagerClient() {

		/*Message for manager logs*/
		String message = " Manager log created at " + Calendar.getInstance().getTime() + "\n" ;

		/*Creating manager logs*/

		String fileName = "Managerlog_"+count + ".txt" ;

		synchronized (this){
			count++;
		}

		try {
			logFile = new FileWriter ( fileName ) ;
			logFile.write(message + System.lineSeparator());
			logFile.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*Generating menu*/
	public void getMenu () {

		while ( true ) {

			/*Creating server object at client side*/
			ClinicServerInterface serverObj = null;

			try {
				/*Creating BufferedReader to take input*/
				InputStreamReader in = new InputStreamReader ( System.in );
				BufferedReader r = new BufferedReader ( in ) ;

				System.out.println( "Enter your ManagerID for authentication: " );
				String managerID = r.readLine();

				/*if else condition to check and create objects for respective locations*/
				if(managerID.contains("MTL")){
					serverObj = getRemoteObject("MTL");

				} else if(managerID.contains("LVL")){
					serverObj = getRemoteObject("LVL");

				} else if(managerID.contains("DDO")){
					serverObj = getRemoteObject("DDO");
				} 

				/*Calling managerAuthentication function to authenticate managers*/
				if(serverObj.managerAuthentication(managerID)){

					/*Menu once the manager is authorised to enter the system*/
					System.out.println("1. Create Doctor Record" );
					System.out.println("2. Create Nurse Record" );
					System.out.println("3. Edit Record" );
					System.out.println("4. Get total count of records");
					System.out.println("5. Exit");

					int choice = Integer.parseInt(r.readLine()) ;

					/*if else condition to call functions for respective choice*/
					if ( choice  == 1 ) {
						createDoctorMenu (serverObj) ;
					} else if ( choice == 2 ) {
						createNurseMenu(serverObj);
					} else if ( choice == 3 ){
						editRecordMenu(serverObj);
					} else if ( choice == 4 ){
						getCountMenu(serverObj);
					} else { 
						System.out.println( "You have entered a wrong choice. Please try again" ) ;
						continue ;
					}
					System.out.println("Press any key to continue:...");
					r.readLine() ;
				} else {
					System.out.println("Authentication Failed...Please try again");
				}
			} catch ( IOException e ) {
				System.out.println( e.getMessage() ) ;
				break ;
			} 
			catch ( NumberFormatException e ) {
				System.out.println( "You have entered a wrong choice. Please try again" ) ;
				continue ;
			}
		}
	}

	/*Function for choice 1*/
	public void createDoctorMenu(ClinicServerInterface serverObj) throws IOException{

		try{

			InputStreamReader in = new InputStreamReader ( System.in ) ;
			BufferedReader r = new BufferedReader ( in ) ;

			/*Menu for choice 1*/
			System.out.println("Enter firstname: ");
			String firstname = r.readLine () ;
			System.out.println("Enter lastname: ");
			String lastname = r.readLine () ;
			System.out.println("Enter address: ");
			String address = r.readLine () ;
			System.out.println(" Enter Clinic location: ");
			String location = r.readLine () ;
			System.out.println(" Enter Phone: ");
			String phone = r.readLine () ;
			System.out.println(" Enter Specialization: ");
			String specialization = r.readLine () ;

			if ( serverObj == null ) {
				System.out.println( "Sorry! the demanded service can't be provided at the moment" );
				System.out.println("Please try again later" );
			}

			/*Passing user input to Clinic Server createDoctorRecord*/
			boolean result;
			result = serverObj.createDRecord(firstname, lastname, address, phone, specialization, location);
			if( result ){
				System.out.println("Thanks! Doctor record is created  ");

				String message = "Doctor record with firstName "+ firstname + " was created at " +Calendar.getInstance().getTime() + "\n" ;
				writeLog(logFile, message);

			}
			else{
				String message = "Doctor record with firstName "+ firstname + " was not created at " +Calendar.getInstance().getTime() + "\n" ;
				writeLog(logFile, message);
				System.out.println(" Operation unsuccessful");
			}

		} catch ( RemoteException e ) {
			System.out.println("Your opeartion failed due to an error at the Clinic server");
			System.out.println ("Please try again") ;
		}catch ( IOException e ) {
			System.out.println("Your operation failed" + " " 
					+ e.getMessage());
		}
	}

	/*Function for choice 2*/
	public void createNurseMenu(ClinicServerInterface serverObj) throws IOException{

		try{

			InputStreamReader in = new InputStreamReader ( System.in ) ;
			BufferedReader r = new BufferedReader ( in ) ;

			/*Menu for choice 2*/
			System.out.println("Enter firstname: ");
			String firstname = r.readLine () ;
			System.out.println("Enter lastname: ");
			String lastname = r.readLine () ;
			System.out.println("Enter designation: ");
			String designation = r.readLine () ;
			System.out.println(" Enter Status: ");
			String status = r.readLine () ;
			System.out.println(" Enter Status_Date in format DD/MM/YYYY : ");
			String status_date = r.readLine () ;

			if ( serverObj == null ) {
				System.out.println( "Sorry! the demanded service can't be provided at the moment" );
				System.out.println("Please try again later" );
			}

			/*Passing user input to ClinicServer createNurseRecord*/
			boolean result;
			result = serverObj.createNRecord(firstname, lastname, designation, status_date, status);
			if( result ){

				String message = "Nurse record with firstName "+ firstname + " was created at " +Calendar.getInstance().getTime() + "\n" ;
				writeLog(logFile, message);

				System.out.println("Thanks! Nurse record is created  ");
			}
			else{

				String message = "Nurse record with firstName "+ firstname + " was not created at " +Calendar.getInstance().getTime() + "\n" ;
				writeLog(logFile, message);

				System.out.println(" Operation unsuccessful");
			}

		} catch ( RemoteException e ) {
			System.out.println("Your opeartion failed due to an error at the Clinic server");
			System.out.println ("Please try again") ;
		}catch ( IOException e ) {
			System.out.println("Your operation failed" + " " 
					+ e.getMessage());
		}
	}

	/*Function for choice 3*/
	public void editRecordMenu(ClinicServerInterface serverObj) throws IOException{

		try{

			InputStreamReader in = new InputStreamReader ( System.in ) ;
			BufferedReader r = new BufferedReader ( in ) ;

			/*Menu for choice 3*/
			System.out.println("Enter RecordID: ");
			String recordID = r.readLine () ;
			System.out.println("Enter fieldName: ");
			String fieldName = r.readLine () ;
			System.out.println("Enter newValue: ");
			String newValue = r.readLine () ;

			if ( serverObj == null ) {
				System.out.println( "Sorry! the demanded service can't be provided at the moment" );
				System.out.println("Please try again later" );
			}

			/*Passing input to edit record function*/
			boolean result;
			result = serverObj.editRecord(recordID, fieldName, newValue);
			if( result ){
				String message = "Record updated with "+ fieldName + newValue + "for "+recordID+" was updated at " +Calendar.getInstance().getTime() + "\n" ;
				writeLog(logFile, message);

				System.out.println("Thanks! Record is updated with new value ");
			}
			else{

				String message = "Record updated with "+ fieldName + newValue + "for "+recordID+" was NOT updated at " +Calendar.getInstance().getTime() + "\n" ;
				writeLog(logFile, message);

				System.out.println(" Operation unsuccessful");
			}

		} catch ( RemoteException e ) {
			System.out.println("Your opeartion failed due to an error at the Clinic server");
			System.out.println ("Please try again") ;
		}catch ( IOException e ) {
			System.out.println("Your operation failed" + " " 
					+ e.getMessage());
		}
	}

	/*Function for choice 4*/
	public void getCountMenu(ClinicServerInterface serverObj) throws IOException{

		try{

			InputStreamReader in = new InputStreamReader ( System.in ) ;
			BufferedReader r = new BufferedReader ( in ) ;

			/*Menu for choice 4*/
			System.out.println("Enter Record Type: ");
			String recordType = r.readLine () ;

			if ( serverObj == null ) {
				System.out.println( "Sorry! the demanded service can't be provided at the moment" );
				System.out.println("Please try again later" );
			}

			int result = -1;
			result = serverObj.getCount(recordType);
			if( result!= -1 ){

				String message = "Count was receieved "+ result+" at" +Calendar.getInstance().getTime() + "\n" ;
				writeLog(logFile, message);

				System.out.println("Total number of records in all location is: "+ result);
			}
			else{

				String message = "Count was NOT receieved "+ result+" at" +Calendar.getInstance().getTime() + "\n" ;
				writeLog(logFile, message);

				System.out.println(" Operation unsuccessful");
			}

		} catch ( RemoteException e ) {
			System.out.println("Your opeartion failed due to an error at the Clinic server");
			System.out.println ("Please try again") ;
		}catch ( IOException e ) {
			System.out.println("Your operation failed" + " " 
					+ e.getMessage());
		}
	}

	/*Function to get remote object reference from Registry*/
	public ClinicServerInterface getRemoteObject ( String location ) throws MalformedURLException {
		try {
			Registry r = LocateRegistry.getRegistry(5002) ;

			/*Displaying Registry objects*/
			String[] arr;
			arr= r.list();
			for(int i=0; i<arr.length;i++){
				System.out.println("Debug: Object registered are : "+ arr[i]);
			}

			ClinicServerInterface click = (ClinicServerInterface) r.lookup(location);
			return  click;	

		} catch ( RemoteException e ) {
			System.out.println("In Remote Catch");
			return null ;

		} catch ( NotBoundException e ){
			System.out.println("In NotBoundException Catch");
			return null ;
		}


	}
	public synchronized void writeLog ( FileWriter obj, String message ) {
		try {
			obj.write( message + System.lineSeparator() ) ;
			obj.flush();
		} catch ( IOException e ) {
			System.out.println( "Could not write the following string to log for manager"  + e.getMessage() ) ;
		}
	}
}

