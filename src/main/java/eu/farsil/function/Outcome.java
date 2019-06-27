package eu.farsil.function;

public interface Outcome {
	static Outcome failure(final Exception cause) {
		return new FailedOutcome(cause);
	}

	static Outcome success() {
		return SuccessfulOutcome.INSTANCE;
	}

	Outcome flatRecover(
			final ThrowingFunction<? super Exception, Outcome> mapper);

	Exception getCause();

	Outcome ifSuccessful(final ThrowingRunnable action);

	boolean isSuccessful();

	void orElseDo(final Runnable action);

	void orElseThrow();

	Outcome recover(final ThrowingConsumer<? super Exception> mapper);
}
