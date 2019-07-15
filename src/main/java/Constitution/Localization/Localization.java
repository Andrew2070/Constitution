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
package constitution.localization;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;

import constitution.ConstitutionMain;
import constitution.chat.component.ChatComponentFormatted;
import net.minecraft.util.text.ITextComponent;
/**
 * Loads and handles Localization files
 */
public class Localization {
	public static final String defaultLocalization = "en_US";

	private Map<String, String> localizations = new HashMap<String, String>();
	private String filePath;
	private String lang;
	private String classPath;
	private Class<?> clazz;

	public Localization(String filePath, String lang, String classPath, Class<?> clazz) {
		this.filePath = filePath;
		this.lang = lang;
		this.classPath = classPath;
		this.clazz = clazz;	
		load();
	}

	private Reader getReader() throws FileNotFoundException {
		InputStream is = null;
		ConstitutionMain.logger.info(filePath.toString());
		if (filePath != null) {
			ConstitutionMain.logger.info("FilePath not null");
			File file = new File(filePath + lang + ".lang");
			if (file.exists() && !file.isDirectory()) {
				ConstitutionMain.logger.info("Localization File Exists");
				is = new FileInputStream(file);
			}
		}
		try {
		if (is == null) {

			
			is = clazz.getResourceAsStream(classPath + lang + ".lang");
			ConstitutionMain.logger.info("Reverting to en_US.lang because " + lang + ".lang does not exist");
			
		}
		} catch (NullPointerException e) {
			is = clazz.getResourceAsStream(classPath + defaultLocalization + ".lang");
			
		}
		return new InputStreamReader(is);
	}
	/**
	 * Do the actual loading of the Localization file
	 */
	public void load() {
		localizations.clear();

		try {
			BufferedReader br = new BufferedReader(getReader());
			String line;

			while ((line = br.readLine()) != null) {
				line = line.trim(); // Trim it in-case there is spaces before
									// the actual key-value pairs
				String[] entry = line.split("=");
				if (line.startsWith("#") || line.isEmpty() || entry.length < 2) {
					// Ignore entries that are not formatted correctly (maybe
					// log later)
					// Ignore comments and empty lines
					continue;
				}

				localizations.put(entry[0].trim(), entry[1].trim());
			}
			br.close();
		} catch (IOException ex) {
			ConstitutionMain.logger.info("Failed to load localization file!");
			ConstitutionMain.logger.info(ExceptionUtils.getStackTrace(ex));
		}
	}

	public ITextComponent getLocalization(String key, Object... args) {
		String localized = localizations.get(key);
		return localized == null ? new ChatComponentFormatted("{|" + key + "}")
				: new ChatComponentFormatted(localized, args);

	}

	public boolean hasLocalization(String key) {
		return localizations.containsKey(key);
	}

	public Map<String, String> getLocalizationMap() {
		return localizations;
	}
}
