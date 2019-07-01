package Constitution.Permissions;


import java.util.ArrayList;
import java.util.Iterator;

import Constitution.Chat.ChatComponentFormatted;
import Constitution.Chat.ChatComponentList;
import Constitution.Chat.IChatFormat;
import Constitution.Localization.LocalizationManager;
import net.minecraft.util.text.ITextComponent;

public class PermissionsContainer extends ArrayList<String> implements IChatFormat {
	

	public Level hasPermission(String permission) {
		Level permLevel = Level.NONE;
		if (contains(permission)) {
			permLevel = Level.ALLOWED;
		}

		for (String p : this) {
			
			if (p.endsWith("*")) {
				if (permission.startsWith(p.substring(0, p.length() - 1))) {
					permLevel = Level.ALLOWED;
				} else if (p.startsWith("-") && permission.startsWith(p.substring(1, p.length() - 1))) {
					permLevel = Level.DENIED;
				}
			} else {
				if (permission.equals(p)) {
					permLevel = Level.ALLOWED;
				} else if (p.startsWith("-") && permission.equals(p.substring(1))) {
					permLevel = Level.DENIED;
				}
			}
		}

		return permLevel;
	}

	public boolean remove(String perm) {
		for (Iterator<String> it = iterator(); it.hasNext();) {
			String p = it.next();
			if (p.equals(perm)) {
				it.remove();
				return true;
			}
		}
		return false;
	}

	@Override
	public ITextComponent toChatMessage() {
		ITextComponent root = new ChatComponentList();
		root.appendSibling(
				LocalizationManager.get("Constitution.format.list.header", new ChatComponentFormatted("{9|PERMISSIONS}")));
		for (String perm : this) {
			root.appendSibling(LocalizationManager.get("Constitution.format.permission", perm));
		}
		return root;
	}
}