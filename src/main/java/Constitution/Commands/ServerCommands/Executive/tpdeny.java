package constitution.commands.servercommands.executive;
import net.minecraft.command.ICommandSender;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import constitution.utilities.PlayerUtilities;
public class tpdeny {
	@Command(name = "tpdeny", permission = "Constitution.exec.cmd.tpdeny", syntax = "/tpdeny", alias = {}, description = "")
	public static CommandResponse tpdenyCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
