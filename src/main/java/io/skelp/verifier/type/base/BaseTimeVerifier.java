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

import java.util.Calendar;

import io.skelp.verifier.VerifierException;
import io.skelp.verifier.verification.Verification;

/**
 * <p>
 * An abstract extension of {@link BaseComparableVerifier} which includes methods for verifying date/time parts of a
 * value. Implementations of {@code BaseTimeVerifier} must be able to provide a {@code Calendar} representation of the
 * value which should also be {@code Comparable}.
 * </p>
 *
 * @param <T>
 *         the type of the value being verified
 * @param <V>
 *         the type of the {@link BaseTimeVerifier} for chaining purposes
 * @author Alasdair Mercer
 */
public abstract class BaseTimeVerifier<T extends Comparable<? super T>, V extends BaseTimeVerifier<T, V>> extends BaseComparableVerifier<T, V> {

    /**
     * <p>
     * Creates an instance of {@link BaseTimeVerifier} based on the {@code verification} provided.
     * </p>
     *
     * @param verification
     *         the {@link Verification} to be used
     */
    public BaseTimeVerifier(final Verification<T> verification) {
        super(verification);
    }

    /**
     * <p>
     * Verifies that the value represents the same day as that of the {@code other} provided.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify(*).sameDayAs(null)                                                      => FAIL
     * Verifier.verify(null).sameDayAs(*)                                                      => FAIL
     * Verifier.verify(parse("31 Oct 2016 13:45:30")).sameDayAs(parse("31 Aug 2016 13:45:30")) => FAIL
     * Verifier.verify(parse("31 Oct 2016 13:45:30")).sameDayAs(parse("15 Oct 2016 13:45:30")) => FAIL
     * Verifier.verify(parse("31 Oct 2016 13:45:30")).sameDayAs(parse("31 Oct 2016 00:07:02")) => PASS
     * </pre>
     *
     * @param other
     *         the object to compare against the day of this value (may be {@literal null})
     * @return A reference to this {@link BaseTimeVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     */
    public V sameDayAs(final T other) throws VerifierException {
        final Calendar calendar1 = getCalendar(verification().getValue());
        final Calendar calendar2 = getCalendar(other);
        final boolean result = calendar1 != null && calendar2 != null &&
            (calendar1.get(Calendar.ERA) == calendar2.get(Calendar.ERA) &&
                calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) &&
                calendar1.get(Calendar.DAY_OF_YEAR) == calendar2.get(Calendar.DAY_OF_YEAR));

        verification().check(result, "be same day as '%s'", other);

