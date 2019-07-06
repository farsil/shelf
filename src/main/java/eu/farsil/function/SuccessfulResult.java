package eu.farsil.function;

import java.util.Objects;
import java.util.function.Supplier;

class SuccessfulResult<T> implements Result<T> {
	private final T value;

	SuccessfulResult(final T value) {
		this.value = Objects.requireNonNull(value);
	}

	@Override
	public Result<T> filter(final ThrowingPredicate<? super T> predicate) {
		try {
			return predicate.test(value)
					? this
					: new FailedResult<>(new PredicateFailedException(value));
		} catch (final Exception e) {
			return new FailedResult<>(e);
		}
	}

	@Override
	public <V> Result<V> flatMap(
			final ThrowingFunction<? super T, ? extends Result<? extends V>> mapper) {
		try {
			@SuppressWarnings("unchecked")
			final Result<V> result = (Result<V>) mapper.apply(value);
			return result;
		} catch (final Exception e) {
			return new FailedResult<>(e);
		}
	}

	@Override
	public Result<T> flatRecover(
			final ThrowingFunction<? super Exception, ? extends Result<? extends T>> mapper) {
		Objects.requireNonNull(mapper);
		return this;
	}

	@Override
	public Exception getCause() {
		return null;
	}

	@Override
	public Result<T> ifSuccessful(final ThrowingConsumer<? super T> action) {
		try {
			action.accept(value);
		} catch (final Exception e) {
			return new FailedResult<>(e);
		}
		return this;
	}

	@Override
	public boolean isSuccessful() {
		return true;
	}

	@Override
	public <V> Result<V> map(
			final ThrowingFunction<? super T, ? extends V> mapper) {
		try {
			return new SuccessfulResult<>(mapper.apply(value));
		} catch (final Exception e) {
			return new FailedResult<>(e);
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
	public Result<T> recover(
			final ThrowingFunction<? super Exception, ? extends T> mapper) {
		Objects.requireNonNull(mapper);
		return this;
	}
}
