package eu.farsil.shelf.mock;

import eu.farsil.shelf.function.ThrowingConsumer;
import eu.farsil.shelf.function.ThrowingFunction;
import eu.farsil.shelf.function.ThrowingPredicate;
import eu.farsil.shelf.function.ThrowingSupplier;

import java.util.function.Function;
import java.util.function.Supplier;

import static org.mockito.Mockito.mock;

/**
 * Returns mock instances of the throwing functional interfaces of package
 * {@code eu.farsil.shelf.function} and {@code java.util.function} that can
 * be verified with {@code org.mockito.Mockito.verify()}.
 * <p/>
 * This class is used to reduce boilerplate code that involves annotating
 * with {@code @SuppressWarnings} and casting to the appropriate functional
 * interface the instances obtained with {@code org.mockito.Mockito.mock()}.
 *
 * @author Marco Buzzanca
 */
public class Functions {
	/**
	 * Utility class.
	 */
	private Functions() {
		throw new AssertionError();
	}

	/**
	 * Returns a mock {@code java.util.Function}.
	 *
	 * @param <T> the type of the input to the function.
	 * @param <R> the type of the result of the function.
	 * @return the mock function.
	 */
	@SuppressWarnings("unchecked")
	public static <T, R> Function<T, R> function() {
		return (Function<T, R>) mock(Function.class);
	}

	/**
	 * Returns a mock {@code java.util.Supplier}.
	 *
	 * @param <T> the type of results supplied by this supplier.
	 * @return the mock supplier.
	 */
	@SuppressWarnings("unchecked")
	public static <T> Supplier<T> supplier() {
		return (Supplier<T>) mock(Supplier.class);
	}

	/**
	 * Returns a mock {@code eu.farsil.shelf.function.ThrowingConsumer}.
	 *
	 * @param <T> the type of the input to the operation.
	 * @return the mock consumer.
	 */
	@SuppressWarnings("unchecked")
	public static <T> ThrowingConsumer<T> throwingConsumer() {
		return (ThrowingConsumer<T>) mock(ThrowingConsumer.class);
	}

	/**
	 * Returns a mock {@code eu.farsil.shelf.function.ThrowingFunction}.
	 *
	 * @param <T> the type of the input to the function.
	 * @param <R> the type of the result of the function.
	 * @return the mock function.
	 */
	@SuppressWarnings("unchecked")
	public static <T, R> ThrowingFunction<T, R> throwingFunction() {
		return (ThrowingFunction<T, R>) mock(ThrowingFunction.class);
	}

	/**
	 * Returns a mock {@code eu.farsil.shelf.function.ThrowingConsumer}.
	 *
	 * @param <T> the type of the input to the predicate.
	 * @return the mock predicate.
	 */
	@SuppressWarnings("unchecked")
	public static <T> ThrowingPredicate<T> throwingPredicate() {
		return (ThrowingPredicate<T>) mock(ThrowingPredicate.class);
	}

	/**
	 * Returns a mock {@code eu.farsil.shelf.function.ThrowingSupplier}.
	 *
	 * @param <T> the type of results supplied by this supplier
	 * @return the mock supplier.
	 */
	@SuppressWarnings("unchecked")
	public static <T> ThrowingSupplier<T> throwingSupplier() {
		return (ThrowingSupplier<T>) mock(ThrowingSupplier.class);
	}
}
