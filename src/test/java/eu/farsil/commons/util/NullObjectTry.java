package eu.farsil.commons.util;

import eu.farsil.commons.function.ThrowingConsumer;
import eu.farsil.commons.function.ThrowingFunction;
import eu.farsil.commons.function.ThrowingPredicate;

import java.util.function.Function;
import java.util.function.Supplier;

class NullObjectTry implements Try<Void> {
	@Override
	public Try<Void> filter(final ThrowingPredicate<? super Void> predicate) {
		return this;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <V> Try<V> flatMap(
			final ThrowingFunction<? super Void, ? extends Try<? extends V>> mapper) {
		return (Try<V>) this;
	}

	@Override
	public Try<Void> flatRecover(
			final ThrowingFunction<? super Exception, ? extends Try<? extends Void>> mapper) {
		return this;
	}

	@Override
	public Exception getCause() {
		return null;
	}

	@Override
	public Try<Void> ifSuccessful(final ThrowingConsumer<? super Void> action) {
		return this;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <V> Try<V> map(
			final ThrowingFunction<? super Void, ? extends V> mapper) {
		return (Try<V>) this;
	}

	@Override
	public Void orElse(final Void value) {
		return null;
	}

	@Override
	public Void orElseGet(final Supplier<? extends Void> supplier) {
		return null;
	}

	@Override
	public Void orElseThrow() {
		return null;
	}

	@Override
	public <E extends Exception> Void orElseThrow(
			final Function<? super Exception, E> function) {
		return null;
	}

	@Override
	public Try<Void> recover(
			final ThrowingFunction<? super Exception, ? extends Void> mapper) {
		return this;
	}
}
