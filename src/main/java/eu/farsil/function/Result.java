package eu.farsil.function;

import java.util.function.Supplier;

/**
 * Represents the outcome of a computation that returns a result.
 * <p/>
 * Its fluent interface allows users to chain method calls handling
 * successful results, and it is the preferred way of using this
 * class:
 * <pre>{@code final int result = Try.get(() -> Integer.parseInt(someString))
 *      .ifSuccessful(i -> System.out.println("Converted value: " + i))
 *      .map(Math::abs)
 *      .orElse(0);}</pre>
 * For completeness's sake, it is also possible, but discouraged, to evaluate
 * the
 * success of the computation using
 * the {@link #isSuccessful()} and the
 * {@link #getCause()} method and manually extract the value:
 * <pre>{@code int result;
 * final Result<Integer> outcome = Try.get(() -> Integer.parseInt(someString));
 * if (outcome.isSuccessful()) {
 *      final int converted = outcome.orElseThrow();
 *      System.out.println("Converted value: " + converted);
 *      result = Math.abs(converted);
 * } else {
 *      // you may access the cause of the exception with getCause() if needed
 *      result = 0;
 * }}</pre>
 * Given the verbosity of the snippet above, it is recommended to use a {@code
 * try/catch} block.
 *
 * @param <T> the type of the result.
 * @author Marco Buzzanca
 */
public interface Result<T> {
	static <V> Result<V> failure(final Exception cause) {
		return new FailedResult<>(cause);
	}

	static <V> Result<V> success(final V result) {
		return new SuccessfulResult<>(result);
	}

	Result<T> filter(final ThrowingPredicate<? super T> predicate);

	<V> Result<V> flatMap(final ThrowingFunction<? super T, Result<V>> mapper);

	Result<T> flatRecover(
			final ThrowingFunction<? super Exception, Result<T>> mapper);

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
