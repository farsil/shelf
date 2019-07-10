package eu.farsil.commons.util;

import eu.farsil.commons.CountingConsumer;
import eu.farsil.commons.DummyException;
import org.junit.jupiter.api.Test;

import static eu.farsil.commons.Assertions.assertInstanceOf;
import static eu.farsil.commons.Assertions.assertSupplierDoesNotThrow;
import static eu.farsil.commons.Functions.*;
import static org.junit.jupiter.api.Assertions.*;

class SuccessTest {
	@Test
	void filterTest() {
		final Try<Double> subject = new Success<>(1.0);

		assertThrows(NullPointerException.class, () -> subject.filter(null));

		assertEquals(1.0, assertSupplierDoesNotThrow(
				subject.filter(i -> i > 0)::orElseThrow));

		assertInstanceOf(PredicateFailedException.class,
				subject.filter(i -> i < 0).getCause());

		assertInstanceOf(DummyException.class,
				subject.filter(throwingPredicate(DummyException::new))
						.getCause());
	}

	@Test
	void flatMapTest() {
		final Try<Integer> subject = new Success<>(1);

		assertThrows(NullPointerException.class, () -> subject.flatMap(null));

		assertEquals(2.0, assertSupplierDoesNotThrow(
				subject.flatMap(i -> new Success<>(i * 2.0))::orElseThrow));

		assertInstanceOf(DummyException.class,
				subject.flatMap(throwingFunction(DummyException::new))
						.getCause());
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

		final CountingConsumer<Integer> action = countingConsumer();

		assertThrows(NullPointerException.class,
				() -> subject.ifSuccessful(null));

		assertDoesNotThrow(() -> subject.ifSuccessful(action));
		assertEquals(1, action.getInvocations());

		assertInstanceOf(DummyException.class,
				subject.ifSuccessful(throwingConsumer(DummyException::new))
						.getCause());
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

		assertInstanceOf(DummyException.class,
				subject.map(throwingFunction(DummyException::new)).getCause());
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
}