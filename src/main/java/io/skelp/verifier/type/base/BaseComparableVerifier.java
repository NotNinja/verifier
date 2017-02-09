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
 * <p>
 * An abstract extension of {@link AbstractCustomVerifier} which includes methods for verifying a {@code Comparable}
 * value in relation to others. Nothing is required of implementations to support these methods, so long as they verify
 * a {@code Comparable} value.
 * </p>
 *
 * @param <T>
 *         the {@code Comparable} type of the value being verified
 * @param <V>
 *         the type of the {@link BaseComparableVerifier} for chaining purposes
 * @author Alasdair Mercer
 */
public abstract class BaseComparableVerifier<T extends Comparable<? super T>, V extends BaseComparableVerifier<T, V>> extends AbstractCustomVerifier<T, V> {

    /**
     * <p>
     * Creates an instance of {@link BaseComparableVerifier} based on the {@code verification} provided.
     * </p>
     *
     * @param verification
     *         the {@link Verification} to be used
     */
    public BaseComparableVerifier(final Verification<T> verification) {
        super(verification);
    }

    /**
     * <p>
     * Verifies that the value is between the specified {@code start} and {@code end} range (both inclusive).
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify(*).between(*, null)            =&gt; FAIL
     * Verifier.verify(*).between(null, *)            =&gt; FAIL
     * Verifier.verify((Integer) null).between(*, *)  =&gt; FAIL
     * Verifier.verify(50).between(0, 25)             =&gt; FAIL
     * Verifier.verify(50).between(75, 100)           =&gt; FAIL
     * Verifier.verify(50).between(0, 100)            =&gt; PASS
     * Verifier.verify(50).between(0, 50)             =&gt; PASS
     * Verifier.verify(50).between(50, 100)           =&gt; PASS
     * </pre>
     *
     * @param start
     *         the start of the range, inclusive (may be {@literal null})
     * @param end
     *         the end of the range, inclusive (may be {@literal null})
     * @return A reference to this {@link BaseComparableVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #between(Comparable, Comparable, Object, Object)
     */
    public V between(final T start, final T end) throws VerifierException {
        return between(start, end, start, end);
    }

    /**
     * <p>
     * Verifies that the value is between the specified {@code start} and {@code end} range (both inclusive) while
     * allowing {@code start} and {@code end} to be given an optional friendlier names for the {@link VerifierException}
     * message in the event that one is thrown.
     * </p>
     * <p>
     * While the names can be {@literal null} it is recommended instead to either use {@link
     * #between(Comparable, Comparable)} or pass {@code start} and {@code end} as the names instead to get a better
     * verification error message.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify(*).between(*, null, *, *)            =&gt; FAIL
     * Verifier.verify(*).between(null, *, *, *)            =&gt; FAIL
     * Verifier.verify((Integer) null).between(*, *, *, *)  =&gt; FAIL
     * Verifier.verify(50).between(0, 25, *, *)             =&gt; FAIL
     * Verifier.verify(50).between(75, 100, *, *)           =&gt; FAIL
     * Verifier.verify(50).between(0, 100, *, *)            =&gt; PASS
     * Verifier.verify(50).between(0, 50, *, *)             =&gt; PASS
     * Verifier.verify(50).between(50, 100, *, *)           =&gt; PASS
     * </pre>
     *
     * @param start
     *         the start of the range, inclusive (may be {@literal null})
     * @param end
     *         the end of the range, inclusive (may be {@literal null})
     * @param startName
     *         the optional name used to represent {@code start} (may be {@literal null})
     * @param endName
     *         the optional name used to represent {@code end} (may be {@literal null})
     * @return A reference to this {@link BaseComparableVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #between(Comparable, Comparable)
     */
    public V between(final T start, final T end, final Object startName, final Object endName) throws VerifierException {
        return between(start, ComparisonOperator.GREATER_THAN_OR_EQUAL_TO, startName, end, ComparisonOperator.LESS_THAN_OR_EQUAL_TO, endName, "be between '%s' and '%s' (inclusive)");
    }

