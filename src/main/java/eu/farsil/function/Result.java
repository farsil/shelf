package eu.farsil.function;

import java.util.function.Supplier;

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

	Exception getCause();

	Result<T> ifSuccessful(final ThrowingConsumer<? super T> action);

	boolean isSuccessful();

	<V> Result<V> map(final ThrowingFunction<? super T, ? extends V> mapper);

	T orElse(final T value);

	T orElseGet(final Supplier<? extends T> supplier);

	T orElseThrow();

	// orElseRecover was cut because "less is more"

	Result<T> recover(
			final ThrowingFunction<? super Exception, ? extends T> mapper);
}
