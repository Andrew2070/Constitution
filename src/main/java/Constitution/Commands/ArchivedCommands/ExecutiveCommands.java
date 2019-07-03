package Constitution.Commands.ArchivedCommands;

import java.util.List;
import java.util.UUID;

import Constitution.Chat.ChatManager;
import Constitution.Commands.Engine.Command;
import Constitution.Commands.Engine.CommandResponse;
import Constitution.Configuration.Config;
import Constitution.Exceptions.PermissionCommandException;
import Constitution.Localization.LocalizationManager;
import Constitution.Permissions.ConstitutionBridge;
import Constitution.Permissions.Group;
import Constitution.Permissions.PermissionProxy;
import Constitution.Permissions.User;
import Constitution.Utilities.PlayerUtilities;
import Constitution.Utilities.VanillaUtilities;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;

public class ExecutiveCommands {
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
	@Command(name = "cnx",
			permission = "Constitution.exec.cmd",
			syntax = "/cnx <command>",
			alias = {},
			description = "Root Command For All Executive Commands")
	public static CommandResponse execCommand(ICommandSender sender, List<String> args) {
		return CommandResponse.SEND_HELP_MESSAGE;
	}
	@Command(name = "warn",
			permission = "Constitution.exec.cmd.warn",
			
			syntax = "/cnx warn <username> <reason>",
			alias = {"Warn"},
			description = "Warn Selected Player")
	public static CommandResponse warnCommand(ICommandSender sender, List<String> args) {
		User user = getManager().users.get(getUUIDFromUsername(args.get(0)));
		EntityPlayerMP target = VanillaUtilities.getMinecraftServer().getPlayerList().getPlayerByUUID(user.getUUID());
		String reason = Config.instance.defaultWarnMessage.get();
		//TODO: Cache Somewhere
		if (args.size() < 1) {
			return CommandResponse.SEND_SYNTAX;
		} else {
			if (args.size() == 2) {
				reason = args.get(1);
				ChatManager.send(sender, "Constitution.notification.user.warned", user.toChatMessage(), reason);
				ChatManager.send(target, "Constitution.notification.punish.warning", user.toChatMessage(), reason);
				return CommandResponse.DONE;
			} 
		}
		ChatManager.send(sender, "Constitution.notification.user.warned", user.toChatMessage(), reason);
		return CommandResponse.DONE;
	}
	@Command(name = "ban",
			permission = "Constitution.exec.cmd.ban",
			
			syntax = "/cnx ban <username> <reason>",
			alias = {"Ban"},
			description = "Permanently Ban Selected Player")
	public static CommandResponse banCommand(ICommandSender sender, List<String> args) {
		User user = getManager().users.get(getUUIDFromUsername(args.get(0)));
		EntityPlayerMP target = VanillaUtilities.getMinecraftServer().getPlayerList().getPlayerByUUID(user.getUUID());
		if (args.size() < 1) {
			return CommandResponse.SEND_SYNTAX;
		} else {
			if (args.size() == 2) {
				user.setBanned(true);
				user.setBanReason(args.get(1));
				target.connection.disconnect(new TextComponentString(user.getBanReason()));
				ChatManager.send(sender, "Constitution.notification.user.banned", user.toChatMessage());
				return CommandResponse.DONE;
			} 
		}
		user.setBanned(true);
		target.connection.disconnect(new TextComponentString(user.getBanReason()));
		ChatManager.send(sender, "Constitution.notification.user.banned", user.toChatMessage());
		return CommandResponse.DONE;
	}
	
	@Command(name = "tempban",
			permission = "Constitution.exec.cmd.tempban",
			
			syntax = "/cnx tempban <username> <reason> <yy:mm:dd:hh:mm:ss>",
			alias = {"Tempban", "TempBan"},
			description = "Temporarily Ban Selected Player")
	public static CommandResponse tempbanCommand(ICommandSender sender, List<String> args) {
		User user = getManager().users.get(getUUIDFromUsername(args.get(0)));
		EntityPlayerMP target = VanillaUtilities.getMinecraftServer().getPlayerList().getPlayerByUUID(user.getUUID());
		if (args.size() < 1) {
			return CommandResponse.SEND_SYNTAX;
		} else {
			if (args.size() == 3) {
				user.setBanned(true);
				//user.setBanDuration(time);
				user.setBanReason(args.get(1));
				target.connection.disconnect(new TextComponentString(user.getBanReason()));
				ChatManager.send(sender, "Constitution.notification.user.tempBanned", user.toChatMessage());
				return CommandResponse.DONE;
			} 
		}
		user.setBanned(true);
		target.connection.disconnect(new TextComponentString(user.getBanReason()));
		ChatManager.send(sender, "Constitution.notification.user.tempBanned", user.toChatMessage());
		return CommandResponse.DONE;
	}

