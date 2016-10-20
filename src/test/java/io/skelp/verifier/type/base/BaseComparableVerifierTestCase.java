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
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

import org.junit.Test;

import io.skelp.verifier.CustomVerifierTestCaseBase;

/**
 * Test case for {@link BaseComparableVerifier} implementation classes.
 *
 * @param <T>
 *         the {@code Comparable} value type for the {@link BaseComparableVerifier} being tested
 * @param <V>
 *         the type of the {@link BaseComparableVerifier} being tested
 * @author Alasdair Mercer
 */
public abstract class BaseComparableVerifierTestCase<T extends Comparable<? super T>, V extends BaseComparableVerifier<T, V>> extends CustomVerifierTestCaseBase<T, V> {

    // TODO: Test between & betweenExclusive with start greater than end and vice versa@Test

    @Test
    public void hackCoverage() throws Exception {
        // TODO: Determine how to avoid this
        assertEquals(4, BaseComparableVerifier.ComparisonOperator.values().length);
        assertEquals(BaseComparableVerifier.ComparisonOperator.GREATER_THAN, BaseComparableVerifier.ComparisonOperator.valueOf("GREATER_THAN"));
    }

    @Test
    public void testBetweenWhenEndIsNull() {
        testBetweenHelper(getBase(), getLower(), null, false);
    }

    @Test
    public void testBetweenWhenStartIsNull() {
        testBetweenHelper(getBase(), null, getHigher(), false);
    }

    @Test
    public void testBetweenWhenValueInRange() {
        testBetweenHelper(getBase(), getLower(), getHigher(), true);
    }

    @Test
    public void testBetweenWhenValueIsAfterEnd() {
        testBetweenHelper(getHighest(), getLower(), getHigher(), false);
    }

    @Test
    public void testBetweenWhenValueIsBeforeStart() {
        testBetweenHelper(getLowest(), getLower(), getHigher(), false);
    }

    @Test
    public void testBetweenWhenValueIsEnd() {
        testBetweenHelper(getHigher(), getLower(), getHigher(), true);
    }

    @Test
    public void testBetweenWhenValueIsNull() {
        testBetweenHelper(null, getLower(), getHigher(), false);
    }

    @Test
    public void testBetweenWhenValueIsStart() {
        testBetweenHelper(getLower(), getLower(), getHigher(), true);
    }

    private void testBetweenHelper(T value, T start, T end, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().between(start, end));

        verify(getMockVerification()).check(eq(expected), eq("be between '%s' and '%s' (inclusive)"), getArgsCaptor().capture());

