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
package io.skelp.verifier.type.base;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

import java.util.Comparator;
import org.junit.Test;

import io.skelp.verifier.VerifierException;

/**
 * <p>
 * Test case for {@link BaseSortableCollectionVerifier} implementation classes.
 * </p>
 *
 * @param <E>
 *         the {@code Comparable} element type for the {@link BaseSortableCollectionVerifier} being tested
 * @param <T>
 *         the value type for the {@link BaseSortableCollectionVerifier} being tested
 * @param <V>
 *         the type of the {@link BaseSortableCollectionVerifier} being tested
 * @author Alasdair Mercer
 */
public abstract class BaseSortableCollectionVerifierTestCase<E extends Comparable<? super E>, T, V extends BaseSortableCollectionVerifier<E, T, V>> extends BaseCollectionVerifierTestCase<E, T, V> {

    @Test
    public void testSortedByWithEmptyValue() {
        testSortedByHelper(createEmptyValue(), new NaturalComparator<E>(), false, true);
    }

    @Test
    public void testSortedByWithNullValue() {
        testSortedByHelper(null, new NaturalComparator<E>(), false, false);
    }

    @Test
    public void testSortedByWithSingleElementValue() {
        testSortedByHelper(createSingleValue(), new NaturalComparator<E>(), false, true);
    }

    @Test
    public void testSortedByWithSortedValue() {
        testSortedByHelper(createSortedValue(), new NaturalComparator<E>(), true, true);
    }

    @Test
    public void testSortedByWithUnsortedValue() {
        testSortedByHelper(createUnsortedValue(), new NaturalComparator<E>(), true, false);
    }

    @Test
    public void testSortedByThrowsIfComparatorIsNull() {
        thrown.expect(VerifierException.class);
        thrown.expectMessage("comparator must not be null: null");

        getCustomVerifier().sortedBy(null);
    }

    private void testSortedByHelper(T value, Comparator<E> comparator, boolean comparatorUseExpected, boolean expected) {
        comparator = spy(comparator);

        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().sortedBy(comparator));

        verify(comparator, comparatorUseExpected ? atLeastOnce() : never()).compare(any(getElementClass()), any(getElementClass()));

        verify(getMockVerification()).check(eq(expected), eq("be sorted by '%s'"), getArgsCaptor().capture());

        assertSame("Passes comparator for message formatting", comparator, getArgsCaptor().getValue());
    }

    @Test
    public void testSortedByWithNameAndEmptyValue() {
        testSortedByHelper(createEmptyValue(), new NaturalComparator<E>(), "comparator", false, true);
    }

    @Test
    public void testSortedByWithNameAndNullValue() {
        testSortedByHelper(null, new NaturalComparator<E>(), "comparator", false, false);
    }

    @Test
    public void testSortedByWithNameAndSingleElementValue() {
        testSortedByHelper(createSingleValue(), new NaturalComparator<E>(), "comparator", false, true);
    }

    @Test
    public void testSortedByWithNameAndSortedValue() {
        testSortedByHelper(createSortedValue(), new NaturalComparator<E>(), "comparator", true, true);
    }

    @Test
    public void testSortedByWithNameAndUnsortedValue() {
        testSortedByHelper(createUnsortedValue(), new NaturalComparator<E>(), "comparator", true, false);
    }

    @Test
    public void testSortedByWithNameThrowsIfComparatorIsNull() {
        thrown.expect(VerifierException.class);
        thrown.expectMessage("comparator must not be null: null");

        getCustomVerifier().sortedBy(null, "comparator");
    }

    private void testSortedByHelper(T value, Comparator<E> comparator, Object name, boolean comparatorUseExpected, boolean expected) {
        comparator = spy(comparator);

        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().sortedBy(comparator, name));

        verify(comparator, comparatorUseExpected ? atLeastOnce() : never()).compare(any(getElementClass()), any(getElementClass()));

        verify(getMockVerification()).check(eq(expected), eq("be sorted by '%s'"), getArgsCaptor().capture());

        assertSame("Passes name for message formatting", name, getArgsCaptor().getValue());
    }

    /**
     * <p>
     * Creates a value containing only a single element.
     * </p>
     *
     * @return A value within one element.
     */
    protected abstract T createSingleValue();

    /**
     * <p>
     * Creates a value whose elements are already in a natural sorted order.
     * </p>
     *
     * @return A sorted value.
     */
    protected abstract T createSortedValue();

    /**
     * <p>
     * Creates a value whose elements are <b>not</b> in a sorted order.
     * </p>
     *
     * @return An unsorted value.
     */
    protected abstract T createUnsortedValue();

    private static class NaturalComparator<T extends Comparable<? super T>> implements Comparator<T> {

        @Override
        public int compare(T o1, T o2) {
            return o1.compareTo(o2);
        }
    }
}
