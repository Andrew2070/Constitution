package constitution.commands.servercommands.administrative;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import net.minecraft.command.ICommandSender;
public class ipban {
	@Command(name = "ipban", permission = "constitution.cmd.perm.ipban", syntax = "/ipban", alias = {}, description = "")
	public static CommandResponse ipbanCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
