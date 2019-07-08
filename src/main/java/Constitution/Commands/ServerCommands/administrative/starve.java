package constitution.commands.servercommands.administrative;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import net.minecraft.command.ICommandSender;
public class starve {
	@Command(name = "starve", permission = "constitution.cmd.perm.starve", syntax = "/starve", alias = {}, description = "")
	public static CommandResponse starveCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
