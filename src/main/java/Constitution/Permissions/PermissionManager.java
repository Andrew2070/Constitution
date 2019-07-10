package constitution.permissions;

import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;

import constitution.ConstitutionMain;
import constitution.chat.channels.Channel;
import constitution.commands.engine.CommandManager;
import constitution.commands.engine.CommandTemplate;
import constitution.configuration.ChannelConfig;
import constitution.configuration.GroupConfig;
import constitution.configuration.UserConfig;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.server.permission.DefaultPermissionLevel;
import net.minecraftforge.server.permission.PermissionAPI;

public class PermissionManager {
	public static final String DEFAULT_COMMAND_NODE = "command.";
	public static final String PERM_COMMANDBLOCK = "commandblock";
	private final static PermissionProvider permProvider = new PermissionProvider();
	
	public Group.Container groups;
	public User.Container users;
	public Channel.Container channels;
	
	private GroupConfig groupConfig;
	private UserConfig userConfig;
	private ChannelConfig channelConfig;
	
	protected static Map<ICommand, String> commandPermissions = new WeakHashMap<ICommand, String>();

	public PermissionManager() {
	}
	
	public static void preInitialization() {
		PermissionAPI.setPermissionHandler(permProvider);
		registerDefaultPermissions();
	}
	
	public void serverLoad() {
		this.groups = new Group.Container();
		this.users = new User.Container();
		this.channels = new Channel.Container();
		this.groupConfig = new GroupConfig(ConstitutionMain.CONFIG_FOLDER + "JSON/Groups.json");
		this.userConfig  = new UserConfig(ConstitutionMain.CONFIG_FOLDER + "JSON/Users.json");
		this.channelConfig = new ChannelConfig(ConstitutionMain.CONFIG_FOLDER + "JSON/Channels.json");
		loadConfigs();
	}
	
	public static void registerDefaultPermissions() {
		permProvider.registerNode(PERM_COMMANDBLOCK, DefaultPermissionLevel.OP, "CommandBlock");

	}
	
	public static void registerPermission(String permission, DefaultPermissionLevel level, String description) {
		permProvider.registerNode(permission, level, description);
	}
	
	public static void registerServerCommandPermissions() {
		Map<String, ICommand> commands = FMLCommonHandler.instance().getMinecraftServerInstance().getCommandManager().getCommands();
		for (ICommand command : commands.values())
			if (!commandPermissions.containsKey(command))
				registerCommandPermission(command);
	}
	
	public static void registerCommandPermission(ICommand command) {
		registerPermission(getCommandPermission(command), getCommandPermissionLevel(command), CommandManager.getDescriptionForCommand(command.getName()));
	}
	
	public static String getCommandPermission(ICommand command) {
		String node = "";
		if (commandPermissions.keySet().contains(command)) {
			node = commandPermissions.get(command);
		} else {
			if (CommandManager.getPermForCommand(command.getName())!=null) {
				node = CommandManager.getPermForCommand(command.getName());
			} else {
				node = DEFAULT_COMMAND_NODE + command.getName();
			}
		}
	return node;
	}
	
	public static DefaultPermissionLevel getCommandPermissionLevel(ICommand command) {
		if (command instanceof CommandTemplate) {
			CommandTemplate cmd = (CommandTemplate) command;
			if (cmd != null) {
				return cmd.getPermissionLevel();
			}
		}
		return DefaultPermissionLevel.NONE;
	}
	public static Boolean hasPermission(UUID uuid, String node) {
		return true;
	}
	public static boolean checkPermission(PermissionContext context, String permission) {
		return permProvider.hasPermission(context.getPlayer().getGameProfile(), permission, context);
	}

	public static boolean checkPermission(EntityPlayer player, String permission) {
		return checkPermission(new PermissionContext(player), permission);
	}

	public static boolean checkPermission(ICommandSender sender, ICommand command, String permission) {
		return checkPermission(new PermissionContext(sender, command), permission);
	}

	public static boolean checkPermission(ICommandSender sender, ICommand command) {
		return checkPermission(new PermissionContext(sender, command), getCommandPermission(command));
	}

	public static boolean checkPermission(ICommandSender sender, String permission) {
		return checkPermission(new PermissionContext(sender), permission);
	}

	public void loadConfigs() {
		groups.clear();
		users.clear();
		channels.clear();
		
		groupConfig.init(groups);
		userConfig.init(users);
		channelConfig.init(channels);
	}

	public void saveConfigs() {
		groupConfig.write(groups);
		userConfig.write(users);
		channelConfig.write(channels);
	}

	public void saveGroups() {
		groupConfig.write(groups);
	}
	public void saveUsers() {
		userConfig.write(users);
	}
	
	public void saveChannels() {
		channelConfig.write(channels);
	}
}