    /**
     * <p>
     * Verifies that the value is between the specified {@code start} and {@code end} range (both exclusive).
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify(*).betweenExclusive(*, null)            =&gt; FAIL
     * Verifier.verify(*).betweenExclusive(null, *)            =&gt; FAIL
     * Verifier.verify((Integer) null).betweenExclusive(*, *)  =&gt; FAIL
     * Verifier.verify(50).betweenExclusive(0, 25)             =&gt; FAIL
     * Verifier.verify(50).betweenExclusive(75, 100)           =&gt; FAIL
     * Verifier.verify(50).betweenExclusive(0, 100)            =&gt; PASS
     * Verifier.verify(50).betweenExclusive(0, 50)             =&gt; FAIL
     * Verifier.verify(50).betweenExclusive(50, 100)           =&gt; FAIL
     * </pre>
     *
     * @param start
     *         the start of the range, exclusive (may be {@literal null})
     * @param end
     *         the end of the range, exclusive (may be {@literal null})
     * @return A reference to this {@link BaseComparableVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #betweenExclusive(Comparable, Comparable, Object, Object)
     */
    public V betweenExclusive(final T start, final T end) throws VerifierException {
        return betweenExclusive(start, end, start, end);
    }

    /**
     * <p>
     * Verifies that the value is between the specified {@code start} and {@code end} range (both exclusive) while
     * allowing {@code start} and {@code end} to be given an optional friendlier names for the {@link VerifierException}
     * message in the event that one is thrown.
     * </p>
     * <p>
     * While the names can be {@literal null} it is recommended instead to either use {@link
     * #betweenExclusive(Comparable, Comparable)} or pass {@code start} and {@code end} as the names instead to get a
     * better verification error message.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify(*).betweenExclusive(*, null, *, *)            =&gt; FAIL
     * Verifier.verify(*).betweenExclusive(null, *, *, *)            =&gt; FAIL
     * Verifier.verify((Integer) null).betweenExclusive(*, *, *, *)  =&gt; FAIL
     * Verifier.verify(50).betweenExclusive(0, 25, *, *)             =&gt; FAIL
     * Verifier.verify(50).betweenExclusive(75, 100, *, *)           =&gt; FAIL
     * Verifier.verify(50).betweenExclusive(0, 100, *, *)            =&gt; PASS
     * Verifier.verify(50).betweenExclusive(0, 50, *, *)             =&gt; FAIL
     * Verifier.verify(50).betweenExclusive(50, 100, *, *)           =&gt; FAIL
     * </pre>
     *
     * @param start
     *         the start of the range, exclusive (may be {@literal null})
     * @param end
     *         the end of the range, exclusive (may be {@literal null})
     * @param startName
     *         the optional name used to represent {@code start} (may be {@literal null})
     * @param endName
     *         the optional name used to represent {@code end} (may be {@literal null})
     * @return A reference to this {@link BaseComparableVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #betweenExclusive(Comparable, Comparable)
     */
    public V betweenExclusive(final T start, final T end, final Object startName, final Object endName) throws VerifierException {
        return between(start, ComparisonOperator.GREATER_THAN, startName, end, ComparisonOperator.LESS_THAN, endName, "be between '%s' and '%s' (exclusive)");
    }

    /**
     * <p>
     * Verifies that the value is greater than ({@code >}) the {@code other} provided.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify(*).greaterThan(null)              =&gt; FAIL
     * Verifier.verify((Integer) null).greaterThan(*)    =&gt; FAIL
     * Verifier.verify((Integer) null).greaterThan(null) =&gt; FAIL
     * Verifier.verify(123).greaterThan(123)             =&gt; FAIL
     * Verifier.verify(123).greaterThan(987)             =&gt; FAIL
     * Verifier.verify(123).greaterThan(62)              =&gt; PASS
     * </pre>
     *
     * @param other
     *         the object to compare against the value (may be {@literal null})
     * @return A reference to this {@link BaseComparableVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #greaterThan(Comparable, Object)
     */
    public V greaterThan(final T other) throws VerifierException {
        return greaterThan(other, other);
    }

    /**
     * <p>
     * Verifies that the value is greater than ({@code >}) the {@code other} provided while allowing {@code other} to be
     * given an optional friendlier {@code name} for the {@link VerifierException} message in the event that one is
     * thrown.
     * </p>
     * <p>
     * While {@code name} can be {@literal null} it is recommended instead to either use {@link
     * #greaterThan(Comparable)} or pass {@code other} as {@code name} instead to get a better verification error
     * message.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify(*).greaterThan(null, *)              =&gt; FAIL
     * Verifier.verify((Integer) null).greaterThan(*, *)    =&gt; FAIL
     * Verifier.verify((Integer) null).greaterThan(null, *) =&gt; FAIL
     * Verifier.verify(123).greaterThan(123, *)             =&gt; FAIL
     * Verifier.verify(123).greaterThan(987, *)             =&gt; FAIL
     * Verifier.verify(123).greaterThan(62, *)              =&gt; PASS
     * </pre>
     *
     * @param other
     *         the object to compare against the value (may be {@literal null})
     * @param name
     *         the optional name used to represent {@code other} (may be {@literal null})
     * @return A reference to this {@link BaseComparableVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #greaterThan(Comparable)
     */
    public V greaterThan(final T other, final Object name) throws VerifierException {
        return comparesTo(ComparisonOperator.GREATER_THAN, other, name, "be greater than '%s'");
    }

