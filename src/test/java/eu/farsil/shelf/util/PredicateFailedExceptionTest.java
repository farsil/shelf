package eu.farsil.shelf.util;

import eu.farsil.shelf.test.Serialization;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * Tests for the {@link PredicateFailedException} class.
 *
 * @author Marco Buzzanca
 */
@DisplayName("Attempt failed test")
class PredicateFailedExceptionTest {
	@Test
	@DisplayName("getValue() test")
	void getValueTest() throws IOException, ClassNotFoundException {
		// null value
		assertNull(new PredicateFailedException(null).getValue());

		// non-null value
		final Object value = new Object();
		final PredicateFailedException ex = new PredicateFailedException(value);
		assertSame(value, ex.getValue());

		// value does not survive serialization
		final PredicateFailedException deserialized =
				Serialization.fromByteArray(PredicateFailedException.class,
						Serialization.toByteArray(ex));
		assertNull(deserialized.getValue());
	}
}