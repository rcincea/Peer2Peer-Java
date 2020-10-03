package Components;

import java.io.IOException;
import java.util.HashMap;

public class RegisterGateway {
	
	private RegisterServer rs;
	private ClientRegister cr;
	private RegisterNode rn;
	
	public RegisterGateway(RegisterNode rn) throws IOException {
		this.rn = rn;
	}
	
	
	public void initConnection() throws IOException {
			
		rs = new RegisterServer(this.rn);
		rs.start(RegisterNode.RegisterPort);
		
	}
	
	public void connectToNodeServer(int puerto) throws IOException {

		this.cr =  new ClientRegister();
		cr.startConnection("127.0.0.1", puerto);
	}
	
	public void sendInfo(String msg) throws IOException {
		
		this.cr.sendMessage(msg);
		
	}
	
	public void disconnect() throws IOException {
		
		this.cr.stopConnection();
		
	}
}
