package Components;

import java.io.IOException;

import org.json.JSONObject;

public interface CacheManagerFachada {
	
	//Remoto
	public void crearNamespace(String namespace) throws IOException;
	public void clearCache(String namespace) throws IOException;
	public void destroyNamespace(String namespace) throws IOException;
	public void putInNamespace(String key, JSONObject value, String namespace) throws IOException;
	public void removeKeyValue(String key, String namespace) throws IOException;
	
	
	//Locales
	public void listarClavesNamespace(String namespace) throws IOException;
	public void listaDeNamespaces() throws IOException;
	public void getKeyValue(String key, String namespace) throws IOException;
}
