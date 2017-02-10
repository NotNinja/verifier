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
import io.skelp.verifier.type.base.BaseComparableVerifierTestCase;

/**
 * <p>
 * Tests for the {@link ComparableVerifier} class.
 * </p>
 *
 * @author Alasdair Mercer
 */
@RunWith(Enclosed.class)
public class ComparableVerifierTest {

    public static class ComparableVerifierAbstractCustomVerifierTest extends AbstractCustomVerifierTestCase<ComparableWrapper, ComparableVerifier<ComparableWrapper>> {

        @Override
        protected ComparableVerifier<ComparableWrapper> createCustomVerifier() {
            return new ComparableVerifier<>(getMockVerification());
        }

        @Override
        protected ComparableWrapper createValueOne() {
            return new ComparableWrapper(0);
        }

        @Override
        protected ComparableWrapper createValueTwo() {
            return new ComparableWrapper(100);
        }

        @Override
        protected Class<?> getParentClass() {
            return Object.class;
        }

        @Override
        protected Class<?> getValueClass() {
            return ComparableWrapper.class;
        }
    }

    public static class ComparableVerifierBaseComparableVerifierTest extends BaseComparableVerifierTestCase<ComparableWrapper, ComparableVerifier<ComparableWrapper>> {

        @Override
        protected ComparableVerifier<ComparableWrapper> createCustomVerifier() {
            return new ComparableVerifier<>(getMockVerification());
        }

        @Override
        public ComparableWrapper getBaseValue() {
            return new ComparableWrapper(50);
        }

        @Override
        public ComparableWrapper getHigherValue() {
            return new ComparableWrapper(75);
        }

        @Override
        public ComparableWrapper getHighestValue() {
            return new ComparableWrapper(100);
        }

        @Override
        public ComparableWrapper getLowerValue() {
            return new ComparableWrapper(25);
        }

        @Override
        public ComparableWrapper getLowestValue() {
            return new ComparableWrapper(0);
        }
    }

    private static class ComparableWrapper implements Comparable<ComparableWrapper> {

        final Integer comparable;

        ComparableWrapper(Integer comparable) {
            this.comparable = comparable;
        }

        @Override
        public int compareTo(ComparableWrapper other) {
            return comparable.compareTo(other.comparable);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (obj == this) {
                return true;
            }
            if (obj.getClass() != getClass()) {
                return false;
            }

            ComparableWrapper other = (ComparableWrapper) obj;
            return comparable.equals(other.comparable);
        }

        @Override
        public int hashCode() {
            return comparable.hashCode();
        }
    }
}
