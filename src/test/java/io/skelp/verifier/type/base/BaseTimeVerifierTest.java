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

import io.skelp.verifier.verification.Verification;

/**
 * Tests for the {@link BaseTimeVerifier} class.
 *
 * @author Alasdair Mercer
 */
public class BaseTimeVerifierTest extends BaseTimeVerifierTestBase<BaseTimeVerifierTest.CalendarWrapper, BaseTimeVerifierTest.BaseTimeVerifierTestImpl> {

    @Override
    protected BaseTimeVerifierTestImpl createCustomVerifier() {
        return new BaseTimeVerifierTestImpl(getMockVerification());
    }

    @Override
    protected CalendarWrapper getValueForCalendar(Calendar calendar) {
        return calendar == null ? null : new CalendarWrapper(calendar);
    }

    static class BaseTimeVerifierTestImpl extends BaseTimeVerifier<CalendarWrapper, BaseTimeVerifierTestImpl> {

        BaseTimeVerifierTestImpl(Verification<CalendarWrapper> verification) {
            super(verification);
        }

        @Override
        protected Calendar getCalendar(CalendarWrapper value) {
            return value == null ? null : value.calendar;
        }
    }

    static class CalendarWrapper implements Comparable<CalendarWrapper> {

        final Calendar calendar;

        CalendarWrapper(Calendar calendar) {
            this.calendar = calendar;
        }

        @Override
        public int compareTo(CalendarWrapper other) {
            return calendar.compareTo(other.calendar);
        }
    }
}
