/*******************************************************************************
 * Copyright (C) 2019, Andrew2070
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
 *    This product includes software developed by [the] Auxility Project.
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
				LocalizationManager.get("constitution.format.list.header", new ChatComponentFormatted("{9|PERMISSIONS}")));
		for (String perm : this) {
			root.appendSibling(LocalizationManager.get("Constitution.format.permission", perm));
		}
		return root;
	}
}
