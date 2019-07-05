package constitution.commands.servercommands.administrative;
import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import constitution.utilities.PlayerUtilities;
import net.minecraft.command.ICommandSender;
import java.util.List;
public class tppos {
	@Command(name = "tppos", permission = "constitution.cmd.perm.tppos", syntax = "/tppos", alias = {}, description = "")
	public static CommandResponse tpposCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
