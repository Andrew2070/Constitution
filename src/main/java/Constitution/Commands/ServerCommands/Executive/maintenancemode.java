package Constitution.Commands.ServerCommands.Executive;
import Constitution.Commands.Engine.Command;
import Constitution.Commands.Engine.CommandResponse;
import Constitution.Utilities.PlayerUtilities;
import net.minecraft.command.ICommandSender;
import java.util.List;
public class maintenancemode {
	@Command(name = "maintenancemode", permission = "Constitution.exec.cmd.maintenancemode", syntax = "/maintenancemode", alias = {}, description = "")
	public static CommandResponse maintenancemodeCommandMethod(ICommandSender sender, List<String> args) {
		//TODO: Implement Command Instructions

		return CommandResponse.DONE;
	}
}
