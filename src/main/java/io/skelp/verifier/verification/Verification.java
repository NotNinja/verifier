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
import io.skelp.verifier.message.MessageKey;
import io.skelp.verifier.message.MessageSource;
import io.skelp.verifier.message.locale.LocaleContext;
import io.skelp.verifier.verification.report.AssertionReporter;
import io.skelp.verifier.verification.report.ReportExecutor;
import io.skelp.verifier.verification.report.Reporter;

/**
 * <p>
 * Contains contextual information for a verification and also provides verifiers with a clean and simply way of
 * checking the results of their verifications without having to handle negation, throwing errors, or message lookup
 * and/or formatting.
 * </p>
 * <p>
 * A {@code Verification} encapsulates a single value which may well be verified multiple times.
 * </p>
 *
 * @param <T>
 *         the type of the value being verified
 * @author Alasdair Mercer
 */
public interface Verification<T> {

    /**
     * <p>
     * Reports the specified {@code result}, which may determine whether it passes verification.
     * </p>
     * <p>
     * The {@code result} will pass depending on the {@link Reporter Reporters}. That said; generally, the default
     * reporter ({@link AssertionReporter}) will pass {@code result} if it is {@literal true} and this
     * {@link Verification} has not be negated or if it has been negated and {@code result} is {@literal false}. If it
     * does not pass, a {@link VerifierException} will be thrown with a suitable localized message, which can be
     * enhanced by providing an optional {@code key} and format {@code args}.
     * </p>
     * <p>
     * This {@link Verification} will no longer be negated as a result of calling this method, regardless of whether
     * {@code result} passes verification.
     * </p>
     *
     * @param result
     *         the result of the verification
     * @param key
     *         the optional {@link MessageKey} which provides a more detailed localized explanation of what was
     *         verified
     * @param args
     *         the optional format arguments which are used to format the localized message
     * @return A reference to this {@link Verification} for chaining purposes.
     * @throws VerifierException
     *         If any {@link Reporter} deems that {@code result} should not pass verification.
     * @see #report(boolean, String, Object...)
     * @since 0.2.0
     */
    Verification<T> report(boolean result, MessageKey key, Object... args);

    /**
     * <p>
     * Reports the specified {@code result}, which may determine whether it passes verification.
     * </p>
     * <p>
     * The {@code result} will pass depending on the {@link Reporter Reporters}. That said; generally, the default
     * reporter ({@link AssertionReporter}) will pass {@code result} if it is {@literal true} and this
     * {@link Verification} has not be negated or if it has been negated and {@code result} is {@literal false}. If it
     * does not pass, a {@link VerifierException} will be thrown with a suitable localized message, which can be
     * enhanced by providing an optional (unlocalized) {@code message} and format {@code args}.
     * </p>
     * <p>
     * This {@link Verification} will no longer be negated as a result of calling this method, regardless of whether
     * {@code result} passes verification.
     * </p>
     *
     * @param result
     *         the result of the verification
     * @param message
     *         the optional message which provides a more detailed explanation of what was verified
     * @param args
     *         the optional format arguments which are used to format {@code message}
     * @return A reference to this {@link Verification} for chaining purposes.
     * @throws VerifierException
     *         If any {@link Reporter} deems that {@code result} should not pass verification.
     * @see #report(boolean, MessageKey, Object...)
     * @since 0.2.0
     */
    Verification<T> report(boolean result, String message, Object... args);

    /**
     * <p>
     * Returns the {@link LocaleContext} that is being used by this {@link Verification} to lookup and format messages
     * for any {@link VerifierException VerifierExceptions} that are thrown by a {@link Reporter}.
     * </p>
     *
     * @return The {@link LocaleContext}.
     * @since 0.2.0
     */
    LocaleContext getLocaleContext();

    /**
     * <p>
     * Returns the message source that is being used by this {@link Verification} to lookup and/or format the messages
     * for any {@link VerifierException VerifierExceptions} that are thrown by a {@link Reporter}.
     * </p>
     *
     * @return The {@link MessageSource}.
     * @since 0.2.0
     */
    MessageSource getMessageSource();

    /**
     * <p>
     * Returns the optional name used to represent the value for this {@link Verification}.
     * </p>
     *
     * @return The name representation for the value or {@literal null} if none was provided.
     */
    Object getName();

    /**
     * <p>
     * Returns whether the next result that is passed to {@link #report} is negated for this {@link Verification}.
     * </p>
     *
     * @return {@literal true} if the next result will be negated; otherwise {@literal false}.
     */
    boolean isNegated();

    /**
     * <p>
     * Sets whether the next result that is passed to {@link #report} is negated for this {@link Verification} to
     * {@code negated}.
     * </p>
     *
     * @param negated
     *         {@literal true} to negate the next result; otherwise {@literal false}
     */
    void setNegated(boolean negated);

    /**
     * <p>
     * Returns the report executor that is being used by this {@link Verification} to execute {@link Reporter Reporters}
     * for a verification result.
     * </p>
     *
     * @return The {@link ReportExecutor}.
     * @since 0.2.0
     */
    ReportExecutor getReportExecutor();

    /**
     * <p>
     * Returns the value for this {@link Verification}.
     * </p>
     *
     * @return The value being verified which may be {@literal null}.
     */
    T getValue();
}
