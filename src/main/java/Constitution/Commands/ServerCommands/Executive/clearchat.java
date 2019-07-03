package constitution.commands.servercommands.executive;
import net.minecraft.command.ICommandSender;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import constitution.utilities.PlayerUtilities;
public class clearchat {
	@Command(name = "clearchat", permission = "Constitution.exec.cmd.clearchat", syntax = "/clearchat", alias = {}, description = "")
	public static CommandResponse clearchatCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
