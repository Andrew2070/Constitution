package constitution.commands.servercommands.administrative;
import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import constitution.utilities.PlayerUtilities;
import net.minecraft.command.ICommandSender;
import java.util.List;
public class commandspy {
	@Command(name = "commandspy", permission = "constitution.cmd.perm.commandspy", syntax = "/commandspy", alias = {}, description = "")
	public static CommandResponse commandspyCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