	@Command(name = "ipban",
			permission = "Constitution.exec.cmd.ipban",
			syntax = "/cnx ipban <username> <reason>",
			alias = {"IPBAN", "IPban", "ipBan", "Ipban"},
			description = "Permanently Ban Selected Player")
	public static CommandResponse ipbanCommand(ICommandSender sender, List<String> args) {
		User user = getManager().users.get(getUUIDFromUsername(args.get(0)));
		EntityPlayerMP target = VanillaUtilities.getMinecraftServer().getPlayerList().getPlayerByUUID(user.getUUID());
		if (args.size() < 1) {
			return CommandResponse.SEND_SYNTAX;
		} else {
			if (args.size() == 2) {
				user.setBanned(true);
				user.setIPBanned(true);
				user.setBanReason(args.get(1));
				target.connection.disconnect(new TextComponentString(user.getBanReason()));
				ChatManager.send(sender, "Constitution.notification.user.ipbanned", user.getUserName());
				return CommandResponse.DONE;
			} 
		}
		user.setBanned(true);
		user.setIPBanned(true);
		target.connection.disconnect(new TextComponentString(user.getBanReason()));
		ChatManager.send(sender, "Constitution.notification.user.ipbanned", user.getUserName());
		return CommandResponse.DONE;
	}
	
	@Command(name = "unban",
			permission = "Constitution.exec.cmd.ipban",
			
			syntax = "/cnx unban <username> <reason>",
			alias = {"Unban, UNban, UNBAN"},
			description = "Unban Selected Player")
	public static CommandResponse unbanCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "mute",
			permission = "Constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse muteCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "kick",
			permission = "Constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse kickCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "immobalize",
			permission = "Constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse immobalizeCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "afk",
			permission = "Constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse afkCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "back",
			permission = "Constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse backCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "burn",
			permission = "Constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse burnCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "kill",
			permission = "Constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse killCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "clearinventory",
			permission = "Constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse clearInventoryCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "clearchat",
			permission = "Constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse clearChatCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "enderchest",
			permission = "Constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse enderChestCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "fly",
			permission = "Constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse flyCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "god",
			permission = "Constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse godCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "heal",
			permission = "Constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse healCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "item",
			permission = "Constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse itemCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "ignore",
			permission = "Constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse ignoreCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "",
			permission = "Constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse nearCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "",
			permission = "Constitution.exec.cmd.",
			
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse lightningCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "ping",
			permission = "Constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse pingCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "socialspy",
			permission = "Constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse socialSpyCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "suicide",
			permission = "Constitution.exec.cmd.",
			
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse suicideCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "tp",
			permission = "Constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse tpCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "tphere",
			permission = "Constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse tphereCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "tpa",
			permission = "Constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse tpaCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "tpahere",
			permission = "Constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse tpahereCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "tpacancel",
			permission = "Constitution.exec.cmd.",
			
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse tpacancelCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "tpaccept",
			permission = "Constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse tpacceptCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "tpall",
			permission = "Constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse tpAllCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "tpdeny",
			permission = "Constitution.exec.cmd.",
			
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse tpdenyCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "tpo",
			permission = "Constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse tpoCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "tpohere",
			permission = "Constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse tpohereCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "tppos",
			permission = "Constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse tpposCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "tptoggle",
			permission = "Constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse tpToggleCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "vanish",
			permission = "Constitution.exec.cmd.",
			
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse vanishCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "workbench",
			permission = "Constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse workbenchCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "sudo",
			permission = "Constitution.exec.cmd.",
			
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse sudoCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "speed",
			permission = "Constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse speedCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "mob",
			permission = "Constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse spawnMobCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "repair",
			permission = "Constitution.exec.cmd.",
			
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse repairCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "kickall",
			permission = "Constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse kickAllCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "fireball",
			permission = "Constitution.exec.cmd.",
			
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse fireballCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "feed",
			permission = "Constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse feedCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "exp",
			permission = "Constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse expCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "enchant",
			permission = "Constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse enchantCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "depth",
			permission = "Constitution.exec.cmd.",
			
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse depthCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "broadcast",
			permission = "Constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse broadcastCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "broadcastall",
			permission = "Constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse broadcastAllCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "sethome",
			permission = "Constitution.exec.cmd.",
			
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse setHomeCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "delhome",
			permission = "Constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse deleteHomeCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "home",
			permission = "Constitution.exec.cmd.",
			
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse homeCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "hat",
			permission = "Constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse hatCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "world",
			permission = "Constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse worldCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "reply",
			permission = "Constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {"r"},
			description = "")
	public static CommandResponse replyCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "getpos",
			permission = "Constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse getPosCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "break",
			permission = "Constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse breakBlockCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "break",
			permission = "Constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse Command(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}

	
}
