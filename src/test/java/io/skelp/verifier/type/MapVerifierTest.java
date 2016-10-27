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
package io.skelp.verifier.type;

import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

import java.util.AbstractMap;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import io.skelp.verifier.AbstractCustomVerifierTestCase;
import io.skelp.verifier.CustomVerifierTestCaseBase;
import io.skelp.verifier.type.base.BaseCollectionVerifierTestCase;

/**
 * <p>
 * Tests for the {@link MapVerifier} class.
 * </p>
 *
 * @author Alasdair Mercer
 */
@RunWith(Enclosed.class)
public class MapVerifierTest {

    public static class MapVerifierAbstractCustomVerifierTest extends AbstractCustomVerifierTestCase<Map<String, Integer>, MapVerifier<String, Integer>> {

        @Override
        protected MapVerifier<String, Integer> createCustomVerifier() {
            return new MapVerifier<>(getMockVerification());
        }

        @Override
        protected Map<String, Integer> createValueOne() {
            return createMap(new String[]{"abc", "def", "ghi"}, new Integer[]{123, 456, 789});
        }

        @Override
        protected Map<String, Integer> createValueTwo() {
            return createMap(new String[]{"ihg", "fed", "cba"}, new Integer[]{987, 654, 321});
        }

        @Override
        protected Class<?> getParentClass() {
            return AbstractMap.class;
        }

        @Override
        protected Class<?> getValueClass() {
            return LinkedHashMap.class;
        }
    }

    public static class MapVerifierBaseCollectionVerifierTest extends BaseCollectionVerifierTestCase<Integer, Map<String, Integer>, MapVerifier<String, Integer>> {

        @Override
        protected MapVerifier<String, Integer> createCustomVerifier() {
            return new MapVerifier<>(getMockVerification());
        }

        @Override
        protected Map<String, Integer> createEmptyValue() {
            return Collections.emptyMap();
        }

        @Override
        protected Map<String, Integer> createFullValue() {
            return createMap(new String[]{"abc", "def", "ghi"}, new Integer[]{123, 456, 789});
        }

        @Override
        protected Map<String, Integer> createValueWithNull() {
            return createMap(new String[]{"abc", "def", "ghi"}, new Integer[]{123, 456, null});
        }

        @Override
        protected Integer getExistingItem() {
            return 789;
        }

        @Override
        protected int getFullValueSize() {
            return 3;
        }

        @Override
        protected Class<Integer> getItemClass() {
            return Integer.class;
        }

        @Override
        protected Integer getMissingItem() {
            return 321;
        }
    }

    public static class MapVerifierMiscTest extends CustomVerifierTestCaseBase<Map<String, Integer>, MapVerifier<String, Integer>> {

        @Override
        protected MapVerifier<String, Integer> createCustomVerifier() {
            return new MapVerifier<>(getMockVerification());
        }

        @Test
        public void testContainKeyWhenKeyIsNotPresentInValue() {
            testContainKeyHelper(createFullValue(), getMissingKey(), false);
        }

        @Test
        public void testContainKeyWhenKeyIsPresentInValue() {
            testContainKeyHelper(createFullValue(), getExistingKey(), true);
        }

        @Test
        public void testContainKeyWithNullKey() {
            Map<String, Integer> value = createMap(new String[]{null}, new Integer[]{123});

            testContainKeyHelper(value, null, true);
        }

        @Test
        public void testContainKeyWithNullValue() {
            testContainKeyHelper(null, getExistingKey(), false);
        }

        private void testContainKeyHelper(Map<String, Integer> value, String key, boolean expected) {
            setValue(value);

            assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().containKey(key));

            verify(getMockVerification()).check(eq(expected), eq("contain key '%s'"), getArgsCaptor().capture());

            assertSame("Passes key for message formatting", key, getArgsCaptor().getValue());
        }

        @Test
        public void testContainAllKeysWhenNoKeys() {
            testContainAllKeysHelper(createFullValue(), new String[0], true);
        }

        @Test
        public void testContainAllKeysWhenNoKeysAndValueIsEmpty() {
            testContainAllKeysHelper(createEmptyValue(), new String[0], true);
        }

