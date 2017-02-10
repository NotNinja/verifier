/*
 * Copyright (C) 2017 Alasdair Mercer, Skelp
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

import java.math.BigInteger;

import io.skelp.verifier.type.base.BaseComparableVerifier;
import io.skelp.verifier.type.base.BaseNumberVerifier;
import io.skelp.verifier.type.base.BaseTruthVerifier;
import io.skelp.verifier.verification.Verification;

/**
 * <p>
 * An implementation of {@link BaseComparableVerifier} and {@link BaseNumberVerifier} which can be used to verify a
 * {@code BigInteger} value.
 * </p>
 * <p>
 * In accordance with {@link BaseNumberVerifier}, all of the {@link BaseTruthVerifier} methods are implemented so that
 * {@literal null} and {@code BigInteger.ZERO} are <b>always</b> considered to be falsy and {@code BigInteger.ONE} is
 * <b>always</b> considered to be truthy.
 * </p>
 *
 * @author Alasdair Mercer
 */
public final class BigIntegerVerifier extends BaseComparableVerifier<BigInteger, BigIntegerVerifier> implements BaseNumberVerifier<BigInteger, BigIntegerVerifier> {

    /**
     * <p>
     * Creates an instance of {@link BigIntegerVerifier} based on the {@code verification} provided.
     * </p>
     *
     * @param verification
     *         the {@link Verification} to be used
     */
    public BigIntegerVerifier(final Verification<BigInteger> verification) {
        super(verification);
    }

    @Override
    public BigIntegerVerifier even() {
        final BigInteger value = verification().getValue();
        final boolean result = value != null && !value.testBit(0);

        verification().report(result, BaseNumberVerifier.MessageKeys.EVEN);

        return this;
    }

    @Override
    public BigIntegerVerifier falsy() {
        final BigInteger value = verification().getValue();
        final boolean result = value == null || value.compareTo(BigInteger.ZERO) == 0;

        verification().report(result, BaseTruthVerifier.MessageKeys.FALSY);

        return this;
    }

    @Override
    public BigIntegerVerifier negative() {
        final BigInteger value = verification().getValue();
        final boolean result = value != null && value.compareTo(BigInteger.ZERO) < 0;

        verification().report(result, BaseNumberVerifier.MessageKeys.NEGATIVE);

        return this;
    }

    @Override
    public BigIntegerVerifier odd() {
        final BigInteger value = verification().getValue();
        final boolean result = value != null && value.testBit(0);

        verification().report(result, BaseNumberVerifier.MessageKeys.ODD);

        return this;
    }

    @Override
    public BigIntegerVerifier one() {
        final BigInteger value = verification().getValue();
        final boolean result = value != null && value.compareTo(BigInteger.ONE) == 0;

        verification().report(result, BaseNumberVerifier.MessageKeys.ONE);

        return this;
    }

    @Override
    public BigIntegerVerifier positive() {
        final BigInteger value = verification().getValue();
        final boolean result = value != null && value.compareTo(BigInteger.ZERO) >= 0;

        verification().report(result, BaseNumberVerifier.MessageKeys.POSITIVE);

        return this;
    }

    @Override
    public BigIntegerVerifier truthy() {
        final BigInteger value = verification().getValue();
        final boolean result = value != null && value.compareTo(BigInteger.ONE) == 0;

        verification().report(result, BaseTruthVerifier.MessageKeys.TRUTHY);

        return this;
    }

    @Override
    public BigIntegerVerifier zero() {
        final BigInteger value = verification().getValue();
        final boolean result = value != null && value.compareTo(BigInteger.ZERO) == 0;

        verification().report(result, BaseNumberVerifier.MessageKeys.ZERO);

        return this;
    }
}
