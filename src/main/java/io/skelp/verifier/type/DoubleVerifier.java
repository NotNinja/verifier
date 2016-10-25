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

import io.skelp.verifier.VerifierException;
import io.skelp.verifier.type.base.BaseComparableVerifier;
import io.skelp.verifier.type.base.BaseNumberVerifier;
import io.skelp.verifier.verification.Verification;

/**
 * TODO: Document
 *
 * @author Alasdair Mercer
 */
public final class DoubleVerifier extends BaseComparableVerifier<Double, DoubleVerifier> implements BaseNumberVerifier<Double, DoubleVerifier> {

    /**
     * TODO: Document
     *
     * @param verification
     */
    public DoubleVerifier(final Verification<Double> verification) {
        super(verification);
    }

    @Override
    public DoubleVerifier even() throws VerifierException {
        final Double value = verification().getValue();
        final boolean result = value != null && value % 2D == 0;

        verification().check(result, "be even");

        return this;
    }

    @Override
    public DoubleVerifier falsehood() throws VerifierException {
        final Double value = verification().getValue();
        final boolean result = value != null && value == 0D;

        verification().check(result, "be false");

        return this;
    }

    @Override
    public DoubleVerifier negative() throws VerifierException {
        final Double value = verification().getValue();
        final boolean result = value != null && value < 0D;

        verification().check(result, "be negative");

        return this;
    }

    @Override
    public DoubleVerifier odd() throws VerifierException {
        final Double value = verification().getValue();
        final boolean result = value != null && value % 2 != 0D;

        verification().check(result, "be odd");

        return this;
    }

    @Override
    public DoubleVerifier one() throws VerifierException {
        final Double value = verification().getValue();
        final boolean result = value != null && value == 1D;

        verification().check(result, "be one");

        return this;
    }

    @Override
    public DoubleVerifier positive() throws VerifierException {
        final Double value = verification().getValue();
        final boolean result = value != null && value >= 0D;

        verification().check(result, "be positive");

        return this;
    }

    @Override
    public DoubleVerifier truth() throws VerifierException {
        final Double value = verification().getValue();
        final boolean result = value != null && value == 1D;

        verification().check(result, "be true");

        return this;
    }

    @Override
    public DoubleVerifier zero() throws VerifierException {
        final Double value = verification().getValue();
        final boolean result = value != null && value == 0D;

        verification().check(result, "be zero");

        return this;
    }
}
