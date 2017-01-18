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
import static org.mockito.Mockito.*;

import org.junit.Test;

import io.skelp.verifier.CustomVerifierTestCaseBase;

/**
 * <p>
 * Test case for {@link BaseComparableVerifier} implementation classes.
 * </p>
 *
 * @param <T>
 *         the {@code Comparable} value type for the {@link BaseComparableVerifier} being tested
 * @param <V>
 *         the type of the {@link BaseComparableVerifier} being tested
 * @author Alasdair Mercer
 */
public abstract class BaseComparableVerifierTestCase<T extends Comparable<? super T>, V extends BaseComparableVerifier<T, V>> extends CustomVerifierTestCaseBase<T, V> {

    @Test
    public void hackCoverage() throws Exception {
        // TODO: Determine how to avoid this
        assertEquals(4, BaseComparableVerifier.ComparisonOperator.values().length);
        assertEquals(BaseComparableVerifier.ComparisonOperator.GREATER_THAN, BaseComparableVerifier.ComparisonOperator.valueOf("GREATER_THAN"));
    }

    @Test
    public void testBetweenWhenEndIsLessThanStart() {
        testBetweenHelper(getBaseValue(), getHigherValue(), getLowerValue(), false);
    }

    @Test
    public void testBetweenWhenEndIsNull() {
        testBetweenHelper(getBaseValue(), getLowerValue(), null, false);
    }

    @Test
    public void testBetweenWhenStartIsNull() {
        testBetweenHelper(getBaseValue(), null, getHigherValue(), false);
    }

    @Test
    public void testBetweenWhenValueInRange() {
        testBetweenHelper(getBaseValue(), getLowerValue(), getHigherValue(), true);
    }

    @Test
    public void testBetweenWhenValueIsAfterEnd() {
        testBetweenHelper(getHighestValue(), getLowerValue(), getHigherValue(), false);
    }

    @Test
    public void testBetweenWhenValueIsBeforeStart() {
        testBetweenHelper(getLowestValue(), getLowerValue(), getHigherValue(), false);
    }

    @Test
    public void testBetweenWhenValueIsEnd() {
        testBetweenHelper(getHigherValue(), getLowerValue(), getHigherValue(), true);
    }

    @Test
    public void testBetweenWhenValueIsNull() {
        testBetweenHelper(null, getLowerValue(), getHigherValue(), false);
    }

    @Test
    public void testBetweenWhenValueIsStart() {
        testBetweenHelper(getLowerValue(), getLowerValue(), getHigherValue(), true);
    }

    private void testBetweenHelper(T value, T start, T end, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().between(start, end));

        verify(getMockVerification()).check(eq(expected), eq("be between '%s' and '%s' (inclusive)"), getArgsCaptor().capture());

