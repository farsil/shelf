package eu.farsil.commons.util;

import eu.farsil.commons.DummyException;
import org.junit.jupiter.api.Test;

import static eu.farsil.commons.Assertions.assertInstanceOf;
import static eu.farsil.commons.Assertions.assertSupplierDoesNotThrow;
import static eu.farsil.commons.Functions.throwingSupplier;
import static org.junit.jupiter.api.Assertions.*;

class TryTest {
	@Test
	void getTest() {
		assertThrows(NullPointerException.class, () -> Try.get(null));

		assertEquals(1.0,
				assertSupplierDoesNotThrow(Try.get(() -> 1.0)::orElseThrow));

		assertInstanceOf(DummyException.class,
				Try.get(throwingSupplier(DummyException::new)).getCause());
	}
}
