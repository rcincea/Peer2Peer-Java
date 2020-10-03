package Commands;

import java.io.IOException;

import Components.Node;

public class GetKeyCommand extends OperativeCommand{

	
	String key, namespace;
	public GetKeyCommand( ) {
		super("get");
		
	}
	
	public GetKeyCommand(String key, String namespace ) {
		super("get");
		this.key = key;
		this.namespace = namespace;
		
	}

	@Override
	public String execute(Node n) throws IOException {
		return n.getKeyValueOperation(key, namespace);
		
		
	}

	@Override
	public Command parse(String[] commandWords) {
		
		if(commandWords.length == 4) {
			
			if(matchTypeAndName(commandWords[0],commandWords[1])) {
				
				if(commandWords[2]!= null && commandWords[3]!= null) {
					return new GetKeyCommand(commandWords[2], commandWords[3]);
				}
			}
		}
		return null;
	}

}
