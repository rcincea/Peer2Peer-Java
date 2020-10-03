package Components;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import Commands.Command;

public class BasicNode extends Node implements CacheManagerFachada {
	
	private static CacheManager cm ;
	private NodeGateway gw;
	private String logicName;
	private int port;
	private Map<String,Integer> nodeMap;
	private Map<String,InfoRespuestas> idRequestInfo; 
	private int contadorPeticionesEnviadas;
	private List<String> listaTodosLosNamespaces;
	
	
	public BasicNode(String nombreLogico,int puerto) throws UnknownHostException, IOException {
		this.nodeMap = new HashMap<String,Integer>();
		this.logicName = nombreLogico;
		this.port = puerto;
		this.idRequestInfo = new HashMap<String,InfoRespuestas>();
		this.contadorPeticionesEnviadas = 0;
		this.listaTodosLosNamespaces = new ArrayList<String>();
	}
	
	
	
	// Metodos que gestionan la conexion
	public void initializeNodeGateway() throws UnknownHostException, IOException {
		this.gw = new NodeGateway(this.logicName,this.port,this);
		this.gw.initConnection();
	}

	public void logOut() throws UnknownHostException, IOException {
		this.gw.stopConnection();
	}
	
	//-----------------------------------------------------------------------------
	
	
	
	//metodos que se ejecutan en local, y si no se pueden, envian un request
	
	@Override
	public void listarClavesNamespace(String namespace) throws IOException {
		if(this.cm.getInstance().existeNamespace(namespace)) {
			List<String> listaClaves = this.cm.getInstance().list(namespace);
			System.out.println("La lista de claves del nodo " + this.logicName+ " y el "+ namespace+ " es "+listaClaves);
		}else {
			request("Op-list-"+namespace);
		}
		
		
	}
	
	@Override
	public void listaDeNamespaces() throws IOException {
		this.listaTodosLosNamespaces = this.cm.getInstance().map();
		request("Op-map");
	}

	@Override
	public void getKeyValue(String key, String namespace) throws IOException {
		if(this.cm.getInstance().existeNamespace(namespace)) {	
				JSONObject value = this.cm.getInstance().get(key, namespace);
				System.out.println("El valor asociado a la clave "+ key+ " es "+ value);		
		}else {
			request("Op-get-"+key+"-"+namespace);
		}
		
		
	}
	
	
	
	
	
	public void crearNamespace(String namespace) throws IOException {
		if(this.cm.getInstance().existeNamespace(namespace)) {
			System.out.println("El namespace se ha encontrado localmente");
		}else request("AdmCache-create-"+namespace);
	}
	
	public void clearCache(String namespace) throws IOException {
		if(!this.cm.getInstance().clear(namespace)) {
			request("AdmCache-clear-"+namespace);
		}else {
			System.out.println("Se ha vaciado la cache asociada al "+ namespace+ " en nodo " + this.logicName);
		}
	}
	
	public void destroyNamespace(String namespace) throws IOException{
		if(!this.cm.getInstance().destroyNameSpace(namespace)) {
			request("AdmCache-destroy-"+namespace);
		}
		else {
			System.out.println("Se ha eliminado completamente el  "+ namespace+ " en nodo " + this.logicName);
		}
	}
	
	public void putInNamespace(String key, JSONObject value, String namespace) throws IOException {
		String valorString = value.toString();
		if(!this.cm.getInstance().put(key, value, namespace)) {
			request("Op-put-"+key+"-"+valorString+"-"+namespace);
		}
		else {
			System.out.println("Se ha colocado con éxito " + key +" y su valor asociado en el " + namespace );
		}
	}
	
	public void removeKeyValue(String key, String namespace) throws IOException {
		if(!this.cm.getInstance().remove(key, namespace)) {
			request("Op-remove-"+key+"-"+namespace);
		}else {
			System.out.println("Se ha eliminado el valor asociado a " +  key +" en el "+namespace);
		}
	}
	
	
	//-----------------------------------------------------------------------------
	
	// metodos que se encargan de enviar las peticiones y de manejar cuando se han recibido las respuestas del resto de nodos
	private void request(String requestedOperation) throws IOException {
		String requestId = "rqstId_"+ this.contadorPeticionesEnviadas;
		this.contadorPeticionesEnviadas++;
		InfoRespuestas nodos_ListaRespuestas = new InfoRespuestas();
		this.idRequestInfo.put(requestId, nodos_ListaRespuestas);
		Iterator it = this.nodeMap.entrySet().iterator();
		while(it.hasNext()) {
			
			Map.Entry par = (Map.Entry)it.next();
			String nombreLogico = (String) par.getKey();
			int puerto = (int) par.getValue();
			this.gw.connectNodeToNode(puerto);
			this.gw.sendRequest("Op/request/"+this.logicName+"/"+requestId+"/"+requestedOperation);
			this.gw.disconnect();	
		}
	}
	
	
	public synchronized void manageRequestResponses(String requestId, String[] response) {
		if(idRequestInfo.containsKey(requestId)) {
			InfoRespuestas par = idRequestInfo.get(requestId);
			par.increaseNodeCounter();
			par.addResponse(Boolean.valueOf(response[1]));
			
			if(response[0].equals("map")) {
				if(!response[2].equals("[]")) {
					listaTodosLosNamespaces.add(response[2]);
				}
			}
			boolean valorRespuesta = Boolean.valueOf(response[1]);
			if(valorRespuesta && !response[0].equals("map")) {
				decodifyResponse(response, valorRespuesta);
				this.idRequestInfo.remove(requestId);
			}
			else {
					
				if(par.getContadorNodos() == this.nodeMap.size()) {
					boolean respuesta = par.comprobarRespuestas();
					decodifyResponse(response,respuesta);
					this.idRequestInfo.remove(requestId);
				}		
			}	
		}
	}
	
		
		
		
	
