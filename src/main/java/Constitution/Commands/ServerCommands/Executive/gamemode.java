package constitution.commands.servercommands.executive;
import net.minecraft.command.ICommandSender;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import constitution.utilities.PlayerUtilities;
public class gamemode {
	@Command(name = "gamemode", permission = "Constitution.exec.cmd.gamemode", syntax = "/gamemode", alias = {}, description = "")
	public static CommandResponse gamemodeCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
