package eu.farsil.shelf.function;

import org.apiguardian.api.API;

import static org.apiguardian.api.API.Status.MAINTAINED;

/**
 * Represents an operation that accepts a single input argument and returns no
 * result which may throw an exception.
 *
 * @param <T> the type of the input to the operation.
 * @author Marco Buzzanca
 * @see java.util.function.Consumer
 */
@FunctionalInterface
@API(status = MAINTAINED, since = "0.1.0")
public interface ThrowingConsumer<T> {
	/**
	 * Performs this operation on the given argument.
	 *
	 * @param t the input argument.
	 * @throws Exception if the operation cannot be performed.
	 */
	void accept(T t) throws Exception;
}
