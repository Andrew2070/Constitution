package constitution.permissions;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mojang.authlib.GameProfile;

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
		PermissionContext cxt = new PermissionContext(context.getPlayer().getCommandSenderEntity());

		// Special permission checks from EntityPlayerMP:
		if (PERM_SEED.equals(node) && ! VanillaUtilities.getMinecraftServer().isDedicatedServer())
			return true;
		if (PERM_TELL.equals(node) || PERM_HELP.equals(node) || PERM_ME.equals(node))
			return true;
		DefaultPermissionLevel level = permissions.get(node);
		if (level == null)
			return true;
		int opLevel = cxt.isPlayer() ? getOpLevel(context.getPlayer().getGameProfile()) : 0;
		return 4 <= opLevel;

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

}	
