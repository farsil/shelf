package eu.farsil.commons.util;

/**
 * Exception that may be thrown when a {@code Predicate} or {@code
 * ThrowingPredicate} fail.
 *
 * @author Marco Buzzanca
 * @see java.util.function.Predicate
 * @see eu.farsil.commons.function.ThrowingPredicate
 */
public class PredicateFailedException extends RuntimeException {
	/**
	 * The value that failed to match the predicate.
	 */
	private final Object value;

	/**
	 * Builds an instance that holds the value that failed to match the
	 * predicate.
	 *
	 * @param value the value that failed to match the predicate.
	 */
	public PredicateFailedException(final Object value) {
		super("value: " + value);
		this.value = value;
	}

	/**
	 * Returns the value that failed to match the predicate.
	 *
	 * @return the value that failed to match the predicate.
	 */
	public Object getValue() {
		return value;
	}
}
