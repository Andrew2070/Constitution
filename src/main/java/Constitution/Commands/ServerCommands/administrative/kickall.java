package constitution.commands.servercommands.administrative;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import net.minecraft.command.ICommandSender;
public class kickall {
	@Command(name = "kickall", permission = "constitution.cmd.perm.kickall", syntax = "/kickall", alias = {}, description = "")
	public static CommandResponse kickallCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
