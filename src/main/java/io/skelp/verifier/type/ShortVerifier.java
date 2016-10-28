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
import io.skelp.verifier.type.base.BaseTruthVerifier;
import io.skelp.verifier.verification.Verification;

/**
 * <p>
 * An implementation of {@link BaseComparableVerifier} and {@link BaseNumberVerifier} which can be used to verify a
 * {@code Short} value.
 * </p>
 * <p>
 * In accordance with {@link BaseNumberVerifier}, all of the {@link BaseTruthVerifier} methods are implemented so that
 * {@literal null} and {@code 0} are <b>always</b> considered to be falsy and {@code 1} is <b>always</b> considered to
 * be truthy.
 * </p>
 *
 * @author Alasdair Mercer
 */
public final class ShortVerifier extends BaseComparableVerifier<Short, ShortVerifier> implements BaseNumberVerifier<Short, ShortVerifier> {

    /**
     * <p>
     * Creates an instance of {@link ShortVerifier} based on the {@code verification} provided.
     * </p>
     *
     * @param verification
     *         the {@link Verification} to be used
     */
    public ShortVerifier(final Verification<Short> verification) {
        super(verification);
    }

    @Override
    public ShortVerifier even() throws VerifierException {
        final Short value = verification().getValue();
        final boolean result = value != null && value % 2 == 0;

        verification().check(result, "be even");

        return this;
    }

    @Override
    public ShortVerifier falsy() throws VerifierException {
        final Short value = verification().getValue();
        final boolean result = value == null || value == 0;

        verification().check(result, "be falsy");

        return this;
    }

    @Override
    public ShortVerifier negative() throws VerifierException {
        final Short value = verification().getValue();
        final boolean result = value != null && value < 0;

        verification().check(result, "be negative");

        return this;
    }

    @Override
    public ShortVerifier odd() throws VerifierException {
        final Short value = verification().getValue();
        final boolean result = value != null && value % 2 != 0;

        verification().check(result, "be odd");

        return this;
    }

    @Override
    public ShortVerifier one() throws VerifierException {
        final Short value = verification().getValue();
        final boolean result = value != null && value == 1;

        verification().check(result, "be one");

        return this;
    }

    @Override
    public ShortVerifier positive() throws VerifierException {
        final Short value = verification().getValue();
        final boolean result = value != null && value >= 0;

        verification().check(result, "be positive");

        return this;
    }

    @Override
    public ShortVerifier truthy() throws VerifierException {
        final Short value = verification().getValue();
        final boolean result = value != null && value == 1;

        verification().check(result, "be truthy");

        return this;
    }

    @Override
    public ShortVerifier zero() throws VerifierException {
        final Short value = verification().getValue();
        final boolean result = value != null && value == 0;

        verification().check(result, "be zero");

        return this;
    }
}
