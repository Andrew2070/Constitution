package constitution.commands.servercommands.administrative;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import net.minecraft.command.ICommandSender;
public class operator {
	@Command(name = "operator", permission = "constitution.cmd.perm.operator", syntax = "/operator", alias = {}, description = "")
	public static CommandResponse operatorCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
