package eu.farsil.shelf.lambda.function;

import org.apiguardian.api.API;

import java.io.Serializable;
import java.util.function.Predicate;

import static org.apiguardian.api.API.Status.EXPERIMENTAL;

/**
 * Exception that is intended to be thrown when a {@link Predicate} or {@link
 * ThrowingPredicate} fails.
 *
 * @author Marco Buzzanca
 * @see Predicate
 * @see ThrowingPredicate
 */
@API(status = EXPERIMENTAL, since = "0.1.0")
public class PredicateFailedException extends RuntimeException {
	/**
	 * Required by {@link Serializable}.
	 */
	private static final long serialVersionUID = -712482672060994332L;

	/**
	 * The value that failed to match the predicate.
	 */
	private final transient Object value;

	/**
	 * Builds an instance that holds the value that failed to match the
	 * predicate.
	 *
	 * @param value the value that failed to match the predicate.
	 */
	public PredicateFailedException(final Object value) {
		super(value == null ? null : "value: " + value);
		this.value = value;
	}

	/**
	 * Returns the value that failed to match the predicate. Always returns
	 * {@code null} if this exception instance was obtained by serialization.
	 *
	 * @return the value that failed to match the predicate.
	 */
	public Object getValue() {
		return value;
	}
}
