package constitution.commands.servercommands.executive;
import net.minecraft.command.ICommandSender;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import constitution.utilities.PlayerUtilities;
public class maildelete {
	@Command(name = "maildelete", permission = "Constitution.exec.cmd.maildelete", syntax = "/maildelete", alias = {}, description = "")
	public static CommandResponse maildeleteCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}