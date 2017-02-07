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
package io.skelp.verifier.verification;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import io.skelp.verifier.VerifierException;
import io.skelp.verifier.message.MessageKey;
import io.skelp.verifier.message.MessageSource;
import io.skelp.verifier.message.locale.LocaleContext;

/**
 * <p>
 * Tests for the {@link SimpleVerification} class.
 * </p>
 *
 * @author Alasdair Mercer
 */
@RunWith(MockitoJUnitRunner.class)
public class SimpleVerificationTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private LocaleContext mockLocaleContext;
    @Mock
    private MessageSource mockMessageSource;

    @Test
    public void testCheckWithMessageWhenResultIsBad() {
        thrown.expect(VerifierException.class);
        thrown.expectMessage("i am expected");

        SimpleVerification<?> verification = new SimpleVerification<>(mockLocaleContext, mockMessageSource, null, null);

        when(mockMessageSource.getMessage(same(verification), eq("test"), Matchers.anyVararg())).thenReturn("i am expected");

        try {
            verification.check(false, "test", "foo", "bar");
            fail("VerifierException expected");
        } catch (VerifierException e) {
            assertFalse("Still not negated", verification.isNegated());
            throw e;
        }
    }

    @Test
    public void testCheckWithMessageWhenResultIsBadAndIsNegated() {
        SimpleVerification<?> verification = new SimpleVerification<>(mockLocaleContext, mockMessageSource, null, null);
        verification.setNegated(true);

        assertSame("Chains reference", verification, verification.check(false, "test", "foo", "bar"));
        assertFalse("No longer negated", verification.isNegated());
    }

    @Test
    public void testCheckWithMessageWhenResultIsGood() {
        SimpleVerification<?> verification = new SimpleVerification<>(mockLocaleContext, mockMessageSource, null, null);

        assertSame("Chains reference", verification, verification.check(true, "test", "foo", "bar"));
        assertFalse("Still not negated", verification.isNegated());
    }

    @Test
    public void testCheckWithMessageWhenResultIsGoodAndIsNegated() {
        thrown.expect(VerifierException.class);
        thrown.expectMessage("i am expected");

        SimpleVerification<?> verification = new SimpleVerification<>(mockLocaleContext, mockMessageSource, null, null);
        verification.setNegated(true);

        when(mockMessageSource.getMessage(same(verification), eq("test"), Matchers.anyVararg())).thenReturn("i am expected");

        try {
            verification.check(true, "test", "foo", "bar");
            fail("VerifierException expected");
        } catch (VerifierException e) {
            assertFalse("No longer negated", verification.isNegated());
            throw e;
        }
    }

    @Test
    public void testCheckWithMessageKeyWhenResultIsBad() {
        thrown.expect(VerifierException.class);
        thrown.expectMessage("i am expected");

        MessageKey key = () -> "test";
        SimpleVerification<?> verification = new SimpleVerification<>(mockLocaleContext, mockMessageSource, null, null);

        when(mockMessageSource.getMessage(same(verification), same(key), Matchers.anyVararg())).thenReturn("i am expected");

        try {
            verification.check(false, key, "foo", "bar");
            fail("VerifierException expected");
        } catch (VerifierException e) {
            assertFalse("Still not negated", verification.isNegated());
            throw e;
        }
    }

    @Test
    public void testCheckWithMessageKeyWhenResultIsBadAndIsNegated() {
        SimpleVerification<?> verification = new SimpleVerification<>(mockLocaleContext, mockMessageSource, null, null);
        verification.setNegated(true);

        assertSame("Chains reference", verification, verification.check(false, () -> "test", "foo", "bar"));
        assertFalse("No longer negated", verification.isNegated());
    }

    @Test
    public void testCheckWithMessageKeyWhenResultIsGood() {
        SimpleVerification<?> verification = new SimpleVerification<>(mockLocaleContext, mockMessageSource, null, null);

        assertSame("Chains reference", verification, verification.check(true, () -> "test", "foo", "bar"));
        assertFalse("Still not negated", verification.isNegated());
    }

    @Test
    public void testCheckWithMessageKeyWhenResultIsGoodAndIsNegated() {
        thrown.expect(VerifierException.class);
        thrown.expectMessage("i am expected");

        MessageKey key = () -> "test";
        SimpleVerification<?> verification = new SimpleVerification<>(mockLocaleContext, mockMessageSource, null, null);
        verification.setNegated(true);

        when(mockMessageSource.getMessage(same(verification), same(key), Matchers.anyVararg())).thenReturn("i am expected");

        try {
            verification.check(true, key, "foo", "bar");
            fail("VerifierException expected");
        } catch (VerifierException e) {
            assertFalse("No longer negated", verification.isNegated());
            throw e;
        }
    }

    @Test
    public void testLocaleContext() {
        SimpleVerification<?> verification = new SimpleVerification<>(mockLocaleContext, null, null, null);

        assertEquals("LocaleContext property is readable", mockLocaleContext, verification.getLocaleContext());
    }

    @Test
    public void testMessageSource() {
        SimpleVerification<?> verification = new SimpleVerification<>(null, mockMessageSource, null, null);

        assertEquals("MessageSource property is readable", mockMessageSource, verification.getMessageSource());
    }

    @Test
    public void testName() {
        SimpleVerification<?> verification = new SimpleVerification<>(null, null, null, "foo");

        assertEquals("Name property is readable", "foo", verification.getName());
    }

    @Test
    public void testNegated() {
        SimpleVerification<?> verification = new SimpleVerification<>(null, null, null, null);

        assertFalse("Negated property is readable and is false by default", verification.isNegated());

        verification.setNegated(true);

        assertTrue("Negated property is writable", verification.isNegated());
    }

    @Test
    public void testValue() {
        SimpleVerification<?> verification = new SimpleVerification<>(null, null, 123, null);

        assertEquals("Value property is readable", 123, verification.getValue());
    }
}
