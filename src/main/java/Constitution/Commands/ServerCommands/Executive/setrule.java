package constitution.commands.servercommands.executive;
import net.minecraft.command.ICommandSender;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import constitution.utilities.PlayerUtilities;
public class setrule {
	@Command(name = "setrule", permission = "Constitution.exec.cmd.setrule", syntax = "/setrule", alias = {}, description = "")
	public static CommandResponse setruleCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
