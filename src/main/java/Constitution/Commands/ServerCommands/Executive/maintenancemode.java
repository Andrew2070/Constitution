package constitution.commands.servercommands.executive;
import net.minecraft.command.ICommandSender;
import java.util.List;

import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import constitution.utilities.PlayerUtilities;
public class maintenancemode {
	@Command(name = "maintenancemode", permission = "Constitution.exec.cmd.maintenancemode", syntax = "/maintenancemode", alias = {}, description = "")
	public static CommandResponse maintenancemodeCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}