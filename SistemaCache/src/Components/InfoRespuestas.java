package Components;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class InfoRespuestas{ //cuenta los nodos que han respondido y guarda las respuestas que ha recibido de cada nodo
    private  int contadorNodos;
    private List<Boolean> listaRespuestas;
  
    
   
    public InfoRespuestas() {
    	this.contadorNodos = 0;
        this.listaRespuestas = new ArrayList<>();
    }

    public int getContadorNodos() {
        return this.contadorNodos;
    }

    public boolean comprobarRespuestas() {
    	
    	Iterator it = this.listaRespuestas.iterator();
		while(it.hasNext()) {
			boolean rsp =  (boolean) it.next();
			if(rsp) {
				return true;
			}
		}
		return false;
    }
    
    public void addResponse(boolean response) {
    	
    	this.listaRespuestas.add(response);
    }
    
    public void increaseNodeCounter() {
    	++contadorNodos;
    }
}
