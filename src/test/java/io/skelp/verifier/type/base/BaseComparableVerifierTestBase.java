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

import io.skelp.verifier.AbstractCustomVerifierTestBase;

/**
 * Base for tests of {@link BaseComparableVerifier} implementation classes.
 *
 * @param <T>
 *         the {@code Comparable} value type for the {@link BaseComparableVerifier} being tested
 * @param <V>
 *         the type of the {@link BaseComparableVerifier} being tested
 * @author Alasdair Mercer
 */
public abstract class BaseComparableVerifierTestBase<T extends Comparable<? super T>, V extends BaseComparableVerifier<T, V>> extends AbstractCustomVerifierTestBase<T, V> {

    // TODO: Test between & betweenExclusive with start greater than end and vice versa

    @Test
    public void testBetweenWhenEndIsNull() {
        ComparableValues<T> values = getComparableValues();

        testBetweenHelper(values.getBase(), values.getLower(), null, false);
    }

    @Test
    public void testBetweenWhenStartIsNull() {
        ComparableValues<T> values = getComparableValues();

        testBetweenHelper(values.getBase(), null, values.getHigher(), false);
    }

    @Test
    public void testBetweenWhenValueInRange() {
        ComparableValues<T> values = getComparableValues();

        testBetweenHelper(values.getBase(), values.getLower(), values.getHigher(), true);
    }

    @Test
    public void testBetweenWhenValueIsAfterEnd() {
        ComparableValues<T> values = getComparableValues();

        testBetweenHelper(values.getHighest(), values.getLower(), values.getHigher(), false);
    }

    @Test
    public void testBetweenWhenValueIsBeforeStart() {
        ComparableValues<T> values = getComparableValues();

        testBetweenHelper(values.getLowest(), values.getLower(), values.getHigher(), false);
    }

    @Test
    public void testBetweenWhenValueIsEnd() {
        ComparableValues<T> values = getComparableValues();

        testBetweenHelper(values.getHigher(), values.getLower(), values.getHigher(), true);
    }

    @Test
    public void testBetweenWhenValueIsNull() {
        ComparableValues<T> values = getComparableValues();

        testBetweenHelper(null, values.getLower(), values.getHigher(), false);
    }

    @Test
    public void testBetweenWhenValueIsStart() {
        ComparableValues<T> values = getComparableValues();

        testBetweenHelper(values.getLower(), values.getLower(), values.getHigher(), true);
    }

    private void testBetweenHelper(T value, T start, T end, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().between(start, end));

        verify(getMockVerification()).check(eq(expected), eq("be between '%s' and '%s' (inclusive)"), getArgsCaptor().capture());

