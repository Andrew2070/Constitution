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
package constitution.configuration;


import java.util.Map;

import com.google.common.collect.ImmutableMap;

import net.minecraftforge.common.config.Property;

/**
 * This acts as a wrapper around any of the basic types of data. Used for
 * loading from or into the config file.
 */
public class ConfigProperty<T> {

	private T value;

	public final String name;
	public final String category;
	public final String comment;

	public ConfigProperty(String name, String category, String comment, T defaultValue) {
		this.name = name;
		this.category = category;
		this.comment = comment;
		this.value = defaultValue;
	}

	/**
	 * Sets the value inside the property
	 */
	public void set(T value) {
		this.value = value;
	}

	/**
	 * Returns the value retained inside the property
	 */
	public T get() {
		return value;
	}

	public boolean isClassType(Class<?> clazz) {
		return value.getClass().isAssignableFrom(clazz);
	}

	public Property.Type getType() {
		return CONFIG_TYPES.get(value.getClass());
	}

	private static final Map<Class<?>, Property.Type> CONFIG_TYPES = ImmutableMap.<Class<?>, Property.Type>builder()
			.put(Integer.class, Property.Type.INTEGER).put(int.class, Property.Type.INTEGER)
			.put(Integer[].class, Property.Type.INTEGER).put(int[].class, Property.Type.INTEGER)
			.put(Double.class, Property.Type.DOUBLE).put(double.class, Property.Type.DOUBLE)
			.put(Double[].class, Property.Type.DOUBLE).put(double[].class, Property.Type.DOUBLE)
			.put(Float.class, Property.Type.DOUBLE).put(float.class, Property.Type.DOUBLE)
			.put(Float[].class, Property.Type.DOUBLE).put(float[].class, Property.Type.DOUBLE)
			.put(Boolean.class, Property.Type.BOOLEAN).put(boolean.class, Property.Type.BOOLEAN)
			.put(Boolean[].class, Property.Type.BOOLEAN).put(boolean[].class, Property.Type.BOOLEAN)
			.put(String.class, Property.Type.STRING).put(String[].class, Property.Type.STRING).build();

}
