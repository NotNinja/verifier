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

import io.skelp.verifier.AbstractCustomVerifier;
import io.skelp.verifier.VerifierException;
import io.skelp.verifier.verification.Verification;

/**
 * TODO: Document
 *
 * @param <T>
 * @param <V>
 * @author Alasdair Mercer
 */
public abstract class BaseComparableVerifier<T extends Comparable<? super T>, V extends BaseComparableVerifier<T, V>> extends AbstractCustomVerifier<T, V> {

    /**
     * TODO: Document
     *
     * @param verification
     */
    public BaseComparableVerifier(final Verification<T> verification) {
        super(verification);
    }

    /**
     * TODO: Document
     *
     * @param start
     * @param end
     * @return
     * @throws VerifierException
     */
    public V between(final T start, final T end) throws VerifierException {
        return between(start, end, start, end);
    }

    /**
     * TODO: Document
     *
     * @param start
     * @param end
     * @param startName
     * @param endName
     * @return
     * @throws VerifierException
     */
    public V between(final T start, final T end, final Object startName, final Object endName) throws VerifierException {
        return between(start, ComparisonOperator.GREATER_THAN_OR_EQUAL_TO, startName, end, ComparisonOperator.LESS_THAN_OR_EQUAL_TO, endName, "be between '%s' and '%s' (inclusive)");
    }

    /**
     * TODO: Document
     *
     * @param start
     * @param end
     * @return
     * @throws VerifierException
     */
    public V betweenExclusive(final T start, final T end) throws VerifierException {
        return betweenExclusive(start, end, start, end);
    }

    /**
     * TODO: Document
     *
     * @param start
     * @param end
     * @param startName
     * @param endName
     * @return
     * @throws VerifierException
     */
    public V betweenExclusive(final T start, final T end, final Object startName, final Object endName) throws VerifierException {
        return between(start, ComparisonOperator.GREATER_THAN, startName, end, ComparisonOperator.LESS_THAN, endName, "be between '%s' and '%s' (exclusive)");
    }

    /**
     * TODO: Document
     *
     * @param other
     * @return
     * @throws VerifierException
     */
    public V greaterThan(final T other) throws VerifierException {
        return greaterThan(other, other);
    }

    /**
     * TODO: Document
     *
     * @param other
     * @param name
     * @return
     * @throws VerifierException
     */
    public V greaterThan(final T other, final Object name) throws VerifierException {
        return comparesTo(ComparisonOperator.GREATER_THAN, other, name, "be greater than '%s'");
    }

    /**
     * TODO: Document
     *
     * @param other
     * @return
     * @throws VerifierException
     */
    public V greaterThanOrEqualTo(final T other) throws VerifierException {
        return greaterThanOrEqualTo(other, other);
    }

    /**
     * TODO: Document
     *
     * @param other
     * @param name
     * @return
     * @throws VerifierException
     */
    public V greaterThanOrEqualTo(final T other, final Object name) throws VerifierException {
        return comparesTo(ComparisonOperator.GREATER_THAN_OR_EQUAL_TO, other, name, "be greater than or equal to '%s'");
    }

    /**
     * TODO: Document
     *
     * @param other
     * @return
     * @throws VerifierException
     */
    public V lessThan(final T other) throws VerifierException {
        return lessThan(other, other);
    }

    /**
     * TODO: Document
     *
     * @param other
     * @param name
     * @return
     * @throws VerifierException
     */
    public V lessThan(final T other, final Object name) throws VerifierException {
        return comparesTo(ComparisonOperator.LESS_THAN, other, name, "be less than '%s'");
    }

    /**
     * TODO: Document
     *
     * @param other
     * @return
     * @throws VerifierException
     */
    public V lessThanOrEqualTo(final T other) throws VerifierException {
        return lessThanOrEqualTo(other, other);
    }

    /**
     * TODO: Document
     *
     * @param other
     * @param name
     * @return
     * @throws VerifierException
     */
    public V lessThanOrEqualTo(final T other, final Object name) throws VerifierException {
        return comparesTo(ComparisonOperator.LESS_THAN_OR_EQUAL_TO, other, name, "be less than or equal to '%s'");
    }

    private V between(final T start, final Comparison startComparison, final Object startName, final T end, final Comparison endComparison, final Object endName, final String message) throws VerifierException {
        final T value = getVerification().getValue();
        final boolean result = value != null && start != null && end != null &&
            (startComparison.compare(value.compareTo(start)) && endComparison.compare(value.compareTo(end)));

        getVerification().check(result, message, startName, endName);

        return chain();
    }

    private V comparesTo(final Comparison comparison, final T other, final Object name, final String message) throws VerifierException {
        final T value = getVerification().getValue();
        final boolean result = value != null && other != null && comparison.compare(value.compareTo(other));

        getVerification().check(result, message, name);

        return chain();
    }

    interface Comparison {

        boolean compare(int result);
    }

    enum ComparisonOperator implements Comparison {

        GREATER_THAN() {
            @Override
            public boolean compare(final int result) {
                return result > 0;
            }
        },
        GREATER_THAN_OR_EQUAL_TO() {
            @Override
            public boolean compare(final int result) {
                return result >= 0;
            }
        },
        LESS_THAN() {
            @Override
            public boolean compare(final int result) {
                return result < 0;
            }
        },
        LESS_THAN_OR_EQUAL_TO() {
            @Override
            public boolean compare(final int result) {
                return result <= 0;
            }
        }
    }
}
