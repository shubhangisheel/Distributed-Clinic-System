package com.dds.sms.udpcommunication;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import com.dds.sms.server.ClinicServer;

public class UDPClientServer extends Communication implements Runnable {

	private ClinicServer server;
	private DatagramSocket socketRequest;
	private int resultGetCount; 

	/*Constructor*/
	public UDPClientServer(ClinicServer server) {
		this.server = server;
		resultGetCount = 0;

		/*Creating a socketRequest to send and receive packets*/
		try {
			socketRequest = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	/*Getters and Setters*/
	public int getResultGetCount() {
		return resultGetCount;
	}

	public void setResultGetCount(int resultGetCount) {
		this.resultGetCount = resultGetCount;
	}

	/*Override run() for Threads*/
	public void run(){
		send();
		resultGetCount= receive();	
	}

	/*Send function to send the packet to the other 2 UDPServers*/
	public void send(){

		System.out.println("Debug: In UDP Client Server class of "+server.getLocation() + " and send function");

		String message = "getCount";

		/*Creating packet*/
		byte[] bufRequest = new byte[256];
		bufRequest = message.getBytes();

		try {

			/*if else condition to manage different clinic locations*/
			if(server.getPortNumber() == 2525){
				DatagramPacket packetRequest1 = new DatagramPacket(bufRequest, bufRequest.length, InetAddress.getLocalHost(), 3030);
				System.out.println("Debug: UDPCS: sending packet to "+ packetRequest1.getAddress() +" and port 0 "+packetRequest1.getPort()+" from server "+server.getLocation());
				socketRequest.send(packetRequest1);

				DatagramPacket packetRequest2 = new DatagramPacket(bufRequest, bufRequest.length, InetAddress.getLocalHost(), 3535);
				System.out.println("Debug: UDPCS: sending packet to "+ packetRequest2.getAddress() +" and port 0 "+packetRequest2.getPort()+" from server "+server.getLocation());
				socketRequest.send(packetRequest2);

			}
			else if(server.getPortNumber() == 3030){
				DatagramPacket packetRequest1 = new DatagramPacket(bufRequest, bufRequest.length, InetAddress.getLocalHost(), 2525);
				socketRequest.send(packetRequest1);

				DatagramPacket packetRequest2 = new DatagramPacket(bufRequest, bufRequest.length, InetAddress.getLocalHost(), 3535);
				socketRequest.send(packetRequest2);


			}
			else if(server.getPortNumber() == 3535){
				DatagramPacket packetRequest1 = new DatagramPacket(bufRequest, bufRequest.length, InetAddress.getLocalHost(), 3030);
				socketRequest.send(packetRequest1);

				DatagramPacket packetRequest2 = new DatagramPacket(bufRequest, bufRequest.length, InetAddress.getLocalHost(), 2525);
				socketRequest.send(packetRequest2);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*Receive function to receive packetResult from other 2 UDPServers*/
	public int receive() {

		System.out.println("Debug: In UDP Client Server class of "+server.getLocation() + " and recieve function");

		/*Initializing 2 integers to save results received*/
		int result1 = 0;
		int result2 = 0;

		try {
			byte[] bufResult1 = new byte[256];
			DatagramPacket packetResult1 = new DatagramPacket(bufResult1, bufResult1.length); 
			socketRequest.receive(packetResult1);

			System.out.println("Debug: Received one packet");

			byte[] bufResult2 = new byte[256];
			DatagramPacket packetResult2 = new DatagramPacket(bufResult2, bufResult2.length); 
			socketRequest.receive(packetResult2);
			System.out.println("Debug: Received second packet");

			/*Converting results to Strings*/
			String result11 = new String(packetResult1.getData(), 0, packetResult1.getLength());
			String result22 = new String(packetResult2.getData(), 0, packetResult2.getLength()) ;

			System.out.println("Debug: result1 "+result11 + " and result2 "+result22 + " ");

			/*Converting results to Integers from Strings*/
			result1 = Integer.parseInt(result11);
			result2 = Integer.parseInt(result22);


		} catch (IOException e) {
			e.printStackTrace();
		}

		/*Adding both the result1 and result2 and returning final value*/
		return result1 + result2;

	}

}
