package Components;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

public class RegisterNode extends Node {
	
	public final static  int RegisterPort = 4444;
	private Map<String,Integer> nodeMap;
	private volatile RegisterGateway rgw;
	
	
	public RegisterNode() throws IOException {
		
		this.nodeMap = new HashMap<String,Integer>();
	}
	
	//metodo para gestionar la conexion
	public void initializeGateway() throws IOException {
		this.rgw = new RegisterGateway(this);
		this.rgw.initConnection();
	}
	
	//-------------------------------------------------------------------------------
	
	//operaciones que se ejecutan en el metodo execute de los comandos.
	public synchronized void signup(String nombre, int port) throws IOException {
		if(!this.nodeMap.containsKey(nombre)) {
			this.nodeMap.put(nombre, port);
			System.out.println("Registro: hemos añadido "+nombre+ " y el mapa queda de esta manera: "+this.nodeMap);
			JSONObject jo = this.nodeDataToJSON(nombre);
			this.rgw.connectToNodeServer(port);
			this.rgw.sendInfo("AdmRed"+"/"+"loadNodeData"+"/"+jo);
			this.rgw.disconnect();	
			this.inform(nombre, port);
		}
	}
	

	@Override
	public void inform(String nombre, int port) throws IOException {
		
		for(String nombreLogico : nodeMap.keySet() ) {
			if(nombreLogico != nombre) {
				System.out.println("RegisterNode: vamos a informar a "+ nombreLogico + " de " + nombre);
				int puerto = this.nodeMap.get(nombreLogico);	
					this.rgw.connectToNodeServer(puerto);
					this.rgw.sendInfo("AdmRed"+"/"+"inform"+"/"+nombre+"/"+port);
					this.rgw.disconnect();	
			}
		}	
	}
	

	@Override
	public synchronized void delete(String nombre) throws IOException {
		this.nodeMap.remove(nombre);
		this.informDelete(nombre);
		System.out.println(this.nodeMap);
	}


	@Override
	public void informDelete(String nombre) throws IOException {
		for(String nombreLogico : nodeMap.keySet() ) {
			if(nombreLogico != nombre) {
				int puerto = this.nodeMap.get(nombreLogico);	
				System.out.println("RegisterNode: voy a informar al "+nombreLogico+ " el nodo que se acaba de desconectar "+ nombre);
					this.rgw.connectToNodeServer(puerto);
					this.rgw.sendInfo("AdmRed"+"/"+"informDelete"+"/"+nombre);
					this.rgw.disconnect();	
			}
		}		
	}

	
	//---------------------------------------------------------------------------------------------------------------
	@Override
	public void loadNodeData(JSONObject nodeMAp) {}

	@Override
	public boolean clearOperation(String namespace) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void sendResponseToRequest(String nombreNodo, String response) throws IOException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean buscarNamespace(String namespace) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void destroyOperation(String namespace) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean putOperation(String key, JSONObject value, String namespace) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean removeOperation(String key, String namespace) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void manageRequestResponses(String requestId, String[] response) {
		// TODO Auto-generated method stub
		
	}

	//Métodos auxiliares de la clase
	private JSONObject nodeDataToJSON(String nombreNodo) {
		JSONObject jo = new JSONObject();
		for(String nombreLogico : nodeMap.keySet() ) {
			if(nombreNodo != nombreLogico) {
				int puerto = this.nodeMap.get(nombreLogico);
				jo.put(nombreLogico, puerto);
			}	
		}	
		return jo;
	}

	@Override
	public String getNamespaces() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getKeyValueOperation(String key, String namespace) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getListOfKeysFromNamespace(String namespace) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
