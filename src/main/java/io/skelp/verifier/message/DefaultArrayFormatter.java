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

/**
 * The default implementation of {@link ArrayFormatter}.
 *
 * @param <T>
 *         the type of elements within the array
 * @author Alasdair Mercer
 */
public final class DefaultArrayFormatter<T> implements ArrayFormatter<T> {

    // TODO: Try to support nested arrays and circular references

    private final T[] array;

    /**
     * Creates an instance of {@link DefaultArrayFormatter} for the {@code array} provided.
     *
     * @param array
     *         the array to be formatted
     */
    public DefaultArrayFormatter(final T[] array) {
        this.array = array;
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
            buffer.append('\'');
            buffer.append(String.valueOf(array[index]));
            buffer.append('\'');

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
