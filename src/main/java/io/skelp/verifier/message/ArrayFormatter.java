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
 * <p>
 * A formatter which transforms an array of elements into a string.
 * </p>
 *
 * @param <T>
 *         the type of elements within the array
 * @author Alasdair Mercer
 */
public interface ArrayFormatter<T> {

    /**
     * <p>
     * Formats the array into a string.
     * </p>
     *
     * @return A formatted string based on the array and its elements.
     */
    String format();

    /**
     * <p>
     * Returns the array for this {@link ArrayFormatter}.
     * </p>
     *
     * @return The array.
     */
    T[] getArray();

    /**
     * <p>
     * Delegates to {@link #format()} to provide a string representation of the array within this {@link
     * ArrayFormatter}.
     * </p>
     *
     * @return A string representation based on the formatted array.
     */
    String toString();
}
