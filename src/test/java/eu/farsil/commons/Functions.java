package eu.farsil.commons;

import eu.farsil.commons.function.ThrowingConsumer;
import eu.farsil.commons.function.ThrowingFunction;
import eu.farsil.commons.function.ThrowingPredicate;

import java.util.function.Supplier;

public class Functions {
	private Functions() {
		throw new AssertionError();
	}

	public static <T> CountingConsumer<T> countingConsumer() {
		return new CountingConsumer<>();
	}

	public static <T, E extends Exception> ThrowingConsumer<T> throwingConsumer(
			final Supplier<E> supplier) {
		return t -> {
			throw supplier.get();
		};
	}

	public static <T, R, E extends Exception> ThrowingFunction<T, R> throwingFunction(
			final Supplier<E> supplier) {
		return t -> {
			throw supplier.get();
		};
	}

	public static <T, E extends Exception> ThrowingPredicate<T> throwingPredicate(
			final Supplier<E> supplier) {
		return t -> {
			throw supplier.get();
		};
	}
}
