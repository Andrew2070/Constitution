package constitution.commands.servercommands.administrative;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import net.minecraft.command.ICommandSender;
public class kill {
	@Command(name = "kill", permission = "constitution.cmd.perm.kill", syntax = "/kill", alias = {}, description = "")
	public static CommandResponse killCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
