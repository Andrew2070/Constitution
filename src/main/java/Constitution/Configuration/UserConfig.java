package constitution.configuration;
import com.google.common.reflect.TypeToken;
import com.google.gson.GsonBuilder;

import constitution.configuration.json.JSONConfig;
import constitution.permissions.Meta;
import constitution.permissions.PermissionManager;
import constitution.permissions.User;

public class UserConfig extends JSONConfig<User, User.Container> {
	
	private PermissionManager permissionsManager;

	public UserConfig(String path, PermissionManager manager) {
		super(path, "Users");
		this.permissionsManager = manager;
		this.gsonType = new TypeToken<User.Container>() {
		}.getType();
		JSONConfig.gson = new GsonBuilder().registerTypeAdapter(User.class, new User.Serializer())
				.registerTypeAdapter(Meta.Container.class, new Meta.Container.Serializer())
				.setPrettyPrinting()
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
	@Override
	public void clearGsonCache() {
		JSONConfig.gson = null;
	}
	
}