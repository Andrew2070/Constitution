package constitution.commands.servercommands.executive;
import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import constitution.utilities.ServerUtilities;
import constitution.permissions.Group;
import constitution.permissions.User;
import constitution.permissions.PermissionManager;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

import java.util.List;
public class broadcast {
	@Command(name = "broadcast",
			permission = "constitution.cmd.broadcast",
			syntax = "/broadcast <message>",
			alias = {},
			description = "Send A Message To The Whole Server")
	public static CommandResponse broadcastCommand(ICommandSender sender, List<String> args) {
		
		if (args.size() < 1) {
			return CommandResponse.SEND_SYNTAX;
		}
		
		ITextComponent message = new TextComponentString("&4[Server]&f" + args.get(0).replaceAll("\u0026([\\da-fk-or])", "\u00A7$1"));
		
		for (EntityPlayerMP player : ServerUtilities.getMinecraftServer().getPlayerList().getPlayers()) {
			player.sendMessage(message);
		}
		return CommandResponse.DONE;
	}
}
