package constitution.commands.servercommands.executive;
import net.minecraft.command.ICommandSender;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import constitution.utilities.PlayerUtilities;
public class suicide {
	@Command(name = "suicide", permission = "Constitution.exec.cmd.suicide", syntax = "/suicide", alias = {}, description = "")
	public static CommandResponse suicideCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
