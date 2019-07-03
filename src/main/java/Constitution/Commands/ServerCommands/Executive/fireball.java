package constitution.commands.servercommands.executive;
import net.minecraft.command.ICommandSender;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import constitution.utilities.PlayerUtilities;
public class fireball {
	@Command(name = "fireball", permission = "Constitution.exec.cmd.fireball", syntax = "/fireball", alias = {}, description = "")
	public static CommandResponse fireballCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
