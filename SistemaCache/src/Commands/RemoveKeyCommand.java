package Commands;

import java.io.IOException;

import Components.Node;

public class RemoveKeyCommand extends OperativeCommand {

	String key, namespace;
	public RemoveKeyCommand() {
		super("remove");
	}
	
	public RemoveKeyCommand(String key, String namespace) {
		super("remove");
		this.key = key;
		this.namespace = namespace;
	}

	@Override
	public String execute(Node n) throws IOException {
		if(n.removeOperation(key, namespace)) return "remove_true_"+key+"_"+namespace;
		else return "remove_false"+"_"+namespace;
		
	}

	@Override
	public Command parse(String[] commandWords) {
		if(commandWords.length == 4) {
			
			if(matchTypeAndName(commandWords[0],commandWords[1])) {
				
				if(commandWords[2]!= null && commandWords[3] != null) {
					
					return new RemoveKeyCommand(commandWords[2], commandWords[3]);
				}
			}
		}
		return null;
	}

}
