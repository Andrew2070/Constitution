package constitution.commands.servercommands.administrative;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import net.minecraft.command.ICommandSender;
public class burn {
	@Command(name = "burn", permission = "constitution.cmd.perm.burn", syntax = "/burn", alias = {}, description = "")
	public static CommandResponse burnCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
