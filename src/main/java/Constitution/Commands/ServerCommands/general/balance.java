package constitution.commands.servercommands.general;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import net.minecraft.command.ICommandSender;
public class balance {
	@Command(name = "balance", permission = "constitution.cmd.perm.balance", syntax = "/balance", alias = {}, description = "")
	public static CommandResponse balanceCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
