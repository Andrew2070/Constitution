package constitution.configuration;
import com.google.common.reflect.TypeToken;
import com.google.gson.GsonBuilder;

import constitution.chat.channels.Channel;
import constitution.configuration.json.JSONConfig;
import constitution.permissions.PermissionManager;
public class ChannelConfig extends JSONConfig<Channel, Channel.Container> {

	private PermissionManager permissionsManager;
	public ChannelConfig(String path, PermissionManager manager) {
		super(path, "Channels");
		this.permissionsManager = manager;
		this.gsonType = new TypeToken<Channel.Container>() {
		}.getType();
		JSONConfig.gson = new GsonBuilder().registerTypeAdapter(Channel.class, new Channel.Serializer())
				.setPrettyPrinting()
				.create();
	}

	@Override
	protected Channel.Container newList() {
		Channel.Container container = new Channel.Container();
		container.add(new Channel());
		return container;
	}
	@Override
	public void create(Channel.Container items) {
		items.add(new Channel());
		super.create(items);
	}

	@Override
	public Channel.Container read() {
		Channel.Container channels = super.read();
	    if (channels == null) {
			Channel.Container container = new Channel.Container();
			container.add(new Channel());
			return container;
	    }
	    else {
	        permissionsManager.channels.addAll(channels);
	    }
	    return channels;
	}
	@Override
	public boolean validate(Channel.Container items) {
		if (items.size() == 0) {
			items.add(new Channel());
			return false;
		}
		return true;
	}
	@Override
	public void clearGsonCache() {
		JSONConfig.gson = null;
	}
}