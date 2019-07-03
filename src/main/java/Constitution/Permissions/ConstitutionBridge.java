package constitution.permissions;

import java.util.UUID;

import constitution.configuration.GroupConfig;
import constitution.configuration.UserConfig;

public class ConstitutionBridge implements IPermissionBridge {


	private static final String DEFAULT_GROUP_NAME = "Default";

	public final Group.Container groups = new Group.Container();
	public final User.Container users = new User.Container();

	public final GroupConfig groupConfig = new GroupConfig(constitution.ConstitutionMain.CONFIG_FOLDER + "JSON/Groups.json", this);
	public final UserConfig userConfig = new UserConfig(constitution.ConstitutionMain.CONFIG_FOLDER + "JSON/Users.json", this);

	public ConstitutionBridge() {
	}

	public void loadConfigs() {
		groups.clear();
		users.clear();

		groupConfig.init(groups);
		userConfig.init(users);
	}

	public void saveConfigs() {
		groupConfig.write(groups);
		userConfig.write(users);
	}

	public void saveGroups() {
		groupConfig.write(groups);
	}

	public void saveUsers() {
		userConfig.write(users);
	}

	@Override
	public boolean hasPermission(UUID uuid, String permission) {
		User user = users.get(uuid);

		return user != null && user.hasPermission(permission);
	}
}