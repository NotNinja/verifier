/*
 * Copyright (C) 2016 Alasdair Mercer, Skelp
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package io.skelp.verifier.type;

import java.util.ArrayList;
import java.util.List;

import io.skelp.verifier.AbstractCustomVerifier;
import io.skelp.verifier.VerifierException;
import io.skelp.verifier.verification.Verification;

/**
 * <p>
 * An implementation of {@link AbstractCustomVerifier} which can be used to verify a {@code Throwable} value.
 * </p>
 *
 * @author Alasdair Mercer
 */
public final class ThrowableVerifier extends AbstractCustomVerifier<Throwable, ThrowableVerifier> {

    private static List<Throwable> getThrowables(Throwable throwable) {
        final List<Throwable> throwables = new ArrayList<>();
        while (throwable != null && !throwables.contains(throwable)) {
            throwables.add(throwable);
            throwable = throwable.getCause();
        }

        return throwables;
    }

    /**
     * <p>
     * Creates an instance of {@link ThrowableVerifier} based on the {@code verification} provided.
     * </p>
     *
     * @param verification
     *         the {@link Verification} to be used
     */
    public ThrowableVerifier(final Verification<Throwable> verification) {
        super(verification);
    }

    /**
     * <p>
     * Verifies that the value is a checked exception.
     * </p>
     * <pre>
     * Verifier.verify((Throwable) null).checked()           => FAIL
     * Verifier.verify(new Exception()).checked()            => PASS
     * Verifier.verify(new IOException()).checked()          => PASS
     * Verifier.verify(new RuntimeException()).checked()     => FAIL
     * Verifier.verify(new NullPointerException()).checked() => FAIL
     * </pre>
     *
     * @return A reference to this {@link ThrowableVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #unchecked()
     */
    public ThrowableVerifier checked() throws VerifierException {
        final Throwable value = verification().getValue();
        final boolean result = value != null && !(value instanceof RuntimeException);

        verification().check(result, "be checked");

        return this;
    }

    /**
     * <p>
     * Verifies that the value is or has been caused by the {@code Class} provided.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify((Throwable) null).causedBy(*)                                                         => FAIL
     * Verifier.verify(*).causedBy((Class) null)                                                             => FAIL
     * Verifier.verify(new IOException()).causedBy(NullPointerException.class)                               => FAIL
     * Verifier.verify(new IOException()).causedBy(IOException.class)                                        => PASS
     * Verifier.verify(new NullPointerException(new IOException())).causedBy(IllegalArgumentException.class) => FAIL
     * Verifier.verify(new NullPointerException(new IOException())).causedBy(RuntimeException.class)         => PASS
     * Verifier.verify(new NullPointerException(new IOException())).causedBy(IOException.class)              => PASS
     * Verifier.verify(*).causedBy(Throwable.class)                                                          => PASS
     * </pre>
     *
     * @param type
     *         the {@code Class} to which the value or one of its cause must be assignable (may be {@literal null})
     * @return A reference to this {@link ThrowableVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #causedBy(Throwable)
     * @see #causedBy(Throwable, Object)
     */
    public ThrowableVerifier causedBy(final Class<?> type) throws VerifierException {
        final Throwable value = verification().getValue();
        boolean result = false;
        for (final Throwable throwable : getThrowables(value)) {
            if (type.isAssignableFrom(throwable.getClass())) {
                result = true;
                break;
            }
        }

        verification().check(result, "have been caused by '%s'", type);

        return this;
    }

    /**
     * <p>
     * Verifies that the value is or has been caused by the {@code cause} provided.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify((Throwable) null).causedBy(*)                                  => FAIL
     * Verifier.verify(*).causedBy((Throwable) null)                                  => FAIL
     * Verifier.verify(ex = new IOException()).causedBy(new IOException())            => FAIL
     * Verifier.verify(ex = new IOException()).causedBy(ex)                           => PASS
     * Verifier.verify(new NullPointerException(ex = new IOException())).causedBy(ex) => PASS
     * </pre>
     *
     * @param cause
     *         the {@code Throwable} to compare against the value or its causes (may be {@literal null})
     * @return A reference to this {@link ThrowableVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #causedBy(Throwable)
     * @see #causedBy(Throwable, Object)
     */
    public ThrowableVerifier causedBy(final Throwable cause) throws VerifierException {
        return causedBy(cause, cause);
    }

    /**
     * <p>
     * Verifies that the value is or has been caused by the {@code cause} provided while allowing {@code cause} to be
     * given an optional friendlier {@code name} for the {@link VerifierException} message in the event that one is
     * thrown.
     * </p>
     * <p>
     * While {@code name} can be {@literal null} it is recommended instead to either use {@link #causedBy(Throwable)} or
     * pass {@code cause} as {@code name} instead to get a better verification error message.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify((Throwable) null).causedBy(*, *)                                  => FAIL
     * Verifier.verify(*).causedBy(null, *)                                              => FAIL
     * Verifier.verify(ex = new IOException()).causedBy(new IOException(), *)            => FAIL
     * Verifier.verify(ex = new IOException()).causedBy(ex, *)                           => PASS
     * Verifier.verify(new NullPointerException(ex = new IOException())).causedBy(ex, *) => PASS
     * </pre>
     *
     * @param cause
     *         the {@code Throwable} to compare against the value or its causes (may be {@literal null})
     * @param name
     *         the optional name used to represent {@code cause} (may be {@literal null})
     * @return A reference to this {@link ThrowableVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #causedBy(Class)
     * @see #causedBy(Throwable)
     */
    public ThrowableVerifier causedBy(final Throwable cause, final Object name) throws VerifierException {
        final Throwable value = verification().getValue();
        final boolean result = getThrowables(value).contains(cause);

        verification().check(result, "have been caused by '%s'", name);

        return this;
    }

    /**
     * <p>
     * Verifies that the value has the {@code message} provided.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify((Throwable) null).message(*)                                  => FAIL
     * Verifier.verify(new Exception()).message(null)                                => PASS
     * Verifier.verify(new Exception()).message("abc")                               => FAIL
     * Verifier.verify(new Exception("abc")).message(null)                           => FAIL
     * Verifier.verify(new Exception("abc")).message("def")                          => FAIL
     * Verifier.verify(new Exception("abc")).message("abc")                          => PASS
     * Verifier.verify(new Exception("abc")).message("ABC")                          => FAIL
     * </pre>
     *
     * @param message
     *         the message to compare against that of the value (may be {@literal null})
     * @return A reference to this {@link ThrowableVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     */
    public ThrowableVerifier message(final String message) throws VerifierException {
        final Throwable value = verification().getValue();
        final boolean result = value != null && (value.getMessage() == null ? message == null : value.getMessage().equals(message));

        verification().check(result, "have message '%s'", message);

        return this;
    }

    /**
     * <p>
     * Verifies that the value is a unchecked exception.
     * </p>
     * <pre>
     * Verifier.verify((Throwable) null).unchecked()           => FAIL
     * Verifier.verify(new Exception()).unchecked()            => FAIL
     * Verifier.verify(new IOException()).unchecked()          => FAIL
     * Verifier.verify(new RuntimeException()).unchecked()     => PASS
     * Verifier.verify(new NullPointerException()).unchecked() => PASS
     * </pre>
     *
     * @return A reference to this {@link ThrowableVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #checked()
     */
    public ThrowableVerifier unchecked() throws VerifierException {
        final Throwable value = verification().getValue();
        final boolean result = value instanceof RuntimeException;

        verification().check(result, "be unchecked");

        return this;
    }
}
