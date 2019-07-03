package constitution.commands.servercommands.executive;
import net.minecraft.command.ICommandSender;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import constitution.utilities.PlayerUtilities;
public class ignore {
	@Command(name = "ignore", permission = "Constitution.exec.cmd.ignore", syntax = "/ignore", alias = {}, description = "")
	public static CommandResponse ignoreCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
