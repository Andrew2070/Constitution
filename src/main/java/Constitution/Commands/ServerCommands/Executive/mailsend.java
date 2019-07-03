package constitution.commands.servercommands.executive;
import net.minecraft.command.ICommandSender;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import constitution.utilities.PlayerUtilities;
public class mailsend {
	@Command(name = "mailsend", permission = "Constitution.exec.cmd.mailsend", syntax = "/mailsend", alias = {}, description = "")
	public static CommandResponse mailsendCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
