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
 * Test case for {@link BaseTimeVerifier} implementation classes.
 *
 * @param <T>
 *         the {@code Comparable} value type for the {@link BaseTimeVerifier} being tested
 * @param <V>
 *         the type of the {@link BaseTimeVerifier} being tested
 * @author Alasdair Mercer
 */
public abstract class BaseTimeVerifierTestCase<T extends Comparable<? super T>, V extends BaseTimeVerifier<T, V>> extends CustomVerifierTestCaseBase<T, V> {

    /**
     * TODO: Document
     *
     * @param era
     * @param year
     * @param dayOfYear
     * @param hourOfDay
     * @param minute
     * @param second
     * @return
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
    public void testSameDayWithDifferentDay() {
        Calendar value = createCalendar(GregorianCalendar.AD, 2016, 1, 0, 0, 0);
        Calendar other = createCalendar(GregorianCalendar.AD, 2016, 2, 0, 0, 0);

        testSameDayHelper(value, other, false);
    }

    @Test
    public void testSameDayWithDifferentEra() {
        Calendar value = createCalendar(GregorianCalendar.BC, 2016, 1, 0, 0, 0);
        Calendar other = createCalendar(GregorianCalendar.AD, 2016, 1, 0, 0, 0);

        testSameDayHelper(value, other, false);
    }

    @Test
    public void testSameDayWithDifferentYear() {
        Calendar value = createCalendar(GregorianCalendar.AD, 2016, 1, 0, 0, 0);
        Calendar other = createCalendar(GregorianCalendar.AD, 2017, 1, 0, 0, 0);

        testSameDayHelper(value, other, false);
    }

    @Test
    public void testSameDayWithNullOther() {
        testSameDayHelper(Calendar.getInstance(), null, false);
    }

    @Test
    public void testSameDayWithNullValue() {
        testSameDayHelper(null, Calendar.getInstance(), false);
    }

    @Test
    public void testSameDayWithNullValueAndNullOther() {
        testSameDayHelper(null, null, false);
    }

    @Test
    public void testSameDayWithSameDay() {
        Calendar value = createCalendar(GregorianCalendar.AD, 2016, 1, 0, 0, 0);
        Calendar other = createCalendar(GregorianCalendar.AD, 2016, 1, 1, 1, 1);

        testSameDayHelper(value, other, true);
    }

    private void testSameDayHelper(Calendar valueCalendar, Calendar otherCalendar, boolean expected) {
        T value = createValueForCalendar(valueCalendar);
        T other = createValueForCalendar(otherCalendar);

        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().sameDay(other));

        verify(getMockVerification()).check(eq(expected), eq("be same day as '%s'"), getArgsCaptor().capture());

        assertSame("Passes other for message formatting", other, getArgsCaptor().getValue());
    }

    @Test
    public void testSameHourWithDifferentDay() {
        Calendar value = createCalendar(GregorianCalendar.AD, 2016, 1, 0, 0, 0);
        Calendar other = createCalendar(GregorianCalendar.AD, 2016, 2, 0, 0, 0);

        testSameHourHelper(value, other, false);
    }

    @Test
    public void testSameHourWithDifferentEra() {
        Calendar value = createCalendar(GregorianCalendar.BC, 2016, 1, 0, 0, 0);
        Calendar other = createCalendar(GregorianCalendar.AD, 2016, 1, 0, 0, 0);

        testSameHourHelper(value, other, false);
    }

    @Test
    public void testSameHourWithDifferentHour() {
        Calendar value = createCalendar(GregorianCalendar.AD, 2016, 1, 0, 0, 0);
        Calendar other = createCalendar(GregorianCalendar.AD, 2016, 1, 1, 0, 0);

        testSameHourHelper(value, other, false);
    }

    @Test
    public void testSameHourWithDifferentYear() {
        Calendar value = createCalendar(GregorianCalendar.AD, 2016, 1, 0, 0, 0);
        Calendar other = createCalendar(GregorianCalendar.AD, 2017, 1, 0, 0, 0);

        testSameHourHelper(value, other, false);
    }

    @Test
    public void testSameHourWithNullOther() {
        testSameHourHelper(Calendar.getInstance(), null, false);
    }

    @Test
    public void testSameHourWithNullValue() {
        testSameHourHelper(null, Calendar.getInstance(), false);
    }

    @Test
    public void testSameHourWithNullValueAndNullOther() {
        testSameHourHelper(null, null, false);
    }

    @Test
    public void testSameHourWithSameHour() {
        Calendar value = createCalendar(GregorianCalendar.AD, 2016, 1, 0, 0, 0);
        Calendar other = createCalendar(GregorianCalendar.AD, 2016, 1, 0, 1, 1);

        testSameHourHelper(value, other, true);
    }

    private void testSameHourHelper(Calendar valueCalendar, Calendar otherCalendar, boolean expected) {
        T value = createValueForCalendar(valueCalendar);
        T other = createValueForCalendar(otherCalendar);

        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().sameHour(other));

        verify(getMockVerification()).check(eq(expected), eq("be same hour as '%s'"), getArgsCaptor().capture());

        assertSame("Passes other for message formatting", other, getArgsCaptor().getValue());
    }

    @Test
    public void testSameMinuteWithDifferentDay() {
        Calendar value = createCalendar(GregorianCalendar.AD, 2016, 1, 0, 0, 0);
        Calendar other = createCalendar(GregorianCalendar.AD, 2016, 2, 0, 0, 0);

        testSameMinuteHelper(value, other, false);
    }

    @Test
    public void testSameMinuteWithDifferentEra() {
        Calendar value = createCalendar(GregorianCalendar.BC, 2016, 1, 0, 0, 0);
        Calendar other = createCalendar(GregorianCalendar.AD, 2016, 1, 0, 0, 0);

        testSameMinuteHelper(value, other, false);
    }

    @Test
    public void testSameMinuteWithDifferentHour() {
        Calendar value = createCalendar(GregorianCalendar.AD, 2016, 1, 0, 0, 0);
        Calendar other = createCalendar(GregorianCalendar.AD, 2016, 1, 1, 0, 0);

        testSameMinuteHelper(value, other, false);
    }

    @Test
    public void testSameMinuteWithDifferentMinute() {
        Calendar value = createCalendar(GregorianCalendar.AD, 2016, 1, 0, 0, 0);
        Calendar other = createCalendar(GregorianCalendar.AD, 2016, 1, 0, 1, 0);

        testSameMinuteHelper(value, other, false);
    }

    @Test
    public void testSameMinuteWithDifferentYear() {
        Calendar value = createCalendar(GregorianCalendar.AD, 2016, 1, 0, 0, 0);
        Calendar other = createCalendar(GregorianCalendar.AD, 2017, 1, 0, 0, 0);

        testSameMinuteHelper(value, other, false);
    }

    @Test
    public void testSameMinuteWithNullOther() {
        testSameMinuteHelper(Calendar.getInstance(), null, false);
    }

    @Test
    public void testSameMinuteWithNullValue() {
        testSameMinuteHelper(null, Calendar.getInstance(), false);
    }

    @Test
    public void testSameMinuteWithNullValueAndNullOther() {
        testSameMinuteHelper(null, null, false);
    }

    @Test
    public void testSameMinuteWithSameMinute() {
        Calendar value = createCalendar(GregorianCalendar.AD, 2016, 1, 0, 0, 0);
        Calendar other = createCalendar(GregorianCalendar.AD, 2016, 1, 0, 0, 1);

        testSameMinuteHelper(value, other, true);
    }

    private void testSameMinuteHelper(Calendar valueCalendar, Calendar otherCalendar, boolean expected) {
        T value = createValueForCalendar(valueCalendar);
        T other = createValueForCalendar(otherCalendar);

        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().sameMinute(other));

        verify(getMockVerification()).check(eq(expected), eq("be same minute as '%s'"), getArgsCaptor().capture());

        assertSame("Passes other for message formatting", other, getArgsCaptor().getValue());
    }

    @Test
    public void testSameMonthWithDifferentEra() {
        Calendar value = createCalendarForMonth(GregorianCalendar.BC, 2016, 0, 1);
        Calendar other = createCalendarForMonth(GregorianCalendar.AD, 2016, 0, 1);

        testSameMonthHelper(value, other, false);
    }

    @Test
    public void testSameMonthWithDifferentMonth() {
        Calendar value = createCalendarForMonth(GregorianCalendar.AD, 2016, 0, 1);
        Calendar other = createCalendarForMonth(GregorianCalendar.AD, 2016, 1, 1);

        testSameMonthHelper(value, other, false);
    }

    @Test
    public void testSameMonthWithDifferentYear() {
        Calendar value = createCalendarForMonth(GregorianCalendar.AD, 2016, 0, 1);
        Calendar other = createCalendarForMonth(GregorianCalendar.AD, 2017, 0, 1);

        testSameMonthHelper(value, other, false);
    }

    @Test
    public void testSameMonthWithNullOther() {
        testSameMonthHelper(Calendar.getInstance(), null, false);
    }

    @Test
    public void testSameMonthWithNullValue() {
        testSameMonthHelper(null, Calendar.getInstance(), false);
    }

    @Test
    public void testSameMonthWithNullValueAndNullOther() {
        testSameMonthHelper(null, null, false);
    }

    @Test
    public void testSameMonthWithSameMonth() {
        Calendar value = createCalendarForMonth(GregorianCalendar.AD, 2016, 0, 1);
        Calendar other = createCalendarForMonth(GregorianCalendar.AD, 2016, 0, 2);

        testSameMonthHelper(value, other, true);
    }

    private void testSameMonthHelper(Calendar valueCalendar, Calendar otherCalendar, boolean expected) {
        T value = createValueForCalendar(valueCalendar);
        T other = createValueForCalendar(otherCalendar);

        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().sameMonth(other));

        verify(getMockVerification()).check(eq(expected), eq("be same month as '%s'"), getArgsCaptor().capture());

        assertSame("Passes other for message formatting", other, getArgsCaptor().getValue());
    }

    @Test
    public void testSameSecondWithDifferentDay() {
        Calendar value = createCalendar(GregorianCalendar.AD, 2016, 1, 0, 0, 0);
        Calendar other = createCalendar(GregorianCalendar.AD, 2016, 2, 0, 0, 0);

        testSameSecondHelper(value, other, false);
    }

    @Test
    public void testSameSecondWithDifferentEra() {
        Calendar value = createCalendar(GregorianCalendar.BC, 2016, 1, 0, 0, 0);
        Calendar other = createCalendar(GregorianCalendar.AD, 2016, 1, 0, 0, 0);

        testSameSecondHelper(value, other, false);
    }

    @Test
    public void testSameSecondWithDifferentHour() {
        Calendar value = createCalendar(GregorianCalendar.AD, 2016, 1, 0, 0, 0);
        Calendar other = createCalendar(GregorianCalendar.AD, 2016, 1, 1, 0, 0);

        testSameSecondHelper(value, other, false);
    }

    @Test
    public void testSameSecondWithDifferentMinute() {
        Calendar value = createCalendar(GregorianCalendar.AD, 2016, 1, 0, 0, 0);
        Calendar other = createCalendar(GregorianCalendar.AD, 2016, 1, 0, 1, 0);

        testSameSecondHelper(value, other, false);
    }

    @Test
    public void testSameSecondWithDifferentSecond() {
        Calendar value = createCalendar(GregorianCalendar.AD, 2016, 1, 0, 0, 0);
        Calendar other = createCalendar(GregorianCalendar.AD, 2016, 1, 0, 0, 1);

        testSameSecondHelper(value, other, false);
    }

    @Test
    public void testSameSecondWithDifferentYear() {
        Calendar value = createCalendar(GregorianCalendar.AD, 2016, 1, 0, 0, 0);
        Calendar other = createCalendar(GregorianCalendar.AD, 2017, 1, 0, 0, 0);

        testSameSecondHelper(value, other, false);
    }

    @Test
    public void testSameSecondWithNullOther() {
        testSameSecondHelper(Calendar.getInstance(), null, false);
    }

    @Test
    public void testSameSecondWithNullValue() {
        testSameSecondHelper(null, Calendar.getInstance(), false);
    }

    @Test
    public void testSameSecondWithNullValueAndNullOther() {
        testSameSecondHelper(null, null, false);
    }

    @Test
    public void testSameSecondWithSameSecond() {
        Calendar value = createCalendar(GregorianCalendar.AD, 2016, 1, 0, 0, 0);
        Calendar other = createCalendar(GregorianCalendar.AD, 2016, 1, 0, 0, 0);

        testSameSecondHelper(value, other, true);
    }

    private void testSameSecondHelper(Calendar valueCalendar, Calendar otherCalendar, boolean expected) {
        T value = createValueForCalendar(valueCalendar);
        T other = createValueForCalendar(otherCalendar);

        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().sameSecond(other));

        verify(getMockVerification()).check(eq(expected), eq("be same second as '%s'"), getArgsCaptor().capture());

        assertSame("Passes other for message formatting", other, getArgsCaptor().getValue());
    }

    @Test
    public void testSameTimeWithDifferentTime() {
        Calendar value = createCalendar(GregorianCalendar.AD, 2016, 1, 0, 0, 0);
        Calendar other = createCalendar(GregorianCalendar.AD, 2016, 1, 0, 0, 1);

        testSameTimeHelper(value, other, false);
    }

    @Test
    public void testSameTimeWithNullOther() {
        testSameTimeHelper(Calendar.getInstance(), null, false);
    }

    @Test
    public void testSameTimeWithNullValue() {
        testSameTimeHelper(null, Calendar.getInstance(), false);
    }

    @Test
    public void testSameTimeWithNullValueAndNullOther() {
        testSameTimeHelper(null, null, false);
    }

    @Test
    public void testSameTimeWithSameTime() {
        Calendar value = createCalendar(GregorianCalendar.AD, 2016, 1, 0, 0, 0);
        Calendar other = createCalendar(GregorianCalendar.AD, 2016, 1, 0, 0, 0);

        testSameTimeHelper(value, other, true);
    }

    private void testSameTimeHelper(Calendar valueCalendar, Calendar otherCalendar, boolean expected) {
        T value = createValueForCalendar(valueCalendar);
        T other = createValueForCalendar(otherCalendar);

        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().sameTime(other));

        verify(getMockVerification()).check(eq(expected), eq("be same time as '%s'"), getArgsCaptor().capture());

        assertSame("Passes other for message formatting", other, getArgsCaptor().getValue());
    }

    @Test
    public void testSameWeekWithDifferentEra() {
        Calendar value = createCalendarForWeek(GregorianCalendar.BC, 2016, 1, Calendar.SATURDAY);
        Calendar other = createCalendarForWeek(GregorianCalendar.AD, 2016, 1, Calendar.SATURDAY);

        testSameWeekHelper(value, other, false);
    }

    @Test
    public void testSameWeekWithDifferentWeek() {
        Calendar value = createCalendarForWeek(GregorianCalendar.AD, 2016, 1, Calendar.SATURDAY);
        Calendar other = createCalendarForWeek(GregorianCalendar.AD, 2016, 2, Calendar.SATURDAY);

        testSameWeekHelper(value, other, false);
    }

    @Test
    public void testSameWeekWithDifferentYear() {
        Calendar value = createCalendarForWeek(GregorianCalendar.AD, 2016, 1, Calendar.SATURDAY);
        Calendar other = createCalendarForWeek(GregorianCalendar.AD, 2017, 1, Calendar.SATURDAY);

        testSameWeekHelper(value, other, false);
    }

    @Test
    public void testSameWeekWithNullOther() {
        testSameWeekHelper(Calendar.getInstance(), null, false);
    }

    @Test
    public void testSameWeekWithNullValue() {
        testSameWeekHelper(null, Calendar.getInstance(), false);
    }

    @Test
    public void testSameWeekWithNullValueAndNullOther() {
        testSameWeekHelper(null, null, false);
    }

    @Test
    public void testSameWeekWithSameWeek() {
        Calendar value = createCalendarForWeek(GregorianCalendar.AD, 2016, 1, Calendar.SATURDAY);
        Calendar other = createCalendarForWeek(GregorianCalendar.AD, 2016, 1, Calendar.SUNDAY);

        testSameWeekHelper(value, other, true);
    }

    private void testSameWeekHelper(Calendar valueCalendar, Calendar otherCalendar, boolean expected) {
        T value = createValueForCalendar(valueCalendar);
        T other = createValueForCalendar(otherCalendar);

        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().sameWeek(other));

        verify(getMockVerification()).check(eq(expected), eq("be same week as '%s'"), getArgsCaptor().capture());

        assertSame("Passes other for message formatting", other, getArgsCaptor().getValue());
    }

    @Test
    public void testSameYearWithDifferentEra() {
        Calendar value = createCalendarForMonth(GregorianCalendar.BC, 2016, 0, 1);
        Calendar other = createCalendarForMonth(GregorianCalendar.AD, 2016, 0, 1);

        testSameYearHelper(value, other, false);
    }

    @Test
    public void testSameYearWithDifferentYear() {
        Calendar value = createCalendarForMonth(GregorianCalendar.AD, 2016, 0, 1);
        Calendar other = createCalendarForMonth(GregorianCalendar.AD, 2017, 0, 1);

        testSameYearHelper(value, other, false);
    }

    @Test
    public void testSameYearWithNullOther() {
        testSameYearHelper(Calendar.getInstance(), null, false);
    }

    @Test
    public void testSameYearWithNullValue() {
        testSameYearHelper(null, Calendar.getInstance(), false);
    }

    @Test
    public void testSameYearWithNullValueAndNullOther() {
        testSameYearHelper(null, null, false);
    }

    @Test
    public void testSameYearWithSameYear() {
        Calendar value = createCalendarForMonth(GregorianCalendar.AD, 2016, 0, 1);
        Calendar other = createCalendarForMonth(GregorianCalendar.AD, 2016, 1, 2);

        testSameYearHelper(value, other, true);
    }

    private void testSameYearHelper(Calendar valueCalendar, Calendar otherCalendar, boolean expected) {
        T value = createValueForCalendar(valueCalendar);
        T other = createValueForCalendar(otherCalendar);

        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().sameYear(other));

        verify(getMockVerification()).check(eq(expected), eq("be same year as '%s'"), getArgsCaptor().capture());

        assertSame("Passes other for message formatting", other, getArgsCaptor().getValue());
    }

    /**
     * Creates an instance of the value being verified that represents the specified {@code calendar}.
     * <p/>
     * {@code calendar} may be {@literal null} in which case an appropriate value should be returned, which may itself
     * be {@literal null}.
     *
     * @param calendar
     *         the {@code Calendar} for which the value representation is to be returned (may be {@literal null})
     * @return The value representation for {@code calendar} which may be {@literal null} if  {@code calendar} is
     * {@literal null} and that is the best representation for the value.
     */
    protected abstract T createValueForCalendar(Calendar calendar);
}