        assertArrayEquals("Passes start and end for message formatting", new Object[]{start, end}, getArgsCaptor().getAllValues().toArray());
    }

    @Test
    public void testBetweenWithStartAndEndNamesWhenEndIsLessThanStart() {
        testBetweenHelper(getBaseValue(), getHigherValue(), getLowerValue(), "start", "end", false);
    }

    @Test
    public void testBetweenWithStartAndEndNamesBetweenWhenEndIsNull() {
        testBetweenHelper(getBaseValue(), getLowerValue(), null, "start", "end", false);
    }

    @Test
    public void testBetweenWithStartAndEndNamesBetweenWhenStartIsNull() {
        testBetweenHelper(getBaseValue(), null, getHigherValue(), "start", "end", false);
    }

    @Test
    public void testBetweenWithStartAndEndNamesWhenValueInRange() {
        testBetweenHelper(getBaseValue(), getLowerValue(), getHigherValue(), "start", "end", true);
    }

    @Test
    public void testBetweenWithStartAndEndNamesWhenValueIsAfterEnd() {
        testBetweenHelper(getHighestValue(), getLowerValue(), getHigherValue(), "start", "end", false);
    }

    @Test
    public void testBetweenWithStartAndEndNamesWhenValueIsBeforeStart() {
        testBetweenHelper(getLowestValue(), getLowerValue(), getHigherValue(), "start", "end", false);
    }

    @Test
    public void testBetweenWithStartAndEndNamesWhenValueIsEnd() {
        testBetweenHelper(getHigherValue(), getLowerValue(), getHigherValue(), "start", "end", true);
    }

    @Test
    public void testBetweenWithStartAndEndNamesBetweenWhenValueIsNull() {
        testBetweenHelper(null, getLowerValue(), getHigherValue(), "start", "end", false);
    }

    @Test
    public void testBetweenWithStartAndEndNamesWhenValueIsStart() {
        testBetweenHelper(getLowerValue(), getLowerValue(), getHigherValue(), "start", "end", true);
    }

    private void testBetweenHelper(T value, T start, T end, Object startName, Object endName, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().between(start, end, startName, endName));

        verify(getMockVerification()).check(eq(expected), eq("be between '%s' and '%s' (inclusive)"), getArgsCaptor().capture());

        assertArrayEquals("Passes start and end names for message formatting", new Object[]{startName, endName}, getArgsCaptor().getAllValues().toArray());
    }

    @Test
    public void testBetweenExclusiveWhenEndIsLessThanStart() {
        testBetweenExclusiveHelper(getBaseValue(), getHigherValue(), getLowerValue(), false);
    }

    @Test
    public void testBetweenExclusiveWhenEndIsNull() {
        testBetweenExclusiveHelper(getBaseValue(), getLowerValue(), null, false);
    }

    @Test
    public void testBetweenExclusiveWhenStartIsNull() {
        testBetweenExclusiveHelper(getBaseValue(), null, getHigherValue(), false);
    }

    @Test
    public void testBetweenExclusiveWhenValueInRange() {
        testBetweenExclusiveHelper(getBaseValue(), getLowerValue(), getHigherValue(), true);
    }

    @Test
    public void testBetweenExclusiveWhenValueIsAfterEnd() {
        testBetweenExclusiveHelper(getHighestValue(), getLowerValue(), getHigherValue(), false);
    }

    @Test
    public void testBetweenExclusiveWhenValueIsBeforeStart() {
        testBetweenExclusiveHelper(getLowestValue(), getLowerValue(), getHigherValue(), false);
    }

    @Test
    public void testBetweenExclusiveWhenValueIsEnd() {
        testBetweenExclusiveHelper(getHigherValue(), getLowerValue(), getHigherValue(), false);
    }

    @Test
    public void testBetweenExclusiveWhenValueIsNull() {
        testBetweenExclusiveHelper(null, getLowerValue(), getHigherValue(), false);
    }

    @Test
    public void testBetweenExclusiveWhenValueIsStart() {
        testBetweenExclusiveHelper(getLowerValue(), getLowerValue(), getHigherValue(), false);
    }

    private void testBetweenExclusiveHelper(T value, T start, T end, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().betweenExclusive(start, end));

        verify(getMockVerification()).check(eq(expected), eq("be between '%s' and '%s' (exclusive)"), getArgsCaptor().capture());

        assertArrayEquals("Passes start and end for message formatting", new Object[]{start, end}, getArgsCaptor().getAllValues().toArray());
    }

    @Test
    public void testBetweenExclusiveWithStartAndEndNamesWhenEndIsLessThanStart() {
        testBetweenExclusiveHelper(getBaseValue(), getHigherValue(), getLowerValue(), "start", "end", false);
    }

    @Test
    public void testBetweenExclusiveWithStartAndEndNamesBetweenWhenEndIsNull() {
        testBetweenExclusiveHelper(getBaseValue(), getLowerValue(), null, "start", "end", false);
    }

    @Test
    public void testBetweenExclusiveWithStartAndEndNamesBetweenWhenStartIsNull() {
        testBetweenExclusiveHelper(getBaseValue(), null, getHigherValue(), "start", "end", false);
    }

    @Test
    public void testBetweenExclusiveWithStartAndEndNamesWhenValueInRange() {
        testBetweenExclusiveHelper(getBaseValue(), getLowerValue(), getHigherValue(), "start", "end", true);
    }

    @Test
    public void testBetweenExclusiveWithStartAndEndNamesWhenValueIsAfterEnd() {
        testBetweenExclusiveHelper(getHighestValue(), getLowerValue(), getHigherValue(), "start", "end", false);
    }

    @Test
    public void testBetweenExclusiveWithStartAndEndNamesWhenValueIsBeforeStart() {
        testBetweenExclusiveHelper(getLowestValue(), getLowerValue(), getHigherValue(), "start", "end", false);
    }

    @Test
    public void testBetweenExclusiveWithStartAndEndNamesWhenValueIsEnd() {
        testBetweenExclusiveHelper(getHigherValue(), getLowerValue(), getHigherValue(), "start", "end", false);
    }

    @Test
    public void testBetweenExclusiveWithStartAndEndNamesBetweenWhenValueIsNull() {
        testBetweenExclusiveHelper(null, getLowerValue(), getHigherValue(), "start", "end", false);
    }

    @Test
    public void testBetweenExclusiveWithStartAndEndNamesWhenValueIsStart() {
        testBetweenExclusiveHelper(getLowerValue(), getLowerValue(), getHigherValue(), "start", "end", false);
    }

    private void testBetweenExclusiveHelper(T value, T start, T end, Object startName, Object endName, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().betweenExclusive(start, end, startName, endName));

        verify(getMockVerification()).check(eq(expected), eq("be between '%s' and '%s' (exclusive)"), getArgsCaptor().capture());

        assertArrayEquals("Passes start and end names for message formatting", new Object[]{startName, endName}, getArgsCaptor().getAllValues().toArray());
    }

    @Test
    public void testGreaterThanWhenOtherIsNull() {
        testGreaterThanHelper(getBaseValue(), null, false);
    }

    @Test
    public void testGreaterThanWhenValueAndOtherAreNull() {
        testGreaterThanHelper(null, null, false);
    }

    @Test
    public void testGreaterThanWhenValueIsEqualToOther() {
        testGreaterThanHelper(getBaseValue(), getBaseValue(), false);
    }

    @Test
    public void testGreaterThanWhenValueIsGreaterThanOther() {
        testGreaterThanHelper(getBaseValue(), getLowerValue(), true);
    }

    @Test
    public void testGreaterThanWhenValueIsLessThanOther() {
        testGreaterThanHelper(getBaseValue(), getHigherValue(), false);
    }

    @Test
    public void testGreaterThanWhenValueIsNull() {
        testGreaterThanHelper(null, getBaseValue(), false);
    }

    private void testGreaterThanHelper(T value, T other, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().greaterThan(other));

        verify(getMockVerification()).check(eq(expected), eq("be greater than '%s'"), getArgsCaptor().capture());

        assertSame("Passes other for message formatting", other, getArgsCaptor().getValue());
    }

    @Test
    public void testGreaterThanWithOtherNameWhenOtherIsNull() {
        testGreaterThanHelper(getBaseValue(), null, "other", false);
    }

    @Test
    public void testGreaterThanWithOtherNameWhenValueAndOtherAreNull() {
        testGreaterThanHelper(null, null, "other", false);
    }

    @Test
    public void testGreaterThanWithOtherNameWhenValueIsEqualToOther() {
        testGreaterThanHelper(getBaseValue(), getBaseValue(), "other", false);
    }

    @Test
    public void testGreaterThanWithOtherNameWhenValueIsGreaterThanOther() {
        testGreaterThanHelper(getBaseValue(), getLowerValue(), "other", true);
    }

    @Test
    public void testGreaterThanWithOtherNameWhenValueIsLessThanOther() {
        testGreaterThanHelper(getBaseValue(), getHigherValue(), "other", false);
    }

    @Test
    public void testGreaterThanWithOtherNameWhenValueIsNull() {
        testGreaterThanHelper(null, getBaseValue(), "other", false);
    }

    private void testGreaterThanHelper(T value, T other, Object otherName, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().greaterThan(other, otherName));

        verify(getMockVerification()).check(eq(expected), eq("be greater than '%s'"), getArgsCaptor().capture());

        assertSame("Passes other name for message formatting", otherName, getArgsCaptor().getValue());
    }

    @Test
    public void testGreaterThanOrEqualToWhenOtherIsNull() {
        testGreaterThanHelper(getBaseValue(), null, false);
    }

    @Test
    public void testGreaterThanOrEqualToWhenValueAndOtherAreNull() {
        testGreaterThanOrEqualToHelper(null, null, true);
    }

    @Test
    public void testGreaterThanOrEqualToWhenValueIsEqualToOther() {
        testGreaterThanOrEqualToHelper(getBaseValue(), getBaseValue(), true);
    }

    @Test
    public void testGreaterThanOrEqualToWhenValueIsGreaterThanOther() {
        testGreaterThanOrEqualToHelper(getBaseValue(), getLowerValue(), true);
    }

    @Test
    public void testGreaterThanOrEqualToWhenValueIsLessThanOther() {
        testGreaterThanOrEqualToHelper(getBaseValue(), getHigherValue(), false);
    }

    @Test
    public void testGreaterThanOrEqualToWhenValueIsNull() {
        testGreaterThanOrEqualToHelper(null, getBaseValue(), false);
    }

    private void testGreaterThanOrEqualToHelper(T value, T other, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().greaterThanOrEqualTo(other));

        verify(getMockVerification()).check(eq(expected), eq("be greater than or equal to '%s'"), getArgsCaptor().capture());

        assertSame("Passes other for message formatting", other, getArgsCaptor().getValue());
    }

    @Test
    public void testGreaterThanOrEqualToWithOtherNameWhenOtherIsNull() {
        testGreaterThanOrEqualToHelper(getBaseValue(), null, "other", false);
    }

    @Test
    public void testGreaterThanOrEqualToWithOtherNameWhenValueAndOtherAreNull() {
        testGreaterThanOrEqualToHelper(null, null, "other", true);
    }

    @Test
    public void testGreaterThanOrEqualToWithOtherNameWhenValueIsEqualToOther() {
        testGreaterThanOrEqualToHelper(getBaseValue(), getBaseValue(), "other", true);
    }

    @Test
    public void testGreaterThanOrEqualToWithOtherNameWhenValueIsGreaterThanOther() {
        testGreaterThanOrEqualToHelper(getBaseValue(), getLowerValue(), "other", true);
    }

    @Test
    public void testGreaterThanOrEqualToWithOtherNameWhenValueIsLessThanOther() {
        testGreaterThanOrEqualToHelper(getBaseValue(), getHigherValue(), "other", false);
    }

    @Test
    public void testGreaterThanOrEqualToWithOtherNameWhenValueIsNull() {
        testGreaterThanOrEqualToHelper(null, getBaseValue(), "other", false);
    }

    private void testGreaterThanOrEqualToHelper(T value, T other, Object otherName, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().greaterThanOrEqualTo(other, otherName));

        verify(getMockVerification()).check(eq(expected), eq("be greater than or equal to '%s'"), getArgsCaptor().capture());

        assertSame("Passes other name for message formatting", otherName, getArgsCaptor().getValue());
    }

    @Test
    public void testLessThanWhenOtherIsNull() {
        testLessThanHelper(getBaseValue(), null, false);
    }

    @Test
    public void testLessThanWhenValueAndOtherAreNull() {
        testLessThanHelper(null, null, false);
    }

    @Test
    public void testLessThanWhenValueIsEqualToOther() {
        testLessThanHelper(getBaseValue(), getBaseValue(), false);
    }

    @Test
    public void testLessThanWhenValueIsGreaterThanOther() {
        testLessThanHelper(getBaseValue(), getLowerValue(), false);
    }

    @Test
    public void testLessThanWhenValueIsLessThanOther() {
        testLessThanHelper(getBaseValue(), getHigherValue(), true);
    }

    @Test
    public void testLessThanWhenValueIsNull() {
        testLessThanHelper(null, getBaseValue(), false);
    }

    private void testLessThanHelper(T value, T other, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().lessThan(other));

        verify(getMockVerification()).check(eq(expected), eq("be less than '%s'"), getArgsCaptor().capture());

        assertSame("Passes other for message formatting", other, getArgsCaptor().getValue());
    }

    @Test
    public void testLessThanWithOtherNameWhenOtherIsNull() {
        testLessThanHelper(getBaseValue(), null, "other", false);
    }

    @Test
    public void testLessThanWithOtherNameWhenValueAndOtherAreNull() {
        testLessThanHelper(null, null, "other", false);
    }

    @Test
    public void testLessThanWithOtherNameWhenValueIsEqualToOther() {
        testLessThanHelper(getBaseValue(), getBaseValue(), "other", false);
    }

    @Test
    public void testLessThanWithOtherNameWhenValueIsGreaterThanOther() {
        testLessThanHelper(getBaseValue(), getLowerValue(), "other", false);
    }

    @Test
    public void testLessThanWithOtherNameWhenValueIsLessThanOther() {
        testLessThanHelper(getBaseValue(), getHigherValue(), "other", true);
    }

    @Test
    public void testLessThanWithOtherNameWhenValueIsNull() {
        testLessThanHelper(null, getBaseValue(), "other", false);
    }

    private void testLessThanHelper(T value, T other, Object otherName, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().lessThan(other, otherName));

        verify(getMockVerification()).check(eq(expected), eq("be less than '%s'"), getArgsCaptor().capture());

        assertSame("Passes other name for message formatting", otherName, getArgsCaptor().getValue());
    }

    @Test
    public void testLessThanOrEqualToWhenOtherIsNull() {
        testGreaterThanHelper(getBaseValue(), null, false);
    }

    @Test
    public void testLessThanOrEqualToWhenValueAndOtherAreNull() {
        testLessThanOrEqualToHelper(null, null, true);
    }

    @Test
    public void testLessThanOrEqualToWhenValueIsEqualToOther() {
        testLessThanOrEqualToHelper(getBaseValue(), getBaseValue(), true);
    }

    @Test
    public void testLessThanOrEqualToWhenValueIsGreaterThanOther() {
        testLessThanOrEqualToHelper(getBaseValue(), getLowerValue(), false);
    }

    @Test
    public void testLessThanOrEqualToWhenValueIsLessThanOther() {
        testLessThanOrEqualToHelper(getBaseValue(), getHigherValue(), true);
    }

    @Test
    public void testLessThanOrEqualToWhenValueIsNull() {
        testLessThanOrEqualToHelper(null, getBaseValue(), false);
    }

    private void testLessThanOrEqualToHelper(T value, T other, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().lessThanOrEqualTo(other));

        verify(getMockVerification()).check(eq(expected), eq("be less than or equal to '%s'"), getArgsCaptor().capture());

        assertSame("Passes other for message formatting", other, getArgsCaptor().getValue());
    }

    @Test
    public void testLessThanOrEqualToWithOtherNameWhenOtherIsNull() {
        testLessThanOrEqualToHelper(getBaseValue(), null, "other", false);
    }

    @Test
    public void testLessThanOrEqualToWithOtherNameWhenValueAndOtherAreNull() {
        testLessThanOrEqualToHelper(null, null, "other", true);
    }

    @Test
    public void testLessThanOrEqualToWithOtherNameWhenValueIsEqualToOther() {
        testLessThanOrEqualToHelper(getBaseValue(), getBaseValue(), "other", true);
    }

    @Test
    public void testLessThanOrEqualToWithOtherNameWhenValueIsGreaterThanOther() {
        testLessThanOrEqualToHelper(getBaseValue(), getLowerValue(), "other", false);
    }

    @Test
    public void testLessThanOrEqualToWithOtherNameWhenValueIsLessThanOther() {
        testLessThanOrEqualToHelper(getBaseValue(), getHigherValue(), "other", true);
    }

    @Test
    public void testLessThanOrEqualToWithOtherNameWhenValueIsNull() {
        testLessThanOrEqualToHelper(null, getBaseValue(), "other", false);
    }

    private void testLessThanOrEqualToHelper(T value, T other, Object otherName, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().lessThanOrEqualTo(other, otherName));

        verify(getMockVerification()).check(eq(expected), eq("be less than or equal to '%s'"), getArgsCaptor().capture());

        assertSame("Passes other name for message formatting", otherName, getArgsCaptor().getValue());
    }

    /**
     * <p>
     * Returns the base value for comparisons.
     * </p>
     * <p>
     * This value provides a baseline and all other values should either be less than or greater than this value.
     * </p>
     *
     * @return The base value.
     */
    protected abstract T getBaseValue();

    /**
     * <p>
     * Returns the higher value for comparisons.
     * </p>
     * <p>
     * This value should be higher than {@link #getBaseValue()} but less than {@link #getHighestValue()}.
     * </p>
     *
     * @return The higher value.
     */
    protected abstract T getHigherValue();

    /**
     * <p>
     * Returns the highest value for comparisons.
     * </p>
     * <p>
     * This value should be higher than all others.
     * </p>
     *
     * @return The highest value.
     */
    protected abstract T getHighestValue();

    /**
     * <p>
     * Returns the lower value for comparisons.
     * </p>
     * <p>
     * This value should be lower than {@link #getBaseValue()} but greater than {@link #getLowestValue()}.
     * </p>
     *
     * @return The lower value.
     */
    protected abstract T getLowerValue();

    /**
     * <p>
     * Returns the lowest value for comparisons.
     * </p>
     * <p>
     * This value should be lower than all others.
     * </p>
     *
     * @return The lowest value.
     */
    protected abstract T getLowestValue();
}
