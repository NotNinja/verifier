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
package io.skelp.verifier.message.formatter;

import static org.junit.Assert.*;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import io.skelp.verifier.verification.Verification;

/**
 * <p>
 * Tests for the {@link MapFormatter} class.
 * </p>
 *
 * @author Alasdair Mercer
 */
@RunWith(MockitoJUnitRunner.class)
public class MapFormatterTest {

    @Mock
    private Verification<?> mockVerification;

    private MapFormatter formatter;

    @Before
    public void setUp() {
        when(mockVerification.getFormatter(isA(Collection.class))).thenReturn(new CollectionFormatter());
        when(mockVerification.getFormatter(isA(Object[].class))).thenReturn(new CollectionFormatter());
        when(mockVerification.getFormatter(isA(Class.class))).thenReturn(new ClassFormatter());
        when(mockVerification.getFormatter(isA(Map.class))).thenReturn(new MapFormatter());

        formatter = new MapFormatter();
    }

    @Test
    public void testFormat() {
        List<Object> nestedList = new ArrayList<>();
        Map<Object, Object> nestedMap = new LinkedHashMap<>();
        Map<Object, Object> map = new LinkedHashMap<>();
        map.put("foo", 123);
        map.put("bar", nestedList);
        map.put("fu", null);
        map.put("baz", nestedMap);
        map.put("fizz", map);
        map.put(Object.class, "buzz");

        nestedList.add(456);
        nestedList.add(nestedList);

        nestedMap.put("test1", 789);
        nestedMap.put("test2", map);

        String expected = "{'foo'='123', 'bar'=['456', circular], 'fu'=null, 'baz'={'test1'='789', 'test2'=circular}, 'fizz'=circular, 'java.lang.Object'='buzz'}";
        String actual = formatter.format(mockVerification, map);

        assertEquals("Formats map", expected, actual);
    }

    @Test
    public void testFormatWhenMapHasSingleEntry() {
        Map<Object, Object> map = new LinkedHashMap<>();
        map.put("test", 123);

        String expected = "{'test'='123'}";
        String actual = formatter.format(mockVerification, map);

        assertEquals("Formats map with single entry", expected, actual);
    }

    @Test
    public void testFormatWhenMapIsEmpty() {
        String expected = "{}";
        String actual = formatter.format(mockVerification, Collections.emptyMap());

        assertEquals("Formats empty map", expected, actual);
    }

    @Test
    public void testSupportsWithMapClasses() {
        assertTrue("Supports maps", formatter.supports(Map.class));
        assertTrue("Supports map implementations", formatter.supports(HashMap.class));
    }

    @Test
    public void testSupportsWithPlainObjectClass() {
        assertFalse("Only supports maps", formatter.supports(Object.class));
    }
}
