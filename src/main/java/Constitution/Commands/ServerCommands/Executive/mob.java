package constitution.commands.servercommands.executive;
import net.minecraft.command.ICommandSender;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import constitution.utilities.PlayerUtilities;
public class mob {
	@Command(name = "mob", permission = "Constitution.exec.cmd.mob", syntax = "/mob", alias = {}, description = "")
	public static CommandResponse mobCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
