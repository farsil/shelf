package eu.farsil.function;

import java.util.Objects;
import java.util.function.Function;

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
	 * Ritorna una funzione composta che applica questa funzione all'argomento
	 * in input, e in seguito la funzione specificata al suo risultato.
	 * <p/>
	 * Chiamata <em>f</em> questa funzione e <em>g</em> la funzione specificata
	 * questo metodo ritorna <em>g ◦ f</em>.
	 *
	 * @param after la funzione da richiamare sull'output di questa funzione.
	 * @param <V> il tipo di ritorno della funzione specificata.
	 * @return la funzione composta.
	 * @throws NullPointerException se after è {@code null}.
	 */
	default <V> ThrowingFunction<T, V> andThen(
			final ThrowingFunction<? super R, ? extends V> after) {
		Objects.requireNonNull(after);
		return t -> after.apply(apply(t));
	}

	/**
	 * Applica questa funzione all'argomento specificato.
	 *
	 * @param t l'argomento della funzione.
	 * @return il risultato della funzione.
	 * @throws Exception se il risultato della funzione non può essere ritornato.
	 */
	R apply(T t) throws Exception;

	/**
	 * Ritorna una funzione composta che applica questa funzione all'argomento
	 * in input, e in seguito la funzione specificata al suo risultato.
	 * <p/>
	 * Chiamata <em>f</em> questa funzione e <em>g</em> la funzione specificata
	 * questo metodo ritorna <em>g ◦ f</em>.
	 *
	 * @param after la funzione da richiamare sull'output di questa funzione.
	 * @param <V> il tipo di ritorno della funzione specificata.
	 * @return la funzione composta.
	 * @throws NullPointerException se after è {@code null}.
	 */
	default <V> ThrowingFunction<T, V> andThen(
			final Function<? super R, ? extends V> after) {
		Objects.requireNonNull(after);
		return t -> after.apply(apply(t));
	}

	/**
	 * Ritorna una funzione composta che applica la funzione specificata
	 * all'argomento in input, e in seguito questa funzione al suo risultato.
	 * <p/>
	 * Chiamata <em>f</em> questa funzione e <em>g</em> la funzione specificata
	 * questo metodo ritorna <em>f ◦ g</em>.
	 *
	 * @param before la funzione da applicare prima che questa funzione si
	 * applicata.
	 * @param <V> il tipo dell'argomento della funzione specificata.
	 * @return la funzione composta.
	 * @throws NullPointerException se after è {@code null}.
	 */
	default <V> ThrowingFunction<V, R> compose(
			final ThrowingFunction<? super V, ? extends T> before) {
		Objects.requireNonNull(before);
		return v -> apply(before.apply(v));
	}

	/**
	 * Ritorna una funzione composta che applica la funzione specificata
	 * all'argomento in input, e in seguito questa funzione al suo risultato.
	 * <p/>
	 * Chiamata <em>f</em> questa funzione e <em>g</em> la funzione specificata
	 * questo metodo ritorna <em>f ◦ g</em>.
	 *
	 * @param before la funzione da applicare prima che questa funzione si
	 * applicata.
	 * @param <V> il tipo dell'argomento della funzione specificata.
	 * @return la funzione composta.
	 * @throws NullPointerException se after è {@code null}.
	 */
	default <V> ThrowingFunction<V, R> compose(
			final Function<? super V, ? extends T> before) {
		Objects.requireNonNull(before);
		return v -> apply(before.apply(v));
	}
}