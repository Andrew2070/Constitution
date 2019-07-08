package constitution.commands.servercommands.general;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import net.minecraft.command.ICommandSender;
public class reply {
	@Command(name = "reply", permission = "constitution.cmd.perm.reply", syntax = "/reply", alias = {}, description = "")
	public static CommandResponse replyCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
