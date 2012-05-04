package org.jasig.ssp.util

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory

/**
 * @see http://snippets.dzone.com/posts/show/4831
 *
 */
class ClassDiscovery {

	private static final Logger LOGGER = LoggerFactory
	.getLogger(ClassDiscovery.class);

	/**
	 * Scans all classes accessible from the context class loader which belong to the given package and subpackages.
	 *
	 * @param packageName The base package
	 * @return The classes
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public static List<Class> getClasses(String packageName) {
		ArrayList<Class> classes = []

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader()

		if(!classLoader){
			LOGGER.info("Found no classes in the package")
			return classes
		}

		String path = packageName.replace('.', '/')
		List<File> dirs = classLoader.getResources(path).collect{ URL url ->
			new File(url.getFile())
		}

		dirs.each { File directory ->
			classes.addAll(findClassesInDirectory(directory, packageName));
		}

		return classes;
	}

	private static List<Class> findClassesInDirectory(File directory, String packageName) throws ClassNotFoundException {
		List<Class> classes = []

		if (!directory || !directory.exists()) {
			return classes
		}

		for (File file : directory.listFiles()) {
			if (file.isDirectory()) {
				classes.addAll(findClassesInDirectory(file, packageName + "." + file.getName()));
			} else if (file.getName().endsWith(".class")) {
				classes.addAll(Class.forName(packageName + '.' +
						file.getName().substring(0, file.getName().length() - 6)));
			}
		}
		return classes;
	}
}
