package Constitution.Permissions;

import Constitution.Configuration.Config;
import Constitution.Constitution;
public class PermissionProxy {
	public static final String PERM_SYSTEM_CONSTITUTION = "$Constitution";

	private static IPermissionBridge permissionManager;

	public static IPermissionBridge getPermissionManager() {
		if (permissionManager == null) {
			init();
		}
		return permissionManager;
	}

	public static void init() {

		if (Config.instance.permissionSystem.get().equals(PERM_SYSTEM_CONSTITUTION)) {
			permissionManager = new ConstitutionBridge();
			((ConstitutionBridge) permissionManager).loadConfigs();
			Constitution.logger.info("Currently using built-in permission system.");
		}
	}
}