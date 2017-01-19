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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 * The default implementation of {@link ArrayFormatter}.
 * </p>
 *
 * @param <T>
 *         the type of elements within the array
 * @author Alasdair Mercer
 */
public final class DefaultArrayFormatter<T> implements ArrayFormatter<T> {

    private final T[] array;
    private final Set<Object> hierarchy;

    /**
     * <p>
     * Creates an instance of {@link DefaultArrayFormatter} for the {@code array} provided.
     * </p>
     *
     * @param array
     *         the array to be formatted
     */
    public DefaultArrayFormatter(final T[] array) {
        this.array = array;
        this.hierarchy = Collections.singleton(array);
    }

    private DefaultArrayFormatter(final T[] array, final Set<Object> hierarchy) {
        this.array = array;
        this.hierarchy = new HashSet<>(hierarchy);
        this.hierarchy.add(array);
    }

    @Override
    public String format() {
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
            final T element = array[index];

            if (element == null) {
                buffer.append("null");
            } else if (hierarchy.contains(element)) {
                buffer.append("{Circular}");
            } else if (element.getClass().isArray()) {
                @SuppressWarnings("unchecked")
                final T[] childArray = (T[]) element;
                buffer.append(new DefaultArrayFormatter<>(childArray, hierarchy).format());
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

    @Override
    public T[] getArray() {
        return array;
    }

    @Override
    public String toString() {
        return format();
    }
}
