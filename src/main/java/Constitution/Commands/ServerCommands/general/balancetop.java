package constitution.commands.servercommands.general;
import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import constitution.utilities.PlayerUtilities;
import net.minecraft.command.ICommandSender;
import java.util.List;
public class balancetop {
	@Command(name = "balancetop", permission = "constitution.cmd.perm.balancetop", syntax = "/balancetop", alias = {}, description = "")
	public static CommandResponse balancetopCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
