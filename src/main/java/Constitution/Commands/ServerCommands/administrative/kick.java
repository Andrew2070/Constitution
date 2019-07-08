package constitution.commands.servercommands.administrative;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import net.minecraft.command.ICommandSender;
public class kick {
	@Command(name = "kick", permission = "constitution.cmd.perm.kick", syntax = "/kick", alias = {}, description = "")
	public static CommandResponse kickCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
