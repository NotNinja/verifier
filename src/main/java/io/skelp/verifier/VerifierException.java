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
package io.skelp.verifier;

/**
 * <p>
 * A runtime exception which is throughout Verifier to avoid consumers having to worry about lots of different types of
 * exceptions.
 * </p>
 *
 * @author Alasdair Mercer
 */
public class VerifierException extends RuntimeException {

    /**
     * <p>
     * Creates a new instance of {@link VerifierException} with no detail message or cause.
     * </p>
     */
    public VerifierException() {
        super();
    }

    /**
     * <p>
     * Creates a new instance of {@link VerifierException} with the detail {@code message} provided but no cause.
     * </p>
     *
     * @param message
     *         the detail message to be used
     */
    public VerifierException(final String message) {
        super(message);
    }

    /**
     * <p>
     * Creates a new instance of {@link VerifierException} with the detail {@code message} and {@code cause} provided.
     * </p>
     *
     * @param message
     *         the detail message to be used
     * @param cause
     *         the {@code Throwable} cause to be used
     */
    public VerifierException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * <p>
     * Creates a new instance of {@link VerifierException} with the {@code cause} provided but no detail message.
     * </p>
     *
     * @param cause
     *         the {@code Throwable} cause to be used
     */
    public VerifierException(final Throwable cause) {
        super(cause);
    }
}
