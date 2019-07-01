package Constitution.Permissions;

import java.util.UUID;

public interface IPermissionBridge {

	boolean hasPermission(UUID uuid, String permission);

}
