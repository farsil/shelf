package eu.farsil.commons;

public final class DummyException extends RuntimeException {
	public DummyException() {
		super();
	}

	public DummyException(final Exception e) {
		super(e);
	}
}
