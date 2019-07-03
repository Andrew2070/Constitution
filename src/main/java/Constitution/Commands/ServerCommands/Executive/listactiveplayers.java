package constitution.commands.servercommands.executive;
import net.minecraft.command.ICommandSender;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import constitution.utilities.PlayerUtilities;
public class listactiveplayers {
	@Command(name = "listactiveplayers", permission = "Constitution.exec.cmd.listactiveplayers", syntax = "/listactiveplayers", alias = {}, description = "")
	public static CommandResponse listactiveplayersCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
