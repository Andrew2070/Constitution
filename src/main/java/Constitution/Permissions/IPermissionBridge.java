package constitution.permissions;

import java.util.UUID;

public interface IPermissionBridge {

	boolean hasPermission(UUID uuid, String permission);

}
