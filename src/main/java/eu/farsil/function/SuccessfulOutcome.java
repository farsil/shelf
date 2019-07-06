package eu.farsil.function;

import java.util.Objects;

enum SuccessfulOutcome implements Outcome {
	INSTANCE;

	@Override
	public Outcome flatRecover(
			final ThrowingFunction<? super Exception, ? extends Outcome> mapper) {
		Objects.requireNonNull(mapper);
		return this;
	}

	@Override
	public Exception getCause() {
		return null;
	}

	@Override
	public Outcome ifSuccessful(final ThrowingRunnable action) {
		try {
			action.run();
		} catch (final Exception e) {
			return new FailedOutcome(e);
		}
		return this;
	}

	@Override
	public boolean isSuccessful() {
		return true;
	}

	@Override
	public void orElseDo(final Runnable action) {
		// does nothing
	}

	@Override
	public void orElseThrow() {
		// does nothing
	}

	@Override
	public Outcome recover(final ThrowingConsumer<? super Exception> mapper) {
		Objects.requireNonNull(mapper);
		return this;
	}
}
