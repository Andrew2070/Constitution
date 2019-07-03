package constitution.commands.servercommands.executive;
import net.minecraft.command.ICommandSender;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import constitution.utilities.PlayerUtilities;
public class enderchest {
	@Command(name = "enderchest", permission = "Constitution.exec.cmd.enderchest", syntax = "/enderchest", alias = {}, description = "")
	public static CommandResponse enderchestCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
