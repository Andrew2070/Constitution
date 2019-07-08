package constitution.commands.servercommands.general;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import net.minecraft.command.ICommandSender;
public class hat {
	@Command(name = "hat", permission = "constitution.cmd.perm.hat", syntax = "/hat", alias = {}, description = "")
	public static CommandResponse hatCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
