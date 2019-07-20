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
import static eu.farsil.commons.test.MoreAssertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Attempt failed test")
class FailureTest {
	@Test
	@DisplayName("filter() test")
	void filterTest() {
		final Try<Integer> subject = new Failure<>(new FailureTestException());
		assertThrows(NullPointerException.class, () -> subject.filter(null));

		final ThrowingPredicate<Integer> mock = throwingPredicate();
		assertInstanceOf(FailureTestException.class,
				subject.filter(mock).getCause());
		verifyZeroInteractions(mock);
	}

	@Test
	@DisplayName("flatMap() test")
	void flatMapTest() {
		final Try<Integer> subject = new Failure<>(new FailureTestException());
		assertThrows(NullPointerException.class, () -> subject.flatMap(null));

		final ThrowingFunction<Integer, Try<Double>> mock = throwingFunction();
		assertInstanceOf(FailureTestException.class,
				subject.flatMap(mock).getCause());
		verifyZeroInteractions(mock);
	}

	@Test
	@DisplayName("flatRecover() test")
	void flatRecoverTest() throws Exception {
		final Exception ex = new FailureTestException();
		final Try<Integer> subject = new Failure<>(ex);
		assertThrows(NullPointerException.class,
				() -> subject.flatRecover(null));

		final ThrowingFunction<Exception, Try<Integer>> mock =
				throwingFunction();
		when(mock.apply(ex)).thenReturn(new Success<>(1));
		assertEquals(1, subject.flatRecover(mock).orElseThrow());
		verify(mock).apply(ex);

		when(mock.apply(ex)).thenReturn(new Failure<>(new IOException()));
		assertInstanceOf(IOException.class,
				subject.flatRecover(mock).getCause());
		verify(mock, times(2)).apply(ex);

		doThrow(IllegalStateException.class).when(mock).apply(ex);
		assertInstanceOf(IllegalStateException.class,
				subject.flatRecover(mock).getCause());
		verify(mock, times(3)).apply(ex);
	}

	@Test
	@DisplayName("getCause() test")
	void getCauseTest() {
		final Exception ex = new FailureTestException();
		final Try<Integer> subject = new Failure<>(ex);
		assertSame(ex, subject.getCause());
	}

	@Test
	@DisplayName("ifSuccessful() test")
	void ifSuccessfulTest() {
		final Try<Integer> subject = new Failure<>(new FailureTestException());
		assertThrows(NullPointerException.class,
				() -> subject.ifSuccessful(null));

		final ThrowingConsumer<Integer> mock = throwingConsumer();
		assertInstanceOf(FailureTestException.class,
				subject.ifSuccessful(mock).getCause());
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
		final Try<Integer> subject = new Failure<>(new FailureTestException());
		assertThrows(NullPointerException.class, () -> subject.map(null));

		final ThrowingFunction<Integer, Double> mock = throwingFunction();
		assertInstanceOf(FailureTestException.class,
				subject.map(mock).getCause());
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
		final Exception ex = new FailureTestException();
		final Try<Integer> subject = new Failure<>(ex);
		assertThrows(NullPointerException.class, () -> subject.recover(null));

		final ThrowingFunction<Exception, Integer> mock = throwingFunction();
		when(mock.apply(ex)).thenReturn(1);
		assertEquals(1, subject.recover(mock).orElseThrow());
		verify(mock).apply(ex);

		doThrow(IOException.class).when(mock).apply(ex);
		assertInstanceOf(IOException.class, subject.recover(mock).getCause());
		verify(mock, times(2)).apply(ex);
	}

	/**
	 * A tag exception for the tests.
	 *
	 * @author Marco Buzzanca
	 */
	private static class FailureTestException extends RuntimeException {
	}
}