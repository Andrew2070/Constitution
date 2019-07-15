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
package constitution.configuration.json;



import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import constitution.ConstitutionMain;

/**
 * An abstract class for all JSON configs.
 * Methods are usually overrided but called inside their overrided method.
 */
public abstract class JSONConfig<T, L extends List<T>> {

	/**
	 * The path to the file used.
	 */
	protected final String path, name;
	protected static Gson gson;
	protected Type gsonType;

	public JSONConfig(String path, String name) {
		this.path = path;
		this.name = name;
	}

	protected abstract L newList();

	public void init() {
		init(newList());
	}

	public static void setExclusion(GsonBuilder builder) {
		gson = builder.excludeFieldsWithoutExposeAnnotation().create();
	}

	/**
	 * Initializes everything.
	 */
	public void init(L items) {
		File file = new File(path);

		File parent = file.getParentFile();
		if (!parent.exists() && !parent.mkdirs()) {
			throw new IllegalStateException("Couldn't create dir: " + parent);
		}
		if (!file.exists() || file.isDirectory()) {
			create(items);
		} else {
			read();
		}
		;
	}

	/**
	 * Creates the file if it doesn't exist with the initial given items
	 */
	public void create(L initialItems) {
		try {
			Writer writer = new FileWriter(path);
			gson.toJson(initialItems, gsonType, writer);
			writer.close();
			ConstitutionMain.logger.info("Created new " + name + " file successfully!");
		} catch (IOException ex) {
			ConstitutionMain.logger.info(ExceptionUtils.getStackTrace(ex));
			ConstitutionMain.logger.info("Failed to create " + name + " file!");

		}
	}

	//test1


	//test
	/**
	 * Writes the given list to the file, completely overwriting it
	 */
	public void write(L items) {
		try {
			Writer writer = new FileWriter(path);
			gson.toJson(items, gsonType, writer);
			writer.close();
			ConstitutionMain.logger.info("Updated the " + name + " file successfully!");
		} catch (IOException ex) {
			ConstitutionMain.logger.info(ExceptionUtils.getStackTrace(ex));
			ConstitutionMain.logger.info("Failed to update " + name + " file!");
		}
	}

	/**
	 * Reads and returns the validated items.
	 */
	public L read() {
		L items = null;
		try {
			Reader reader = new FileReader(path);
			items = gson.fromJson(reader, gsonType);
			reader.close();     
			ConstitutionMain.logger.info("Loaded " + name + " successfully!");
		} catch (IOException ex) {
			ConstitutionMain.logger.info(ExceptionUtils.getStackTrace(ex));
			ConstitutionMain.logger.info("Failed to read from " + name + " file!");
		}
		if (!validate(items)) {
			write(items);
		}
		return items;
	}

	/**
	 * Checks for validity and modifies the given list so that is valid.
	 */
	public boolean validate(L items) {
		return true;
	}

	public String getName() {
		return this.name;
	}

	/**
	 * Clears the GSON "Cache" (Useful when altering different JSON files in a single instance)
	 */
	public void clearGsonCache() {
		JSONConfig.gson = null;
		gsonType = null;
	}
}
