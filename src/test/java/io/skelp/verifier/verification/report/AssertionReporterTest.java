/*
 * Copyright (C) 2017 Alasdair Mercer, Skelp
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
package io.skelp.verifier.verification.report;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import io.skelp.verifier.VerifierException;
import io.skelp.verifier.service.Weighted;
import io.skelp.verifier.verification.Verification;

/**
 * <p>
 * Tests for the {@link AssertionReporter} class.
 * </p>
 *
 * @author Alasdair Mercer
 */
@RunWith(MockitoJUnitRunner.class)
public class AssertionReporterTest {

    private static final String TEST_MESSAGE = "i am expected";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private MessageHolder mockMessageHolder;
    @Mock
    private Verification<?> mockVerification;

    private AssertionReporter reporter;

    @Before
    public void setUp() {
        when(mockMessageHolder.getMessage(mockVerification)).thenReturn(TEST_MESSAGE);

        reporter = new AssertionReporter();
    }

    @Test
    public void testReportWhenResultIsFalse() {
        testReportHelper(false, false, true);
    }

    @Test
    public void testReportWhenResultIsFalseAndVerificationIsNegated() {
        testReportHelper(true, false, false);
    }

    @Test
    public void testReportWhenResultIsTrue() {
        testReportHelper(false, true, false);
    }

    @Test
    public void testReportWhenResultIsTrueAndVerificationIsNegated() {
        testReportHelper(true, true, true);
    }

    private void testReportHelper(boolean negated, boolean result, boolean exceptionExpected) {
        if (exceptionExpected) {
            thrown.expect(VerifierException.class);
            thrown.expectMessage(TEST_MESSAGE);
        }

        when(mockVerification.isNegated()).thenReturn(negated);

        assertTrue("Never returns false", reporter.report(mockVerification, result, mockMessageHolder));
    }

    @Test
    public void testGetWeight() {
        assertEquals("Has default implementation weight", Weighted.DEFAULT_IMPLEMENTATION_WEIGHT, reporter.getWeight());
    }
}
