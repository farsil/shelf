package eu.farsil.function;

import java.util.Objects;
import java.util.function.Supplier;

class FailedResult<T> implements Result<T> {
	private final Exception cause;

	FailedResult(final Exception cause) {
		this.cause = Objects.requireNonNull(cause);
	}

	@Override
	public Result<T> filter(final ThrowingPredicate<? super T> predicate) {
		Objects.requireNonNull(predicate);
		return this;
	}

	@Override
	public <V> Result<V> flatMap(
			final ThrowingFunction<? super T, ? extends Result<? extends V>> mapper) {
		Objects.requireNonNull(mapper);
		@SuppressWarnings("unchecked")
		final Result<V> result = (Result<V>) this;
		return result;
	}

	@Override
	public Result<T> flatRecover(
			final ThrowingFunction<? super Exception, ? extends Result<? extends T>> mapper) {
		try {
			@SuppressWarnings("unchecked")
			final Result<T> result = (Result<T>) mapper.apply(cause);
			return result;
		} catch (final Exception e) {
			return new FailedResult<>(e);
		}
	}

	@Override
	public Exception getCause() {
		return cause;
	}

	@Override
	public Result<T> ifSuccessful(final ThrowingConsumer<? super T> action) {
		Objects.requireNonNull(action);
		return this;
	}

	@Override
	public boolean isSuccessful() {
		return false;
	}

	@Override
	public <V> Result<V> map(
			final ThrowingFunction<? super T, ? extends V> mapper) {
		Objects.requireNonNull(mapper);
		@SuppressWarnings("unchecked")
		final Result<V> result = (Result<V>) this;
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
	public Result<T> recover(
			final ThrowingFunction<? super Exception, ? extends T> mapper) {
		try {
			final T recovered = mapper.apply(cause);
			return new SuccessfulResult<>(recovered);
		} catch (final Exception e) {
			return new FailedResult<>(e);
		}
	}
}
