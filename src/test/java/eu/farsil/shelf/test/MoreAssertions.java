package eu.farsil.shelf.test;

import eu.farsil.shelf.function.ThrowingRunnable;
import eu.farsil.shelf.function.ThrowingSupplier;
import eu.farsil.shelf.util.Try;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * More assertions to be used alongside {@link Assertions}.
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
	 * Asserts that supplied {@link ThrowingRunnable} does execute without
	 * throwing exceptions. Useful in circumstances when
	 * {@link Assertions#assertDoesNotThrow(Executable) assertDoesNotThrow()}
	 * is ambiguous.
	 *
	 * @param runnable the runnable.
	 */
	public static void assertDoesRun(final ThrowingRunnable runnable) {
		assertDoesNotThrow(runnable::run);
	}

	/**
	 * Asserts that supplied {@link ThrowingSupplier} does return a result
	 * without throwing exceptions. Useful in circumstances when
	 * {@link Assertions#assertDoesNotThrow(org.junit.jupiter.api.function.ThrowingSupplier)
	 * assertDoesNotThrow()} is ambiguous.
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
			fail("Unexpected instance type ==> expected: <%s> " +
					"but was: <%s>", expectedClass, object.getClass());
		}
		return expectedClass.cast(object);
	}

	/**
	 * Asserts that the supplied attempt is unsuccessful.
	 *
	 * @param attempt the attempt.
	 * @return the cause of the failure.
	 */
	public static Exception assertNotSuccessful(final Try<?> attempt) {
		if (attempt.isSuccessful()) {
			fail("Attempt is successful => expected a failure, but was a " +
					"success with computed value <%s>", attempt.orElseThrow());
		}
		return attempt.getCause();
	}

	/**
	 * Asserts that the supplied attempt is successful.
	 *
	 * @param attempt the attempt.
	 * @param <T> the computed result type.
	 * @return the computed result of the attempt.
	 */
	public static <T> T assertSuccessful(final Try<T> attempt) {
		if (!attempt.isSuccessful()) {
			fail("Attempt is not successful => expected a success, but " +
							"was a failure with cause <%s>",
					attempt.getCause().getClass());
		}
		return attempt.orElseThrow();
	}

	/**
	 * Fails with a formatted message.
	 *
	 * @param format message format.
	 * @param args format arguments.
	 */
	private static void fail(final String format, Object... args) {
		Assertions.fail(String.format(format, args));
	}
}
