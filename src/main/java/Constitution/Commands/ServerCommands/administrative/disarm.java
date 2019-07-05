package constitution.commands.servercommands.administrative;
import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import constitution.utilities.PlayerUtilities;
import net.minecraft.command.ICommandSender;
import java.util.List;
public class disarm {
	@Command(name = "disarm", permission = "constitution.cmd.perm.disarm", syntax = "/disarm", alias = {}, description = "")
	public static CommandResponse disarmCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
