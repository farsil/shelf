package eu.farsil.commons.function;

/**
 * Rappresenta un'operazione che accetta un argomento in input, non restituisce
 * alcun risultato, e può sollevare un'eccezione.
 *
 * @param <T> il tipo dell'argomento dell'operazione.
 * @author Marco Buzzanca
 */
@FunctionalInterface
public interface ThrowingConsumer<T> {
	/**
	 * Effettua quest'operazione sull'argomento specificato.
	 *
	 * @param t l'argomento specificato.
	 * @throws Exception se l'operazione non può essere applicata all'argomento
	 * specificato.
	 */
	void accept(T t) throws Exception;
}
