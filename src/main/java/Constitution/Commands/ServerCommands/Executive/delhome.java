package constitution.commands.servercommands.executive;
import net.minecraft.command.ICommandSender;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import constitution.utilities.PlayerUtilities;
public class delhome {
	@Command(name = "delhome", permission = "Constitution.exec.cmd.delhome", syntax = "/delhome", alias = {}, description = "")
	public static CommandResponse delhomeCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
