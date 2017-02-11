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
package io.skelp.verifier.type;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import io.skelp.verifier.AbstractCustomVerifierTestCase;
import io.skelp.verifier.type.base.BaseSortableCollectionVerifierTestCase;

/**
 * <p>
 * Tests for the {@link CollectionVerifier} class.
 * </p>
 *
 * @author Alasdair Mercer
 */
@RunWith(Enclosed.class)
public class CollectionVerifierTest {

    public static class CollectionVerifierAbstractCustomVerifierTest extends AbstractCustomVerifierTestCase<Collection<Integer>, CollectionVerifier<Integer>> {

        @Override
        protected CollectionVerifier<Integer> createCustomVerifier() {
            return new CollectionVerifier<>(getMockVerification());
        }

        @Override
        protected Collection<Integer> createValueOne() {
            return new ArrayList<>(Arrays.asList(123, 456, 789));
        }

        @Override
        protected Collection<Integer> createValueTwo() {
            return new ArrayList<>(Arrays.asList(987, 654, 321));
        }

        @Override
        protected Class<?> getParentClass() {
            return AbstractList.class;
        }

        @Override
        protected Class<?> getValueClass() {
            return ArrayList.class;
        }
    }

    public static class CollectionVerifierBaseSortableCollectionVerifierTest extends BaseSortableCollectionVerifierTestCase<Integer, Collection<Integer>, CollectionVerifier<Integer>> {

        @Override
        protected CollectionVerifier<Integer> createCustomVerifier() {
            return new CollectionVerifier<>(getMockVerification());
        }

        @Override
        protected Collection<Integer> createEmptyValue() {
            return Collections.emptyList();
        }

        @Override
        protected Collection<Integer> createFullValue() {
            return createSortedValue();
        }

        @Override
        protected Collection<Integer> createSingleValue() {
            return Collections.singleton(123);
        }

        @Override
        protected Collection<Integer> createSortedValue() {
            return new ArrayList<>(Arrays.asList(123, 456, 789));
        }

        @Override
        protected Collection<Integer> createUnsortedValue() {
            return new ArrayList<>(Arrays.asList(123, 789, 456));
        }

        @Override
        protected Collection<Integer> createValueWithNull() {
            return new ArrayList<>(Arrays.asList(123, 456, null));
        }

        @Override
        protected Class<Integer> getElementClass() {
            return Integer.class;
        }

        @Override
        protected Integer getExistingElement() {
            return 789;
        }

        @Override
        protected int getFullValueSize() {
            return 3;
        }

        @Override
        protected Integer getMissingElement() {
            return 321;
        }
    }
}
