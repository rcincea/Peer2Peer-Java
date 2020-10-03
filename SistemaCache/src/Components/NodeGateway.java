package Components;

import java.io.IOException;
import java.net.UnknownHostException;

public class NodeGateway {
	
	private String nodeLogicName;
	private static int nodePort;
	private ClientNode cn;
	private NodeServer ns;
	private static BasicNode bn;
	
	public NodeGateway(String nombreLogico,int puerto,BasicNode bn) throws UnknownHostException, IOException {
		
		this.nodeLogicName = nombreLogico;
		this.nodePort = puerto;
		this.bn = bn;
		cn = new ClientNode(nombreLogico,puerto);
		ns = new NodeServer(bn);
		
	}
	
	
	

	
	public void initConnection() throws UnknownHostException, IOException {
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					cn.startConnection("127.0.0.1", 4444);
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
		
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					ns.start(nodePort);
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();

	}
	
	public void connectNodeToNode(int puerto) throws UnknownHostException, IOException {
		this.cn = new ClientNode(nodeLogicName, puerto);
		this.cn.connectToNode("127.0.0.1", puerto);
	}
	

	public String sendRequest(String msg) throws IOException {
		 return this.cn.sendMessage(msg);
	}
	
	
	//desconectar un nodo
	public void disconnect() throws IOException {
		this.cn.disconnect();
	}
	
	//finalizar hebra cliente y servidora
	public void stopConnection() throws UnknownHostException, IOException {
		//Desconexion del registro
		cn.stopConnection();
		//Se para la parte servidora del node
		ns.stop();
	}
	
	
}
