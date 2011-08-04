package up.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import up.model.Flow;
import up.model.Network;
import up.model.Revenue;

public class PopulatorDriver {

	public static void main(String[] args){
		String theURL = "http://cse-cig.unl.edu:8080/upproject/rest/networkGenerator?groupId=3";
		Network newNetwork = new Network();
		Populator pop = new Populator();
		ArrayList<Flow> flows = new ArrayList<Flow>();
		ArrayList<Revenue> revs = new ArrayList<Revenue>();
		//ServiceConsumer sc = new ServiceConsumer();
		try {
			pop.getXML("http://cse-cig.unl.edu:8080/upproject/rest/networkGenerator?groupId=3", "network");
			pop.getXML("http://cse-cig.unl.edu:8080/upproject/rest/inputGenerator?groupId=3", "");
			newNetwork = pop.populateNetwork();
			flows = pop.populateFlow();
			revs = pop.populateRevenue();
		//newNetwork = sc.buildNetworkFromService(new URL(theURL));
		//} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("syup");
	}
	
}
