package eu.farsil.commons.function;

/**
 * Rappresenta un fornitore di risultati che può sollevare eccezioni.
 *
 * @param <T> il tipo di risultati che può essere fornito.
 * @author Marco Buzzanca
 */
@FunctionalInterface
public interface ThrowingSupplier<T> {
	/**
	 * Ritorna un rsultato.
	 *
	 * @return il risultato.
	 * @throws Exception se il risultato non può essere ritornato.
	 */
	T get() throws Exception;
}
