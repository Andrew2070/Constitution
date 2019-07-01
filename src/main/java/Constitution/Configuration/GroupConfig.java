package Constitution.Configuration;



import com.google.common.reflect.TypeToken;
import com.google.gson.GsonBuilder;

import Constitution.JSON.JSONConfig;
import Constitution.Permissions.ConstitutionBridge;
import Constitution.Permissions.Group;
import Constitution.Permissions.Meta;

public class GroupConfig extends JSONConfig<Group, Group.Container> {

	private ConstitutionBridge permissionManager;

	public GroupConfig(String path, ConstitutionBridge permissionManager) {
		super(path, "Groups");
		this.permissionManager = permissionManager;
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

		permissionManager.groups.addAll(groups);

		return groups;
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
