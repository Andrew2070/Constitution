package constitution.commands.servercommands.permissions;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import constitution.ConstitutionMain;
import constitution.chat.ChatManager;
import constitution.chat.component.ChatComponentBorders;
import constitution.chat.component.ChatComponentFormatted;
import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import constitution.configuration.json.JSONMessageBuilder;
import constitution.exceptions.CommandException;
import constitution.exceptions.PermissionCommandException;
import constitution.localization.LocalizationManager;
import constitution.permissions.Group;
import constitution.permissions.PermissionManager;
import constitution.permissions.User;
import constitution.utilities.PlayerUtilities;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent;

public class PermissionCommands {
	
	private static PermissionManager getManager() {
		return PlayerUtilities.getManager();
	}

	protected static Group getGroupFromName(String name) {
		Group group = getManager().groups.get(name);
		if (group == null) {
			throw new PermissionCommandException("constitution.cmd.perm.err.group.notExist",
					LocalizationManager.get("constitution.format.group.short", name));
		}
		return group;
	}

	protected static UUID getUUIDFromUsername(String username) {
		UUID uuid = PlayerUtilities.getUUIDFromName(username);
		if (uuid == null) {
			throw new PermissionCommandException("constitution.cmd.perm.err.player.notExist",
					LocalizationManager.get("constitution.format.user.short", username));
		}
		return uuid;
	}
	
		@Command(name = "cperm",
				permission = "constitution.cmd.perm",
				syntax = "/cperm <command>",
				alias = { "conpermission", "Conpermission", "conperm", "Conperm", "constitutionperm", "ConstitutionPerm", "constitution", "Constitution", "cperm", "Cperm" },
				description = "Root Command For All Permission Commands")
		public static CommandResponse permCommand(ICommandSender sender, List<String> args) {
			return CommandResponse.SEND_HELP_MESSAGE;
	}

		@Command(name = "config",
				permission = "constitution.cmd.perm.config",
				parentName = "constitution.cmd.perm", syntax = "/cperm config <command>",
				description = "Base Configuration Parent Command")
		public static CommandResponse configCommand(ICommandSender sender, List<String> args) {
			return CommandResponse.SEND_HELP_MESSAGE;
	}

		@Command(name = "reload",
				permission = "constitution.cmd.perm.config.reload",
				parentName = "constitution.cmd.perm.config",
				syntax = "/cperm config reload",
				description = "Reloads Configuration Files")
		public static CommandResponse configReloadCommand(ICommandSender sender, List<String> args) {
			ConstitutionMain.instance.loadConfig();
			// REF: Change these to localized versions of themselves
					getManager().loadConfigs();
					ChatManager.send(sender, "constitution.notification.permissions.config.reloaded");
			return CommandResponse.DONE;
		}
//Group Commands:
		@Command(name = "group",
				permission = "constitution.cmd.perm.group",
				parentName = "constitution.cmd.perm",
				syntax = "/cperm group <command>",
				description = "Base Group Command/Parent Command For Group Sub Commands")
		public static CommandResponse groupCommand(ICommandSender sender, List<String> args) {
			return CommandResponse.SEND_HELP_MESSAGE;
		}
		
		@Command(name = "info",
				permission = "constitution.cmd.perm.group.info",
				parentName = "constitution.cmd.perm.group",
				syntax = "/cperm group info <Group>",
				description = "Shows In-Depth Properties of Sender or Targeted Group")
		public static CommandResponse groupInfoCommand(ICommandSender sender, List<String> args) {
			List<Group> groups = new ArrayList<Group>();
			if (args.size()<1) {
				if (sender instanceof EntityPlayer) {
					User user = getManager().users.get(sender.getCommandSenderEntity().getUniqueID());
					Group group = user.getDominantGroup();
					groups.add(group);
				} else {
					throw new CommandException("constitution.cmd.perm.err.notPlayer", args.get(0));
				}
			} else {
				if ("@a".equals(args.get(0))) {
					groups = new ArrayList<Group>(getManager().groups);
				} else {
					if (getManager().groups.get(args.get(0))!= null) {
						groups.add(getManager().groups.get(args.get(0)));
					}
				}
			}
			for (Group group : groups) {
				ITextComponent header = LocalizationManager.get("constitution.format.list.header", new ChatComponentFormatted("{6|%s}", ChatComponentBorders.borderEditor(group.getName())));
				ChatManager.send(sender, "constitution.format.group.long",
						header,
						group.getDesc(),
						group.getPrefix(),
						group.getSuffix(),
						group.getRank(),
						group.getUsers().toChatMessage(),
						group.getNodes());
			}
			return CommandResponse.DONE;
		}
		
