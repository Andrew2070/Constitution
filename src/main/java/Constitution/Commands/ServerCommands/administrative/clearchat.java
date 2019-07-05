package constitution.commands.servercommands.administrative;
import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import constitution.utilities.PlayerUtilities;
import net.minecraft.command.ICommandSender;
import java.util.List;
public class clearchat {
	@Command(name = "clearchat", permission = "constitution.cmd.perm.clearchat", syntax = "/clearchat", alias = {}, description = "")
	public static CommandResponse clearchatCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
