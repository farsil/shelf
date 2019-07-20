package eu.farsil.commons.util;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;

/**
 * Exception that is intended to be thrown when a {@code Predicate} or {@code
 * ThrowingPredicate} fails.
 *
 * @author Marco Buzzanca
 * @see java.util.function.Predicate
 * @see eu.farsil.commons.function.ThrowingPredicate
 */
public class PredicateFailedException extends RuntimeException {
	/**
	 * Required by {@code java.lang.Serializable}.
	 */
	private static final long serialVersionUID = -712482672060994332L;

	/**
	 * The value that failed to match the predicate. Its serialization is
	 * handled by {@code SerializationProxy}.
	 */
	private final transient Object value;

	/**
	 * Builds an instance that holds the value that failed to match the
	 * predicate.
	 *
	 * @param value the value that failed to match the predicate.
	 */
	public PredicateFailedException(final Object value) {
		super("value: " + value);
		this.value = value;
	}

	/**
	 * Returns the value that failed to match the predicate. Returns
	 * {@code null} if this exception instance was unserialized with the
	 * standard mechanism, and the value did not implement {@code Serializable}.
	 *
	 * @return the value that failed to match the predicate.
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Protects against serialization attacks. Real serialization is
	 * handled by {@code writeReplace()}.
	 *
	 * @param stream unused.
	 * @throws InvalidObjectException always.
	 */
	private void readObject(final ObjectInputStream stream)
			throws InvalidObjectException {
		throw new InvalidObjectException("Proxy required");
	}

	/**
	 * Serializes this instance using the serialization proxy.
	 */
	private Object writeReplace() {
		return new SerializationProxy(this);
	}

	/**
	 * Serialization Proxy.
	 *
	 * @author Marco Buzzanca
	 */
	private static class SerializationProxy implements Serializable {
		/**
		 * Required by {@code java.lang.Serializable}.
		 */
		private static final long serialVersionUID = 3975615527852594170L;

		/**
		 * The value to serialize.
		 */
		private final Serializable value;

		/**
		 * Constructor.
		 *
		 * @param obj the object to serialize.
		 */
		private SerializationProxy(final PredicateFailedException obj) {
			this.value = obj.value instanceof Serializable
					? (Serializable) obj.value
					: null;
		}

		/**
		 * Builds a {@code PredicateFailedException} out of the deserialized
		 * value.
		 *
		 * @return the deserialized exception.
		 */
		private Object readResolve() {
			return new PredicateFailedException(value);
		}
	}
}
