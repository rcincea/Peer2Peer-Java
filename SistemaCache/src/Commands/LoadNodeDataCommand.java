package Commands;

import java.io.IOException;

import org.json.JSONObject;

import Components.Node;

public class LoadNodeDataCommand extends AdminRedCommand{
	
	private JSONObject json;
	
	public LoadNodeDataCommand() {
		super("loadNodeData");
		
	}
	
	public LoadNodeDataCommand( JSONObject json) {
		super("loadNodeData");
		this.json = json;
		
	}
	
	@Override
	public String execute(Node n) throws IOException {
		n.loadNodeData(json);
		return null;
	}

	@Override
	public Command parse(String[] commandWords) {
		
		if(commandWords.length == 3) {
			
			if(matchTypeAndName(commandWords[0],commandWords[1])) {
				
				if(commandWords[2]!= null) {
					JSONObject jo = new JSONObject(commandWords[2]);
					return new LoadNodeDataCommand(jo);
				}
			}
		}
		
		
		return null;
	}

}
