package constitution.configuration;



import com.google.common.reflect.TypeToken;
import com.google.gson.GsonBuilder;

import constitution.configuration.json.JSONConfig;
import constitution.permissions.Group;
import constitution.permissions.Meta;
import constitution.permissions.PermissionManager;
import constitution.utilities.ServerUtilities;

public class GroupConfig extends JSONConfig<Group, Group.Container> {

	private PermissionManager permissionManager = ServerUtilities.getManager();

	public GroupConfig(String path) {
		super(path, "Groups");
		this.gsonType = new TypeToken<Group.Container>() {
		}.getType();
		JSONConfig.gson = new GsonBuilder().registerTypeAdapter(Group.class, new Group.Serializer())
				.registerTypeAdapter(Meta.Container.class, new Meta.Container.Serializer()).setPrettyPrinting()
				.create();
	}

	@Override
	protected Group.Container newList() {
		return new Group.Container();
	}

	@Override
	public void create(Group.Container items) {
		items.add(new Group());
		super.create(items);
	}

	@Override
	public Group.Container read() {
		Group.Container groups = super.read();
		if (groups==null) {
		return new Group.Container();
		} else {
			permissionManager.groups.addAll(groups);
		}
	return null;
	}

	@Override
	public boolean validate(Group.Container items) {
		if (items.size() == 0) {
			Group group = new Group();
			items.add(group);
			return false;
		}
		return true;
	}
}
