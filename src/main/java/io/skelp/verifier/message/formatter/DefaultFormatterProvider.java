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

import io.skelp.verifier.service.Services;

/**
 * <p>
 * The default implementation of {@link FormatterProvider} which looks up {@link Formatter Formatters} using Java's SPI
 * and returns the first one to support a given object.
 * </p>
 *
 * @author Alasdair Mercer
 * @since 0.2.0
 */
public final class DefaultFormatterProvider implements FormatterProvider {

    @Override
    public Formatter getFormatter(final Object obj) {
        if (obj == null) {
            return null;
        }

        final Class<?> cls = obj.getClass();
        return Services.getServices(Formatter.class).stream()
            .filter(formatter -> formatter.supports(cls))
            .findFirst()
            .orElse(null);
    }

    @Override
    public int getWeight() {
        return DEFAULT_IMPLEMENTATION_WEIGHT;
    }
}
