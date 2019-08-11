package eu.farsil.shelf.lambda.function;

import org.apiguardian.api.API;

import static org.apiguardian.api.API.Status.MAINTAINED;

/**
 * Represents a function that accepts one argument and produces a result
 * which may throw an exception.
 *
 * @param <T> the type of the input to the function.
 * @param <R> the type of the result of the function.
 * @author Marco Buzzanca
 * @see java.util.function.Function
 */
@FunctionalInterface
@API(status = MAINTAINED, since = "0.1.0")
public interface ThrowingFunction<T, R> {
	/**
	 * Applies this function to the given argument.
	 *
	 * @param t the function argument.
	 * @return the function result.
	 * @throws Exception if the function cannot be applied.
	 */
	R apply(T t) throws Exception;
}