package Components;
import java.io.IOException;
import java.net.UnknownHostException;

import org.json.JSONObject;

public interface IComunicationProtocol {
	
	// Registro<-->Nodo
	
	public void inform(String nombre, int port) throws IOException;
	public void loadNodeData(JSONObject nodeMAp);
	public void delete(String nombre) throws UnknownHostException, IOException;
	public void informDelete(String nombre) throws IOException;
	public void signup(String nombre, int puerto) throws IOException;
	
	
	//NODO<-> NODO
	
	public boolean clearOperation(String namespace);
	public boolean buscarNamespace(String namespace);
	public void destroyOperation(String namespace);
	public boolean putOperation(String key, JSONObject value, String namespace);
	public boolean removeOperation(String key, String namespace);
	public String getNamespaces();
	public String getKeyValueOperation(String key, String namespace);
	public String getListOfKeysFromNamespace(String namespace);
	
	
	public void sendResponseToRequest(String nombreNodo, String response) throws IOException;
	public void manageRequestResponses(String requestId, String[] response);
	
	
	
	
	
	
	
	
	
	
}
