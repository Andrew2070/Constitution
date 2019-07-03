package constitution.commands.servercommands.executive;
import net.minecraft.command.ICommandSender;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import constitution.utilities.PlayerUtilities;
public class tppos {
	@Command(name = "tppos", permission = "Constitution.exec.cmd.tppos", syntax = "/tppos", alias = {}, description = "")
	public static CommandResponse tpposCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
