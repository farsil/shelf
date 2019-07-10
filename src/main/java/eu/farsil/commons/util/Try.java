package eu.farsil.commons.util;

import eu.farsil.commons.function.ThrowingConsumer;
import eu.farsil.commons.function.ThrowingFunction;
import eu.farsil.commons.function.ThrowingPredicate;
import eu.farsil.commons.function.ThrowingSupplier;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * This class provides an abstraction of a computation attempt.
 * <p>
 * The outcome of the computation determines whether instances of this class
 * are in a <i>successful</i> or <i>failed</i> state. Instances of {@code Try} in a
 * successful state hold the computed value, while instances in a failed
 * state hold the cause of the failure.
 * <p/>
 * In its essence, {@code Try} is a generalization of the
 * {@code java.util.Optional} class which is able to handle any arbitrary
 * failure, while the {@code java.util.Optional} class is limited to handle
 * failures due to {@code null} values.
 * <p/>
 * The computation is performed by the static method {@code get()}, and this
 * class's fluent interface allows users to chain method calls handling
 * successful or failed operations:
 * <pre>{@code final int result = Try.get(() -> Integer.parseInt(someString))
 *      .recover(e -> (int)Double.parseDouble(someString))
 *      .map(Math::abs)
 *      .orElse(0);}</pre>
 * It is also possible to evaluate the success of the computation using the
 * {@link #isSuccessful()} and the {@link #getCause()} methods.
 * <p/>
 * This class is not meant to replace the {@code try/catch} mechanism
 * entirely: its purpose is to help programmers deal with chains of method
 * calls that may throw an exception when invoked.
 *
 * @param <T> the type of the result.
 * @author Marco Buzzanca
 * @see java.util.Optional
 */
public interface Try<T> {
	/**
	 * Attempts to perform a computation that returns a value, which is
	 * represented by the specified supplier. If it throws an
	 * exception, the result returned will be in a <i>failed</i> state,
	 * otherwise it will be in a <i>successful</i> state.
	 *
	 * @param supplier the computation that returns a value.
	 * @param <T> the type of the result.
	 * @return an instance of {@code Try}, which represents the outcome of
	 * a computation that returns a value, in a successful or failed
	 * state. The {@code Try} instance may further processed with its
	 * @throws NullPointerException if the supplier is {@code null}.
	 * fluent interface.
	 */
	static <T> Try<T> get(final ThrowingSupplier<? extends T> supplier) {
		Objects.requireNonNull(supplier);
		try {
			return new Success<>(supplier.get());
		} catch (final Exception e) {
			return new Failure<>(e);
		}
	}

	/**
	 * Converts this instance to a failure if the computed value does not match
	 * the predicate, or if the predicate throws an exception, otherwise
	 * returns a success holding the matching value.
	 *
	 * @param predicate the predicate to match the result against.
	 * @return a failure if the result does not match the predicate or the
	 * predicate throws, a success otherwise.
	 * @throws NullPointerException if the predicate is {@code null}.
	 * @see ThrowingPredicate
	 */
	Try<T> filter(final ThrowingPredicate<? super T> predicate);

	/**
	 * Applies the given mapping function to the computed value if the
	 * computation was successful.
	 * <p/>
	 * If the computation was successful, and the given function throws an
	 * exception, or if the computation was unsuccessful, it returns a failure.
	 *
	 * @param mapper the function to apply.
	 * @param <V> the type of the returned {@code Try}.
	 * @return an instance of {@code Try} obtained by applying the given
	 * function if the computation was successful, a failure otherwise.
	 * @throws NullPointerException if the function is {@code null}.
	 * @see ThrowingFunction
	 */
	<V> Try<V> flatMap(
			final ThrowingFunction<? super T, ? extends Try<? extends V>> mapper);

	/**
	 * Applies the given mapping function to the cause of the failure if the
	 * computation was unsuccessful.
	 * <p/>
	 * If the computation was successful, and the given function throws an
	 * exception, or if the computation was unsuccessful, it returns a failure.
	 *
	 * @param mapper the function to apply.
	 * @return an instance of {@code Try} obtained by applying the given
	 * function if the computation was unsuccessful, a success otherwise.
	 * @throws NullPointerException if the function is {@code null}.
	 * @see ThrowingFunction
	 */
	Try<T> flatRecover(
			final ThrowingFunction<? super Exception, ? extends Try<? extends T>> mapper);

	/**
	 * Performs the given action only if the computation was successful.
	 * <p/>
	 * If the action throws an exception it returns a failure, otherwise it
	 * returns this instance regardless if it is a success or a failure.
	 *
	 * @param action the action to perform. The computed value is passed as a
	 * parameter.
	 * @return this instance, or a failure if the action throws.
	 * @throws NullPointerException if the action is {@code null}.
	 * @see ThrowingConsumer
	 */
	Try<T> ifSuccessful(final ThrowingConsumer<? super T> action);

	/**
	 * Returns whether the computation was successful.
	 *
	 * @return {@code true} if the computation was successful, {@code false}
	 * otherwise.
	 */
	boolean isSuccessful();

	/**
	 * Returns the cause of the failure or {@code null} if the
	 * computation was successful.
	 *
	 * @return the cause of the failure, or {@code null} if the computation
	 * was successful.
	 * @see #isSuccessful()
	 */
	Exception getCause();

	/**
	 * Applies the given mapping function to the computed value if the
	 * computation was successful and wraps the result in a success.
	 * <p/>
	 * If the computation was successful, and the given function throws an
	 * exception, or if the computation was unsuccessful, it returns a failure.
	 *
	 * @param mapper the function to apply.
	 * @param <V> the type of the returned {@code Try}.
	 * @return a success obtained by applying the given function if
	 * the computation was successful, a failure otherwise.
	 * @throws NullPointerException if the function is {@code null}.
	 * @see ThrowingFunction
	 */
	<V> Try<V> map(final ThrowingFunction<? super T, ? extends V> mapper);

	/**
	 * Returns the computed value if the computation was successful,
	 * otherwise returns the specified value.
	 *
	 * @return the computed value if the computation was successful,
	 * the specified value otherwise.
	 */
	T orElse(final T value);

	/**
	 * Returns the computed value if the computation was successful,
	 * otherwise obtains a value from the given supplier.
	 *
	 * @return the computed value if the computation was successful,
	 * a value obtained from the given supplier otherwise.
	 * @throws NullPointerException if the supplier is {@code null}.
	 * @see Supplier
	 */
	T orElseGet(final Supplier<? extends T> supplier);

	/**
	 * Returns the computed value if the computation was successful,
	 * otherwise throws an {@code AttemptFailedException} holding the cause
	 * of the failure.
	 *
	 * @return the computed value.
	 * @throws AttemptFailedException if the computation was unsuccessful.
	 */
	T orElseThrow();

	/**
	 * Returns the computed value if the computation was successful,
	 * otherwise throws an exception obtained by applying the specified
	 * function to the cause of the failure.
	 * <p/>
	 * The given function is usually an exception constructor that takes a
	 * single exception argument, used for exception chaining:
	 * <pre>{@code final int result = Try.get(() -> Integer.parseInt(someString))
	 *      .map(Math::abs)
	 *      .orElseThrow(IllegalArgumentException::new);}</pre>
	 *
	 * @param <E> the exception type.
	 * @param function the function to apply to the cause of the failure.
	 * @return the computed value.
	 * @throws NullPointerException if the function is {@code null}.
	 * @throws E if the computation was unsuccessful.
	 */
	<E extends Exception> T orElseThrow(
			final Function<? super Exception, E> function) throws E;

	/**
	 * Applies the given mapping function to the cause of the failure if the
	 * computation was unsuccessful and wraps the result in a success.
	 * <p/>
	 * If the computation was successful, it returns a success holding the
	 * original computed value. If computation was unsuccessful, and the given
	 * function throws an exception, it returns a failure.
	 *
	 * @param mapper the function to apply.
	 * @return a success obtained by applying the given function if
	 * the computation was unsuccessful, a success if the computation was
	 * successful, or a failure if the given function throws an exception.
	 * @throws NullPointerException if the function is {@code null}.
	 * @see ThrowingFunction
	 */
	Try<T> recover(
			final ThrowingFunction<? super Exception, ? extends T> mapper);
}
