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
 * TODO: Document
 *
 * @param <T>
 * @param <V>
 * @author Alasdair Mercer
 */
public abstract class BaseTruthVerifierTestCase<T, V extends BaseTruthVerifier<T, V>> extends CustomVerifierTestCaseBase<T, V> {

    @Test
    public void testFalsehoodWhenValueIsFalsehood() {
        testFalsehoodHelper(getFalsehoodValues(), true);
    }

    @Test
    public void testFalsehoodWhenValueIsNull() {
        @SuppressWarnings("unchecked")
        T[] values = (T[]) new Object[1];
        values[0] = null;

        testFalsehoodHelper(values, isNullFalsehood());
    }

    @Test
    public void testFalsehoodWhenValueIsTruth() {
        testFalsehoodHelper(getTruthValues(), false);
    }

    private void testFalsehoodHelper(T[] values, boolean expected) {
        for (T value : values) {
            setValue(value);

            assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().falsehood());
        }

        verify(getMockVerification(), times(values.length)).check(expected, "be false");
    }

    @Test
    public void testTruthWhenValueIsFalsehood() {
        testTruthHelper(getFalsehoodValues(), false);
    }

    @Test
    public void testTruthWhenValueIsNull() {
        @SuppressWarnings("unchecked")
        T[] values = (T[]) new Object[1];
        values[0] = null;

        testTruthHelper(values, isNullTruth());
    }

    @Test
    public void testTruthWhenValueIsTruth() {
        testTruthHelper(getTruthValues(), true);
    }

    private void testTruthHelper(T[] values, boolean expected) {
        for (T value : values) {
            setValue(value);

            assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().truth());
        }

        verify(getMockVerification(), times(values.length)).check(expected, "be true");
    }

    /**
     * TODO: Document
     *
     * @return
     */
    protected abstract T[] getFalsehoodValues();

    /**
     * TODO: Document
     *
     * @return
     */
    protected abstract T[] getTruthValues();

    /**
     * TODO: Document
     *
     * @return
     */
    protected boolean isNullFalsehood() {
        return false;
    }

    /**
     * TODO: Document
     *
     * @return
     */
    protected boolean isNullTruth() {
        return false;
    }
}
