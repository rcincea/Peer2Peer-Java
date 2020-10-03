package Commands;

import java.io.IOException;

import Components.Node;

public class RequestCommand extends OperativeCommand{

	private String requesterNode;
	private String requestId;
	private String requestedOperation;
	private CommandGenerator gx;
	
	public RequestCommand() {
		super("request");
	}
	
	public RequestCommand(String requesterNode, String requestId, String requestedOperation) {
		super("request");
		this.requesterNode  = requesterNode;
		this.requestId = requestId;
		this.requestedOperation = requestedOperation;
	}

	@Override
	public String execute(Node n) throws IOException {
		String [] commands = this.requestedOperation.split("-");
		Command comand = gx.parseCommand(commands);
		String response = comand.execute(n);
		String respuesta = "Op/replyRequest/"+this.requestId+"/"+ response;
		n.sendResponseToRequest(this.requesterNode, respuesta);
		
		return null;
	}

	@Override
	public Command parse(String[] commandWords) {
		if(commandWords.length == 5) {
			
			if(matchTypeAndName(commandWords[0],commandWords[1])) {
				if(commandWords[2]!= null && commandWords[3] != null && commandWords[4] != null) {
					return new RequestCommand(commandWords[2], commandWords[3],commandWords[4]);	
				}
			}
		}
		return null;
	}
	

}
