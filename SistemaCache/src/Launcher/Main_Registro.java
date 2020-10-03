package Launcher;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import Components.BasicNode;
import Components.ClientNode;
import Components.RegisterNode;

public class Main_Registro {

	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
		RegisterNode registerNode = new RegisterNode();
		registerNode.initializeGateway();
	}
	
}
