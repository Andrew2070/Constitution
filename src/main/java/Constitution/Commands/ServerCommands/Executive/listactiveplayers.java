package Constitution.Commands.ServerCommands.Executive;
import Constitution.Commands.Engine.Command;
import Constitution.Commands.Engine.CommandResponse;
import Constitution.Utilities.PlayerUtilities;
import net.minecraft.command.ICommandSender;
import java.util.List;
public class listactiveplayers {
	@Command(name = "listactiveplayers", permission = "Constitution.exec.cmd.listactiveplayers", syntax = "/listactiveplayers", alias = {}, description = "")
	public static CommandResponse listactiveplayersCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
