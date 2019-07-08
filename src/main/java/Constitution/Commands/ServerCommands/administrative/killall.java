package constitution.commands.servercommands.administrative;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import net.minecraft.command.ICommandSender;
public class killall {
	@Command(name = "killall", permission = "constitution.cmd.perm.killall", syntax = "/killall", alias = {}, description = "")
	public static CommandResponse killallCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
