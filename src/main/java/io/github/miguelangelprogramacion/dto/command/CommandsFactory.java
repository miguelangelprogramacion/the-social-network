package io.github.miguelangelprogramacion.dto.command;

public class CommandsFactory {
	
	private CommandsFactory() {
		super();
		
	}

	public static ICommand getCommand(String command) {
		String[] splitted = command.split(" ", 3);
		ICommand retorno = null;
		if (splitted.length == 1)
			retorno = new GetTimeline(splitted[0]);
		else {
			switch (splitted[1]) {
			case "->":
				retorno = new Publish(splitted[0], splitted[2]);
				break;
			case "follows":
				retorno = new Follows(splitted[0], splitted[2]);
				break;
			case "wall":
				retorno = new GetWall(splitted[0]);
				break;
			default:
				retorno = new CommandError(command);
			}
		}

		return retorno;
	}
}
