package constitution.commands.servercommands.general;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import net.minecraft.command.ICommandSender;
public class afk {
	@Command(name = "afk", permission = "constitution.cmd.perm.afk", syntax = "/afk", alias = {}, description = "")
	public static CommandResponse afkCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
