package constitution.commands.servercommands.executive;
import net.minecraft.command.ICommandSender;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import constitution.utilities.PlayerUtilities;
public class tpo {
	@Command(name = "tpo", permission = "Constitution.exec.cmd.tpo", syntax = "/tpo", alias = {}, description = "")
	public static CommandResponse tpoCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
