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

import java.math.BigDecimal;

import io.skelp.verifier.type.base.BaseComparableVerifier;
import io.skelp.verifier.type.base.BaseNumberVerifier;
import io.skelp.verifier.type.base.BaseTruthVerifier;
import io.skelp.verifier.verification.Verification;

/**
 * <p>
 * An implementation of {@link BaseComparableVerifier} and {@link BaseNumberVerifier} which can be used to verify a
 * {@code BigDecimal} value.
 * </p>
 * <p>
 * In accordance with {@link BaseNumberVerifier}, all of the {@link BaseTruthVerifier} methods are implemented so that
 * {@literal null} and {@code BigDecimal.ZERO} are <b>always</b> considered to be falsy and {@code BigDecimal.ONE} is
 * <b>always</b> considered to be truthy.
 * </p>
 *
 * @author Alasdair Mercer
 */
public final class BigDecimalVerifier extends BaseComparableVerifier<BigDecimal, BigDecimalVerifier> implements BaseNumberVerifier<BigDecimal, BigDecimalVerifier> {

    /**
     * <p>
     * Creates an instance of {@link BigDecimalVerifier} based on the {@code verification} provided.
     * </p>
     *
     * @param verification
     *         the {@link Verification} to be used
     */
    public BigDecimalVerifier(final Verification<BigDecimal> verification) {
        super(verification);
    }

    @Override
    public BigDecimalVerifier even() {
        final BigDecimal value = verification().getValue();
        final boolean result = value != null && !value.stripTrailingZeros().unscaledValue().testBit(0);

        verification().report(result, BaseNumberVerifier.MessageKeys.EVEN);

        return this;
    }

    @Override
    public BigDecimalVerifier falsy() {
        final BigDecimal value = verification().getValue();
        final boolean result = value == null || value.compareTo(BigDecimal.ZERO) == 0;

        verification().report(result, BaseTruthVerifier.MessageKeys.FALSY);

        return this;
    }

    @Override
    public BigDecimalVerifier negative() {
        final BigDecimal value = verification().getValue();
        final boolean result = value != null && value.compareTo(BigDecimal.ZERO) < 0;

        verification().report(result, BaseNumberVerifier.MessageKeys.NEGATIVE);

        return this;
    }

    @Override
    public BigDecimalVerifier odd() {
        final BigDecimal value = verification().getValue();
        final boolean result = value != null && value.stripTrailingZeros().unscaledValue().testBit(0);

        verification().report(result, BaseNumberVerifier.MessageKeys.ODD);

        return this;
    }

    @Override
    public BigDecimalVerifier one() {
        final BigDecimal value = verification().getValue();
        final boolean result = value != null && value.compareTo(BigDecimal.ONE) == 0;

        verification().report(result, BaseNumberVerifier.MessageKeys.ONE);

        return this;
    }

    @Override
    public BigDecimalVerifier positive() {
        final BigDecimal value = verification().getValue();
        final boolean result = value != null && value.compareTo(BigDecimal.ZERO) >= 0;

        verification().report(result, BaseNumberVerifier.MessageKeys.POSITIVE);

        return this;
    }

    @Override
    public BigDecimalVerifier truthy() {
        final BigDecimal value = verification().getValue();
        final boolean result = value != null && value.compareTo(BigDecimal.ONE) == 0;

        verification().report(result, BaseTruthVerifier.MessageKeys.TRUTHY);

        return this;
    }

    @Override
    public BigDecimalVerifier zero() {
        final BigDecimal value = verification().getValue();
        final boolean result = value != null && value.compareTo(BigDecimal.ZERO) == 0;

        verification().report(result, BaseNumberVerifier.MessageKeys.ZERO);

        return this;
    }
}
