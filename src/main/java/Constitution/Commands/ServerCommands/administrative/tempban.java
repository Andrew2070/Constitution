package constitution.commands.servercommands.administrative;
import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import constitution.utilities.PlayerUtilities;
import net.minecraft.command.ICommandSender;
import java.util.List;
public class tempban {
	@Command(name = "tempban", permission = "constitution.cmd.perm.tempban", syntax = "/tempban", alias = {}, description = "")
	public static CommandResponse tempbanCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
