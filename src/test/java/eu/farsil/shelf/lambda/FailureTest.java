package eu.farsil.shelf.lambda;

import eu.farsil.shelf.lambda.function.ThrowingFunction;
import eu.farsil.shelf.lambda.function.ThrowingPredicate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.Serializable;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static eu.farsil.shelf.mock.Functions.*;
import static eu.farsil.shelf.test.MoreAssertions.*;
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
		final Try<Integer> subject = new Failure<>(new TestException());
		assertThrows(NullPointerException.class, () -> subject.filter(null));

		// dummy predicate, never gets called
		final ThrowingPredicate<Integer> mock = throwingPredicate();
		assertInstanceOf(TestException.class,
				assertNotSuccessful(subject.filter(mock)));
		verifyZeroInteractions(mock);
	}

	@Test
	@DisplayName("flatMap() test")
	void flatMapTest() {
		// null function
		final Try<Integer> subject = new Failure<>(new TestException());
		assertThrows(NullPointerException.class, () -> subject.flatMap(null));

		// dummy function, never gets called
		final ThrowingFunction<Integer, Try<Double>> mock = throwingFunction();
		assertInstanceOf(TestException.class,
				assertNotSuccessful(subject.flatMap(mock)));
		verifyZeroInteractions(mock);
	}

	@Test
	@DisplayName("flatRecover() test")
	void flatRecoverTest() throws Exception {
		// null function
		final Exception ex = new TestException();
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
				assertNotSuccessful(subject.flatRecover(mock)));
		verify(mock, times(2)).apply(ex);

		// function that throws
		doThrow(IllegalStateException.class).when(mock).apply(ex);
		assertInstanceOf(IllegalStateException.class,
				assertNotSuccessful(subject.flatRecover(mock)));
		verify(mock, times(3)).apply(ex);
	}

	@Test
	@DisplayName("getCause() test")
	void getCauseTest() {
		final Exception ex = new TestException();
		assertSame(ex, new Failure<>(ex).getCause());
	}

	@Test
	@DisplayName("ifSuccessful() test")
	void ifSuccessfulTest() {
		// null consumer
		final Try<Integer> subject = new Failure<>(new TestException());
		assertThrows(NullPointerException.class,
				() -> subject.ifSuccessful(null));

		// dummy consumer, never gets called
		final Consumer<Integer> mock = consumer();
		assertInstanceOf(TestException.class,
				assertNotSuccessful(subject.ifSuccessful(mock)));
		verifyZeroInteractions(mock);
	}

	@Test
	void ifUnsuccessfulTest() {
		// null consumer
		final Exception ex = new TestException();
		final Try<Integer> subject = new Failure<>(ex);
		assertThrows(NullPointerException.class,
				() -> subject.ifUnsuccessful(null));

		// consumer
		final Consumer<Exception> mock = consumer();
		assertEquals(ex, assertNotSuccessful(subject.ifUnsuccessful(mock)));
		verify(mock).accept(ex);
	}

	@Test
	@DisplayName("isSuccessful() test")
	void isSuccessfulTest() {
		assertFalse(new Failure<>(new TestException()).isSuccessful());
	}

	@Test
	@DisplayName("isUnsuccessful() test")
	void isUnsuccessfulTest() {
		assertTrue(new Failure<>(new TestException()).isUnsuccessful());
	}

	@Test
	@DisplayName("map() test")
	void mapTest() {
		// null function
		final Try<Integer> subject = new Failure<>(new TestException());
		assertThrows(NullPointerException.class, () -> subject.map(null));

		// dummy function, never gets called
		final ThrowingFunction<Integer, Double> mock = throwingFunction();
		assertInstanceOf(TestException.class,
				assertNotSuccessful(subject.map(mock)));
		verifyZeroInteractions(mock);
	}

	@Test
	@DisplayName("orElseGet() test")
	void orElseGetTest() {
		// null supplier
		final Try<Integer> subject = new Failure<>(new TestException());
		assertThrows(NullPointerException.class, () -> subject.orElseGet(null));

		// constant supplier
		final Supplier<Integer> mock = supplier();
		when(mock.get()).thenReturn(0);
		assertEquals(0, subject.orElseGet(mock));
		verify(mock).get();
	}

	@Test
	@DisplayName("orElse() test")
	void orElseTest() {
		assertEquals(0, new Failure<>(new TestException()).orElse(0));
	}

	@Test
	@DisplayName("orElseThrow(Function) test")
	void orElseThrowAnyTest() {
		// null function
		final Exception ex = new TestException();
		final Try<Integer> subject = new Failure<>(ex);
		assertThrows(NullPointerException.class,
				() -> subject.orElseThrow(null));

		// constant function
		final Function<Exception, IOException> mock = function();
		when(mock.apply(ex)).thenReturn(new IOException());
		assertThrows(IOException.class, () -> subject.orElseThrow(mock));
		verify(mock).apply(ex);
	}

	@Test
	@DisplayName("orElseThrow() test")
	void orElseThrowTest() {
		assertThrows(AttemptFailedException.class,
				new Failure<>(new TestException())::orElseThrow);
	}

	@Test
	@DisplayName("recover() test")
	void recoverTest() throws Exception {
		// null function
		final Exception ex = new TestException();
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
				assertNotSuccessful(subject.recover(mock)));
		verify(mock, times(2)).apply(ex);
	}

	/**
	 * A tag exception for the tests.
	 *
	 * @author Marco Buzzanca
	 */
	private static class TestException extends RuntimeException {
		/**
		 * Required by {@link Serializable}.
		 */
		private static final long serialVersionUID = 3887642717116774331L;
	}
}