package constitution.commands.servercommands.general;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import net.minecraft.command.ICommandSender;
public class balancetop {
	@Command(name = "balancetop", permission = "constitution.cmd.perm.balancetop", syntax = "/balancetop", alias = {}, description = "")
	public static CommandResponse balancetopCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
