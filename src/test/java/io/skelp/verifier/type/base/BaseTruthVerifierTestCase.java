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
 * Test case for {@link BaseTruthVerifier} implementation classes.
 * </p>
 *
 * @param <T>
 *         the value type for the {@link BaseTruthVerifier} being tested
 * @param <V>
 *         the type of the {@link BaseTruthVerifier} being tested
 * @author Alasdair Mercer
 */
public abstract class BaseTruthVerifierTestCase<T, V extends BaseTruthVerifier<T, V>> extends CustomVerifierTestCaseBase<T, V> {

    @Test
    public void testFalsyWhenValueIsFalsy() {
        testFalsyHelper(getFalsyValues(), true);
    }

    @Test
    public void testFalsyWhenValueIsNull() {
        @SuppressWarnings("unchecked")
        T[] values = (T[]) new Object[1];
        values[0] = null;

        testFalsyHelper(values, isNullFalsy());
    }

    @Test
    public void testFalsyWhenValueIsTruthy() {
        testFalsyHelper(getTruthyValues(), false);
    }

    private void testFalsyHelper(T[] values, boolean expected) {
        for (T value : values) {
            setValue(value);

            assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().falsy());
        }

        verify(getMockVerification(), times(values.length)).check(expected, "be falsy");
    }

    @Test
    public void testTruthyWhenValueIsFalsy() {
        testTruthyHelper(getFalsyValues(), false);
    }

    @Test
    public void testTruthyWhenValueIsNull() {
        @SuppressWarnings("unchecked")
        T[] values = (T[]) new Object[1];
        values[0] = null;

        testTruthyHelper(values, isNullTruthy());
    }

    @Test
    public void testTruthyWhenValueIsTruthy() {
        testTruthyHelper(getTruthyValues(), true);
    }

    private void testTruthyHelper(T[] values, boolean expected) {
        for (T value : values) {
            setValue(value);

            assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().truthy());
        }

        verify(getMockVerification(), times(values.length)).check(expected, "be truthy");
    }

    /**
     * <p>
     * Returns the values considered to be falsy.
     * </p>
     * <p>
     * If {@literal null} is considered falsy, {@link #isNullFalsy()} should be overridden to return {@literal true}
     * instead of including {@literal null} amongst these values.
     * </p>
     *
     * @return The falsy values.
     */
    protected abstract T[] getFalsyValues();

    /**
     * <p>
     * Returns the values considered to be truthy.
     * </p>
     * <p>
     * If {@literal null} is considered truthy, {@link #isNullTruthy()} should be overridden to return {@literal true}
     * instead of including {@literal null} amongst these values.
     * </p>
     *
     * @return The truthy values.
     */
    protected abstract T[] getTruthyValues();

    /**
     * <p>
     * Returns whether {@literal null} is considered falsy.
     * </p>
     * <p>
     * By default this method returns {@literal false}.
     * </p>
     *
     * @return {@literal true} if {@literal null} is expected to be falsy; otherwise {@literal false}.
     */
    protected boolean isNullFalsy() {
        return false;
    }

    /**
     * <p>
     * Returns whether {@literal null} is considered truthy.
     * </p>
     * <p>
     * By default this method returns {@literal false}.
     * </p>
     *
     * @return {@literal true} if {@literal null} is expected to be truthy; otherwise {@literal false}.
     */
    protected boolean isNullTruthy() {
        return false;
    }
}