    /**
     * <p>
     * Verifies that the value is greater than or equal to ({@code >=}) the {@code other} provided.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify(*).greaterThanOrEqualTo(null)              =&gt; FAIL
     * Verifier.verify((Integer) null).greaterThanOrEqualTo(*)    =&gt; FAIL
     * Verifier.verify((Integer) null).greaterThanOrEqualTo(null) =&gt; PASS
     * Verifier.verify(123).greaterThanOrEqualTo(123)             =&gt; PASS
     * Verifier.verify(123).greaterThanOrEqualTo(987)             =&gt; FAIL
     * Verifier.verify(123).greaterThanOrEqualTo(62)              =&gt; PASS
     * </pre>
     *
     * @param other
     *         the object to compare against the value (may be {@literal null})
     * @return A reference to this {@link BaseComparableVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #greaterThanOrEqualTo(Comparable, Object)
     */
    public V greaterThanOrEqualTo(final T other) throws VerifierException {
        return greaterThanOrEqualTo(other, other);
    }

    /**
     * <p>
     * Verifies that the value is greater than or equal to ({@code >=}) the {@code other} provided while allowing
     * {@code other} to be given an optional friendlier {@code name} for the {@link VerifierException} message in the
     * event that one is thrown.
     * </p>
     * <p>
     * While {@code name} can be {@literal null} it is recommended instead to either use {@link
     * #greaterThanOrEqualTo(Comparable)} or pass {@code other} as {@code name} instead to get a better verification
     * error message.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify(*).greaterThanOrEqualTo(null, *)              =&gt; FAIL
     * Verifier.verify((Integer) null).greaterThanOrEqualTo(*, *)    =&gt; FAIL
     * Verifier.verify((Integer) null).greaterThanOrEqualTo(null, *) =&gt; PASS
     * Verifier.verify(123).greaterThanOrEqualTo(123, *)             =&gt; PASS
     * Verifier.verify(123).greaterThanOrEqualTo(987, *)             =&gt; FAIL
     * Verifier.verify(123).greaterThanOrEqualTo(62, *)              =&gt; PASS
     * </pre>
     *
     * @param other
     *         the object to compare against the value (may be {@literal null})
     * @param name
     *         the optional name used to represent {@code other} (may be {@literal null})
     * @return A reference to this {@link BaseComparableVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #greaterThanOrEqualTo(Comparable)
     */
    public V greaterThanOrEqualTo(final T other, final Object name) throws VerifierException {
        return comparesTo(ComparisonOperator.GREATER_THAN_OR_EQUAL_TO, other, name, "be greater than or equal to '%s'");
    }

    /**
     * <p>
     * Verifies that the value is less than ({@code <}) the {@code other} provided.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify(*).lessThan(null)              =&gt; FAIL
     * Verifier.verify((Integer) null).lessThan(*)    =&gt; FAIL
     * Verifier.verify((Integer) null).lessThan(null) =&gt; FAIL
     * Verifier.verify(123).lessThan(123)             =&gt; FAIL
     * Verifier.verify(123).lessThan(987)             =&gt; PASS
     * Verifier.verify(123).lessThan(62)              =&gt; FAIL
     * </pre>
     *
     * @param other
     *         the object to compare against the value (may be {@literal null})
     * @return A reference to this {@link BaseComparableVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #lessThan(Comparable, Object)
     */
    public V lessThan(final T other) throws VerifierException {
        return lessThan(other, other);
    }

    /**
     * <p>
     * Verifies that the value is less than ({@code <}) the {@code other} provided while allowing {@code other} to be
     * given an optional friendlier {@code name} for the {@link VerifierException} message in the event that one is
     * thrown.
     * </p>
     * <p>
     * While {@code name} can be {@literal null} it is recommended instead to either use {@link #lessThan(Comparable)}
     * or pass {@code other} as {@code name} instead to get a better verification error message.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify(*).lessThan(null, *)              =&gt; FAIL
     * Verifier.verify((Integer) null).lessThan(*, *)    =&gt; FAIL
     * Verifier.verify((Integer) null).lessThan(null, *) =&gt; FAIL
     * Verifier.verify(123).lessThan(123, *)             =&gt; FAIL
     * Verifier.verify(123).lessThan(987, *)             =&gt; PASS
     * Verifier.verify(123).lessThan(62, *)              =&gt; FAIL
     * </pre>
     *
     * @param other
     *         the object to compare against the value (may be {@literal null})
     * @param name
     *         the optional name used to represent {@code other} (may be {@literal null})
     * @return A reference to this {@link BaseComparableVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #lessThan(Comparable)
     */
    public V lessThan(final T other, final Object name) throws VerifierException {
        return comparesTo(ComparisonOperator.LESS_THAN, other, name, "be less than '%s'");
    }

