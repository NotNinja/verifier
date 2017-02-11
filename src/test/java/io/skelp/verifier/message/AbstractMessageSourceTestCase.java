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
package io.skelp.verifier.message;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import io.skelp.verifier.message.formatter.ClassFormatter;
import io.skelp.verifier.message.formatter.CollectionFormatter;
import io.skelp.verifier.message.formatter.MapFormatter;
import io.skelp.verifier.util.TestUtils;
import io.skelp.verifier.verification.Verification;

/**
 * <p>
 * Test case for {@link AbstractMessageSource} implementation classes.
 * </p>
 *
 * @param <T>
 *         the type of the {@link AbstractMessageSource} being tested
 * @author Alasdair Mercer
 */
@RunWith(MockitoJUnitRunner.class)
public abstract class AbstractMessageSourceTestCase<T extends AbstractMessageSource> {

    @Mock
    private Verification<?> mockVerification;

    private T messageSource;

    @Before
    public void setUp() {
        messageSource = createMessageSource();

        when(mockVerification.getFormatter(isA(Class.class))).thenReturn(new ClassFormatter());
        when(mockVerification.getFormatter(isA(Collection.class))).thenReturn(new CollectionFormatter());
        when(mockVerification.getFormatter(isA(Object[].class))).thenReturn(new CollectionFormatter());
        when(mockVerification.getFormatter(isA(Map.class))).thenReturn(new MapFormatter());
        when(mockVerification.getLocale()).thenReturn(getLocale());
    }

    @After
    public void tearDown() throws Exception {
        TestUtils.setStaticField(AbstractMessageSource.class, "EMPTY_ARRAY", new Object[0], true);
    }

    @Test
    public void hackCoverage() throws Exception {
        // TODO: Determine how to avoid this
        TestUtils.setField(AbstractMessageSource.class, messageSource, "messageFormatsPerMessage", Collections.unmodifiableMap(getMessageFormatsPerMessage()), true);

        try {
            messageSource.clearCache();
            fail("Should have thrown since map is unmodifiable");
        } catch (UnsupportedOperationException e) {
            // Do nothing
        }

        TestUtils.setStaticField(AbstractMessageSource.class, "EMPTY_ARRAY", new Object[]{"bar"}, true);

        AbstractMessageSource messageSource = new AbstractMessageSource() {
            @Override
            protected String buildMessage(String message, Object name, Object value, Verification<?> verification) {
                return null;
            }

            @Override
            protected String getDefaultMessage(Verification<?> verification) {
                return null;
            }

            @Override
            protected Object getDefaultName(Verification<?> verification) {
                return null;
            }

            @Override
            protected MessageFormat resolveKey(MessageKey key, Verification<?> verification) {
                return new MessageFormat("foo {0,date,short}", getLocale());
            }
        };

        try {
            messageSource.resolveKeyWithoutArguments(() -> "foo", mockVerification);
            fail("Should have thrown since EMPTY_ARRAY contains string that cannot be formatted to a date");
        } catch (IllegalArgumentException e) {
            // Do nothing
        }
    }

    @Test
    public void testAlwaysUseMessageFormat() {
        assertFalse("AlwaysUseMessageFormat property is readable and is false by default", messageSource.isAlwaysUseMessageFormat());

        messageSource.setAlwaysUseMessageFormat(true);

        assertTrue("AlwaysUseMessageFormat property is writable", messageSource.isAlwaysUseMessageFormat());
    }

    @Test
    public void testBuildMessage() {
        testBuildMessageHelper(false);
    }

    @Test
    public void testBuildMessageWhenNegated() {
        testBuildMessageHelper(true);
    }

    private void testBuildMessageHelper(boolean negated) {
        String message = "be so awesome";
        String name = "foo";
        String value = "bar";

        when(mockVerification.isNegated()).thenReturn(negated);

        assertEquals("Returns expected built message", getBuiltMessage(message, name, value, negated), messageSource.buildMessage(message, name, value, mockVerification));
    }

