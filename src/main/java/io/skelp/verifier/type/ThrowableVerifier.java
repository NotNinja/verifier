/*
 * Copyright (C) 2017 Alasdair Mercer, Skelp
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
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import io.skelp.verifier.AbstractCustomVerifier;
import io.skelp.verifier.VerifierException;
import io.skelp.verifier.message.MessageKey;
import io.skelp.verifier.verification.Verification;

/**
 * <p>
 * An implementation of {@link AbstractCustomVerifier} which can be used to verify a {@code Throwable} value.
 * </p>
 *
 * @author Alasdair Mercer
 */
public final class ThrowableVerifier extends AbstractCustomVerifier<Throwable, ThrowableVerifier> {

    private static boolean isCausedBy(final Collection<Class<?>> throwableTypes, final Class<?> type) {
        return throwableTypes.stream()
            .anyMatch(type::isAssignableFrom);
    }

    private static boolean isCausedBy(final Collection<Throwable> throwables, final Throwable throwable) {
        return throwables.contains(throwable);
    }

    private static List<Throwable> getThrowables(Throwable throwable) {
        final List<Throwable> throwables = new ArrayList<>();
        while (throwable != null && !throwables.contains(throwable)) {
            throwables.add(throwable);
            throwable = throwable.getCause();
        }

        return throwables;
    }