        assertArrayEquals("Passes start and end for message formatting", new Object[]{start, end}, getArgsCaptor().getAllValues().toArray());
    }

    @Test
    public void testBetweenWithStartAndEndNamesBetweenWhenEndIsNull() {
        ComparableValues<T> values = getComparableValues();

        testBetweenHelper(values.getBase(), values.getLower(), null, "start", "end", false);
    }

    @Test
    public void testBetweenWithStartAndEndNamesBetweenWhenStartIsNull() {
        ComparableValues<T> values = getComparableValues();

        testBetweenHelper(values.getBase(), null, values.getHigher(), "start", "end", false);
    }

    @Test
    public void testBetweenWithStartAndEndNamesWhenValueInRange() {
        ComparableValues<T> values = getComparableValues();

        testBetweenHelper(values.getBase(), values.getLower(), values.getHigher(), "start", "end", true);
    }

    @Test
    public void testBetweenWithStartAndEndNamesWhenValueIsAfterEnd() {
        ComparableValues<T> values = getComparableValues();

        testBetweenHelper(values.getHighest(), values.getLower(), values.getHigher(), "start", "end", false);
    }

    @Test
    public void testBetweenWithStartAndEndNamesWhenValueIsBeforeStart() {
        ComparableValues<T> values = getComparableValues();

        testBetweenHelper(values.getLowest(), values.getLower(), values.getHigher(), "start", "end", false);
    }

    @Test
    public void testBetweenWithStartAndEndNamesWhenValueIsEnd() {
        ComparableValues<T> values = getComparableValues();

        testBetweenHelper(values.getHigher(), values.getLower(), values.getHigher(), "start", "end", true);
    }

    @Test
    public void testBetweenWithStartAndEndNamesBetweenWhenValueIsNull() {
        ComparableValues<T> values = getComparableValues();

        testBetweenHelper(null, values.getLower(), values.getHigher(), "start", "end", false);
    }

    @Test
    public void testBetweenWithStartAndEndNamesWhenValueIsStart() {
        ComparableValues<T> values = getComparableValues();

        testBetweenHelper(values.getLower(), values.getLower(), values.getHigher(), "start", "end", true);
    }

    private void testBetweenHelper(T value, T start, T end, Object startName, Object endName, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().between(start, end, startName, endName));

        verify(getMockVerification()).check(eq(expected), eq("be between '%s' and '%s' (inclusive)"), getArgsCaptor().capture());

        assertArrayEquals("Passes start and end names for message formatting", new Object[]{startName, endName}, getArgsCaptor().getAllValues().toArray());
    }

    @Test
    public void testBetweenExclusiveWhenEndIsNull() {
        ComparableValues<T> values = getComparableValues();

        testBetweenExclusiveHelper(values.getBase(), values.getLower(), null, false);
    }

    @Test
    public void testBetweenExclusiveWhenStartIsNull() {
        ComparableValues<T> values = getComparableValues();

        testBetweenExclusiveHelper(values.getBase(), null, values.getHigher(), false);
    }

    @Test
    public void testBetweenExclusiveWhenValueInRange() {
        ComparableValues<T> values = getComparableValues();

        testBetweenExclusiveHelper(values.getBase(), values.getLower(), values.getHigher(), true);
    }

    @Test
    public void testBetweenExclusiveWhenValueIsAfterEnd() {
        ComparableValues<T> values = getComparableValues();

        testBetweenExclusiveHelper(values.getHighest(), values.getLower(), values.getHigher(), false);
    }

    @Test
    public void testBetweenExclusiveWhenValueIsBeforeStart() {
        ComparableValues<T> values = getComparableValues();

        testBetweenExclusiveHelper(values.getLowest(), values.getLower(), values.getHigher(), false);
    }

    @Test
    public void testBetweenExclusiveWhenValueIsEnd() {
        ComparableValues<T> values = getComparableValues();

        testBetweenExclusiveHelper(values.getHigher(), values.getLower(), values.getHigher(), false);
    }

    @Test
    public void testBetweenExclusiveWhenValueIsNull() {
        ComparableValues<T> values = getComparableValues();

        testBetweenExclusiveHelper(null, values.getLower(), values.getHigher(), false);
    }

    @Test
    public void testBetweenExclusiveWhenValueIsStart() {
        ComparableValues<T> values = getComparableValues();

        testBetweenExclusiveHelper(values.getLower(), values.getLower(), values.getHigher(), false);
    }

    private void testBetweenExclusiveHelper(T value, T start, T end, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().betweenExclusive(start, end));

        verify(getMockVerification()).check(eq(expected), eq("be between '%s' and '%s' (exclusive)"), getArgsCaptor().capture());

        assertArrayEquals("Passes start and end for message formatting", new Object[]{start, end}, getArgsCaptor().getAllValues().toArray());
    }

    @Test
    public void testBetweenExclusiveWithStartAndEndNamesBetweenWhenEndIsNull() {
        ComparableValues<T> values = getComparableValues();

        testBetweenExclusiveHelper(values.getBase(), values.getLower(), null, "start", "end", false);
    }

    @Test
    public void testBetweenExclusiveWithStartAndEndNamesBetweenWhenStartIsNull() {
        ComparableValues<T> values = getComparableValues();

        testBetweenExclusiveHelper(values.getBase(), null, values.getHigher(), "start", "end", false);
    }

    @Test
    public void testBetweenExclusiveWithStartAndEndNamesWhenValueInRange() {
        ComparableValues<T> values = getComparableValues();

        testBetweenExclusiveHelper(values.getBase(), values.getLower(), values.getHigher(), "start", "end", true);
    }

    @Test
    public void testBetweenExclusiveWithStartAndEndNamesWhenValueIsAfterEnd() {
        ComparableValues<T> values = getComparableValues();

        testBetweenExclusiveHelper(values.getHighest(), values.getLower(), values.getHigher(), "start", "end", false);
    }

    @Test
    public void testBetweenExclusiveWithStartAndEndNamesWhenValueIsBeforeStart() {
        ComparableValues<T> values = getComparableValues();

        testBetweenExclusiveHelper(values.getLowest(), values.getLower(), values.getHigher(), "start", "end", false);
    }

    @Test
    public void testBetweenExclusiveWithStartAndEndNamesWhenValueIsEnd() {
        ComparableValues<T> values = getComparableValues();

        testBetweenExclusiveHelper(values.getHigher(), values.getLower(), values.getHigher(), "start", "end", false);
    }

    @Test
    public void testBetweenExclusiveWithStartAndEndNamesBetweenWhenValueIsNull() {
        ComparableValues<T> values = getComparableValues();

        testBetweenExclusiveHelper(null, values.getLower(), values.getHigher(), "start", "end", false);
    }

    @Test
    public void testBetweenExclusiveWithStartAndEndNamesWhenValueIsStart() {
        ComparableValues<T> values = getComparableValues();

        testBetweenExclusiveHelper(values.getLower(), values.getLower(), values.getHigher(), "start", "end", false);
    }

    private void testBetweenExclusiveHelper(T value, T start, T end, Object startName, Object endName, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().betweenExclusive(start, end, startName, endName));

        verify(getMockVerification()).check(eq(expected), eq("be between '%s' and '%s' (exclusive)"), getArgsCaptor().capture());

        assertArrayEquals("Passes start and end names for message formatting", new Object[]{startName, endName}, getArgsCaptor().getAllValues().toArray());
    }

    @Test
    public void testGreaterThanWhenValueIsEqualToOther() {
        ComparableValues<T> values = getComparableValues();

        testGreaterThanHelper(values.getBase(), values.getBase(), false);
    }

    @Test
    public void testGreaterThanWhenValueIsGreaterThanOther() {
        ComparableValues<T> values = getComparableValues();

        testGreaterThanHelper(values.getBase(), values.getLower(), true);
    }

    @Test
    public void testGreaterThanWhenValueIsLessThanOther() {
        ComparableValues<T> values = getComparableValues();

        testGreaterThanHelper(values.getBase(), values.getHigher(), false);
    }

    @Test
    public void testGreaterThanWhenValueIsNull() {
        ComparableValues<T> values = getComparableValues();

        testGreaterThanHelper(null, values.getBase(), false);
    }

    @Test
    public void testGreaterThanWhenOtherIsNull() {
        ComparableValues<T> values = getComparableValues();

        testGreaterThanHelper(values.getBase(), null, false);
    }

    private void testGreaterThanHelper(T value, T other, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().greaterThan(other));

        verify(getMockVerification()).check(eq(expected), eq("be greater than '%s'"), getArgsCaptor().capture());

        assertSame("Passes other for message formatting", other, getArgsCaptor().getValue());
    }

    @Test
    public void testGreaterThanWithOtherNameWhenValueIsEqualToOther() {
        ComparableValues<T> values = getComparableValues();

        testGreaterThanHelper(values.getBase(), values.getBase(), "other", false);
    }

    @Test
    public void testGreaterThanWithOtherNameWhenValueIsGreaterThanOther() {
        ComparableValues<T> values = getComparableValues();

        testGreaterThanHelper(values.getBase(), values.getLower(), "other", true);
    }

    @Test
    public void testGreaterThanWithOtherNameWhenValueIsLessThanOther() {
        ComparableValues<T> values = getComparableValues();

        testGreaterThanHelper(values.getBase(), values.getHigher(), "other", false);
    }

    @Test
    public void testGreaterThanWithOtherNameWhenValueIsNull() {
        ComparableValues<T> values = getComparableValues();

        testGreaterThanHelper(null, values.getBase(), "other", false);
    }

    @Test
    public void testGreaterThanWithOtherNameWhenOtherIsNull() {
        ComparableValues<T> values = getComparableValues();

        testGreaterThanHelper(values.getBase(), null, "other", false);
    }

    private void testGreaterThanHelper(T value, T other, Object otherName, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().greaterThan(other, otherName));

        verify(getMockVerification()).check(eq(expected), eq("be greater than '%s'"), getArgsCaptor().capture());

        assertSame("Passes other name for message formatting", otherName, getArgsCaptor().getValue());
    }

    @Test
    public void testGreaterThanOrEqualToWhenValueIsEqualToOther() {
        ComparableValues<T> values = getComparableValues();

        testGreaterThanOrEqualToHelper(values.getBase(), values.getBase(), true);
    }

    @Test
    public void testGreaterThanOrEqualToWhenValueIsGreaterThanOther() {
        ComparableValues<T> values = getComparableValues();

        testGreaterThanOrEqualToHelper(values.getBase(), values.getLower(), true);
    }

    @Test
    public void testGreaterThanOrEqualToWhenValueIsLessThanOther() {
        ComparableValues<T> values = getComparableValues();

        testGreaterThanOrEqualToHelper(values.getBase(), values.getHigher(), false);
    }

    @Test
    public void testGreaterThanOrEqualToWhenValueIsNull() {
        ComparableValues<T> values = getComparableValues();

        testGreaterThanOrEqualToHelper(null, values.getBase(), false);
    }

    @Test
    public void testGreaterThanOrEqualToWhenOtherIsNull() {
        ComparableValues<T> values = getComparableValues();

        testGreaterThanHelper(values.getBase(), null, false);
    }

    private void testGreaterThanOrEqualToHelper(T value, T other, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().greaterThanOrEqualTo(other));

        verify(getMockVerification()).check(eq(expected), eq("be greater than or equal to '%s'"), getArgsCaptor().capture());

        assertSame("Passes other for message formatting", other, getArgsCaptor().getValue());
    }

    @Test
    public void testGreaterThanOrEqualToWithOtherNameWhenValueIsEqualToOther() {
        ComparableValues<T> values = getComparableValues();

        testGreaterThanOrEqualToHelper(values.getBase(), values.getBase(), "other", true);
    }

    @Test
    public void testGreaterThanOrEqualToWithOtherNameWhenValueIsGreaterThanOther() {
        ComparableValues<T> values = getComparableValues();

        testGreaterThanOrEqualToHelper(values.getBase(), values.getLower(), "other", true);
    }

    @Test
    public void testGreaterThanOrEqualToWithOtherNameWhenValueIsLessThanOther() {
        ComparableValues<T> values = getComparableValues();

        testGreaterThanOrEqualToHelper(values.getBase(), values.getHigher(), "other", false);
    }

    @Test
    public void testGreaterThanOrEqualToWithOtherNameWhenValueIsNull() {
        ComparableValues<T> values = getComparableValues();

        testGreaterThanOrEqualToHelper(null, values.getBase(), "other", false);
    }

    @Test
    public void testGreaterThanOrEqualToWithOtherNameWhenOtherIsNull() {
        ComparableValues<T> values = getComparableValues();

        testGreaterThanOrEqualToHelper(values.getBase(), null, "other", false);
    }

    private void testGreaterThanOrEqualToHelper(T value, T other, Object otherName, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().greaterThanOrEqualTo(other, otherName));

        verify(getMockVerification()).check(eq(expected), eq("be greater than or equal to '%s'"), getArgsCaptor().capture());

        assertSame("Passes other name for message formatting", otherName, getArgsCaptor().getValue());
    }

    @Test
    public void testLessThanWhenValueIsEqualToOther() {
        ComparableValues<T> values = getComparableValues();

        testLessThanHelper(values.getBase(), values.getBase(), false);
    }

    @Test
    public void testLessThanWhenValueIsGreaterThanOther() {
        ComparableValues<T> values = getComparableValues();

        testLessThanHelper(values.getBase(), values.getLower(), false);
    }

    @Test
    public void testLessThanWhenValueIsLessThanOther() {
        ComparableValues<T> values = getComparableValues();

        testLessThanHelper(values.getBase(), values.getHigher(), true);
    }

    @Test
    public void testLessThanWhenValueIsNull() {
        ComparableValues<T> values = getComparableValues();

        testLessThanHelper(null, values.getBase(), false);
    }

    @Test
    public void testLessThanWhenOtherIsNull() {
        ComparableValues<T> values = getComparableValues();

        testLessThanHelper(values.getBase(), null, false);
    }

    private void testLessThanHelper(T value, T other, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().lessThan(other));

        verify(getMockVerification()).check(eq(expected), eq("be less than '%s'"), getArgsCaptor().capture());

        assertSame("Passes other for message formatting", other, getArgsCaptor().getValue());
    }

    @Test
    public void testLessThanWithOtherNameWhenValueIsEqualToOther() {
        ComparableValues<T> values = getComparableValues();

        testLessThanHelper(values.getBase(), values.getBase(), "other", false);
    }

    @Test
    public void testLessThanWithOtherNameWhenValueIsGreaterThanOther() {
        ComparableValues<T> values = getComparableValues();

        testLessThanHelper(values.getBase(), values.getLower(), "other", false);
    }

    @Test
    public void testLessThanWithOtherNameWhenValueIsLessThanOther() {
        ComparableValues<T> values = getComparableValues();

        testLessThanHelper(values.getBase(), values.getHigher(), "other", true);
    }

    @Test
    public void testLessThanWithOtherNameWhenValueIsNull() {
        ComparableValues<T> values = getComparableValues();

        testLessThanHelper(null, values.getBase(), "other", false);
    }

    @Test
    public void testLessThanWithOtherNameWhenOtherIsNull() {
        ComparableValues<T> values = getComparableValues();

        testLessThanHelper(values.getBase(), null, "other", false);
    }

    private void testLessThanHelper(T value, T other, Object otherName, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().lessThan(other, otherName));

        verify(getMockVerification()).check(eq(expected), eq("be less than '%s'"), getArgsCaptor().capture());

        assertSame("Passes other name for message formatting", otherName, getArgsCaptor().getValue());
    }

    @Test
    public void testLessThanOrEqualToWhenValueIsEqualToOther() {
        ComparableValues<T> values = getComparableValues();

        testLessThanOrEqualToHelper(values.getBase(), values.getBase(), true);
    }

    @Test
    public void testLessThanOrEqualToWhenValueIsGreaterThanOther() {
        ComparableValues<T> values = getComparableValues();

        testLessThanOrEqualToHelper(values.getBase(), values.getLower(), false);
    }

    @Test
    public void testLessThanOrEqualToWhenValueIsLessThanOther() {
        ComparableValues<T> values = getComparableValues();

        testLessThanOrEqualToHelper(values.getBase(), values.getHigher(), true);
    }

    @Test
    public void testLessThanOrEqualToWhenValueIsNull() {
        ComparableValues<T> values = getComparableValues();

        testLessThanOrEqualToHelper(null, values.getBase(), false);
    }

    @Test
    public void testLessThanOrEqualToWhenOtherIsNull() {
        ComparableValues<T> values = getComparableValues();

        testGreaterThanHelper(values.getBase(), null, false);
    }

    private void testLessThanOrEqualToHelper(T value, T other, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().lessThanOrEqualTo(other));

        verify(getMockVerification()).check(eq(expected), eq("be less than or equal to '%s'"), getArgsCaptor().capture());

        assertSame("Passes other for message formatting", other, getArgsCaptor().getValue());
    }

    @Test
    public void testLessThanOrEqualToWithOtherNameWhenValueIsEqualToOther() {
        ComparableValues<T> values = getComparableValues();

        testLessThanOrEqualToHelper(values.getBase(), values.getBase(), "other", true);
    }

    @Test
    public void testLessThanOrEqualToWithOtherNameWhenValueIsGreaterThanOther() {
        ComparableValues<T> values = getComparableValues();

        testLessThanOrEqualToHelper(values.getBase(), values.getLower(), "other", false);
    }

    @Test
    public void testLessThanOrEqualToWithOtherNameWhenValueIsLessThanOther() {
        ComparableValues<T> values = getComparableValues();

        testLessThanOrEqualToHelper(values.getBase(), values.getHigher(), "other", true);
    }

    @Test
    public void testLessThanOrEqualToWithOtherNameWhenValueIsNull() {
        ComparableValues<T> values = getComparableValues();

        testLessThanOrEqualToHelper(null, values.getBase(), "other", false);
    }

    @Test
    public void testLessThanOrEqualToWithOtherNameWhenOtherIsNull() {
        ComparableValues<T> values = getComparableValues();

        testLessThanOrEqualToHelper(values.getBase(), null, "other", false);
    }

    private void testLessThanOrEqualToHelper(T value, T other, Object otherName, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().lessThanOrEqualTo(other, otherName));

        verify(getMockVerification()).check(eq(expected), eq("be less than or equal to '%s'"), getArgsCaptor().capture());

        assertSame("Passes other name for message formatting", otherName, getArgsCaptor().getValue());
    }

    /**
     * Returns the {@code Comparable} values to be used for testing the {@link BaseComparableVerifier} methods.
     *
     * @return An instance of {@link ComparableValues} containing values for {@code Comparable} testing.
     */
    protected abstract ComparableValues<T> getComparableValues();

    /**
     * Contains specific values which can be used to perform predictable and thorough tests for {@code Comparable}
     * objects.
     * <p/>
     * The different values can be used for range and comparison testing and can cover any {@code Comparable} type.
     *
     * @param <T>
     *         the {@code Comparable} type being tested
     */
    protected interface ComparableValues<T extends Comparable<? super T>> {

        /**
         * Returns the base value for this {@link ComparableValues}.
         * <p>
         * This value provides a baseline and all other values should either be less than or greater than this value.
         *
         * @return The base value.
         */
        T getBase();

        /**
         * Returns the higher value for this {@link ComparableValues}.
         * <p>
         * This value should be higher than {@link #getBase()} but less than {@link #getHighest()}.
         *
         * @return The higher value.
         */
        T getHigher();

        /**
         * Returns the highest value for this {@link ComparableValues}.
         * <p>
         * This value should be higher than all other values.
         *
         * @return The highest value.
         */
        T getHighest();

        /**
         * Returns the lower value for this {@link ComparableValues}.
         * <p>
         * This value should be lower than {@link #getBase()} but greater than {@link #getLowest()}.
         *
         * @return The lower value.
         */
        T getLower();

        /**
         * Returns the lowest value for this {@link ComparableValues}.
         * <p>
         * This value should be lower than all other values.
         *
         * @return The lowest value.
         */
        T getLowest();
    }
}
