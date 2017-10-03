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
package org.notninja.verifier.message;

import org.notninja.verifier.verification.Verification;

/**
 * <p>
 * A source of meaningful and human-readable messages for verification errors to help aid diagnosis of such errors. Such
 * messages can (and should) also be localized and supporting messages can also be formatted using arguments.
 * </p>
 *
 * @author Alasdair Mercer
 * @since 0.2.0
 */
public interface MessageSource {

    /**
     * <p>
     * Returns the message for the specified {@code verification}, optionally using the {@code key} and format
     * {@code args} provided.
     * </p>
     * <p>
     * Where {@code key} is provided, it is used to lookup a localized message which may also be formatted using the
     * arguments provided. Using {@link MessageKey} is the ideal approach as it can help to localize messages which aids
     * consumers and their users.
     * </p>
     *
     * @param verification
     *         the current {@link Verification}
     * @param key
     *         the optional {@link MessageKey} which provides a more detailed localized explanation of what was verified
     * @param args
     *         the optional format arguments which are used to format localized message
     * @return The localized message based on {@code verification}.
     * @throws IllegalArgumentException
     *         If the localized message is formatted but is an invalid format pattern or any of the format {@code args}
     *         are invalid for their placeholders.
     * @throws NoSuchMessageException
     *         If no message could be found for {@code key} or any other that is required to build the full message.
     */
    String getMessage(Verification<?> verification, MessageKey key, Object[] args);

    /**
     * <p>
     * Returns the message for the specified {@code verification}, optionally using the {@code message} and format
     * {@code args} provided.
     * </p>
     * <p>
     * While the context of the message should still be localized by the implementation, this method is intended for
     * cases where {@code message} is not pre-defined and is most likely provided by consumers. Ideally, all messages
     * should be localized and can be referenced via a {@link MessageKey}.
     * </p>
     *
     * @param verification
     *         the current {@link Verification}
     * @param message
     *         the optional message which provides a more detailed explanation of what was verified
     * @param args
     *         the optional format arguments which are used to format {@code message}
     * @return The potentially formatted message based on {@code verification}.
     * @throws IllegalArgumentException
     *         If {@code message} is formatted but is an invalid format pattern or any of the format {@code args} are
     *         invalid for their placeholders.
     * @throws NoSuchMessageException
     *         If no message could be found for any keys that are required to build the full message.
     */
    String getMessage(Verification<?> verification, String message, Object[] args);
}
