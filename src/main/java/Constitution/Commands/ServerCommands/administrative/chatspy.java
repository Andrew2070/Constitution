package constitution.commands.servercommands.administrative;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import net.minecraft.command.ICommandSender;
public class chatspy {
	@Command(name = "chatspy", permission = "constitution.cmd.perm.chatspy", syntax = "/chatspy", alias = {}, description = "")
	public static CommandResponse chatspyCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
