package constitution.commands.servercommands.administrative;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import net.minecraft.command.ICommandSender;
public class unstuck {
	@Command(name = "unstuck", permission = "constitution.cmd.perm.unstuck", syntax = "/unstuck", alias = {}, description = "")
	public static CommandResponse unstuckCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
