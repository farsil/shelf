package eu.farsil.shelf.util;

import eu.farsil.shelf.function.ThrowingConsumer;
import eu.farsil.shelf.function.ThrowingFunction;
import eu.farsil.shelf.function.ThrowingPredicate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.function.Function;
import java.util.function.Supplier;

import static eu.farsil.shelf.mock.Functions.*;
import static eu.farsil.shelf.test.MoreAssertions.*;
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
						assertNotSuccessful(subject.filter(mock)));
		assertEquals(1.0, cause.getValue());
		verify(mock, times(2)).test(1.0);

		// throwing predicate
		doThrow(IllegalStateException.class).when(mock).test(1.0);
		assertInstanceOf(IllegalStateException.class,
				assertNotSuccessful(subject.filter(mock)));
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
				assertNotSuccessful(subject.flatMap(mock)));
		verify(mock, times(2)).apply(1);

		// function that throws
		doThrow(IllegalStateException.class).when(mock).apply(1);
		assertInstanceOf(IllegalStateException.class,
				assertNotSuccessful(subject.flatMap(mock)));
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
				assertNotSuccessful(subject.ifSuccessful(mock)));
		verify(mock, times(2)).accept(1);
	}

	@Test
	@DisplayName("ifUnsuccessful() test")
	void ifUnsuccessfulTest() {
		// null consumer
		final Try<Integer> subject = new Success<>(1);
		assertThrows(NullPointerException.class,
				() -> subject.ifUnsuccessful(null));

		// throwing consumer
		final ThrowingConsumer<Exception> mock = throwingConsumer();
		assertEquals(1,
				assertSuccessful(new Success<>(1).ifUnsuccessful(mock)));
		verifyZeroInteractions(mock);
	}

	@Test
	@DisplayName("isSuccessful() test")
	void isSuccessfulTest() {
		assertTrue(new Success<>(1).isSuccessful());
	}

	@Test
	@DisplayName("isUnsuccessful() test")
	void isUnsuccessfulTest() {
		assertFalse(new Success<>(1).isUnsuccessful());
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
				assertNotSuccessful(subject.map(mock)));
		verify(mock, times(2)).apply(1);
	}

	@Test
	@DisplayName("orElseGet() test")
	void orElseGetTest() {
		// null supplier
		final Try<Integer> subject = new Success<>(1);
		assertThrows(NullPointerException.class, () -> subject.orElseGet(null));

		// dummy supplier
		final Supplier<Integer> mock = supplier();
		assertEquals(1, subject.orElseGet(mock));
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
		// null function
		final Try<Integer> subject = new Success<>(1);
		assertThrows(NullPointerException.class,
				() -> subject.orElseThrow(null));

		// dummy function
		final Function<Exception, IOException> mock = function();
		assertEquals(1, assertDoesSupply(() -> subject.orElseThrow(mock)));
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