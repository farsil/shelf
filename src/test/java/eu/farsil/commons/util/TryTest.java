package eu.farsil.commons.util;

import eu.farsil.commons.function.ThrowingSupplier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static eu.farsil.commons.mock.Functions.throwingSupplier;
import static eu.farsil.commons.test.MoreAssertions.assertDoesSupply;
import static eu.farsil.commons.test.MoreAssertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@DisplayName("Try creation test")
class TryTest {
	@Test
	@DisplayName("get() test")
	void getTest() throws Exception {
		assertThrows(NullPointerException.class, () -> Try.get(null));

		final ThrowingSupplier<Double> mock = throwingSupplier();
		when(mock.get()).thenReturn(1.0);
		assertEquals(1.0, assertDoesSupply(Try.get(mock)::orElseThrow));
		verify(mock).get();

		doThrow(IOException.class).when(mock).get();
		assertInstanceOf(IOException.class, Try.get(mock).getCause());
		verify(mock, times(2)).get();
	}
}
