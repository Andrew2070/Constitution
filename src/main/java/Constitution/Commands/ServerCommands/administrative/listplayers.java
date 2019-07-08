package constitution.commands.servercommands.administrative;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import net.minecraft.command.ICommandSender;
public class listplayers {
	@Command(name = "listplayers", permission = "constitution.cmd.perm.listplayers", syntax = "/listplayers", alias = {}, description = "")
	public static CommandResponse listplayersCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
