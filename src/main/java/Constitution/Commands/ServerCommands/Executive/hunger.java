package constitution.commands.servercommands.executive;
import net.minecraft.command.ICommandSender;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import constitution.utilities.PlayerUtilities;
public class hunger {
	@Command(name = "hunger", permission = "Constitution.exec.cmd.hunger", syntax = "/hunger", alias = {}, description = "")
	public static CommandResponse hungerCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
