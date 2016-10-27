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
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import io.skelp.verifier.verification.Verification;

/**
 * <p>
 * Tests for the {@link DefaultMessageFormatter} class.
 * </p>
 *
 * @author Alasdair Mercer
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultMessageFormatterTest {

    @Mock
    private Verification<Object> mockVerification;

    private DefaultMessageFormatter formatter;

    @Before
    public void setUp() {
        when(mockVerification.getMessageFormatter()).thenReturn(formatter);

        formatter = new DefaultMessageFormatter();
    }

    @Test
    public void testFormat() {
        when(mockVerification.getName()).thenReturn("foo");
        when(mockVerification.getValue()).thenReturn(123);

        String expected = "foo must be a test: 123";
        String actual = formatter.format(mockVerification, "be a %s", "test");

        assertEquals("Formats verification", expected, actual);
    }

    @Test
    public void testFormatWhenNegated() {
        when(mockVerification.getName()).thenReturn("foo");
        when(mockVerification.isNegated()).thenReturn(true);
        when(mockVerification.getValue()).thenReturn(123);

        String expected = "foo must not be a test: 123";
        String actual = formatter.format(mockVerification, "be a %s", "test");

        assertEquals("Formats negated verification", expected, actual);
    }

    @Test
    public void testFormatWithArrayValue() {
        Integer[] array = new Integer[]{1, 2, 3};

        when(mockVerification.getName()).thenReturn("foo");
        when(mockVerification.getValue()).thenReturn(array);

        String expected = "foo must be a test: ['1', '2', '3']";
        String actual = formatter.format(mockVerification, "be a %s", "test");

        assertEquals("Formats verification for array", expected, actual);
    }

    @Test
    public void testFormatWithNoName() {
        when(mockVerification.getValue()).thenReturn(123);

        String expected = "Value must be a test: 123";
        String actual = formatter.format(mockVerification, "be a %s", "test");

        assertEquals("Formats verification with no name", expected, actual);
    }

    @Test
    public void testFormatWithNoMessage() {
        when(mockVerification.getName()).thenReturn("foo");
        when(mockVerification.getValue()).thenReturn(123);

        String expected = "foo must match: 123";
        String actual = formatter.format(mockVerification, null);

        assertEquals("Formats verification with no message", expected, actual);
    }

    @Test
    public void testFormatWithNoMessageWhenNegated() {
        when(mockVerification.getName()).thenReturn("foo");
        when(mockVerification.isNegated()).thenReturn(true);
        when(mockVerification.getValue()).thenReturn(123);

        String expected = "foo must not match: 123";
        String actual = formatter.format(mockVerification, null);

        assertEquals("Formats verification", expected, actual);
    }

    @Test
    public void testFormatWithNullValue() {
        when(mockVerification.getName()).thenReturn("foo");

        String expected = "foo must be a test: null";
        String actual = formatter.format(mockVerification, "be a %s", "test");

        assertEquals("Formats verification with null value", expected, actual);
    }

    @Test
    public void testFormatArray() {
        Integer[] array = new Integer[]{1, 2, 3};

        DefaultArrayFormatter<Integer> arrayFormatter = formatter.formatArray(array);

        assertNotNull("Never returns null", arrayFormatter);
        assertArrayEquals("Passes array to formatter", array, arrayFormatter.getArray());
    }
}
