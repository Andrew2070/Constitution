package constitution.commands.servercommands.administrative;
import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import constitution.utilities.PlayerUtilities;
import net.minecraft.command.ICommandSender;
import java.util.List;
public class setbalance {
	@Command(name = "setbalance", permission = "constitution.cmd.perm.setbalance", syntax = "/setbalance", alias = {}, description = "")
	public static CommandResponse setbalanceCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}