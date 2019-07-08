package constitution.commands.servercommands.administrative;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import net.minecraft.command.ICommandSender;
public class tp {
	@Command(name = "tp", permission = "constitution.cmd.perm.tp", syntax = "/tp", alias = {}, description = "")
	public static CommandResponse tpCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