		@Command(name = "list",
				permission = "constitution.cmd.perm.group.list",
				parentName = "constitution.cmd.perm.group",
				syntax = "/cperm group list",
				description = "Displays a List of Existing Groups and Their Properties")
		public static CommandResponse groupListCommand(ICommandSender sender, List<String> args) {
			JSONMessageBuilder msgBuilder = new JSONMessageBuilder();
			JSONMessageBuilder extraBuilder = msgBuilder.addExtra();
			
			for (Group group : getManager().groups) {
				ChatManager.send(sender, group.toChatMessage());
			}
			return CommandResponse.DONE;
		}
		
		@Command(name = "add",
				permission = "constitution.cmd.perm.group.add",
				parentName = "constitution.cmd.perm.group",
				syntax = "/cperm group add <name> [parents]",
				description = "Creates Group With Provided Name and Optional ParentGroup")
		public static CommandResponse groupAddCommand(ICommandSender sender, List<String> args) {
			if (args.size() < 1) {
				return CommandResponse.SEND_SYNTAX;
			}
			Group group = new Group(args.get(0));
			if (args.size() == 2) {
				group.parents.add(getGroupFromName(args.get(1)));
			}
			getManager().groups.add(group);
			getManager().saveGroups();
			ChatManager.send(sender, LocalizationManager.get("constitution.notification.group.added", group.getName()));
			return CommandResponse.DONE;
		}

		@Command(name = "delete",
				permission = "constitution.cmd.perm.group.delete",
				parentName = "constitution.cmd.perm.group",
				syntax = "/cperm group delete <name>",
				description = "Deletes A Selected Group")
		public static CommandResponse groupDeleteCommand(ICommandSender sender, List<String> args) {
			if (args.size() < 1) {
				return CommandResponse.SEND_SYNTAX;
			}
			if (getGroupFromName(args.get(0))==null) {
				ChatManager.send(sender, LocalizationManager.get("constitution.cmd.perm.err.group.notExist", args.get(0)));
				return CommandResponse.DONE;
			}
			Group group = getGroupFromName(args.get(0));
			getManager().groups.remove(group);
			getManager().saveGroups();
			ChatManager.send(sender, LocalizationManager.get("constitution.notification.group.deleted", group.getName()));
			return CommandResponse.DONE;
		}

		@Command(name = "rename",
				permission = "constitution.cmd.perm.group.rename",
				parentName = "constitution.cmd.perm.group",
				syntax = "/cperm group rename <group> <name>",
				description = "Renames A Selected Group")
		public static CommandResponse groupRenameCommand(ICommandSender sender, List<String> args) {
			if (args.size() < 2) {
				return CommandResponse.SEND_SYNTAX;
			}
			if (getGroupFromName(args.get(0))==null) {
				ChatManager.send(sender, LocalizationManager.get("constitution.cmd.perm.err.group.notExist", args.get(0)));
				return CommandResponse.DONE;
			}
			Group group = getGroupFromName(args.get(0));
			String originalGroupName = group.getName();
			group.setName(args.get(1));
			getManager().saveGroups();
			ChatManager.send(sender, LocalizationManager.get("constitution.notification.group.renamed", originalGroupName, group.getName()));
			return CommandResponse.DONE;
		}
		
		@Command(name = "desc",
				permission = "constitution.cmd.perm.group.desc",
				parentName = "constitution.cmd.perm.group",
				syntax = "/cperm group desc <group> <desc>",
				description = "Sets/Overwrites the Description of target Group")
		public static CommandResponse groupDescCommand(ICommandSender sender, List<String> args) {
			if (args.size() < 2) {
				return CommandResponse.SEND_SYNTAX;
			}
			if (getManager().groups.get(args.get(0))==null) {
				ChatManager.send(sender, LocalizationManager.get("constitution.cmd.perm.err.group.notExist", args.get(0)));
				return CommandResponse.DONE;
			}
			Group group = getGroupFromName(args.get(0));
			group.setDesc(args.get(1));
			getManager().saveGroups();
			ChatManager.send(sender, LocalizationManager.get("constitution.notification.group.desc.changed", group.getName(), group.getDesc()));
			return CommandResponse.DONE;
		}
		
