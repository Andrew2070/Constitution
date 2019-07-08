package constitution.configuration.json;
import com.google.common.reflect.TypeToken;
import com.google.gson.GsonBuilder;

import constitution.chat.channels.Channel;
import constitution.permissions.ConstitutionBridge;
import constitution.permissions.Meta;
import constitution.permissions.User;
public class ChannelConfig extends JSONConfig<Channel, Channel.Container> {

	private ConstitutionBridge permissionsManager;

	public ChannelConfig(String path, ConstitutionBridge permissionsManager) {
		super(path, "Channels");
		this.permissionsManager = permissionsManager;
		this.gsonType = new TypeToken<Channel.Container>() {
		}.getType();
		JSONConfig.gson = new GsonBuilder().registerTypeAdapter(User.class, new User.Serializer())
				.registerTypeAdapter(Meta.Container.class, new Meta.Container.Serializer()).setPrettyPrinting()
				.create();
	}

	@Override
	protected Channel.Container newList() {
		return new Channel.Container();
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
	    	 return new Channel.Container();
	    }
	    else {
	        permissionsManager.channels.addAll(channels);
	    }
	    return channels;
	}
	
	@Override
	public boolean validate(Channel.Container items) {
		if (items.size() == 0) {
			Channel channel = new Channel();
			items.add(channel);
			return false;
		}
		return true;
	}
}