package eu.farsil.shelf.function;

import org.apiguardian.api.API;

import static org.apiguardian.api.API.Status.MAINTAINED;

/**
 * Represents a supplier of results which may throw an exception.
 *
 * @param <T> the result type.
 * @author Marco Buzzanca
 * @see java.util.function.Supplier
 */
@FunctionalInterface
@API(status = MAINTAINED, since = "0.1.0")
public interface ThrowingSupplier<T> {
	/**
	 * Gets a result.
	 *
	 * @return a result.
	 * @throws Exception if a result cannot be returned.
	 */
	T get() throws Exception;
}
