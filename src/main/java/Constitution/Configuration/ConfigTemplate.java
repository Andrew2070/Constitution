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
package constitution.configuration;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;

import constitution.ConstitutionMain;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
/**
 * Config class which contains most of the basic functionality needed in a
 * config. All the fields of the type ConfigProperty inside the extended class
 * are automatically added to the loading list. Other ConfigProperties can be
 * added externally.
 */
public abstract class ConfigTemplate {

	protected String modID;
	protected Configuration config;
	protected List<ConfigProperty> properties = new ArrayList<ConfigProperty>();

	public void init(String forgeConfigPath, String modID) {
		init(new File(forgeConfigPath), modID);
	}

	public void init(File forgeConfigFile, String modID) {
		init(new Configuration(forgeConfigFile), modID);
	}

	public void init(Configuration forgeConfig, String modID) {
		this.config = forgeConfig;
		this.modID = modID;
		bind();
		reload();
	}

	public Configuration getForgeConfig() {
		return this.config;
	}

	public String getModID() {
		return this.modID;
	}

	/**
	 * Adds a ConfigProperty instance which can then be loaded/saved in the
	 * config.
	 */
	public void addBinding(ConfigProperty property) {
		addBinding(property, false);
	}

	/**
	 * Adds a ConfigProperty instance which can then be loaded/saved in the
	 * config. It will automatically reload if specified.
	 */
	public void addBinding(ConfigProperty property, boolean reload) {
		this.properties.add(property);
		if (reload) {
			reload();
		}
	}

	/**
	 * Loads the config file from the hard drive
	 */
	public void reload() {
		config.load();
		for (ConfigProperty property : properties) {
			ConfigCategory category = config.getCategory(property.category);
			Property forgeProp;
			if (!category.containsKey(property.name)) {
				forgeProp = new Property(property.name, property.get().toString(), property.getType());
				forgeProp.setComment(property.comment);
				category.put(property.name, forgeProp);
			} else {
				forgeProp = category.get(property.name);
				forgeProp.setComment(property.comment);
			}
			setProperty(property, forgeProp);
		}
		config.save();
	}

	private void bind() {
		for (Field field : getClass().getDeclaredFields()) {
			if (field.getType().isAssignableFrom(ConfigProperty.class)) {
				try {
					properties.add((ConfigProperty) field.get(this));
				} catch (IllegalAccessException ex) {
					ConstitutionMain.logger.info("Failed to access " + field.getName() + " while binding to config "
							+ config.getConfigFile().getName());
					ConstitutionMain.logger.info(ExceptionUtils.getStackTrace(ex));

				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void setProperty(ConfigProperty property, Property forgeProp) {
		try {
			if (property.isClassType(Integer.class)) {
				property.set(forgeProp.getInt());
			} else if (property.isClassType(Double.class)) {
				property.set(forgeProp.getDouble());
			} else if (property.isClassType(Boolean.class)) {
				property.set(forgeProp.getBoolean());
			} else if (property.isClassType(String.class)) {
				property.set(forgeProp.getString());
			} else if (property.isClassType(Integer[].class)) {
				property.set(forgeProp.getIntList());
			} else if (property.isClassType(Double[].class)) {
				property.set(forgeProp.getDoubleList());
			} else if (property.isClassType(Boolean[].class)) {
				property.set(forgeProp.getBooleanList());
			} else if (property.isClassType(String[].class)) {
				property.set(forgeProp.getStringList());
			}
		} catch (RuntimeException ex) {
			ConstitutionMain.logger.info("Config value of " + property.name + " in category " + property.category
					+ " was not of the proper type!");
			ConstitutionMain.logger.info(ExceptionUtils.getStackTrace(ex));
			throw ex;
		}
	}
}
