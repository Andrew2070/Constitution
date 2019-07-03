package constitution.commands.servercommands.executive;
import net.minecraft.command.ICommandSender;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import constitution.utilities.PlayerUtilities;
public class getposition {
	@Command(name = "getposition", permission = "Constitution.exec.cmd.getposition", syntax = "/getposition", alias = {}, description = "")
	public static CommandResponse getpositionCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
