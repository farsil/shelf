package eu.farsil.commons.util;

import java.util.Objects;

/**
 * Exception that is thrown when the method {@code Try.orElseThrow()} is
 * invoked on a instance of {@code Try} in a failed state.
 *
 * @author Marco Buzzanca
 */
public class AttemptFailedException extends RuntimeException {
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