		@Command(name = "prefix",
				permission = "constitution.cmd.perm.group.prefix",
				parentName = "constitution.cmd.perm.group",
				syntax = "/cperm group prefix",
				description = "Base Group Prefix Editing Command")
		public static CommandResponse groupPermPrefix(ICommandSender sender, List<String> args) {
			return CommandResponse.SEND_HELP_MESSAGE;
		}
		
		@Command(name = "add",
				permission = "constitution.cmd.perm.group.prefix.add",
				parentName = "constitution.cmd.perm.group.prefix",
				syntax = "/cperm group prefix add <Group> <Prefix>",
				description = "Creates Prefix For Specified Group")
		public static CommandResponse groupPermPrefixRemove(ICommandSender sender, List<String> args) {
			if (args.size() < 2) {
				return CommandResponse.SEND_SYNTAX;
			}
			if (getGroupFromName(args.get(0))==null) {
				ChatManager.send(sender, LocalizationManager.get("constitution.cmd.perm.err.group.notExist", args.get(0)));
				return CommandResponse.DONE;
			}
			Group group = getManager().groups.get(args.get(0));
			group.setPrefix(args.get(1));
			getManager().saveGroups();
			ChatManager.send(sender, LocalizationManager.get("constitution.notification.group.prefix.added", group.getPrefix(), group.getName()));
			return CommandResponse.DONE;
		}
		
		@Command(name = "remove",
				permission = "constitution.cmd.perm.group.prefix.remove",
				parentName = "constitution.cmd.perm.group.prefix",
				syntax = "/cperm group prefix remove <Group>",
				description = "Removes/Clears The Specified Group's Prefix")
		public static CommandResponse groupPermPrefixAdd(ICommandSender sender, List<String> args) {
			if (args.size() < 1) {
				return CommandResponse.SEND_SYNTAX;
			}
			if (getGroupFromName(args.get(0))==null) {
				ChatManager.send(sender, LocalizationManager.get("constitution.cmd.perm.err.group.notExist", args.get(0)));
				return CommandResponse.DONE;
			}
			Group group = getManager().groups.get(args.get(0));
			group.setPrefix("");
			getManager().saveGroups();
			ChatManager.send(sender, LocalizationManager.get("constitution.notification.group.prefix.removed", group.getName()));
			return CommandResponse.DONE;
		}
		
		@Command(name = "suffix",
				permission = "constitution.cmd.perm.group.suffix",
				parentName = "constitution.cmd.perm.group",
				syntax = "/cperm group suffix",
				description = "Base Group Suffix Editing Command")
		public static CommandResponse groupPermSuffix(ICommandSender sender, List<String> args) {
			return CommandResponse.SEND_HELP_MESSAGE;
		}
		
		@Command(name = "add",
				permission = "constitution.cmd.perm.group.suffix.add",
				parentName = "constitution.cmd.perm.group.suffix",
				syntax = "/cperm group suffix add <Group> <Suffix>",
				description = "Creates Suffix For Specified Group")
		public static CommandResponse groupPermSuffixAdd(ICommandSender sender, List<String> args) {
			if (args.size() < 2) {
				return CommandResponse.SEND_SYNTAX;
			}
			
			if (getGroupFromName(args.get(0))==null) {
				ChatManager.send(sender, LocalizationManager.get("constitution.cmd.perm.err.group.notExist", args.get(0)));
				return CommandResponse.DONE;
			}
			
			Group group = getManager().groups.get(args.get(0));
			group.setSuffix(args.get(1));
			getManager().saveGroups();
			ChatManager.send(sender, LocalizationManager.get("constitution.notification.group.suffix.added", group.getPrefix(), group.getName()));
			return CommandResponse.DONE;
		}
		
