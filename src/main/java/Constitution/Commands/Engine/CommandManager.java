package constitution.commands.engine;



import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import constitution.ConstitutionMain;
import constitution.commands.engine.registrar.CommandRegistrar;
import constitution.commands.engine.registrar.ICommandRegistrar;
import constitution.exceptions.CommandException;
import constitution.localization.Localization;
import constitution.permissions.PermissionManager;
import constitution.utilities.WikiUtilities;
import net.minecraft.command.ICommandSender;
public class CommandManager {

	/**
	 * Registrar used to register any commands. Offers compatibility for Bukkit
	 * and ForgeEssentials
	 */
	private static final ICommandRegistrar registrar = makeRegistrar();

	private static final List<CommandTree> commandTrees = new ArrayList<CommandTree>();

	public static final String ROOT_PERM_NODE = "ROOT";

	private CommandManager() {
	}

	/**
	 * It is enforced that the class has to contain ONE root command .
	 */
	public static void registerCommands(Class<?> clazz, String rootPerm, Localization local, PermissionManager permissionManager) {
		CommandTreeNode root = null;
		CommandTree commandTree = rootPerm == null ? null : getTree(rootPerm);
		Map<Command, Method> nodes = new HashMap<Command, Method>();
		for (final Method method : clazz.getDeclaredMethods()) {
			if (method.isAnnotationPresent(Command.class)) {
				if (isMethodValid(method)) {
					Command command = method.getAnnotation(Command.class);
					if (command.parentName().equals(ROOT_PERM_NODE)) {
						if (commandTree == null) {
							root = new CommandTreeNode(command, method);
						} else {
							throw new CommandException("Class " + clazz.getName() + " has more than one root command.");
						}
					} else {
						nodes.put(command, method);
					}
				} else {
					ConstitutionMain.logger.info("Method " + method.getName() + " from class " + clazz.getName()
							+ " is not valid for command usage");
				}
			}
		}
		if (commandTree == null) {
			if (root == null) {
				throw new CommandException("Class " + clazz.getName() + " has no root command.");
			} else {
				commandTree = new CommandTree(root, local, permissionManager);
				commandTrees.add(commandTree);
			}
		}

		registrar.registerCommand(new CommandTemplate(commandTree), commandTree.getRoot().getAnnotation().permission(),
				false);
		WikiUtilities.nodeDescriptions.put(commandTree.getRoot().getAnnotation().permission(), commandTree.getRoot().getAnnotation().description());
		constructTree(commandTree.getRoot(), nodes);

		for (Map.Entry<Command, Method> entry : nodes.entrySet()) {
			ConstitutionMain.logger.info("Missing parent: " + entry.getKey().permission() + " |<-| " + entry.getKey().parentName());
		}
	}

	public static CommandTree getTree(String basePerm) {
		for (CommandTree tree : commandTrees) {
			if (tree.getRoot().getAnnotation().permission().equals(basePerm))
				return tree;
		}
		return null;
	}

	public static CommandTree getTreeFromPermission(String perm) {
		for (CommandTree tree : commandTrees) {
			if (tree.hasCommandNode(perm)) {
				return tree;
			}
		}
		return null;
	}

	public static String getPermForCommand(String commandName) {
		for (CommandTree tree : commandTrees) {
			if (tree.getRoot().getLocalizedName().equals(commandName)) {
				return tree.getRoot().getAnnotation().permission();
			}
		}
		return null;
	}
	
	
	public static String getDescriptionForCommand(String commandName) {
		for (CommandTree tree : commandTrees) {
			if (tree.getRoot().getLocalizedName().equals(commandName)) {
				return tree.getRoot().getAnnotation().description();
			}
		}
	return "No Description Found";
	}

	private static CommandTreeNode findNode(CommandTreeNode root, String perm) {
		if (root.getAnnotation().permission().equals(perm))
			return root;

		for (CommandTreeNode child : root.getChildren()) {
			CommandTreeNode foundNode = findNode(child, perm);
			if (foundNode != null)
				return foundNode;
		}
		return null;
	}

	private static void constructTree(CommandTreeNode root, Map<Command, Method> nodes) {
		int currentNodeNumber;
		do {
			currentNodeNumber = nodes.size();
			for (Iterator<Map.Entry<Command, Method>> it = nodes.entrySet().iterator(); it.hasNext();) {
				Map.Entry<Command, Method> entry = it.next();

				CommandTreeNode parent = findNode(root, entry.getKey().parentName());

				if (parent != null) {
					parent.addChild(new CommandTreeNode(parent, entry.getKey(), entry.getValue()));
					if (!root.getLocal().hasLocalization(entry.getKey().permission() + ".help")) {
						ConstitutionMain.logger.info("Missing help: " + entry.getKey().permission() + ".help"); 
					}
					it.remove();
				}
			}
		} while (currentNodeNumber != nodes.size());
	}

	private static boolean isMethodValid(Method method) {
		if (!method.getReturnType().equals(CommandResponse.class))
			return false;

		if (method.getParameterTypes().length != 2)
			return false;

		if (!(method.getParameterTypes()[0].equals(ICommandSender.class)
				&& method.getParameterTypes()[1].equals(List.class)))
			return false;
		return true;
	}

	private static ICommandRegistrar makeRegistrar() {
			return new CommandRegistrar();
	}
}
