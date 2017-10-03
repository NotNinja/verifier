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
 * {@code Long} value.
 * </p>
 * <p>
 * In accordance with {@link BaseNumberVerifier}, all of the {@link BaseTruthVerifier} methods are implemented so that
 * {@literal null} and {@code 0L} are <b>always</b> considered to be falsy and {@code 1L} is <b>always</b> considered to
 * be truthy.
 * </p>
 *
 * @author Alasdair Mercer
 */
public final class LongVerifier extends BaseComparableVerifier<Long, LongVerifier> implements BaseNumberVerifier<Long, LongVerifier> {

    /**
     * <p>
     * Creates an instance of {@link LongVerifier} based on the {@code verification} provided.
     * </p>
     *
     * @param verification
     *         the {@link Verification} to be used
     */
    public LongVerifier(final Verification<Long> verification) {
        super(verification);
    }

    @Override
    public LongVerifier even() {
        final Long value = verification().getValue();
        final boolean result = value != null && value % 2L == 0;

        verification().report(result, BaseNumberVerifier.MessageKeys.EVEN);

        return this;
    }

    @Override
    public LongVerifier falsy() {
        final Long value = verification().getValue();
        final boolean result = value == null || value == 0L;

        verification().report(result, BaseTruthVerifier.MessageKeys.FALSY);

        return this;
    }

    @Override
    public LongVerifier negative() {
        final Long value = verification().getValue();
        final boolean result = value != null && value < 0L;

        verification().report(result, BaseNumberVerifier.MessageKeys.NEGATIVE);

        return this;
    }

    @Override
    public LongVerifier odd() {
        final Long value = verification().getValue();
        final boolean result = value != null && value % 2 != 0L;

        verification().report(result, BaseNumberVerifier.MessageKeys.ODD);

        return this;
    }

    @Override
    public LongVerifier one() {
        final Long value = verification().getValue();
        final boolean result = value != null && value == 1L;

        verification().report(result, BaseNumberVerifier.MessageKeys.ONE);

        return this;
    }

    @Override
    public LongVerifier positive() {
        final Long value = verification().getValue();
        final boolean result = value != null && value >= 0L;

        verification().report(result, BaseNumberVerifier.MessageKeys.POSITIVE);

        return this;
    }

    @Override
    public LongVerifier truthy() {
        final Long value = verification().getValue();
        final boolean result = value != null && value == 1L;

        verification().report(result, BaseTruthVerifier.MessageKeys.TRUTHY);

        return this;
    }

    @Override
    public LongVerifier zero() {
        final Long value = verification().getValue();
        final boolean result = value != null && value == 0L;

        verification().report(result, BaseNumberVerifier.MessageKeys.ZERO);

        return this;
    }
}
