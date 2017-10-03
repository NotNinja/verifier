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
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import org.notninja.verifier.verification.Verification;

/**
 * <p>
 * Tests for the {@link CollectionFormatter} class.
 * </p>
 *
 * @author Alasdair Mercer
 */
@RunWith(MockitoJUnitRunner.class)
public class CollectionFormatterTest {

    @Mock
    private Verification<?> mockVerification;

    private CollectionFormatter formatter;

    @Before
    public void setUp() {
        when(mockVerification.getFormatter(isA(Collection.class))).thenReturn(new CollectionFormatter());
        when(mockVerification.getFormatter(isA(Object[].class))).thenReturn(new CollectionFormatter());
        when(mockVerification.getFormatter(isA(Class.class))).thenReturn(new ClassFormatter());
        when(mockVerification.getFormatter(isA(Map.class))).thenReturn(new MapFormatter());

        formatter = new CollectionFormatter();
    }

    @Test
    public void testFormatWithArray() {
        Object[] nestedArray = new Object[2];
        Map<Object, Object> nestedMap = new LinkedHashMap<>();
        Object[] array = new Object[6];
        array[0] = "foo";
        array[1] = nestedMap;
        array[2] = null;
        array[3] = nestedArray;
        array[4] = array;
        array[5] = Object.class;

        nestedArray[0] = 123;
        nestedArray[1] = array;

        nestedMap.put("test1", 456);
        nestedMap.put("test2", nestedMap);

        String expected = "['foo', {'test1'='456', 'test2'=circular}, null, ['123', circular], circular, 'java.lang.Object']";
        String actual = formatter.format(mockVerification, array);

        assertEquals("Formats array", expected, actual);
    }

    @Test
    public void testFormatWithArrayWhenArrayHasSingleItem() {
        String expected = "['123']";
        String actual = formatter.format(mockVerification, new Integer[]{123});

        assertEquals("Formats array with single item", expected, actual);
    }

    @Test
    public void testFormatWithArrayWhenArrayIsEmpty() {
        String expected = "[]";
        String actual = formatter.format(mockVerification, new Object[0]);

        assertEquals("Formats empty array", expected, actual);
    }

    @Test
    public void testFormatWithCollection() {
        List<Object> nestedList = new ArrayList<>();
        Map<Object, Object> nestedMap = new LinkedHashMap<>();
        List<Object> list = new ArrayList<>();
        list.add("foo");
        list.add(nestedMap);
        list.add(null);
        list.add(nestedList);
        list.add(list);
        list.add(Object.class);

        nestedList.add(123);
        nestedList.add(list);

        nestedMap.put("test1", 456);
        nestedMap.put("test2", nestedMap);

        String expected = "['foo', {'test1'='456', 'test2'=circular}, null, ['123', circular], circular, 'java.lang.Object']";
        String actual = formatter.format(mockVerification, list);

        assertEquals("Formats collection", expected, actual);
    }

    @Test
    public void testFormatWithCollectionWhenCollectionHasSingleItem() {
        String expected = "['123']";
        String actual = formatter.format(mockVerification, Collections.singletonList(123));

        assertEquals("Formats collection with single item", expected, actual);
    }

    @Test
    public void testFormatWithCollectionWhenCollectionIsEmpty() {
        String expected = "[]";
        String actual = formatter.format(mockVerification, Collections.emptyList());

        assertEquals("Formats empty collection", expected, actual);
    }

    @Test
    public void testSupportsWithArrayClass() {
        assertTrue("Supports arrays", formatter.supports(Object[].class));
    }

    @Test
    public void testSupportsWithCollectionClasses() {
        assertTrue("Supports collections", formatter.supports(Collection.class));
        assertTrue("Supports collection implementations", formatter.supports(ArrayList.class));
    }

    @Test
    public void testSupportsWithPlainObjectClass() {
        assertFalse("Only supports arrays and collections", formatter.supports(Object.class));
    }
}
