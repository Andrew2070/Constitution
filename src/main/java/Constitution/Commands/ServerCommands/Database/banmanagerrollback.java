package constitution.commands.servercommands.database;
import net.minecraft.command.ICommandSender;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import constitution.utilities.PlayerUtilities;
public class banmanagerrollback {
	@Command(name = "banmanagerrollback", permission = "Constitution.exec.cmd.banmanagerrollback", syntax = "/banmanagerrollback", alias = {}, description = "")
	public static CommandResponse banmanagerrollbackCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
