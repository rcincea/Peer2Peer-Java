package Commands;

import java.io.IOException;

import Components.Node;

public class InformCommand extends AdminRedCommand  {
	//comando para captar el inform de RegsiterNode
	
	
	
	private String logicName;
	private int port;
	
	public InformCommand() {
		super("inform");
		
	}
	
	public InformCommand(String logicName, int port) {
		super("inform");
		this.logicName = logicName;
		this.port = port;
		
	}
	
	@Override
	public String execute(Node n) throws IOException {
		n.inform(logicName, port);
		return null;
	}

	@Override
	public Command parse(String[] commandWords) {
		
		if(commandWords.length == 4) {
			
			if(matchTypeAndName(commandWords[0],commandWords[1])) {
				
				if(commandWords[2]!= null && commandWords[3]!=null) {
					
					return new InformCommand(commandWords[2], Integer.parseInt(commandWords[3]));
				}
			}
		}
		
		
		return null;
	}

	
}
