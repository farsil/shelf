package eu.farsil.commons.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * {@code PredicateFailedException} tests.
 *
 * @author Marco Buzzanca
 */
@DisplayName("PredicateFailedException tests")
class PredicateFailedExceptionTest {
	/**
	 * Tests arguments for {@code serializationTest()}.
	 *
	 * @return the arguments.
	 */
	private static Stream<Arguments> serializationTestArgs() {
		return Stream.of(Arguments.of("Serializable", 1, 1),
				Arguments.of("Non-serializable", new Object(), null),
				Arguments.of("null", null, null));
	}

	/**
	 * Serialization test.
	 *
	 * @param name name of the test.
	 * @param value the value to serialize together with the exception.
	 * @param expected the value that is expected after deserialization.
	 */
	@DisplayName("Serialization test")
	@ParameterizedTest(name = "{0}")
	@MethodSource("serializationTestArgs")
	void serializationTest(final String name, final Object value,
			final Object expected) {
		// serialize a new PredicateFailedException with the provided value
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		try (final ObjectOutputStream oos = new ObjectOutputStream(out)) {
			oos.writeObject(new PredicateFailedException(value));
		} catch (final IOException e) {
			fail("Serialization was unsuccessful", e);
		}

		// deserialize the exception from the bytes
		final ByteArrayInputStream in =
				new ByteArrayInputStream(out.toByteArray());
		final PredicateFailedException ex;
		try (final ObjectInputStream ois = new ObjectInputStream(in)) {
			ex = (PredicateFailedException) ois.readObject();
		} catch (final IOException | ClassNotFoundException e) {
			fail("Deserialization was unsuccessful", e);
			// makes sure the test ends even if -ea flag is missing
			return;
		}

		// the deserialized value must be equal to the expected value
		assertEquals(expected, ex.getValue());
	}
}