package constitution.commands.servercommands.administrative;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import net.minecraft.command.ICommandSender;
public class fly {
	@Command(name = "fly", permission = "constitution.cmd.perm.fly", syntax = "/fly", alias = {}, description = "")
	public static CommandResponse flyCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