		@Command(name = "remove",
				permission = "constitution.cmd.perm.group.suffix.remove",
				parentName = "constitution.cmd.perm.group.suffix",
				syntax = "/cperm group suffix remove <Group>",
				description = "Removes/Clears The Specified Group's Suffix")
		public static CommandResponse groupPermSuffixRemove(ICommandSender sender, List<String> args) {
			if (args.size() < 1) {
				return CommandResponse.SEND_SYNTAX;
			}
			
			if (getGroupFromName(args.get(0))==null) {
				ChatManager.send(sender, LocalizationManager.get("constitution.cmd.perm.err.group.notExist", args.get(0)));
				return CommandResponse.DONE;
			}
			Group group = getManager().groups.get(args.get(0));
			group.setSuffix("");
			getManager().saveGroups();
			ChatManager.send(sender, LocalizationManager.get("constitution.notification.group.suffix.removed", group.getName()));
			return CommandResponse.DONE;
		}
		
		@Command(name = "perm",
				permission = "constitution.cmd.perm.group.perm",
				parentName = "constitution.cmd.perm.group",
				syntax = "/cperm group perm <command>",
				description = "Base Group Permission Editing Command")
		public static CommandResponse groupPermCommand(ICommandSender sender, List<String> args) {
			return CommandResponse.SEND_HELP_MESSAGE;
		}

		@Command(name = "add",
				permission = "constitution.cmd.perm.group.perm.add",
				parentName = "constitution.cmd.perm.group.perm",
				syntax = "/cperm group perm add <group> <perm>",
				description = "Adds Selected Permission Node To A Defined Group")
		public static CommandResponse groupPermAddCommand(ICommandSender sender, List<String> args) {
			if (args.size() < 2) {
				return CommandResponse.SEND_SYNTAX;
			}
			
			if (getGroupFromName(args.get(0))==null) {
				ChatManager.send(sender, LocalizationManager.get("constitution.cmd.perm.err.group.notExist", args.get(0)));
				return CommandResponse.DONE;
			}
			Group group = getGroupFromName(args.get(0));
			String node = args.get(1);
			group.permsContainer.add(node);
			getManager().saveGroups();
			ChatManager.send(sender, LocalizationManager.get("constitution.notification.perm.added", args.get(1), "Group", group.getName()));
			return CommandResponse.DONE;
		}

		@Command(name = "remove",
				permission = "constitution.cmd.perm.group.perm.remove",
				parentName = "constitution.cmd.perm.group.perm",
				syntax = "/cperm group perm remove <group> <perm>",
				description = "Removes Selected Permission Node From a Defined Group")
		public static CommandResponse groupPermRemoveCommand(ICommandSender sender, List<String> args) {
			if (args.size() < 2) {
				return CommandResponse.SEND_SYNTAX;
			}
			
			if (getGroupFromName(args.get(0))==null) {
				ChatManager.send(sender, LocalizationManager.get("constitution.cmd.perm.err.group.notExist", args.get(0)));
				return CommandResponse.DONE;
			}
			
			Group group = getGroupFromName(args.get(0));
			group.permsContainer.remove(args.get(1));
			getManager().saveGroups();
			ChatManager.send(sender, LocalizationManager.get("constitution.notification.perm.removed", args.get(1), "Group", group.getName()));
			return CommandResponse.DONE;
		}
		
		@Command(name = "list",
				permission = "constitution.cmd.perm.group.perm.list",
				parentName = "constitution.cmd.perm.group.perm",
				syntax = "/cperm group perm list <group>",
				description = "Lists The Permission Nodes of A Selected Group")
		public static CommandResponse groupPermListCommand(ICommandSender sender, List<String> args) {
			if (args.size() < 1) {
				return CommandResponse.SEND_SYNTAX;
			}
			
			if (getManager().groups.get(args.get(0))==null) {
				ChatManager.send(sender, LocalizationManager.get("constitution.cmd.perm.err.group.notExist", args.get(0)));
				return CommandResponse.DONE;
			}
			
			Group group = getGroupFromName(args.get(0));
			ChatManager.send(sender, group.permsContainer.toChatMessage());
			return CommandResponse.DONE;
		}
	//User Commands:
		@Command(name = "user",
				permission = "constitution.cmd.perm.user",
				parentName = "constitution.cmd.perm",
				syntax = "/cperm user <command>",
				description = "Base User Editing Command")
		public static CommandResponse userCommand(ICommandSender sender, List<String> args) {
			return CommandResponse.SEND_HELP_MESSAGE;
		}
		
