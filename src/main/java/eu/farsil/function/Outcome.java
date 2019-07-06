package eu.farsil.function;

public interface Outcome {
	Outcome flatRecover(
			final ThrowingFunction<? super Exception, ? extends Outcome> mapper);

	Exception getCause();

	Outcome ifSuccessful(final ThrowingRunnable action);

	boolean isSuccessful();

	void orElseDo(final Runnable action);

	void orElseThrow();

	Outcome recover(final ThrowingConsumer<? super Exception> mapper);
}
