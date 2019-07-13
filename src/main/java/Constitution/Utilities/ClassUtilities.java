package constitution.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

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
