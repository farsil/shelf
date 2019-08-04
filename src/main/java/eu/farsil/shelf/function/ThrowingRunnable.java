package eu.farsil.shelf.function;

import org.apiguardian.api.API;

import static org.apiguardian.api.API.Status.MAINTAINED;

/**
 * Represents an action which may throw an exception.
 *
 * @author Marco Buzzanca
 * @see Runnable
 */
@FunctionalInterface
@API(status = MAINTAINED, since = "0.1.0")
public interface ThrowingRunnable {
	/**
	 * Performs an action.
	 *
	 * @throws Exception if the action cannot be performed.
	 */
	void run() throws Exception;
}
