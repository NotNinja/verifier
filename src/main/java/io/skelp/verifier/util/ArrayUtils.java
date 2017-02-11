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
package io.skelp.verifier.util;

/**
 * <p>
 * Contains utility methods for dealing with arrays.
 * </p>
 *
 * @author Alasdair Mercer
 * @since 0.2.0
 */
public final class ArrayUtils {

    /**
     * <p>
     * Returns whether the specified object is an array.
     * </p>
     * <pre>
     * ArrayUtils.isArray(null)                =&gt; false
     * ArrayUtils.isArray("foo")               =&gt; false
     * ArrayUtils.isArray(new Object[0])       =&gt; true
     * ArrayUtils.isArray(new String[]{"foo"}) =&gt; true
     * </pre>
     *
     * @param obj
     *         the object to be checked (may be {@literal null})
     * @return {@literal true} if {@code obj} is an array; otherwise {@literal false}.
     */
    public static boolean isArray(final Object obj) {
        return obj != null && obj.getClass().isArray();
    }

    /**
     * <p>
     * Returns whether the specified {@code array} is empty.
     * </p>
     * <pre>
     * ArrayUtils.isEmpty(null)                =&gt; true
     * ArrayUtils.isEmpty(new Object[0])       =&gt; true
     * ArrayUtils.isEmpty(new String[]{"foo"}) =&gt; false
     * </pre>
     *
     * @param array
     *         the array to be checked (may be {@literal null})
     * @return {@literal true} if {@code array} is {@literal null} or empty; otherwise {@literal false}.
     */
    public static boolean isEmpty(final Object[] array) {
        return array == null || array.length == 0;
    }

    /**
     * <p>
     * Creates an instance of {@link ArrayUtils}.
     * </p>
     * <p>
     * This should <b>not</b> be used for standard programming but is available for cases where an instance is needed
     * for a Java Bean etc.
     * </p>
     */
    public ArrayUtils() {
    }
}
