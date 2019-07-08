package constitution.commands.servercommands.administrative;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import net.minecraft.command.ICommandSender;
public class whitelist {
	@Command(name = "whitelist", permission = "constitution.cmd.perm.whitelist", syntax = "/whitelist", alias = {}, description = "")
	public static CommandResponse whitelistCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
