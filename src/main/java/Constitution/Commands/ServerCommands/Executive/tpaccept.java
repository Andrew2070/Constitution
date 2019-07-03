package Constitution.Commands.ServerCommands.Executive;
import Constitution.Commands.Engine.Command;
import Constitution.Commands.Engine.CommandResponse;
import Constitution.Utilities.PlayerUtilities;
import net.minecraft.command.ICommandSender;
import java.util.List;
public class tpaccept {
	@Command(name = "tpaccept", permission = "Constitution.exec.cmd.tpaccept", syntax = "/tpaccept", alias = {}, description = "")
	public static CommandResponse tpacceptCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
