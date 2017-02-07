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

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * <p>
 * Tests for the {@link ArrayUtils} class.
 * </p>
 *
 * @author Alasdair Mercer
 */
public class ArrayUtilsTest {

    @Test
    public void testConstructor() {
        // Ensure that ArrayUtils can be instantiated, if required
        new ArrayUtils();
    }

    @Test
    public void testIsArray() {
        assertFalse("Null is not an array", ArrayUtils.isArray(null));
        assertFalse("Plain object is not an array", ArrayUtils.isArray(new Object()));
        assertTrue("Array is obviously an array", ArrayUtils.isArray(new Object[0]));
    }

    @Test
    public void testIsEmpty() {
        assertTrue("Null is empty", ArrayUtils.isEmpty(null));
        assertTrue("Array with no elements is empty", ArrayUtils.isEmpty(new Object[0]));
        assertFalse("Array with at least one element is not empty", ArrayUtils.isEmpty(new Object[]{"foo"}));
    }
}
