package constitution.commands.servercommands.administrative;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import net.minecraft.command.ICommandSender;
public class repair {
	@Command(name = "repair", permission = "constitution.cmd.perm.repair", syntax = "/repair", alias = {}, description = "")
	public static CommandResponse repairCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
