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

import Constitution.Configuration.JSONTemp.JSONConfig;
import Constitution.Permissions.Group;
import Constitution.Permissions.Meta;
import Constitution.Permissions.PermissionManager;

public class GroupConfig extends JSONConfig<Group, Group.Container> {

	private PermissionManager permissionsManager;
	public GroupConfig(String path, PermissionManager manager) {
		super(path, "Groups");
		this.permissionsManager = manager;
		this.gsonType = new TypeToken<Group.Container>() {
		}.getType();
		JSONConfig.gson = new GsonBuilder().registerTypeAdapter(Group.class, new Group.Serializer())
				.registerTypeAdapter(Meta.Container.class, new Meta.Container.Serializer())
				.setPrettyPrinting()
				.create();
	}

	@Override
	protected Group.Container newList() {
		return new Group.Container();
	}

	@Override
	public void create(Group.Container items) {
		items.add(new Group());
		super.create(items);
	}

	@Override
	public Group.Container read() {
		Group.Container groups = super.read();
		if (groups==null) {
			return new Group.Container();
		} else {
			permissionsManager.groups.addAll(groups);
		}
		return null;
	}

	@Override
	public boolean validate(Group.Container items) {
		if (items.size() == 0) {
			Group group = new Group();
			items.add(group);
			return false;
		}
		return true;
	}
	@Override
	public void clearGsonCache() {
		JSONConfig.gson = null;
	}
}
