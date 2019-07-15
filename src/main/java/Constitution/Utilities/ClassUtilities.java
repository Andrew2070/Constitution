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
package constitution.utilities;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ClassUtilities {
	public static final List<Class<?>> getClassesInPackage(String packageName) {
		String path = packageName.replaceAll("\\.", File.separator);
		List<Class<?>> classes = new ArrayList<>();
		String[] classPathEntries = System.getProperty("java.class.path").split(
				System.getProperty("path.separator")
				);
		String name;
		for (String classpathEntry : classPathEntries) {
			try {
				File base = new File(classpathEntry + File.separatorChar + path);
				for (File file : base.listFiles()) {
					name = file.getName();
					if (name.endsWith(".class")) {
						name = name.substring(0, name.length() - 6);
						classes.add(Class.forName(packageName + "." + name));
					} else {
						base = new File(classpathEntry + File.separatorChar + path + File.separatorChar + name);
						if (name.endsWith(".class")) {
							for (File sub : base.listFiles()) {
								name = file.getName();
								if (name.endsWith(".class")) {
									name = name.substring(0, name.length() - 6);
									classes.add(Class.forName(packageName + "." + name));
								}
							}
						}
					}
				}
			} catch (Exception ex) {
			}
		}
		return classes;
	}
}
