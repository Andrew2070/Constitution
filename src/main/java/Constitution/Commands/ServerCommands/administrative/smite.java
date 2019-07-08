package constitution.commands.servercommands.administrative;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import net.minecraft.command.ICommandSender;
public class smite {
	@Command(name = "smite", permission = "constitution.cmd.perm.smite", syntax = "/smite", alias = {}, description = "")
	public static CommandResponse smiteCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
