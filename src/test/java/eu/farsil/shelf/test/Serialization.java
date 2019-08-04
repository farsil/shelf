package eu.farsil.shelf.test;

import java.io.*;

/**
 * Class that serializes/deserializes objects based on the standard java
 * serialization mechanism.
 *
 * @author Marco Buzzanca
 */
public final class Serialization {
	/**
	 * Utility class.
	 */
	private Serialization() {
		throw new AssertionError();
	}

	/**
	 * Deserializes an instance of the expected class from a byte array source.
	 *
	 * @param expectedClass the expected class.
	 * @param bytes the byte array.
	 * @param <T> the type of the object to be deserialized.
	 * @return the deserialized object.
	 * @throws IOException if the object cannot be deserialized.
	 * @throws ClassNotFoundException if the class of the object to be
	 * deserialized cannot be found.
	 * @throws ClassCastException if the deserialized object cannot be cast
	 * to the expected class.
	 */
	public static <T> T fromByteArray(final Class<T> expectedClass,
			final byte[] bytes) throws IOException, ClassNotFoundException {
		return fromInputStream(expectedClass, new ByteArrayInputStream(bytes));
	}

	/**
	 * Serializes the specified object into a byte array.
	 *
	 * @param object the object to be serialized.
	 * @return the byte array that contains the serialized object.
	 * @throws IOException if the object cannot be serialized.
	 */
	public static byte[] toByteArray(final Object object) throws IOException {
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		toOutputStream(object, out);
		return out.toByteArray();
	}

	/**
	 * Deserializes an instance of the expected class from an
	 * {@link InputStream}.
	 *
	 * @param expectedClass the expected class.
	 * @param in {@code InputStream}.
	 * @param <T> the type of the object to be deserialized.
	 * @return the deserialized object.
	 * @throws IOException if the object cannot be deserialized.
	 * @throws ClassNotFoundException if the class of the object to be
	 * deserialized cannot be found.
	 * @throws ClassCastException if the deserialized object cannot be cast
	 * to the expected class.
	 */
	private static <T> T fromInputStream(final Class<T> expectedClass,
			final InputStream in) throws IOException, ClassNotFoundException {
		try (final ObjectInputStream stream = new ObjectInputStream(in)) {
			return expectedClass.cast(stream.readObject());
		}
	}

	/**
	 * Serializes the specified object into an {@link OutputStream}.
	 *
	 * @param object the object to be serialized.
	 * @param out the {@code OutputStream} where the object will be written.
	 * @throws IOException if the object cannot be serialized.
	 */
	private static void toOutputStream(final Object object,
			final OutputStream out) throws IOException {
		try (final ObjectOutputStream stream = new ObjectOutputStream(out)) {
			stream.writeObject(object);
		}
	}
}