    @Test
    public void testClearCache() throws Exception {
        String message = "foo {0}";

        assertMessageFormatCacheIsEmpty();

        messageSource.formatMessage(message, new Object[]{"bar"}, mockVerification);

        assertMessageFormatCacheContains(message);

        messageSource.clearCache();

        assertMessageFormatCacheIsEmpty();
    }

    @Test
    public void testCreateMessageFormat() {
        String message = "foo {0}";
        MessageFormat messageFormat = messageSource.createMessageFormat(message, mockVerification);

        assertNotNull("Never returns null", messageFormat);
        assertEquals("Uses message as format pattern", message, messageFormat.toPattern());
        assertEquals("Uses contextual locale", getLocale(), messageFormat.getLocale());
    }

    @Test
    public void testCreateMessageFormatWhenMessageIsNull() {
        MessageFormat messageFormat = messageSource.createMessageFormat(null, mockVerification);

        assertNotNull("Never returns null", messageFormat);
        assertEquals("Uses empty format pattern", "", messageFormat.toPattern());
        assertEquals("Uses contextual locale", getLocale(), messageFormat.getLocale());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateMessageFormatThrowsWhenMessageIsInvalidPattern() {
        messageSource.createMessageFormat("foo {{0}", mockVerification);
    }

    @Test
    public void testFormatMessage() throws Exception {
        testFormatMessageHelper("foo {0}: {1,number,integer}", new Object[]{"bar", 123}, false, true, "foo bar: 123");
    }

    @Test
    public void testFormatMessageWhenArgsIsEmpty() throws Exception {
        testFormatMessageHelper("foo {0}", new Object[0], false, false, "foo {0}");
    }

    @Test
    public void testFormatMessageWhenArgsIsNull() throws Exception {
        testFormatMessageHelper("foo {0}", null, false, false, "foo {0}");
    }

    @Test
    public void testFormatMessageWhenArgsIsEmptyAndAlwaysUseMessageFormat() throws Exception {
        testFormatMessageHelper("foo {0}", new Object[0], true, true, "foo {0}");
    }

    @Test
    public void testFormatMessageWhenArgsIsNullAndAlwaysUseMessageFormat() throws Exception {
        testFormatMessageHelper("foo {0}", null, true, true, "foo {0}");
    }

    @Test
    public void testFormatMessageWhenMessageIsNull() throws Exception {
        testFormatMessageHelper(null, new Object[]{"foo"}, false, false, null);
    }

    @Test
    public void testFormatMessageWhenMessageIsInvalidPattern() throws Exception {
        String message = "foo {{0}";

        assertEquals("Returns reference to message", message, messageSource.formatMessage(message, new Object[]{"bar"}, mockVerification));

        assertMessageFormatCacheContainsInvalidFormatMessage(message);
    }

    @Test
    public void testFormatMessageWhenOtherMessagePreviouslyCachedForLocale() throws Exception {
        String expected1 = "foo bar: 123";
        String expected2 = "fu baz: 321";
        String message1 = "foo {0}: {1,number,integer}";
        String message2 = "fu {0}: {1,number,integer}";

        assertEquals("Returns formatted message", expected1, messageSource.formatMessage(message1, new Object[]{"bar", 123}, mockVerification));

        testFormatMessageHelper(message2, new Object[]{"baz", 321}, false, true, expected2);

        assertMessageFormatCacheContains(message2);
    }

    @Test
    public void testFormatMessageWhenPreviouslyCachedForLocale() throws Exception {
        String message = "foo {0}: {1,number,integer}";

        assertEquals("Returns formatted message", "foo bar: 123", messageSource.formatMessage(message, new Object[]{"bar", 123}, mockVerification));

        testFormatMessageHelper("foo {0}: {1,number,integer}", new Object[]{"baz", 321}, false, true, "foo baz: 321");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFormatMessageThrowsWhenArgsIsInvalid() {
        messageSource.formatMessage("foo {0,date,short}", new Object[]{"bar"}, mockVerification);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFormatMessageThrowsWhenMessageIsInvalidPatternAndAlwaysUseMessageFormat() {
        messageSource.setAlwaysUseMessageFormat(true);

        messageSource.formatMessage("foo {{0}", new Object[]{"bar"}, mockVerification);
    }

    private void testFormatMessageHelper(String message, Object[] args, boolean alwaysUseMessageFormat, boolean cached, String expected) throws Exception {
        messageSource.setAlwaysUseMessageFormat(alwaysUseMessageFormat);

        assertEquals("Returns correct message", expected, messageSource.formatMessage(message, args, mockVerification));

        if (cached) {
            assertMessageFormatCacheContains(message);
        } else {
            assertMessageFormatCacheIsEmpty();
        }
    }

    @Test
    public void testFormatMessageWithoutArguments() {
        String message = "foo";

        assertSame("Returns reference to message", message, messageSource.formatMessageWithoutArguments(message, mockVerification));
    }

    @Test
    public void testFormatMessageWithoutArgumentsWhenMessageIsNull() {
        assertNull("Returns null", messageSource.formatMessageWithoutArguments(null, mockVerification));
    }

    @Test
    public void testGetDefaultMessage() {
        testGetDefaultMessageHelper(false);
    }

    @Test
    public void testGetDefaultMessageWhenNegated() {
        testGetDefaultMessageHelper(true);
    }

    private void testGetDefaultMessageHelper(boolean negated) {
        when(mockVerification.isNegated()).thenReturn(negated);

        assertEquals("Returns expected default message", getDefaultMessage(negated), messageSource.getDefaultMessage(mockVerification));
    }

    @Test
    public void testGetDefaultMessageWithMessageKey() {
        testGetDefaultMessageWithMessageKeyHelper(() -> "foo", false, null);
    }

    @Test
    public void testGetDefaultMessageWithMessageKeyWhenKeyIsNull() {
        testGetDefaultMessageWithMessageKeyHelper(null, false, null);
    }

    @Test
    public void testGetDefaultMessageWithMessageKeyWhenUseKeyAsDefaultMessage() {
        testGetDefaultMessageWithMessageKeyHelper(() -> "foo", true, "foo");
    }

    @Test
    public void testGetDefaultMessageWithMessageKeyWhenUseKeyAsDefaultMessageAndKeyIsNull() {
        testGetDefaultMessageWithMessageKeyHelper(null, true, null);
    }

    private void testGetDefaultMessageWithMessageKeyHelper(MessageKey key, boolean useKeyAsDefaultMessage, String expected) {
        messageSource.setUseKeyAsDefaultMessage(useKeyAsDefaultMessage);

        assertEquals("Returns expected default message", expected, messageSource.getDefaultMessage(key, mockVerification));
    }

    @Test
    public void testGetDefaultName() {
        assertEquals("Returns expected default name", getDefaultName(), messageSource.getDefaultName(mockVerification));
    }

    @Test
    public void testGetMessageWithMessage() throws Exception {
        testGetMessageWithMessageHelper(getMessage(), new Object[]{"bar", new Object[]{123, 456, 789}}, false, true, getMessageFormatted(new Object[]{"bar", "['123', '456', '789']"}));
    }

    @Test
    public void testGetMessageWithMessageWhenMessageIsNull() throws Exception {
        testGetMessageWithMessageHelper(null, new Object[]{"foo"}, false, false, getDefaultMessage(false));
    }

    @Test
    public void testGetMessageWithMessageWhenMessageIsNullAndNegated() throws Exception {
        testGetMessageWithMessageHelper(null, new Object[]{"foo"}, true, false, getDefaultMessage(true));
    }

    private void testGetMessageWithMessageHelper(String message, Object[] args, boolean negated, boolean cached, String expected) throws Exception {
        String name = "fizz";
        String value = "buzz";
        String expectedFinalMessage = getBuiltMessage(expected, name, value, negated);

        when(mockVerification.getName()).thenReturn(name);
        when(mockVerification.isNegated()).thenReturn(negated);
        when(mockVerification.getValue()).thenAnswer((invocation) -> value);

        assertEquals("Returns correct message", expectedFinalMessage, messageSource.getMessage(mockVerification, message, args));

        if (cached) {
            assertMessageFormatCacheContains(message);
        } else {
            assertMessageFormatCacheIsEmpty();
        }
    }

    @Test
    public void testGetMessageWithMessageKey() throws Exception {
        testGetMessageWithMessageKeyHelper(getMessageKey(), new Object[]{"bar", new Object[]{123, 456, 789}}, false, true, getMessageFormatted(new Object[]{"bar", "['123', '456', '789']"}));
    }

    @Test
    public void testGetMessageWithMessageKeyWhenNegated() throws Exception {
        testGetMessageWithMessageKeyHelper(getMessageKey(), new Object[]{"bar", new Object[]{123, 456, 789}}, true, true, getMessageFormatted(new Object[]{"bar", "['123', '456', '789']"}));
    }

    @Test
    public void testGetMessageWithMessageKeyWhenMessageNotFoundAndUseKeyAsDefaultMessage() throws Exception {
        testGetMessageWithMessageKeyHelper(getMissingMessageKey(), new Object[]{"foo"}, true, false, getMissingMessageKey().code());
    }

    @Test(expected = NoSuchMessageException.class)
    public void testGetMessageWithMessageKeyThrowsWhenMessageNotFound() throws Exception {
        messageSource.getMessage(mockVerification, getMissingMessageKey(), null);
    }

    private void testGetMessageWithMessageKeyHelper(MessageKey key, Object[] args, boolean useKeyAsDefaultMessage, boolean negated, String expected) {
        String name = "fizz";
        String value = "buzz";
        String expectedFinalMessage = getBuiltMessage(expected, name, value, negated);

        when(mockVerification.getName()).thenReturn(name);
        when(mockVerification.isNegated()).thenReturn(negated);
        when(mockVerification.getValue()).thenAnswer((invocation) -> value);

        messageSource.setUseKeyAsDefaultMessage(useKeyAsDefaultMessage);

        assertEquals("Returns correct message", expectedFinalMessage, messageSource.getMessage(mockVerification, key, args));
    }

    @Test
    public void testGetMessageInternalWithMessage() throws Exception {
        testGetMessageInternalWithMessageHelper(getMessage(), new Object[]{"bar", new Object[]{123, 456, 789}}, false, true, getMessageFormatted(new Object[]{"bar", "['123', '456', '789']"}));
    }

    @Test
    public void testGetMessageInternalWithMessageWhenNegated() throws Exception {
        testGetMessageInternalWithMessageHelper(getMessage(), new Object[]{"bar", new Object[]{123, 456, 789}}, true, true, getMessageFormatted(new Object[]{"bar", "['123', '456', '789']"}));
    }

    @Test
    public void testGetMessageInternalWithMessageWhenArgsIsEmpty() throws Exception {
        testGetMessageInternalWithMessageHelper(getMessage(), new Object[0], false, false, getMessage());
    }

    @Test
    public void testGetMessageInternalWithMessageWhenArgsIsNull() throws Exception {
        testGetMessageInternalWithMessageHelper(getMessage(), null, false, false, getMessage());
    }

    @Test
    public void testGetMessageInternalWithMessageWhenMessageIsNull() throws Exception {
        testGetMessageInternalWithMessageHelper(null, new Object[]{"foo"}, false, false, null);
    }

    @Test
    public void testGetMessageInternalWithMessageWhenArgsIsEmptyAndAlwaysUseMessageFormat() throws Exception {
        testGetMessageInternalWithMessageHelper(getMessage(), new Object[0], true, true, getMessage());
    }

    @Test
    public void testGetMessageInternalWithMessageWhenArgsIsNullAndAlwaysUseMessageFormat() throws Exception {
        testGetMessageInternalWithMessageHelper(getMessage(), null, true, true, getMessage());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetMessageInternalWithMessageThrowsWhenArgsIsInvalid() {
        messageSource.getMessageInternal("foo {0,date,short}", new Object[]{"bar"}, mockVerification);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetMessageInternalWithMessageThrowsWhenMessageIsInvalidPatternAndAlwaysUseMessageFormat() {
        messageSource.setAlwaysUseMessageFormat(true);

        messageSource.getMessageInternal(getMessageWithInvalidPattern(), new Object[]{"bar"}, mockVerification);
    }

    private void testGetMessageInternalWithMessageHelper(String message, Object[] args, boolean alwaysUseMessageFormat, boolean cached, String expected) throws Exception {
        messageSource.setAlwaysUseMessageFormat(alwaysUseMessageFormat);

        assertEquals("Returns correct message", expected, messageSource.getMessageInternal(message, args, mockVerification));

        if (cached) {
            assertMessageFormatCacheContains(message);
        } else {
            assertMessageFormatCacheIsEmpty();
        }
    }

    @Test
    public void testGetMessageInternalWithMessageKey() throws Exception {
        testGetMessageInternalWithMessageKeyHelper(getMessageKey(), new Object[]{"bar", new Object[]{123, 456, 789}}, false, getMessageFormatted(new Object[]{"bar", "['123', '456', '789']"}));
    }

    @Test
    public void testGetMessageInternalWithMessageKeyWhenArgsIsEmpty() throws Exception {
        testGetMessageInternalWithMessageKeyHelper(getMessageKey(), new Object[0], false, getMessage());
    }

    @Test
    public void testGetMessageInternalWithMessageKeyWhenArgsIsNull() throws Exception {
        testGetMessageInternalWithMessageKeyHelper(getMessageKey(), null, false, getMessage());
    }

    @Test
    public void testGetMessageInternalWithMessageKeyWhenMessageIsNull() throws Exception {
        testGetMessageInternalWithMessageKeyHelper(null, new Object[]{"foo"}, false, null);
    }

    @Test
    public void testGetMessageInternalWithMessageKeyWhenArgsIsEmptyAndAlwaysUseMessageFormat() throws Exception {
        testGetMessageInternalWithMessageKeyHelper(getMessageKey(), new Object[0], true, getMessage());
    }

    @Test
    public void testGetMessageInternalWithMessageKeyWhenArgsIsNullAndAlwaysUseMessageFormat() throws Exception {
        testGetMessageInternalWithMessageKeyHelper(getMessageKey(), null, true, getMessage());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetMessageInternalWithMessageKeyThrowsWhenArgsIsInvalid() {
        AbstractMessageSource messageSource = new AbstractMessageSource() {
            @Override
            protected String buildMessage(String message, Object name, Object value, Verification<?> verification) {
                return null;
            }

            @Override
            protected String getDefaultMessage(Verification<?> verification) {
                return null;
            }

            @Override
            protected Object getDefaultName(Verification<?> verification) {
                return null;
            }

            @Override
            protected MessageFormat resolveKey(MessageKey key, Verification<?> verification) {
                return new MessageFormat("foo {0,date,short}", getLocale());
            }
        };

        messageSource.getMessageInternal(() -> "foo", new Object[]{"bar"}, mockVerification);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetMessageInternalWithMessageKeyThrowsWhenResolvedMessageIsInvalidPatternAndAlwaysUseMessageFormat() {
        messageSource.setAlwaysUseMessageFormat(true);

        messageSource.getMessageInternal(getMessageKeyWithInvalidPattern(), new Object[]{"bar"}, mockVerification);
    }

    private void testGetMessageInternalWithMessageKeyHelper(MessageKey key, Object[] args, boolean alwaysUseMessageFormat, String expected) {
        messageSource.setAlwaysUseMessageFormat(alwaysUseMessageFormat);

        assertEquals("Returns correct message", expected, messageSource.getMessageInternal(key, args, mockVerification));
    }

    @Test
    public void testResolveArguments() {
        Object[] result = messageSource.resolveArguments(new Object[]{null, "foo", new Object[]{123, 456, 789}}, mockVerification);

        assertNotNull("Never returns null", result);
        assertArrayEquals("Resolves arguments correctly where possible", new Object[]{null, "foo", "['123', '456', '789']"}, result);
    }

    @Test
    public void testResolveArgumentsWhenArgsIsNull() {
        Object[] result = messageSource.resolveArguments(null, mockVerification);

        assertNotNull("Never returns null", result);
        assertEquals("Returns empty array", 0, result.length);
    }

    @Test
    public void testResolveKey() throws Exception {
        MessageFormat messageFormat = messageSource.resolveKey(getMessageKey(), mockVerification);

        assertEquals("Returns correct message format for resolved message", new MessageFormat(getMessage(), getLocale()), messageFormat);
        assertNotSame("Message format was not invalid", AbstractMessageSource.INVALID_MESSAGE_FORMAT, messageFormat);
    }

    @Test
    public void testResolveKeyWhenMessageNotFound() throws Exception {
        assertNull("Returns null when message is not found", messageSource.resolveKey(getMissingMessageKey(), mockVerification));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testResolveKeyThrowsWhenMessageIsInvalidPatternAndAlwaysUseMessageFormat() throws Exception {
        messageSource.resolveKey(getMessageKeyWithInvalidPattern(), mockVerification);
    }

    @Test
    public void testResolveKeyWithoutArgumentsDefaultImplementation() {
        AbstractMessageSource messageSource = new AbstractMessageSource() {
            @Override
            protected String buildMessage(String message, Object name, Object value, Verification<?> verification) {
                return null;
            }

            @Override
            protected String getDefaultMessage(Verification<?> verification) {
                return null;
            }

            @Override
            protected Object getDefaultName(Verification<?> verification) {
                return null;
            }

            @Override
            protected MessageFormat resolveKey(MessageKey key, Verification<?> verification) {
                return new MessageFormat("bar {0}");
            }
        };

        assertEquals("Formats message using MessageFormat by default", "bar {0}", messageSource.resolveKeyWithoutArguments(() -> "foo", getMockVerification()));
    }

    @Test
    public void testResolveKeyWithoutArgumentsDefaultImplementationWhenNoMessageFound() {
        AbstractMessageSource messageSource = new AbstractMessageSource() {
            @Override
            protected String buildMessage(String message, Object name, Object value, Verification<?> verification) {
                return null;
            }

            @Override
            protected String getDefaultMessage(Verification<?> verification) {
                return null;
            }

            @Override
            protected Object getDefaultName(Verification<?> verification) {
                return null;
            }

            @Override
            protected MessageFormat resolveKey(MessageKey key, Verification<?> verification) {
                return null;
            }
        };

        assertNull("Returns null if message could not be resolved by default", messageSource.resolveKeyWithoutArguments(() -> "foo", getMockVerification()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testResolveKeyWithoutArgumentsDefaultImplementationThrowsWhenResolvedMessageIsInvalidPattern() {
        AbstractMessageSource messageSource = new AbstractMessageSource() {
            @Override
            protected String buildMessage(String message, Object name, Object value, Verification<?> verification) {
                return null;
            }

            @Override
            protected String getDefaultMessage(Verification<?> verification) {
                return null;
            }

            @Override
            protected Object getDefaultName(Verification<?> verification) {
                return null;
            }

            @Override
            protected MessageFormat resolveKey(MessageKey key, Verification<?> verification) {
                return new MessageFormat("bar {{0}");
            }
        };

        messageSource.resolveKeyWithoutArguments(() -> "foo", getMockVerification());
    }

    @Test
    public void testResolveName() {
        Object name = "foo";

        assertSame("Returns name as-is if no formatter is found", name, messageSource.resolveName(name, mockVerification));
    }

    @Test
    public void testResolveNameWhenNameHasFormatter() {
        assertEquals("Returns formatted name where possible", "['123', '456', '789']", messageSource.resolveName(new Object[]{123, 456, 789}, mockVerification));
    }

    @Test
    public void testResolveNameWhenNameIsNull() {
        assertEquals("Returns default name", getDefaultName(), messageSource.resolveName(null, mockVerification));
    }

    @Test
    public void testResolveValue() {
        Object value = "foo";

        assertSame("Returns value as-is if no formatter is found", value, messageSource.resolveValue(value, mockVerification));
    }

    @Test
    public void testResolveValueWhenValueHasFormatter() {
        assertEquals("Returns formatted value where possible", "['123', '456', '789']", messageSource.resolveValue(new Object[]{123, 456, 789}, mockVerification));
    }

    @Test
    public void testResolveValueWhenValueIsNull() {
        assertNull("Returns null for null value", messageSource.resolveValue(null, mockVerification));
    }

    @Test
    public void testTryFormat() {
        assertEquals("Returns formatted object where possible", "['123', '456', '789']", messageSource.tryFormat(new Object[]{123, 456, 789}, mockVerification));
    }

    @Test
    public void testTryFormatWhenNoFormatterIsFound() {
        Object obj = "foo";

        assertSame("Returns object as-is if no formatter is found", obj, messageSource.tryFormat(obj, mockVerification));
    }

    @Test
    public void testTryFormatWhenObjectIsNull() {
        assertNull("Returns null for null object", messageSource.tryFormat(null, mockVerification));
    }

    @Test
    public void testUseKeyAsDefaultMessage() {
        assertFalse("UseKeyAsDefaultMessage property is readable and is false by default", messageSource.isUseKeyAsDefaultMessage());

        messageSource.setUseKeyAsDefaultMessage(true);

        assertTrue("UseKeyAsDefaultMessage property is writable", messageSource.isUseKeyAsDefaultMessage());
    }

    /**
     * <p>
     * Creates an instance of the message source test subject to be tested.
     * </p>
     *
     * @return The {@link AbstractMessageSource} to be tested.
     */
    protected abstract T createMessageSource();

    /**
     * <p>
     * Returns the expected final message based on the information provided for testing the
     * {@link AbstractMessageSource#buildMessage(String, Object, Object, Verification)} method.
     * </p>
     *
     * @param message
     *         the resolved message
     * @param name
     *         the resolved name
     * @param value
     *         the resolved value
     * @param negated
     *         {@literal true} if the {@link Verification} has been negated; otherwise {@literal false}
     * @return The expected final message.
     */
    protected abstract String getBuiltMessage(String message, String name, String value, boolean negated);

    /**
     * <p>
     * Returns the expected default message based on the whether the verification has been {@code negated} for testing
     * the {@link AbstractMessageSource#getDefaultMessage(Verification)} method.
     * </p>
     *
     * @param negated
     *         {@literal true} if the {@link Verification} has been negated; otherwise {@literal false}
     * @return The expected default message.
     */
    protected abstract String getDefaultMessage(boolean negated);

    /**
     * <p>
     * Returns the expected default name for testing the {@link AbstractMessageSource#getDefaultName(Verification)}
     * method.
     * </p>
     *
     * @return The expected default name.
     */
    protected abstract String getDefaultName();

    /**
     * <p>
     * Returns the locale being used to test the subject.
     * </p>
     *
     * @return The {@code Locale}.
     */
    protected abstract Locale getLocale();

    /**
     * <p>
     * Returns a simple message that should contain at least two format placeholder for testing various methods.
     * </p>
     * <p>
     * Implementations should ensure that this message is also returned when {@link #getMessageKey()} is used to lookup
     * a message.
     * </p>
     *
     * @return A simple message.
     * @see #getMessageKey()
     * @see #getMessageFormatted(Object[])
     */
    protected abstract String getMessage();

    /**
     * <p>
     * Formats a simple message that should contain at least two format placeholder using the format {@code args}
     * provided for testing various methods.
     * </p>
     * <p>
     * Implementations should ensure that this formatted message is based on the message that is returned by
     * {@link #getMessage()}.
     * </p>
     *
     * @param args
     *         the format arguments to be used
     * @return The formatted simple message.
     * @see #getMessage()
     * @see #getMessageKey()
     */
    protected abstract String getMessageFormatted(Object[] args);

    /**
     * <p>
     * Returns the key of a simple message that should contain at least two format placeholder for testing various
     * methods.
     * </p>
     * <p>
     * Implementations should ensure that the message looked up with this key is the also returned by
     * {@link #getMessage()}.
     * </p>
     *
     * @return The {@link MessageKey} for a simple message.
     * @see #getMessage()
     */
    protected abstract MessageKey getMessageKey();

    /**
     * <p>
     * Returns the key of a message that is an invalid format pattern for testing various methods.
     * </p>
     * <p>
     * Implementations should ensure that the message looked up with this key is the also returned by
     * {@link #getMessageWithInvalidPattern()}.
     * </p>
     *
     * @return The {@link MessageKey} for a message containing a malformed format pattern.
     * @see #getMessageWithInvalidPattern()
     */
    protected abstract MessageKey getMessageKeyWithInvalidPattern();

    /**
     * <p>
     * Returns a message that is an invalid format pattern for testing various methods.
     * </p>
     * <p>
     * Implementations should ensure that this message is also returned when {@link #getMessageKeyWithInvalidPattern()}
     * is used to lookup a message.
     * </p>
     *
     * @return A message containing a malformed format pattern.
     * @see #getMessageKeyWithInvalidPattern()
     */
    protected abstract String getMessageWithInvalidPattern();

    /**
     * <p>
     * Returns the message source test subject that is being tested.
     * </p>
     *
     * @return The {@link AbstractMessageSource} being tested.
     */
    protected T getMessageSource() {
        return messageSource;
    }

    /**
     * <p>
     * Returns the key of a message that does not existing for testing various methods.
     * </p>
     *
     * @return The {@link MessageKey} of a missing message.
     */
    protected abstract MessageKey getMissingMessageKey();

    /**
     * <p>
     * Returns the mock verification being used to test the subject.
     * </p>
     *
     * @return The mock {@link Verification}.
     */
    protected Verification<?> getMockVerification() {
        return mockVerification;
    }

    private void assertMessageFormatCacheContains(String message) throws Exception {
        Map<Locale, MessageFormat> messageFormatsPerLocale = getMessageFormatsPerMessage().get(message);

        assertNotNull("Message format cache exists for message: " + message, messageFormatsPerLocale);
        assertEquals("Message format is cached for locale", new MessageFormat(message, getLocale()), messageFormatsPerLocale.get(getLocale()));
    }

    private void assertMessageFormatCacheContainsInvalidFormatMessage(String message) throws Exception {
        Map<Locale, MessageFormat> messageFormatsPerLocale = getMessageFormatsPerMessage().get(message);

        assertNotNull("Message format cache exists for message: " + message, messageFormatsPerLocale);
        assertSame("Invalid message format is cached for locale", AbstractMessageSource.INVALID_MESSAGE_FORMAT, messageFormatsPerLocale.get(getLocale()));
    }

    private void assertMessageFormatCacheIsEmpty() throws Exception {
        assertTrue("Message format cache is empty", getMessageFormatsPerMessage().isEmpty());
    }

    @SuppressWarnings("unchecked")
    private Map<String, Map<Locale, MessageFormat>> getMessageFormatsPerMessage() throws ReflectiveOperationException {
        return (Map<String, Map<Locale, MessageFormat>>) TestUtils.getField(AbstractMessageSource.class, messageSource, "messageFormatsPerMessage", true);
    }
}
