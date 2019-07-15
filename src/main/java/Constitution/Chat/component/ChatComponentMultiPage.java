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
 *    This product includes software developed by the <organization>.
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
package constitution.chat.component;

import net.minecraft.command.ICommandSender;

/**
 * A multi-page IChatComponent container. Used for sending large amount of lines
 * to a player.
 */
public class ChatComponentMultiPage extends ChatComponentContainer {

	private int maxComponentsPerPage = 15;

	public ChatComponentMultiPage(int maxComponentsPerPage) {
		this.maxComponentsPerPage = maxComponentsPerPage;
	}

	public void sendPage(ICommandSender sender, int page) {
		getHeader(page).send(sender);
		getPage(page).send(sender);
	}

	public ChatComponentContainer getHeader(int page) {
		ChatComponentContainer header = new ChatComponentContainer();
		header.add(					
				new ChatComponentFormatted("{6|========[Constitution]====}{9|[Menu]}{6|=====[Page: %s/%s]========}", page, getNumberOfPages()));

		return header;
	}

	public ChatComponentContainer getPage(int page) {
		ChatComponentContainer result = new ChatComponentContainer();
		result.addAll(this.subList(maxComponentsPerPage * (page - 1),
				maxComponentsPerPage * page > size() ? size() : maxComponentsPerPage * page));
		return result;
	}

	public int getNumberOfPages() {
		return (int) Math.ceil((float) size() / (float) maxComponentsPerPage);
	}
}
