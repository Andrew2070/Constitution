package constitution.commands.servercommands.administrative;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import net.minecraft.command.ICommandSender;
public class gamemode {
	@Command(name = "gamemode", permission = "constitution.cmd.perm.gamemode", syntax = "/gamemode", alias = {}, description = "")
	public static CommandResponse gamemodeCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
