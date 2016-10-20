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
 * TODO: Document
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
     * TODO: Document
     *
     * @param verification
     */
    public ThrowableVerifier(final Verification<Throwable> verification) {
        super(verification);
    }

    /**
     * TODO: Document
     *
     * @return
     * @throws VerifierException
     */
    public ThrowableVerifier checked() throws VerifierException {
        final Throwable value = getVerification().getValue();
        final boolean result = value != null && !(value instanceof RuntimeException);

        getVerification().check(result, "be checked");

        return this;
    }

    /**
     * TODO: Document
     *
     * @param type
     * @return
     * @throws VerifierException
     */
    public ThrowableVerifier cause(final Class<?> type) throws VerifierException {
        final Throwable value = getVerification().getValue();
        boolean result = false;
        for (final Throwable throwable : getThrowables(value)) {
            if (type.isAssignableFrom(throwable.getClass())) {
                result = true;
                break;
            }
        }

        getVerification().check(result, "have been caused by '%s'", type);

        return this;
    }

    /**
     * TODO: Document
     *
     * @param cause
     * @return
     * @throws VerifierException
     */
    public ThrowableVerifier cause(final Throwable cause) throws VerifierException {
        return cause(cause, cause);
    }

    /**
     * TODO: Document
     *
     * @param cause
     * @param name
     * @return
     * @throws VerifierException
     */
    public ThrowableVerifier cause(final Throwable cause, final Object name) throws VerifierException {
        final Throwable value = getVerification().getValue();
        final boolean result = getThrowables(value).contains(cause);

        getVerification().check(result, "have been caused by '%s'", name);

        return this;
    }

    /**
     * TODO: Document
     *
     * @param message
     * @return
     * @throws VerifierException
     */
    public ThrowableVerifier message(final String message) throws VerifierException {
        final Throwable value = getVerification().getValue();
        final boolean result = value != null && (value.getMessage() == null ? message == null : value.getMessage().equals(message));

        getVerification().check(result, "have message '%s'", message);

        return this;
    }

    /**
     * TODO: Document
     *
     * @return
     * @throws VerifierException
     */
    public ThrowableVerifier unchecked() throws VerifierException {
        final Throwable value = getVerification().getValue();
        final boolean result = value instanceof RuntimeException;

        getVerification().check(result, "be unchecked");

        return this;
    }
}
