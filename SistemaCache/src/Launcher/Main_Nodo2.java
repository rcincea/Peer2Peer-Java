package Launcher;

import java.io.IOException;
import java.net.UnknownHostException;

import org.json.JSONObject;
import org.json.JSONString;

import Components.BasicNode;

public class Main_Nodo2 {
	
	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
		
		BasicNode n2 = new BasicNode("Nodo 2",9999);
		
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					n2.initializeNodeGateway();
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// TODO Auto-generated method stub
				
			}
		}).start();
		
		Thread.sleep(1500);
		// Estas operaciones se realizan antes de iniciar nodo 3(prueba local)
		JSONObject jo = new JSONObject();
		jo.put("objetoKey", "objetoValue");
		n2.crearNamespace("namespace1");
		n2.crearNamespace("namespace2");
		n2.crearNamespace("namespace3");
		n2.putInNamespace("clave1", jo, "namespace1");
		n2.putInNamespace("clave2", jo, "namespace1");
		n2.putInNamespace("clave3", jo, "namespace1");
		n2.putInNamespace("clave4", jo, "namespace2");
		n2.putInNamespace("clave5", jo, "namespace2");
		n2.listarClavesNamespace("namespace2");
		/*n2.listaDeNamespaces();
		
		n2.removeKeyValue("clave1", "namespace1");
		n2.clearCache("namespace1");
		n2.listarClavesNamespace("namespace1");
		n2.destroyNamespace("namespace1");
		n2.listaDeNamespaces();*/
		
		
		
		
		
	}

}
