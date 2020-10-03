package Components;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import Commands.Command;
import Commands.CommandGenerator;

public class RegisterServer {

	 private ServerSocket serverSocket;
	 private static RegisterNode rNode;
	 private static CommandGenerator cg;
	 
	 	public RegisterServer(RegisterNode rnode) {
	 		this.rNode = rnode;
	 		this.cg = new CommandGenerator();
	 	}

		public void start(int port) throws IOException {
	        serverSocket = new ServerSocket(port);
	        while (true)
	            new EchoClientHandler(serverSocket.accept()).start();
	    }
	 
	    public void stop() throws IOException {
	        serverSocket.close();
	    }
	 
	    private static class EchoClientHandler extends Thread {
	    	
	        private Socket clientSocket;
	        private PrintWriter out;
	        private BufferedReader in;
	        
	        private boolean disconnected;
	 
	        public EchoClientHandler(Socket socket) {
	            this.clientSocket = socket;
	            this.disconnected = false;
	        }
	 
	        public void run() {
	        	
	            try {
	            	
					out = new PrintWriter(clientSocket.getOutputStream(), true);
					
				} catch (IOException e) {
					e.printStackTrace();
				}
	            
	            try {
	            	
					in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
					
				} catch (IOException e) {
					e.printStackTrace();
				}
	             
	            
	            String inputLine;
	            
	            try {
	            	
					while ((inputLine = in.readLine()) != null && !disconnected) {
						
						String [] command = inputLine.split("/");
						Command comand = cg.parseCommand(command);
						if(comand != null ) {
							comand.execute(rNode);
							out.println("Registro: Operation "+command[1]+" -->succesfully done!");
							
						}else {
							out.println("Registro: No se ha encontrado ningun comando");
						}
						
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}
	 
	            try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	            out.close();
	            try {
					clientSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	    }
	        
	        
	   
	        
	        
	}
	    
	    
	

}
