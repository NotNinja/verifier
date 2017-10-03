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
package org.notninja.verifier.message.formatter;

import org.notninja.verifier.service.Weighted;

/**
 * <p>
 * Provides {@link Formatter} instances which can be used to format a given object.
 * </p>
 * <p>
 * {@code FormatterProviders} are registered via Java's SPI, so in order to register a custom {@code FormatterProvider}
 * projects should contain should create a
 * {@code org.notninja.verifier.verification.message.formatter.FormatterProvider} file within {@code META-INF/services}
 * listing the class reference for each custom {@code FormatterProvider} (e.g.
 * {@code com.example.verifier.MyCustomFormatterProvider}) on separate lines. {@code FormatterProviders} are also
 * {@link Weighted}, which means that they are loaded in priority order (the lower the weight, the higher the priority).
 * Verifier has a built-in default {@code FormatterProvider} which is given a low priority (i.e.
 * {@value #DEFAULT_IMPLEMENTATION_WEIGHT}) to allow custom implementations to be easily ordered around it.
 * </p>
 *
 * @author Alasdair Mercer
 * @since 0.2.0
 */
public interface FormatterProvider extends Weighted {

    /**
     * <p>
     * Returns a formatter that can be used to format the specified object so that it is more human-friendly.
     * </p>
     * <p>
     * This method will return the first {@link Formatter} that supports {@code obj} and will return {@literal null} if
     * none support {@code obj} or if {@code obj} is {@literal null}.
     * </p>
     *
     * @param obj
     *         the object for which a supporting {@link Formatter} is to be returned (may be {@literal null})
     * @return A {@link Formatter} that supports {@code obj} or {@literal null} if none was found or {@code obj} is
     * {@literal null}.
     */
    Formatter getFormatter(Object obj);
}
