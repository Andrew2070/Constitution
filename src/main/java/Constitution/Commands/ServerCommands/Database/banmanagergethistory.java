package constitution.commands.servercommands.database;
import net.minecraft.command.ICommandSender;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import constitution.utilities.PlayerUtilities;
public class banmanagergethistory {
	@Command(name = "banmanagergethistory", permission = "Constitution.exec.cmd.banmanagergethistory", syntax = "/banmanagergethistory", alias = {}, description = "")
	public static CommandResponse banmanagergethistoryCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
