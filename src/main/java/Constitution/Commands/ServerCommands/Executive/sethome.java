package constitution.commands.servercommands.executive;
import net.minecraft.command.ICommandSender;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import constitution.utilities.PlayerUtilities;
public class sethome {
	@Command(name = "sethome", permission = "Constitution.exec.cmd.sethome", syntax = "/sethome", alias = {}, description = "")
	public static CommandResponse sethomeCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
