package Constitution.Commands.ServerCommands.Executive;
import Constitution.Commands.Engine.Command;
import Constitution.Commands.Engine.CommandResponse;
import Constitution.Utilities.PlayerUtilities;
import net.minecraft.command.ICommandSender;
import java.util.List;
public class nearbyplayers {
	@Command(name = "nearbyplayers", permission = "Constitution.exec.cmd.nearbyplayers", syntax = "/nearbyplayers", alias = {}, description = "")
	public static CommandResponse nearbyplayersCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