	private void decodifyResponse(String[] response, boolean respuesta) {
		
		switch (response[0]) {
		
			case "clear":
					if(respuesta) {
						System.out.println(response[2]+" has been cleared !");
					}else {
						System.out.println(response[2]+" doesnt exist !");
					}
				break;
			case "create":
					if(respuesta) {
						System.out.println(response[2]+"  already exists !");
					}else {
						this.cm.getInstance().create(response[2]);
						System.out.println(response[2] + " has been created !");
					}
				
				break;
			case "destroy":
				
					System.out.println(response[2] + " has been destroyed !");

				break;
			case "put":
				if(respuesta) {
					System.out.println("The key :"+response[2]+" has been succesfully saved in "+response[3]+" !");
				}else {
					System.out.println(response[3]+" doesnt exist !");
				}
		
			break;
			case "remove":
				if(respuesta) {
					System.out.println("The key: "+response[2] + " has been removed !");
				}else {
					System.out.println(response[2] + " doesn't exist !");
				}
				
				
			break;
			case "map":
				System.out.println("La lista de namespaces es "+ this.listaTodosLosNamespaces);
		
			break;
			case "list":
				
				if(respuesta) {
					System.out.println("La lista de claves del " + response[3] + " es: "+ response[2] );
				}else {
					System.out.println(response[3]+" doesnt exist !");
				}
				
				break;
			case "get":
					System.out.println("El valor asociado a la clave " + response[3] + " es " + response[2] + " del " + response[4]);
				
				break;
			case "error":
					if(response[1].equals("namespace")) {
						System.out.println("NotSuchNamespace");
					}else if(response[1].equals("key")) {
						System.out.println("NotFound");
					}
				
				
			default:
				break;
			}
	}
	
	
	//-----------------------------------------------------------------------------
	
	
	

	
	
	// métodos que se ejecutan al llamar al metodo execute de un comando 
	@Override
	public synchronized void inform(String nombre, int port) {
		this.nodeMap.put(nombre, port);
		System.out.println("En mapa del "+ this.logicName+ " añadimos "+ nombre + " mapa queda asi "+ this.nodeMap );
	}

	@Override
	public synchronized void loadNodeData(JSONObject nodeMap) {
		
        Iterator<String> keys = nodeMap.keys();

        while( keys.hasNext() ){
            String key = keys.next();
            int value = nodeMap.getInt(key); 
            this.nodeMap.put(key, value);
        }
        System.out.println("BasicNode: El "+ this.logicName + " acaba de obtener la información de los nodos actuales, que son: " + this.nodeMap);
	}
	
	@Override
	public void delete(String nombre) throws UnknownHostException, IOException {
		
	}

	@Override
	public synchronized void informDelete(String nombre) {
		this.nodeMap.remove(nombre);
		System.out.println("BasicNode: El mapa de nodos en BasicNode después de eliminar queda así " + this.nodeMap);
		
	}
	
	@Override
	public void signup(String nombre, int puerto) {	
	}

	@Override
	public boolean clearOperation(String namespace) {
		return this.cm.getInstance().clear(namespace);
	}

	@Override
	public boolean buscarNamespace(String namespace) {
		return this.cm.getInstance().existeNamespace(namespace);
	}

	@Override
	public void destroyOperation(String namespace) {
		this.cm.getInstance().destroyNameSpace(namespace);
	}

	@Override
	public boolean putOperation(String key, JSONObject value, String namespace) {
		return this.cm.getInstance().put(key, value, namespace);
	}

	@Override
	public boolean removeOperation(String key, String namespace) {
		return this.cm.getInstance().remove(key, namespace);
	}
	
	
	
	public void sendResponseToRequest(String nombreNodo, String response) throws IOException {
		  int port = nodeMap.get(nombreNodo);
		  this.gw.connectNodeToNode(port);
		  this.gw.sendRequest(response);
		  this.gw.disconnect();
	}



	@Override
	public String getNamespaces() {
		return this.cm.getInstance().map().toString();
	}




	@Override
	public String getKeyValueOperation(String key, String namespace) {
		
		try{
			return  "get_true_"+this.cm.getInstance().get(key, namespace)+"_"+key+"_"+namespace;
		}catch(IllegalArgumentException e) {
			return  "error_"+namespace;
		}
			
	}



	@Override
	public String getListOfKeysFromNamespace(String namespace) {
		try {
			return "list_true_"+this.cm.getInstance().list(namespace).toString()+"_"+namespace;
		}catch(IllegalArgumentException e) {
			return "list_false_"+namespace;
		}
		
	}

	//-----------------------------------------------------------------------------------------------
	
	


	


	

}
