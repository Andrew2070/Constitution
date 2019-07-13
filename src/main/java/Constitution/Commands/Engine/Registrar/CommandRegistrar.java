package constitution.commands.engine.registrar;

import net.minecraft.command.CommandHandler;
import net.minecraft.command.ICommand;

/**
 * Standard vanilla command registrar
 */
public class CommandRegistrar implements ICommandRegistrar {
	protected CommandHandler commandHandler;

	public CommandRegistrar() {
		this.commandHandler = (CommandHandler) constitution.utilities.ServerUtilities.getMinecraftServer().getCommandManager();
	}

	@Override
	public void registerCommand(ICommand cmd, String permNode, boolean defaultPerm) {
		this.commandHandler.registerCommand(cmd);
	}
}