		@Command(name = "info",
				permission = "constitution.cmd.perm.user.info",
				parentName = "constitution.cmd.perm.user",
				syntax = "/cperm user info <User>",
				description = "Shows In-Depth Properties of Sender or Targeted User")
		public static CommandResponse userInfoCommand(ICommandSender sender, List<String> args) {
			List<User> users = new ArrayList<User>();
			if (args.size()<1) {
				if (sender instanceof EntityPlayer) {
					users.add(getManager().users.get(sender.getCommandSenderEntity().getUniqueID()));
				} else {
					throw new CommandException("constitution.cmd.perm.err.notPlayer", args.get(0));
				}
			} else {
				if ("@a".equals(args.get(0))) {
					users = new ArrayList<User>(getManager().users);
				} else {
					if (getManager().users.get(args.get(0))!= null) {
						users.add(getManager().users.get(args.get(0)));
					}
				}
			}
			for (User user : users) {
				String location = "X: " + user.getLocation().getX() + " Y: " + user.getLocation().getY() + " Z:" + user.getLocation().getZ();
				ITextComponent header = LocalizationManager.get("constitution.format.list.header", new ChatComponentFormatted("{6|%s}", ChatComponentBorders.borderEditor(user.getUserName())));
				ChatManager.send(sender, "constitution.format.user.long", header, user.getUUID(), user.getPrefix(), user.getSuffix(), location, user.getGroups().toChatMessage(), user.getIP(), user.getNodes());
			}
			return CommandResponse.DONE;
		}
		
		@Command(name = "list",
				permission = "constitution.cmd.perm.user.list",
				parentName = "constitution.cmd.perm.user",
				syntax = "/cperm user list",
				description = "Displays a List of Users and their properties")
		public static CommandResponse userListCommand(ICommandSender sender, List<String> args) {
			JSONMessageBuilder msgBuilder = new JSONMessageBuilder();
			JSONMessageBuilder extraBuilder = msgBuilder.addExtra();
			
			for (User user : getManager().users) {
				ChatManager.send(sender, user.toChatMessage());
				extraBuilder.setClickEventRunCommand("/cperm info " + user.getUserName());
				extraBuilder.setHoverEventShowText(user.getformattedUserInfo());
			}
			return CommandResponse.DONE;
		}
		
		@Command(name = "group",
				permission = "constitution.cmd.perm.user.group",
				parentName = "constitution.cmd.perm.user",
				syntax = "/cperm user group <command>",
				description = "Base Group Editing Command")
		public static CommandResponse userGroupCommand(ICommandSender sender, List<String> args) {
			return CommandResponse.SEND_HELP_MESSAGE;
		}

		@Command(name = "show",
				permission = "constitution.cmd.perm.user.group.show",
				parentName = "constitution.cmd.perm.user.group",
				syntax = "/cperm user group show <player>",
				description = "Displays The Groups of A Selected User")
		public static CommandResponse userGroupShowCommand(ICommandSender sender, List<String> args) {
			if (args.size() < 1) {
				return CommandResponse.SEND_SYNTAX;
			}
			
			if (getManager().users.get(args.get(0))==null) {
				ChatManager.send(sender, LocalizationManager.get("constitution.cmd.perm.err.player.notExist", args.get(0)));
				return CommandResponse.DONE;
			}
			
			UUID uuid = getUUIDFromUsername(args.get(0));
			User user = getManager().users.get(uuid);
			ChatManager.send(sender, "constitution.notification.user.group", user, user.getGroups());
			return CommandResponse.DONE;
		}
		
		@Command(name = "set",
				permission = "constitution.cmd.perm.user.group.set",
				parentName = "constitution.cmd.perm.user.group",
				syntax = "/cperm user group set <player> <group>",
				description = "Sets The Selected User To A Defined Group")
		public static CommandResponse userGroupSetCommand(ICommandSender sender, List<String> args) {
			if (args.size() < 2) {
				return CommandResponse.SEND_SYNTAX;
			}
			
			if (getManager().users.get(args.get(0))==null) {
				ChatManager.send(sender, LocalizationManager.get("constitution.cmd.perm.err.player.notExist", args.get(0)));
				return CommandResponse.DONE;
			}
			
			if (getManager().groups.get(args.get(1))==null) {
				ChatManager.send(sender, LocalizationManager.get("constitution.cmd.perm.err.group.notExist", args.get(0)));
				return CommandResponse.DONE;
			}
			
			UUID uuid = getUUIDFromUsername(args.get(0));
			Group group = getGroupFromName(args.get(1));
			User user = getManager().users.get(uuid);

			user.setGroup(group);
			getManager().saveUsers();
			ChatManager.send(sender, "constitution.notification.user.group.set");
			return CommandResponse.DONE;
		}
		
