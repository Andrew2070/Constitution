package Constitution.Commands.ServerCommands.Executive;
import Constitution.Commands.Engine.Command;
import Constitution.Commands.Engine.CommandResponse;
import Constitution.Utilities.PlayerUtilities;
import net.minecraft.command.ICommandSender;
import java.util.List;
public class enderchest {
	@Command(name = "enderchest", permission = "Constitution.exec.cmd.enderchest", syntax = "/enderchest", alias = {}, description = "")
	public static CommandResponse enderchestCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
