package Constitution.Commands.ServerCommands.Executive;
import Constitution.Commands.Engine.Command;
import Constitution.Commands.Engine.CommandResponse;
import Constitution.Utilities.PlayerUtilities;
import net.minecraft.command.ICommandSender;
import java.util.List;
public class unstuck {
	@Command(name = "unstuck", permission = "Constitution.exec.cmd.unstuck", syntax = "/unstuck", alias = {}, description = "")
	public static CommandResponse unstuckCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
