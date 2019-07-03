package Constitution.Commands.ServerCommands.Database;
import Constitution.Commands.Engine.Command;
import Constitution.Commands.Engine.CommandResponse;
import Constitution.Utilities.PlayerUtilities;
import net.minecraft.command.ICommandSender;
import java.util.List;
public class banmanagergethistory {
	@Command(name = "banmanagergethistory", permission = "Constitution.exec.cmd.banmanagergethistory", syntax = "/banmanagergethistory", alias = {}, description = "")
	public static CommandResponse banmanagergethistoryCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
