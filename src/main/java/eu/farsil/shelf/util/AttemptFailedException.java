package eu.farsil.shelf.util;

import java.io.Serializable;
import java.util.Objects;

/**
 * Exception that is thrown when the method {@code Try.orElseThrow()} is
 * invoked on a instance of {@code Try} in a failed state.
 *
 * @author Marco Buzzanca
 */
public class AttemptFailedException extends RuntimeException {
	/**
	 * Required by {@link Serializable}.
	 */
	private static final long serialVersionUID = -6198901905401795069L;

	/**
	 * Builds an instance that holds the cause of the failure.
	 *
	 * @param cause the cause of the failure.
	 * @throws NullPointerException if the cause is {@code null}.
	 */
	// package-private, users are not supposed to instantiate this class
	AttemptFailedException(final Throwable cause) {
		super(Objects.requireNonNull(cause));
	}
}
