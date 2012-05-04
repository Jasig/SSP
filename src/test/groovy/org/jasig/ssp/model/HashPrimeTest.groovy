package org.jasig.ssp.model;

import static org.junit.Assert.*

import java.lang.reflect.Modifier

import org.junit.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory


/**
 * @see http://snippets.dzone.com/posts/show/4831
 *
 */
public class HashPrimeTest {

	private static final Logger LOGGER = LoggerFactory
	.getLogger(HashPrimeTest.class);

	@Test
	void testForUniqueHashPrimes(){
		List<Class> offenders = []
		Map<Integer, List<Class>> primeGroups = [:]

		List<Class> classes = getClasses("org.jasig.ssp.model")

		primeGroups = classes.groupBy { Class clazz ->
			if(Auditable.class.isAssignableFrom(clazz) && !Modifier.isAbstract(clazz.getModifiers())){
				LOGGER.debug(clazz.toString() + " - Auditable")
				Auditable auditable = clazz.newInstance()
				return auditable.hashPrime()
			}else{
				return 0
				LOGGER.debug(clazz.toString())
			}
		}

		boolean success = true;
		primeGroups.each{ key, value ->
			if(value.size()>1 && key!=0){
				success = false
				LOGGER.error("More than one class generating $key as its hashprime, see: " + value.toString())
			}
		}

		assertTrue("More than one class using the same hashPrime", success);
	}

	/**
	 * Scans all classes accessible from the context class loader which belong to the given package and subpackages.
	 *
	 * @param packageName The base package
	 * @return The classes
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	private static List<Class> getClasses(String packageName)
	throws ClassNotFoundException, IOException {
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