package constitution.commands.servercommands.administrative;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import net.minecraft.command.ICommandSender;
public class immobalize {
	@Command(name = "immobalize", permission = "constitution.cmd.perm.immobalize", syntax = "/immobalize", alias = {}, description = "")
	public static CommandResponse immobalizeCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
