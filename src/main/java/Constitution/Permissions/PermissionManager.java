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
import constitution.utilities.ServerUtilities;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.server.permission.DefaultPermissionLevel;
import net.minecraftforge.server.permission.PermissionAPI;

public class PermissionManager {
	public static final String DEFAULT_COMMAND_NODE = "command.";
	public static final String PERM_COMMANDBLOCK = "commandblock";
	protected PermissionProvider permProvider;

	public Group.Container groups;
	public User.Container users;
	public Channel.Container channels;

	private GroupConfig groupConfig;
	private UserConfig userConfig;
	private ChannelConfig channelConfig;

	protected static Map<ICommand, String> commandPermissions = new WeakHashMap<ICommand, String>();

	public PermissionManager() {
	}

	public void preInitialization() {
		//JSON & GSON Loading Protocol: Must initialize array, then object's configuration class, then load/construct.
		permProvider = new PermissionProvider();
		PermissionAPI.setPermissionHandler(permProvider);
		this.users = new User.Container();
		this.groups = new Group.Container();
		this.channels = new Channel.Container();
		loadGroups();
		loadUsers();
		loadChannels();
		registerDefaultPermissions();
	}

	public void serverLoad() {

	}

	public void registerDefaultPermissions() {
		permProvider.registerNode(PERM_COMMANDBLOCK, DefaultPermissionLevel.OP, "CommandBlock");
		for (Group group : this.groups) {
			for (String node : group.permsContainer) {
				if (!permProvider.getRegisteredNodes().contains(node)) {
					permProvider.registerNode(node, DefaultPermissionLevel.ALL, "");
				}
			}
		}
		if (this.users!=null) {
			for (User user : this.users) {
				for (String node : user.permsContainer) {
					if (!permProvider.getRegisteredNodes().contains(node)) {
						permProvider.registerNode(node, DefaultPermissionLevel.ALL, "");
					}
				}
			}
		}
	}

	public void registerPermission(String permission, DefaultPermissionLevel level, String description) {
		permProvider.registerNode(permission, level, description);
	}

	public void registerServerCommandPermissions() {
		Map<String, ICommand> commands = FMLCommonHandler.instance().getMinecraftServerInstance().getCommandManager().getCommands();
		for (ICommand command : commands.values())
			if (!commandPermissions.containsKey(command))
				registerCommandPermission(command);
	}

	public void registerCommandPermission(ICommand command) {
		registerPermission(getCommandPermission(command), getCommandPermissionLevel(command), CommandManager.getDescriptionForCommand(command.getName()));
	}

	public String getCommandPermission(ICommand command) {
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

	public DefaultPermissionLevel getCommandPermissionLevel(ICommand command) {
		if (command instanceof CommandTemplate) {
			CommandTemplate cmd = (CommandTemplate) command;
			if (cmd != null) {
				return cmd.getPermissionLevel();
			}
		}
		return DefaultPermissionLevel.NONE;
	}

	public Boolean hasPermission(UUID uuid, String node) {
		EntityPlayerMP player = ServerUtilities.getMinecraftServer().getPlayerList().getPlayerByUUID(uuid);
		PermissionContext context = new PermissionContext(player.getCommandSenderEntity());
		return permProvider.hasPermission(player.getGameProfile(), node, context);
	}

	public Boolean hasPermission(PermissionContext context, String node) {
		return permProvider.hasPermission(context.getPlayer().getGameProfile(), node, context);
	}

	public boolean checkPermission(PermissionContext context, String permission) {
		return hasPermission(context, permission);
	}

	public boolean checkPermission(EntityPlayer player, String permission) {
		return checkPermission(new PermissionContext(player), permission);
	}

	public boolean checkPermission(ICommandSender sender, ICommand command, String permission) {
		return checkPermission(new PermissionContext(sender, command), permission);
	}

	public boolean checkPermission(ICommandSender sender, ICommand command) {
		return checkPermission(new PermissionContext(sender, command), getCommandPermission(command));
	}

	public boolean checkPermission(ICommandSender sender, String permission) {
		return checkPermission(new PermissionContext(sender), permission);
	}

	public void loadUsers() {
		this.userConfig = new UserConfig(ConstitutionMain.CONFIG_FOLDER + "JSON/Users.json", this);
		users.clear();
		userConfig.init(users);
		userConfig.clearGsonCache();
	}

	public void loadGroups() {
		this.groupConfig = new GroupConfig(ConstitutionMain.CONFIG_FOLDER + "JSON/Groups.json", this);
		groups.clear();
		groupConfig.init(groups);
		groupConfig.clearGsonCache();
	}

	public void loadChannels() {
		this.channelConfig = new ChannelConfig(ConstitutionMain.CONFIG_FOLDER + "JSON/Channels.json", this);
		channels.clear();
		channelConfig.init(channels);
		channelConfig.clearGsonCache();
	}

	public void saveGroups() {
		this.groupConfig = new GroupConfig(ConstitutionMain.CONFIG_FOLDER + "JSON/Groups.json", this);
		groupConfig.write(groups);
		groupConfig.clearGsonCache();
	}
	public void saveUsers() {
		this.userConfig = new UserConfig(ConstitutionMain.CONFIG_FOLDER + "JSON/Users.json", this);
		userConfig.write(users);
		userConfig.clearGsonCache();
	}
	public void saveChannels() {
		this.channelConfig = new ChannelConfig(ConstitutionMain.CONFIG_FOLDER + "JSON/Channels.json", this);
		channelConfig.write(channels);
		channelConfig.clearGsonCache();
	}
}
