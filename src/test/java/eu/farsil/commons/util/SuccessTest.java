package eu.farsil.commons.util;

import eu.farsil.commons.function.ThrowingConsumer;
import eu.farsil.commons.function.ThrowingFunction;
import eu.farsil.commons.function.ThrowingPredicate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.ThrowingSupplier;

import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

class SuccessTest {
	// necessary because java type inference is still kind of weak
	private static <T> T assertSupplierDoesNotThrow(
			final ThrowingSupplier<T> supplier) {
		return assertDoesNotThrow(supplier);
	}

	private static <T, E extends Exception> ThrowingConsumer<T> throwingConsumer(
			final Supplier<E> supplier) {
		return t -> {
			throw supplier.get();
		};
	}

	private static <T, R, E extends Exception> ThrowingFunction<T, R> throwingFunction(
			final Supplier<E> supplier) {
		return t -> {
			throw supplier.get();
		};
	}

	private static <T, E extends Exception> ThrowingPredicate<T> throwingPredicate(
			final Supplier<E> supplier) {
		return t -> {
			throw supplier.get();
		};
	}

	@Test
	void filterTest() {
		final Try<Double> subject = new Success<>(1.0);

		assertThrows(NullPointerException.class, () -> subject.filter(null));

		assertEquals(1.0, assertSupplierDoesNotThrow(
				subject.filter(i -> i > 0)::orElseThrow));

		assertTrue(subject.filter(i -> i < 0)
				.getCause() instanceof PredicateFailedException);

		assertTrue(subject.filter(throwingPredicate(DummyException::new))
				.getCause() instanceof DummyException);
	}

	@Test
	void flatMapTest() {
		final Try<Integer> subject = new Success<>(1);

		assertThrows(NullPointerException.class, () -> subject.flatMap(null));

		assertEquals(2.0, assertSupplierDoesNotThrow(
				subject.flatMap(i -> new Success<>(i * 2.0))::orElseThrow));

		assertTrue(subject.flatMap(throwingFunction(DummyException::new))
				.getCause() instanceof DummyException);
	}

	@Test
	void flatRecoverTest() {
		final Try<Integer> subject = new Success<>(1);

		assertThrows(NullPointerException.class,
				() -> subject.flatRecover(null));

		assertEquals(subject.orElseThrow(),
				subject.flatRecover(i -> null).orElseThrow());
	}

	@Test
	void getCauseTest() {
		assertNull(new Success<>(1).getCause());
	}

	@Test
	void ifSuccessfulTest() throws Exception {
		final Try<Integer> subject = new Success<>(1);

		final VerifiableConsumer<Integer> action = new VerifiableConsumer<>();

		assertThrows(NullPointerException.class,
				() -> subject.ifSuccessful(null));

		assertDoesNotThrow(() -> subject.ifSuccessful(action));
		assertEquals(1, action.invocations);

		assertTrue(subject.ifSuccessful(throwingConsumer(DummyException::new))
				.getCause() instanceof DummyException);
	}

	@Test
	void isSuccessfulTest() {
		assertTrue(new Success<>(1).isSuccessful());
	}

	@Test
	void mapTest() {
		final Try<Integer> subject = new Success<>(1);

		assertThrows(NullPointerException.class, () -> subject.map(null));

		assertEquals(2.0, assertSupplierDoesNotThrow(
				subject.map(i -> i * 2.0)::orElseThrow));

		assertTrue(subject.map(throwingFunction(DummyException::new))
				.getCause() instanceof DummyException);
	}

	@Test
	void orElseGetTest() {
		assertEquals(1, new Success<>(1).orElseGet(() -> 0));
	}

	@Test
	void orElseTest() {
		assertEquals(1, new Success<>(1).orElse(0));
	}

	@Test
	void orElseThrowAnyTest() {
		assertEquals(1, new Success<>(1).orElseThrow(DummyException::new));
	}

	@Test
	void orElseThrowTest() {
		assertEquals(1, new Success<>(1).orElseThrow());
	}

	@Test
	void recoverTest() {
		final Try<Integer> subject = new Success<>(1);

		assertThrows(NullPointerException.class, () -> subject.recover(null));

		assertEquals(subject.orElseThrow(),
				subject.recover(i -> null).orElseThrow());
	}

	private static final class DummyException extends RuntimeException {
		private DummyException() {
			super();
		}

		private DummyException(final Exception e) {
			super(e);
		}
	}

	private static final class VerifiableConsumer<T>
			implements ThrowingConsumer<T> {
		private int invocations = 0;

		@Override
		public void accept(final T t) {
			invocations++;
		}
	}
}