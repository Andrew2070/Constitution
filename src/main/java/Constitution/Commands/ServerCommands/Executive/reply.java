package constitution.commands.servercommands.executive;
import net.minecraft.command.ICommandSender;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import constitution.utilities.PlayerUtilities;
public class reply {
	@Command(name = "reply", permission = "Constitution.exec.cmd.reply", syntax = "/reply", alias = {}, description = "")
	public static CommandResponse replyCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
