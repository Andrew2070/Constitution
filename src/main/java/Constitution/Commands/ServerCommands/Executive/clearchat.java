package Constitution.Commands.ServerCommands.Executive;
import Constitution.Commands.Engine.Command;
import Constitution.Commands.Engine.CommandResponse;
import Constitution.Utilities.PlayerUtilities;
import net.minecraft.command.ICommandSender;
import java.util.List;
public class clearchat {
	@Command(name = "clearchat", permission = "Constitution.exec.cmd.clearchat", syntax = "/clearchat", alias = {}, description = "")
	public static CommandResponse clearchatCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
