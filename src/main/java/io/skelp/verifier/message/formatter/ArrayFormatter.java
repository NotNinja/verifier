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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import io.skelp.verifier.util.ArrayUtils;
import io.skelp.verifier.verification.Verification;

/**
 * <p>
 * An implementation of {@link Formatter} which transforms an array of elements into a string. The array is formatted
 * recursively while preventing infinite loops by highlighting circular references.
 * </p>
 *
 * @author Alasdair Mercer
 * @since 0.2.0
 */
public final class ArrayFormatter implements Formatter {

    @Override
    public String format(final Verification<?> verification, final Object obj) {
        return format((Object[]) obj, Collections.singleton(obj));
    }

    @Override
    public boolean supports(final Class<?> cls) {
        return cls.isArray();
    }

    private String format(final Object[] array, final Set<Object> parentHierarchy) {
        final Set<Object> hierarchy = new HashSet<>(parentHierarchy);
        hierarchy.add(array);

        if (array == null) {
            return "null";
        }

        final int last = array.length - 1;
        if (last == -1) {
            return "[]";
        }

        final StringBuilder buffer = new StringBuilder();
        buffer.append('[');

        int index = 0;
        while (true) {
            final Object element = array[index];

            if (element == null) {
                buffer.append("null");
            } else if (hierarchy.contains(element)) {
                buffer.append("{Circular}");
            } else if (ArrayUtils.isArray(element)) {
                final Object[] childArray = (Object[]) element;
                buffer.append(format(childArray, hierarchy));
            } else {
                buffer.append('\'');
                buffer.append(element);
                buffer.append('\'');
            }

            if (index == last) {
                return buffer.append(']').toString();
            }

            buffer.append(", ");
            index++;
        }
    }
}
