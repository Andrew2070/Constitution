package constitution.commands.servercommands.administrative;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import net.minecraft.command.ICommandSender;
public class ban {
	@Command(name = "ban", permission = "constitution.cmd.perm.ban", syntax = "/ban", alias = {}, description = "")
	public static CommandResponse banCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
