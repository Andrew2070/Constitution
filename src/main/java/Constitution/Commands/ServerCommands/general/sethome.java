package constitution.commands.servercommands.general;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import net.minecraft.command.ICommandSender;
public class sethome {
	@Command(name = "sethome", permission = "constitution.cmd.perm.sethome", syntax = "/sethome", alias = {}, description = "")
	public static CommandResponse sethomeCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
