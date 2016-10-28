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

import io.skelp.verifier.CustomVerifier;
import io.skelp.verifier.VerifierException;

/**
 * <p>
 * An extension of {@link CustomVerifier} which includes methods for verifying the truthiness of a value. How to
 * determine whether a value is truthy or falsy will obviously differ greatly between data types and is open to
 * interpretation, however, implementations of {@code BaseTruthVerifier} will make an opinionated decision as to what
 * this will be for the values being verified. Implementations should document what they consider to be falsy and truthy
 * on their type so that it can be easily discovered if it's not obvious.
 * </p>
 * <p>
 * One thing that is shared by all implementations is that {@literal null} will <b>always</b> be considered falsy.
 * </p>
 *
 * @param <T>
 *         the type of the value being verified
 * @param <V>
 *         the type of the {@link BaseTruthVerifier} for chaining purposes
 * @author Alasdair Mercer
 */
public interface BaseTruthVerifier<T, V extends BaseTruthVerifier<T, V>> extends CustomVerifier<T, V> {

    /**
     * <p>
     * The message used to enhance that of the {@link VerifierException} thrown by {@link #falsy()} when the
     * verification fails.
     * </p>
     *
     * @see #falsy()
     */
    String FALSY_MESSAGE = "be falsy";

    /**
     * <p>
     * The message used to enhance that of the {@link VerifierException} thrown by {@link #truthy()} when the
     * verification fails.
     * </p>
     *
     * @see #truthy()
     */
    String TRUTHY_MESSAGE = "be truthy";

    /**
     * <p>
     * Verifies that the value is falsy.
     * </p>
     * <p>
     * What is considered falsy can vary for each {@link BaseTruthVerifier} implementation so be sure to check the
     * documentation for the type being used for more information. One thing that is consistent is that a
     * {@literal null} value is <b>always</b> considered to be falsy.
     * </p>
     * <pre>
     * Verifier.verify((Boolean) null).falsy() => PASS
     * Verifier.verify(false).falsy()          => PASS
     * Verifier.verify(true).falsy()           => FAIL
     * </pre>
     *
     * @return A reference to this {@link BaseTruthVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #truthy()
     */
    V falsy() throws VerifierException;

    /**
     * <p>
     * Verifies that the value is truthy.
     * </p>
     * <p>
     * What is considered truthy can vary for each {@link BaseTruthVerifier} implementation so be sure to check the
     * documentation for the type being used for more information. One thing that is consistent is that a
     * {@literal null} value is <b>never</b> considered to be truthy.
     * </p>
     * <pre>
     * Verifier.verify((Boolean) null).truthy() => FAIL
     * Verifier.verify(false).truthy()          => FAIL
     * Verifier.verify(true).truthy()           => PASS
     * </pre>
     *
     * @return A reference to this {@link BaseTruthVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #falsy()
     */
    V truthy() throws VerifierException;
}
