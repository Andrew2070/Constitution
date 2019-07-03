package Constitution.Commands.ServerCommands.Executive;
import Constitution.Commands.Engine.Command;
import Constitution.Commands.Engine.CommandResponse;
import Constitution.Utilities.PlayerUtilities;
import net.minecraft.command.ICommandSender;
import java.util.List;
public class tptoggle {
	@Command(name = "tptoggle", permission = "Constitution.exec.cmd.tptoggle", syntax = "/tptoggle", alias = {}, description = "")
	public static CommandResponse tptoggleCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
