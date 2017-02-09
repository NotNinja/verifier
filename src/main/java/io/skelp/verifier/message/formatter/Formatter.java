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
package io.skelp.verifier.message.formatter;

import io.skelp.verifier.verification.Verification;

/**
 * <p>
 * Formats a given object into a string.
 * </p>
 * <p>
 * {@code Formatters} can choose which types of objects that they support to allow other implementations to provide more
 * precise formatting for that type as only one {@code Formatter} will be used for a single instance.
 * </p>
 * <p>
 * {@code Formatters} are registered via Java's SPI, so in order to register a custom {@code Formatter} projects should
 * contain should create a {@code io.skelp.verifier.message.formatter.Formatter} file within {@code META-INF/services}
 * listing the class reference for each custom {@code Formatter} (e.g. {@code com.example.verifier.MyCustomFormatter})
 * on separate lines.
 * </p>
 *
 * @author Alasdair Mercer
 * @since 0.2.0
 */
public interface Formatter {

    /**
     * <p>
     * Formats the specified object into a string.
     * </p>
     *
     * @param verification
     *         the current {@link Verification}
     * @param obj
     *         the object to be formatted
     * @return A formatted string based on {@code obj}.
     */
    String format(Verification<?> verification, Object obj);

    /**
     * <p>
     * Returns whether this {@link Formatter} supports objects of specified type.
     * </p>
     *
     * @param cls
     *         the type to be checked
     * @return {@literal true} if this {@link Formatter} supports instances of {@code cls}; otherwise {@literal false}.
     */
    boolean supports(Class<?> cls);
}
