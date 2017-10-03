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

import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.notninja.verifier.service.Weighted;

/**
 * <p>
 * Tests for the {@link DefaultFormatterProvider} class.
 * </p>
 *
 * @author Alasdair Mercer
 */
public class DefaultFormatterProviderTest {

    private DefaultFormatterProvider provider;

    @Before
    public void setUp() {
        provider = new DefaultFormatterProvider();
    }

    @Test
    public void testGetFormatterWithArray() {
        Formatter formatter = provider.getFormatter(new Object[0]);

        assertNotNull("Returns formatter for array", formatter);
        assertTrue("Returns an instance of CollectionFormatter", formatter instanceof CollectionFormatter);
    }

    @Test
    public void testGetFormatterWithClass() {
        Formatter formatter = provider.getFormatter(Object.class);

        assertNotNull("Returns formatter for class", formatter);
        assertTrue("Returns an instance of ClassFormatter", formatter instanceof ClassFormatter);
    }

    @Test
    public void testGetFormatterWithCollection() {
        Formatter formatter = provider.getFormatter(Collections.emptyList());

        assertNotNull("Returns formatter for collection", formatter);
        assertTrue("Returns an instance of CollectionFormatter", formatter instanceof CollectionFormatter);
    }

    @Test
    public void testGetFormatterWithMap() {
        Formatter formatter = provider.getFormatter(Collections.emptyMap());

        assertNotNull("Returns formatter for map", formatter);
        assertTrue("Returns an instance of MapFormatter", formatter instanceof MapFormatter);
    }

    @Test
    public void testGetFormatterWhenNoFormatterFound() {
        assertNull("Returns null for unsupported object", provider.getFormatter(123));
    }

    @Test
    public void testGetFormatterWhenObjectIsNull() {
        assertNull("Returns null for null object", provider.getFormatter(null));
    }

    @Test
    public void testGetWeight() {
        Assert.assertEquals("Has default implementation weight", Weighted.DEFAULT_IMPLEMENTATION_WEIGHT, provider.getWeight());
    }
}