        @Test
        public void testContainAllKeysWhenKeyIsNull() {
            testContainAllKeysHelper(createFullValue(), new String[]{null}, false);
        }

        @Test
        public void testContainAllKeysWhenKeysIsNull() {
            testContainAllKeysHelper(createFullValue(), null, true);
        }

        @Test
        public void testContainAllKeysWhenValueContainsAllKeys() {
            testContainAllKeysHelper(createFullValue(), new String[]{getExistingKey(), getExistingKey(), getExistingKey()}, true);
        }

        @Test
        public void testContainAllKeysWhenValueContainsSomeKeys() {
            testContainAllKeysHelper(createFullValue(), new String[]{getExistingKey(), getMissingKey(), getMissingKey()}, false);
        }

        @Test
        public void testContainAllKeysWhenValueDoesNotContainKey() {
            testContainAllKeysHelper(createFullValue(), new String[]{getMissingKey(), getMissingKey(), getMissingKey()}, false);
        }

        @Test
        public void testContainAllKeysWhenValueIsEmpty() {
            testContainAllKeysHelper(createEmptyValue(), new String[]{getExistingKey()}, false);
        }

        @Test
        public void testContainAllKeysWhenValueIsNull() {
            testContainAllKeysHelper(null, new String[0], false);
        }

        private void testContainAllKeysHelper(Map<String, Integer> value, String[] keys, boolean expected) {
            setValue(value);

            assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().containAllKeys(keys));

            verify(getMockVerification()).check(eq(expected), eq("contain all keys %s"), getArgsCaptor().capture());

            assertArrayFormatter(getArgsCaptor().getValue(), keys);
        }

        @Test
        public void testContainAnyKeyWhenNoKeys() {
            testContainAnyKeyHelper(createFullValue(), new String[0], false);
        }

        @Test
        public void testContainAnyKeyWhenNoKeysAndValueIsEmpty() {
            testContainAnyKeyHelper(createEmptyValue(), new String[0], false);
        }

        @Test
        public void testContainAnyKeyWhenKeyIsNull() {
            testContainAnyKeyHelper(createFullValue(), new String[]{null}, false);
        }

        @Test
        public void testContainAnyKeyWhenKeysIsNull() {
            testContainAnyKeyHelper(createFullValue(), null, false);
        }

        @Test
        public void testContainAnyKeyWhenValueContainsAllKeys() {
            testContainAnyKeyHelper(createFullValue(), new String[]{getExistingKey(), getExistingKey(), getExistingKey()}, true);
        }

        @Test
        public void testContainAnyKeyWhenValueContainsSomeKeys() {
            testContainAnyKeyHelper(createFullValue(), new String[]{getMissingKey(), getMissingKey(), getExistingKey()}, true);
        }

        @Test
        public void testContainAnyKeyWhenValueDoesNotContainKey() {
            testContainAnyKeyHelper(createFullValue(), new String[]{getMissingKey(), getMissingKey(), getMissingKey()}, false);
        }

        @Test
        public void testContainAnyKeyWhenValueIsEmpty() {
            testContainAnyKeyHelper(createEmptyValue(), new String[]{getExistingKey()}, false);
        }

        @Test
        public void testContainAnyKeyWhenValueIsNull() {
            testContainAnyKeyHelper(null, new String[0], false);
        }

        private void testContainAnyKeyHelper(Map<String, Integer> value, String[] keys, boolean expected) {
            setValue(value);

            assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().containAnyKey(keys));

            verify(getMockVerification()).check(eq(expected), eq("contain any key %s"), getArgsCaptor().capture());

            assertArrayFormatter(getArgsCaptor().getValue(), keys);
        }

        private Map<String, Integer> createEmptyValue() {
            return Collections.emptyMap();
        }

        private Map<String, Integer> createFullValue() {
            return createMap(new String[]{"abc", "def", "ghi"}, new Integer[]{123, 456, 789});
        }

        private String getExistingKey() {
            return "abc";
        }

        private String getMissingKey() {
            return "foo";
        }
    }
}
