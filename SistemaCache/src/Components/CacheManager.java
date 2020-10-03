package Components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public class CacheManager implements ICacheManagmentOperations {
	
	private static  CacheManager instancia;
	
	private Map<String,Map<String,JSONObject>> cache;
	
	private CacheManager () {
		cache = new HashMap<String,Map<String,JSONObject>>() ;
	}
	
	public synchronized static CacheManager getInstance() {
		if(instancia == null) {
			instancia = new CacheManager();
		}
		return instancia;
	}
	
	@Override
	public synchronized boolean clear(String namespace){
	
		if(!cache.containsKey(namespace)) return false;	
		cache.get(namespace).clear();
		return true;
	}

	@Override
	public synchronized void create(String namespace) {
		Map<String,JSONObject> kv = new HashMap<String,JSONObject>(); 
		cache.put(namespace, kv);
	}

	@Override
	public synchronized boolean destroyNameSpace(String namespace) {
		if(cache.containsKey(namespace)) {
			cache.remove(namespace);
			return true;
			
		}
		else {
			return false;
		}
		
		
	}

	@Override
	public synchronized JSONObject get(String key, String namespace) {
		
		if(!cache.containsKey(namespace)) {
			throw new  IllegalArgumentException("NOT SUCH A NAMESPACE");
		}else if(!cache.get(namespace).containsKey(key)) {
			throw new  IllegalArgumentException("NOT FOUND");
		}
		
		
		return cache.get(namespace).get(key);
	}

	@Override
	public synchronized List<String> list(String namespace) {
		if(!cache.containsKey(namespace)) {
			throw new IllegalArgumentException("NOT SUCH A NAMESPACE");
		}
		List<String> l = new ArrayList<String>();
		
		Map<String,JSONObject> kv = cache.get(namespace);
		
		for (String k : kv.keySet()) {
		    l.add(k);
		}
		
		return l;
	}

	@Override
	public synchronized List<String> map() {
		
		List<String> l = new ArrayList<String>();
		
		for (String ns : cache.keySet()) {
		    l.add(ns);
		}
		
		return l;
	}

	@Override
	public synchronized boolean  put(String Key, JSONObject Value, String namespace) {
		if(cache.containsKey(namespace)){
			cache.get(namespace).put(Key, Value);
			return true;
		}else {
			return false;
		}
	}

	@Override
	public synchronized boolean remove(String key, String namespace) {
		if(cache.containsKey(namespace)) {	
			cache.get(namespace).remove(key);
			return true;	
		}
		else return false;
		
	}


	@Override
	public synchronized boolean existeNamespace(String namespace) {
		
		return cache.containsKey(namespace);
	}
	
	
	
}
