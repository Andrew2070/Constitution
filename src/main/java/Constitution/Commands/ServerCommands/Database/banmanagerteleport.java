package constitution.commands.servercommands.database;
import net.minecraft.command.ICommandSender;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import constitution.utilities.PlayerUtilities;
public class banmanagerteleport {
	@Command(name = "banmanagerteleport", permission = "Constitution.exec.cmd.banmanagerteleport", syntax = "/banmanagerteleport", alias = {}, description = "")
	public static CommandResponse banmanagerteleportCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