        return chain();
    }

    /**
     * <p>
     * Verifies that the value represents the same era as that of the {@code other} provided.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify(*).sameEraAs(null)                                                            => FAIL
     * Verifier.verify(null).sameEraAs(*)                                                            => FAIL
     * Verifier.verify(parse("31 Oct 2016 AD 13:45:30")).sameEraAs(parse("31 Oct 2016 BC 13:45:30")) => FAIL
     * Verifier.verify(parse("31 Oct 2016 AD 13:45:30")).sameEraAs(parse("15 Aug 2017 AD 00:07:02")) => PASS
     * </pre>
     *
     * @param other
     *         the object to compare against the era of this value (may be {@literal null})
     * @return A reference to this {@link BaseTimeVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     */
    public V sameEraAs(final T other) throws VerifierException {
        final Calendar calendar1 = getCalendar(verification().getValue());
        final Calendar calendar2 = getCalendar(other);
        final boolean result = calendar1 != null && calendar2 != null &&
            calendar1.get(Calendar.ERA) == calendar2.get(Calendar.ERA);

        verification().check(result, "be same era as '%s'", other);

        return chain();
    }

    /**
     * <p>
     * Verifies that the value represents the same hour of the same day as that of the {@code other} provided.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify(*).sameHourAs(null)                                                      => FAIL
     * Verifier.verify(null).sameHourAs(*)                                                      => FAIL
     * Verifier.verify(parse("31 Oct 2016 13:45:30")).sameHourAs(parse("15 Oct 2016 13:45:30")) => FAIL
     * Verifier.verify(parse("31 Oct 2016 13:45:30")).sameHourAs(parse("31 Oct 2016 00:45:30")) => FAIL
     * Verifier.verify(parse("31 Oct 2016 13:45:30")).sameHourAs(parse("31 Oct 2016 13:15:02")) => PASS
     * </pre>
     *
     * @param other
     *         the object to compare against the hour of this value (may be {@literal null})
     * @return A reference to this {@link BaseTimeVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     */
    public V sameHourAs(final T other) throws VerifierException {
        final Calendar calendar1 = getCalendar(verification().getValue());
        final Calendar calendar2 = getCalendar(other);
        final boolean result = calendar1 != null && calendar2 != null &&
            (calendar1.get(Calendar.ERA) == calendar2.get(Calendar.ERA) &&
                calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) &&
                calendar1.get(Calendar.DAY_OF_YEAR) == calendar2.get(Calendar.DAY_OF_YEAR) &&
                calendar1.get(Calendar.HOUR_OF_DAY) == calendar2.get(Calendar.HOUR_OF_DAY));

        verification().check(result, "be same hour as '%s'", other);

        return chain();
    }

    /**
     * <p>
     * Verifies that the value represents the same minute of the same day as that of the {@code other} provided.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify(*).sameMinuteAs(null)                                                      => FAIL
     * Verifier.verify(null).sameMinuteAs(*)                                                      => FAIL
     * Verifier.verify(parse("31 Oct 2016 13:45:30")).sameMinuteAs(parse("31 Oct 2016 00:45:30")) => FAIL
     * Verifier.verify(parse("31 Oct 2016 13:45:30")).sameMinuteAs(parse("31 Oct 2016 13:15:30")) => FAIL
     * Verifier.verify(parse("31 Oct 2016 13:45:30")).sameMinuteAs(parse("31 Oct 2016 13:45:02")) => PASS
     * </pre>
     *
     * @param other
     *         the object to compare against the minute of this value (may be {@literal null})
     * @return A reference to this {@link BaseTimeVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     */
    public V sameMinuteAs(final T other) throws VerifierException {
        final Calendar calendar1 = getCalendar(verification().getValue());
        final Calendar calendar2 = getCalendar(other);
        final boolean result = calendar1 != null && calendar2 != null &&
            (calendar1.get(Calendar.ERA) == calendar2.get(Calendar.ERA) &&
                calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) &&
                calendar1.get(Calendar.DAY_OF_YEAR) == calendar2.get(Calendar.DAY_OF_YEAR) &&
                calendar1.get(Calendar.HOUR_OF_DAY) == calendar2.get(Calendar.HOUR_OF_DAY) &&
                calendar1.get(Calendar.MINUTE) == calendar2.get(Calendar.MINUTE));

        verification().check(result, "be same minute as '%s'", other);

        return chain();
    }

    /**
     * <p>
     * Verifies that the value represents the same month as that of the {@code other} provided.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify(*).sameMonthAs(null)                                                      => FAIL
     * Verifier.verify(null).sameMonthAs(*)                                                      => FAIL
     * Verifier.verify(parse("31 Oct 2016 13:45:30")).sameMonthAs(parse("31 Oct 2017 13:45:30")) => FAIL
     * Verifier.verify(parse("31 Oct 2016 13:45:30")).sameMonthAs(parse("31 Aug 2016 13:45:30")) => FAIL
     * Verifier.verify(parse("31 Oct 2016 13:45:30")).sameMonthAs(parse("15 Oct 2016 00:07:02")) => PASS
     * </pre>
     *
     * @param other
     *         the object to compare against the month of this value (may be {@literal null})
     * @return A reference to this {@link BaseTimeVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     */
    public V sameMonthAs(final T other) throws VerifierException {
        final Calendar calendar1 = getCalendar(verification().getValue());
        final Calendar calendar2 = getCalendar(other);
        final boolean result = calendar1 != null && calendar2 != null &&
            (calendar1.get(Calendar.ERA) == calendar2.get(Calendar.ERA) &&
                calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) &&
                calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH));

        verification().check(result, "be same month as '%s'", other);

        return chain();
    }

    /**
     * <p>
     * Verifies that the value represents the same second of the same day as that of the {@code other} provided.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify(*).sameSecondAs(null)                                                      => FAIL
     * Verifier.verify(null).sameSecondAs(*)                                                      => FAIL
     * Verifier.verify(parse("31 Oct 2016 13:45:30")).sameSecondAs(parse("31 Oct 2016 13:15:30")) => FAIL
     * Verifier.verify(parse("31 Oct 2016 13:45:30")).sameSecondAs(parse("31 Oct 2016 13:45:02")) => FAIL
     * Verifier.verify(parse("31 Oct 2016 13:45:30")).sameSecondAs(parse("31 Oct 2016 13:45:30")) => PASS
     * </pre>
     *
     * @param other
     *         the object to compare against the second of this value (may be {@literal null})
     * @return A reference to this {@link BaseTimeVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     */
    public V sameSecondAs(final T other) throws VerifierException {
        final Calendar calendar1 = getCalendar(verification().getValue());
        final Calendar calendar2 = getCalendar(other);
        final boolean result = calendar1 != null && calendar2 != null &&
            (calendar1.get(Calendar.ERA) == calendar2.get(Calendar.ERA) &&
                calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) &&
                calendar1.get(Calendar.DAY_OF_YEAR) == calendar2.get(Calendar.DAY_OF_YEAR) &&
                calendar1.get(Calendar.HOUR_OF_DAY) == calendar2.get(Calendar.HOUR_OF_DAY) &&
                calendar1.get(Calendar.MINUTE) == calendar2.get(Calendar.MINUTE) &&
                calendar1.get(Calendar.SECOND) == calendar2.get(Calendar.SECOND));

        verification().check(result, "be same second as '%s'", other);

        return chain();
    }

    /**
     * <p>
     * Verifies that the value represents the exact same time as that of the {@code other} provided.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify(*).sameTimeAs(null)                                                              => FAIL
     * Verifier.verify(null).sameTimeAs(*)                                                              => FAIL
     * Verifier.verify(parse("31 Oct 2016 13:45:30.123")).sameTimeAs(parse("31 Oct 2016 13:45:15.123")) => FAIL
     * Verifier.verify(parse("31 Oct 2016 13:45:30.123")).sameTimeAs(parse("31 Oct 2016 13:45:30.789")) => FAIL
     * Verifier.verify(parse("31 Oct 2016 13:45:30.123")).sameTimeAs(parse("31 Oct 2016 13:45:30.123")) => PASS
     * </pre>
     *
     * @param other
     *         the object to compare against the time of this value (may be {@literal null})
     * @return A reference to this {@link BaseTimeVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     */
    public V sameTimeAs(final T other) throws VerifierException {
        final Calendar calendar1 = getCalendar(verification().getValue());
        final Calendar calendar2 = getCalendar(other);
        final boolean result = calendar1 != null && calendar2 != null &&
            calendar1.getTimeInMillis() == calendar2.getTimeInMillis();

        verification().check(result, "be same time as '%s'", other);

        return chain();
    }

    /**
     * <p>
     * Verifies that the value represents the same week as that of the {@code other} provided.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify(*).sameWeekAs(null)                                                      => FAIL
     * Verifier.verify(null).sameWeekAs(*)                                                      => FAIL
     * Verifier.verify(parse("31 Oct 2016 13:45:30")).sameWeekAs(parse("31 Oct 2017 13:45:30")) => FAIL
     * Verifier.verify(parse("31 Oct 2016 13:45:30")).sameWeekAs(parse("15 Oct 2016 13:45:30")) => FAIL
     * Verifier.verify(parse("31 Oct 2016 13:45:30")).sameWeekAs(parse("1 Nov 2016 00:07:02"))  => PASS
     * </pre>
     *
     * @param other
     *         the object to compare against the week of this value (may be {@literal null})
     * @return A reference to this {@link BaseTimeVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     */
    public V sameWeekAs(final T other) throws VerifierException {
        final Calendar calendar1 = getCalendar(verification().getValue());
        final Calendar calendar2 = getCalendar(other);
        final boolean result = calendar1 != null && calendar2 != null &&
            (calendar1.get(Calendar.ERA) == calendar2.get(Calendar.ERA) &&
                calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) &&
                calendar1.get(Calendar.WEEK_OF_YEAR) == calendar2.get(Calendar.WEEK_OF_YEAR));

        verification().check(result, "be same week as '%s'", other);

        return chain();
    }

    /**
     * <p>
     * Verifies that the value represents the same year as that of the {@code other} provided.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify(*).sameYearAs(null)                                                            => FAIL
     * Verifier.verify(null).sameYearAs(*)                                                            => FAIL
     * Verifier.verify(parse("31 Oct 2016 AD 13:45:30")).sameYearAs(parse("31 Oct 2016 BC 13:45:30")) => FAIL
     * Verifier.verify(parse("31 Oct 2016 AD 13:45:30")).sameYearAs(parse("31 Oct 2017 AD 13:45:30")) => FAIL
     * Verifier.verify(parse("31 Oct 2016 AD 13:45:30")).sameYearAs(parse("15 Aug 2016 AD 00:07:02")) => PASS
     * </pre>
     *
     * @param other
     *         the object to compare against the year of this value (may be {@literal null})
     * @return A reference to this {@link BaseTimeVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     */
    public V sameYearAs(final T other) throws VerifierException {
        final Calendar calendar1 = getCalendar(verification().getValue());
        final Calendar calendar2 = getCalendar(other);
        final boolean result = calendar1 != null && calendar2 != null &&
            (calendar1.get(Calendar.ERA) == calendar2.get(Calendar.ERA) &&
                calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR));

        verification().check(result, "be same year as '%s'", other);

        return chain();
    }

    /**
     * <p>
     * Returns a calendar representation of the specified {@code value}.
     * </p>
     * <p>
     * Implementations should ensure that this method returns {@literal null} when {@code value} is {@literal null}.
     * </p>
     *
     * @param value
     *         the value to be transformed into a {@code Calendar}
     * @return A {@code Calendar} instance to represent {@code value} or {@literal null} if {@code value} is {@literal
     * null}.
     */
    protected abstract Calendar getCalendar(T value);
}
