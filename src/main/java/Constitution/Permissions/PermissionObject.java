package constitution.permissions;

import net.minecraftforge.server.permission.DefaultPermissionLevel;

public interface PermissionObject {

	public String getPermissionNode();

	public DefaultPermissionLevel getPermissionLevel();

}