package eu.farsil.commons;

import eu.farsil.commons.function.ThrowingSupplier;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Assertions {
	private Assertions() {
		throw new AssertionError();
	}

	public static <T> T assertInstanceOf(final Class<T> expectedClass,
			final Object object) {
		assertTrue(expectedClass.isInstance(object));
		return expectedClass.cast(object);
	}

	public static <T> T assertSupplierDoesNotThrow(
			final ThrowingSupplier<T> supplier) {
		return org.junit.jupiter.api.Assertions.assertDoesNotThrow(
				supplier::get);
	}
}
