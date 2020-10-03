package Commands;

import java.io.IOException;

import Components.Node;

public class SignUpCommand extends AdminRedCommand{

	private String logicName;
	private int port;
	
	public SignUpCommand() {
		super("signup");
		
	}
	
	public SignUpCommand(String logicName , int port) {
		super("signup");
		this.logicName = logicName;
		this.port = port;
		
	}
	
	@Override
	public String execute(Node n) throws IOException {
		n.signup(logicName, port);
		return null;
		
	}

	@Override
	public Command parse(String[] commandWords) {
		
		if(commandWords.length == 4) {
			
			if(matchTypeAndName(commandWords[0],commandWords[1])) {
				
				if(commandWords[2]!= null && commandWords[3]!=null) {
					
					return new SignUpCommand(commandWords[2], Integer.parseInt(commandWords[3]));
				}
			}
		}
		
		
		return null;
	}


	
}
