package Commands;

public class CommandGenerator {
	
	
	private static OperativeCommand[] opCommand = {
			new PutKeyValueCommand(), new RemoveKeyCommand(), new RequestCommand(), new ManageRequestReplyCommand(),
			new GetKeyCommand(), new MapNamespaceCommand(), new ListNamespaceKeysCommand()
			
	};
	private static AdminRedCommand[] admRedCommand = {
		new SignUpCommand(), new LogOutCommand(), new InformCommand(), new InformDeleteCommand(),
		new LoadNodeDataCommand()
	};
	
	private static AdminCacheCommand[] admCacheCommand = {
			new ClearNamespaceCommand(), new CreateNamespaceCommand(), new DestroyNamespaceCommand()
	};
	
	
	
	public static Command parseCommand(String[] commandWords) {
		

			
		if(commandWords[0].equalsIgnoreCase("Op")){
			
			for (Command command : opCommand) {
				if (command.parse(commandWords)!=null)
					return  command.parse(commandWords);
			}
			
		}else if(commandWords[0].equalsIgnoreCase("AdmRed")) {
			
			for (Command command : admRedCommand) {
				if (command.parse(commandWords)!=null)
					return  command.parse(commandWords);
			}
			
		}
		else if(commandWords[0].equalsIgnoreCase("AdmCache")) {
			
			for (Command command : admCacheCommand) {
				if (command.parse(commandWords)!=null)
					return  command.parse(commandWords);
			}
			
		}
		
		throw new IllegalArgumentException("Comando inexistente");
		
	}
}
