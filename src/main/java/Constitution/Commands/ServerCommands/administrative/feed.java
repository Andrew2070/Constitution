package constitution.commands.servercommands.administrative;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import net.minecraft.command.ICommandSender;
public class feed {
	@Command(name = "feed", permission = "constitution.cmd.perm.feed", syntax = "/feed", alias = {}, description = "")
	public static CommandResponse feedCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
