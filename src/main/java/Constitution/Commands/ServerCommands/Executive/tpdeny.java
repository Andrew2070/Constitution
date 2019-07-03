package Constitution.Commands.ServerCommands.Executive;
import Constitution.Commands.Engine.Command;
import Constitution.Commands.Engine.CommandResponse;
import Constitution.Utilities.PlayerUtilities;
import net.minecraft.command.ICommandSender;
import java.util.List;
public class tpdeny {
	@Command(name = "tpdeny", permission = "Constitution.exec.cmd.tpdeny", syntax = "/tpdeny", alias = {}, description = "")
	public static CommandResponse tpdenyCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
