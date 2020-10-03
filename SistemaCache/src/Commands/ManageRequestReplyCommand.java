package Commands;

import java.io.IOException;

import Components.Node;

public class ManageRequestReplyCommand extends OperativeCommand{
	
	
	String requestId;
	String[] respuesta;
	public ManageRequestReplyCommand() {
		super("replyRequest");
		
	}
	
	public ManageRequestReplyCommand(String requestId, String[] respuesta ) {
		super("replyRequest");
		this.requestId = requestId;
		this.respuesta = respuesta;
	}
	

	@Override
	public String execute(Node n) throws IOException {
		n.manageRequestResponses(requestId, respuesta);
		return null;
	}

	@Override
	public Command parse(String[] commandWords) {
		
		if(commandWords.length == 4) {
			
			if(matchTypeAndName(commandWords[0],commandWords[1])) {
				
				if(commandWords[2]!= null && commandWords[3] != null) {
					
					String[] splited = commandWords[3].split("_");
					return new ManageRequestReplyCommand(commandWords[2], splited);
				}
			}
		}
		
		return null;
	}
	
		
}
