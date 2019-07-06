package eu.farsil.function;

/**
 * The {@code Try} class provides an abstraction of a computation attempt.
 * <p>
 * It provides methods that perform some task and return
 * its result, whether it is a success or a failure.
 * <p/>
 * In its essence, {@code Try} is a generalization of the
 * {@code java.util.Optional} class which is able to handle any arbitrary
 * failure, while the {@code java.util.Optional} class is limited to handle
 * failures due to {@code null} values.
 * The outcome of the computation is collected by either of the two classes
 * {@code Result} or {@code Outcome}, respectively returned by the methods
 * {@link #get(ThrowingSupplier) get()} and {@link #run(ThrowingRunnable)
 * run()}. This facilty is not meant to replace the {@code try/catch}
 * mechanism entirely: its purpose is to help programmers deal with chains of
 * method calls that may throw an exception when invoked.
 *
 * @author Marco Buzzanca
 * @see java.util.Optional
 * @see Result
 * @see Outcome
 */
public final class Try {
	/**
	 * Utility class.
	 */
	private Try() {
		throw new AssertionError();
	}

	/**
	 * Attempts to perform a computation that returns a value, which is
	 * represented by the specified {@code ThrowingSupplier}. If it throws an
	 * exception, the result returned will be in a <i>failed</i> state,
	 * otherwise it will be in a <i>successful</i> state.
	 *
	 * @param supplier the computation that returns a value.
	 * @param <T> the type of the result.
	 * @return an instance of {@code Result}, which represents the outcome of
	 * a computation that returns a value, in a successful or failed
	 * state. The {@code Result} instance may further processed with its
	 * fluent interface.
	 * @see Result
	 */
	public static <T> Result<T> get(
			final ThrowingSupplier<? extends T> supplier) {
		try {
			return new SuccessfulResult<>(supplier.get());
		} catch (final Exception e) {
			return new FailedResult<>(e);
		}
	}

	/**
	 * Attempts to perform a computation, which is represented by the
	 * specified {@code ThrowingRunnable}. If it throws an exception, the
	 * outcome returned will be in a <i>failed</i> state, otherwise it will
	 * be in a <i>successful</i> state.
	 *
	 * @param runnable the computation that returns a value.
	 * @return an instance of {@code Outcome}, which represents the outcome of
	 * a computation that does not return a value, in a successful or failed
	 * state. The {@code Outcome} instance may be further processed
	 * with its fluent interface.
	 * @see Outcome
	 */
	public static Outcome run(final ThrowingRunnable runnable) {
		try {
			runnable.run();
			return SuccessfulOutcome.INSTANCE;
		} catch (final Exception e) {
			return new FailedOutcome(e);
		}
	}
}
