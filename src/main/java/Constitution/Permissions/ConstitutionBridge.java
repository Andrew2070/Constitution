package constitution.permissions;

import java.util.UUID;

import constitution.chat.channels.Channel;
import constitution.configuration.GroupConfig;
import constitution.configuration.UserConfig;
import constitution.configuration.json.ChannelConfig;

public class ConstitutionBridge implements IPermissionBridge {

	public final Group.Container groups = new Group.Container();
	public final User.Container users = new User.Container();
	public final Channel.Container channels = new Channel.Container();

	public final GroupConfig groupConfig = new GroupConfig(constitution.ConstitutionMain.CONFIG_FOLDER + "JSON/Groups.json", this);
	public final UserConfig userConfig = new UserConfig(constitution.ConstitutionMain.CONFIG_FOLDER + "JSON/Users.json", this);
	public final ChannelConfig channelConfig = new ChannelConfig(constitution.ConstitutionMain.CONFIG_FOLDER + "JSON/Channels.json", this);

	public ConstitutionBridge() {
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

	@Override
	public boolean hasPermission(UUID uuid, String permission) {
		User user = users.get(uuid);

		return user != null && user.hasPermission(permission);
	}
}