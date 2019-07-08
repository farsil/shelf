package eu.farsil.commons.function;

/**
 * Represents a supplier of results which may throw an exception.
 *
 * @param <T> the result type.
 * @author Marco Buzzanca
 * @see java.util.function.Supplier
 */
@FunctionalInterface
public interface ThrowingSupplier<T> {
	/**
	 * Gets a result.
	 *
	 * @return a result.
	 * @throws Exception if a result cannot be returned.
	 */
	T get() throws Exception;
}
