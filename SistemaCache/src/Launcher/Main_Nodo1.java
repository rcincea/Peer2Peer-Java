package Launcher;

import java.io.IOException;
import java.net.UnknownHostException;

import Components.BasicNode;

public class Main_Nodo1 {
	
	
	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
	
	
	BasicNode n1 = new BasicNode("Nodo 1",3333);
	
	
	new Thread(new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				n1.initializeNodeGateway();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}	
	}).start();

	}
	
	
	
}
