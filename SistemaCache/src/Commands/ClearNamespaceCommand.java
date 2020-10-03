package Commands;

import java.io.IOException;


import Components.Node;

public class ClearNamespaceCommand extends AdminCacheCommand{
	
	
	private String namespace;
	
	public ClearNamespaceCommand() {
		super("clear");
		
	}
	
	public ClearNamespaceCommand(String namespace) {
		super("clear");
		this.namespace = namespace;
	}
	
	
	
	
	@Override
	public String execute(Node n) throws IOException {
		if(n.clearOperation(namespace)){
			return "clear_true_"+namespace;
		}else {
			return "clear_false_"+namespace;
		}
	
		
	}

	@Override
	public Command parse(String[] commandWords) {
		if(commandWords.length == 3) {
			
			if(matchTypeAndName(commandWords[0],commandWords[1])) {
				
				if(commandWords[2]!= null) {
					
					return new ClearNamespaceCommand(commandWords[2]);
				}
			}
		}
		return null;
	}
	
	
	@Override
    public String toString() {
		return "Op/clear/"+this.namespace ;
	}
}
