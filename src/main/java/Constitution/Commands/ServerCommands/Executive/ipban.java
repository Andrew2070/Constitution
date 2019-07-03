package constitution.commands.servercommands.executive;
import net.minecraft.command.ICommandSender;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import constitution.utilities.PlayerUtilities;
public class ipban {
	@Command(name = "ipban", permission = "Constitution.exec.cmd.ipban", syntax = "/ipban", alias = {}, description = "")
	public static CommandResponse ipbanCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
