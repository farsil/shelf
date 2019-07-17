package eu.farsil.commons.util;

import eu.farsil.commons.function.ThrowingConsumer;
import eu.farsil.commons.function.ThrowingFunction;
import eu.farsil.commons.function.ThrowingPredicate;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.function.Function;
import java.util.function.Supplier;

import static eu.farsil.commons.mock.Functions.*;
import static eu.farsil.commons.test.MoreAssertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FailureTest {
	@Test
	void filterTest() {
		final Try<Integer> subject = new Failure<>(new FailureTestException());
		assertThrows(NullPointerException.class, () -> subject.filter(null));

		final ThrowingPredicate<Integer> mock = throwingPredicate();
		assertInstanceOf(FailureTestException.class,
				subject.filter(mock).getCause());
		verifyZeroInteractions(mock);
	}

	@Test
	void flatMapTest() {
		final Try<Integer> subject = new Failure<>(new FailureTestException());
		assertThrows(NullPointerException.class, () -> subject.flatMap(null));

		final ThrowingFunction<Integer, Try<Double>> mock = throwingFunction();
		assertInstanceOf(FailureTestException.class,
				subject.flatMap(mock).getCause());
		verifyZeroInteractions(mock);
	}

	@Test
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
	void getCauseTest() {
		final Exception ex = new FailureTestException();
		final Try<Integer> subject = new Failure<>(ex);
		assertSame(ex, subject.getCause());
	}

	@Test
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
	void isSuccessfulTest() {
		assertFalse(new Failure<>(new FailureTestException()).isSuccessful());
	}

	@Test
	void mapTest() {
		final Try<Integer> subject = new Failure<>(new FailureTestException());
		assertThrows(NullPointerException.class, () -> subject.map(null));

		final ThrowingFunction<Integer, Double> mock = throwingFunction();
		assertInstanceOf(FailureTestException.class,
				subject.map(mock).getCause());
		verifyZeroInteractions(mock);
	}

	@Test
	void orElseGetTest() {
		final Supplier<Integer> mock = supplier();
		when(mock.get()).thenReturn(0);
		assertEquals(0,
				new Failure<>(new FailureTestException()).orElseGet(mock));
		verify(mock).get();
	}

	@Test
	void orElseTest() {
		assertEquals(0, new Failure<>(new FailureTestException()).orElse(0));
	}

	@Test
	void orElseThrowAnyTest() {
		final Exception ex = new FailureTestException();
		final Function<Exception, IOException> mock = function();
		when(mock.apply(ex)).thenReturn(new IOException());
		assertThrows(IOException.class,
				() -> new Failure<>(ex).orElseThrow(mock));
		verify(mock).apply(ex);
	}

	@Test
	void orElseThrowTest() {
		assertThrows(AttemptFailedException.class,
				new Failure<>(new FailureTestException())::orElseThrow);
	}

	@Test
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

	private static class FailureTestException extends RuntimeException {
	}
}