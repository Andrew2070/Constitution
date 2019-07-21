package constitution.commands.servercommands.executive;
import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import constitution.utilities.ServerUtilities;
import constitution.permissions.Group;
import constitution.permissions.User;
import constitution.permissions.PermissionManager;
import net.minecraft.command.ICommandSender;
import java.util.List;
public class killall {
	@Command(name = "killall", permission = "constitution.cmd.killall", syntax = "/killall", alias = {}, description = "")
	public static CommandResponse killallCommand(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
