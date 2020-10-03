package Commands;

import java.io.IOException;

import Components.Node;

public class InformDeleteCommand extends AdminRedCommand {
	

	
	private String logicName;
	
	public InformDeleteCommand() {
		super("informDelete");
		
	}
	
	public InformDeleteCommand(String logicName) {
		super("informDelete");
		this.logicName = logicName;
		
	}
	
	@Override
	public String execute(Node n) throws IOException {
		n.informDelete(logicName);
		return null;
	}

	@Override
	public Command parse(String[] commandWords) {
		
		if(commandWords.length == 3) {
			
			if(matchTypeAndName(commandWords[0],commandWords[1])) {
				
				if(commandWords[2]!= null) {
					
					return new InformDeleteCommand(commandWords[2]);
				}
			}
		}
		
		
		return null;
	}

}
