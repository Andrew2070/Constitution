package Constitution.Commands.ServerCommands.Executive;
import Constitution.Commands.Engine.Command;
import Constitution.Commands.Engine.CommandResponse;
import Constitution.Utilities.PlayerUtilities;
import net.minecraft.command.ICommandSender;
import java.util.List;
public class breakblock {
	@Command(name = "breakblock", permission = "Constitution.exec.cmd.breakblock", syntax = "/breakblock", alias = {}, description = "")
	public static CommandResponse breakblockCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}