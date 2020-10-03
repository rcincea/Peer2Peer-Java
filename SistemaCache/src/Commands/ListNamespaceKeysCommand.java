package Commands;

import java.io.IOException;

import Components.Node;

public class ListNamespaceKeysCommand extends OperativeCommand{

	String namespace;
	public ListNamespaceKeysCommand() {
		super("list");
	}
	
	public ListNamespaceKeysCommand(String namespace) {
		super("list");
		this.namespace = namespace;
	}

	@Override
	public String execute(Node n) throws IOException {
	
		return n.getListOfKeysFromNamespace(namespace);
		
	}

	@Override
	public Command parse(String[] commandWords) {
		if(commandWords.length == 3) {
			
			if(matchTypeAndName(commandWords[0],commandWords[1])) {
				
				if(commandWords[2]!= null) {
					
					return new ListNamespaceKeysCommand(commandWords[2]);
				}
			}
		}
		return null;
	}

}
