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

import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.skelp.verifier.VerifierException;
import io.skelp.verifier.verification.Verification;

/**
 * <p>
 * Test case for {@link AbstractReportExecutor} implementation classes.
 * </p>
 *
 * @param <T>
 *         the type of the {@link AbstractReportExecutor} being tested
 * @author Alasdair Mercer
 */
@RunWith(Theories.class)
public abstract class AbstractReportExecutorTestCase<T extends AbstractReportExecutor> {

    @DataPoints
    public static final boolean[] DATA_POINTS = {true, false};

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private MessageHolder mockMessageHolder;
    @Mock
    private Reporter mockReporter1;
    @Mock
    private Reporter mockReporter2;
    @Mock
    private Reporter mockReporter3;
    @Mock
    private Verification<?> mockVerification;

    private List<Reporter> reporters;

    private T reportExecutor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        reporters = Arrays.asList(mockReporter1, mockReporter2, mockReporter3);

        reportExecutor = createReportExecutor(reporters);
    }

    @Theory
    public void testExecute(boolean result) {
        when(mockReporter1.report(mockVerification, result, mockMessageHolder)).thenReturn(true);
        when(mockReporter2.report(mockVerification, result, mockMessageHolder)).thenReturn(true);
        when(mockReporter3.report(mockVerification, result, mockMessageHolder)).thenReturn(true);

        reportExecutor.execute(mockVerification, result, mockMessageHolder);

        verify(mockReporter1).report(mockVerification, result, mockMessageHolder);
        verify(mockReporter2).report(mockVerification, result, mockMessageHolder);
        verify(mockReporter3).report(mockVerification, result, mockMessageHolder);
    }

    @Theory
    public void testExecuteWhenReporterBlocksNext(boolean result) {
        when(mockReporter1.report(mockVerification, result, mockMessageHolder)).thenReturn(true);
        when(mockReporter2.report(mockVerification, result, mockMessageHolder)).thenReturn(false);
        when(mockReporter3.report(mockVerification, result, mockMessageHolder)).thenReturn(true);

        reportExecutor.execute(mockVerification, result, mockMessageHolder);

        verify(mockReporter1).report(mockVerification, result, mockMessageHolder);
        verify(mockReporter2).report(mockVerification, result, mockMessageHolder);
        verify(mockReporter3, never()).report(mockVerification, result, mockMessageHolder);
    }

    @Theory
    public void testExecuteWhenReporterThrows(boolean result) {
        thrown.expect(VerifierException.class);

        when(mockReporter1.report(mockVerification, result, mockMessageHolder)).thenReturn(true);
        when(mockReporter2.report(mockVerification, result, mockMessageHolder)).thenThrow(new VerifierException());
        when(mockReporter3.report(mockVerification, result, mockMessageHolder)).thenReturn(true);

        try {
            reportExecutor.execute(mockVerification, result, mockMessageHolder);
        } finally {
            verify(mockReporter1).report(mockVerification, result, mockMessageHolder);
            verify(mockReporter2).report(mockVerification, result, mockMessageHolder);
            verify(mockReporter3, never()).report(mockVerification, result, mockMessageHolder);
        }
    }

    @Test
    public void testGetReporters() {
        assertEquals("Has correct list of reporters", reporters, reportExecutor.getReporters());
    }

    /**
     * <p>
     * Creates an instance of the reporter executor test subject to be tested using the {@code reporters} provided.
     * </p>
     *
     * @param reporters
     *         the {@link Reporter Reporters} to be used
     * @return The {@link AbstractReportExecutor} to be tested.F
     */
    protected abstract T createReportExecutor(List<Reporter> reporters);

    /**
     * <p>
     * Returns the reporters being used to test the subject.
     * </p>
     *
     * @return The {@code List} of {@link Reporter Reporters}.
     */
    protected List<Reporter> getReporters() {
        return reporters;
    }
}
