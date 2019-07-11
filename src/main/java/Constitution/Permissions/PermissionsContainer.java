package constitution.permissions;


import java.util.ArrayList;
import java.util.Iterator;

import constitution.chat.IChatFormat;
import constitution.chat.component.ChatComponentFormatted;
import constitution.chat.component.ChatComponentList;
import constitution.localization.LocalizationManager;
import net.minecraft.util.text.ITextComponent;

public class PermissionsContainer extends ArrayList<String> implements IChatFormat {

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