package constitution.commands.servercommands.general;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import net.minecraft.command.ICommandSender;
public class rules {
	@Command(name = "rules", permission = "constitution.cmd.perm.rules", syntax = "/rules", alias = {}, description = "")
	public static CommandResponse rulesCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
