package constitution.commands.servercommands.executive;
import net.minecraft.command.ICommandSender;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import constitution.utilities.PlayerUtilities;
public class tpahere {
	@Command(name = "tpahere", permission = "Constitution.exec.cmd.tpahere", syntax = "/tpahere", alias = {}, description = "")
	public static CommandResponse tpahereCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
