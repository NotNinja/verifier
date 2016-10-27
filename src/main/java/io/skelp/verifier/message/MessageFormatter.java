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
package io.skelp.verifier.message;

import io.skelp.verifier.verification.Verification;

/**
 * <p>
 * A formatter for verification errors to provide meaningful and human-readable messages to help aid diagnosis of such
 * errors.
 * </p>
 *
 * @author Alasdair Mercer
 */
public interface MessageFormatter {

    // TODO: Try to support multiple locales and possibly resources

    /**
     * <p>
     * Formats the specified {@code verification} using the optional {@code message} and format {@code args} provided.
     * </p>
     *
     * @param verification
     *         the {@link Verification} containing the verified value and states to be formatted
     * @param message
     *         the optional message which provides a (slightly) more detailed explanation of what was verified
     * @param args
     *         the optional format arguments which are only used to format {@code message}
     * @return The formatted message based on {@code verification}.
     */
    String format(Verification<?> verification, String message, Object... args);

    /**
     * <p>
     * Returns an array formatter which wraps the specified {@code array} so that it is formatted in a readable fashion
     * if/when required.
     * </p>
     * <p>
     * This method will not immediately format {@code array} and, instead, it's the responsibility of the calling code
     * to do so only when appropriate. This attempts to avoid unnecessarily formatting arrays, which could be a time
     * consuming process unless absolutely required.
     * </p>
     *
     * @param array
     *         the array to be wrapped in an {@link ArrayFormatter} (may be {@literal null})
     * @param <T>
     *         the type of elements within {@code array}
     * @return An {@link ArrayFormatter} for {@code array}.
     */
    <T> ArrayFormatter<T> formatArray(T[] array);
}
