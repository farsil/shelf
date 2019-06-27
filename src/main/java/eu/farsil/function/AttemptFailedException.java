package eu.farsil.function;

import java.util.Objects;

public class AttemptFailedException extends RuntimeException {
	AttemptFailedException(final Throwable cause) {
		super(Objects.requireNonNull(cause));
	}
}
