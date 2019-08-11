package eu.farsil.shelf.lambda;

import eu.farsil.shelf.lambda.function.PredicateFailedException;
import eu.farsil.shelf.lambda.function.ThrowingFunction;
import eu.farsil.shelf.lambda.function.ThrowingPredicate;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Implementation of {@link Try} that represents a successful attempt.
 *
 * @param <T> the type of the result.
 * @author Marco Buzzanca
 */
class Success<T> implements Try<T> {
	/**
	 * The result value.
	 */
	private final T value;

	/**
	 * Builds a {@link Success} holding the specified value.
	 *
	 * @param value the result value.
	 */
	Success(final T value) {
		this.value = Objects.requireNonNull(value);
	}

	@Override
	public Try<T> filter(final ThrowingPredicate<? super T> predicate) {
		Objects.requireNonNull(predicate);
		try {
			return predicate.test(value)
					? this
					: new Failure<>(new PredicateFailedException(value));
		} catch (final Exception e) {
			return new Failure<>(e);
		}
	}

	@Override
	public <V> Try<V> flatMap(
			final ThrowingFunction<? super T, ? extends Try<? extends V>> mapper) {
		Objects.requireNonNull(mapper);
		try {
			@SuppressWarnings("unchecked")
			final Try<V> result = (Try<V>) mapper.apply(value);
			return result;
		} catch (final Exception e) {
			return new Failure<>(e);
		}
	}

	@Override
	public Try<T> flatRecover(
			final ThrowingFunction<? super Exception, ? extends Try<? extends T>> mapper) {
		Objects.requireNonNull(mapper);
		return this;
	}

	@Override
	public Exception getCause() {
		return null;
	}

	@Override
	public Try<T> ifSuccessful(final Consumer<? super T> action) {
		action.accept(value);
		return this;
	}

	@Override
	public Try<T> ifUnsuccessful(final Consumer<? super Exception> action) {
		Objects.requireNonNull(action);
		return this;
	}

	@Override
	public boolean isSuccessful() {
		return true;
	}

	@Override
	public boolean isUnsuccessful() {
		return false;
	}

	@Override
	public <V> Try<V> map(
			final ThrowingFunction<? super T, ? extends V> mapper) {
		Objects.requireNonNull(mapper);
		try {
			return new Success<>(mapper.apply(value));
		} catch (final Exception e) {
			return new Failure<>(e);
		}
	}

	@Override
	public T orElse(final T value) {
		return this.value;
	}

	@Override
	public T orElseGet(final Supplier<? extends T> supplier) {
		Objects.requireNonNull(supplier);
		return value;
	}

	@Override
	public T orElseThrow() {
		return value;
	}

	@Override
	public <E extends Exception> T orElseThrow(
			final Function<? super Exception, E> function) {
		Objects.requireNonNull(function);
		return value;
	}

	@Override
	public Try<T> recover(
			final ThrowingFunction<? super Exception, ? extends T> mapper) {
		Objects.requireNonNull(mapper);
		return this;
	}
}
