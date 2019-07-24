package eu.farsil.shelf.util;

import eu.farsil.shelf.function.ThrowingConsumer;
import eu.farsil.shelf.function.ThrowingFunction;
import eu.farsil.shelf.function.ThrowingPredicate;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Implementation of {@code Try} that represents an unsuccessful attempt.
 *
 * @param <T> the type of the result.
 * @author Marco Buzzanca
 */
class Failure<T> implements Try<T> {
	/**
	 * The cause of the failure.
	 */
	private final Exception cause;

	/**
	 * Builds a {@code Success} holding the cause of the failure.
	 *
	 * @param cause the cause of the failure.
	 */
	Failure(final Exception cause) {
		this.cause = Objects.requireNonNull(cause);
	}

	@Override
	public Try<T> filter(final ThrowingPredicate<? super T> predicate) {
		Objects.requireNonNull(predicate);
		return this;
	}

	@Override
	public <V> Try<V> flatMap(
			final ThrowingFunction<? super T, ? extends Try<? extends V>> mapper) {
		Objects.requireNonNull(mapper);
		@SuppressWarnings("unchecked")
		final Try<V> result = (Try<V>) this;
		return result;
	}

	@Override
	public Try<T> flatRecover(
			final ThrowingFunction<? super Exception, ? extends Try<? extends T>> mapper) {
		Objects.requireNonNull(mapper);
		try {
			@SuppressWarnings("unchecked")
			final Try<T> result = (Try<T>) mapper.apply(cause);
			return result;
		} catch (final Exception e) {
			return new Failure<>(e);
		}
	}

	@Override
	public Exception getCause() {
		return cause;
	}

	@Override
	public Try<T> ifSuccessful(final ThrowingConsumer<? super T> action) {
		Objects.requireNonNull(action);
		return this;
	}

	@Override
	public boolean isSuccessful() {
		return false;
	}

	@Override
	public <V> Try<V> map(
			final ThrowingFunction<? super T, ? extends V> mapper) {
		Objects.requireNonNull(mapper);
		@SuppressWarnings("unchecked")
		final Try<V> result = (Try<V>) this;
		return result;
	}

	@Override
	public T orElse(final T value) {
		return value;
	}

	@Override
	public T orElseGet(final Supplier<? extends T> supplier) {
		return supplier.get();
	}

	@Override
	public T orElseThrow() {
		throw new AttemptFailedException(cause);
	}

	@Override
	public <E extends Exception> T orElseThrow(
			final Function<? super Exception, E> function) throws E {
		throw function.apply(cause);
	}

	@Override
	public Try<T> recover(
			final ThrowingFunction<? super Exception, ? extends T> mapper) {
		Objects.requireNonNull(mapper);
		try {
			final T recovered = mapper.apply(cause);
			return new Success<>(recovered);
		} catch (final Exception e) {
			return new Failure<>(e);
		}
	}
}
