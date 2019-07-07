package eu.farsil.commons.function;

/**
 * Rappresenta un'azione che può sollevare un'eccezione.
 *
 * @author Marco Buzzanca
 */
@FunctionalInterface
public interface ThrowingRunnable {
	/**
	 * Esegue un'azione.
	 *
	 * @throws Exception se l'azione non può essere portata a termine.
	 */
	void run() throws Exception;
}
