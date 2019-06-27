package eu.farsil.function;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * Rappresenta un predicato (funzione booleana) con un argomento che può
 * sollevare eccezioni.
 *
 * @param <T> il tipo dell'argomento del predicato.
 * @author Marco Buzzanca (Accenture)
 */
@FunctionalInterface
public interface ThrowingPredicate<T> {
	/**
	 * Restituisce un predicato composto ottenuto dall'AND di questo
	 * predicato e
	 * di quello specificato.
	 *
	 * @param other il predicato che verrà sottoposto ad un AND logico con
	 * questo predicato.
	 * @return il predicato composto.
	 * @throws NullPointerException se other è {@code null}.
	 */
	default ThrowingPredicate<T> and(final Predicate<? super T> other) {
		Objects.requireNonNull(other);
		return t -> test(t) && other.test(t);
	}

	/**
	 * Valuta il predicato sull'argomento specificato.
	 *
	 * @param t l'argomento su cui valutare il predicato.
	 * @return {@code true} se l'argomento soddisfa il predicato, altrimenti
	 * {@code false}.
	 * @throws Exception se l'argomento non può essere valutato.
	 */
	boolean test(T t) throws Exception;

	/**
	 * Restituisce un predicato composto ottenuto dall'AND di questo
	 * predicato e
	 * di quello specificato.
	 *
	 * @param other il predicato che verrà sottoposto ad un AND logico con
	 * questo predicato.
	 * @return il predicato composto.
	 * @throws NullPointerException se other è {@code null}.
	 */
	default ThrowingPredicate<T> and(final ThrowingPredicate<? super T> other) {
		Objects.requireNonNull(other);
		return t -> test(t) && other.test(t);
	}

	/**
	 * Ritorna la negazione logica di questo predicato.
	 *
	 * @return la negazione logica di questo predicato.
	 */
	default ThrowingPredicate<T> negate() {
		return t -> !test(t);
	}

	/**
	 * Restituisce un predicato composto ottenuto dall'OR di questo predicato e
	 * di quello specificato.
	 *
	 * @param other il predicato che verrà sottoposto ad un OR logico con
	 * questo
	 * predicato.
	 * @return il predicato composto.
	 * @throws NullPointerException se other è {@code null}.
	 */
	default ThrowingPredicate<T> or(final ThrowingPredicate<? super T> other) {
		Objects.requireNonNull(other);
		return t -> test(t) || other.test(t);
	}

	/**
	 * Restituisce un predicato composto ottenuto dall'OR di questo predicato e
	 * di quello specificato.
	 *
	 * @param other il predicato che verrà sottoposto ad un OR logico con
	 * questo
	 * predicato.
	 * @return il predicato composto.
	 * @throws NullPointerException se other è {@code null}.
	 */
	default ThrowingPredicate<T> or(final Predicate<? super T> other) {
		Objects.requireNonNull(other);
		return t -> test(t) || other.test(t);
	}
}
