package Constitution.Commands.ServerCommands.Executive;
import Constitution.Commands.Engine.Command;
import Constitution.Commands.Engine.CommandResponse;
import Constitution.Utilities.PlayerUtilities;
import net.minecraft.command.ICommandSender;
import java.util.List;
public class tempmute {
	@Command(name = "tempmute", permission = "Constitution.exec.cmd.tempmute", syntax = "/tempmute", alias = {}, description = "")
	public static CommandResponse tempmuteCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
