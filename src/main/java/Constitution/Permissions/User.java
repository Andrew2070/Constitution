package constitution.permissions;


import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.google.common.collect.ImmutableList;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;

import constitution.chat.IChatFormat;
import constitution.chat.channels.Channel;
import constitution.chat.component.ChatComponentBorders;
import constitution.chat.component.ChatComponentFormatted;
import constitution.configuration.Config;
import constitution.configuration.json.JSONSerializerTemplate;
import constitution.localization.LocalizationManager;
import constitution.utilities.PlayerUtilities;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class User implements IChatFormat {

	public 					UUID 							uuid				 = null;
	private				    String 							UserName 		     = "";
	private					String							LastName			 = "";
	private					Group							DominantGroup		 = null;
	private					String 							Nick 				 = "";
	private 				Date 							JoinDate 			 = new Date();
	private				    Date 							LastOnline			 = new Date();
	private					Long							LastActivity		 = null;
	private					String 							IPAddress 			 = "";
	private					Integer							Dimension			 = null;
	private					BlockPos						Location			 = null;
	private					Double							Balance				 = Config.instance.defaultUserBalance.get();
	private 				Boolean 						Operator 			 = false;
	private 				Boolean 						FakePlayer 			 = false;
	private					Boolean							GodMode				 = false;
	private					Boolean							canFly				 = false;
	private					Boolean							Creative			 = false;
	private					Float							Health				 = null;
	private					Integer							XPTotal				 = 0;
	private 				String 							Prefix 				 = "";
	private 				String						 	Suffix 				 = "";
	private					String							Channel				 = Config.instance.defaultChatChannel.get();
	private					String							BanReason			 = Config.instance.defaultBanMessage.get();
	private					Date							BanDuration			 = new Date();
	private					Boolean							Banned				 = false;
	private					Boolean							IPBanned			 = false;
	public final			List<Group> 					Groups 				 = new ArrayList<Group>();
	public final			List<String> 					alternateAccounts 	 = new ArrayList<String>();
	public final		    PermissionsContainer 			permsContainer		 = new PermissionsContainer();
	public final 			Meta.Container 					metaContainer 		 = new Meta.Container();

	// UUID, Name, JoinDate, LastOnline, LastActive, Location, IPAddress, Operator, FakePlayer, GodMode, Gamemode, Prefix, Suffix, Nick, Banned, IPBanned, Nodes, Groups, Alts, etc

	public User(EntityPlayerMP player, Date joinDate, long lastOnline) {
		this.UserName = player.getDisplayNameString();
		this.LastName = player.getDisplayNameString();
		this.uuid = player.getUniqueID();
		this.IPAddress = player.getPlayerIP();
		this.LastName = player.getDisplayNameString();
		this.JoinDate = joinDate;
		this.LastOnline.setTime(lastOnline * 1000L);
		this.Location = player.getPosition();
		this.GodMode = player.getIsInvulnerable();
		this.Creative = player.isCreative();
		this.Dimension = player.dimension;
		this.canFly = player.capabilities.allowFlying;
		this.LastActivity = player.getLastActiveTime();
		this.Health = player.getHealth();
		this.XPTotal = player.experienceTotal;
		this.setGroup(PlayerUtilities.getManager().groups.get(Config.instance.defaultGroupName.get()));
		this.setDominantGroup(PlayerUtilities.getManager().groups.get(Config.instance.defaultGroupName.get()));
		this.Operator = PlayerUtilities.isOP(player.getPersistentID());
	}

	public User(UUID uuid) {
		this.uuid = uuid;

	}

	public User(UUID uuid, Group group) {
		this(uuid);
		this.Groups.add(group);
	}

	//Get Methods:
	public UUID getUUID() {
		return this.uuid;
	}

	public String getUserName() {
		return this.UserName;
	}

	public String getformattedUserInfo() {
		String location = "X: " + this.getLocation().getX() + " Y: " + this.getLocation().getY() + " Z:" + this.getLocation().getZ();
		return String.format(" ----------%1$s----------\nUUID: %2$s\nPrefix: %3$s\nSuffix: %4$s\nLocation: %5$s\nGroup(s): %6$s\nIPAddress: %7$s\nPermissions: %8$s",
				this.getUserName(), this.getUUID(), this.getPrefix(), this.getSuffix(), location, this.getGroups().getName(), this.getIP(), this.getNodes());
	}
	public String getPrefix() {
		return this.Prefix.replaceAll("\u0026([\\da-fk-or])", "\u00A7$1");
	}

	public String getSuffix() {
		return this.Suffix.replaceAll("\u0026([\\da-fk-or])", "\u00A7$1");
	}

	public String getNick() {
		return this.Nick.replaceAll("\u0026([\\da-fk-or])", "\u00A7$1");
	}

	public String getLastPlayerName() {
		return this.LastName;
	}
	public Boolean isOp() {
		return this.Operator;
	}

	public Boolean isfakePlayer() {
		return this.FakePlayer;
	}

	public String getNodes() {
		for (String node : this.permsContainer) {
			return node;
		}
		return "No Permissions Assigned";
	}

	public String getAlts() {
		for (String alternateName : this.alternateAccounts) {
			return alternateName;
		}
		return "No Alternative Account Names";
	}

	public Group getGroups() {
		for (Group group : this.Groups) {
			return group;
		}
		return null;
	}

	public String getGroupNames() {
		for (Group group : this.Groups) {
			return group.getName();
		}
		return null;
	}
	public List<Group> Groups() {
		return this.Groups;
	}

	public String getIP() {
		return this.IPAddress;
	}
	
//	public Long getBanDuration() {
	//	return this.BanDuration;
	//}

	public Boolean canFly() {
		return this.canFly;
	}

	public Boolean godMode() {
		return this.GodMode;
	}

	public Boolean isCreative() {
		return this.Creative;
	}

	public Boolean isBanned() {
		return this.Banned;
	}

	public Boolean isIPBanned() {
		return this.IPBanned;
	}

	public Date joinDate() {
		return this.JoinDate;
	}

	public Date lastOnline() {
		return this.LastOnline;
	}

	public Long lastActivity() {
		return this.LastActivity;
	}

	public Float getHealth() {
		return this.Health;
	}

	public Integer getXPTotal() {
		return this.XPTotal;
	}

	public BlockPos getLocation() {
		return this.Location;
	}

	public Integer getDimension() {
		return this.Dimension;
	}

	public Group getDominantGroup() {
		return this.DominantGroup;
	}
	
	public String getBanReason() {
		return this.BanReason;
	}

	public String getChannelName() {
		return this.Channel;
	}
	public Channel getChannelObject() {
		return PlayerUtilities.getManager().channels.get(this.Channel);
	}
	
	public String getLocationAsString() {
		String location = "X: " + this.getLocation().getX() + " Y: " + this.getLocation().getY() + " Z: " + this.getLocation().getZ();
		return location;
	}
	//Set Methods:

	public void setUUID(UUID uuid) {
		this.uuid = uuid;
	}
	
	public void setChannel(String channel) {
		this.Channel = channel;
		if (PlayerUtilities.getManager().channels.contains(channel)) {
			if (!PlayerUtilities.getManager().channels.get(channel).users.contains(this)) {
				PlayerUtilities.getManager().channels.get(channel).users.add(this);
				PlayerUtilities.getManager().saveChannels();
			}
		}
	}

	public void setIP(EntityPlayerMP player) {
		this.IPAddress = player.getPlayerIP();
	}

	public void setIP(String string) {
		this.IPAddress = string;
	}

	public void setLastPlayerName(String name) {
		this.LastName = name;
	}

	public void setUserName(String username) {
		this.UserName = username;
	}

	public void setPrefix(String prefix) {
		this.Prefix = prefix;
	}

	public void setSuffix(String suffix) {
		this.Suffix = suffix;
	}

	public void setNick(String nick) {
		this.Nick = nick;
	}

	public void setOP(Boolean value) {
		this.Operator = value;
	}

	public void setGroup(Group group) {
		this.Groups.add(group);
	}

	public void setXPTotal(Integer total) {
		this.XPTotal = total;
	}

	public void setLocation(BlockPos position) {
		this.Location = position;
	}

	public void setGodMode(Boolean value) {
		this.GodMode = value;
	}

	public void setCanFly(Boolean value) {
		this.canFly = value;
	}

	public void setCreative(Boolean value) {
		this.Creative = value;
	}

	public void setFakePlayer(Boolean value) {
		this.FakePlayer = value;
	}

	public void setHealth(Float value) {
		this.Health = value;
	}

	public void setDimension(Integer dim) {
		this.Dimension = dim;
	}

	public void setJoinDate(Date date) {
		this.JoinDate = date;
	}

	public void setLastOnline(Date date) {
		this.LastOnline = date;
	}

	public void setLastActivity(Long time) {
		this.LastActivity = time;
	}

	public void setNode(String node) {
		this.permsContainer.add(node);
	}

	public void setAlt(String alternateName) {
		this.alternateAccounts.add(alternateName);
	}

	public void setDominantGroup(Group group) {
		this.DominantGroup = group;
	}

	public void setBanned(Boolean val) {
		this.Banned = val;
	}

	//public void setBanDuration(Long long) {
	//	this.BanDuration = long;
	//}
	
	public void setIPBanned(Boolean val) {
		this.IPBanned = val;
	}
	
	public void setBanReason(String reason) {
		this.BanReason = reason;
	}

	public void setDominantGroup() {
		Group dominant = new Group();
		int rank = dominant.getRank();

		for (Group group : this.Groups) {
			if (group.getRank() > rank) {
				if (rank > this.DominantGroup.getRank()) {
					this.DominantGroup = group;
				}
			}
		}
	}

	public Boolean isOP(UUID uuid) {
		return PlayerUtilities.isOP(uuid);
	}

	public boolean hasPermission(String permission) {
		Level permLevel = permsContainer.hasPermission(permission);

		if (permLevel == Level.ALLOWED) {
			return true;
		} else if (permLevel == Level.DENIED) {
			return false;
		}
		Group group = getGroups();
		return (group != null && group.hasPermission(permission) == Level.ALLOWED)
				|| (Config.instance.fullAccessForOPS.get() && isOP(uuid));
	}


	@Override
	public ITextComponent toChatMessage() {
		String location = "X: " + this.getLocation().getX() + " Y: " + this.getLocation().getY() + " Z: " + this.getLocation().getZ();
		String modifiedUUID = this.getUUID().toString().replace("-", ""); //Decrease the Character Count by removing "-" between characters.

		ITextComponent header = LocalizationManager.get("constitution.format.list.header", new ChatComponentFormatted("{9|%s}", ChatComponentBorders.borderEditorHover(this.getUserName())));
		ITextComponent hoverComponent = ((ChatComponentFormatted)LocalizationManager.get("constitution.format.user.long.hover",
				header, 						
				modifiedUUID,
				this.getPrefix(),
				this.getSuffix(),
				location,
				this.getGroups().toChatMessage(),
				this.getIP(),
				this.getNodes())).applyDelimiter("\n");
		return LocalizationManager.get("constitution.format.short", this.getUserName(), hoverComponent);
	}

	public static class Serializer extends JSONSerializerTemplate<User> {

		@Override
		public void register(GsonBuilder builder) {
			builder.registerTypeAdapter(User.class, this);
			new Meta.Container.Serializer().register(builder);
		}


		@Override
		public User deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
			JsonObject jsonObject = json.getAsJsonObject();

			UUID uuid = UUID.fromString(jsonObject.get("UUID").getAsString());
			User user = new User(uuid);

			JsonElement playerUserName = jsonObject.get("Player");
			if (playerUserName != null) {
				user.UserName = playerUserName.getAsString();
			}
			JsonElement dominantGroup = jsonObject.get("DominantGroup");
			if (dominantGroup != null) {
				user.setDominantGroup(((ConstitutionBridge) PermissionProxy.getPermissionManager()).groups.get(jsonObject.get("DominantGroup").getAsString()));
			} else {
				user.setDominantGroup();
			}
			
			if (jsonObject.has("groups")) {
				List<String> groupNames = new ArrayList<String>(ImmutableList.copyOf(context.<String[]>deserialize(jsonObject.get("groups"), String[].class)));	
				for (int i=0; i<PlayerUtilities.getManager().groups.size(); i++) {
					if (PlayerUtilities.getManager().groups.get(i).getName().equals(groupNames.get(i))) {
						user.setGroup(PlayerUtilities.getManager().groups.get(i));
					}
				}
			}
			
			if (jsonObject.has("permissions")) {
				user.permsContainer.addAll(ImmutableList.copyOf(context.<String[]>deserialize(jsonObject.get("permissions"), String[].class)));
			}
			if (jsonObject.has("meta")) {
				user.metaContainer.addAll(context.<Meta.Container>deserialize(jsonObject.get("meta"), Meta.Container.class));
			}
			user.setLastPlayerName(jsonObject.get("LastName").getAsString());
			user.setPrefix(jsonObject.get("Prefix").getAsString());
			user.setSuffix(jsonObject.get("Suffix").getAsString());
			user.setNick(jsonObject.get("Nickname").getAsString());
			user.setOP(jsonObject.get("Operator").getAsBoolean());
			user.setFakePlayer(jsonObject.get("FakePlayer").getAsBoolean());
			user.setGodMode(jsonObject.get("GodMode").getAsBoolean());
			user.setCanFly(jsonObject.get("CanFly").getAsBoolean());
			user.setCreative(jsonObject.get("Creative").getAsBoolean());
			user.setHealth(jsonObject.get("Health").getAsFloat());
			user.setXPTotal(jsonObject.get("XPTotal").getAsInt());
			user.setBanned(jsonObject.get("Banned").getAsBoolean());
			user.setIPBanned(jsonObject.get("IPBanned").getAsBoolean());
			user.setIP(jsonObject.get("IPAddress").getAsString());
			user.setDimension(jsonObject.get("Dimension").getAsInt());
			user.setLocation(new BlockPos(jsonObject.get("LocationX").getAsInt(),jsonObject.get("LocationY").getAsInt(), jsonObject.get("LocationZ").getAsInt()));
			user.setLastActivity(jsonObject.get("LastActivity").getAsLong());
			user.setBanReason(jsonObject.get("BanReason").getAsString());
			try {
				user.setJoinDate(new SimpleDateFormat().parse(jsonObject.get("JoinDate").getAsString()));
				user.setLastOnline(new SimpleDateFormat().parse(jsonObject.get("LastOnline").getAsString()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return user;
		}

		@Override
		public JsonElement serialize(User user, Type typeOfSrc, JsonSerializationContext context) {
			JsonObject json = new JsonObject();
			json.addProperty("UUID", user.uuid.toString());
			if (user.getUserName() != null) {
				json.addProperty("Player", user.getUserName());
			}
			json.add("LastName", context.serialize(user.getLastPlayerName()));
			json.add("DominantGroup", context.serialize(user.getDominantGroup().getName()));
			json.add("Nickname", context.serialize(user.getNick()));
			json.add("JoinDate", context.serialize(user.joinDate()));
			json.add("LastOnline", context.serialize(user.lastOnline()));
			json.add("LastActivity", context.serialize(user.lastActivity()));
			json.add("IPAddress", context.serialize(user.IPAddress));
			json.add("Dimension", context.serialize(user.Dimension));
			json.add("LocationX", context.serialize(user.getLocation().getX()));
			json.add("LocationY", context.serialize(user.getLocation().getY()));
			json.add("LocationZ", context.serialize(user.getLocation().getZ()));
			json.add("Operator", context.serialize(user.Operator));
			json.add("FakePlayer", context.serialize(user.isfakePlayer()));
			json.add("GodMode", context.serialize(user.godMode()));
			json.add("CanFly", context.serialize(user.canFly()));
			json.add("Creative", context.serialize(user.isCreative()));
			json.add("Health", context.serialize(user.getHealth()));
			json.add("XPTotal", context.serialize(user.getXPTotal()));
			json.add("Banned", context.serialize(user.isBanned()));
			json.add("IPBanned", context.serialize(user.isIPBanned()));
			json.add("Prefix", context.serialize(user.getPrefix()));
			json.add("Suffix", context.serialize(user.getSuffix()));
			json.add("BanReason", context.serialize(user.getBanReason()));
			if (!user.Groups.isEmpty()) {
				List<String> GroupNames = new ArrayList<String>();
				for (Group group : user.Groups) {
					GroupNames.add(group.getName());
				}
				json.add("groups", context.serialize(GroupNames));
			}
			if (!user.alternateAccounts.isEmpty()) {
				json.add("Alternative Accounts", context.serialize(user.alternateAccounts));
			}
			if (!user.permsContainer.isEmpty()) {
				json.add("permissions", context.serialize(user.permsContainer));
			}
			if (!user.metaContainer.isEmpty()) {
				json.add("meta", context.serialize(user.metaContainer));
			}
			return json;
		}
	}

	public static class Container extends ArrayList<User> implements IChatFormat {

		private Group defaultGroup;

		public boolean add(UUID uuid) {
			if (get(uuid) == null) {
				Group group = (defaultGroup == null)
						? ((ConstitutionBridge) PermissionProxy.getPermissionManager()).groups.get("default")
								: defaultGroup;
						User newUser = new User(uuid, group);
						super.add(newUser);
						return true;
			}
			return false;
		}

		public User get(UUID uuid) {
			for (User user : this) {
				if (user.uuid.equals(uuid)) {
					return user;
				}
			}
			return null;
		}

		public User get(String userName) {
			for (User user : this) {
				if (user.getUserName().equals(userName))
					return user;
			}
			return null;
		}

		public Group getPlayerGroup(UUID uuid) {
			for (User user : this) {
				if (user.uuid.equals(uuid)) {
					return user.getGroups();
				}
			}

			User user = new User(uuid, defaultGroup);
			add(user);
			return defaultGroup;
		}

		public boolean contains(UUID uuid) {
			for (User user : this) {
				if (user.uuid.equals(uuid)) {
					return true;
				}
			}
			return false;
		}

		public boolean contains(String userName) {
			for (User user : this) {
				if (user.getUserName().equals(userName))
					return true;
			}
			return false;
		}

		@Override
		public ITextComponent toChatMessage() {
			ITextComponent root = new TextComponentString("");

			for (User user : this) {
				if (root.getSiblings().size() > 0) {
					root.appendSibling(new ChatComponentFormatted("{7|, }"));
				}
				root.appendSibling(user.toChatMessage());
			}
			return root;
		}
	}
} 