		@Command(name = "prefix",
				permission = "constitution.cmd.perm.user.prefix",
				parentName = "constitution.cmd.perm.user",
				syntax = "/cperm user prefix",
				description = "Base User Prefix Editing Command")
		public static CommandResponse userPermPrefix(ICommandSender sender, List<String> args) {
			return CommandResponse.SEND_HELP_MESSAGE;
		}
		
		@Command(name = "add",
				permission = "constitution.cmd.perm.user.prefix.add",
				parentName = "constitution.cmd.perm.user.prefix",
				syntax = "/cperm user prefix add <User> <Prefix>",
				description = "Creates Prefix For Specified User")
		public static CommandResponse userPermPrefixRemove(ICommandSender sender, List<String> args) {
			if (args.size() < 2) {
				return CommandResponse.SEND_SYNTAX;
			}
			
			if (getManager().users.get(args.get(0))==null) {
				ChatManager.send(sender, LocalizationManager.get("constitution.cmd.perm.err.player.notExist", args.get(0)));
				return CommandResponse.DONE;
			}
			
			User user = getManager().users.get(args.get(0));
			user.setPrefix(args.get(1));
			getManager().saveUsers();
			ChatManager.send(sender, LocalizationManager.get("constitution.notification.user.prefix.added", user.getPrefix(), user.getUserName()));
			return CommandResponse.DONE;
		}
		
		@Command(name = "remove",
				permission = "constitution.cmd.perm.user.prefix.remove",
				parentName = "constitution.cmd.perm.user.prefix",
				syntax = "/cperm user prefix remove <User>",
				description = "Removes/Clears The Specified User's Prefix")
		public static CommandResponse userPermPrefixAdd(ICommandSender sender, List<String> args) {
			if (args.size() < 1) {
				return CommandResponse.SEND_SYNTAX;
			}
			
			if (getManager().users.get(args.get(0))==null) {
				ChatManager.send(sender, LocalizationManager.get("constitution.cmd.perm.err.player.notExist", args.get(0)));
				return CommandResponse.DONE;
			}
			User user = getManager().users.get(args.get(0));	
			user.setPrefix("");
			getManager().saveUsers();
			ChatManager.send(sender, LocalizationManager.get("constitution.notification.user.prefix.removed", user.getUserName()));
			return CommandResponse.DONE;
		}
		
		@Command(name = "suffix",
				permission = "constitution.cmd.perm.user.suffix",
				parentName = "constitution.cmd.perm.user",
				syntax = "/cperm user suffix",
				description = "Base User Suffix Editing Command")
		public static CommandResponse userPermSuffix(ICommandSender sender, List<String> args) {
			return CommandResponse.SEND_HELP_MESSAGE;
		}
		
		@Command(name = "add",
				permission = "constitution.cmd.perm.user.suffix.add",
				parentName = "constitution.cmd.perm.user.suffix",
				syntax = "/cperm user suffix add <User> <Suffix>",
				description = "Creates Suffix For Specified User")
		public static CommandResponse userPermSuffixAdd(ICommandSender sender, List<String> args) {
			if (args.size() < 2) {
				return CommandResponse.SEND_SYNTAX;
			}
			
			if (getManager().users.get(args.get(0))==null) {
				ChatManager.send(sender, LocalizationManager.get("constitution.cmd.perm.err.player.notExist", args.get(0)));
				return CommandResponse.DONE;
			}
			
			User user = getManager().users.get(args.get(0));
			user.setSuffix(args.get(1));
			getManager().saveUsers();
			ChatManager.send(sender, LocalizationManager.get("constitution.notification.user.suffix.added", user.getSuffix(), user.getUserName()));
			return CommandResponse.DONE;
		}
		
