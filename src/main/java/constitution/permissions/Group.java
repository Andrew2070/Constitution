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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
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
import constitution.chat.component.ChatComponentBorders;
import constitution.chat.component.ChatComponentFormatted;
import constitution.configuration.Config;
import constitution.configuration.json.JSONSerializerTemplate;
import constitution.localization.LocalizationManager;
import constitution.utilities.ServerUtilities;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;



public class Group implements IChatFormat {
	public						String 								name 				= "";
	private 					String 								desc 				= "";
	private 					String 								prefix 				= "";
	private 					String 								suffix 				= "";
	private 					Integer 							rank 				= null;
	public final 				PermissionsContainer 				permsContainer 		= new PermissionsContainer();
	public final    			Meta.Container 						metaContainer	    = new Meta.Container();
	public final 				Container 							parents 			= new Container();
	private final 				List<UUID> 							users 				= new ArrayList<UUID>();

	public Group() {
		this.name = Config.instance.defaultGroupName.get();
		this.rank = Config.instance.defaultGroupRank.get();
		this.desc = Config.instance.defaultGroupDesc.get();
		this.prefix = Config.instance.defaultGroupPrefix.get();
		this.suffix = Config.instance.defaultGroupSuffix.get();
		this.permsContainer.add("command.help");
		this.permsContainer.add("consitutiton.cmd");
		this.permsContainer.add("consitutiton.cmd.perm");
	}

	public Group(String name) {
		this.name = name;
		this.rank = Config.instance.defaultGroupRank.get();
		this.desc = Config.instance.defaultGroupDesc.get();
		this.prefix = Config.instance.defaultGroupPrefix.get();
		this.suffix = Config.instance.defaultGroupSuffix.get();
	}

	public Group(String name, Integer rank, String desc, String prefix, String suffix) {	
		this.name = name;
		this.rank = rank;
		this.desc = desc;
		this.prefix = prefix;
		this.suffix = suffix;

	}

	//Get Methods:

	public String getName() {
		return this.name;
	}

	public Integer getRank() {
		return this.rank;
	}

	public String getDesc() {
		return this.desc.replaceAll("\u0026([\\da-fk-or])", "\u00A7$1");
	}

	public String getPrefix() {
		return this.prefix.replaceAll("\u0026([\\da-fk-or])", "\u00A7$1");
	}

	public String getSuffix() {
		return this.suffix.replaceAll("\u0026([\\da-fk-or])", "\u00A7$1");
	}

	public String getNodes() {
		for (String node : this.permsContainer) {
			return node;
		}
		return "No Permissions Assigned";
	}

	public String getPerms() {
		for (String node : permsContainer) {
			return node;
		}
		return ("");
	}

	public User getUsers() {
		for (UUID uuid : users) {
			return ServerUtilities.getManager().users.get(uuid);
		}
		return null;
	}

	public Group getParents() {
		for (Group group : this.parents) {
			return group;
		}
		return null;
	}

	public UUID getUserUUIDS() {
		for (UUID uuid : users) {
			return uuid;
		}
		return null;
	}

	public Collection<String >getUserUUIDSListAsString() {
		List<String> uuids = new ArrayList<String>();
		for (UUID uuid : users) {
			uuids.add(uuid.toString());
		}
		return uuids;
	}
	//Set Methods:

	public void setName(String name) {
		this.name = name;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}
	public void setDesc(String desc) {
		this.desc = desc.replaceAll("\u0026([\\da-fk-or])", "\u00A7$1");
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix.replaceAll("\u0026([\\da-fk-or])", "\u00A7$1");
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix.replaceAll("\u0026([\\da-fk-or])", "\u00A7$1");
	}

	public void setNode(String node) {
		this.permsContainer.add(node);
	}

	public void setUser(UUID uuid) {
		this.users.add(uuid);
	}

	public void setParent(Group group) {
		this.parents.add(group);
	}

	@Override
	public ITextComponent toChatMessage() {
		ITextComponent header = LocalizationManager.get("constitution.format.list.header", new ChatComponentFormatted("{9|%s}", ChatComponentBorders.borderEditorHover((this.getName()))));
		ITextComponent hoverComponent = ((ChatComponentFormatted)LocalizationManager.get("constitution.format.group.long.hover",
				header,
				this.getDesc(),
				this.getRank(),
				this.getPrefix(),
				this.getSuffix(),
				this.permsContainer)).applyDelimiter("\n");
		return LocalizationManager.get("constitution.format.short", this.getName(), hoverComponent);
	}

