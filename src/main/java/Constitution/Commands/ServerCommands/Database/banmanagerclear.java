package Constitution.Commands.ServerCommands.Database;
import Constitution.Commands.Engine.Command;
import Constitution.Commands.Engine.CommandResponse;
import Constitution.Utilities.PlayerUtilities;
import net.minecraft.command.ICommandSender;
import java.util.List;
public class banmanagerclear {
	@Command(name = "banmanagerclear", permission = "Constitution.exec.cmd.banmanagerclear", syntax = "/banmanagerclear", alias = {}, description = "")
	public static CommandResponse banmanagerclearCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
