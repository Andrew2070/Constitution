package Constitution.Commands.ServerCommands.Executive;
import Constitution.Commands.Engine.Command;
import Constitution.Commands.Engine.CommandResponse;
import Constitution.Utilities.PlayerUtilities;
import net.minecraft.command.ICommandSender;
import java.util.List;
public class tpohere {
	@Command(name = "tpohere", permission = "Constitution.exec.cmd.tpohere", syntax = "/tpohere", alias = {}, description = "")
	public static CommandResponse tpohereCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
