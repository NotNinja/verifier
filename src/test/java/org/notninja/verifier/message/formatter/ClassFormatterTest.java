/*
 * Copyright (C) 2017 Alasdair Mercer, !ninja
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
package org.notninja.verifier.message.formatter;

import static org.junit.Assert.*;

import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import org.notninja.verifier.message.MessageKey;
import org.notninja.verifier.message.ResourceBundleMessageSource;
import org.notninja.verifier.verification.Verification;

/**
 * <p>
 * Tests for the {@link ClassFormatter} class.
 * </p>
 *
 * @author Alasdair Mercer
 */
@RunWith(MockitoJUnitRunner.class)
public class ClassFormatterTest {

    @Mock
    private Verification<?> mockVerification;

    private ClassFormatter formatter;

    @Before
    public void setUp() {
        formatter = new ClassFormatter();
    }

    @Test
    public void testFormat() {
        assertEquals("Returns correct representation of Class", "java.lang.Class", formatter.format(mockVerification, Class.class));
        assertEquals("Returns correct representation of an object class", "java.lang.Integer", formatter.format(mockVerification, Integer.class));
        assertEquals("Returns correct representation of an object array class", "java.lang.Integer[]", formatter.format(mockVerification, Integer[].class));
        assertEquals("Returns correct representation of an object multi-dimensional array class", "java.lang.Integer[][][]", formatter.format(mockVerification, Integer[][][].class));
        assertEquals("Returns correct representation of a primitive class", "int", formatter.format(mockVerification, int.class));
        assertEquals("Returns correct representation of a primitive array class", "int[]", formatter.format(mockVerification, int[].class));
        assertEquals("Returns correct representation of a primitive multi-dimensional array class", "int[][][]", formatter.format(mockVerification, int[][][].class));
        assertEquals("Returns correct representation of a nested class", "java.util.Map$Entry", formatter.format(mockVerification, Map.Entry.class));
        assertEquals("Returns correct representation of an enum", "org.notninja.verifier.message.ResourceBundleMessageSource$MessageKeys", formatter.format(mockVerification, ResourceBundleMessageSource.MessageKeys.class));
        assertEquals("Returns correct representation of an interface", "org.notninja.verifier.message.MessageKey", formatter.format(mockVerification, MessageKey.class));
        assertEquals("Returns correct representation of an anonymous class", "org.notninja.verifier.message.formatter.ClassFormatterTest$1", formatter.format(mockVerification, new MessageKey() {
            @Override
            public String code() {
                return null;
            }
        }.getClass()));
    }

    @Test
    public void testSupportsWithClass() {
        assertTrue("Supports classes", formatter.supports(Class.class));
    }

    @Test
    public void testSupportsWithPlainObjectClass() {
        assertFalse("Only supports classes", formatter.supports(Object.class));
    }
}
