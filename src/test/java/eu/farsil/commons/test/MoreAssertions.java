package eu.farsil.commons.test;

import eu.farsil.commons.function.ThrowingRunnable;
import eu.farsil.commons.function.ThrowingSupplier;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * More assertions to be used alongside
 * {@code org.junit.jupiter.api.Assertions}.
 *
 * @author Marco Buzzanca
 */
public class MoreAssertions {
	/**
	 * Utility class.
	 */
	private MoreAssertions() {
		throw new AssertionError();
	}

	/**
	 * Asserts that supplied {@code ThrowingRunnable} does execute without
	 * throwing exceptions. Useful in circumstances when
	 * {@code assertDoesNotThrow()} is ambiguous.
	 *
	 * @param runnable the supplier.
	 */
	public static void assertDoesRun(final ThrowingRunnable runnable) {
		assertDoesNotThrow(runnable::run);
	}

	/**
	 * Asserts that supplied {@code ThrowingSupplier} does return a result
	 * without throwing exceptions. Useful in circumstances when
	 * {@code assertDoesNotThrow()} is ambiguous.
	 *
	 * @param supplier the supplier.
	 * @param <T> the supplier result type.
	 * @return the result obtained from the supplier.
	 */
	public static <T> T assertDoesSupply(final ThrowingSupplier<T> supplier) {
		return assertDoesNotThrow(supplier::get);
	}

	/**
	 * Asserts that the supplied object is an instance of the expected class.
	 *
	 * @param expectedClass the expected class.
	 * @param object the object.
	 * @param <T> the expected object type.
	 * @return the supplied object cast to the expected class.
	 */
	public static <T> T assertInstanceOf(final Class<T> expectedClass,
			final Object object) {
		if (!expectedClass.isInstance(object)) {
			fail(String.format("Unexpected instance type ==> expected: <%s> " +
					"but was: <%s>", expectedClass, object.getClass()));
		}
		return expectedClass.cast(object);
	}
}
