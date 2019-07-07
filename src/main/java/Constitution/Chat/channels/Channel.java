package constitution.chat.channels;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;

import constitution.configuration.json.JSONSerializerTemplate;
import constitution.permissions.Meta;
import constitution.permissions.User;

public class Channel {

	protected String name = "";
	protected String password = "";

	protected Boolean enabled = true;
	protected Boolean hidden = false;
	protected Boolean verbose = false;
	protected boolean forced = false;
	protected boolean autoJoinable = false;
	protected boolean quickMessagable = false;
	protected boolean crossDimensionable = false;
	
	protected List<User> users = new ArrayList<User>();
    protected List<User> moderators = new ArrayList<User>();
    protected List<User> blacklist = new ArrayList<User>();
    protected List<User> whitelist = new ArrayList<User>();
    protected List<String> voicelist = new ArrayList<String>();
    protected List<User> mutelist = new ArrayList<User>();
    protected List<Integer> dimensions = new ArrayList<Integer>();
    protected List<String> gameToIRCTags = new ArrayList<String>();
    protected List<String> IRCToGameTags = new ArrayList<String>();
	
    public Channel(String name, String password) {
    	this.name = name;
    	this.password = password;
    }
    public String getName() {
    	return this.name;
    }
    public String getPassword() {
    	return this.password;
    }
    public Boolean getEnabled() {
    	return this.enabled;
    }
    public Boolean getHidden() {
    	return this.hidden;
    }
    public Boolean getVerbose() {
    	return this.verbose;
    }
    public Boolean getForced() {
    	return this.forced;
    }
    public Boolean autoJoined() {
    	return this.autoJoined();
    }
    public Boolean quickMessagable() {
    	return this.quickMessagable();
    }
    public Boolean crossDimensions() {
    	return this.crossDimensions();
    }
    public User getUsers() {
    	for (User user : users) {
    		return user;
    	}
    return null;
    }
    public User getModerators() {
    	for (User user : users) {
    		if (user.hasPermission("constitution.perm.chat.channel.admin" + this.name)) {
    			return user;
    		}
    	}
    return null;
    }
    public User getBlackListedUsers() {
    	for (User user : this.blacklist) {
    		return user;
    	}
    return null;
    }
    public User getWhiteListedUsers() {
    	for (User user : this.whitelist) {
    		return user;
    	}
    return null;
    }
    public User getMutedUsers() {
    	for (User user : this.mutelist) {
    		return user;
    	}
    return null;
    }
    public Integer getDimensions() {
    	for (Integer dim : this.dimensions) {
    		return dim;
    	}
    return 0;
    }
    public String getGameToIRCTags() {
    	for (String tag : this.gameToIRCTags) {
    		return tag;
    	}
    return null;
    }
    public String getIRCToGameTags() {
    	for (String tag : this.IRCToGameTags) {
    		return tag;
    	}
    return null;
    }
    public void setName(String name) {
    	this.name = name;
    }
    
    public void setPassword(String password) {
    	this.password = password;
    }
    
    public void setEnabled(Boolean value) {
    	this.enabled = value;
    }
    
    public void setHidden(Boolean value) {
    	this.hidden = value;
    }
    public void setVerbose(Boolean value) {
    	this.verbose = value;
    }
    public void setForced(Boolean value) {
    	this.forced = value;
    }
    public void setAutoJoin(Boolean value) {
    	this.autoJoinable = value;
    }
    public void setQuickMessageable(Boolean value) {
    	this.quickMessagable = value;
    }
    public void setCrossDimensionable(Boolean value) {
    	this.crossDimensionable = value;
    }
    
    public void setUser(User user) {
    	this.users.add(user);
    }
    
    public void setModerator(User user) {
    	if (user.hasPermission("constitution.perm.chat.channel.admin" + this.name)) {
    	this.moderators.add(user);
    	}
    }
    
    public void setBlackListedUser(User user) {
    	this.blacklist.add(user);
    }
    
    public void setWhiteListedUser(User user) {
    	this.whitelist.add(user);
    }
    
    public void setMuteListedUser(User user) {
    	this.mutelist.add(user);
    }
    
    public void setDimensionList(int dimension) {
    	this.dimensions.add(dimension);
    }
    
    public void setGameToIRCTags(String tag) {
    	this.gameToIRCTags.add(tag);
    }
    
    public void setIRCToGameTags(String tag) {
    	this.IRCToGameTags.add(tag);
    }

    public static class Serializer extends JSONSerializerTemplate<Channel> {

		@Override
		public void register(GsonBuilder builder) {
			builder.registerTypeAdapter(User.class, this);
			new Meta.Container.Serializer().register(builder);
		}


		@Override
		public Channel deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
			JsonObject jsonObject = json.getAsJsonObject();
			
			Channel channel = new Channel(name, password);
			return channel;
		}


		@Override
		public JsonElement serialize(Channel channel, Type typeOfSrc, JsonSerializationContext context) {
			JsonObject json = new JsonObject();
			
			json.addProperty("Name", channel.getName());
			json.add("Password", context.serialize(channel.getPassword()));
			json.add("Enabled", context.serialize(channel.getEnabled()));
			json.add("Hidden", context.serialize(channel.getHidden()));
			json.add("Verbose", context.serialize(channel.getVerbose()));
			json.add("Forced", context.serialize(channel.getForced()));
			json.add("AutoJoining", context.serialize(channel.geta()));
			json.add("QuickMessaging", context.serialize(channel.getName()));
			json.add("CrossDimension", context.serialize(channel.getName()));
			json.add("Users", context.serialize(channel.getName()));
			json.add("Moderators", context.serialize(channel.getName()));
			json.add("BlackList", context.serialize(channel.getName()));
			json.add("WhiteList", context.serialize(channel.getName()));
			json.add("MuteList", context.serialize(channel.getName()));
			json.add("Dimensions", context.serialize(channel.getName()));
			json.add("gameToIRCTags", context.serialize(channel.getName()));
			json.add("ircToGameTags", context.serialize(channel.getName()));
			
			return json;
		}
    }
}


