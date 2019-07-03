package Constitution.Commands.ServerCommands.Database;
import Constitution.Commands.Engine.Command;
import Constitution.Commands.Engine.CommandResponse;
import Constitution.Utilities.PlayerUtilities;
import net.minecraft.command.ICommandSender;
import java.util.List;
public class banmanagerclearall {
	@Command(name = "banmanagerclearall", permission = "Constitution.exec.cmd.banmanagerclearall", syntax = "/banmanagerclearall", alias = {}, description = "")
	public static CommandResponse banmanagerclearallCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
