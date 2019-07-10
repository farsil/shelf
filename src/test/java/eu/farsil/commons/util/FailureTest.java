package eu.farsil.commons.util;

import eu.farsil.commons.CountingConsumer;
import eu.farsil.commons.DummyException;
import org.junit.jupiter.api.Test;

import static eu.farsil.commons.Assertions.assertInstanceOf;
import static eu.farsil.commons.Functions.*;
import static org.junit.jupiter.api.Assertions.*;

class FailureTest {
	@Test
	void filterTest() {
		final Try<Integer> subject = new Failure<>(new FailureTestException());

		assertThrows(NullPointerException.class, () -> subject.filter(null));

		assertInstanceOf(FailureTestException.class,
				subject.filter(i -> true).getCause());

		assertInstanceOf(FailureTestException.class,
				subject.filter(throwingPredicate(DummyException::new))
						.getCause());
	}

	@Test
	void flatMapTest() {
		final Try<Integer> subject = new Failure<>(new FailureTestException());

		assertThrows(NullPointerException.class, () -> subject.flatMap(null));

		assertInstanceOf(FailureTestException.class,
				subject.flatMap(i -> null).getCause());

		assertInstanceOf(FailureTestException.class,
				subject.flatMap(throwingFunction(DummyException::new))
						.getCause());
	}

	@Test
	void flatRecoverTest() {
		final Exception expected = new FailureTestException();
		final Try<Integer> subject = new Failure<>(expected);

		assertThrows(NullPointerException.class,
				() -> subject.flatRecover(null));

		assertEquals(expected.hashCode(),
				subject.flatRecover(e -> new Success<>(e.hashCode()))
						.orElseThrow());

		assertInstanceOf(DummyException.class,
				subject.flatRecover(e -> new Failure<>(new DummyException()))
						.getCause());

		assertInstanceOf(DummyException.class,
				subject.flatRecover(throwingFunction(DummyException::new))
						.getCause());
	}

	@Test
	void getCauseTest() {
		final Exception expected = new FailureTestException();
		final Try<Integer> subject = new Failure<>(expected);
		assertSame(expected, subject.getCause());
	}

	@Test
	void ifSuccessfulTest() {
		final Try<Integer> subject = new Failure<>(new FailureTestException());
		final CountingConsumer<Integer> action = countingConsumer();

		assertThrows(NullPointerException.class,
				() -> subject.ifSuccessful(null));

		assertDoesNotThrow(() -> subject.ifSuccessful(action));
		assertEquals(0, action.getInvocations());

		assertInstanceOf(FailureTestException.class,
				subject.ifSuccessful(throwingConsumer(DummyException::new))
						.getCause());
	}

	@Test
	void isSuccessfulTest() {
		assertFalse(new Failure<>(new FailureTestException()).isSuccessful());
	}

	@Test
	void mapTest() {
		final Try<Integer> subject = new Failure<>(new FailureTestException());

		assertThrows(NullPointerException.class, () -> subject.map(null));

		assertInstanceOf(FailureTestException.class,
				subject.map(i -> null).getCause());

		assertInstanceOf(FailureTestException.class,
				subject.map(throwingFunction(DummyException::new)).getCause());
	}

	@Test
	void orElseGetTest() {
		assertEquals(0,
				new Failure<>(new FailureTestException()).orElseGet(() -> 0));
	}

	@Test
	void orElseTest() {
		assertEquals(0, new Failure<>(new FailureTestException()).orElse(0));
	}

	@Test
	void orElseThrowAnyTest() {
		assertThrows(DummyException.class,
				() -> new Failure<>(new FailureTestException()).orElseThrow(
						DummyException::new));
	}

	@Test
	void orElseThrowTest() {
		assertThrows(AttemptFailedException.class,
				() -> new Failure<>(new FailureTestException()).orElseThrow());
	}

	@Test
	void recoverTest() {
		final Exception expected = new FailureTestException();
		final Try<Integer> subject = new Failure<>(expected);

		assertThrows(NullPointerException.class, () -> subject.recover(null));

		assertEquals(expected.hashCode(),
				subject.recover(Object::hashCode).orElseThrow());

		assertInstanceOf(DummyException.class,
				subject.recover(throwingFunction(DummyException::new))
						.getCause());
	}

	private static class FailureTestException extends RuntimeException {
	}
}