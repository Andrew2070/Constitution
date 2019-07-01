package Constitution.Commands.Executable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import Constitution.Constitution;
import Constitution.Chat.ChatComponentBorders;
import Constitution.Chat.ChatComponentFormatted;
import Constitution.Chat.ChatComponentGroupList;
import Constitution.Chat.ChatComponentList;
import Constitution.Chat.ChatComponentMultiPage;
import Constitution.Chat.ChatManager;
import Constitution.Commands.Command;
import Constitution.Commands.CommandResponse;
import Constitution.Exceptions.CommandException;
import Constitution.Exceptions.PermissionCommandException;
import Constitution.JSON.JSONMessageBuilder;
import Constitution.Localization.LocalizationManager;
import Constitution.Permissions.ConstitutionBridge;
import Constitution.Permissions.Group;
import Constitution.Permissions.PermissionProxy;
import Constitution.Permissions.User;
import Constitution.Utilities.ColorUtilities;
import Constitution.Utilities.PlayerUtilities;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class CoreCommands {
	
	private static ConstitutionBridge getManager() {
		return (ConstitutionBridge) PermissionProxy.getPermissionManager();
	}

	protected static Group getGroupFromName(String name) {
		Group group = getManager().groups.get(name);
		if (group == null) {
			throw new PermissionCommandException("Constitution.perm.cmd.err.group.notExist",
					LocalizationManager.get("Constitution.format.group.short", name));
		}
		return group;
	}

	protected static UUID getUUIDFromUsername(String username) {
		UUID uuid = PlayerUtilities.getUUIDFromName(username);
		if (uuid == null) {
			throw new PermissionCommandException("Constitution.perm.cmd.err.player.notExist",
					LocalizationManager.get("Constitution.format.user.short", username));
		}
		return uuid;
	}
//Misc Commands:
		@Command(name = "cperm",
				permission = "Constitution.perm.cmd",
				syntax = "/cperm <command>",
				alias = { "conpermission", "Conpermission", "conperm", "Conperm", "constitutionperm", "ConstitutionPerm", "constitution", "Constitution", "cperm", "Cperm" },
				description = "Root Command For All Permission Commands")
		public static CommandResponse permCommand(ICommandSender sender, List<String> args) {
			return CommandResponse.SEND_HELP_MESSAGE;
	}

		@Command(name = "config",
				permission = "Constitution.perm.cmd.config",
				parentName = "Constitution.perm.cmd", syntax = "/cperm config <command>",
				description = "Base Configuration Parent Command")
		public static CommandResponse configCommand(ICommandSender sender, List<String> args) {
			return CommandResponse.SEND_HELP_MESSAGE;
	}

		@Command(name = "reload",
				permission = "Constitution.perm.cmd.config.reload",
				parentName = "Constitution.perm.cmd.config",
				syntax = "/cperm config reload",
				description = "Reloads Configuration Files")
		public static CommandResponse configReloadCommand(ICommandSender sender, List<String> args) {
			Constitution.instance.loadConfig();
			// REF: Change these to localized versions of themselves
			ChatManager.send(sender, "Constitution.notification.config.reloaded");
			if (PermissionProxy.getPermissionManager() instanceof ConstitutionBridge) {
				((ConstitutionBridge) PermissionProxy.getPermissionManager()).loadConfigs();
					ChatManager.send(sender, "Constitution.notification.permissions.config.reloaded");
				} else {
					ChatManager.send(sender, "Constitution.notification.permissions.third_party");
				}
			return CommandResponse.DONE;
		}
//Group Commands:
		@Command(name = "group",
				permission = "Constitution.perm.cmd.group",
				parentName = "Constitution.perm.cmd",
				syntax = "/cperm group <command>",
				description = "Base Group Command/Parent Command For Group Sub Commands")
		public static CommandResponse groupCommand(ICommandSender sender, List<String> args) {
			return CommandResponse.SEND_HELP_MESSAGE;
		}
		
		@Command(name = "info",
				permission = "Constitution.perm.cmd.group.info",
				parentName = "Constitution.perm.cmd.group",
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
					throw new CommandException("Constitution.perm.cmd.err.notPlayer", args.get(0));
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
				ITextComponent header = LocalizationManager.get("Constitution.format.list.header", new ChatComponentFormatted("{6|%s}", ChatComponentBorders.borderEditor(group.getName())));
				ChatManager.send(sender, "Constitution.format.group.long",
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
				permission = "Constitution.perm.cmd.group.list",
				parentName = "Constitution.perm.cmd.group",
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
				permission = "Constitution.perm.cmd.group.add",
				parentName = "Constitution.perm.cmd.group",
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
			ChatManager.send(sender, LocalizationManager.get("Constitution.notification.group.added", group.getName()));
			return CommandResponse.DONE;
		}

		@Command(name = "delete",
				permission = "Constitution.perm.cmd.group.delete",
				parentName = "Constitution.perm.cmd.group",
				syntax = "/cperm group delete <name>",
				description = "Deletes A Selected Group")
		public static CommandResponse groupDeleteCommand(ICommandSender sender, List<String> args) {
			if (args.size() < 1) {
				return CommandResponse.SEND_SYNTAX;
			}
			if (getGroupFromName(args.get(0))==null) {
				ChatManager.send(sender, LocalizationManager.get("Constitution.perm.cmd.err.group.notExist", args.get(0)));
				return CommandResponse.DONE;
			}
			Group group = getGroupFromName(args.get(0));
			getManager().groups.remove(group);
			getManager().saveGroups();
			ChatManager.send(sender, LocalizationManager.get("Constitution.notification.group.deleted", group.getName()));
			return CommandResponse.DONE;
		}

		@Command(name = "rename",
				permission = "Constitution.perm.cmd.group.rename",
				parentName = "Constitution.perm.cmd.group",
				syntax = "/cperm group rename <group> <name>",
				description = "Renames A Selected Group")
		public static CommandResponse groupRenameCommand(ICommandSender sender, List<String> args) {
			if (args.size() < 2) {
				return CommandResponse.SEND_SYNTAX;
			}
			if (getGroupFromName(args.get(0))==null) {
				ChatManager.send(sender, LocalizationManager.get("Constitution.perm.cmd.err.group.notExist", args.get(0)));
				return CommandResponse.DONE;
			}
			Group group = getGroupFromName(args.get(0));
			String originalGroupName = group.getName();
			group.setName(args.get(1));
			getManager().saveGroups();
			ChatManager.send(sender, LocalizationManager.get("Constitution.notification.group.renamed", originalGroupName, group.getName()));
			return CommandResponse.DONE;
		}
		
		@Command(name = "desc",
				permission = "Constitution.perm.cmd.group.desc",
				parentName = "Constitution.perm.cmd.group",
				syntax = "/cperm group desc <group> <desc>",
				description = "Sets/Overwrites the Description of target Group")
		public static CommandResponse groupDescCommand(ICommandSender sender, List<String> args) {
			if (args.size() < 2) {
				return CommandResponse.SEND_SYNTAX;
			}
			if (getManager().groups.get(args.get(0))==null) {
				ChatManager.send(sender, LocalizationManager.get("Constitution.perm.cmd.err.group.notExist", args.get(0)));
				return CommandResponse.DONE;
			}
			Group group = getGroupFromName(args.get(0));
			group.setDesc(args.get(1));
			getManager().saveGroups();
			ChatManager.send(sender, LocalizationManager.get("Constitution.notification.group.desc.changed", group.getName(), group.getDesc()));
			return CommandResponse.DONE;
		}
		
		@Command(name = "prefix",
				permission = "Constitution.perm.cmd.group.prefix",
				parentName = "Constitution.perm.cmd.group",
				syntax = "/cperm group prefix",
				description = "Base Group Prefix Editing Command")
		public static CommandResponse groupPermPrefix(ICommandSender sender, List<String> args) {
			return CommandResponse.SEND_HELP_MESSAGE;
		}
		
		@Command(name = "add",
				permission = "Constitution.perm.cmd.group.prefix.add",
				parentName = "Constitution.perm.cmd.group.prefix",
				syntax = "/cperm group prefix add <Group> <Prefix>",
				description = "Creates Prefix For Specified Group")
		public static CommandResponse groupPermPrefixRemove(ICommandSender sender, List<String> args) {
			if (args.size() < 2) {
				return CommandResponse.SEND_SYNTAX;
			}
			if (getGroupFromName(args.get(0))==null) {
				ChatManager.send(sender, LocalizationManager.get("Constitution.perm.cmd.err.group.notExist", args.get(0)));
				return CommandResponse.DONE;
			}
			Group group = getManager().groups.get(args.get(0));
			group.setPrefix(args.get(1));
			getManager().saveGroups();
			ChatManager.send(sender, LocalizationManager.get("Constitution.notification.group.prefix.added", group.getPrefix(), group.getName()));
			return CommandResponse.DONE;
		}
		
		@Command(name = "remove",
				permission = "Constitution.perm.cmd.group.prefix.remove",
				parentName = "Constitution.perm.cmd.group.prefix",
				syntax = "/cperm group prefix remove <Group>",
				description = "Removes/Clears The Specified Group's Prefix")
		public static CommandResponse groupPermPrefixAdd(ICommandSender sender, List<String> args) {
			if (args.size() < 1) {
				return CommandResponse.SEND_SYNTAX;
			}
			if (getGroupFromName(args.get(0))==null) {
				ChatManager.send(sender, LocalizationManager.get("Constitution.perm.cmd.err.group.notExist", args.get(0)));
				return CommandResponse.DONE;
			}
			Group group = getManager().groups.get(args.get(0));
			group.setPrefix("");
			getManager().saveGroups();
			ChatManager.send(sender, LocalizationManager.get("Constitution.notification.group.prefix.removed", group.getName()));
			return CommandResponse.DONE;
		}
		
		@Command(name = "suffix",
				permission = "Constitution.perm.cmd.group.suffix",
				parentName = "Constitution.perm.cmd.group",
				syntax = "/cperm group suffix",
				description = "Base Group Suffix Editing Command")
		public static CommandResponse groupPermSuffix(ICommandSender sender, List<String> args) {
			return CommandResponse.SEND_HELP_MESSAGE;
		}
		
		@Command(name = "add",
				permission = "Constitution.perm.cmd.group.suffix.add",
				parentName = "Constitution.perm.cmd.group.suffix",
				syntax = "/cperm group suffix add <Group> <Suffix>",
				description = "Creates Suffix For Specified Group")
		public static CommandResponse groupPermSuffixAdd(ICommandSender sender, List<String> args) {
			if (args.size() < 2) {
				return CommandResponse.SEND_SYNTAX;
			}
			
			if (getGroupFromName(args.get(0))==null) {
				ChatManager.send(sender, LocalizationManager.get("Constitution.perm.cmd.err.group.notExist", args.get(0)));
				return CommandResponse.DONE;
			}
			
			Group group = getManager().groups.get(args.get(0));
			group.setSuffix(args.get(1));
			getManager().saveGroups();
			ChatManager.send(sender, LocalizationManager.get("Constitution.notification.group.suffix.added", group.getPrefix(), group.getName()));
			return CommandResponse.DONE;
		}
		
		@Command(name = "remove",
				permission = "Constitution.perm.cmd.group.suffix.remove",
				parentName = "Constitution.perm.cmd.group.suffix",
				syntax = "/cperm group suffix remove <Group>",
				description = "Removes/Clears The Specified Group's Suffix")
		public static CommandResponse groupPermSuffixRemove(ICommandSender sender, List<String> args) {
			if (args.size() < 1) {
				return CommandResponse.SEND_SYNTAX;
			}
			
			if (getGroupFromName(args.get(0))==null) {
				ChatManager.send(sender, LocalizationManager.get("Constitution.perm.cmd.err.group.notExist", args.get(0)));
				return CommandResponse.DONE;
			}
			Group group = getManager().groups.get(args.get(0));
			group.setSuffix("");
			getManager().saveGroups();
			ChatManager.send(sender, LocalizationManager.get("Constitution.notification.group.suffix.removed", group.getName()));
			return CommandResponse.DONE;
		}
		
		@Command(name = "perm",
				permission = "Constitution.perm.cmd.group.perm",
				parentName = "Constitution.perm.cmd.group",
				syntax = "/cperm group perm <command>",
				description = "Base Group Permission Editing Command")
		public static CommandResponse groupPermCommand(ICommandSender sender, List<String> args) {
			return CommandResponse.SEND_HELP_MESSAGE;
		}

		@Command(name = "add",
				permission = "Constitution.perm.cmd.group.perm.add",
				parentName = "Constitution.perm.cmd.group.perm",
				syntax = "/cperm group perm add <group> <perm>",
				description = "Adds Selected Permission Node To A Defined Group")
		public static CommandResponse groupPermAddCommand(ICommandSender sender, List<String> args) {
			if (args.size() < 2) {
				return CommandResponse.SEND_SYNTAX;
			}
			
			if (getGroupFromName(args.get(0))==null) {
				ChatManager.send(sender, LocalizationManager.get("Constitution.perm.cmd.err.group.notExist", args.get(0)));
				return CommandResponse.DONE;
			}
			Group group = getGroupFromName(args.get(0));
			group.permsContainer.add(args.get(1));
			getManager().saveGroups();
			ChatManager.send(sender, LocalizationManager.get("Constitution.notification.perm.added", args.get(1), "Group", group.getName()));
			return CommandResponse.DONE;
		}

		@Command(name = "remove",
				permission = "Constitution.perm.cmd.group.perm.remove",
				parentName = "Constitution.perm.cmd.group.perm",
				syntax = "/cperm group perm remove <group> <perm>",
				description = "Removes Selected Permission Node From a Defined Group")
		public static CommandResponse groupPermRemoveCommand(ICommandSender sender, List<String> args) {
			if (args.size() < 2) {
				return CommandResponse.SEND_SYNTAX;
			}
			
			if (getGroupFromName(args.get(0))==null) {
				ChatManager.send(sender, LocalizationManager.get("Constitution.perm.cmd.err.group.notExist", args.get(0)));
				return CommandResponse.DONE;
			}
			
			Group group = getGroupFromName(args.get(0));
			group.permsContainer.remove(args.get(1));
			getManager().saveGroups();
			ChatManager.send(sender, LocalizationManager.get("Constitution.notification.perm.removed", args.get(1), "Group", group.getName()));
			return CommandResponse.DONE;
		}
		
		@Command(name = "list",
				permission = "Constitution.perm.cmd.group.perm.list",
				parentName = "Constitution.perm.cmd.group.perm",
				syntax = "/cperm group perm list <group>",
				description = "Lists The Permission Nodes of A Selected Group")
		public static CommandResponse groupPermListCommand(ICommandSender sender, List<String> args) {
			if (args.size() < 1) {
				return CommandResponse.SEND_SYNTAX;
			}
			
			if (getManager().groups.get(args.get(0))==null) {
				ChatManager.send(sender, LocalizationManager.get("Constitution.perm.cmd.err.group.notExist", args.get(0)));
				return CommandResponse.DONE;
			}
			
			Group group = getGroupFromName(args.get(0));
			ChatManager.send(sender, group.permsContainer.toChatMessage());
			return CommandResponse.DONE;
		}
	//User Commands:
		@Command(name = "user",
				permission = "Constitution.perm.cmd.user",
				parentName = "Constitution.perm.cmd",
				syntax = "/cperm user <command>",
				description = "Base User Editing Command")
		public static CommandResponse userCommand(ICommandSender sender, List<String> args) {
			return CommandResponse.SEND_HELP_MESSAGE;
		}
		
		@Command(name = "info",
				permission = "Constitution.perm.cmd.user.info",
				parentName = "Constitution.perm.cmd.user",
				syntax = "/cperm user info <User>",
				description = "Shows In-Depth Properties of Sender or Targeted User")
		public static CommandResponse userInfoCommand(ICommandSender sender, List<String> args) {
			List<User> users = new ArrayList<User>();
			if (args.size()<1) {
				if (sender instanceof EntityPlayer) {
					users.add(getManager().users.get(sender.getCommandSenderEntity().getUniqueID()));
				} else {
					throw new CommandException("Constitution.perm.cmd.err.notPlayer", args.get(0));
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
				ITextComponent header = LocalizationManager.get("Constitution.format.list.header", new ChatComponentFormatted("{6|%s}", ChatComponentBorders.borderEditor(user.getUserName())));
				ChatManager.send(sender, "Constitution.format.user.long", header, user.getUUID(), user.getPrefix(), user.getSuffix(), location, user.getGroups().toChatMessage(), user.getIP(), user.getNodes());
			}
			return CommandResponse.DONE;
		}
		
		@Command(name = "list",
				permission = "Constitution.perm.cmd.user.list",
				parentName = "Constitution.perm.cmd.user",
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
				permission = "Constitution.perm.cmd.user.group",
				parentName = "Constitution.perm.cmd.user",
				syntax = "/cperm user group <command>",
				description = "Base Group Editing Command")
		public static CommandResponse userGroupCommand(ICommandSender sender, List<String> args) {
			return CommandResponse.SEND_HELP_MESSAGE;
		}

		@Command(name = "show",
				permission = "Constitution.perm.cmd.user.group.show",
				parentName = "Constitution.perm.cmd.user.group",
				syntax = "/cperm user group show <player>",
				description = "Displays The Groups of A Selected User")
		public static CommandResponse userGroupShowCommand(ICommandSender sender, List<String> args) {
			if (args.size() < 1) {
				return CommandResponse.SEND_SYNTAX;
			}
			
			if (getManager().users.get(args.get(0))==null) {
				ChatManager.send(sender, LocalizationManager.get("Constituion.perm.cmd.err.player.notExist", args.get(0)));
				return CommandResponse.DONE;
			}
			
			UUID uuid = getUUIDFromUsername(args.get(0));
			User user = getManager().users.get(uuid);
			ChatManager.send(sender, "Constitution.notification.user.group", user, user.getGroups());
			return CommandResponse.DONE;
		}
		
		@Command(name = "set",
				permission = "Constitution.perm.cmd.user.group.set",
				parentName = "Constitution.perm.cmd.user.group",
				syntax = "/cperm user group set <player> <group>",
				description = "Sets The Selected User To A Defined Group")
		public static CommandResponse userGroupSetCommand(ICommandSender sender, List<String> args) {
			if (args.size() < 2) {
				return CommandResponse.SEND_SYNTAX;
			}
			
			if (getManager().users.get(args.get(0))==null) {
				ChatManager.send(sender, LocalizationManager.get("Constituion.perm.cmd.err.player.notExist", args.get(0)));
				return CommandResponse.DONE;
			}
			
			if (getManager().groups.get(args.get(1))==null) {
				ChatManager.send(sender, LocalizationManager.get("Constitution.perm.cmd.err.group.notExist", args.get(0)));
				return CommandResponse.DONE;
			}
			
			UUID uuid = getUUIDFromUsername(args.get(0));
			Group group = getGroupFromName(args.get(1));
			User user = getManager().users.get(uuid);

			user.setGroup(group);
			getManager().saveUsers();
			ChatManager.send(sender, "Constitution.notification.user.group.set");
			return CommandResponse.DONE;
		}
		
		@Command(name = "prefix",
				permission = "Constitution.perm.cmd.user.prefix",
				parentName = "Constitution.perm.cmd.user",
				syntax = "/cperm user prefix",
				description = "Base User Prefix Editing Command")
		public static CommandResponse userPermPrefix(ICommandSender sender, List<String> args) {
			return CommandResponse.SEND_HELP_MESSAGE;
		}
		
		@Command(name = "add",
				permission = "Constitution.perm.cmd.user.prefix.add",
				parentName = "Constitution.perm.cmd.user.prefix",
				syntax = "/cperm user prefix add <User> <Prefix>",
				description = "Creates Prefix For Specified User")
		public static CommandResponse userPermPrefixRemove(ICommandSender sender, List<String> args) {
			if (args.size() < 2) {
				return CommandResponse.SEND_SYNTAX;
			}
			
			if (getManager().users.get(args.get(0))==null) {
				ChatManager.send(sender, LocalizationManager.get("Constituion.perm.cmd.err.player.notExist", args.get(0)));
				return CommandResponse.DONE;
			}
			
			User user = getManager().users.get(args.get(0));
			user.setPrefix(args.get(1));
			getManager().saveUsers();
			ChatManager.send(sender, LocalizationManager.get("Constitution.notification.user.prefix.added", user.getPrefix(), user.getUserName()));
			return CommandResponse.DONE;
		}
		
		@Command(name = "remove",
				permission = "Constitution.perm.cmd.user.prefix.remove",
				parentName = "Constitution.perm.cmd.user.prefix",
				syntax = "/cperm user prefix remove <User>",
				description = "Removes/Clears The Specified User's Prefix")
		public static CommandResponse userPermPrefixAdd(ICommandSender sender, List<String> args) {
			if (args.size() < 1) {
				return CommandResponse.SEND_SYNTAX;
			}
			
			if (getManager().users.get(args.get(0))==null) {
				ChatManager.send(sender, LocalizationManager.get("Constituion.perm.cmd.err.player.notExist", args.get(0)));
				return CommandResponse.DONE;
			}
			User user = getManager().users.get(args.get(0));	
			user.setPrefix("");
			getManager().saveUsers();
			ChatManager.send(sender, LocalizationManager.get("Constitution.notification.user.prefix.removed", user.getUserName()));
			return CommandResponse.DONE;
		}
		
		@Command(name = "suffix",
				permission = "Constitution.perm.cmd.user.suffix",
				parentName = "Constitution.perm.cmd.user",
				syntax = "/cperm user suffix",
				description = "Base User Suffix Editing Command")
		public static CommandResponse userPermSuffix(ICommandSender sender, List<String> args) {
			return CommandResponse.SEND_HELP_MESSAGE;
		}
		
		@Command(name = "add",
				permission = "Constitution.perm.cmd.user.suffix.add",
				parentName = "Constitution.perm.cmd.user.suffix",
				syntax = "/cperm user suffix add <User> <Suffix>",
				description = "Creates Suffix For Specified User")
		public static CommandResponse userPermSuffixAdd(ICommandSender sender, List<String> args) {
			if (args.size() < 2) {
				return CommandResponse.SEND_SYNTAX;
			}
			
			if (getManager().users.get(args.get(0))==null) {
				ChatManager.send(sender, LocalizationManager.get("Constituion.perm.cmd.err.player.notExist", args.get(0)));
				return CommandResponse.DONE;
			}
			
			User user = getManager().users.get(args.get(0));
			user.setSuffix(args.get(1));
			getManager().saveUsers();
			ChatManager.send(sender, LocalizationManager.get("Constitution.notification.user.suffix.added", user.getSuffix(), user.getUserName()));
			return CommandResponse.DONE;
		}
		
		@Command(name = "remove",
				permission = "Constitution.perm.cmd.user.suffix.remove",
				parentName = "Constitution.perm.cmd.user.suffix",
				syntax = "/cperm user suffix remove <User>",
				description = "Removes/Clears The Specified User's Suffix")
		public static CommandResponse userPermSuffixRemove(ICommandSender sender, List<String> args) {
			if (args.size() < 1) {
				return CommandResponse.SEND_SYNTAX;
			}
			
			if (getManager().users.get(args.get(0))==null) {
				ChatManager.send(sender, LocalizationManager.get("Constituion.perm.cmd.err.player.notExist", args.get(0)));
				return CommandResponse.DONE;
			}
			
			User user = getManager().users.get(args.get(0));
			
			user.setSuffix("");
			getManager().saveUsers();
			ChatManager.send(sender, LocalizationManager.get("Constitution.notification.user.suffix.removed", user.getUserName()));
			return CommandResponse.DONE;
		}

		@Command(name = "perm",
				permission = "Constitution.perm.cmd.user.perm",
				parentName = "Constitution.perm.cmd.user",
				syntax = "/cperm user perm <command>",
				description = "Base Permission Editing Command")
		public static CommandResponse userPermCommand(ICommandSender sender, List<String> args) {
			return CommandResponse.SEND_HELP_MESSAGE;
		}

		@Command(name = "add",
				permission = "Constitution.perm.cmd.user.perm.add",
				parentName = "Constitution.perm.cmd.user.perm",
				syntax = "/cperm user perm add <player> <perm>",
				description = "Adds Selected Permission Nodes to Users")
		public static CommandResponse userPermAddCommand(ICommandSender sender, List<String> args) {
			if (args.size() < 2) {
				return CommandResponse.SEND_SYNTAX;
			}
			
			if (getManager().users.get(args.get(0))==null) {
				ChatManager.send(sender, LocalizationManager.get("Constituion.perm.cmd.err.player.notExist", args.get(0)));
				return CommandResponse.DONE;
			}
			
			UUID uuid = getUUIDFromUsername(args.get(0));
			User user = getManager().users.get(uuid);
			user.permsContainer.add(args.get(1));
			getManager().saveUsers();
			ChatManager.send(sender, LocalizationManager.get("Constitution.notification.perm.added", args.get(1), "User", user.getUserName()));
			return CommandResponse.DONE;
		}

		@Command(name = "remove",
				permission = "Constitution.perm.cmd.user.perm.remove",
				parentName = "Constitution.perm.cmd.user.perm",
				syntax = "/cperm user perm remove <player> <perm>",
				description = "Removes Selected Permission Nodes From Users")
		public static CommandResponse userPermRemoveCommand(ICommandSender sender, List<String> args) {
			if (args.size() < 2) {
				return CommandResponse.SEND_SYNTAX;
			}
			
			if (getManager().users.get(args.get(0))==null) {
				ChatManager.send(sender, LocalizationManager.get("Constituion.perm.cmd.err.player.notExist", args.get(0)));
				return CommandResponse.DONE;
			}
			
			UUID uuid = getUUIDFromUsername(args.get(0));
			User user = getManager().users.get(uuid);
			user.permsContainer.remove(args.get(1));
			getManager().saveUsers();
			ChatManager.send(sender, LocalizationManager.get("Constitution.notification.perm.removed", args.get(1),"User", user.getUserName()));
			return CommandResponse.DONE;
		}

		@Command(name = "list",
				permission = "Constitution.perm.cmd.user.perm.list",
				parentName = "Constitution.perm.cmd.user.perm",
				syntax = "/cperm user perm list <player>",
				description = "Lists Permission Nodes of Selected User")
		public static CommandResponse userPermListCommand(ICommandSender sender, List<String> args) {
			if (args.size() < 1) {
				return CommandResponse.SEND_SYNTAX;
			}
			
			if (getManager().users.get(args.get(0))==null) {
				ChatManager.send(sender, LocalizationManager.get("Constituion.perm.cmd.err.player.notExist", args.get(0)));
				return CommandResponse.DONE;
			}
			
			UUID uuid = getUUIDFromUsername(args.get(0));
			User user = getManager().users.get(uuid);
			getManager().saveUsers();
			ChatManager.send(sender, user.permsContainer.toChatMessage());
			return CommandResponse.DONE;
		}

	
	
	
	
	
	
	
	
	
	
	
	
}
