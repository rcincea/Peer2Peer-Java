package Commands;

import java.io.IOException;

import Components.Node;

public class MapNamespaceCommand extends OperativeCommand{

	public MapNamespaceCommand() {
		super("map");
	}

	@Override
	public String execute(Node n) throws IOException {
		return "map_true_"+n.getNamespaces();
		
	}

	@Override
	public Command parse(String[] commandWords) {
		if(commandWords.length == 2) {
			
			if(matchTypeAndName(commandWords[0],commandWords[1])) {
	
					return new MapNamespaceCommand();
			}
		}
		return null;
	}

}
