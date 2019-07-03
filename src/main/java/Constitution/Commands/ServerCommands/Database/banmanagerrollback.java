package Constitution.Commands.ServerCommands.Database;
import Constitution.Commands.Engine.Command;
import Constitution.Commands.Engine.CommandResponse;
import Constitution.Utilities.PlayerUtilities;
import net.minecraft.command.ICommandSender;
import java.util.List;
public class banmanagerrollback {
	@Command(name = "banmanagerrollback", permission = "Constitution.exec.cmd.banmanagerrollback", syntax = "/banmanagerrollback", alias = {}, description = "")
	public static CommandResponse banmanagerrollbackCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
