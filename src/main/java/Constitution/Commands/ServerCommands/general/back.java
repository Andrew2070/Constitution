package constitution.commands.servercommands.general;
import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import constitution.utilities.PlayerUtilities;
import net.minecraft.command.ICommandSender;
import java.util.List;
public class back {
	@Command(name = "back", permission = "constitution.cmd.perm.back", syntax = "/back", alias = {}, description = "")
	public static CommandResponse backCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}