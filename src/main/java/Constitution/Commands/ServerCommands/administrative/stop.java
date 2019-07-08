package constitution.commands.servercommands.administrative;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import net.minecraft.command.ICommandSender;
public class stop {
	@Command(name = "stop", permission = "constitution.cmd.perm.stop", syntax = "/stop", alias = {}, description = "")
	public static CommandResponse stopCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
