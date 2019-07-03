package Constitution.Commands.ServerCommands.Executive;
import Constitution.Commands.Engine.Command;
import Constitution.Commands.Engine.CommandResponse;
import Constitution.Utilities.PlayerUtilities;
import net.minecraft.command.ICommandSender;
import java.util.List;
public class immobalize {
	@Command(name = "immobalize", permission = "Constitution.exec.cmd.immobalize", syntax = "/immobalize", alias = {}, description = "")
	public static CommandResponse immobalizeCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
