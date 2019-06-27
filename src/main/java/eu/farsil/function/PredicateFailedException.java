package eu.farsil.function;

public class PredicateFailedException extends RuntimeException {
	PredicateFailedException(final Object value) {
		super("value: " + value);
	}
}
