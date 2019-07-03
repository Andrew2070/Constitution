package Constitution.Commands.ServerCommands.Executive;
import Constitution.Commands.Engine.Command;
import Constitution.Commands.Engine.CommandResponse;
import Constitution.Utilities.PlayerUtilities;
import net.minecraft.command.ICommandSender;
import java.util.List;
public class chatspy {
	@Command(name = "chatspy", permission = "Constitution.exec.cmd.chatspy", syntax = "/chatspy", alias = {}, description = "")
	public static CommandResponse chatspyCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
