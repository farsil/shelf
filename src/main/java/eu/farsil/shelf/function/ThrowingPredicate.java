package eu.farsil.shelf.function;

import org.apiguardian.api.API;

import static org.apiguardian.api.API.Status.MAINTAINED;

/**
 * Represents a predicate (boolean-valued function) of one argument which may
 * throw an exception.
 *
 * @param <T> the type of the input to the predicate.
 * @author Marco Buzzanca
 * @see java.util.function.Predicate
 */
@FunctionalInterface
@API(status = MAINTAINED, since = "0.1.0")
public interface ThrowingPredicate<T> {
	/**
	 * Evaluates this predicate on the given argument.
	 *
	 * @param t the input argument.
	 * @return {@code true} if the input argument matches the predicate,
	 * otherwise {@code false}.
	 * @throws Exception if the predicate cannot be evaluated.
	 */
	boolean test(T t) throws Exception;
}
