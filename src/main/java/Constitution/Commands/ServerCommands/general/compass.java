package constitution.commands.servercommands.general;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import net.minecraft.command.ICommandSender;
public class compass {
	@Command(name = "compass", permission = "constitution.cmd.perm.compass", syntax = "/compass", alias = {}, description = "")
	public static CommandResponse compassCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
