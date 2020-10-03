package Components;

import java.util.List;

import org.json.JSONObject;

public interface ICacheManagmentOperations {
	
	public boolean clear(String namespace);
	public void create(String namespace);
	public boolean destroyNameSpace(String namespace);
	public JSONObject get(String key,String namespace);
	public List<String> list(String namespace);
	public List<String> map();
	public boolean remove(String key,String namespace);
	public boolean put(String Key, JSONObject Value, String namespace);
	
	////////////////////Busqueda//////////////////////
	
	public boolean existeNamespace(String namespace);
	
	
}
