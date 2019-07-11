package constitution.permissions;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mojang.authlib.GameProfile;

import constitution.ConstitutionMain;
import constitution.configuration.Config;
import constitution.utilities.PlayerUtilities;
import constitution.utilities.VanillaUtilities;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.UserListOpsEntry;
import net.minecraftforge.server.permission.DefaultPermissionLevel;
import net.minecraftforge.server.permission.IPermissionHandler;
import net.minecraftforge.server.permission.context.IContext;
public class PermissionProvider implements IPermissionHandler{

	private static final String PERM_SEED = PermissionManager.DEFAULT_COMMAND_NODE + ".seed";
	private static final String PERM_TELL = PermissionManager.DEFAULT_COMMAND_NODE + ".tell";
	private static final String PERM_HELP = PermissionManager.DEFAULT_COMMAND_NODE + ".help";
	private static final String PERM_ME = PermissionManager.DEFAULT_COMMAND_NODE + ".me";
	protected static final Map<String, DefaultPermissionLevel> permissions = new HashMap<String, DefaultPermissionLevel>();
	protected static final Map<String, String> permissionDescriptions = new HashMap<String, String>();

	@Override
	public void registerNode(String node, DefaultPermissionLevel level, String desc) {
		permissions.put(node, level);
		permissionDescriptions.put(node, desc);
	}
	@Override
	public Collection<String> getRegisteredNodes() {
		List<String>registeredNodes = new ArrayList<String>();
		for (String node : permissions.keySet()) {
			if (!registeredNodes.contains(node)) {
				registeredNodes.add(node);
			}
		}
		return registeredNodes;
	}
	@Override
	public boolean hasPermission(GameProfile profile, String node, IContext context) {
		Boolean permLevel = false;
		PermissionManager manager = PlayerUtilities.getManager();
		if (profile.getId() == context.getPlayer().getUniqueID()) {
			User user = manager.users.get(profile.getId());
			if (!user.permsContainer.isEmpty() && user.permsContainer!=null & user.permsContainer.contains(node)) {
				if (searchNodes(user.permsContainer, node)) {
					permLevel = true;
				} else {
					if (user.getDominantGroup()!=null && manager.groups.contains(user.getDominantGroup())) {
						Group group = user.getDominantGroup();
						if (searchNodes(group.permsContainer, node)) {
							permLevel = true;
						} else {
							for (Group parent : group.parents) {
								if (searchNodes(parent.permsContainer, node)) {
									permLevel = true;
								}
							}
						}
					} else {
						if (manager.groups.contains(user.getGroups())) {
							Group group = user.getGroups();
							if (searchNodes(group.permsContainer, node)) {
								permLevel = true;
							} else {
								for (Group parent : group.parents) {
									if (searchNodes(parent.permsContainer, node)) {
										permLevel = true;
									}
								}
							}
						}
					}
				}
			}
			//FailSafes For (Non-Authorized) Users with (Non-Problematic) context(s) or exceptions:
			if (PERM_SEED.equals(node) && ! VanillaUtilities.getMinecraftServer().isDedicatedServer()) {
				permLevel = true;
			}
			if (PERM_TELL.equals(node) || PERM_HELP.equals(node) || PERM_ME.equals(node)) {
				permLevel = true;
			}
			if (Config.instance.fullAccessForOPS.get() && PlayerUtilities.isOP(profile.getId())) {
				ConstitutionMain.logger.warning(("Operator: " + profile.getName() + " Exempted From Permission Checking!"));
				permLevel = true;
			}
		} 
	return permLevel;
	}
	@Override
	public String getNodeDescription(String node) {
		return permissionDescriptions.get(node);
	}
	protected int getOpLevel(GameProfile gameProfile) {
		MinecraftServer server = VanillaUtilities.getMinecraftServer();
		if (!server.getPlayerList().canSendCommands(gameProfile)) {
			return 0;
		}
		UserListOpsEntry entry = server.getPlayerList().getOppedPlayers().getEntry(gameProfile);
		return entry != null ? entry.getPermissionLevel() : server.getServer().getOpPermissionLevel();
	}
	
	protected boolean searchNodes(PermissionsContainer container, String permission) {
		Boolean permLevel = false;
		if (container.contains(permission)) {
			permLevel = true;
		}
		for (String p : container) {
			if (p.endsWith("*")) {
				if (permission.startsWith(p.substring(0, p.length() - 1))) {
					permLevel = true;
				} else if (p.startsWith("-") && permission.startsWith(p.substring(1, p.length() - 1))) {
					permLevel = false;
				}
			} else {
				if (permission.equals(p)) {
					permLevel = true;
				} else if (p.startsWith("-") && permission.equals(p.substring(1))) {
					permLevel = false;
				}
			}
		}
		return permLevel;
	}
}	
