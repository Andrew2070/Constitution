package Constitution.Commands.ServerCommands.Executive;
import Constitution.Commands.Engine.Command;
import Constitution.Commands.Engine.CommandResponse;
import Constitution.Utilities.PlayerUtilities;
import net.minecraft.command.ICommandSender;
import java.util.List;
public class clearinventory {
	@Command(name = "clearinventory", permission = "Constitution.exec.cmd.clearinventory", syntax = "/clearinventory", alias = {}, description = "")
	public static CommandResponse clearinventoryCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}