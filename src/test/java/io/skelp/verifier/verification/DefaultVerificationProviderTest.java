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
package io.skelp.verifier.verification;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import io.skelp.verifier.message.MessageSource;
import io.skelp.verifier.message.MessageSourceProvider;
import io.skelp.verifier.message.TestMessageSourceProvider;
import io.skelp.verifier.message.formatter.TestFormatterProvider;
import io.skelp.verifier.message.locale.LocaleContext;
import io.skelp.verifier.message.locale.LocaleContextProvider;
import io.skelp.verifier.message.locale.TestLocaleContextProvider;
import io.skelp.verifier.service.Weighted;
import io.skelp.verifier.util.TestUtils;
import io.skelp.verifier.verification.report.ReportExecutor;
import io.skelp.verifier.verification.report.ReportExecutorProvider;
import io.skelp.verifier.verification.report.TestReportExecutorProvider;

/**
 * <p>
 * Tests for the {@link DefaultVerificationProvider} class.
 * </p>
 *
 * @author Alasdair Mercer
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultVerificationProviderTest {

    @Mock
    private LocaleContext mockLocaleContext;
    @Mock
    private LocaleContextProvider mockLocaleContextProvider;
    @Mock
    private MessageSource mockMessageSource;
    @Mock
    private MessageSourceProvider mockMessageSourceProvider;
    @Mock
    private ReportExecutor mockReportExecutor;
    @Mock
    private ReportExecutorProvider mockReportExecutorProvider;

    private DefaultVerificationProvider provider;

    @Before
    public void setUp() {
        when(mockLocaleContextProvider.getLocaleContext()).thenReturn(mockLocaleContext);
        when(mockMessageSourceProvider.getMessageSource()).thenReturn(mockMessageSource);
        when(mockReportExecutorProvider.getReportExecutor()).thenReturn(mockReportExecutor);

        TestLocaleContextProvider.setDelegate(mockLocaleContextProvider);
        TestMessageSourceProvider.setDelegate(mockMessageSourceProvider);
        TestReportExecutorProvider.setDelegate(mockReportExecutorProvider);

        provider = new DefaultVerificationProvider();
    }

    @After
    public void tearDown() {
        TestLocaleContextProvider.setDelegate(null);
        TestMessageSourceProvider.setDelegate(null);
        TestReportExecutorProvider.setDelegate(null);
    }

    @Test
    public void testGetVerification() throws Exception {
        Verification<Integer> verification = provider.getVerification(123, "foo");

        assertNotNull("Never returns null", verification);
        assertTrue("Returns instance of SimpleVerification", verification instanceof SimpleVerification);
        assertTrue("Passed FormatterProvider", TestUtils.getInstanceField(verification, "formatterProvider", true) instanceof TestFormatterProvider);
        assertSame("Passed LocaleContext from provider", mockLocaleContext, TestUtils.getInstanceField(verification, "localeContext", true));
        assertSame("Passed MessageSource from provider", mockMessageSource, TestUtils.getInstanceField(verification, "messageSource", true));
        assertSame("Passed ReportExecutor from provider", mockReportExecutor, TestUtils.getInstanceField(verification, "reportExecutor", true));
        assertEquals("Passed name", "foo", verification.getName());
        assertEquals("Passed value", Integer.valueOf(123), verification.getValue());
    }

    @Test
    public void testGetWeight() {
        assertEquals("Has default implementation weight", Weighted.DEFAULT_IMPLEMENTATION_WEIGHT, provider.getWeight());
    }
}
