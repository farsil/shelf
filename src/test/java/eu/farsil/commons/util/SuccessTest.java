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
import static eu.farsil.commons.test.MoreAssertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Successful attempt tests.
 *
 * @author Marco Buzzanca
 */
@DisplayName("Attempt successful test")
class SuccessTest {
	@Test
	@DisplayName("filter() test")
	void filterTest() throws Exception {
		// null predicate
		final Try<Double> subject = new Success<>(1.0);
		assertThrows(NullPointerException.class, () -> subject.filter(null));

		// matching predicate
		final ThrowingPredicate<Double> mock = throwingPredicate();
		when(mock.test(1.0)).thenReturn(true);
		assertEquals(1.0, assertSuccessful(subject.filter(mock)));
		verify(mock).test(1.0);

		// mismatching predicate
		when(mock.test(1.0)).thenReturn(false);
		final PredicateFailedException cause =
				assertInstanceOf(PredicateFailedException.class,
						assertUnsuccessful(subject.filter(mock)));
		assertEquals(1.0, cause.getValue());
		verify(mock, times(2)).test(1.0);

		// throwing predicate
		doThrow(IllegalStateException.class).when(mock).test(1.0);
		assertInstanceOf(IllegalStateException.class,
				assertUnsuccessful(subject.filter(mock)));
		verify(mock, times(3)).test(1.0);
	}

	@Test
	@DisplayName("flatMap() test")
	void flatMapTest() throws Exception {
		// null function
		final Try<Integer> subject = new Success<>(1);
		assertThrows(NullPointerException.class, () -> subject.flatMap(null));

		// function that returns a success
		final ThrowingFunction<Integer, Try<Double>> mock = throwingFunction();
		when(mock.apply(1)).thenReturn(new Success<>(2.0));
		assertEquals(2.0, assertSuccessful(subject.flatMap(mock)));
		verify(mock).apply(1);

		// function that returns a failure
		when(mock.apply(1)).thenReturn(new Failure<>(new IOException()));
		assertInstanceOf(IOException.class,
				assertUnsuccessful(subject.flatMap(mock)));
		verify(mock, times(2)).apply(1);

		// function that throws
		doThrow(IllegalStateException.class).when(mock).apply(1);
		assertInstanceOf(IllegalStateException.class,
				assertUnsuccessful(subject.flatMap(mock)));
		verify(mock, times(3)).apply(1);
	}

	@Test
	@DisplayName("flatRecover() test")
	void flatRecoverTest() {
		// null function
		final Try<Double> subject = new Success<>(1.0);
		assertThrows(NullPointerException.class,
				() -> subject.flatRecover(null));

		// dummy function, never gets called
		final ThrowingFunction<Exception, Try<Double>> mock =
				throwingFunction();
		assertEquals(1.0, assertSuccessful(subject.flatRecover(mock)));
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
		// null consumer
		final Try<Integer> subject = new Success<>(1);
		assertThrows(NullPointerException.class,
				() -> subject.ifSuccessful(null));

		// dummy consumer
		final ThrowingConsumer<Integer> mock = throwingConsumer();
		assertEquals(1, assertSuccessful(subject.ifSuccessful(mock)));
		verify(mock).accept(1);

		// throwing consumer
		doThrow(IOException.class).when(mock).accept(1);
		assertInstanceOf(IOException.class,
				assertUnsuccessful(subject.ifSuccessful(mock)));
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
		// null function
		final Try<Integer> subject = new Success<>(1);
		assertThrows(NullPointerException.class, () -> subject.map(null));

		// function that converts to double
		final ThrowingFunction<Integer, Double> mock = throwingFunction();
		when(mock.apply(1)).thenReturn(1.0);
		assertEquals(1.0, assertSuccessful(subject.map(mock)));
		verify(mock).apply(1);

		// throwing function
		doThrow(IOException.class).when(mock).apply(1);
		assertInstanceOf(IOException.class,
				assertUnsuccessful(subject.map(mock)));
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
		// null function
		final Try<Double> subject = new Success<>(1.0);
		assertThrows(NullPointerException.class, () -> subject.recover(null));

		// dummy function, never gets called
		final ThrowingFunction<Exception, Double> mock = throwingFunction();
		assertEquals(1.0, assertSuccessful(subject.recover(mock)));
		verifyZeroInteractions(mock);
	}
}