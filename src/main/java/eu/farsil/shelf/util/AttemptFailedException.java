package eu.farsil.shelf.util;

import java.io.Serializable;
import java.util.Objects;

/**
 * Exception that is thrown when the method {@link Try#orElseThrow()} is
 * invoked on a instance of {@link Try} in a failed state.
 *
 * @author Marco Buzzanca
 */
// package-private, users are not supposed to use or catch this class
class AttemptFailedException extends RuntimeException {
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
	AttemptFailedException(final Throwable cause) {
		super(Objects.requireNonNull(cause));
	}
}
