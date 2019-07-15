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
package constitution.permissions;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;

import constitution.configuration.json.JSONSerializerTemplate;

/**
 * Variables inside permission strings.
 */
public class Meta {

	public final String permission;
	public final int metadata;

	public Meta(String permission, int metadata) {
		this.permission = permission;
		this.metadata = metadata;
	}

	public static class Container extends ArrayList<Meta> {

		public Meta get(String permission) {
			for (Meta item : this) {
				if (item.permission.equals(permission)) {
					return item;
				}
			}
			return null;
		}

		/**
		 * Since Meta is represented by a "key":value format in Json it needs to
		 * stay in the Container rather than in the Meta class
		 */
		public static class Serializer extends JSONSerializerTemplate<Container> {

			@Override
			public void register(GsonBuilder builder) {
				builder.registerTypeAdapter(Container.class, this);
			}

			@Override
			public Container deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
					throws JsonParseException {
				JsonObject jsonObject = json.getAsJsonObject();

				Container container = new Container();
				for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
					container.add(new Meta(entry.getKey(), entry.getValue().getAsInt()));
				}

				return container;
			}

			@Override
			public JsonElement serialize(Container container, Type typeOfSrc, JsonSerializationContext context) {
				JsonObject jsonObject = new JsonObject();

				for (Meta meta : container) {
					jsonObject.addProperty(meta.permission, meta.metadata);
				}

				return jsonObject;
			}
		}
	}
}
