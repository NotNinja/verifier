/*
 * Copyright (C) 2017 Alasdair Mercer, !ninja
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
package org.notninja.verifier.verification;

import static org.junit.Assert.*;
import static org.mockito.AdditionalMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.eq;

import java.util.Locale;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import org.notninja.verifier.VerifierException;
import org.notninja.verifier.message.MessageKey;
import org.notninja.verifier.message.MessageSource;
import org.notninja.verifier.message.formatter.Formatter;
import org.notninja.verifier.message.formatter.FormatterProvider;
import org.notninja.verifier.message.locale.LocaleContext;
import org.notninja.verifier.util.TestUtils;
import org.notninja.verifier.verification.report.MessageHolder;
import org.notninja.verifier.verification.report.ReportExecutor;

/**
 * <p>
 * Tests for the {@link SimpleVerification} class.
 * </p>
 *
 * @author Alasdair Mercer
 */
@RunWith(MockitoJUnitRunner.class)
public class SimpleVerificationTest {

    private static final Object TEST_NAME_1 = "foo";
    private static final Object TEST_NAME_2 = "fu";
    private static final Object TEST_VALUE_1 = 123;
    private static final Object TEST_VALUE_2 = 456;

    @Captor
    private ArgumentCaptor<MessageHolder> messageHolderCaptor;
    @Mock
    private Formatter mockFormatter;
    @Mock
    private FormatterProvider mockFormatterProvider;
    @Mock
    private LocaleContext mockLocaleContext;
    @Mock
    private MessageSource mockMessageSource;
    @Mock
    private ReportExecutor mockReportExecutor;

    private SimpleVerification<?> verification;

    @Before
    public void setUp() {
        verification = new SimpleVerification<>(mockLocaleContext, mockMessageSource, mockFormatterProvider, mockReportExecutor, TEST_VALUE_1, TEST_NAME_1);
    }

    @Test
    public void testCopy() throws Exception {
        verification.setNegated(true);

        Verification<?> copy = verification.copy(TEST_VALUE_2, TEST_NAME_2);

        assertNotNull("Never returns null", copy);
        assertTrue("Returns instance of SimpleVerification", copy instanceof SimpleVerification);
        assertFalse("Copy is not negated", copy.isNegated());
        assertSame("Reuses FormatterProvider", TestUtils.getInstanceField(verification, "formatterProvider", true), TestUtils.getInstanceField(copy, "formatterProvider", true));
        assertSame("Reuses LocaleContext", TestUtils.getInstanceField(verification, "localeContext", true), TestUtils.getInstanceField(copy, "localeContext", true));
        assertSame("Reuses MessageSource", TestUtils.getInstanceField(verification, "messageSource", true), TestUtils.getInstanceField(copy, "messageSource", true));
        assertSame("Reuses ReportExecutor", TestUtils.getInstanceField(verification, "reportExecutor", true), TestUtils.getInstanceField(copy, "reportExecutor", true));
        assertEquals("Passed name", TEST_NAME_2, copy.getName());
        assertEquals("Passed value", TEST_VALUE_2, copy.getValue());
    }

    @Test
    public void testGetFormatter() {
        Object obj = 123;

        when(mockFormatterProvider.getFormatter(obj)).thenReturn(mockFormatter);

        assertEquals("Delegates to FormatterProvider", mockFormatter, verification.getFormatter(obj));
    }

    @Test
    public void testGetMessageWithMessage() {
        String expected = "i am expected";
        String message = "test";
        Object[] args = new Object[]{"foo", "bar"};

        when(mockMessageSource.getMessage(verification, message, args)).thenReturn(expected);

        assertEquals("Delegates to MessageSource", expected, verification.getMessage(message, args));
    }

    @Test
    public void testGetMessageWithMessageKey() {
        String expected = "i am expected";
        MessageKey key = () -> "test";
        Object[] args = new Object[]{"foo", "bar"};

        when(mockMessageSource.getMessage(verification, key, args)).thenReturn(expected);

        assertEquals("Delegates to MessageSource", expected, verification.getMessage(key, args));
    }

    @Test
    public void testReportWithMessage() {
        verification.setNegated(true);

        verification.report(false, "test", "foo", "bar");

        assertFalse("Negated state is reset", verification.isNegated());

        verify(mockReportExecutor).execute(eq(verification), eq(false), messageHolderCaptor.capture());

        MessageHolder messageHolder = messageHolderCaptor.getValue();

        assertNotNull("Message holder is never null", messageHolder);

        messageHolder.getMessage(verification);

        verify(mockMessageSource).getMessage(eq(verification), eq("test"), aryEq(new Object[]{"foo", "bar"}));
    }

    @Test(expected = VerifierException.class)
    public void testReportWithMessageWhenReportExecutorThrows() {
        doThrow(new VerifierException()).when(mockReportExecutor).execute(eq(verification), eq(true), isA(MessageHolder.class));

        verification.setNegated(true);

        try {
            verification.report(true, "test", "foo", "bar");
        } finally {
            assertFalse("Negated state is reset", verification.isNegated());
        }
    }

    @Test
    public void testReportWithMessageKey() {
        MessageKey key = () -> "test";

        verification.setNegated(true);

        verification.report(false, key, "foo", "bar");

        assertFalse("Negated state is reset", verification.isNegated());

        verify(mockReportExecutor).execute(eq(verification), eq(false), messageHolderCaptor.capture());

        MessageHolder messageHolder = messageHolderCaptor.getValue();

        assertNotNull("Message holder is never null", messageHolder);

        messageHolder.getMessage(verification);

        verify(mockMessageSource).getMessage(eq(verification), eq(key), aryEq(new Object[]{"foo", "bar"}));
    }

    @Test(expected = VerifierException.class)
    public void testReportWithMessageKeyWhenReportExecutorThrows() {
        doThrow(new VerifierException()).when(mockReportExecutor).execute(eq(verification), eq(true), isA(MessageHolder.class));

        verification.setNegated(true);

        try {
            verification.report(true, () -> "test", "foo", "bar");
        } finally {
            assertFalse("Negated state is reset", verification.isNegated());
        }
    }

    @Test
    public void testGetLocale() {
        Locale locale = Locale.FRENCH;

        when(mockLocaleContext.getLocale()).thenReturn(locale);

        assertEquals("Delegates to LocaleContext", locale, verification.getLocale());
    }

    @Test
    public void testName() {
        assertEquals("Name property is readable", TEST_NAME_1, verification.getName());
    }

    @Test
    public void testNegated() {
        assertFalse("Negated property is readable and is false by default", verification.isNegated());

        verification.setNegated(true);

        assertTrue("Negated property is writable", verification.isNegated());
    }

    @Test
    public void testValue() {
        assertEquals("Value property is readable", TEST_VALUE_1, verification.getValue());
    }
}
