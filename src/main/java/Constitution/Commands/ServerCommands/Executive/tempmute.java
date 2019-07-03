package constitution.commands.servercommands.executive;
import net.minecraft.command.ICommandSender;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import constitution.utilities.PlayerUtilities;
public class tempmute {
	@Command(name = "tempmute", permission = "Constitution.exec.cmd.tempmute", syntax = "/tempmute", alias = {}, description = "")
	public static CommandResponse tempmuteCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