	public static class Serializer extends JSONSerializerTemplate<Group> {

		@Override
		public void register(GsonBuilder builder) {
			builder.registerTypeAdapter(Group.class, this);
			new Meta.Container.Serializer().register(builder);
		}

		@Override
		public Group deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
			JsonObject jsonObject = json.getAsJsonObject();

			String name = jsonObject.get("Name").getAsString();
			Group group = new Group(name);
			group.setRank(jsonObject.get("Rank").getAsInt());
			group.setPrefix(jsonObject.get("Prefix").getAsString());
			group.setSuffix(jsonObject.get("Suffix").getAsString());
			if (jsonObject.has("Parents")) {
				List<String> parentNames = new ArrayList<String>(ImmutableList.copyOf(context.<String[]>deserialize(jsonObject.get("Parents"), String[].class)));	
				for (int i=0; i<ServerUtilities.getManager().groups.size(); i++) {
					if (ServerUtilities.getManager().groups.get(i).getName().equals(parentNames.get(i))) {
						group.setParent(ServerUtilities.getManager().groups.get(i));
					}
				}
			}
			if (jsonObject.has("Users")) {
				List<String> usersUUIDS = new ArrayList<String>(ImmutableList.copyOf(context.<String[]>deserialize(jsonObject.get("Users"), String[].class)));	
				for (String uuid : usersUUIDS) {
					UUID userID = UUID.fromString(uuid);
					group.setUser(userID);
				}
			}
			if (jsonObject.has("Permissions")) {
				group.permsContainer.addAll(context.<PermissionsContainer>deserialize(jsonObject.get("Permissions"), PermissionsContainer.class));
			}
			if (jsonObject.has("Meta")) {
				group.metaContainer.addAll(context.<Meta.Container>deserialize(jsonObject.get("Meta"), Meta.Container.class));
			}
			return group;
		}

		@Override
		public JsonElement serialize(Group group, Type typeOfSrc, JsonSerializationContext context) {
			JsonObject json = new JsonObject();
			json.addProperty("Name", group.getName().toString());
			json.add("Rank", context.serialize(group.getRank()));
			json.add("Prefix", context.serialize(group.getPrefix()));
			json.add("Suffix", context.serialize(group.getSuffix()));

			if (!group.parents.isEmpty()) {
				List<String> GroupNames = new ArrayList<String>();
				for (Group parent : group.parents) {
					GroupNames.add(parent.getName());
				}
				json.add("Parents", context.serialize(GroupNames));
			}
			if (!group.users.isEmpty()) {
				json.add("Users", context.serialize(group.getUserUUIDSListAsString()));
			}
			if (!group.permsContainer.isEmpty()) {
				json.add("Permissions", context.serialize(group.permsContainer));
			}
			if (!group.metaContainer.isEmpty()) {
				json.add("Meta", context.serialize(group.metaContainer));
			}

			return json;
		}
	}

	public static class Container extends ArrayList<Group> implements IChatFormat {

		@Override
		public boolean add(Group group) {
			if (ServerUtilities.getManager().groups.contains(group)) {
				return true;
			} else {
				super.add(group);
				return false;
			}
		}

		public void remove(String groupName) {
			for (Iterator<Group> it = iterator(); it.hasNext();) {
				Group group = it.next();
				if (group.getName().equals(groupName)) {
					it.remove();
					return;
				}
			}
		}
		public boolean contains(String groupName) {
			for (Group group : this) {
				if (group.getName().equals(groupName))
					return true;
			}
			return false;
		}
		public Group get(String groupName) {
			for (Group group : this) {
				if (group.getName().equals(groupName))
					return group;
			}
			return null;
		}

		@Override
		public ITextComponent toChatMessage() {
			ITextComponent root = new TextComponentString("");

			for (Group group : this) {
				if (root.getSiblings().size() > 0) {
					root.appendSibling(new ChatComponentFormatted("{7|, }"));
				}
				root.appendSibling(group.toChatMessage());
			}
			return root;
		}
	}
}

