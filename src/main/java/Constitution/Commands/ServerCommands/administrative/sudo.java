package constitution.commands.servercommands.administrative;
import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import constitution.utilities.PlayerUtilities;
import net.minecraft.command.ICommandSender;
import java.util.List;
public class sudo {
	@Command(name = "sudo", permission = "constitution.cmd.perm.sudo", syntax = "/sudo", alias = {}, description = "")
	public static CommandResponse sudoCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}