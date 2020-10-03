package Commands;

import java.io.IOException;

import Components.Node;


public abstract class Command {
	
	protected String type;
	protected String name;
	
	public Command(String commandType,String commandName) {

		this.type = commandType;
		this.name = commandName;
	}
	
	public abstract String execute(Node n) throws IOException;
	
	public abstract Command parse(String[] commandWords);
	
	protected boolean matchTypeAndName(String type,String name) {

		return this.type.equalsIgnoreCase(type) && this.name.equalsIgnoreCase(name);
	}
	
	public String getType() {
		return type;
	}
	
	public String getName() {
		return name;
	}
}
