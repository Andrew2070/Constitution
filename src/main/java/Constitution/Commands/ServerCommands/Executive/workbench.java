package constitution.commands.servercommands.executive;
import net.minecraft.command.ICommandSender;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import constitution.utilities.PlayerUtilities;
public class workbench {
	@Command(name = "workbench", permission = "Constitution.exec.cmd.workbench", syntax = "/workbench", alias = {}, description = "")
	public static CommandResponse workbenchCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
