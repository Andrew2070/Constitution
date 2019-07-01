package Constitution.Configuration;
import java.util.UUID;

import com.google.common.reflect.TypeToken;
import com.google.gson.GsonBuilder;

import Constitution.JSON.JSONConfig;
import Constitution.Permissions.ConstitutionBridge;
import Constitution.Permissions.Meta;
import Constitution.Permissions.User;

public class UserConfig extends JSONConfig<User, User.Container> {
	UUID uuid = UUID.randomUUID();
	User user = new User(uuid);
	private ConstitutionBridge permissionsManager;

	public UserConfig(String path, ConstitutionBridge permissionsManager) {
		super(path, "Users");
		this.permissionsManager = permissionsManager;
		this.gsonType = new TypeToken<User.Container>() {
		}.getType();
		JSONConfig.gson = new GsonBuilder().registerTypeAdapter(User.class, new User.Serializer())
				.registerTypeAdapter(Meta.Container.class, new Meta.Container.Serializer()).setPrettyPrinting()
				.create();
	}

	@Override
	protected User.Container newList() {
		return new User.Container();
	}

	@Override
	public User.Container read() {
	    User.Container users = super.read();

	    if (users == null) {
	    	 return new User.Container();
	    }
	    else {
	        permissionsManager.users.addAll(users);

	    }
	    return users;
	}
	
}