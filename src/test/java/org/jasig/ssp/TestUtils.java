package org.jasig.ssp;

import static org.junit.Assert.assertNotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.exc.UnrecognizedPropertyException;
import org.jasig.ssp.model.Auditable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Various helper methods for unit and integration tests
 */
public final class TestUtils {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(TestUtils.class);

	private transient static ObjectMapper objectMapper = new ObjectMapper();

	private TestUtils() {
		/* empty, private constructor */
	}

	/**
	 * Asserts that all items in the list are not null.
	 * 
	 * @param objects
	 *            collection of {@link Auditable} objects; if null will not
	 *            assert anything.
	 */
	public static <T extends Auditable> void assertListDoesNotContainNullItems(
			@NotNull final Collection<T> objects) {
		if (objects != null) {
			for (final T object : objects) {
				assertNotNull("Object in the list should not have been null.",
						object.getId());
			}
		}
	}

	/**
	 * Load and parse a JSON file based on the testClass and file.
	 * 
	 * <p>
	 * JSON file must not contain any line breaks or new line characters.
	 * 
	 * <p>
	 * For example, if testClass is
	 * <code>org.jasig.mygps.business.StudentIntakeFormManagerTest</code> and
	 * file is <code>studentintakeform_empty.json</code>, this method will
	 * attempt to load the file
	 * <code>org/jasig/mygps/business/studentintakeform_empty.json</code>.
	 * 
	 * @param testClass
	 *            Testing class, used for resource loading based on the
	 *            concatenated namespace and file name, via {link
	 *            Class#getResourceAsStream(String)}. Required.
	 * @param file
	 *            the JSON file to load, relative to the namespace of the
	 *            calling testClass. Required.
	 * @param TOClass
	 *            Required; serializes the JSON to this transfer object class.
	 *            If the JSON can not be serialized to this class, an exception
	 *            will be thrown.
	 * @return Transfer object equivalent of the JSON file contents
	 * @exception JsonParseException
	 *                if test file could not be parsed
	 * @exception JsonMappingException
	 *                if test file could not be mapped
	 * @exception IOException
	 *                if test file could not be loaded, or missing/empty file
	 */
	public static <T, TO, TS> TO loadJson(@NotNull final Class<TS> testClass,
			@NotNull final String file, @NotNull final Class<TO> TOClass)
			throws JsonParseException, JsonMappingException, IOException {
		if (!StringUtils.isNotBlank(file)) {
			throw new IOException("Missing file parameter.");
		}

		assertNotNull("Test suite class can not be null.", testClass);
		assertNotNull("Transfer object class can not be null.", TOClass);

		// Load file
		try {
			final InputStreamReader is = new InputStreamReader(
					testClass.getResourceAsStream(file));

			final BufferedReader in = new BufferedReader(is);

			final String json = in.readLine();
			LOGGER.debug("Test class {} loaded the following JSON: {}",
					testClass.getName() + " with file " + file, json);

			final TO obj = objectMapper.readValue(json, TOClass);

			if (in != null) {
				in.close();
			}

			if (is != null) {
				is.close();
			}

			assertNotNull("File data could not be parsed from " + file, obj);

			return obj;
		} catch (final NullPointerException exc) { // NOPMD NPE is in library
			throw new IOException("Could not load the specified file "
					+ testClass.getPackage().getName().replace('.', '/') + "/"
					+ file, exc);
		} catch (final UnrecognizedPropertyException exc) {
			throw new JsonMappingException(
					"JSON fields did not match transfer object class properties. See exception cause for erroneous field names.",
					exc);
		}
	}
}