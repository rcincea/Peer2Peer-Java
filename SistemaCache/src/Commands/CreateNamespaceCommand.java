package Commands;

import java.io.IOException;

import Components.Node;

public class CreateNamespaceCommand extends AdminCacheCommand {
	
	String namespace;
	public CreateNamespaceCommand( ) {
		super("create");
	}
	
	public CreateNamespaceCommand(String namespace) {
		super("create");
		this.namespace = namespace;
	}

	
	@Override
	public String execute(Node n) throws IOException {
		if(n.buscarNamespace(namespace)) {
			return "create_true_"+namespace;
		}
		else return "create_false_"+namespace;
	}

	@Override
	public Command parse(String[] commandWords) {
		if(commandWords.length == 3) {
			
			if(matchTypeAndName(commandWords[0],commandWords[1])) {
				
				if(commandWords[2]!= null) {
					
					return new CreateNamespaceCommand(commandWords[2]);
				}
			}
		}
		return null;
	}

}
