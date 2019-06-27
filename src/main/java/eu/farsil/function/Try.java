package eu.farsil.function;

/**
 * The {@code Try} class provides an abstraction of a computation attempt.
 *
 * provides methods that perform a computation and return
 * its result, whether it is a success or a failure.
 * <p/>
 * The {@code Try} class is essentially a generalization of the
 * {@link java.util.Optional Optional} class which is able to handle any
 * arbitrary failure, while the {@code Optional} class is only able to handle
 * failures due to {@code null} values.
 * The outcome of the computation is collected by either of the two classes
 * {@link Result} and {@link Outcome}, respectively returned by the methods
 * {@link #get(ThrowingSupplier) get()} and {@link #run(ThrowingRunnable)
 * run()}.
 *
 * @author Marco Buzzanca
 */
public final class Try {
	public static <T> Result<T> get(
			final ThrowingSupplier<? extends T> supplier) {
		try {
			return new SuccessfulResult<>(supplier.get());
		} catch (final Exception e) {
			return new FailedResult<>(e);
		}
	}

	public static Outcome run(final ThrowingRunnable runnable) {
		try {
			runnable.run();
			return SuccessfulOutcome.INSTANCE;
		} catch (final Exception e) {
			return new FailedOutcome(e);
		}
	}
}
