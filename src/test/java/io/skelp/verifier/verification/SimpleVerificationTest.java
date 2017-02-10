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
import static org.mockito.AdditionalMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.eq;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import io.skelp.verifier.VerifierException;
import io.skelp.verifier.message.MessageKey;
import io.skelp.verifier.message.MessageSource;
import io.skelp.verifier.message.locale.LocaleContext;
import io.skelp.verifier.verification.report.MessageHolder;
import io.skelp.verifier.verification.report.ReportExecutor;

/**
 * <p>
 * Tests for the {@link SimpleVerification} class.
 * </p>
 *
 * @author Alasdair Mercer
 */
@RunWith(MockitoJUnitRunner.class)
public class SimpleVerificationTest {

    private static final Object TEST_NAME = "foo";
    private static final Object TEST_VALUE = 123;

    @Captor
    private ArgumentCaptor<MessageHolder> messageHolderCaptor;
    @Mock
    private LocaleContext mockLocaleContext;
    @Mock
    private MessageSource mockMessageSource;
    @Mock
    private ReportExecutor mockReportExecutor;

    private SimpleVerification<?> verification;

    @Before
    public void setUp() {
        verification = new SimpleVerification<>(mockLocaleContext, mockMessageSource, mockReportExecutor, TEST_VALUE, TEST_NAME);
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
    public void testLocaleContext() {
        assertEquals("LocaleContext property is readable", mockLocaleContext, verification.getLocaleContext());
    }

    @Test
    public void testMessageSource() {
        assertEquals("MessageSource property is readable", mockMessageSource, verification.getMessageSource());
    }

    @Test
    public void testReportExecutor() {
        assertEquals("ReportExecutor property is readable", mockReportExecutor, verification.getReportExecutor());
    }

    @Test
    public void testName() {
        assertEquals("Name property is readable", TEST_NAME, verification.getName());
    }

    @Test
    public void testNegated() {
        assertFalse("Negated property is readable and is false by default", verification.isNegated());

        verification.setNegated(true);

        assertTrue("Negated property is writable", verification.isNegated());
    }

    @Test
    public void testValue() {
        assertEquals("Value property is readable", TEST_VALUE, verification.getValue());
    }
}
