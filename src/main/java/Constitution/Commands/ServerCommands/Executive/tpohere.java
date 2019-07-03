package constitution.commands.servercommands.executive;
import net.minecraft.command.ICommandSender;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import constitution.utilities.PlayerUtilities;
public class tpohere {
	@Command(name = "tpohere", permission = "Constitution.exec.cmd.tpohere", syntax = "/tpohere", alias = {}, description = "")
	public static CommandResponse tpohereCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
