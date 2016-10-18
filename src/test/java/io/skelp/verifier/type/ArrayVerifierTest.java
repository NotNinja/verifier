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
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.*;

import java.util.Comparator;
import org.junit.Test;

import io.skelp.verifier.AbstractCustomVerifierTestBase;
import io.skelp.verifier.VerifierException;

/**
 * Tests for the {@link ArrayVerifier} class.
 *
 * @author Alasdair Mercer
 */
public class ArrayVerifierTest extends AbstractCustomVerifierTestBase<Integer[], ArrayVerifier<Integer>> {

    @Test
    public void testContainWhenItemIsNotPresentInValue() {
        testContainHelper(new Integer[]{123, 456, 789}, 321, false);
    }

    @Test
    public void testContainWhenItemIsPresentInValue() {
        testContainHelper(new Integer[]{123, 456, 789}, 789, true);
    }

    @Test
    public void testContainWithNullItem() {
        testContainHelper(new Integer[]{123, 456, null}, null, true);
    }

    @Test
    public void testContainWithNullValue() {
        testContainHelper(null, 123, false);
    }

    private void testContainHelper(Integer[] value, Integer item, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().contain(item));

        verify(getMockVerification()).check(eq(expected), eq("contain '%s'"), getArgsCaptor().capture());

        assertSame("Passes item for message formatting", item, getArgsCaptor().getValue());
    }

    @Test
    public void testEmptyWithEmptyValue() {
        testEmptyHelper(new Integer[0], true);
    }

    @Test
    public void testEmptyWithNonEmptyValue() {
        testEmptyHelper(new Integer[]{123}, false);
    }

    @Test
    public void testEmptyWithNullValue() {
        testEmptyHelper(null, true);
    }

    private void testEmptyHelper(Integer[] value, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().empty());

        verify(getMockVerification()).check(expected, "be empty");
    }

    @Test
    public void testLengthWithEmptyValue() {
        testLengthHelper(new Integer[0], 0, true);
    }

    @Test
    public void testLengthWithEmptyValueAndIncorrectLength() {
        testLengthHelper(new Integer[0], 1, false);
    }

    @Test
    public void testLengthWithNonEmptyValue() {
        testLengthHelper(new Integer[]{123, 456, 789}, 3, true);
    }

    @Test
    public void testLengthWithNonEmptyValueAndIncorrectLength() {
        testLengthHelper(new Integer[]{123, 456, 789}, 4, false);
    }

    @Test
    public void testLengthWithNullValue() {
        testLengthHelper(null, 0, true);
    }

    @Test
    public void testLengthWithNullValueAndIncorrectLength() {
        testLengthHelper(null, 1, false);
    }

    private void testLengthHelper(Integer[] value, int length, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().length(length));

        verify(getMockVerification()).check(eq(expected), eq("have length of '%d'"), getArgsCaptor().capture());

        assertSame("Passes length for message formatting", length, getArgsCaptor().getValue());
    }

    @Test
    public void testSortedWithEmptyValue() {
        testSortedHelper(new Integer[0], new StandardComparator<Integer>(), false, true);
    }

    @Test
    public void testSortedWithNullValue() {
        testSortedHelper(null, new StandardComparator<Integer>(), false, false);
    }

    @Test
    public void testSortedWithSingleElementValue() {
        testSortedHelper(new Integer[]{123}, new StandardComparator<Integer>(), false, true);
    }

    @Test
    public void testSortedWithSortedValue() {
        testSortedHelper(new Integer[]{123, 456, 789}, new StandardComparator<Integer>(), true, true);
    }

    @Test
    public void testSortedWithUnsortedValue() {
        testSortedHelper(new Integer[]{123, 789, 456}, new StandardComparator<Integer>(), true, false);
    }

    @Test
    public void testSortedThrowsIfComparatorIsNull() {
        thrown.expect(VerifierException.class);
        thrown.expectMessage("comparator must not be null");

        getCustomVerifier().sorted(null);
    }

    private void testSortedHelper(Integer[] value, Comparator<Integer> comparator, boolean comparatorUseExpected, boolean expected) {
        comparator = spy(comparator);

        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().sorted(comparator));

        verify(comparator, comparatorUseExpected ? atLeastOnce() : never()).compare(any(Integer.class), any(Integer.class));

        verify(getMockVerification()).check(eq(expected), eq("be sorted by '%s'"), getArgsCaptor().capture());

        assertSame("Passes comparator for message formatting", comparator, getArgsCaptor().getValue());
    }

    @Test
    public void testSortedWithNameAndEmptyValue() {
        testSortedHelper(new Integer[0], new StandardComparator<Integer>(), "comparator", false, true);
    }

    @Test
    public void testSortedWithNameAndNullValue() {
        testSortedHelper(null, new StandardComparator<Integer>(), "comparator", false, false);
    }

    @Test
    public void testSortedWithNameAndSingleElementValue() {
        testSortedHelper(new Integer[]{123}, new StandardComparator<Integer>(), "comparator", false, true);
    }

    @Test
    public void testSortedWithNameAndSortedValue() {
        testSortedHelper(new Integer[]{123, 456, 789}, new StandardComparator<Integer>(), "comparator", true, true);
    }

    @Test
    public void testSortedWithNameAndUnsortedValue() {
        testSortedHelper(new Integer[]{123, 789, 456}, new StandardComparator<Integer>(), "comparator", true, false);
    }

    @Test
    public void testSortedWithNameThrowsIfComparatorIsNull() {
        thrown.expect(VerifierException.class);
        thrown.expectMessage("comparator must not be null");

        getCustomVerifier().sorted(null, "comparator");
    }

    private void testSortedHelper(Integer[] value, Comparator<Integer> comparator, Object name, boolean comparatorUseExpected, boolean expected) {
        comparator = spy(comparator);

        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().sorted(comparator, name));

        verify(comparator, comparatorUseExpected ? atLeastOnce() : never()).compare(any(Integer.class), any(Integer.class));

        verify(getMockVerification()).check(eq(expected), eq("be sorted by '%s'"), getArgsCaptor().capture());

        assertSame("Passes name for message formatting", name, getArgsCaptor().getValue());
    }

    @Override
    protected ArrayVerifier<Integer> createCustomVerifier() {
        return new ArrayVerifier<>(getMockVerification());
    }

    private static class StandardComparator<T extends Comparable<? super T>> implements Comparator<T> {

        @Override
        public int compare(T o1, T o2) {
            return o1.compareTo(o2);
        }
    }
}
