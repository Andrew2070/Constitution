package Constitution.Commands.ServerCommands.Executive;
import Constitution.Commands.Engine.Command;
import Constitution.Commands.Engine.CommandResponse;
import Constitution.Utilities.PlayerUtilities;
import net.minecraft.command.ICommandSender;
import java.util.List;
public class maildelete {
	@Command(name = "maildelete", permission = "Constitution.exec.cmd.maildelete", syntax = "/maildelete", alias = {}, description = "")
	public static CommandResponse maildeleteCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
