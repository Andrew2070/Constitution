package Constitution.Commands.Engine.Registrar;

import net.minecraft.command.CommandHandler;
import net.minecraft.command.ICommand;

/**
 * Standard vanilla command registrar
 */
public class VanillaCommandRegistrar implements ICommandRegistrar {
	protected CommandHandler commandHandler;

	public VanillaCommandRegistrar() {
		this.commandHandler = (CommandHandler) Constitution.Utilities.VanillaUtilities.getMinecraftServer().getCommandManager();
	}

	@Override
	public void registerCommand(ICommand cmd, String permNode, boolean defaultPerm) {
		this.commandHandler.registerCommand(cmd);
	}
}