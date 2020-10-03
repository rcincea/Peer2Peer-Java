package Launcher;

import java.io.IOException;
import java.net.UnknownHostException;

import org.json.JSONObject;

import Components.BasicNode;

public class Main_Nodo3 {

	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
		BasicNode n3 = new BasicNode("Nodo 3", 1222);

	
	

	new Thread(new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				n3.initializeNodeGateway();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}	
		}).start();
	
	Thread.sleep(5000);
	
	JSONObject jo = new JSONObject();
	jo.put("objetoKey", "objetoValue");
	n3.listarClavesNamespace("namespace2");
	n3.crearNamespace("namespace2");
	n3.putInNamespace("clave6", jo, "namespace2");
	n3.listarClavesNamespace("namespace2");
	n3.removeKeyValue("clave8", "namespace2");
	n3.destroyNamespace("namespace1");
	Thread.sleep(1500);
	n3.getKeyValue("clave5", "namespace2");
	n3.removeKeyValue("clave6", "namespace2");
	n3.listarClavesNamespace("namespace2");
	n3.listaDeNamespaces();
	n3.logOut();
	
	}
	
	

}
