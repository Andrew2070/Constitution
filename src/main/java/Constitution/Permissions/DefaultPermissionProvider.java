package Constitution.Permissions;



import java.util.HashMap;
import java.util.Map;

import com.mojang.authlib.GameProfile;

import Constitution.Utilities.VanillaUtilities;
import net.minecraft.server.management.UserListOpsEntry;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class DefaultPermissionProvider implements IPermissionProvider {

	private static final String PERM_SEED = PermissionManager.DEFAULT_COMMAND_NODE + ".seed";
	private static final String PERM_TELL = PermissionManager.DEFAULT_COMMAND_NODE + ".tell";
	private static final String PERM_HELP = PermissionManager.DEFAULT_COMMAND_NODE + ".help";
	private static final String PERM_ME = PermissionManager.DEFAULT_COMMAND_NODE + ".me";

	protected static final Map<String, PermissionLevel> permissions = new HashMap<String, PermissionLevel>();

	@Override
	public boolean checkPermission(PermissionContext context, String permission) {
		// Special permission checks from EntityPlayerMP
		if (PERM_SEED.equals(permission) && ! VanillaUtilities.getMinecraftServer().isDedicatedServer())
			return true;
		if (PERM_TELL.equals(permission) || PERM_HELP.equals(permission) || PERM_ME.equals(permission))
			return true;

		PermissionLevel level = permissions.get(permission);
		if (level == null)
			return true;
		int opLevel = context.isPlayer() ? getOpLevel(context.getPlayer().getGameProfile()) : 0;
		return level.getOpLevel() <= opLevel;
	}

	@Override
	public void registerPermission(String permission, PermissionLevel level) {
		permissions.put(permission, level);
	}

	protected int getOpLevel(GameProfile gameProfile) {

		UserListOpsEntry Ops = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getOppedPlayers().getEntry(gameProfile);
		
		if (Ops.getPermissionLevel() == 0) {
			UserListOpsEntry entry = VanillaUtilities.getMinecraftServer().getPlayerList().getOppedPlayers().getEntry(gameProfile);
			
			return entry.getPermissionLevel();
		}
		return 0;
	}

}