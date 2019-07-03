package Constitution.Commands.ServerCommands.Database;
import Constitution.Commands.Engine.Command;
import Constitution.Commands.Engine.CommandResponse;
import Constitution.Utilities.PlayerUtilities;
import net.minecraft.command.ICommandSender;
import java.util.List;
public class banmanagerteleport {
	@Command(name = "banmanagerteleport", permission = "Constitution.exec.cmd.banmanagerteleport", syntax = "/banmanagerteleport", alias = {}, description = "")
	public static CommandResponse banmanagerteleportCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
