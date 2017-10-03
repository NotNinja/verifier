/*
 * Copyright (C) 2017 Alasdair Mercer, !ninja
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
package org.notninja.verifier.verification;

import java.util.Locale;

import org.notninja.verifier.VerifierException;
import org.notninja.verifier.message.MessageKey;
import org.notninja.verifier.message.MessageSource;
import org.notninja.verifier.message.NoSuchMessageException;
import org.notninja.verifier.message.formatter.Formatter;
import org.notninja.verifier.message.formatter.FormatterProvider;
import org.notninja.verifier.message.locale.LocaleContext;
import org.notninja.verifier.verification.report.AssertionReporter;
import org.notninja.verifier.verification.report.MessageHolder;
import org.notninja.verifier.verification.report.ReportExecutor;
import org.notninja.verifier.verification.report.Reporter;

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
     * Creates a copy of this {@link Verification} but using the {@code value} and optional {@code name} provided.
     * </p>
     * <p>
     * The current negated state of this {@link Verification} is <b>not</b> copied.
     * </p>
     *
     * @param value
     *         the new value being verified
     * @param name
     *         the new optional name used to represent {@code value}
     * @param <V>
     *         the type of the new {@code value} being verified
     * @return A copy for {@code value} and {@code name}.
     * @since 0.2.0
     */
    <V> Verification<V> copy(V value, Object name);

    /**
     * <p>
     * Returns a formatter that can be used to format the specified object so that it is more human-friendly.
     * </p>
     * <p>
     * This method will return the first {@link Formatter} that supports {@code obj} and will return {@literal null} if
     * none support {@code obj} or if {@code obj} is {@literal null}.
     * </p>
     * <p>
     * Generally, this method simply acts as a convenient proxy for {@link FormatterProvider#getFormatter(Object)}.
     * </p>
     *
     * @param obj
     *         the object for which a supporting {@link Formatter} is to be returned (may be {@literal null})
     * @return A {@link Formatter} that supports {@code obj} or {@literal null} if none was found or {@code obj} is
     * {@literal null}.
     * @since 0.2.0
     */
    Formatter getFormatter(Object obj);

    /**
     * <p>
     * Returns the message for this {@link Verification}, optionally using the {@code key} and format {@code args}
     * provided.
     * </p>
     * <p>
     * Where {@code key} is provided, it is used to lookup a localized message which may also be formatted using the
     * arguments provided. Using {@link MessageKey} is the ideal approach as it can help to localize messages which aids
     * consumers and their users.
     * </p>
     * <p>
     * Generally, this method simply acts as a convenient proxy for
     * {@link MessageSource#getMessage(Verification, MessageKey, Object[])}.
     * </p>
     *
     * @param key
     *         the optional {@link MessageKey} which provides a more detailed localized explanation of what was verified
     * @param args
     *         the optional format arguments which are used to format localized message
     * @return The localized message.
     * @throws IllegalArgumentException
     *         If the localized message is formatted but is an invalid format pattern or any of the format {@code args}
     *         are invalid for their placeholders.
     * @throws NoSuchMessageException
     *         If no message could be found for {@code key} or any other that is required to build the full message.
     * @see #getMessage(String, Object...)
     * @since 0.2.0
     */
    String getMessage(MessageKey key, Object... args);

    /**
     * <p>
     * Returns the message for this {@link Verification}, optionally using the {@code message} and format {@code args}
     * provided.
     * </p>
     * <p>
     * While the context of the message should still be localized by the implementation, this method is intended for
     * cases where {@code message} is not pre-defined and is most likely provided by consumers. Ideally, all messages
     * should be localized and can be referenced via a {@link MessageKey}.
     * </p>
     * <p>
     * Generally, this method simply acts as a convenient proxy for
     * {@link MessageSource#getMessage(Verification, String, Object[])}.
     * </p>
     *
     * @param message
     *         the optional message which provides a more detailed explanation of what was verified
     * @param args
     *         the optional format arguments which are used to format {@code message}
     * @return The potentially formatted message.
     * @throws IllegalArgumentException
     *         If {@code message} is formatted but is an invalid format pattern or any of the format {@code args} are
     *         invalid for their placeholders.
     * @throws NoSuchMessageException
     *         If no message could be found for any keys that are required to build the full message.
     * @see #getMessage(MessageKey, Object...)
     * @since 0.2.0
     */
    String getMessage(String message, Object... args);

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
     * Generally, this method simply acts as a convenient proxy for
     * {@link ReportExecutor#execute(Verification, boolean, MessageHolder)}, while also ensuring that this
     * {@link Verification} will no longer be negated as a result of calling this method, regardless of whether
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
     * Generally, this method simply acts as a convenient proxy for
     * {@link ReportExecutor#execute(Verification, boolean, MessageHolder)}, while also ensuring that this
     * {@link Verification} will no longer be negated as a result of calling this method, regardless of whether
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
     * Returns the current locale that is being used by this {@link Verification} to lookup and format messages for any
     * {@link VerifierException VerifierExceptions} that are thrown by a {@link Reporter}.
     * </p>
     * <p>
     * Generally, this method simply acts as a convenient proxy for {@link LocaleContext#getLocale()}.
     * </p>
     *
     * @return The current {@code Locale}.
     * @since 0.2.0
     */
    Locale getLocale();

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
     * Returns the value for this {@link Verification}.
     * </p>
     *
     * @return The value being verified which may be {@literal null}.
     */
    T getValue();
}
