package eu.farsil.function;

import java.util.function.Supplier;

/**
 * Represents the outcome of a computation that returns a result.
 * <p/>
 * Its fluent interface allows users to chain method calls handling
 * successful or failed operations:
 * <pre>{@code final int result = Try.get(() -> Integer.parseInt(someString))
 *      .recover(e -> (int)Double.parseDouble(someString))
 *      .map(Math::abs)
 *      .orElse(0);}</pre>
 * It is also possible to evaluate the success of the computation using the
 * {@link #isSuccessful()} and the {@link #getCause()} methods.
 *
 * @param <T> the type of the result.
 * @author Marco Buzzanca
 */
public interface Result<T> {
	Result<T> filter(final ThrowingPredicate<? super T> predicate);

	<V> Result<V> flatMap(
			final ThrowingFunction<? super T, ? extends Result<? extends V>> mapper);

	Result<T> flatRecover(
			final ThrowingFunction<? super Exception, ? extends Result<? extends T>> mapper);

	Result<T> ifSuccessful(final ThrowingConsumer<? super T> action);

	default boolean isSuccessful() {
		return getCause() == null;
	}

	Exception getCause();

	<V> Result<V> map(final ThrowingFunction<? super T, ? extends V> mapper);

	T orElse(final T value);

	T orElseGet(final Supplier<? extends T> supplier);

	T orElseThrow();

	// orElseRecover was cut because "less is more"

	Result<T> recover(
			final ThrowingFunction<? super Exception, ? extends T> mapper);
}
