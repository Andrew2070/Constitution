package constitution.commands.servercommands.executive;
import net.minecraft.command.ICommandSender;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import constitution.utilities.PlayerUtilities;
public class chatspy {
	@Command(name = "chatspy", permission = "Constitution.exec.cmd.chatspy", syntax = "/chatspy", alias = {}, description = "")
	public static CommandResponse chatspyCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
