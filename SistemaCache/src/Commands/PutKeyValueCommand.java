package Commands;

import java.io.IOException;

import org.json.JSONObject;

import Components.Node;

public class PutKeyValueCommand extends OperativeCommand {
	
	String key;
	JSONObject value;
	String namespace;
	public PutKeyValueCommand() {
		super("put");
		
	}
	
	public PutKeyValueCommand(String key, JSONObject value, String namespace) {
		super("put");
		this.key = key;
		this.value = value;
		this.namespace = namespace;
		
	}

	@Override
	public String execute(Node n) throws IOException {
		if(n.putOperation(key, value, namespace)) {
			return "put_true"+"_"+key+"_"+namespace;
		}else {
			return "put_false"+"_"+key+"_"+namespace;
		}
		
	}

	@Override
	public Command parse(String[] commandWords) {
		if(commandWords.length == 5) {
			
			if(matchTypeAndName(commandWords[0],commandWords[1])) {
				
				if(commandWords[2]!= null && commandWords[3] != null && commandWords[4] != null) {
					JSONObject jo = new JSONObject(commandWords[3]);
					return new PutKeyValueCommand(commandWords[2], jo, commandWords[4]);
				}
			}
		}
		return null;
	}

}
