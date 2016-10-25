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
package io.skelp.verifier.verification;

import io.skelp.verifier.VerifierException;
import io.skelp.verifier.message.MessageFormatter;

/**
 * Contains contextual information for a verification and also provides verifiers with a clean and simply way of
 * checking the results of their verifications without having to handle negation, throwing errors, or message
 * formatting.
 * <p>
 * A {@code Verification} encapsulates a single value which may well be verified multiple times.
 *
 * @param <T>
 *         the type of the value being verified
 * @author Alasdair Mercer
 */
public interface Verification<T> {

    /**
     * Checks the specified {@code result} to determine whether it passes verification.
     * <p>
     * {@code result} will pass it is {@literal true} and this {@link Verification} has not be negated or if it has been
     * negated and {@code result} is {@literal false}. If it does not pass, a {@link VerifierException} will be thrown
     * with a suitable message, which can be enhanced using the optional {@code message} and format {@code args}
     * provided.
     * <p>
     * This {@link Verification} will no longer be negated as a result of calling this method, regardless of whether
     * {@code result} passes verification.
     *
     * @param result
     *         the result of the verification
     * @param message
     *         the optional message which provides a (slightly) more detailed explanation of what was verified
     * @param args
     *         the optional format arguments which are only used to format {@code message}
     * @return A reference to this {@link Verification} for chaining purposes.
     * @throws VerifierException
     *         If {@code result} does not pass verification.
     */
    Verification<T> check(boolean result, String message, Object... args) throws VerifierException;

    /**
     * Returns the message formatter that is being used by this {@link Verification} to format the messages for any
     * {@link VerifierException VerifierExceptions} that are thrown by {@link #check(boolean, String, Object...)}.
     *
     * @return The {@link MessageFormatter}.
     * @throws VerifierException
     *         If a problem occurs while trying to retrieve the {@link MessageFormatter}.
     */
    MessageFormatter getMessageFormatter() throws VerifierException;

    /**
     * Returns the optional name used to represent the value for this {@link Verification}.
     *
     * @return The name representation for the value or {@literal null} if none was provided.
     */
    Object getName();

    /**
     * Returns whether the next result that is passed to {@link #check(boolean, String, Object...)} is negated for this
     * {@link Verification}.
     *
     * @return {@literal true} if the next result will be negated; otherwise {@literal false}.
     */
    boolean isNegated();

    /**
     * Sets whether the next result that is passed to {@link #check(boolean, String, Object...)} is negated for this
     * {@link Verification} to {@code negated}.
     *
     * @param negated
     *         {@literal true} to negate the next result; otherwise {@literal false}
     */
    void setNegated(boolean negated);

    /**
     * Returns the value for this {@link Verification}.
     *
     * @return The value being verified which may be {@literal null}.
     */
    T getValue();
}
