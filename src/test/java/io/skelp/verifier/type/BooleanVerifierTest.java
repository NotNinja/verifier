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

import org.junit.Test;

import io.skelp.verifier.AbstractCustomVerifierTestBase;
import io.skelp.verifier.type.base.BaseComparableVerifier;
import io.skelp.verifier.type.base.BaseComparableVerifierTestBase;

/**
 * Tests for the {@link BooleanVerifier} class.
 * <p>
 * Due to the binary nature of booleans this class does not extend {@link BaseComparableVerifierTestBase} and instead
 * performs basic tests on the {@link BaseComparableVerifier} methods for booleans.
 *
 * @author Alasdair Mercer
 */
public class BooleanVerifierTest extends AbstractCustomVerifierTestBase<Boolean, BooleanVerifier> {

    @Test
    public void testBetweenWhenValueIsFalse() {
        testBetweenHelper(false);
    }

    @Test
    public void testBetweenWhenValueIsTrue() {
        testBetweenHelper(true);
    }

    private void testBetweenHelper(Boolean value) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().between(false, true));

        verify(getMockVerification()).check(eq(true), eq("be between '%s' and '%s' (inclusive)"), getArgsCaptor().capture());

        assertArrayEquals("Passes start and end for message formatting", new Object[]{false, true}, getArgsCaptor().getAllValues().toArray());
    }

    @Test
    public void testBetweenExclusiveWhenValueIsFalse() {
        testBetweenExclusiveHelper(false);
    }

    @Test
    public void testBetweenExclusiveWhenValueIsTrue() {
        testBetweenExclusiveHelper(true);
    }

    private void testBetweenExclusiveHelper(Boolean value) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().betweenExclusive(false, true));

        verify(getMockVerification()).check(eq(false), eq("be between '%s' and '%s' (exclusive)"), getArgsCaptor().capture());

        assertArrayEquals("Passes start and end for message formatting", new Object[]{false, true}, getArgsCaptor().getAllValues().toArray());
    }

    @Test
    public void testLessThanWhenValueIsEqualToOther() {
        testLessThanHelper(false, false, false);
    }

    @Test
    public void testLessThanWhenValueIsGreaterThanOther() {
        testLessThanHelper(true, false, false);
    }

    @Test
    public void testLessThanWhenValueIsLessThanOther() {
        testLessThanHelper(false, true, true);
    }

    private void testLessThanHelper(Boolean value, Boolean other, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().lessThan(other));

        verify(getMockVerification()).check(eq(expected), eq("be less than '%s'"), getArgsCaptor().capture());

        assertSame("Passes other for message formatting", other, getArgsCaptor().getValue());
    }

    @Test
    public void testLessThanOrEqualToWhenValueIsEqualToOther() {
        testLessThanOrEqualToHelper(false, false, true);
    }

    @Test
    public void testLessThanOrEqualToWhenValueIsGreaterThanOther() {
        testLessThanOrEqualToHelper(true, false, false);
    }

    @Test
    public void testLessThanOrEqualToWhenValueIsLessThanOther() {
        testLessThanOrEqualToHelper(false, true, true);
    }

    private void testLessThanOrEqualToHelper(Boolean value, Boolean other, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().lessThanOrEqualTo(other));

        verify(getMockVerification()).check(eq(expected), eq("be less than or equal to '%s'"), getArgsCaptor().capture());

        assertSame("Passes other for message formatting", other, getArgsCaptor().getValue());
    }

    @Test
    public void testGreaterThanWhenValueIsEqualToOther() {
        testGreaterThanHelper(false, false, false);
    }

    @Test
    public void testGreaterThanWhenValueIsGreaterThanOther() {
        testGreaterThanHelper(true, false, true);
    }

    @Test
    public void testGreaterWhenValueIsLessThanOther() {
        testGreaterThanHelper(false, true, false);
    }

    private void testGreaterThanHelper(Boolean value, Boolean other, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().greaterThan(other));

        verify(getMockVerification()).check(eq(expected), eq("be greater than '%s'"), getArgsCaptor().capture());

        assertSame("Passes other for message formatting", other, getArgsCaptor().getValue());
    }

    @Test
    public void testGreaterThanOrEqualToWhenValueIsEqualToOther() {
        testGreaterThanOrEqualToHelper(false, false, true);
    }

    @Test
    public void testGreaterThanOrEqualToWhenValueIsGreaterThanOther() {
        testGreaterThanOrEqualToHelper(true, false, true);
    }

    @Test
    public void testGreaterThanOrEqualToWhenValueIsLessThanOther() {
        testGreaterThanOrEqualToHelper(false, true, false);
    }

    private void testGreaterThanOrEqualToHelper(Boolean value, Boolean other, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().greaterThanOrEqualTo(other));

        verify(getMockVerification()).check(eq(expected), eq("be greater than or equal to '%s'"), getArgsCaptor().capture());

        assertSame("Passes other for message formatting", other, getArgsCaptor().getValue());
    }

    @Test
    public void testFalsehoodWhenValueIsFalse() {
        testFalsehoodHelper(false, true);
    }

    @Test
    public void testFalsehoodWhenValueIsNull() {
        testFalsehoodHelper(null, true);
    }

    @Test
    public void testFalsehoodWhenValueIsTrue() {
        testFalsehoodHelper(true, false);
    }

    private void testFalsehoodHelper(Boolean value, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().falsehood());

        verify(getMockVerification()).check(expected, "be false");
    }

    @Test
    public void testTruthWhenValueIsFalse() {
        testTruthHelper(false, false);
    }

    @Test
    public void testTruthWhenValueIsNull() {
        testTruthHelper(null, false);
    }

    @Test
    public void testTruthWhenValueIsTrue() {
        testTruthHelper(true, true);
    }

    private void testTruthHelper(Boolean value, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().truth());

        verify(getMockVerification()).check(expected, "be true");
    }

    @Override
    protected BooleanVerifier createCustomVerifier() {
        return new BooleanVerifier(getMockVerification());
    }
}
