package eu.farsil.commons.function;

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