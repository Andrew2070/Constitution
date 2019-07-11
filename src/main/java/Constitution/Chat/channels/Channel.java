package constitution.chat.channels;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.google.common.collect.ImmutableList;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;

import constitution.configuration.Config;
import constitution.configuration.json.JSONSerializerTemplate;
import constitution.permissions.Meta;
import constitution.permissions.User;
import constitution.utilities.PlayerUtilities;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class Channel {

	protected String name = "";
	protected String password = "";
	protected String prefix = "";
	protected Boolean enabled = true;
	protected Boolean hidden = false;
	protected Boolean verbose = false;
	protected Boolean forced = false;
	protected Boolean autoJoinable = false;
	protected Boolean quickMessagable = false;
	protected Boolean crossDimensionable = false;

	public List<User> users = new ArrayList<User>();
	public List<User> moderators = new ArrayList<User>();
	public List<User> blacklist = new ArrayList<User>();
	public List<User> whitelist = new ArrayList<User>();
	public List<String> voicelist = new ArrayList<String>();
	public List<User> mutelist = new ArrayList<User>();
	protected List<Integer> dimensions = new ArrayList<Integer>();
	protected List<String> gameToIRCTags = new ArrayList<String>();
	protected List<String> IRCToGameTags = new ArrayList<String>();
	
	public Channel() {
		this.name = Config.instance.defaultChatChannel.get();
		this.quickMessagable = true;
		this.crossDimensionable = true;
		this.autoJoinable = true;
		this.forced = true;
		this.dimensions.add(0);
		this.prefix = "[G]";
	}
	
	
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
	public String getPrefix() {
		return this.prefix.replaceAll("\u0026([\\da-fk-or])", "\u00A7$1");
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
			if (user!=null) {
			return user;
			}
		}
		return null;
	}
	
	public UUID getUserUUIDs() {
		for (User user : users) {
			if (user!=null) {
				return user.getUUID();
			}
		}
		return null;
	}
	public User getModerators() {
		for (User user : users) {
				return user;
			}
		return null;
		}
	public UUID getModeratorUUIDs() {
		for (User user : users) {
			return user.getUUID();
		}
		return null;
	}
	public User getBlackListedUsers() {
		for (User user : this.blacklist) {
			if (user!=null) {
				return user;
			}
		}
		return null;
	}
	public UUID getBlackListedUsersUUIDS() {
		for (User user : this.blacklist) {
			if (user!=null) {
			return user.getUUID();
			}
		}
		return null;
	}
	public User getWhiteListedUsers() {
		for (User user : this.whitelist) {
			if (user!=null) {
			return user;
			}
		}
		return null;
	}
	public UUID getWhiteListedUsersUUIDS() {
		for (User user : this.whitelist) {
			if (user!=null) {
				return user.getUUID();
			}
		}
		return null;
	}
	public User getMutedUsers() {
		for (User user : this.mutelist) {
			if (user!=null) {
				return user;
			}
		}
		return null;
	}
	public UUID getMutedUsersUUIDS() {
		for (User user : this.mutelist) {
			if (user!=null) {
				return user.getUUID();
			}
		}
		return null;
	}
	public Integer getDimensions() {
		for (Integer dim : this.dimensions) {
			if (dim!=null) {
			return dim;
			}
		}
		return 0;
	}
	public String getGameToIRCTags() {
		for (String tag : this.gameToIRCTags) {
			if (tag!=null) {
			return tag;
			}
		}
		return null;
	}
	public String getIRCToGameTags() {
		for (String tag : this.IRCToGameTags) {
			if (tag!=null) {
			return tag;
			}
		}
		return null;
	}
	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setPrefix(String prefix) {
		this.prefix = prefix.replaceAll("\u0026([\\da-fk-or])", "\u00A7$1");
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
		this.moderators.add(user);
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

	public void setDimension(int dimension) {
		this.dimensions.add(dimension);
	}

	public void setGameToIRCTags(String tag) {
		this.gameToIRCTags.add(tag);
	}

	public void setIRCToGameTags(String tag) {
		this.IRCToGameTags.add(tag);
	}
	
	public void sendMessage(ITextComponent message) {
		for (User user : this.users) {
			 EntityPlayerMP receivingPlayer = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUUID(user.getUUID());
			if (!this.mutelist.contains(user)) {
				if (!this.blacklist.contains(user)) {
					receivingPlayer.sendMessage(message);
				}
			}
		}
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
			Channel channel = new Channel("", "");
			channel.setName(jsonObject.get("Name").getAsString());
			channel.setPassword(jsonObject.get("Password").getAsString());
			channel.setEnabled(jsonObject.get("Enabled").getAsBoolean());
			channel.setHidden(jsonObject.get("Hidden").getAsBoolean());
			channel.setVerbose(jsonObject.get("Verbose").getAsBoolean());
			channel.setForced(jsonObject.get("Forced").getAsBoolean());
			channel.setAutoJoin(jsonObject.get("AutoJoining").getAsBoolean());
			channel.setQuickMessageable(jsonObject.get("QuickMessaging").getAsBoolean());
			channel.setCrossDimensionable(jsonObject.get("CrossDimension").getAsBoolean());
			if (jsonObject.has("Users")) {
				List<String> usersUUIDS = new ArrayList<String>(ImmutableList.copyOf(context.<String[]>deserialize(jsonObject.get("Users"), String[].class)));	
				for (String uuid : usersUUIDS) {
					UUID userID = UUID.fromString(uuid);
					if (PlayerUtilities.getManager().users != null && !PlayerUtilities.getManager().users.isEmpty()) {
						User user = PlayerUtilities.getManager().users.get(userID);
						channel.setUser(user);
					}
				}
			}
			if (jsonObject.has("Moderators")) {
				List<String> modUUIDS = new ArrayList<String>(ImmutableList.copyOf(context.<String[]>deserialize(jsonObject.get("Moderators"), String[].class)));	
				for (String uuid : modUUIDS) {
					UUID modID = UUID.fromString(uuid);
					if (PlayerUtilities.getManager().users != null && !PlayerUtilities.getManager().users.isEmpty()) {
						User moderator = PlayerUtilities.getManager().users.get(modID);
						channel.setModerator(moderator);
					}
				}
			}
			if (jsonObject.has("BlackList")) {
				List<String> blackList = new ArrayList<String>(ImmutableList.copyOf(context.<String[]>deserialize(jsonObject.get("BlackList"), String[].class)));	
				for (String uuid : blackList) {
					UUID userID = UUID.fromString(uuid);
					if (PlayerUtilities.getManager().users != null && !PlayerUtilities.getManager().users.isEmpty()) {
						User blackListedUser = PlayerUtilities.getManager().users.get(userID);
						channel.setUser(blackListedUser);
					}
				}
			}
			if (jsonObject.has("WhiteList")) {
				List<String> whiteList = new ArrayList<String>(ImmutableList.copyOf(context.<String[]>deserialize(jsonObject.get("WhiteList"), String[].class)));	
				for (String uuid : whiteList) {
					UUID userID = UUID.fromString(uuid);
					if (PlayerUtilities.getManager().users != null && !PlayerUtilities.getManager().users.isEmpty()) {
						User whiteListedUser = PlayerUtilities.getManager().users.get(userID);
						channel.setUser(whiteListedUser);
					}
				}
			}
			if (jsonObject.has("MuteList")) {
				List<String> muteList = new ArrayList<String>(ImmutableList.copyOf(context.<String[]>deserialize(jsonObject.get("MuteList"), String[].class)));	
				for (String uuid : muteList) {
					UUID userID = UUID.fromString(uuid);
					if (PlayerUtilities.getManager().users != null && !PlayerUtilities.getManager().users.isEmpty()) {
						User mutedUser = PlayerUtilities.getManager().users.get(userID);
						channel.setUser(mutedUser);
					}
				}
			}
			if (jsonObject.has("Dimensions")) {
				List<Integer> dimensions = new ArrayList<Integer>(ImmutableList.copyOf(context.<Integer[]>deserialize(jsonObject.get("Dimensions"), Integer[].class)));	
				for (int dim : dimensions) {
					channel.setDimension(dim);
				}
			}
			if (jsonObject.has("gameToIRCTags")) {
				List<String> gameToIRCTags = new ArrayList<String>(ImmutableList.copyOf(context.<String[]>deserialize(jsonObject.get("gameToIRCTags"), String[].class)));	
				for (String tag : gameToIRCTags) {
					channel.setGameToIRCTags(tag);
				}
			}
			if (jsonObject.has("ircToGameTags")) {
				List<String> ircToGameTags = new ArrayList<String>(ImmutableList.copyOf(context.<String[]>deserialize(jsonObject.get("ircToGameTags"), String[].class)));	
				for (String tag : ircToGameTags) {
					channel.setIRCToGameTags(tag);
				}
			}
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
			json.add("AutoJoining", context.serialize(channel.autoJoined()));
			json.add("QuickMessaging", context.serialize(channel.quickMessagable()));
			json.add("CrossDimension", context.serialize(channel.getName()));

			if (channel.users!=null && !channel.users.isEmpty()) {
				json.add("Users", context.serialize(channel.getUserUUIDs().toString()));
			}
			if (channel.moderators!= null && !channel.moderators.isEmpty()) {
				json.add("Moderators", context.serialize(channel.getModeratorUUIDs().toString()));
			}
			if (channel.blacklist!= null && !channel.blacklist.isEmpty()) {
				json.add("BlackList", context.serialize(channel.getBlackListedUsersUUIDS().toString()));
			}
			if (channel.whitelist!= null && !channel.whitelist.isEmpty()) {
				json.add("WhiteList", context.serialize(channel.getWhiteListedUsersUUIDS().toString()));
			}
			if (channel.mutelist!= null && !channel.mutelist.isEmpty()) {
				json.add("MuteList", context.serialize(channel.getMutedUsersUUIDS().toString()));
			}
			if (channel.dimensions!= null && !channel.dimensions.isEmpty()) {
				json.add("Dimensions", context.serialize(channel.getDimensions()));
			}
			if (channel.gameToIRCTags!= null && !channel.gameToIRCTags.isEmpty()) {
				json.add("gameToIRCTags", context.serialize(channel.getGameToIRCTags()));
			}
			if (channel.IRCToGameTags!= null && !channel.IRCToGameTags.isEmpty()) {
				json.add("ircToGameTags", context.serialize(channel.getIRCToGameTags()));
			}

			return json;
		}
	}
	
	public static class Container extends ArrayList<Channel> {

		private Channel defaultChannel;
		
		@Override
		public boolean add(Channel channel) {
			if (PlayerUtilities.getManager().channels.contains(channel)) {
				return true;
			} else {
				super.add(channel);
				return false;
			}
		}
		
		public Channel get(String channelName) {
			for (Channel channel : this) {
				if (channel.getName().equals(channelName)) {
					return channel;
				}
			}
			return null;
		}
		public boolean contains(Channel channel) {
			for (Channel ch : this) {
				if (ch == channel) {
					return true;
				}
			}
			return false;
		}
		public boolean contains(String channelname) {
			for (Channel ch : this) {
				if (ch.getName() == channelname) {
					return true;
				}
			}
			return false;
		}
	}
}


