package eu.farsil.function;

import java.util.Objects;

class FailedOutcome implements Outcome {
	private final Exception cause;

	FailedOutcome(final Exception cause) {
		this.cause = Objects.requireNonNull(cause);
	}

	@Override
	public Outcome flatRecover(
			final ThrowingFunction<? super Exception, Outcome> mapper) {
		try {
			return mapper.apply(cause);
		} catch (final Exception e) {
			return new FailedOutcome(e);
		}
	}

	@Override
	public Exception getCause() {
		return cause;
	}

	@Override
	public Outcome ifSuccessful(final ThrowingRunnable action) {
		return this;
	}

	@Override
	public boolean isSuccessful() {
		return false;
	}

	@Override
	public void orElseDo(final Runnable action) {
		action.run();
	}

	@Override
	public void orElseThrow() {
		throw new AttemptFailedException(cause);
	}

	@Override
	public Outcome recover(
			final ThrowingConsumer<? super Exception> mapper) {
		try {
			mapper.accept(cause);
			return SuccessfulOutcome.INSTANCE;
		} catch (final Exception e) {
			return new FailedOutcome(e);
		}
	}
}
