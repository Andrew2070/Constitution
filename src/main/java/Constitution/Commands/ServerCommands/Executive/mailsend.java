package Constitution.Commands.ServerCommands.Executive;
import Constitution.Commands.Engine.Command;
import Constitution.Commands.Engine.CommandResponse;
import Constitution.Utilities.PlayerUtilities;
import net.minecraft.command.ICommandSender;
import java.util.List;
public class mailsend {
	@Command(name = "mailsend", permission = "Constitution.exec.cmd.mailsend", syntax = "/mailsend", alias = {}, description = "")
	public static CommandResponse mailsendCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
