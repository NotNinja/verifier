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

import java.util.Calendar;
import java.util.GregorianCalendar;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import io.skelp.verifier.AbstractCustomVerifierTestCase;
import io.skelp.verifier.type.base.BaseComparableVerifierTestCase;
import io.skelp.verifier.type.base.BaseTimeVerifierTestCase;

/**
 * Tests for the {@link CalendarVerifier} class.
 *
 * @author Alasdair Mercer
 */
@RunWith(Enclosed.class)
public class CalendarVerifierTest {

    public static class CalendarVerifierAbstractCustomVerifierTest extends AbstractCustomVerifierTestCase<Calendar, CalendarVerifier> {

        @Override
        protected CalendarVerifier createCustomVerifier() {
            return new CalendarVerifier(getMockVerification());
        }

        @Override
        protected Calendar createValueOne() {
            return BaseTimeVerifierTestCase.createCalendar(GregorianCalendar.AD, 2016, 1, 0, 0, 0);
        }

        @Override
        protected Calendar createValueTwo() {
            return BaseTimeVerifierTestCase.createCalendar(GregorianCalendar.AD, 2016, 1, 0, 1, 0);
        }

        @Override
        protected Class<?> getParentClass() {
            return Object.class;
        }

        @Override
        protected Class<?> getValueClass() {
            return Calendar.class;
        }
    }

    public static class CalendarVerifierBaseComparableVerifierTest extends BaseComparableVerifierTestCase<Calendar, CalendarVerifier> {

        @Override
        protected CalendarVerifier createCustomVerifier() {
            return new CalendarVerifier(getMockVerification());
        }

        @Override
        public Calendar getBaseValue() {
            return BaseTimeVerifierTestCase.createCalendar(GregorianCalendar.AD, 2016, 1, 0, 0, 30);
        }

        @Override
        public Calendar getHigherValue() {
            return BaseTimeVerifierTestCase.createCalendar(GregorianCalendar.AD, 2016, 1, 0, 0, 45);
        }

        @Override
        public Calendar getHighestValue() {
            return BaseTimeVerifierTestCase.createCalendar(GregorianCalendar.AD, 2016, 1, 0, 1, 0);
        }

        @Override
        public Calendar getLowerValue() {
            return BaseTimeVerifierTestCase.createCalendar(GregorianCalendar.AD, 2016, 1, 0, 0, 15);
        }

        @Override
        public Calendar getLowestValue() {
            return BaseTimeVerifierTestCase.createCalendar(GregorianCalendar.AD, 2016, 1, 0, 0, 0);
        }
    }

    public static class CalendarVerifierBaseTimeVerifierTest extends BaseTimeVerifierTestCase<Calendar, CalendarVerifier> {

        @Override
        protected CalendarVerifier createCustomVerifier() {
            return new CalendarVerifier(getMockVerification());
        }

        @Override
        protected Calendar createValueForCalendar(Calendar calendar) {
            return calendar;
        }
    }
}
