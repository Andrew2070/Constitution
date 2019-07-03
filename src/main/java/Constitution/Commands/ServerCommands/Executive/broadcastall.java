package Constitution.Commands.ServerCommands.Executive;
import Constitution.Commands.Engine.Command;
import Constitution.Commands.Engine.CommandResponse;
import Constitution.Utilities.PlayerUtilities;
import net.minecraft.command.ICommandSender;
import java.util.List;
public class broadcastall {
	@Command(name = "broadcastall", permission = "Constitution.exec.cmd.broadcastall", syntax = "/broadcastall", alias = {}, description = "")
	public static CommandResponse broadcastallCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