    private static Set<Class<?>> getThrowableTypes(final Throwable throwable) {
        return getThrowables(throwable).stream()
            .map(Throwable::getClass)
            .collect(Collectors.toSet());
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
     * Verifies that the value is or has been caused by a throwable of the {@code type} provided.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify((Throwable) null).causedBy(*)                                                         =&gt; FAIL
     * Verifier.verify(*).causedBy((Class) null)                                                             =&gt; FAIL
     * Verifier.verify(new IOException()).causedBy(NullPointerException.class)                               =&gt; FAIL
     * Verifier.verify(new IOException()).causedBy(IOException.class)                                        =&gt; PASS
     * Verifier.verify(new NullPointerException(new IOException())).causedBy(IllegalArgumentException.class) =&gt; FAIL
     * Verifier.verify(new NullPointerException(new IOException())).causedBy(RuntimeException.class)         =&gt; PASS
     * Verifier.verify(new NullPointerException(new IOException())).causedBy(IOException.class)              =&gt; PASS
     * Verifier.verify(*).causedBy(Throwable.class)                                                          =&gt; PASS
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
    public ThrowableVerifier causedBy(final Class<?> type) {
        final Throwable value = verification().getValue();
        final boolean result = isCausedBy(getThrowableTypes(value), type);

        verification().report(result, MessageKeys.CAUSED_BY, type);

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
     * Verifier.verify((Throwable) null).causedBy(*)                                       =&gt; FAIL
     * Verifier.verify(*).causedBy((Throwable) null)                                       =&gt; FAIL
     * Verifier.verify(ex = new IOException("foo")).causedBy(new IOException())            =&gt; FAIL
     * Verifier.verify(ex = new IOException("foo")).causedBy(ex)                           =&gt; PASS
     * Verifier.verify(new NullPointerException(ex = new IOException("foo"))).causedBy(ex) =&gt; PASS
     * </pre>
     *
     * @param cause
     *         the {@code Throwable} to compare against the value or its causes (may be {@literal null})
     * @return A reference to this {@link ThrowableVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #causedBy(Class)
     * @see #causedBy(Throwable, Object)
     */
    public ThrowableVerifier causedBy(final Throwable cause) {
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
     * Verifier.verify((Throwable) null).causedBy(*, *)                                       =&gt; FAIL
     * Verifier.verify(*).causedBy(null, *)                                                   =&gt; FAIL
     * Verifier.verify(ex = new IOException("foo")).causedBy(new IOException(), *)            =&gt; FAIL
     * Verifier.verify(ex = new IOException("foo")).causedBy(ex, *)                           =&gt; PASS
     * Verifier.verify(new NullPointerException(ex = new IOException("foo"))).causedBy(ex, *) =&gt; PASS
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
    public ThrowableVerifier causedBy(final Throwable cause, final Object name) {
        final Throwable value = verification().getValue();
        final boolean result = isCausedBy(getThrowables(value), cause);

        verification().report(result, MessageKeys.CAUSED_BY, name);

        return this;
    }

    /**
     * <p>
     * Verifies that the value is or has been caused by a throwable of <b>all</b> of the {@code types} provided.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify(*).causedByAll()                                                                                                             =&gt; PASS
     * Verifier.verify(*).causedByAll((Class[]) null)                                                                                               =&gt; PASS
     * Verifier.verify(*).causedByAll(*, null)                                                                                                      =&gt; FAIL
     * Verifier.verify((Throwable) null).causedByAll(*)                                                                                             =&gt; FAIL
     * Verifier.verify(new IOException()).causedByAll(IOException.class, Exception.class)                                                           =&gt; PASS
     * Verifier.verify(new IOException()).causedByAll(IOException.class, IllegalArgumentException.class)                                            =&gt; FAIL
     * Verifier.verify(new NullPointerException(new IOException())).causedByAll(IOException.class, NullPointerException.class, Exception.class)     =&gt; PASS
     * Verifier.verify(new NullPointerException(new IOException())).causedByAll(IOException.class, IllegalArgumentException.class, Exception.class) =&gt; FAIL
     * </pre>
     *
     * @param types
     *         the classes to which the value or one of its cause must be assignable (may be {@literal null} or contain
     *         {@literal null} references)
     * @return A reference to this {@link ThrowableVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @since 0.2.0
     */
    public ThrowableVerifier causedByAll(final Class<?>... types) {
        final Throwable value = verification().getValue();
        final Set<Class<?>> throwableTypes = getThrowableTypes(value);
        final boolean result = matchAll(types, input -> input != null && isCausedBy(throwableTypes, input));

        verification().report(result, MessageKeys.CAUSED_BY_ALL, (Object) types);

        return this;
    }

    /**
     * <p>
     * Verifies that the value is or has been caused by a throwable of <b>any</b> of the {@code types} provided.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify(*).causedByAny()                                                                                                                                            =&gt; FAIL
     * Verifier.verify(*).causedByAny((Class[]) null)                                                                                                                              =&gt; FAIL
     * Verifier.verify(*).causedByAny(*, Throwable.class)                                                                                                                          =&gt; PASS
     * Verifier.verify((Throwable) null).causedByAny(*)                                                                                                                            =&gt; FAIL
     * Verifier.verify(new IOException()).causedByAny(IllegalArgumentException.class, IOException.class)                                                                           =&gt; PASS
     * Verifier.verify(new IOException()).causedByAny(IllegalArgumentException.class, NullPointerException.class)                                                                  =&gt; FAIL
     * Verifier.verify(new NullPointerException(new IOException())).causedByAny(IllegalArgumentException.class, IllegalStateException.class, IOException.class)                    =&gt; PASS
     * Verifier.verify(new NullPointerException(new IOException())).causedByAny(ArrayIndexOutOfBoundsException.class, IllegalArgumentException.class, IllegalStateException.class) =&gt; FAIL
     * </pre>
     *
     * @param types
     *         the classes to which the value or one of its cause must be assignable (may be {@literal null} or contain
     *         {@literal null} references)
     * @return A reference to this {@link ThrowableVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #causedByAny(Throwable...)
     * @since 0.2.0
     */
    public ThrowableVerifier causedByAny(final Class<?>... types) {
        final Throwable value = verification().getValue();
        final Set<Class<?>> throwableTypes = getThrowableTypes(value);
        final boolean result = matchAny(types, input -> input != null && isCausedBy(throwableTypes, input));

        verification().report(result, MessageKeys.CAUSED_BY_ANY, (Object) types);

        return this;
    }

    /**
     * <p>
     * Verifies that the value is or has been caused by the <b>any</b> {@code causes} provided.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify(*).causedByAny()                                                                                                      =&gt; FAIL
     * Verifier.verify(*).causedByAny((Throwable[]) null)                                                                                    =&gt; FAIL
     * Verifier.verify((Throwable) null).causedByAny(*)                                                                                      =&gt; FAIL
     * Verifier.verify(ex = new IOException("foo")).causedByAny(new IllegalArgumentException(), ex)                                          =&gt; PASS
     * Verifier.verify(ex = new IOException("foo")).causedByAny(new IllegalArgumentException(), new IOException())                           =&gt; FAIL
     * Verifier.verify(new NullPointerException(ex = new IOException("foo"))).causedByAny(new IllegalArgumentException(), ex)                =&gt; PASS
     * Verifier.verify(new NullPointerException(ex = new IOException("foo"))).causedByAny(new IllegalArgumentException(), new IOException()) =&gt; FAIL
     * </pre>
     *
     * @param causes
     *         the {@code Throwables} to compare against the value or its causes (may be {@literal null} or contain
     *         {@literal null} references)
     * @return A reference to this {@link ThrowableVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #causedByAny(Class...)
     * @since 0.2.0
     */
    public ThrowableVerifier causedByAny(final Throwable... causes) {
        final Throwable value = verification().getValue();
        final List<Throwable> throwables = getThrowables(value);
        final boolean result = matchAny(causes, input -> isCausedBy(throwables, input));

        verification().report(result, MessageKeys.CAUSED_BY_ANY, (Object) causes);

        return this;
    }

    /**
     * <p>
     * Verifies that the value is a checked exception.
     * </p>
     * <pre>
     * Verifier.verify((Throwable) null).checked()           =&gt; FAIL
     * Verifier.verify(new Exception()).checked()            =&gt; PASS
     * Verifier.verify(new IOException()).checked()          =&gt; PASS
     * Verifier.verify(new RuntimeException()).checked()     =&gt; FAIL
     * Verifier.verify(new NullPointerException()).checked() =&gt; FAIL
     * </pre>
     *
     * @return A reference to this {@link ThrowableVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #unchecked()
     */
    public ThrowableVerifier checked() {
        final Throwable value = verification().getValue();
        final boolean result = value != null && !(value instanceof RuntimeException);

        verification().report(result, MessageKeys.CHECKED);

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
     * Verifier.verify((Throwable) null).message(*)                                  =&gt; FAIL
     * Verifier.verify(new Exception()).message(null)                                =&gt; PASS
     * Verifier.verify(new Exception()).message("abc")                               =&gt; FAIL
     * Verifier.verify(new Exception("abc")).message(null)                           =&gt; FAIL
     * Verifier.verify(new Exception("abc")).message("def")                          =&gt; FAIL
     * Verifier.verify(new Exception("abc")).message("abc")                          =&gt; PASS
     * Verifier.verify(new Exception("abc")).message("ABC")                          =&gt; FAIL
     * </pre>
     *
     * @param message
     *         the message to compare against that of the value (may be {@literal null})
     * @return A reference to this {@link ThrowableVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     */
    public ThrowableVerifier message(final String message) {
        final Throwable value = verification().getValue();
        final boolean result = value != null && (value.getMessage() == null ? message == null : value.getMessage().equals(message));

        verification().report(result, MessageKeys.MESSAGE, message);

        return this;
    }

    /**
     * <p>
     * Verifies that the value is a unchecked exception.
     * </p>
     * <pre>
     * Verifier.verify((Throwable) null).unchecked()           =&gt; FAIL
     * Verifier.verify(new Exception()).unchecked()            =&gt; FAIL
     * Verifier.verify(new IOException()).unchecked()          =&gt; FAIL
     * Verifier.verify(new RuntimeException()).unchecked()     =&gt; PASS
     * Verifier.verify(new NullPointerException()).unchecked() =&gt; PASS
     * </pre>
     *
     * @return A reference to this {@link ThrowableVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #checked()
     */
    public ThrowableVerifier unchecked() {
        final Throwable value = verification().getValue();
        final boolean result = value instanceof RuntimeException;

        verification().report(result, MessageKeys.UNCHECKED);

        return this;
    }

    /**
     * <p>
     * The {@link MessageKey MessageKeys} that are used by {@link ThrowableVerifier}.
     * </p>
     *
     * @since 0.2.0
     */
    enum MessageKeys implements MessageKey {

        CAUSED_BY("io.skelp.verifier.type.ThrowableVerifier.causedBy"),
        CAUSED_BY_ALL("io.skelp.verifier.type.ThrowableVerifier.causedByAll"),
        CAUSED_BY_ANY("io.skelp.verifier.type.ThrowableVerifier.causedByAny"),
        CHECKED("io.skelp.verifier.type.ThrowableVerifier.checked"),
        MESSAGE("io.skelp.verifier.type.ThrowableVerifier.message"),
        UNCHECKED("io.skelp.verifier.type.ThrowableVerifier.unchecked");

        private final String code;

        MessageKeys(final String code) {
            this.code = code;
        }

        @Override
        public String code() {
            return code;
        }
    }
}
