package eu.farsil.function;

import java.util.Objects;
import java.util.function.Consumer;

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
	 * Ritorna un'operazione composta che applica questa operazione e quella
	 * specificata in sequenza all'argomento in input.
	 *
	 * @param after l'operazione da richiamare dopo questa operazione.
	 * @return l'operazione composta.
	 * @throws NullPointerException se after è {@code null}.
	 */
	default ThrowingConsumer<T> andThen(final Consumer<? super T> after) {
		Objects.requireNonNull(after);
		return t -> {
			accept(t);
			after.accept(t);
		};
	}

	/**
	 * Effettua quest'operazione sull'argomento specificato.
	 *
	 * @param t l'argomento specificato.
	 * @throws Exception se l'operazione non può essere applicata all'argomento
	 * specificato.
	 */
	void accept(T t) throws Exception;

	/**
	 * Ritorna un'operazione composta che applica questa operazione e quella
	 * specificata in sequenza all'argomento in input.
	 *
	 * @param after l'operazione da richiamare dopo questa operazione.
	 * @return l'operazione composta.
	 * @throws NullPointerException se after è {@code null}.
	 */
	default ThrowingConsumer<T> andThen(final ThrowingConsumer<? super T> after) {
		Objects.requireNonNull(after);
		return t -> {
			accept(t);
			after.accept(t);
		};
	}
}
