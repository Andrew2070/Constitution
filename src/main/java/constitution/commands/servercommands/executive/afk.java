package constitution.commands.servercommands.executive;
import constitution.chat.ChatManager;
import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import constitution.utilities.ServerUtilities;
import constitution.permissions.Group;
import constitution.permissions.User;
import constitution.permissions.PermissionManager;
import net.minecraft.command.ICommandSender;
import java.util.List;
public class afk {
	@Command(name = "afk",
			permission = "constitution.cmd.afk",
			syntax = "/afk",
			alias = {},
			description = "Toggle AFK Status")
	public static CommandResponse afkCommand(ICommandSender sender, List<String> args) {
		
		User user = ServerUtilities.getManager().users.get(sender.getCommandSenderEntity().getUniqueID());
		
		if (user.getAFK() == false) {
			user.setAFK(true);
			ChatManager.send(sender, "constitution.cmd.afk.toggle.true");
			return CommandResponse.DONE;
		}
		
		if (user.getAFK() == true) {
			user.setAFK(false);
			ChatManager.send(sender, "constitution.cmd.afk.toggle.false");
			return CommandResponse.DONE;
		}
		return CommandResponse.DONE;
	}
}