		@Command(name = "remove",
				permission = "constitution.cmd.perm.user.suffix.remove",
				parentName = "constitution.cmd.perm.user.suffix",
				syntax = "/cperm user suffix remove <User>",
				description = "Removes/Clears The Specified User's Suffix")
		public static CommandResponse userPermSuffixRemove(ICommandSender sender, List<String> args) {
			if (args.size() < 1) {
				return CommandResponse.SEND_SYNTAX;
			}
			
			if (getManager().users.get(args.get(0))==null) {
				ChatManager.send(sender, LocalizationManager.get("constitution.cmd.perm.err.player.notExist", args.get(0)));
				return CommandResponse.DONE;
			}
			
			User user = getManager().users.get(args.get(0));
			
			user.setSuffix("");
			getManager().saveUsers();
			ChatManager.send(sender, LocalizationManager.get("constitution.notification.user.suffix.removed", user.getUserName()));
			return CommandResponse.DONE;
		}

		@Command(name = "perm",
				permission = "constitution.cmd.perm.user.perm",
				parentName = "constitution.cmd.perm.user",
				syntax = "/cperm user perm <command>",
				description = "Base Permission Editing Command")
		public static CommandResponse userPermCommand(ICommandSender sender, List<String> args) {
			return CommandResponse.SEND_HELP_MESSAGE;
		}

		@Command(name = "add",
				permission = "constitution.cmd.perm.user.perm.add",
				parentName = "constitution.cmd.perm.user.perm",
				syntax = "/cperm user perm add <player> <perm>",
				description = "Adds Selected Permission Nodes to Users")
		public static CommandResponse userPermAddCommand(ICommandSender sender, List<String> args) {
			if (args.size() < 2) {
				return CommandResponse.SEND_SYNTAX;
			}
			
			if (getManager().users.get(args.get(0))==null) {
				ChatManager.send(sender, LocalizationManager.get("constitution.cmd.perm.err.player.notExist", args.get(0)));
				return CommandResponse.DONE;
			}
			
			UUID uuid = getUUIDFromUsername(args.get(0));
			User user = getManager().users.get(uuid);
			user.permsContainer.add(args.get(1));
			getManager().saveUsers();
			ChatManager.send(sender, LocalizationManager.get("constitution.notification.perm.added", args.get(1), "User", user.getUserName()));
			return CommandResponse.DONE;
		}

		@Command(name = "remove",
				permission = "constitution.cmd.perm.user.perm.remove",
				parentName = "constitution.cmd.perm.user.perm",
				syntax = "/cperm user perm remove <player> <perm>",
				description = "Removes Selected Permission Nodes From Users")
		public static CommandResponse userPermRemoveCommand(ICommandSender sender, List<String> args) {
			if (args.size() < 2) {
				return CommandResponse.SEND_SYNTAX;
			}
			
			if (getManager().users.get(args.get(0))==null) {
				ChatManager.send(sender, LocalizationManager.get("constitution.cmd.perm.err.player.notExist", args.get(0)));
				return CommandResponse.DONE;
			}
			
			UUID uuid = getUUIDFromUsername(args.get(0));
			User user = getManager().users.get(uuid);
			user.permsContainer.remove(args.get(1));
			getManager().saveUsers();
			ChatManager.send(sender, LocalizationManager.get("constitution.notification.perm.removed", args.get(1),"User", user.getUserName()));
			return CommandResponse.DONE;
		}

		@Command(name = "list",
				permission = "constitution.cmd.perm.user.perm.list",
				parentName = "constitution.cmd.perm.user.perm",
				syntax = "/cperm user perm list <player>",
				description = "Lists Permission Nodes of Selected User")
		public static CommandResponse userPermListCommand(ICommandSender sender, List<String> args) {
			if (args.size() < 1) {
				return CommandResponse.SEND_SYNTAX;
			}
			
			if (getManager().users.get(args.get(0))==null) {
				ChatManager.send(sender, LocalizationManager.get("constitution.cmd.perm.err.player.notExist", args.get(0)));
				return CommandResponse.DONE;
			}
			
			UUID uuid = getUUIDFromUsername(args.get(0));
			User user = getManager().users.get(uuid);
			getManager().saveUsers();
			ChatManager.send(sender, user.permsContainer.toChatMessage());
			return CommandResponse.DONE;
		}

	
	
	
	
	
	
	
	
	
	
	
	
}
