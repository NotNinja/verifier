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
 * Test case for {@link BaseNumberVerifier} implementation classes.
 * </p>
 *
 * @param <T>
 *         the {@code Number} value type for the {@link BaseNumberVerifier} being tested
 * @param <V>
 *         the type of the {@link BaseNumberVerifier} being tested
 * @author Alasdair Mercer
 */
public abstract class BaseNumberVerifierTestCase<T extends Number, V extends BaseNumberVerifier<T, V>> extends CustomVerifierTestCaseBase<T, V> {

    @Test
    public void testEvenWhenValueIsEven() {
        testEvenHelper(getEvenValue(), true);
    }

    @Test
    public void testEvenWhenValueIsNull() {
        testEvenHelper(null, false);
    }

    @Test
    public void testEvenWhenValueIsOdd() {
        testEvenHelper(getOddValue(), false);
    }

    @Test
    public void testEvenWhenValueIsZero() {
        testEvenHelper(getZeroValue(), true);
    }

    private void testEvenHelper(T value, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().even());

        verify(getMockVerification()).check(expected, "be even");
    }

    @Test
    public void testNegativeWhenValueIsNegative() {
        testNegativeHelper(getNegativeOneValue(), true);
    }

    @Test
    public void testNegativeWhenValueIsNull() {
        testNegativeHelper(null, false);
    }

    @Test
    public void testNegativeWhenValueIsPositive() {
        testNegativeHelper(getPositiveOneValue(), false);
    }

    @Test
    public void testNegativeWhenValueIsZero() {
        testNegativeHelper(getZeroValue(), false);
    }

    private void testNegativeHelper(T value, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().negative());

        verify(getMockVerification()).check(expected, "be negative");
    }

    @Test
    public void testOddWhenValueIsEven() {
        testOddHelper(getEvenValue(), false);
    }

    @Test
    public void testOddWhenValueIsNull() {
        testOddHelper(null, false);
    }

    @Test
    public void testOddWhenValueIsOdd() {
        testOddHelper(getOddValue(), true);
    }

    @Test
    public void testOddWhenValueIsZero() {
        testOddHelper(getZeroValue(), false);
    }

    private void testOddHelper(T value, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().odd());

        verify(getMockVerification()).check(expected, "be odd");
    }

    @Test
    public void testOneWhenValueIsNegativeOne() {
        testOneHelper(getNegativeOneValue(), false);
    }

    @Test
    public void testOneWhenValueIsNull() {
        testOneHelper(null, false);
    }

    @Test
    public void testOneWhenValueIsPositiveOne() {
        testOneHelper(getPositiveOneValue(), true);
    }

    @Test
    public void testOneWhenValueIsZero() {
        testOneHelper(getZeroValue(), false);
    }

    private void testOneHelper(T value, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().one());

        verify(getMockVerification()).check(expected, "be one");
    }

    @Test
    public void testPositiveWhenValueIsNegative() {
        testPositiveHelper(getNegativeOneValue(), false);
    }

    @Test
    public void testPositiveWhenValueIsNull() {
        testPositiveHelper(null, false);
    }

    @Test
    public void testPositiveWhenValueIsPositive() {
        testPositiveHelper(getPositiveOneValue(), true);
    }

    @Test
    public void testPositiveWhenValueIsZero() {
        testPositiveHelper(getZeroValue(), true);
    }

    private void testPositiveHelper(T value, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().positive());

        verify(getMockVerification()).check(expected, "be positive");
    }

    @Test
    public void testZeroWhenValueIsNegativeOne() {
        testZeroHelper(getNegativeOneValue(), false);
    }

    @Test
    public void testZeroWhenValueIsNull() {
        testZeroHelper(null, false);
    }

    @Test
    public void testZeroWhenValueIsPositiveOne() {
        testZeroHelper(getPositiveOneValue(), false);
    }

    @Test
    public void testZeroWhenValueIsZero() {
        testZeroHelper(getZeroValue(), true);
    }

    private void testZeroHelper(T value, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().zero());

        verify(getMockVerification()).check(expected, "be zero");
    }

    /**
     * <p>
     * Returns an even value for testing.
     * </p>
     *
     * @return An even value.
     */
    protected abstract T getEvenValue();

    /**
     * <p>
     * Returns an even value for testing.
     * </p>
     *
     * @return An even value.
     */
    protected abstract T getOddValue();

    /**
     * <p>
     * Returns the value of +1 for testing.
     * </p>
     *
     * @return Positive one.
     */
    protected abstract T getPositiveOneValue();

    /**
     * <p>
     * Returns the value of -1 for testing.
     * </p>
     *
     * @return Negative one.
     */
    protected abstract T getNegativeOneValue();

    /**
     * <p>
     * Returns the value of zero for testing.
     * </p>
     *
     * @return Zero.
     */
    protected abstract T getZeroValue();
}
