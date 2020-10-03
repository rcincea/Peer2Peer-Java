package Commands;

import java.io.IOException;

import Components.Node;

public class DestroyNamespaceCommand extends AdminCacheCommand {
	
	
	String namespace;
	public DestroyNamespaceCommand() {
		super("destroy");
	}
	
	public DestroyNamespaceCommand(String namespace) {
		super("destroy");
		this.namespace = namespace;
	}

	@Override
	public String execute(Node n) throws IOException {
		n.destroyOperation(namespace);
		return "destroy_true_"+namespace;
		
	}

	@Override
	public Command parse(String[] commandWords) {
		if(commandWords.length == 3) {
			if(matchTypeAndName(commandWords[0],commandWords[1])) {
				if(commandWords[2]!= null) {	
					return new DestroyNamespaceCommand(commandWords[2]);
				}
			}
		}
		return null;
		}
}
