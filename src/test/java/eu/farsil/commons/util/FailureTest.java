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
 * Failed attempt tests.
 *
 * @author Marco Buzzanca
 */
@DisplayName("Attempt failed test")
class FailureTest {
	@Test
	@DisplayName("filter() test")
	void filterTest() {
		// null predicate
		final Try<Integer> subject = new Failure<>(new FailureTestException());
		assertThrows(NullPointerException.class, () -> subject.filter(null));

		// dummy predicate, never gets called
		final ThrowingPredicate<Integer> mock = throwingPredicate();
		assertInstanceOf(FailureTestException.class,
				assertUnsuccessful(subject.filter(mock)));
		verifyZeroInteractions(mock);
	}

	@Test
	@DisplayName("flatMap() test")
	void flatMapTest() {
		// null function
		final Try<Integer> subject = new Failure<>(new FailureTestException());
		assertThrows(NullPointerException.class, () -> subject.flatMap(null));

		// dummy function, never gets called
		final ThrowingFunction<Integer, Try<Double>> mock = throwingFunction();
		assertInstanceOf(FailureTestException.class,
				assertUnsuccessful(subject.flatMap(mock)));
		verifyZeroInteractions(mock);
	}

	@Test
	@DisplayName("flatRecover() test")
	void flatRecoverTest() throws Exception {
		// null function
		final Exception ex = new FailureTestException();
		final Try<Integer> subject = new Failure<>(ex);
		assertThrows(NullPointerException.class,
				() -> subject.flatRecover(null));

		// function that returns a success
		final ThrowingFunction<Exception, Try<Integer>> mock =
				throwingFunction();
		when(mock.apply(ex)).thenReturn(new Success<>(1));
		assertEquals(1, assertSuccessful(subject.flatRecover(mock)));
		verify(mock).apply(ex);

		// function that returns a failure
		when(mock.apply(ex)).thenReturn(new Failure<>(new IOException()));
		assertInstanceOf(IOException.class,
				assertUnsuccessful(subject.flatRecover(mock)));
		verify(mock, times(2)).apply(ex);

		// function that throws
		doThrow(IllegalStateException.class).when(mock).apply(ex);
		assertInstanceOf(IllegalStateException.class,
				assertUnsuccessful(subject.flatRecover(mock)));
		verify(mock, times(3)).apply(ex);
	}

	@Test
	@DisplayName("getCause() test")
	void getCauseTest() {
		final Exception ex = new FailureTestException();
		assertSame(ex, new Failure<>(ex).getCause());
	}

	@Test
	@DisplayName("ifSuccessful() test")
	void ifSuccessfulTest() {
		// null consumer
		final Try<Integer> subject = new Failure<>(new FailureTestException());
		assertThrows(NullPointerException.class,
				() -> subject.ifSuccessful(null));

		// dummy consumer, never gets called
		final ThrowingConsumer<Integer> mock = throwingConsumer();
		assertInstanceOf(FailureTestException.class,
				assertUnsuccessful(subject.ifSuccessful(mock)));
		verifyZeroInteractions(mock);
	}

	@Test
	@DisplayName("isSuccessful() test")
	void isSuccessfulTest() {
		assertFalse(new Failure<>(new FailureTestException()).isSuccessful());
	}

	@Test
	@DisplayName("map() test")
	void mapTest() {
		// null function
		final Try<Integer> subject = new Failure<>(new FailureTestException());
		assertThrows(NullPointerException.class, () -> subject.map(null));

		// dummy function, never gets called
		final ThrowingFunction<Integer, Double> mock = throwingFunction();
		assertInstanceOf(FailureTestException.class,
				assertUnsuccessful(subject.map(mock)));
		verifyZeroInteractions(mock);
	}

	@Test
	@DisplayName("orElseGet() test")
	void orElseGetTest() {
		final Supplier<Integer> mock = supplier();
		when(mock.get()).thenReturn(0);
		assertEquals(0,
				new Failure<>(new FailureTestException()).orElseGet(mock));
		verify(mock).get();
	}

	@Test
	@DisplayName("orElse() test")
	void orElseTest() {
		assertEquals(0, new Failure<>(new FailureTestException()).orElse(0));
	}

	@Test
	@DisplayName("orElseThrow(Function) test")
	void orElseThrowAnyTest() {
		final Exception ex = new FailureTestException();
		final Function<Exception, IOException> mock = function();
		when(mock.apply(ex)).thenReturn(new IOException());
		assertThrows(IOException.class,
				() -> new Failure<>(ex).orElseThrow(mock));
		verify(mock).apply(ex);
	}

	@Test
	@DisplayName("orElseThrow() test")
	void orElseThrowTest() {
		assertThrows(AttemptFailedException.class,
				new Failure<>(new FailureTestException())::orElseThrow);
	}

	@Test
	@DisplayName("recover() test")
	void recoverTest() throws Exception {
		// null function
		final Exception ex = new FailureTestException();
		final Try<Integer> subject = new Failure<>(ex);
		assertThrows(NullPointerException.class, () -> subject.recover(null));

		// function that returns an integer
		final ThrowingFunction<Exception, Integer> mock = throwingFunction();
		when(mock.apply(ex)).thenReturn(1);
		assertEquals(1, assertSuccessful(subject.recover(mock)));
		verify(mock).apply(ex);

		// throwing function
		doThrow(IOException.class).when(mock).apply(ex);
		assertInstanceOf(IOException.class,
				assertUnsuccessful(subject.recover(mock)));
		verify(mock, times(2)).apply(ex);
	}

	/**
	 * A tag exception for the tests.
	 *
	 * @author Marco Buzzanca
	 */
	private static class FailureTestException extends RuntimeException {
		/**
		 * Required by {@code java.lang.Serializable}.
		 */
		private static final long serialVersionUID = 3887642717116774331L;
	}
}