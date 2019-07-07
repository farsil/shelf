package eu.farsil.commons.function;

/**
 * Rappresenta un predicato (funzione booleana) con un argomento che può
 * sollevare eccezioni.
 *
 * @param <T> il tipo dell'argomento del predicato.
 * @author Marco Buzzanca
 */
@FunctionalInterface
public interface ThrowingPredicate<T> {
	/**
	 * Valuta il predicato sull'argomento specificato.
	 *
	 * @param t l'argomento su cui valutare il predicato.
	 * @return {@code true} se l'argomento soddisfa il predicato, altrimenti
	 * {@code false}.
	 * @throws Exception se l'argomento non può essere valutato.
	 */
	boolean test(T t) throws Exception;
}
