package eu.farsil.commons;

import eu.farsil.commons.function.ThrowingConsumer;

public final class CountingConsumer<T> implements ThrowingConsumer<T> {
	private int invocations;

	CountingConsumer() {
		this.invocations = 0;
	}

	@Override
	public void accept(final T t) {
		invocations++;
	}

	public int getInvocations() {
		return invocations;
	}
}
