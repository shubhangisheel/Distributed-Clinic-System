package com.dds.sms.udpcommunication;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import com.dds.sms.server.ClinicServer;

public class UDPServer extends Communication implements Runnable{

	public ClinicServer server;
	public DatagramPacket serverpacket;

	/*Constructor*/
	public UDPServer(DatagramPacket packet, ClinicServer server) {
		serverpacket = packet;
		this.server = server;
		System.out.println("Debug: In CONSTRUCTOR : "+server.getLocation()+" and packet address: "+serverpacket.getAddress() + " and port number: "+ serverpacket.getPort());
	}

	/*Override run() method for threads*/
	public void run(){

		System.out.println("Debug: In UDP Server class of "+server.getLocation() + " and send function");
		System.out.println("MANAGER ka bawal: "+serverpacket.getAddress());

		/*Creating a socket*/
		DatagramSocket socketResult = null;

		/*Parsing integer to String to store into a message*/
		String message = Integer.toString(server.getNum_Of_Records());

		System.out.println("Debug: In UDP Server class: "+server.getLocation()+" message sent:  "+message);

		/*Creating a packet to store result*/
		byte[] bufResult = new byte[256];
		bufResult = message.getBytes();

		System.out.println("Debug: In UDP Server class: bufResult after conversion: "+bufResult+" Clinic server location: "+server.getLocation());

		System.out.println("Debug: In UDP Server class : "+server.getLocation()+" and packet address: "+serverpacket.getAddress() + " and port number: "+ serverpacket.getPort());

		DatagramPacket packetResult = new DatagramPacket(bufResult, message.length(), serverpacket .getAddress(), serverpacket .getPort());

		System.out.println("Debug: UDPS: sending packet to "+ serverpacket.getAddress() +" and port 0 "+serverpacket .getPort()+" from server "+server.getLocation());

		/*Sending packetResult to UDPClientServer*/
		try {
			socketResult = new DatagramSocket();
			socketResult.send(packetResult);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			socketResult.close();
		}
	}
}
