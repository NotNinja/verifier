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

import java.util.Collection;
import java.util.List;
import java.util.Map;

import io.skelp.verifier.verification.Verification;

/**
 * <p>
 * An implementation of {@link Formatter} which transforms the key/value pairs within a {@code Map} into a string. The
 * entries are formatted recursively while preventing infinite loops by highlighting circular references.
 * </p>
 *
 * @author Alasdair Mercer
 * @since 0.2.0
 */
public final class MapFormatter extends HierarchicalFormatter {

    @Override
    protected void appendChild(final Verification<?> verification, final Object child, final List<Object> hierarchy, final StringBuilder buffer) {
        final Map.Entry entry = (Map.Entry) child;

        super.appendChild(verification, entry.getKey(), hierarchy, buffer);

        buffer.append("=");

        super.appendChild(verification, entry.getValue(), hierarchy, buffer);
    }

    @Override
    protected Collection<Object> getChildren(final Object parent) {
        return ((Map) parent).entrySet();
    }

    @Override
    public boolean supports(final Class<?> cls) {
        return Map.class.isAssignableFrom(cls);
    }
}
