package constitution.configuration;
import com.google.common.reflect.TypeToken;
import com.google.gson.GsonBuilder;

import constitution.chat.channels.Channel;
import constitution.configuration.json.JSONConfig;
import constitution.permissions.Meta;
import constitution.permissions.PermissionManager;
import constitution.permissions.User;
import constitution.utilities.ServerUtilities;
public class ChannelConfig extends JSONConfig<Channel, Channel.Container> {

	private PermissionManager permissionsManager = ServerUtilities.getManager();

	public ChannelConfig(String path) {
		super(path, "Channels");
		this.gsonType = new TypeToken<Channel.Container>() {
		}.getType();
		JSONConfig.gson = new GsonBuilder().registerTypeAdapter(Channel.class, new Channel.Serializer())
				.registerTypeAdapter(Meta.Container.class, new Meta.Container.Serializer()).setPrettyPrinting()
				.create();
	}

	@Override
	protected Channel.Container newList() {
		Channel.Container container = new Channel.Container();
		container.add(new Channel());
		return container;
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
}