package eu.farsil.commons.util;

import eu.farsil.commons.function.ThrowingConsumer;
import eu.farsil.commons.function.ThrowingFunction;
import eu.farsil.commons.function.ThrowingPredicate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.function.Function;
import java.util.function.Supplier;

import static eu.farsil.commons.mock.Functions.*;
import static eu.farsil.commons.test.MoreAssertions.assertDoesSupply;
import static eu.farsil.commons.test.MoreAssertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Attempt successful test")
class SuccessTest {
	@Test
	@DisplayName("filter() test")
	void filterTest() throws Exception {
		final Try<Double> subject = new Success<>(1.0);
		assertThrows(NullPointerException.class, () -> subject.filter(null));

		final ThrowingPredicate<Double> mock = throwingPredicate();
		when(mock.test(1.0)).thenReturn(true);
		assertEquals(1.0, assertDoesSupply(subject.filter(mock)::orElseThrow));
		verify(mock).test(1.0);

		when(mock.test(1.0)).thenReturn(false);
		assertEquals(1.0, assertInstanceOf(PredicateFailedException.class,
				subject.filter(mock).getCause()).getValue());
		verify(mock, times(2)).test(1.0);

		doThrow(IllegalStateException.class).when(mock).test(1.0);
		assertInstanceOf(IllegalStateException.class,
				subject.filter(mock).getCause());
		verify(mock, times(3)).test(1.0);
	}

	@Test
	@DisplayName("flatMap() test")
	void flatMapTest() throws Exception {
		final Try<Integer> subject = new Success<>(1);
		assertThrows(NullPointerException.class, () -> subject.flatMap(null));

		final ThrowingFunction<Integer, Try<Double>> mock = throwingFunction();
		when(mock.apply(1)).thenReturn(new Success<>(2.0));
		assertEquals(2.0, assertDoesSupply(subject.flatMap(mock)::orElseThrow));
		verify(mock).apply(1);

		when(mock.apply(1)).thenReturn(new Failure<>(new IOException()));
		assertInstanceOf(IOException.class, subject.flatMap(mock).getCause());
		verify(mock, times(2)).apply(1);

		doThrow(IllegalStateException.class).when(mock).apply(1);
		assertInstanceOf(IllegalStateException.class,
				subject.flatMap(mock).getCause());
		verify(mock, times(3)).apply(1);
	}

	@Test
	@DisplayName("flatRecover() test")
	void flatRecoverTest() {
		final Try<Double> subject = new Success<>(1.0);
		assertThrows(NullPointerException.class,
				() -> subject.flatRecover(null));

		final ThrowingFunction<Exception, Try<Double>> mock =
				throwingFunction();
		assertEquals(1.0,
				assertDoesSupply(subject.flatRecover(mock)::orElseThrow));
		verifyZeroInteractions(mock);
	}

	@Test
	@DisplayName("getCause() test")
	void getCauseTest() {
		assertNull(new Success<>(1).getCause());
	}

	@Test
	@DisplayName("ifSuccessful() test")
	void ifSuccessfulTest() throws Exception {
		final Try<Integer> subject = new Success<>(1);
		assertThrows(NullPointerException.class,
				() -> subject.ifSuccessful(null));

		final ThrowingConsumer<Integer> mock = throwingConsumer();
		assertTrue(subject.ifSuccessful(mock).isSuccessful());
		verify(mock).accept(1);

		doThrow(IOException.class).when(mock).accept(1);
		assertInstanceOf(IOException.class,
				subject.ifSuccessful(mock).getCause());
		verify(mock, times(2)).accept(1);
	}

	@Test
	@DisplayName("isSuccessful() test")
	void isSuccessfulTest() {
		assertTrue(new Success<>(1).isSuccessful());
	}

	@Test
	@DisplayName("map() test")
	void mapTest() throws Exception {
		final Try<Integer> subject = new Success<>(1);
		assertThrows(NullPointerException.class, () -> subject.map(null));

		final ThrowingFunction<Integer, Double> mock = throwingFunction();
		when(mock.apply(1)).thenReturn(2.0);
		assertEquals(2.0, assertDoesSupply(subject.map(mock)::orElseThrow));
		verify(mock).apply(1);

		doThrow(IOException.class).when(mock).apply(1);
		assertInstanceOf(IOException.class, subject.map(mock).getCause());
		verify(mock, times(2)).apply(1);
	}

	@Test
	@DisplayName("orElseGet() test")
	void orElseGetTest() {
		final Supplier<Integer> mock = supplier();
		assertEquals(1, new Success<>(1).orElseGet(mock));
		verifyZeroInteractions(mock);
	}

	@Test
	@DisplayName("orElse() test")
	void orElseTest() {
		assertEquals(1, new Success<>(1).orElse(0));
	}

	@Test
	@DisplayName("orElseThrow(Function) test")
	void orElseThrowAnyTest() {
		final Function<Exception, IOException> mock = function();
		assertEquals(1,
				assertDoesSupply(() -> new Success<>(1).orElseThrow(mock)));
		verifyZeroInteractions(mock);
	}

	@Test
	@DisplayName("orElseThrow() test")
	void orElseThrowTest() {
		assertEquals(1.0, assertDoesSupply(new Success<>(1.0)::orElseThrow));
	}

	@Test
	@DisplayName("recover() test")
	void recoverTest() {
		final Try<Double> subject = new Success<>(1.0);
		assertThrows(NullPointerException.class, () -> subject.recover(null));

		final ThrowingFunction<Exception, Double> mock = throwingFunction();
		assertEquals(1.0, assertDoesSupply(subject.recover(mock)::orElseThrow));
		verifyZeroInteractions(mock);
	}
}