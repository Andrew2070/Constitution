package constitution.commands.servercommands.administrative;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import net.minecraft.command.ICommandSender;
public class enderchest {
	@Command(name = "enderchest", permission = "constitution.cmd.perm.enderchest", syntax = "/enderchest", alias = {}, description = "")
	public static CommandResponse enderchestCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
