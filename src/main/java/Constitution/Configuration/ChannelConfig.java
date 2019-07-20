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
package Constitution.Configuration;
import com.google.common.reflect.TypeToken;
import com.google.gson.GsonBuilder;

import Constitution.Chat.Channels.Channel;
import Constitution.Configuration.JSONTemp.JSONConfig;
import Constitution.Permissions.PermissionManager;
public class ChannelConfig extends JSONConfig<Channel, Channel.Container> {

	private PermissionManager permissionsManager;
	public ChannelConfig(String path, PermissionManager manager) {
		super(path, "Channels");
		this.permissionsManager = manager;
		this.gsonType = new TypeToken<Channel.Container>() {
		}.getType();
		JSONConfig.gson = new GsonBuilder().registerTypeAdapter(Channel.class, new Channel.Serializer())
				.setPrettyPrinting()
				.create();
	}

	@Override
	protected Channel.Container newList() {
		Channel.Container container = new Channel.Container();
		container.add(new Channel());
		return container;
	}
	@Override
	public void create(Channel.Container items) {
		items.add(new Channel());
		super.create(items);
	}

	@Override
	public Channel.Container read() {
		Channel.Container channels = super.read();
		if (channels == null) {
			Channel.Container container = new Channel.Container();
			container.add(new Channel());
			return container;
		}
		else {
			permissionsManager.channels.addAll(channels);
		}
		return channels;
	}
	@Override
	public boolean validate(Channel.Container items) {
		if (items.size() == 0) {
			items.add(new Channel());
			return false;
		}
		return true;
	}
	@Override
	public void clearGsonCache() {
		JSONConfig.gson = null;
	}
}
