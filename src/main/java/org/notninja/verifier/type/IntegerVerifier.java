/*
 * Copyright (C) 2017 Alasdair Mercer, !ninja
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
package org.notninja.verifier.type;

import org.notninja.verifier.type.base.BaseComparableVerifier;
import org.notninja.verifier.type.base.BaseNumberVerifier;
import org.notninja.verifier.type.base.BaseTruthVerifier;
import org.notninja.verifier.verification.Verification;

/**
 * <p>
 * An implementation of {@link BaseComparableVerifier} and {@link BaseNumberVerifier} which can be used to verify a
 * {@code Integer} value.
 * </p>
 * <p>
 * In accordance with {@link BaseNumberVerifier}, all of the {@link BaseTruthVerifier} methods are implemented so that
 * {@literal null} and {@code 0} are <b>always</b> considered to be falsy and {@code 1} is <b>always</b> considered to
 * be truthy.
 * </p>
 *
 * @author Alasdair Mercer
 */
public final class IntegerVerifier extends BaseComparableVerifier<Integer, IntegerVerifier> implements BaseNumberVerifier<Integer, IntegerVerifier> {

    /**
     * <p>
     * Creates an instance of {@link IntegerVerifier} based on the {@code verification} provided.
     * </p>
     *
     * @param verification
     *         the {@link Verification} to be used
     */
    public IntegerVerifier(final Verification<Integer> verification) {
        super(verification);
    }

    @Override
    public IntegerVerifier even() {
        final Integer value = verification().getValue();
        final boolean result = value != null && value % 2 == 0;

        verification().report(result, BaseNumberVerifier.MessageKeys.EVEN);

        return this;
    }

    @Override
    public IntegerVerifier falsy() {
        final Integer value = verification().getValue();
        final boolean result = value == null || value == 0;

        verification().report(result, BaseTruthVerifier.MessageKeys.FALSY);

        return this;
    }

    @Override
    public IntegerVerifier negative() {
        final Integer value = verification().getValue();
        final boolean result = value != null && value < 0;

        verification().report(result, BaseNumberVerifier.MessageKeys.NEGATIVE);

        return this;
    }

    @Override
    public IntegerVerifier odd() {
        final Integer value = verification().getValue();
        final boolean result = value != null && value % 2 != 0;

        verification().report(result, BaseNumberVerifier.MessageKeys.ODD);

        return this;
    }

    @Override
    public IntegerVerifier one() {
        final Integer value = verification().getValue();
        final boolean result = value != null && value == 1;

        verification().report(result, BaseNumberVerifier.MessageKeys.ONE);

        return this;
    }

    @Override
    public IntegerVerifier positive() {
        final Integer value = verification().getValue();
        final boolean result = value != null && value >= 0;

        verification().report(result, BaseNumberVerifier.MessageKeys.POSITIVE);

        return this;
    }

    @Override
    public IntegerVerifier truthy() {
        final Integer value = verification().getValue();
        final boolean result = value != null && value == 1;

        verification().report(result, BaseTruthVerifier.MessageKeys.TRUTHY);

        return this;
    }

    @Override
    public IntegerVerifier zero() {
        final Integer value = verification().getValue();
        final boolean result = value != null && value == 0;

        verification().report(result, BaseNumberVerifier.MessageKeys.ZERO);

        return this;
    }
}
