package constitution.commands.servercommands.general;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import net.minecraft.command.ICommandSender;
public class pay {
	@Command(name = "pay", permission = "constitution.cmd.perm.pay", syntax = "/pay", alias = {}, description = "")
	public static CommandResponse payCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
