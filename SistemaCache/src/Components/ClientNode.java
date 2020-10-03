package Components;

import java.net.*;

import org.json.JSONObject;

import java.io.*;

public class ClientNode{
	
	private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    
    
    private String nodeLogicName;
	private int nodePort;
	
    public ClientNode(String nodeLogicName,int nodePort) {
		this.nodeLogicName = nodeLogicName;
		this.nodePort = nodePort;
	}
 
    public void startConnection(String ip, int port) throws UnknownHostException, IOException {
    	
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        
        //Register
        System.out.println("Hello Im "+this.nodeLogicName );
        out.println("AdmRed"+"/"+"signup"+"/"+this.nodeLogicName+"/"+this.nodePort);
        String resp = in.readLine();
        System.out.println(resp);
        
    }
    
    public void connectToNode(String ip, int port) throws UnknownHostException, IOException {
    	
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        
    }
    
    //desconexion nodo-registro
    public void stopConnection() throws IOException {
    	System.out.println("Adios, Im "+this.nodeLogicName );
        String respond =  sendMessage("AdmRed"+"/"+"logout"+"/"+this.nodeLogicName);
        System.out.println(respond);
    
        in.close();
        out.close();
        clientSocket.close();
    }
 
    public String sendMessage(String msg) throws IOException {
        out.println(msg);
        String resp = in.readLine();
        return resp;
    }
    
    
    //desconexion nodo-nodo
    public void disconnect() throws IOException { 
        in.close();
        out.close();
        clientSocket.close();
    }
 
   
 
	

}
