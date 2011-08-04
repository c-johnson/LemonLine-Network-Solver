package up.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import up.model.Network;

public class PopulatorDriver {

	public static void main(String[] args){
		String theURL = "http://cse-cig.unl.edu:8080/upproject/rest/networkGenerator?groupId=3";
		Network newNetwork = new Network();
		ServiceConsumer sc = new ServiceConsumer();
		try {
		newNetwork = sc.buildNetworkFromService(new URL(theURL));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("syup");
	}
	
}