    /**
     * <p>
     * Verifies that the value is less than or equal to ({@code <=}) the {@code other} provided.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify(*).lessThanOrEqualTo(null)              =&gt; FAIL
     * Verifier.verify((Integer) null).lessThanOrEqualTo(*)    =&gt; FAIL
     * Verifier.verify((Integer) null).lessThanOrEqualTo(null) =&gt; PASS
     * Verifier.verify(123).lessThanOrEqualTo(123)             =&gt; PASS
     * Verifier.verify(123).lessThanOrEqualTo(987)             =&gt; PASS
     * Verifier.verify(123).lessThanOrEqualTo(62)              =&gt; FAIL
     * </pre>
     *
     * @param other
     *         the object to compare against the value (may be {@literal null})
     * @return A reference to this {@link BaseComparableVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #lessThanOrEqualTo(Comparable, Object)
     */
    public V lessThanOrEqualTo(final T other) throws VerifierException {
        return lessThanOrEqualTo(other, other);
    }

    /**
     * <p>
     * Verifies that the value is less than or equal to ({@code <=}) the {@code other} provided while allowing
     * {@code other} to be given an optional friendlier {@code name} for the {@link VerifierException} message in the
     * event that one is thrown.
     * </p>
     * <p>
     * While {@code name} can be {@literal null} it is recommended instead to either use {@link
     * #lessThanOrEqualTo(Comparable)} or pass {@code other} as {@code name} instead to get a better verification error
     * message.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify(*).lessThanOrEqualTo(null, *)              =&gt; FAIL
     * Verifier.verify((Integer) null).lessThanOrEqualTo(*, *)    =&gt; FAIL
     * Verifier.verify((Integer) null).lessThanOrEqualTo(null, *) =&gt; PASS
     * Verifier.verify(123).lessThanOrEqualTo(123, *)             =&gt; PASS
     * Verifier.verify(123).lessThanOrEqualTo(987, *)             =&gt; PASS
     * Verifier.verify(123).lessThanOrEqualTo(62, *)              =&gt; FAIL
     * </pre>
     *
     * @param other
     *         the object to compare against the value (may be {@literal null})
     * @param name
     *         the optional name used to represent {@code other} (may be {@literal null})
     * @return A reference to this {@link BaseComparableVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #lessThanOrEqualTo(Comparable)
     */
    public V lessThanOrEqualTo(final T other, final Object name) throws VerifierException {
        return comparesTo(ComparisonOperator.LESS_THAN_OR_EQUAL_TO, other, name, "be less than or equal to '%s'");
    }

    private V between(final T start, final Comparison startComparison, final Object startName, final T end, final Comparison endComparison, final Object endName, final String message) throws VerifierException {
        final T value = verification().getValue();
        final boolean result = value != null && start != null && end != null &&
            (startComparison.compare(value.compareTo(start)) && endComparison.compare(value.compareTo(end)));

        verification().check(result, message, startName, endName);

        return chain();
    }

    private V comparesTo(final Comparison comparison, final T other, final Object name, final String message) throws VerifierException {
        final T value = verification().getValue();
        final boolean result = (value == null || other == null) ? comparison.areNullsEqual() && value == other : comparison.compare(value.compareTo(other));

        verification().check(result, message, name);

        return chain();
    }

    private interface Comparison {

        boolean areNullsEqual();

        boolean compare(int result);
    }

    enum ComparisonOperator implements Comparison {

        GREATER_THAN(false) {
            @Override
            public boolean compare(final int result) {
                return result > 0;
            }
        },
        GREATER_THAN_OR_EQUAL_TO(true) {
            @Override
            public boolean compare(final int result) {
                return result >= 0;
            }
        },
        LESS_THAN(false) {
            @Override
            public boolean compare(final int result) {
                return result < 0;
            }
        },
        LESS_THAN_OR_EQUAL_TO(true) {
            @Override
            public boolean compare(final int result) {
                return result <= 0;
            }
        };

        private final boolean nullsEqual;

        ComparisonOperator(final boolean nullsEqual) {
            this.nullsEqual = nullsEqual;
        }

        @Override
        public boolean areNullsEqual() {
            return nullsEqual;
        }
    }
}
