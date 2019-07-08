package constitution.commands.servercommands.administrative;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import net.minecraft.command.ICommandSender;
public class setrule {
	@Command(name = "setrule", permission = "constitution.cmd.perm.setrule", syntax = "/setrule", alias = {}, description = "")
	public static CommandResponse setruleCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
