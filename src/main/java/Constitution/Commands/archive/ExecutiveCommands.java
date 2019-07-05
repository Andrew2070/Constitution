package constitution.commands.archive;

import java.util.List;
import java.util.UUID;

import constitution.chat.ChatManager;
import constitution.commands.engine.Command;
import constitution.commands.engine.CommandResponse;
import constitution.configuration.Config;
import constitution.exceptions.PermissionCommandException;
import constitution.localization.LocalizationManager;
import constitution.permissions.ConstitutionBridge;
import constitution.permissions.Group;
import constitution.permissions.PermissionProxy;
import constitution.permissions.User;
import constitution.utilities.PlayerUtilities;
import constitution.utilities.VanillaUtilities;
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
			throw new PermissionCommandException("constitution.perm.cmd.err.group.notExist",
					LocalizationManager.get("constitution.format.group.short", name));
		}
		return group;
	}
	protected static UUID getUUIDFromUsername(String username) {
		UUID uuid = PlayerUtilities.getUUIDFromName(username);
		if (uuid == null) {
			throw new PermissionCommandException("constitution.perm.cmd.err.player.notExist",
					LocalizationManager.get("constitution.format.user.short", username));
		}
		return uuid;
	}
	@Command(name = "cnx",
			permission = "constitution.exec.cmd",
			syntax = "/cnx <command>",
			alias = {},
			description = "Root Command For All Executive Commands")
	public static CommandResponse execCommand(ICommandSender sender, List<String> args) {
		return CommandResponse.SEND_HELP_MESSAGE;
	}
	@Command(name = "warn",
			permission = "constitution.exec.cmd.warn",
			
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
				ChatManager.send(sender, "constitution.notification.user.warned", user.toChatMessage(), reason);
				ChatManager.send(target, "constitution.notification.punish.warning", user.toChatMessage(), reason);
				return CommandResponse.DONE;
			} 
		}
		ChatManager.send(sender, "constitution.notification.user.warned", user.toChatMessage(), reason);
		return CommandResponse.DONE;
	}
	@Command(name = "ban",
			permission = "constitution.exec.cmd.ban",
			
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
				ChatManager.send(sender, "constitution.notification.user.banned", user.toChatMessage());
				return CommandResponse.DONE;
			} 
		}
		user.setBanned(true);
		target.connection.disconnect(new TextComponentString(user.getBanReason()));
		ChatManager.send(sender, "constitution.notification.user.banned", user.toChatMessage());
		return CommandResponse.DONE;
	}
	
	@Command(name = "tempban",
			permission = "constitution.exec.cmd.tempban",
			
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
				ChatManager.send(sender, "constitution.notification.user.tempBanned", user.toChatMessage());
				return CommandResponse.DONE;
			} 
		}
		user.setBanned(true);
		target.connection.disconnect(new TextComponentString(user.getBanReason()));
		ChatManager.send(sender, "constitution.notification.user.tempBanned", user.toChatMessage());
		return CommandResponse.DONE;
	}

	@Command(name = "ipban",
			permission = "constitution.exec.cmd.ipban",
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
				ChatManager.send(sender, "constitution.notification.user.ipbanned", user.getUserName());
				return CommandResponse.DONE;
			} 
		}
		user.setBanned(true);
		user.setIPBanned(true);
		target.connection.disconnect(new TextComponentString(user.getBanReason()));
		ChatManager.send(sender, "constitution.notification.user.ipbanned", user.getUserName());
		return CommandResponse.DONE;
	}
	
	@Command(name = "unban",
			permission = "constitution.exec.cmd.ipban",
			
			syntax = "/cnx unban <username> <reason>",
			alias = {"Unban, UNban, UNBAN"},
			description = "Unban Selected Player")
	public static CommandResponse unbanCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "mute",
			permission = "constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse muteCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "kick",
			permission = "constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse kickCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "immobalize",
			permission = "constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse immobalizeCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "afk",
			permission = "constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse afkCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "back",
			permission = "constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse backCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "burn",
			permission = "constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse burnCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "kill",
			permission = "constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse killCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "clearinventory",
			permission = "constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse clearInventoryCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "clearchat",
			permission = "constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse clearChatCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "enderchest",
			permission = "constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse enderChestCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "fly",
			permission = "constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse flyCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "god",
			permission = "constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse godCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "heal",
			permission = "constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse healCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "item",
			permission = "constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse itemCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "ignore",
			permission = "constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse ignoreCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "",
			permission = "constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse nearCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "",
			permission = "constitution.exec.cmd.",
			
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse lightningCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "ping",
			permission = "constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse pingCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "socialspy",
			permission = "constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse socialSpyCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "suicide",
			permission = "constitution.exec.cmd.",
			
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse suicideCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "tp",
			permission = "constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse tpCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "tphere",
			permission = "constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse tphereCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "tpa",
			permission = "constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse tpaCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "tpahere",
			permission = "constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse tpahereCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "tpacancel",
			permission = "constitution.exec.cmd.",
			
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse tpacancelCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "tpaccept",
			permission = "constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse tpacceptCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "tpall",
			permission = "constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse tpAllCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "tpdeny",
			permission = "constitution.exec.cmd.",
			
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse tpdenyCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "tpo",
			permission = "constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse tpoCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "tpohere",
			permission = "constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse tpohereCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "tppos",
			permission = "constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse tpposCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "tptoggle",
			permission = "constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse tpToggleCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "vanish",
			permission = "constitution.exec.cmd.",
			
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse vanishCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "workbench",
			permission = "constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse workbenchCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "sudo",
			permission = "constitution.exec.cmd.",
			
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse sudoCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "speed",
			permission = "constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse speedCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "mob",
			permission = "constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse spawnMobCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "repair",
			permission = "constitution.exec.cmd.",
			
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse repairCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "kickall",
			permission = "constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse kickAllCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "fireball",
			permission = "constitution.exec.cmd.",
			
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse fireballCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "feed",
			permission = "constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse feedCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "exp",
			permission = "constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse expCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "enchant",
			permission = "constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse enchantCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "depth",
			permission = "constitution.exec.cmd.",
			
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse depthCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "broadcast",
			permission = "constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse broadcastCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "broadcastall",
			permission = "constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse broadcastAllCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "sethome",
			permission = "constitution.exec.cmd.",
			
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse setHomeCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "delhome",
			permission = "constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse deleteHomeCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "home",
			permission = "constitution.exec.cmd.",
			
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse homeCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "hat",
			permission = "constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse hatCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "world",
			permission = "constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse worldCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "reply",
			permission = "constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {"r"},
			description = "")
	public static CommandResponse replyCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "getpos",
			permission = "constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse getPosCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "break",
			permission = "constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse breakBlockCommand(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}
	@Command(name = "break",
			permission = "constitution.exec.cmd.",
			syntax = "/cnx ",
			alias = {},
			description = "")
	public static CommandResponse Command(ICommandSender sender, List<String> args) {
		
		
		
		
		return CommandResponse.DONE;
	}

	
}
