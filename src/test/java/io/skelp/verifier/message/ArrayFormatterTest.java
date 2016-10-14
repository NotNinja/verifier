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

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests for the {@link ArrayFormatter} class.
 *
 * @author Alasdair Mercer
 */
public class ArrayFormatterTest {

    @Test
    public void testFormat() {
        String expected = "['1', '2', '3']";
        String actual = new ArrayFormatter<>(new Integer[]{1, 2, 3}).format();

        assertEquals("Formats array", expected, actual);
    }

    @Test
    public void testFormatWhenArrayHasSingleItem() {
        String expected = "['1']";
        String actual = new ArrayFormatter<>(new Integer[]{1}).format();

        assertEquals("Formats array with single item", expected, actual);
    }

    @Test
    public void testFormatWhenArrayIsEmpty() {
        String expected = "[]";
        String actual = new ArrayFormatter<>(new Object[0]).format();

        assertEquals("Formats empty array", expected, actual);
    }

    @Test
    public void testFormatWhenArrayIsNull() {
        String expected = "null";
        String actual = new ArrayFormatter<>(null).format();

        assertEquals("Formats null array", expected, actual);
    }

    @Test
    public void testToString() {
        ArrayFormatter<Integer> formatter = new ArrayFormatter<>(new Integer[]{1, 2, 3});
        String expected = formatter.format();
        String actual = formatter.toString();

        assertEquals("Formats array", expected, actual);
    }
}
