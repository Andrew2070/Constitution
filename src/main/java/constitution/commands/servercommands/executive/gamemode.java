package constitution.commands.servercommands.executive;
import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.List;
public class gamemode {
	@Command(name = "gamemode", permission = "constitution.cmd.gamemode", syntax = "/gamemode", alias = {}, description = "")
	public static CommandResponse gamemodeCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions
		EntityPlayerMP player =  (EntityPlayerMP) sender.getCommandSenderEntity();
		return CommandResponse.DONE;
	}
}
