package eu.farsil.commons.function;

/**
 * Rappresenta una funzione che accetta un argomento, produce un risultato, e
 * può sollevare un'eccezione.
 *
 * @param <T> il tipo dell'argomento della funzione.
 * @param <R> il tipo di ritorno della funzione.
 * @author Marco Buzzanca
 */
@FunctionalInterface
public interface ThrowingFunction<T, R> {
	/**
	 * Applica questa funzione all'argomento specificato.
	 *
	 * @param t l'argomento della funzione.
	 * @return il risultato della funzione.
	 * @throws Exception se il risultato della funzione non può essere ritornato.
	 */
	R apply(T t) throws Exception;
}