package constitution.commands.servercommands.executive;
import net.minecraft.command.ICommandSender;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import constitution.utilities.PlayerUtilities;
public class tpaccept {
	@Command(name = "tpaccept", permission = "Constitution.exec.cmd.tpaccept", syntax = "/tpaccept", alias = {}, description = "")
	public static CommandResponse tpacceptCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
