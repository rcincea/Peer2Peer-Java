package Commands;

import java.io.IOException;

import Components.Node;

public class LogOutCommand extends AdminRedCommand {
	
	
	
	private String nombreLogico;
	
	public LogOutCommand() {
		super("logout");
	}
	
	public LogOutCommand(String name) {
		super("logout");
		this.nombreLogico = name;
	}

	@Override
	public String execute(Node n) throws IOException {
		n.delete(nombreLogico);
		return null;
		
	}

	@Override
	public Command parse(String[] commandWords) {
		if(commandWords.length == 3) {
			
			if(matchTypeAndName(commandWords[0],commandWords[1])) {
				
				if(commandWords[2]!= null) {
					
					return new LogOutCommand(commandWords[2]);
				}
			}
		}
		return null;
	}
	

}
