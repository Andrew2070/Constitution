package constitution.commands.servercommands.general;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import net.minecraft.command.ICommandSender;
public class mail {
	@Command(name = "mail", permission = "constitution.cmd.perm.mail", syntax = "/mail", alias = {}, description = "")
	public static CommandResponse mailCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