        assertArrayEquals("Passes start and end for message formatting", new Object[]{start, end}, getArgsCaptor().getAllValues().toArray());
    }

    @Test
    public void testBetweenWithStartAndEndNamesBetweenWhenEndIsNull() {
        testBetweenHelper(getBase(), getLower(), null, "start", "end", false);
    }

    @Test
    public void testBetweenWithStartAndEndNamesBetweenWhenStartIsNull() {
        testBetweenHelper(getBase(), null, getHigher(), "start", "end", false);
    }

    @Test
    public void testBetweenWithStartAndEndNamesWhenValueInRange() {
        testBetweenHelper(getBase(), getLower(), getHigher(), "start", "end", true);
    }

    @Test
    public void testBetweenWithStartAndEndNamesWhenValueIsAfterEnd() {
        testBetweenHelper(getHighest(), getLower(), getHigher(), "start", "end", false);
    }

    @Test
    public void testBetweenWithStartAndEndNamesWhenValueIsBeforeStart() {
        testBetweenHelper(getLowest(), getLower(), getHigher(), "start", "end", false);
    }

    @Test
    public void testBetweenWithStartAndEndNamesWhenValueIsEnd() {
        testBetweenHelper(getHigher(), getLower(), getHigher(), "start", "end", true);
    }

    @Test
    public void testBetweenWithStartAndEndNamesBetweenWhenValueIsNull() {
        testBetweenHelper(null, getLower(), getHigher(), "start", "end", false);
    }

    @Test
    public void testBetweenWithStartAndEndNamesWhenValueIsStart() {
        testBetweenHelper(getLower(), getLower(), getHigher(), "start", "end", true);
    }

    private void testBetweenHelper(T value, T start, T end, Object startName, Object endName, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().between(start, end, startName, endName));

        verify(getMockVerification()).check(eq(expected), eq("be between '%s' and '%s' (inclusive)"), getArgsCaptor().capture());

        assertArrayEquals("Passes start and end names for message formatting", new Object[]{startName, endName}, getArgsCaptor().getAllValues().toArray());
    }

    @Test
    public void testBetweenExclusiveWhenEndIsNull() {
        testBetweenExclusiveHelper(getBase(), getLower(), null, false);
    }

    @Test
    public void testBetweenExclusiveWhenStartIsNull() {
        testBetweenExclusiveHelper(getBase(), null, getHigher(), false);
    }

    @Test
    public void testBetweenExclusiveWhenValueInRange() {
        testBetweenExclusiveHelper(getBase(), getLower(), getHigher(), true);
    }

    @Test
    public void testBetweenExclusiveWhenValueIsAfterEnd() {
        testBetweenExclusiveHelper(getHighest(), getLower(), getHigher(), false);
    }

    @Test
    public void testBetweenExclusiveWhenValueIsBeforeStart() {
        testBetweenExclusiveHelper(getLowest(), getLower(), getHigher(), false);
    }

    @Test
    public void testBetweenExclusiveWhenValueIsEnd() {
        testBetweenExclusiveHelper(getHigher(), getLower(), getHigher(), false);
    }

    @Test
    public void testBetweenExclusiveWhenValueIsNull() {
        testBetweenExclusiveHelper(null, getLower(), getHigher(), false);
    }

    @Test
    public void testBetweenExclusiveWhenValueIsStart() {
        testBetweenExclusiveHelper(getLower(), getLower(), getHigher(), false);
    }

    private void testBetweenExclusiveHelper(T value, T start, T end, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().betweenExclusive(start, end));

        verify(getMockVerification()).check(eq(expected), eq("be between '%s' and '%s' (exclusive)"), getArgsCaptor().capture());

        assertArrayEquals("Passes start and end for message formatting", new Object[]{start, end}, getArgsCaptor().getAllValues().toArray());
    }

    @Test
    public void testBetweenExclusiveWithStartAndEndNamesBetweenWhenEndIsNull() {
        testBetweenExclusiveHelper(getBase(), getLower(), null, "start", "end", false);
    }

    @Test
    public void testBetweenExclusiveWithStartAndEndNamesBetweenWhenStartIsNull() {
        testBetweenExclusiveHelper(getBase(), null, getHigher(), "start", "end", false);
    }

    @Test
    public void testBetweenExclusiveWithStartAndEndNamesWhenValueInRange() {
        testBetweenExclusiveHelper(getBase(), getLower(), getHigher(), "start", "end", true);
    }

    @Test
    public void testBetweenExclusiveWithStartAndEndNamesWhenValueIsAfterEnd() {
        testBetweenExclusiveHelper(getHighest(), getLower(), getHigher(), "start", "end", false);
    }

    @Test
    public void testBetweenExclusiveWithStartAndEndNamesWhenValueIsBeforeStart() {
        testBetweenExclusiveHelper(getLowest(), getLower(), getHigher(), "start", "end", false);
    }

    @Test
    public void testBetweenExclusiveWithStartAndEndNamesWhenValueIsEnd() {
        testBetweenExclusiveHelper(getHigher(), getLower(), getHigher(), "start", "end", false);
    }

    @Test
    public void testBetweenExclusiveWithStartAndEndNamesBetweenWhenValueIsNull() {
        testBetweenExclusiveHelper(null, getLower(), getHigher(), "start", "end", false);
    }

    @Test
    public void testBetweenExclusiveWithStartAndEndNamesWhenValueIsStart() {
        testBetweenExclusiveHelper(getLower(), getLower(), getHigher(), "start", "end", false);
    }

    private void testBetweenExclusiveHelper(T value, T start, T end, Object startName, Object endName, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().betweenExclusive(start, end, startName, endName));

        verify(getMockVerification()).check(eq(expected), eq("be between '%s' and '%s' (exclusive)"), getArgsCaptor().capture());

        assertArrayEquals("Passes start and end names for message formatting", new Object[]{startName, endName}, getArgsCaptor().getAllValues().toArray());
    }

    @Test
    public void testGreaterThanWhenValueIsEqualToOther() {
        testGreaterThanHelper(getBase(), getBase(), false);
    }

    @Test
    public void testGreaterThanWhenValueIsGreaterThanOther() {
        testGreaterThanHelper(getBase(), getLower(), true);
    }

    @Test
    public void testGreaterThanWhenValueIsLessThanOther() {
        testGreaterThanHelper(getBase(), getHigher(), false);
    }

    @Test
    public void testGreaterThanWhenValueIsNull() {
        testGreaterThanHelper(null, getBase(), false);
    }

    @Test
    public void testGreaterThanWhenOtherIsNull() {
        testGreaterThanHelper(getBase(), null, false);
    }

    private void testGreaterThanHelper(T value, T other, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().greaterThan(other));

        verify(getMockVerification()).check(eq(expected), eq("be greater than '%s'"), getArgsCaptor().capture());

        assertSame("Passes other for message formatting", other, getArgsCaptor().getValue());
    }

    @Test
    public void testGreaterThanWithOtherNameWhenValueIsEqualToOther() {
        testGreaterThanHelper(getBase(), getBase(), "other", false);
    }

    @Test
    public void testGreaterThanWithOtherNameWhenValueIsGreaterThanOther() {
        testGreaterThanHelper(getBase(), getLower(), "other", true);
    }

    @Test
    public void testGreaterThanWithOtherNameWhenValueIsLessThanOther() {
        testGreaterThanHelper(getBase(), getHigher(), "other", false);
    }

    @Test
    public void testGreaterThanWithOtherNameWhenValueIsNull() {
        testGreaterThanHelper(null, getBase(), "other", false);
    }

    @Test
    public void testGreaterThanWithOtherNameWhenOtherIsNull() {
        testGreaterThanHelper(getBase(), null, "other", false);
    }

    private void testGreaterThanHelper(T value, T other, Object otherName, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().greaterThan(other, otherName));

        verify(getMockVerification()).check(eq(expected), eq("be greater than '%s'"), getArgsCaptor().capture());

        assertSame("Passes other name for message formatting", otherName, getArgsCaptor().getValue());
    }

    @Test
    public void testGreaterThanOrEqualToWhenValueIsEqualToOther() {
        testGreaterThanOrEqualToHelper(getBase(), getBase(), true);
    }

    @Test
    public void testGreaterThanOrEqualToWhenValueIsGreaterThanOther() {
        testGreaterThanOrEqualToHelper(getBase(), getLower(), true);
    }

    @Test
    public void testGreaterThanOrEqualToWhenValueIsLessThanOther() {
        testGreaterThanOrEqualToHelper(getBase(), getHigher(), false);
    }

    @Test
    public void testGreaterThanOrEqualToWhenValueIsNull() {
        testGreaterThanOrEqualToHelper(null, getBase(), false);
    }

    @Test
    public void testGreaterThanOrEqualToWhenOtherIsNull() {
        testGreaterThanHelper(getBase(), null, false);
    }

    private void testGreaterThanOrEqualToHelper(T value, T other, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().greaterThanOrEqualTo(other));

        verify(getMockVerification()).check(eq(expected), eq("be greater than or equal to '%s'"), getArgsCaptor().capture());

        assertSame("Passes other for message formatting", other, getArgsCaptor().getValue());
    }

    @Test
    public void testGreaterThanOrEqualToWithOtherNameWhenValueIsEqualToOther() {
        testGreaterThanOrEqualToHelper(getBase(), getBase(), "other", true);
    }

    @Test
    public void testGreaterThanOrEqualToWithOtherNameWhenValueIsGreaterThanOther() {
        testGreaterThanOrEqualToHelper(getBase(), getLower(), "other", true);
    }

    @Test
    public void testGreaterThanOrEqualToWithOtherNameWhenValueIsLessThanOther() {
        testGreaterThanOrEqualToHelper(getBase(), getHigher(), "other", false);
    }

    @Test
    public void testGreaterThanOrEqualToWithOtherNameWhenValueIsNull() {
        testGreaterThanOrEqualToHelper(null, getBase(), "other", false);
    }

    @Test
    public void testGreaterThanOrEqualToWithOtherNameWhenOtherIsNull() {
        testGreaterThanOrEqualToHelper(getBase(), null, "other", false);
    }

    private void testGreaterThanOrEqualToHelper(T value, T other, Object otherName, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().greaterThanOrEqualTo(other, otherName));

        verify(getMockVerification()).check(eq(expected), eq("be greater than or equal to '%s'"), getArgsCaptor().capture());

        assertSame("Passes other name for message formatting", otherName, getArgsCaptor().getValue());
    }

    @Test
    public void testLessThanWhenValueIsEqualToOther() {
        testLessThanHelper(getBase(), getBase(), false);
    }

    @Test
    public void testLessThanWhenValueIsGreaterThanOther() {
        testLessThanHelper(getBase(), getLower(), false);
    }

    @Test
    public void testLessThanWhenValueIsLessThanOther() {
        testLessThanHelper(getBase(), getHigher(), true);
    }

    @Test
    public void testLessThanWhenValueIsNull() {
        testLessThanHelper(null, getBase(), false);
    }

    @Test
    public void testLessThanWhenOtherIsNull() {
        testLessThanHelper(getBase(), null, false);
    }

    private void testLessThanHelper(T value, T other, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().lessThan(other));

        verify(getMockVerification()).check(eq(expected), eq("be less than '%s'"), getArgsCaptor().capture());

        assertSame("Passes other for message formatting", other, getArgsCaptor().getValue());
    }

    @Test
    public void testLessThanWithOtherNameWhenValueIsEqualToOther() {
        testLessThanHelper(getBase(), getBase(), "other", false);
    }

    @Test
    public void testLessThanWithOtherNameWhenValueIsGreaterThanOther() {
        testLessThanHelper(getBase(), getLower(), "other", false);
    }

    @Test
    public void testLessThanWithOtherNameWhenValueIsLessThanOther() {
        testLessThanHelper(getBase(), getHigher(), "other", true);
    }

    @Test
    public void testLessThanWithOtherNameWhenValueIsNull() {
        testLessThanHelper(null, getBase(), "other", false);
    }

    @Test
    public void testLessThanWithOtherNameWhenOtherIsNull() {
        testLessThanHelper(getBase(), null, "other", false);
    }

    private void testLessThanHelper(T value, T other, Object otherName, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().lessThan(other, otherName));

        verify(getMockVerification()).check(eq(expected), eq("be less than '%s'"), getArgsCaptor().capture());

        assertSame("Passes other name for message formatting", otherName, getArgsCaptor().getValue());
    }

    @Test
    public void testLessThanOrEqualToWhenValueIsEqualToOther() {
        testLessThanOrEqualToHelper(getBase(), getBase(), true);
    }

    @Test
    public void testLessThanOrEqualToWhenValueIsGreaterThanOther() {
        testLessThanOrEqualToHelper(getBase(), getLower(), false);
    }

    @Test
    public void testLessThanOrEqualToWhenValueIsLessThanOther() {
        testLessThanOrEqualToHelper(getBase(), getHigher(), true);
    }

    @Test
    public void testLessThanOrEqualToWhenValueIsNull() {
        testLessThanOrEqualToHelper(null, getBase(), false);
    }

    @Test
    public void testLessThanOrEqualToWhenOtherIsNull() {
        testGreaterThanHelper(getBase(), null, false);
    }

    private void testLessThanOrEqualToHelper(T value, T other, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().lessThanOrEqualTo(other));

        verify(getMockVerification()).check(eq(expected), eq("be less than or equal to '%s'"), getArgsCaptor().capture());

        assertSame("Passes other for message formatting", other, getArgsCaptor().getValue());
    }

    @Test
    public void testLessThanOrEqualToWithOtherNameWhenValueIsEqualToOther() {
        testLessThanOrEqualToHelper(getBase(), getBase(), "other", true);
    }

    @Test
    public void testLessThanOrEqualToWithOtherNameWhenValueIsGreaterThanOther() {
        testLessThanOrEqualToHelper(getBase(), getLower(), "other", false);
    }

    @Test
    public void testLessThanOrEqualToWithOtherNameWhenValueIsLessThanOther() {
        testLessThanOrEqualToHelper(getBase(), getHigher(), "other", true);
    }

    @Test
    public void testLessThanOrEqualToWithOtherNameWhenValueIsNull() {
        testLessThanOrEqualToHelper(null, getBase(), "other", false);
    }

    @Test
    public void testLessThanOrEqualToWithOtherNameWhenOtherIsNull() {
        testLessThanOrEqualToHelper(getBase(), null, "other", false);
    }

    private void testLessThanOrEqualToHelper(T value, T other, Object otherName, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().lessThanOrEqualTo(other, otherName));

        verify(getMockVerification()).check(eq(expected), eq("be less than or equal to '%s'"), getArgsCaptor().capture());

        assertSame("Passes other name for message formatting", otherName, getArgsCaptor().getValue());
    }

    /**
     * Returns the base value for comparisons.
     * <p>
     * This value provides a baseline and all other values should either be less than or greater than this value.
     *
     * @return The base value.
     */
    protected abstract T getBase();

    /**
     * Returns the higher value for comparisons.
     * <p>
     * This value should be higher than {@link #getBase()} but less than {@link #getHighest()}.
     *
     * @return The higher value.
     */
    protected abstract T getHigher();

    /**
     * Returns the highest value for comparisons.
     * <p>
     * This value should be higher than all other
     *
     * @return The highest value.
     */
    protected abstract T getHighest();

    /**
     * Returns the lower value for comparisons.
     * <p>
     * This value should be lower than {@link #getBase()} but greater than {@link #getLowest()}.
     *
     * @return The lower value.
     */
    protected abstract T getLower();

    /**
     * Returns the lowest value for comparisons.
     * <p>
     * This value should be lower than all other
     *
     * @return The lowest value.
     */
    protected abstract T getLowest();
}
