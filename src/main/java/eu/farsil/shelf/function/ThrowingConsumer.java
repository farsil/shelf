package eu.farsil.shelf.function;

/**
 * Represents an operation that accepts a single input argument and returns no
 * result which may throw an exception.
 *
 * @param <T> the type of the input to the operation.
 * @author Marco Buzzanca
 * @see java.util.function.Consumer
 */
@FunctionalInterface
public interface ThrowingConsumer<T> {
	/**
	 * Performs this operation on the given argument.
	 *
	 * @param t the input argument.
	 * @throws Exception if the operation cannot be performed.
	 */
	void accept(T t) throws Exception;
}
