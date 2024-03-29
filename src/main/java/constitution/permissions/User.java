/*******************************************************************************
 * Copyright (C) July/14/2019, Andrew2070
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * 3. All advertising materials mentioning features or use of this software must
 *    display the following acknowledgement:
 *    This product includes software developed by Andrew2070.
 * 
 * 4. Neither the name of the copyright holder nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package constitution.permissions;


import java.lang.reflect.Type;
import java.net.SocketAddress;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
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
import com.mojang.authlib.GameProfile;

import constitution.chat.IChatFormat;
import constitution.chat.component.ChatComponentBorders;
import constitution.chat.component.ChatComponentFormatted;
import constitution.configuration.Config;
import constitution.configuration.json.JSONSerializerTemplate;
import constitution.localization.LocalizationManager;
import constitution.utilities.Formatter;
import constitution.utilities.ServerUtilities;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class User implements IChatFormat {
	private 				UUID 							uuid				 = null;
	private				    String 							UserName 		     = "";
	private					String							LastName			 = "";
	private					Group							DominantGroup		 = null;
	private					String							ChannelName		     = "";
	private					String 							Nick 				 = "";
	private 				Date 							JoinDate 			 = new Date();
	private				    Date 							LastOnline			 = new Date();
	private					String 							IPAddress 			 = "";
	private					Integer							Dimension			 = null;
	private					Integer							LastDimension		 = null;
	private					BlockPos						Location			 = null;
	private					BlockPos						LastLocation		 = null;
	private 				Boolean 						Operator 			 = false;
	private 				Boolean 						FakePlayer 			 = false;
	private					Boolean							GodMode				 = false;
	private					Boolean							Flight				 = false;
	private					Boolean							Creative			 = false;
	private					Boolean							afk					 = false;
	private 				String 							Prefix 				 = "";
	private 				String						 	Suffix 				 = "";
	private					Boolean							Banned				 = false;
	private					Boolean							IPBanned			 = false;
	private final			List<Group> 					Groups 				 = new ArrayList<Group>();
	private final			List<String> 					alternateAccounts 	 = new ArrayList<String>();
	private final		    PermissionsContainer 			permsContainer		 = new PermissionsContainer();
	private final 			Meta.Container 					metaContainer 		 = new Meta.Container();

	// UUID, Name, JoinDate, LastOnline, LastActive, Location, IPAddress, Operator, FakePlayer, GodMode, Gamemode, Prefix, Suffix, Nick, Banned, IPBanned, Nodes, Groups, Alts, etc

	public User(EntityPlayerMP player, long joinDate, long lastOnline) {
		setUUID(player.getUniqueID());
		setUserName(player.getDisplayNameString());
		setLastPlayerName(player.getDisplayNameString());
		setDominantGroup(ServerUtilities.getManager().groups.get(Config.instance.defaultGroupName.get()));
		setIP(player.getPlayerIP());
		setDimension(player.dimension);
		setLocation(player.getPosition());
		setOP(ServerUtilities.isOP(player.getPersistentID()));
		setGodMode(player.getIsInvulnerable());
		setFlight(player.capabilities.allowFlying);
		setCreative(player.isCreative());
		setGroup(ServerUtilities.getManager().groups.get(Config.instance.defaultGroupName.get()));
		this.JoinDate.setTime(joinDate * 1000L);
		this.LastOnline.setTime(joinDate);
	}

	public User(UUID uuid) {
		this.uuid = uuid;
	}

	public User(UUID uuid, Group group) {
		this(uuid);
		this.Groups.add(group);
	}

	public String getformattedUserInfo() {
		String location = "X: " + this.getLocation().getX() + " Y: " + this.getLocation().getY() + " Z:" + this.getLocation().getZ();
		return String.format(" ----------%1$s----------\nUUID: %2$s\nPrefix: %3$s\nSuffix: %4$s\nLocation: %5$s\nGroup(s): %6$s\nIPAddress: %7$s\nPermissions: %8$s",
				this.getUserName(), this.getUUID(), this.getPrefix(), this.getSuffix(), location, this.getGroups().getName(), this.getIP(), this.getNodes());
	}

	public Boolean getAFK() {
		return this.afk;
	}

	public UUID getUUID() {
		return this.uuid;
	}

	public String getUserName() {
		return this.UserName;
	}

	public String getLastPlayerName() {
		return this.LastName;
	}

	public Group getDominantGroup() {
		return this.DominantGroup;
	}

	public String getDominantGroupName() {
		return this.DominantGroup.getName();
	}

	public String getChannel() {
		return this.ChannelName;
	}

	public String getNick() {
		return this.Nick.replaceAll("\u0026([\\da-fk-or])", "\u00A7$1");
	}

	public Date getJoinDate() {
		return this.JoinDate;
	}

	public Date lastOnline() {
		return this.LastOnline;
	}

	public String getIP() {
		return this.IPAddress;
	}

	public Integer getDimension() {
		return this.Dimension;
	}

	public Integer getLastDimension() {
		return this.LastDimension;
	}

	public BlockPos getLocation() {
		if (FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUUID(this.getUUID()).getPosition() != this.Location) {
			BlockPos location = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUUID(this.getUUID()).getPosition();
			setLocation(location);
			return location;
		}
		return this.Location;
	}

	public BlockPos getLastLocation() {
		return this.Location;
	}

	public String getLocationAsString() {
		String location = "X: " + this.getLocation().getX() + " Y: " + this.getLocation().getY() + " Z:" + this.getLocation().getZ();
		return location;
	}

	public Boolean getIsOp() {
		return this.Operator;
	}

	public Boolean getIsfakePlayer() {
		return this.FakePlayer;
	}

	public Boolean getGodMode() {
		return this.GodMode;
	}

	public Boolean getFlight() {
		return this.Flight;
	}

	public Boolean getCreative() {
		return this.Creative;
	}

	public String getPrefix() {
		return this.Prefix.replaceAll("\u0026([\\da-fk-or])", "\u00A7$1");
	}

	public String getSuffix() {
		return this.Suffix.replaceAll("\u0026([\\da-fk-or])", "\u00A7$1");
	}

	public Boolean getBanned() {
		return this.Banned;
	}

	public Boolean getIPBanned() {
		return this.IPBanned;
	}

	public Group getGroups() {
		for (Group group : this.Groups) {
			return group;
		}
		return null;
	}

	public List<Group> getGroupsList() {
		return this.Groups;
	}

	public String getGroupNames() {
		for (Group group : this.Groups) {
			return group.getName();
		}
		return null;
	}

	public Collection<String> getGroupNamesList() {
		List<String> groups = new ArrayList<String>();
		for (Group group : this.Groups) {
			groups.add(group.getName());
		}
		return groups;
	}

	public String getAlts() {
		for (String alternateName : this.alternateAccounts) {
			return alternateName;
		}
		return "No Alternative Account Names";
	}

	public String getNodes() {
		for (String node : this.permsContainer) {
			return node;
		}
		return "No Permissions Assigned";
	}

	public PermissionsContainer getPermsContainer() {
		return this.permsContainer;
	}

	public void setAFK(Boolean value) {
		this.afk = value;
	}

	public void setUUID(UUID uuid) {
		this.uuid = uuid;
	}

	public void setUserName(String username) {
		this.UserName = username;
	}

	public void setLastPlayerName(String name) {
		this.LastName = name;
	}

	public void setDominantGroup(Group group) {
		this.DominantGroup = group;
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

	public void setChannel(String channel) {
		this.ChannelName = channel;
	}

	public void setJoinDate(Date date) {
		this.JoinDate = date;
	}

	public void setLastOnline(Date date) {
		this.LastOnline = date;
	}

	public void setNick(String nick) {
		this.Nick = nick;
	}

	public void setIP(EntityPlayerMP player) {
		this.IPAddress = player.getPlayerIP();
	}

	public void setIP(String string) {
		this.IPAddress = string;
	}

	public void setDimension(Integer dim) {
		this.Dimension = dim;
	}

	public void setLastDimension(Integer dim) {
		this.LastDimension = dim;
	}

	public void setLocation(BlockPos position) {
		this.Location = position;
	}

	public void setLastLocation(BlockPos location) {
		this.LastLocation = location;
	}

	public void setPrefix(String prefix) {
		this.Prefix = prefix;
	}

	public void setSuffix(String suffix) {
		this.Suffix = suffix;
	}

	public void setOP(Boolean value) {
		this.Operator = value;
	}

	public void setGroup(Group group) {
		this.Groups.add(group);
	}

	public void setFakePlayer(Boolean value) {
		this.FakePlayer = value;
	}

	public void setGodMode(Boolean value) {
		this.GodMode = value;
	}

	public void setFlight(Boolean value) {
		this.Flight = value;
	}

	public void setCreative(Boolean value) {
		this.Creative = value;
	}

	public void setBanned(Boolean val) {
		this.Banned = val;
	}

	public void setIPBanned(Boolean val) {
		this.IPBanned = val;
	}

	public void setAlt(String alternateName) {
		this.alternateAccounts.add(alternateName);
	}

	public void setNode(String node) {
		this.permsContainer.add(node);
	}

	public void removeNode(String node) {
		this.permsContainer.remove(node);
	}

	public Boolean isOP(UUID uuid) {
		return ServerUtilities.isOP(uuid);
	}

	public Boolean getBanStatus() {
		UUID playerUUID = getUUID();
		EntityPlayerMP player = ServerUtilities.getMinecraftServer().getPlayerList().getPlayerByUUID(playerUUID);
		GameProfile profile = player.getGameProfile();
		SocketAddress socketAddress = player.connection.getNetworkManager().getRemoteAddress();
		Boolean banned = false;
			if (ServerUtilities.getMinecraftServer().getPlayerList().getBannedIPs().isBanned(socketAddress)) {
				setIPBanned(true);
				banned = true;
			}
			if (ServerUtilities.getMinecraftServer().getPlayerList().getBannedPlayers().isBanned(profile)) {
				setBanned(true);
				banned = true;
			}

			if (getBanned() || getIPBanned()) {
				banned = true;
			}
		return banned;
	}

	@Override
	public ITextComponent toChatMessage() {
		String modifiedUUID = this.getUUID().toString().replace("-", ""); //Decrease the Character Count by removing "-" between characters.
		Float health = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUUID(this.uuid).getHealth();
		ITextComponent header = LocalizationManager.get("constitution.format.list.header", new ChatComponentFormatted("{9|%s}", ChatComponentBorders.borderEditorHover(this.getUserName())));
		ITextComponent hoverComponent = ((ChatComponentFormatted)LocalizationManager.get("constitution.format.user.long.hover",
				header, 						
				modifiedUUID,
				this.getPrefix(),
				this.getSuffix(),
				Float.toString(health),
				Formatter.formatDate(this.getJoinDate()),
				Formatter.formatDate(this.lastOnline()),
				this.getLocationAsString(),
				this.getGroups().toChatMessage(),
				this.getIP(),
				this.permsContainer)).applyDelimiter("\n");
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
			user.setUserName(playerUserName.getAsString());		
			JsonElement dominantGroup = jsonObject.get("DominantGroup");
			if (dominantGroup != null) {
				user.setDominantGroup(ServerUtilities.getManager().groups.get(jsonObject.get("DominantGroup").getAsString()));
			} else {
				user.setDominantGroup();
			}

			if (jsonObject.has("Groups")) {
				List<String> groupNames = new ArrayList<String>(ImmutableList.copyOf(context.<String[]>deserialize(jsonObject.get("Groups"), String[].class)));	
				for (int i=0; i<ServerUtilities.getManager().groups.size(); i++) {
					if (ServerUtilities.getManager().groups.get(i).getName().equals(groupNames.get(i))) {
						user.setGroup(ServerUtilities.getManager().groups.get(i));
					}
				}
			}
			if (jsonObject.has("Permissions")) {
				user.permsContainer.addAll(context.<PermissionsContainer>deserialize(jsonObject.get("Permissions"), PermissionsContainer.class));
			}
			if (jsonObject.has("Meta")) {
				user.metaContainer.addAll(context.<Meta.Container>deserialize(jsonObject.get("Meta"), Meta.Container.class));
			}
			user.setLastPlayerName(jsonObject.get("LastName").getAsString());
			user.setChannel(jsonObject.get("Channel").getAsString());
			user.setPrefix(jsonObject.get("Prefix").getAsString());
			user.setSuffix(jsonObject.get("Suffix").getAsString());
			user.setNick(jsonObject.get("Nickname").getAsString());
			user.setOP(jsonObject.get("Operator").getAsBoolean());
			user.setFakePlayer(jsonObject.get("FakePlayer").getAsBoolean());
			user.setGodMode(jsonObject.get("GodMode").getAsBoolean());
			user.setFlight(jsonObject.get("Flight").getAsBoolean());
			user.setCreative(jsonObject.get("Creative").getAsBoolean());
			user.setBanned(jsonObject.get("Banned").getAsBoolean());
			user.setIPBanned(jsonObject.get("IPBanned").getAsBoolean());
			user.setIP(jsonObject.get("IPAddress").getAsString());
			user.setDimension(jsonObject.get("Dimension").getAsInt());
			user.setLocation(new BlockPos(jsonObject.get("LocationX").getAsInt(),jsonObject.get("LocationY").getAsInt(), jsonObject.get("LocationZ").getAsInt()));
			try {
				SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss a");
				user.setJoinDate(format.parse(jsonObject.get("JoinDate").getAsString()));
				user.setLastOnline(format.parse(jsonObject.get("LastOnline").getAsString()));
			} catch (ParseException e) {
				e.printStackTrace();
			}

			return user;
		}

		@Override
		public JsonElement serialize(User user, Type typeOfSrc, JsonSerializationContext context) {
			JsonObject json = new JsonObject();

			json.addProperty("UUID", user.uuid.toString());
			json.add("Player", context.serialize(user.getUserName()));
			json.add("LastName", context.serialize(user.getLastPlayerName()));
			json.add("Channel", context.serialize(user.getChannel()));
			json.add("DominantGroup", context.serialize(user.getDominantGroupName()));
			json.add("Nickname", context.serialize(user.getNick()));
			json.add("JoinDate", context.serialize(user.getJoinDate()));
			json.add("LastOnline", context.serialize(user.lastOnline()));
			json.add("IPAddress", context.serialize(user.IPAddress));
			json.add("Dimension", context.serialize(user.Dimension));
			json.add("LocationX", context.serialize(user.getLocation().getX()));
			json.add("LocationY", context.serialize(user.getLocation().getY()));
			json.add("LocationZ", context.serialize(user.getLocation().getZ()));
			json.add("Operator", context.serialize(user.Operator));
			json.add("FakePlayer", context.serialize(user.getIsfakePlayer()));
			json.add("GodMode", context.serialize(user.getGodMode()));
			json.add("Flight", context.serialize(user.getFlight()));
			json.add("Creative", context.serialize(user.getCreative()));
			json.add("Banned", context.serialize(user.getBanned()));
			json.add("IPBanned", context.serialize(user.getIPBanned()));
			json.add("Prefix", context.serialize(user.getPrefix()));
			json.add("Suffix", context.serialize(user.getSuffix()));

			if (!user.Groups.isEmpty()) {
				json.add("Groups", context.serialize(user.getGroupNamesList()));
			}
			if (!user.alternateAccounts.isEmpty()) {
				json.add("Alternative Accounts", context.serialize(user.alternateAccounts));
			}
			if (!user.permsContainer.isEmpty()) {
				json.add("Permissions", context.serialize(user.permsContainer));
			}
			if (!user.metaContainer.isEmpty()) {
				json.add("Meta", context.serialize(user.metaContainer));
			}
			return json;
		}
	}

	public static class Container extends ArrayList<User> implements IChatFormat {

		private Group defaultGroup;

		public boolean add(UUID uuid) {
			if (get(uuid) == null) {
				Group group = (defaultGroup == null)
						? ServerUtilities.getManager().groups.get("default")
								: defaultGroup;
						User newUser = new User(uuid, group);
						add(newUser);
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


