package constitution.commands.servercommands.executive;
import net.minecraft.command.ICommandSender;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import constitution.utilities.PlayerUtilities;
public class tptoggle {
	@Command(name = "tptoggle", permission = "Constitution.exec.cmd.tptoggle", syntax = "/tptoggle", alias = {}, description = "")
	public static CommandResponse tptoggleCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
