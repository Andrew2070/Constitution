package constitution.commands.servercommands.executive;
import net.minecraft.command.ICommandSender;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import constitution.utilities.PlayerUtilities;
public class immobalize {
	@Command(name = "immobalize", permission = "Constitution.exec.cmd.immobalize", syntax = "/immobalize", alias = {}, description = "")
	public static CommandResponse immobalizeCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
