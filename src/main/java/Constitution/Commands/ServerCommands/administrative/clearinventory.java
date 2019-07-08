package constitution.commands.servercommands.administrative;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import net.minecraft.command.ICommandSender;
public class clearinventory {
	@Command(name = "clearinventory", permission = "constitution.cmd.perm.clearinventory", syntax = "/clearinventory", alias = {}, description = "")
	public static CommandResponse clearinventoryCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
