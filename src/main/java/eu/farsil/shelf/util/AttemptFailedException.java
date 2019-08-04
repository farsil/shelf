package eu.farsil.shelf.util;

import org.apiguardian.api.API;

import java.io.Serializable;
import java.util.Objects;

import static org.apiguardian.api.API.Status.MAINTAINED;

/**
 * Exception that is thrown when the method {@link Try#orElseThrow()} is
 * invoked on a instance of {@link Try} in a failed state.
 *
 * @author Marco Buzzanca
 */
@API(status = MAINTAINED, since = "0.1.0")
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
