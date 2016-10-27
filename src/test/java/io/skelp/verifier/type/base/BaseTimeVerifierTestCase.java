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

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Calendar;
import java.util.GregorianCalendar;
import org.junit.Test;

import io.skelp.verifier.CustomVerifierTestCaseBase;

/**
 * <p>
 * Test case for {@link BaseTimeVerifier} implementation classes.
 * </p>
 *
 * @param <T>
 *         the {@code Comparable} value type for the {@link BaseTimeVerifier} being tested
 * @param <V>
 *         the type of the {@link BaseTimeVerifier} being tested
 * @author Alasdair Mercer
 */
public abstract class BaseTimeVerifierTestCase<T extends Comparable<? super T>, V extends BaseTimeVerifier<T, V>> extends CustomVerifierTestCaseBase<T, V> {

    /**
     * <p>
     * Creates a {@code Calendar} instance based on the information provided.
     * </p>
     *
     * @param era
     *         the era to be used
     * @param year
     *         the year to be used
     * @param dayOfYear
     *         the day of year to be used
     * @param hourOfDay
     *         the hour of day to be used
     * @param minute
     *         the minute to be used
     * @param second
     *         the second to be used
     * @return The newly created {@code Calendar}.
     */
    public static Calendar createCalendar(int era, int year, int dayOfYear, int hourOfDay, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.ERA, era);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.DAY_OF_YEAR, dayOfYear);
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar;
    }

    private static Calendar createCalendarForMonth(int era, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.ERA, era);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar;
    }

    private static Calendar createCalendarForWeek(int era, int year, int weekOfYear, int dayOfWeek) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.ERA, era);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.WEEK_OF_YEAR, weekOfYear);
        calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar;
    }

    @Test
    public void testSameDayAsWithDifferentDay() {
        Calendar value = createCalendar(GregorianCalendar.AD, 2016, 1, 0, 0, 0);
        Calendar other = createCalendar(GregorianCalendar.AD, 2016, 2, 0, 0, 0);

        testSameDayAsHelper(value, other, false);
    }

    @Test
    public void testSameDayAsWithDifferentEra() {
        Calendar value = createCalendar(GregorianCalendar.BC, 2016, 1, 0, 0, 0);
        Calendar other = createCalendar(GregorianCalendar.AD, 2016, 1, 0, 0, 0);

        testSameDayAsHelper(value, other, false);
    }

    @Test
    public void testSameDayAsWithDifferentYear() {
        Calendar value = createCalendar(GregorianCalendar.AD, 2016, 1, 0, 0, 0);
        Calendar other = createCalendar(GregorianCalendar.AD, 2017, 1, 0, 0, 0);

        testSameDayAsHelper(value, other, false);
    }

    @Test
    public void testSameDayAsWithNullOther() {
        testSameDayAsHelper(Calendar.getInstance(), null, false);
    }

    @Test
    public void testSameDayAsWithNullValue() {
        testSameDayAsHelper(null, Calendar.getInstance(), false);
    }

    @Test
    public void testSameDayAsWithNullValueAndNullOther() {
        testSameDayAsHelper(null, null, false);
    }

    @Test
    public void testSameDayAsWithSameDay() {
        Calendar value = createCalendar(GregorianCalendar.AD, 2016, 1, 0, 0, 0);
        Calendar other = createCalendar(GregorianCalendar.AD, 2016, 1, 1, 1, 1);

        testSameDayAsHelper(value, other, true);
    }

    private void testSameDayAsHelper(Calendar valueCalendar, Calendar otherCalendar, boolean expected) {
        T value = createValueForCalendar(valueCalendar);
        T other = createValueForCalendar(otherCalendar);

        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().sameDayAs(other));

        verify(getMockVerification()).check(eq(expected), eq("be same day as '%s'"), getArgsCaptor().capture());

        assertSame("Passes other for message formatting", other, getArgsCaptor().getValue());
    }

    @Test
    public void testSameHourAsWithDifferentDay() {
        Calendar value = createCalendar(GregorianCalendar.AD, 2016, 1, 0, 0, 0);
        Calendar other = createCalendar(GregorianCalendar.AD, 2016, 2, 0, 0, 0);

        testSameHourAsHelper(value, other, false);
    }

    @Test
    public void testSameHourAsWithDifferentEra() {
        Calendar value = createCalendar(GregorianCalendar.BC, 2016, 1, 0, 0, 0);
        Calendar other = createCalendar(GregorianCalendar.AD, 2016, 1, 0, 0, 0);

        testSameHourAsHelper(value, other, false);
    }

    @Test
    public void testSameHourAsWithDifferentHour() {
        Calendar value = createCalendar(GregorianCalendar.AD, 2016, 1, 0, 0, 0);
        Calendar other = createCalendar(GregorianCalendar.AD, 2016, 1, 1, 0, 0);

        testSameHourAsHelper(value, other, false);
    }

    @Test
    public void testSameHourAsWithDifferentYear() {
        Calendar value = createCalendar(GregorianCalendar.AD, 2016, 1, 0, 0, 0);
        Calendar other = createCalendar(GregorianCalendar.AD, 2017, 1, 0, 0, 0);

        testSameHourAsHelper(value, other, false);
    }

    @Test
    public void testSameHourAsWithNullOther() {
        testSameHourAsHelper(Calendar.getInstance(), null, false);
    }

    @Test
    public void testSameHourAsWithNullValue() {
        testSameHourAsHelper(null, Calendar.getInstance(), false);
    }

    @Test
    public void testSameHourAsWithNullValueAndNullOther() {
        testSameHourAsHelper(null, null, false);
    }

    @Test
    public void testSameHourAsWithSameHour() {
        Calendar value = createCalendar(GregorianCalendar.AD, 2016, 1, 0, 0, 0);
        Calendar other = createCalendar(GregorianCalendar.AD, 2016, 1, 0, 1, 1);

        testSameHourAsHelper(value, other, true);
    }

    private void testSameHourAsHelper(Calendar valueCalendar, Calendar otherCalendar, boolean expected) {
        T value = createValueForCalendar(valueCalendar);
        T other = createValueForCalendar(otherCalendar);

        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().sameHourAs(other));

        verify(getMockVerification()).check(eq(expected), eq("be same hour as '%s'"), getArgsCaptor().capture());

        assertSame("Passes other for message formatting", other, getArgsCaptor().getValue());
    }

    @Test
    public void testSameMinuteAsWithDifferentDay() {
        Calendar value = createCalendar(GregorianCalendar.AD, 2016, 1, 0, 0, 0);
        Calendar other = createCalendar(GregorianCalendar.AD, 2016, 2, 0, 0, 0);

        testSameMinuteAsHelper(value, other, false);
    }

    @Test
    public void testSameMinuteAsWithDifferentEra() {
        Calendar value = createCalendar(GregorianCalendar.BC, 2016, 1, 0, 0, 0);
        Calendar other = createCalendar(GregorianCalendar.AD, 2016, 1, 0, 0, 0);

        testSameMinuteAsHelper(value, other, false);
    }

    @Test
    public void testSameMinuteAsWithDifferentHour() {
        Calendar value = createCalendar(GregorianCalendar.AD, 2016, 1, 0, 0, 0);
        Calendar other = createCalendar(GregorianCalendar.AD, 2016, 1, 1, 0, 0);

        testSameMinuteAsHelper(value, other, false);
    }

    @Test
    public void testSameMinuteAsWithDifferentMinute() {
        Calendar value = createCalendar(GregorianCalendar.AD, 2016, 1, 0, 0, 0);
        Calendar other = createCalendar(GregorianCalendar.AD, 2016, 1, 0, 1, 0);

        testSameMinuteAsHelper(value, other, false);
    }

    @Test
    public void testSameMinuteAsWithDifferentYear() {
        Calendar value = createCalendar(GregorianCalendar.AD, 2016, 1, 0, 0, 0);
        Calendar other = createCalendar(GregorianCalendar.AD, 2017, 1, 0, 0, 0);

        testSameMinuteAsHelper(value, other, false);
    }

    @Test
    public void testSameMinuteAsWithNullOther() {
        testSameMinuteAsHelper(Calendar.getInstance(), null, false);
    }

    @Test
    public void testSameMinuteAsWithNullValue() {
        testSameMinuteAsHelper(null, Calendar.getInstance(), false);
    }

    @Test
    public void testSameMinuteAsWithNullValueAndNullOther() {
        testSameMinuteAsHelper(null, null, false);
    }

    @Test
    public void testSameMinuteAsWithSameMinute() {
        Calendar value = createCalendar(GregorianCalendar.AD, 2016, 1, 0, 0, 0);
        Calendar other = createCalendar(GregorianCalendar.AD, 2016, 1, 0, 0, 1);

        testSameMinuteAsHelper(value, other, true);
    }

    private void testSameMinuteAsHelper(Calendar valueCalendar, Calendar otherCalendar, boolean expected) {
        T value = createValueForCalendar(valueCalendar);
        T other = createValueForCalendar(otherCalendar);

        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().sameMinuteAs(other));

        verify(getMockVerification()).check(eq(expected), eq("be same minute as '%s'"), getArgsCaptor().capture());

        assertSame("Passes other for message formatting", other, getArgsCaptor().getValue());
    }

    @Test
    public void testSameMonthAsWithDifferentEra() {
        Calendar value = createCalendarForMonth(GregorianCalendar.BC, 2016, 0, 1);
        Calendar other = createCalendarForMonth(GregorianCalendar.AD, 2016, 0, 1);

        testSameMonthAsHelper(value, other, false);
    }

    @Test
    public void testSameMonthAsWithDifferentMonth() {
        Calendar value = createCalendarForMonth(GregorianCalendar.AD, 2016, 0, 1);
        Calendar other = createCalendarForMonth(GregorianCalendar.AD, 2016, 1, 1);

        testSameMonthAsHelper(value, other, false);
    }

    @Test
    public void testSameMonthAsWithDifferentYear() {
        Calendar value = createCalendarForMonth(GregorianCalendar.AD, 2016, 0, 1);
        Calendar other = createCalendarForMonth(GregorianCalendar.AD, 2017, 0, 1);

        testSameMonthAsHelper(value, other, false);
    }

    @Test
    public void testSameMonthAsWithNullOther() {
        testSameMonthAsHelper(Calendar.getInstance(), null, false);
    }

    @Test
    public void testSameMonthAsWithNullValue() {
        testSameMonthAsHelper(null, Calendar.getInstance(), false);
    }

    @Test
    public void testSameMonthAsWithNullValueAndNullOther() {
        testSameMonthAsHelper(null, null, false);
    }

    @Test
    public void testSameMonthAsWithSameMonth() {
        Calendar value = createCalendarForMonth(GregorianCalendar.AD, 2016, 0, 1);
        Calendar other = createCalendarForMonth(GregorianCalendar.AD, 2016, 0, 2);

        testSameMonthAsHelper(value, other, true);
    }

    private void testSameMonthAsHelper(Calendar valueCalendar, Calendar otherCalendar, boolean expected) {
        T value = createValueForCalendar(valueCalendar);
        T other = createValueForCalendar(otherCalendar);

        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().sameMonthAs(other));

        verify(getMockVerification()).check(eq(expected), eq("be same month as '%s'"), getArgsCaptor().capture());

        assertSame("Passes other for message formatting", other, getArgsCaptor().getValue());
    }

    @Test
    public void testSameSecondAsWithDifferentDay() {
        Calendar value = createCalendar(GregorianCalendar.AD, 2016, 1, 0, 0, 0);
        Calendar other = createCalendar(GregorianCalendar.AD, 2016, 2, 0, 0, 0);

        testSameSecondAsHelper(value, other, false);
    }

    @Test
    public void testSameSecondAsWithDifferentEra() {
        Calendar value = createCalendar(GregorianCalendar.BC, 2016, 1, 0, 0, 0);
        Calendar other = createCalendar(GregorianCalendar.AD, 2016, 1, 0, 0, 0);

        testSameSecondAsHelper(value, other, false);
    }

    @Test
    public void testSameSecondAsWithDifferentHour() {
        Calendar value = createCalendar(GregorianCalendar.AD, 2016, 1, 0, 0, 0);
        Calendar other = createCalendar(GregorianCalendar.AD, 2016, 1, 1, 0, 0);

        testSameSecondAsHelper(value, other, false);
    }

    @Test
    public void testSameSecondAsWithDifferentMinute() {
        Calendar value = createCalendar(GregorianCalendar.AD, 2016, 1, 0, 0, 0);
        Calendar other = createCalendar(GregorianCalendar.AD, 2016, 1, 0, 1, 0);

        testSameSecondAsHelper(value, other, false);
    }

    @Test
    public void testSameSecondAsWithDifferentSecond() {
        Calendar value = createCalendar(GregorianCalendar.AD, 2016, 1, 0, 0, 0);
        Calendar other = createCalendar(GregorianCalendar.AD, 2016, 1, 0, 0, 1);

        testSameSecondAsHelper(value, other, false);
    }

    @Test
    public void testSameSecondAsWithDifferentYear() {
        Calendar value = createCalendar(GregorianCalendar.AD, 2016, 1, 0, 0, 0);
        Calendar other = createCalendar(GregorianCalendar.AD, 2017, 1, 0, 0, 0);

        testSameSecondAsHelper(value, other, false);
    }

    @Test
    public void testSameSecondAsWithNullOther() {
        testSameSecondAsHelper(Calendar.getInstance(), null, false);
    }

    @Test
    public void testSameSecondAsWithNullValue() {
        testSameSecondAsHelper(null, Calendar.getInstance(), false);
    }

    @Test
    public void testSameSecondAsWithNullValueAndNullOther() {
        testSameSecondAsHelper(null, null, false);
    }

    @Test
    public void testSameSecondAsWithSameSecond() {
        Calendar value = createCalendar(GregorianCalendar.AD, 2016, 1, 0, 0, 0);
        Calendar other = createCalendar(GregorianCalendar.AD, 2016, 1, 0, 0, 0);

        testSameSecondAsHelper(value, other, true);
    }

    private void testSameSecondAsHelper(Calendar valueCalendar, Calendar otherCalendar, boolean expected) {
        T value = createValueForCalendar(valueCalendar);
        T other = createValueForCalendar(otherCalendar);

        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().sameSecondAs(other));

        verify(getMockVerification()).check(eq(expected), eq("be same second as '%s'"), getArgsCaptor().capture());

        assertSame("Passes other for message formatting", other, getArgsCaptor().getValue());
    }

    @Test
    public void testSameTimeAsWithDifferentTime() {
        Calendar value = createCalendar(GregorianCalendar.AD, 2016, 1, 0, 0, 0);
        Calendar other = createCalendar(GregorianCalendar.AD, 2016, 1, 0, 0, 1);

        testSameTimeAsHelper(value, other, false);
    }

    @Test
    public void testSameTimeAsWithNullOther() {
        testSameTimeAsHelper(Calendar.getInstance(), null, false);
    }

    @Test
    public void testSameTimeAsWithNullValue() {
        testSameTimeAsHelper(null, Calendar.getInstance(), false);
    }

    @Test
    public void testSameTimeAsWithNullValueAndNullOther() {
        testSameTimeAsHelper(null, null, false);
    }

    @Test
    public void testSameTimeAsWithSameTime() {
        Calendar value = createCalendar(GregorianCalendar.AD, 2016, 1, 0, 0, 0);
        Calendar other = createCalendar(GregorianCalendar.AD, 2016, 1, 0, 0, 0);

        testSameTimeAsHelper(value, other, true);
    }

    private void testSameTimeAsHelper(Calendar valueCalendar, Calendar otherCalendar, boolean expected) {
        T value = createValueForCalendar(valueCalendar);
        T other = createValueForCalendar(otherCalendar);

        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().sameTimeAs(other));

        verify(getMockVerification()).check(eq(expected), eq("be same time as '%s'"), getArgsCaptor().capture());

        assertSame("Passes other for message formatting", other, getArgsCaptor().getValue());
    }

    @Test
    public void testSameWeekAsWithDifferentEra() {
        Calendar value = createCalendarForWeek(GregorianCalendar.BC, 2016, 1, Calendar.SATURDAY);
        Calendar other = createCalendarForWeek(GregorianCalendar.AD, 2016, 1, Calendar.SATURDAY);

        testSameWeekAsHelper(value, other, false);
    }

    @Test
    public void testSameWeekAsWithDifferentWeek() {
        Calendar value = createCalendarForWeek(GregorianCalendar.AD, 2016, 1, Calendar.SATURDAY);
        Calendar other = createCalendarForWeek(GregorianCalendar.AD, 2016, 2, Calendar.SATURDAY);

        testSameWeekAsHelper(value, other, false);
    }

    @Test
    public void testSameWeekAsWithDifferentYear() {
        Calendar value = createCalendarForWeek(GregorianCalendar.AD, 2016, 1, Calendar.SATURDAY);
        Calendar other = createCalendarForWeek(GregorianCalendar.AD, 2017, 1, Calendar.SATURDAY);

        testSameWeekAsHelper(value, other, false);
    }

    @Test
    public void testSameWeekAsWithNullOther() {
        testSameWeekAsHelper(Calendar.getInstance(), null, false);
    }

    @Test
    public void testSameWeekAsWithNullValue() {
        testSameWeekAsHelper(null, Calendar.getInstance(), false);
    }

    @Test
    public void testSameWeekAsWithNullValueAndNullOther() {
        testSameWeekAsHelper(null, null, false);
    }

    @Test
    public void testSameWeekAsWithSameWeek() {
        Calendar value = createCalendarForWeek(GregorianCalendar.AD, 2016, 1, Calendar.SATURDAY);
        Calendar other = createCalendarForWeek(GregorianCalendar.AD, 2016, 1, Calendar.SUNDAY);

        testSameWeekAsHelper(value, other, true);
    }

    private void testSameWeekAsHelper(Calendar valueCalendar, Calendar otherCalendar, boolean expected) {
        T value = createValueForCalendar(valueCalendar);
        T other = createValueForCalendar(otherCalendar);

        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().sameWeekAs(other));

        verify(getMockVerification()).check(eq(expected), eq("be same week as '%s'"), getArgsCaptor().capture());

        assertSame("Passes other for message formatting", other, getArgsCaptor().getValue());
    }

    @Test
    public void testSameYearAsWithDifferentEra() {
        Calendar value = createCalendarForMonth(GregorianCalendar.BC, 2016, 0, 1);
        Calendar other = createCalendarForMonth(GregorianCalendar.AD, 2016, 0, 1);

        testSameYearAsHelper(value, other, false);
    }

    @Test
    public void testSameYearAsWithDifferentYear() {
        Calendar value = createCalendarForMonth(GregorianCalendar.AD, 2016, 0, 1);
        Calendar other = createCalendarForMonth(GregorianCalendar.AD, 2017, 0, 1);

        testSameYearAsHelper(value, other, false);
    }

    @Test
    public void testSameYearAsWithNullOther() {
        testSameYearAsHelper(Calendar.getInstance(), null, false);
    }

    @Test
    public void testSameYearAsWithNullValue() {
        testSameYearAsHelper(null, Calendar.getInstance(), false);
    }

    @Test
    public void testSameYearAsWithNullValueAndNullOther() {
        testSameYearAsHelper(null, null, false);
    }

    @Test
    public void testSameYearAsWithSameYear() {
        Calendar value = createCalendarForMonth(GregorianCalendar.AD, 2016, 0, 1);
        Calendar other = createCalendarForMonth(GregorianCalendar.AD, 2016, 1, 2);

        testSameYearAsHelper(value, other, true);
    }

    private void testSameYearAsHelper(Calendar valueCalendar, Calendar otherCalendar, boolean expected) {
        T value = createValueForCalendar(valueCalendar);
        T other = createValueForCalendar(otherCalendar);

        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().sameYearAs(other));

        verify(getMockVerification()).check(eq(expected), eq("be same year as '%s'"), getArgsCaptor().capture());

        assertSame("Passes other for message formatting", other, getArgsCaptor().getValue());
    }

    /**
     * <p>
     * Creates an instance of the value being verified that represents the specified {@code calendar}.
     * </p>
     * <p>
     * {@code calendar} may be {@literal null} in which case an appropriate value should be returned, which may itself
     * be {@literal null}.
     * </p>
     *
     * @param calendar
     *         the {@code Calendar} for which the value representation is to be returned (may be {@literal null})
     * @return The value representation for {@code calendar} which may be {@literal null} if  {@code calendar} is
     * {@literal null} and that is the best representation for the value.
     */
    protected abstract T createValueForCalendar(Calendar calendar);
}
