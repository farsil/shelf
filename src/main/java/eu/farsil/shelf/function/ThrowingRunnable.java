package eu.farsil.shelf.function;

/**
 * Represents an action which may throw an exception.
 *
 * @author Marco Buzzanca
 * @see Runnable
 */
@FunctionalInterface
public interface ThrowingRunnable {
	/**
	 * Performs an action.
	 *
	 * @throws Exception if the action cannot be performed.
	 */
	void run() throws Exception;
}
