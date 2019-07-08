package constitution.commands.servercommands.general;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import net.minecraft.command.ICommandSender;
public class deletehome {
	@Command(name = "deletehome", permission = "constitution.cmd.perm.deletehome", syntax = "/deletehome", alias = {}, description = "")
	public static CommandResponse deletehomeCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
