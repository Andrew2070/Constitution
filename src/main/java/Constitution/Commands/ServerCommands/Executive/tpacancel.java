package Constitution.Commands.ServerCommands.Executive;
import Constitution.Commands.Engine.Command;
import Constitution.Commands.Engine.CommandResponse;
import Constitution.Utilities.PlayerUtilities;
import net.minecraft.command.ICommandSender;
import java.util.List;
public class tpacancel {
	@Command(name = "tpacancel", permission = "Constitution.exec.cmd.tpacancel", syntax = "/tpacancel", alias = {}, description = "")
	public static CommandResponse tpacancelCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
