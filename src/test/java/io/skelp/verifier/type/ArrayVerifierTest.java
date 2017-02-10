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

import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import io.skelp.verifier.AbstractCustomVerifierTestCase;
import io.skelp.verifier.type.base.BaseSortableCollectionVerifierTestCase;

/**
 * <p>
 * Tests for the {@link ArrayVerifier} class.
 * </p>
 *
 * @author Alasdair Mercer
 */
@RunWith(Enclosed.class)
public class ArrayVerifierTest {

    public static class ArrayVerifierAbstractCustomVerifierTest extends AbstractCustomVerifierTestCase<Integer[], ArrayVerifier<Integer>> {

        @Override
        protected ArrayVerifier<Integer> createCustomVerifier() {
            return new ArrayVerifier<>(getMockVerification());
        }

        @Override
        protected Integer[] createValueOne() {
            return new Integer[]{123, 456, 789};
        }

        @Override
        protected Integer[] createValueTwo() {
            return new Integer[]{987, 654, 321};
        }

        @Override
        protected Class<?> getParentClass() {
            return Object.class;
        }

        @Override
        protected Class<?> getValueClass() {
            return Integer[].class;
        }
    }

    public static class ArrayVerifierBaseSortableCollectionVerifierTest extends BaseSortableCollectionVerifierTestCase<Integer, Integer[], ArrayVerifier<Integer>> {

        @Override
        protected ArrayVerifier<Integer> createCustomVerifier() {
            return new ArrayVerifier<>(getMockVerification());
        }

        @Override
        protected Integer[] createEmptyValue() {
            return new Integer[0];
        }

        @Override
        protected Integer[] createFullValue() {
            return createSortedValue();
        }

        @Override
        protected Integer[] createSingleValue() {
            return new Integer[]{123};
        }

        @Override
        protected Integer[] createSortedValue() {
            return new Integer[]{123, 456, 789};
        }

        @Override
        protected Integer[] createUnsortedValue() {
            return new Integer[]{123, 789, 456};
        }

        @Override
        protected Integer[] createValueWithNull() {
            return new Integer[]{123, 456, null};
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
