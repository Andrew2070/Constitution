package Constitution.Commands.ServerCommands.Executive;
import Constitution.Commands.Engine.Command;
import Constitution.Commands.Engine.CommandResponse;
import Constitution.Utilities.PlayerUtilities;
import net.minecraft.command.ICommandSender;
import java.util.List;
public class teleportdeny {
	@Command(name = "teleportdeny", permission = "Constitution.exec.cmd.teleportdeny", syntax = "/teleportdeny", alias = {}, description = "")
	public static CommandResponse teleportdenyCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
