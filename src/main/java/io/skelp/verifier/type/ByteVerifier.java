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
 * {@code Byte} value.
 * </p>
 * <p>
 * In accordance with {@link BaseNumberVerifier}, all of the {@link BaseTruthVerifier} methods are implemented so that
 * {@literal null} and {@code 0} are <b>always</b> considered to be falsy and {@code 1} is <b>always</b> considered to
 * be truthy.
 * </p>
 *
 * @author Alasdair Mercer
 */
public final class ByteVerifier extends BaseComparableVerifier<Byte, ByteVerifier> implements BaseNumberVerifier<Byte, ByteVerifier> {

    /**
     * <p>
     * Creates an instance of {@link ByteVerifier} based on the {@code verification} provided.
     * </p>
     *
     * @param verification
     *         the {@link Verification} to be used
     */
    public ByteVerifier(final Verification<Byte> verification) {
        super(verification);
    }

    @Override
    public ByteVerifier even() throws VerifierException {
        final Byte value = verification().getValue();
        final boolean result = value != null && value % 2 == 0;

        verification().check(result, "be even");

        return this;
    }

    @Override
    public ByteVerifier falsy() throws VerifierException {
        final Byte value = verification().getValue();
        final boolean result = value == null || value == 0;

        verification().check(result, "be falsy");

        return this;
    }

    @Override
    public ByteVerifier negative() throws VerifierException {
        final Byte value = verification().getValue();
        final boolean result = value != null && value < 0;

        verification().check(result, "be negative");

        return this;
    }

    @Override
    public ByteVerifier odd() throws VerifierException {
        final Byte value = verification().getValue();
        final boolean result = value != null && value % 2 != 0;

        verification().check(result, "be odd");

        return this;
    }

    @Override
    public ByteVerifier one() throws VerifierException {
        final Byte value = verification().getValue();
        final boolean result = value != null && value == 1;

        verification().check(result, "be one");

        return this;
    }

    @Override
    public ByteVerifier positive() throws VerifierException {
        final Byte value = verification().getValue();
        final boolean result = value != null && value >= 0;

        verification().check(result, "be positive");

        return this;
    }

    @Override
    public ByteVerifier truthy() throws VerifierException {
        final Byte value = verification().getValue();
        final boolean result = value != null && value == 1;

        verification().check(result, "be truthy");

        return this;
    }

    @Override
    public ByteVerifier zero() throws VerifierException {
        final Byte value = verification().getValue();
        final boolean result = value != null && value == 0;

        verification().check(result, "be zero");

        return this;
    }
}
