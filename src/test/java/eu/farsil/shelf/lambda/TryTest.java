package eu.farsil.shelf.lambda;

import eu.farsil.shelf.lambda.function.ThrowingSupplier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static eu.farsil.shelf.mock.Functions.throwingSupplier;
import static eu.farsil.shelf.test.MoreAssertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * Attempt creation tests.
 *
 * @author Marco Buzzanca
 */
@DisplayName("Attempt creation test")
class TryTest {
	@Test
	@DisplayName("get() test")
	void getTest() throws Exception {
		// null supplier
		assertThrows(NullPointerException.class, () -> Try.get(null));

		// successful attempt
		final ThrowingSupplier<Double> mock = throwingSupplier();
		when(mock.get()).thenReturn(1.0);
		assertEquals(1.0, assertSuccessful(Try.get(mock)));
		verify(mock).get();

		// failed attempt
		doThrow(IOException.class).when(mock).get();
		assertInstanceOf(IOException.class, assertNotSuccessful(Try.get(mock)));
		verify(mock, times(2)).get();
	}
}
