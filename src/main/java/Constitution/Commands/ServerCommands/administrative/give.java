package constitution.commands.servercommands.administrative;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import net.minecraft.command.ICommandSender;
public class give {
	@Command(name = "give", permission = "constitution.cmd.perm.give", syntax = "/give", alias = {}, description = "")
	public static CommandResponse giveCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
