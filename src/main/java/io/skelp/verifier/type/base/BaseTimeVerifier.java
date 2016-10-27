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
 * TODO: Document
 *
 * @param <T>
 * @param <V>
 * @author Alasdair Mercer
 */
public abstract class BaseTimeVerifier<T extends Comparable<? super T>, V extends BaseTimeVerifier<T, V>> extends BaseComparableVerifier<T, V> {

    // TODO: Add sameEraAs method

    /**
     * TODO: Document
     *
     * @param verification
     */
    public BaseTimeVerifier(final Verification<T> verification) {
        super(verification);
    }

    /**
     * TODO: Document
     *
     * @param other
     * @return
     * @throws VerifierException
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
     * TODO: Document
     *
     * @param other
     * @return
     * @throws VerifierException
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
     * TODO: Document
     *
     * @param other
     * @return
     * @throws VerifierException
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
     * TODO: Document
     *
     * @param other
     * @return
     * @throws VerifierException
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
     * TODO: Document
     *
     * @param other
     * @return
     * @throws VerifierException
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
     * TODO: Document
     *
     * @param other
     * @return
     * @throws VerifierException
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
     * TODO: Document
     *
     * @param other
     * @return
     * @throws VerifierException
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
     * TODO: Document
     *
     * @param other
     * @return
     * @throws VerifierException
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
     * TODO: Document
     *
     * @param value
     * @return
     */
    protected abstract Calendar getCalendar(T value);
}
