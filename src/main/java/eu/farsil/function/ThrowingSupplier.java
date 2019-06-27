package eu.farsil.function;

/**
 * Rappresenta un fornitore di risultati che può sollevare eccezioni.
 *
 * @param <T> il tipo di risultati che può essere fornito.
 * @author Marco Buzzanca (Accenture)
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

	/**
	 * Ritorna un {@link Try} costruito invocando questo {@link
	 * ThrowingSupplier}.
	 *
	 * @return un {@code Try} costruito invocando questo {@code
	 * ThrowingSupplier}.
	 */
	default Result<T> tryGet() {
		// this = this::get since it's a functional interface
		return Try.get(this);
	}
}
