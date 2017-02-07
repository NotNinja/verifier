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
package io.skelp.verifier.message;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import io.skelp.verifier.message.locale.LocaleContext;
import io.skelp.verifier.util.TestUtils;
import io.skelp.verifier.verification.Verification;

/**
 * <p>
 * Tests for the {@link ResourceBundleMessageSource} class.
 * </p>
 *
 * @author Alasdair Mercer
 */
@RunWith(Enclosed.class)
public class ResourceBundleMessageSourceTest {

    private static final String[] TEST_BASE_NAMES = {"Verifier", "Missing", "TestVerifier"};
    private static final Locale TEST_LOCALE = Locale.ENGLISH;
    private static final String TEST_MESSAGE = "foo {0}: {1}";

    private static void assertMessageFormatCacheContains(ResourceBundleMessageSource messageSource, String baseName, Locale locale, MessageKey key) throws Exception {
        ResourceBundle resourceBundle = getResourceBundleForBaseName(messageSource, baseName, locale);

        assertNotNull("Resource bundle exists for base name: " + baseName, resourceBundle);

        Map<MessageKey, Map<Locale, MessageFormat>> messageFormatsPerMessageKey = getMessageFormatsPerResourceBundle(messageSource).get(resourceBundle);

        assertNotNull("Message format cache exists for resource bundle", messageFormatsPerMessageKey);

        Map<Locale, MessageFormat> messageFormatsPerLocale = messageFormatsPerMessageKey.get(key);

        assertNotNull("Message format cache exists for message key: " + key, messageFormatsPerLocale);
        assertNotNull("Message format is cached for locale", messageFormatsPerLocale.get(locale));
        assertNotSame("Valid message format is cached for locale", ResourceBundleMessageSource.INVALID_MESSAGE_FORMAT, messageFormatsPerLocale.get(locale));
    }

    private static void assertMessageFormatCacheIsEmpty(ResourceBundleMessageSource messageSource) throws Exception {
        assertTrue("Message format cache is empty", getMessageFormatsPerResourceBundle(messageSource).isEmpty());
    }

    private static void assertResourceBundle(ResourceBundle bundle, String baseName, Locale locale) {
        assertNotNull("Resource bundle is not null", bundle);
        assertEquals("Resource bundle has correct base name", baseName, bundle.getBaseBundleName());
        assertEquals("Resource bundle has correct locale", locale, bundle.getLocale());
    }

    private static void assertResourceBundleCacheContains(ResourceBundleMessageSource messageSource, String baseName, Locale locale) throws Exception {
        Map<Locale, ResourceBundle> resourceBundlesPerLocale = getResourceBundlesPerBaseName(messageSource).get(baseName);

        assertNotNull("Resource bundle cache exists for base name: " + baseName, resourceBundlesPerLocale);
        assertNotNull("Resource bundle is cached for locale", resourceBundlesPerLocale.get(locale));
    }

    private static void assertResourceBundleCacheIsEmpty(ResourceBundleMessageSource messageSource) throws Exception {
        assertTrue("Resource bundle cache is empty", getResourceBundlesPerBaseName(messageSource).isEmpty());
    }

    @SuppressWarnings("unchecked")
    private static Map<ResourceBundle, Map<MessageKey, Map<Locale, MessageFormat>>> getMessageFormatsPerResourceBundle(ResourceBundleMessageSource messageSource) throws ReflectiveOperationException {
        return (Map<ResourceBundle, Map<MessageKey, Map<Locale, MessageFormat>>>) TestUtils.getInstanceField(messageSource, "messageFormatsPerResourceBundle", true);
    }

    @SuppressWarnings("unchecked")
    private static ResourceBundle getResourceBundleForBaseName(ResourceBundleMessageSource messageSource, String baseName, Locale locale) throws ReflectiveOperationException {
        Map<Locale, ResourceBundle> resourceBundlesPerLocale = getResourceBundlesPerBaseName(messageSource).get(baseName);
        return resourceBundlesPerLocale != null ? resourceBundlesPerLocale.get(locale) : null;
    }

    @SuppressWarnings("unchecked")
    private static Map<String, Map<Locale, ResourceBundle>> getResourceBundlesPerBaseName(ResourceBundleMessageSource messageSource) throws ReflectiveOperationException {
        return (Map<String, Map<Locale, ResourceBundle>>) TestUtils.getInstanceField(messageSource, "resourceBundlesPerBaseName", true);
    }

    public static class ResourceBundleMessageSourceAbstractMessageSourceTest extends AbstractMessageSourceTestCase<ResourceBundleMessageSource> {

        @Rule
        public ExpectedException thrown = ExpectedException.none();

        @Test
        @Override
        public void hackCoverage() throws Exception {
            super.hackCoverage();

            // TODO: Determine how to avoid this
            ResourceBundleMessageSource messageSource = createMessageSource();

            TestUtils.setInstanceField(messageSource, "messageFormatsPerResourceBundle", Collections.unmodifiableMap(getMessageFormatsPerResourceBundle(messageSource)), true);

            try {
                messageSource.clearCache();
                fail("Should have thrown since map is unmodifiable");
            } catch (UnsupportedOperationException e) {
                // Do nothing
            }

            messageSource = createMessageSource();

            TestUtils.setInstanceField(messageSource, "resourceBundlesPerBaseName", Collections.unmodifiableMap(getResourceBundlesPerBaseName(messageSource)), true);

            try {
                messageSource.clearCache();
                fail("Should have thrown since map is unmodifiable");
            } catch (UnsupportedOperationException e) {
                // Do nothing
            }
        }

        @Test
        public void testBuildMessageThrowsIfMessageNotFound() {
            thrown.expect(NoSuchMessageException.class);
            thrown.expectMessage("No message found under key 'io.skelp.verifier.message.ResourceBundleMessageSource.message.normal' for locale 'en'");

            ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource() {
                @Override
                protected String getStringOrNull(ResourceBundle bundle, MessageKey key) {
                    return null;
                }
            };

            messageSource.buildMessage("be so awesome", "foo", "bar", getMockVerification());
        }

        @Test
        @Override
        public void testClearCache() throws Exception {
            String baseName = TEST_BASE_NAMES[0];
            MessageKey key = ResourceBundleMessageSource.MessageKeys.DEFAULT_NAME;
            Locale locale = TEST_LOCALE;
            ResourceBundleMessageSource messageSource = getMessageSource();
            Verification<?> mockVerification = getMockVerification();

            assertMessageFormatCacheIsEmpty(messageSource);
            assertResourceBundleCacheIsEmpty(messageSource);

            messageSource.getResourceBundle(baseName, mockVerification);
            messageSource.getMessageFormat(getResourceBundleForBaseName(messageSource, baseName, locale), key, mockVerification);

            assertResourceBundleCacheContains(messageSource, baseName, locale);
            assertMessageFormatCacheContains(messageSource, baseName, locale, key);

            super.testClearCache();

            assertMessageFormatCacheIsEmpty(messageSource);
            assertResourceBundleCacheIsEmpty(messageSource);
        }

        @Test
        public void testGetDefaultMessageThrowsIfMessageNotFound() {
            thrown.expect(NoSuchMessageException.class);
            thrown.expectMessage("No message found under key 'io.skelp.verifier.message.ResourceBundleMessageSource.default.message.normal' for locale 'en'");

            ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource() {
                @Override
                protected String getStringOrNull(ResourceBundle bundle, MessageKey key) {
                    return null;
                }
            };

            messageSource.getDefaultMessage(getMockVerification());
        }

        @Test
        public void testGetDefaultNameThrowsIfMessageNotFound() {
            thrown.expect(NoSuchMessageException.class);
            thrown.expectMessage("No message found under key 'io.skelp.verifier.message.ResourceBundleMessageSource.default.name' for locale 'en'");

            ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource() {
                @Override
                protected String getStringOrNull(ResourceBundle bundle, MessageKey key) {
                    return null;
                }
            };

            messageSource.getDefaultName(getMockVerification());
        }

        @Test
        @Override
        public void testGetMessageInternalWithMessageKey() throws Exception {
            super.testGetMessageInternalWithMessageKey();

            assertMessageFormatCacheContains(getMessageSource(), TEST_BASE_NAMES[2], TEST_LOCALE, getMessageKey());
        }

        @Test
        @Override
        public void testGetMessageInternalWithMessageKeyWhenArgsIsEmpty() throws Exception {
            super.testGetMessageInternalWithMessageKeyWhenArgsIsEmpty();

            assertMessageFormatCacheIsEmpty(getMessageSource());
        }

        @Test
        @Override
        public void testGetMessageInternalWithMessageKeyWhenArgsIsNull() throws Exception {
            super.testGetMessageInternalWithMessageKeyWhenArgsIsNull();

            assertMessageFormatCacheIsEmpty(getMessageSource());
        }

        @Test
        @Override
        public void testGetMessageInternalWithMessageKeyWhenMessageIsNull() throws Exception {
            super.testGetMessageInternalWithMessageKeyWhenMessageIsNull();

            assertMessageFormatCacheIsEmpty(getMessageSource());
        }

        @Test
        @Override
        public void testGetMessageInternalWithMessageKeyWhenArgsIsEmptyAndAlwaysUseMessageFormat() throws Exception {
            super.testGetMessageInternalWithMessageKeyWhenArgsIsEmptyAndAlwaysUseMessageFormat();

            assertMessageFormatCacheContains(getMessageSource(), TEST_BASE_NAMES[2], TEST_LOCALE, getMessageKey());
        }

        @Test
        @Override
        public void testGetMessageInternalWithMessageKeyWhenArgsIsNullAndAlwaysUseMessageFormat() throws Exception {
            super.testGetMessageInternalWithMessageKeyWhenArgsIsNullAndAlwaysUseMessageFormat();

            assertMessageFormatCacheContains(getMessageSource(), TEST_BASE_NAMES[2], TEST_LOCALE, getMessageKey());
        }

        @Test
        @Override
        public void testResolveKey() throws Exception {
            super.testResolveKey();

            assertMessageFormatCacheContains(getMessageSource(), TEST_BASE_NAMES[2], TEST_LOCALE, getMessageKey());
        }

        @Test
        public void testResolveKeyWithoutArguments() throws Exception {
            String message = getMessageSource().resolveKeyWithoutArguments(getMessageKey(), getMockVerification());

            assertEquals("Returns resolved message", getMessage(), message);

            assertMessageFormatCacheIsEmpty(getMessageSource());
        }

        @Test
        public void testResolveKeyWithoutArgumentsWhenMessageNotFound() throws Exception {
            assertNull("Returns null when message is not found", getMessageSource().resolveKeyWithoutArguments(getMissingMessageKey(), getMockVerification()));

            assertMessageFormatCacheIsEmpty(getMessageSource());
        }

        @Test
        public void testResolveKeyWithoutArgumentsWhenMessageIsInvalidPattern() throws Exception {
            String message = getMessageSource().resolveKeyWithoutArguments(getMessageKeyWithInvalidPattern(), getMockVerification());

            assertEquals("Returns resolved message even if its invalid pattern", getMessageWithInvalidPattern(), message);

            assertMessageFormatCacheIsEmpty(getMessageSource());
        }

        @Override
        protected ResourceBundleMessageSource createMessageSource() {
            return new ResourceBundleMessageSource(TEST_BASE_NAMES);
        }

        @Override
        protected String getBuiltMessage(String message, String name, String value, boolean negated) {
            String pattern = negated ? "%s must not %s: %s" : "%s must %s: %s";
            return String.format(pattern, name, message, value);
        }

        @Override
        protected String getDefaultMessage(boolean negated) {
            return negated ? "be invalid" : "be valid";
        }

        @Override
        protected String getDefaultName() {
            return "Value";
        }

        @Override
        protected Locale getLocale() {
            return TEST_LOCALE;
        }

        @Override
        protected String getMessage() {
            return TEST_MESSAGE;
        }

        @Override
        protected String getMessageFormatted(Object[] args) {
            return String.format("foo %s: %s", args);
        }

        @Override
        protected MessageKey getMessageKey() {
            return TestMessageKeys.MESSAGE_1;
        }

        @Override
        protected MessageKey getMessageKeyWithInvalidPattern() {
            return TestMessageKeys.MESSAGE_INVALID;
        }

        @Override
        protected MessageKey getMissingMessageKey() {
            return TestMessageKeys.MESSAGE_MISSING;
        }

        @Override
        protected String getMessageWithInvalidPattern() {
            return "foo {{0}";
        }
    }

    @RunWith(MockitoJUnitRunner.class)
    public static class ResourceBundleMessageSourceMiscTest {

        @Mock
        private LocaleContext mockLocaleContext;
        @Mock
        private Verification<?> mockVerification;

        private ResourceBundleMessageSource messageSource;

        @Before
        public void setUp() {
            messageSource = new ResourceBundleMessageSource(TEST_BASE_NAMES);

            when(mockLocaleContext.getLocale()).thenReturn(TEST_LOCALE);

            when(mockVerification.getLocaleContext()).thenReturn(mockLocaleContext);
            when(mockVerification.getMessageSource()).thenReturn(messageSource);
        }

        @Test
        public void hackCoverage() throws Exception {
            // TODO: Determine how to avoid this
            TestUtils.setInstanceField(messageSource, "resourceBundlesPerBaseName", Collections.unmodifiableMap(getResourceBundlesPerBaseName(messageSource)), true);

            try {
                messageSource.getResourceBundle(TEST_BASE_NAMES[2], mockVerification);
                fail("Should have thrown since map is unmodifiable");
            } catch (UnsupportedOperationException e) {
                // Do nothing
            }
        }

        @Test
        public void testConstructorWithCollection() {
            List<String> baseNames = Arrays.asList(TEST_BASE_NAMES);

            ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource(baseNames);

            assertEquals("Has expected initial base names", new LinkedHashSet<>(baseNames), messageSource.getBaseNames());
        }

        @Test
        public void testConstructorWithCollectionWhenCollectionIsEmpty() {
            ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource(Collections.emptyList());
            Set<String> baseNames = messageSource.getBaseNames();

            assertNotNull("Base names is never null", baseNames);
            assertTrue("Has no initial base names", baseNames.isEmpty());
        }

        @Test
        public void testConstructorWithCollectionWhenCollectionIsNull() {
            ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource((Collection<String>) null);
            Set<String> baseNames = messageSource.getBaseNames();

            assertNotNull("Base names is never null", baseNames);
            assertTrue("Has no initial base names", baseNames.isEmpty());
        }

        @Test
        public void testConstructorWithNoArgs() {
            Set<String> expectedBaseNames = new LinkedHashSet<>();
            expectedBaseNames.add("Verifier");

            ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();

            assertEquals("Has expected default base names", expectedBaseNames, messageSource.getBaseNames());
        }

        @Test
        public void testConstructorWithVarArgs() {
            Set<String> expectedBaseNames = new LinkedHashSet<>(Arrays.asList(TEST_BASE_NAMES));
            ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource(TEST_BASE_NAMES);

            assertEquals("Has expected initial base names", expectedBaseNames, messageSource.getBaseNames());
        }

        @Test
        public void testConstructorWithVarArgsWhenVarArgsIsEmpty() {
            ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource((String[]) null);
            Set<String> baseNames = messageSource.getBaseNames();

            assertNotNull("Base names is never null", baseNames);
            assertTrue("Has no initial base names", baseNames.isEmpty());
        }

        @Test
        public void testConstructorWithVarArgsWhenVarArgsIsNull() {
            ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource(new String[0]);
            Set<String> baseNames = messageSource.getBaseNames();

            assertNotNull("Base names is never null", baseNames);
            assertTrue("Has no initial base names", baseNames.isEmpty());
        }

        @Test
        public void testBaseNames() {
            assertNotNull("BaseNames property is readable and is never null by default", messageSource.getBaseNames());

            messageSource.getBaseNames().add("foo");

            assertTrue("BaseNames property is modifiable", messageSource.getBaseNames().contains("foo"));
        }

        @Test
        public void testGetMessageFormat() throws Exception {
            testGetMessageFormatHelper(TEST_BASE_NAMES[2], TEST_LOCALE, TestMessageKeys.MESSAGE_1, true, "foo {0}: {1}");
        }

        @Test
        public void testGetMessageFormatWhenMessageNotFound() throws Exception {
            testGetMessageFormatHelper(TEST_BASE_NAMES[2], TEST_LOCALE, TestMessageKeys.MESSAGE_MISSING, false, null);
        }

        @Test
        public void testGetMessageFormatWhenOtherMessageKeyPreviouslyCachedForLocale() throws Exception {
            String baseName = TEST_BASE_NAMES[2];
            Locale locale = TEST_LOCALE;
            ResourceBundle bundle = messageSource.getResourceBundle(baseName, mockVerification);
            MessageFormat messageFormat = messageSource.getMessageFormat(bundle, TestMessageKeys.MESSAGE_1, mockVerification);

            assertNotNull("Message format is returned", messageFormat);
            assertEquals("Message format has correct pattern", "foo {0}: {1}", messageFormat.toPattern());
            assertEquals("Message format has correct locale", locale, messageFormat.getLocale());

            testGetMessageFormatHelper(baseName, locale, TestMessageKeys.MESSAGE_2, true, "foo {0}");
        }

        @Test
        public void testGetMessageFormatWhenPreviouslyCachedForLocale() throws Exception {
            String expected = "foo {0}: {1}";
            String baseName = TEST_BASE_NAMES[2];
            MessageKey key = TestMessageKeys.MESSAGE_1;
            Locale locale = TEST_LOCALE;
            ResourceBundle bundle = messageSource.getResourceBundle(baseName, mockVerification);
            MessageFormat messageFormat = messageSource.getMessageFormat(bundle, key, mockVerification);

            assertNotNull("Message format is returned", messageFormat);
            assertEquals("Message format has correct pattern", expected, messageFormat.toPattern());
            assertEquals("Message format has correct locale", locale, messageFormat.getLocale());

            testGetMessageFormatHelper(baseName, locale, key, true, expected);
        }

        @Test
        public void testGetMessageFormatWhenPreviouslyCachedForOtherLocale() throws Exception {
            String expected = "foo {0}: {1}";
            String baseName = TEST_BASE_NAMES[2];
            MessageKey key = TestMessageKeys.MESSAGE_1;
            ResourceBundle bundle = messageSource.getResourceBundle(baseName, mockVerification);
            MessageFormat messageFormat = messageSource.getMessageFormat(bundle, key, mockVerification);

            assertNotNull("Message format is returned", messageFormat);
            assertEquals("Message format has correct pattern", expected, messageFormat.toPattern());
            assertEquals("Message format has correct locale", TEST_LOCALE, messageFormat.getLocale());

            testGetMessageFormatHelper(baseName, Locale.CHINESE, key, true, "foo {0}: {1}");
        }

        @Test(expected = IllegalArgumentException.class)
        public void testGetMessageFormatThrowsWhenResolvedMessageIsInvalidPattern() throws Exception {
            testGetMessageFormatHelper(TEST_BASE_NAMES[2], TEST_LOCALE, TestMessageKeys.MESSAGE_INVALID, false, null);
        }

        private void testGetMessageFormatHelper(String baseName, Locale locale, MessageKey key, boolean cached, String expected) throws Exception {
            reset(mockLocaleContext);
            when(mockLocaleContext.getLocale()).thenReturn(locale);

            ResourceBundle bundle = messageSource.getResourceBundle(baseName, mockVerification);
            MessageFormat messageFormat = messageSource.getMessageFormat(bundle, key, mockVerification);

            if (expected == null) {
                assertNull("Returns null for missing key", messageFormat);
            } else {
                assertNotNull("Message format is returned for valid key", messageFormat);
                assertEquals("Message format has correct pattern", expected, messageFormat.toPattern());
                assertEquals("Message format has correct locale", locale, messageFormat.getLocale());
            }

            if (cached) {
                assertMessageFormatCacheContains(messageSource, baseName, locale, key);
            } else {
                assertMessageFormatCacheIsEmpty(messageSource);
            }
        }

        @Test
        public void testGetResourceBundle() throws Exception {
            Locale locale = Locale.FRENCH;

            testGetResourceBundleHelper(TEST_BASE_NAMES[2], locale, locale);
        }

        @Test
        public void testGetResourceBundleWhenPreviouslyCachedForLocale() throws Exception {
            String baseName = TEST_BASE_NAMES[2];
            ResourceBundle bundle = messageSource.getResourceBundle(baseName, mockVerification);

            assertResourceBundle(bundle, baseName, new Locale(""));

            testGetResourceBundleHelper(baseName, TEST_LOCALE, new Locale(""));
        }

        @Test
        public void testGetResourceBundleWhenPreviouslyCachedForOtherLocale() throws Exception {
            String baseName = TEST_BASE_NAMES[2];
            ResourceBundle bundle = messageSource.getResourceBundle(baseName, mockVerification);

            assertResourceBundle(bundle, baseName, new Locale(""));

            Locale locale = Locale.FRENCH;

            testGetResourceBundleHelper(baseName, locale, locale);

            assertResourceBundleCacheContains(messageSource, baseName, TEST_LOCALE);
        }

        @Test
        public void testGetResourceBundleWhenResourceBundleNotFound() throws Exception {
            assertNull("Returns null when resource bundle not found", messageSource.getResourceBundle(TEST_BASE_NAMES[1], mockVerification));

            assertResourceBundleCacheIsEmpty(messageSource);
        }

        @Test
        public void testGetResourceBundleWhenResourceBundleNotFoundForLocale() {
            String baseName = TEST_BASE_NAMES[2];
            ResourceBundle defaultBundle = messageSource.getResourceBundle(baseName, mockVerification);

            assertResourceBundle(defaultBundle, baseName, new Locale(""));
        }

        private void testGetResourceBundleHelper(String baseName, Locale locale, Locale expectedLocale) throws Exception {
            reset(mockLocaleContext);
            when(mockLocaleContext.getLocale()).thenReturn(locale);

            ResourceBundle bundle = messageSource.getResourceBundle(baseName, mockVerification);

            assertResourceBundle(bundle, baseName, expectedLocale);
            assertResourceBundleCacheContains(messageSource, baseName, locale);
        }

        @Test
        public void testGetResourceBundleInternal() {
            Locale locale = Locale.FRENCH;

            testGetResourceBundleInternalHelper(TEST_BASE_NAMES[2], locale, locale);
        }

        @Test
        public void testGetResourceBundleInternalWhenResourceBundleNotFoundForLocale() {
            testGetResourceBundleInternalHelper(TEST_BASE_NAMES[2], TEST_LOCALE, new Locale(""));
        }

        @Test(expected = MissingResourceException.class)
        public void testGetResourceBundleInternalThrowsWhenResourceBundleNotFound() {
            messageSource.getResourceBundleInternal(TEST_BASE_NAMES[1], TEST_LOCALE);
        }

        private void testGetResourceBundleInternalHelper(String baseName, Locale locale, Locale expectedLocale) {
            ResourceBundle bundle = messageSource.getResourceBundleInternal(baseName, locale);

            assertResourceBundle(bundle, baseName, expectedLocale);
        }

        @Test
        public void testGetStringOrNull() {
            ResourceBundle bundle = messageSource.getResourceBundleInternal(TEST_BASE_NAMES[2], TEST_LOCALE);

            assertEquals("Returns string for key", TEST_MESSAGE, messageSource.getStringOrNull(bundle, TestMessageKeys.MESSAGE_1));
        }

        @Test
        public void testGetStringOrNullWhenMessageNotFound() {
            ResourceBundle bundle = messageSource.getResourceBundleInternal(TEST_BASE_NAMES[2], TEST_LOCALE);

            assertNull("Returns null when key is missing", messageSource.getStringOrNull(bundle, TestMessageKeys.MESSAGE_MISSING));
        }

        @Test
        public void testSetBaseNamesWithCollection() {
            Set<String> originalBaseNames = messageSource.getBaseNames();

            assertEquals("Has expected initial base names", new LinkedHashSet<>(Arrays.asList(TEST_BASE_NAMES)), originalBaseNames);

            messageSource.setBaseNames(Arrays.asList("foo", "bar"));

            Set<String> baseNames = messageSource.getBaseNames();

            assertEquals("Base names contains expected base names", new LinkedHashSet<>(Arrays.asList("foo", "bar")), baseNames);
            assertSame("Base names reference is not changed", originalBaseNames, baseNames);
        }

        @Test
        public void testSetBaseNamesWithCollectionWhenCollectionIsEmpty() {
            Set<String> originalBaseNames = messageSource.getBaseNames();

            assertEquals("Has expected initial base names", new LinkedHashSet<>(Arrays.asList(TEST_BASE_NAMES)), originalBaseNames);

            messageSource.setBaseNames(Collections.emptyList());

            Set<String> baseNames = messageSource.getBaseNames();

            assertNotNull("Base names is never null", baseNames);
            assertTrue("Base names is empty", baseNames.isEmpty());
            assertSame("Base names reference is not changed", originalBaseNames, baseNames);
        }

        @Test
        public void testSetBaseNamesWithCollectionWhenCollectionIsNull() {
            Set<String> originalBaseNames = messageSource.getBaseNames();

            assertEquals("Has expected initial base names", new LinkedHashSet<>(Arrays.asList(TEST_BASE_NAMES)), originalBaseNames);

            messageSource.setBaseNames((Collection<String>) null);

            Set<String> baseNames = messageSource.getBaseNames();

            assertNotNull("Base names is never null", baseNames);
            assertTrue("Base names is empty", baseNames.isEmpty());
            assertSame("Base names reference is not changed", originalBaseNames, baseNames);
        }

        @Test
        public void testSetBaseNamesWithVarArgs() {
            Set<String> originalBaseNames = messageSource.getBaseNames();

            assertEquals("Has expected initial base names", new LinkedHashSet<>(Arrays.asList(TEST_BASE_NAMES)), originalBaseNames);

            messageSource.setBaseNames("foo", "bar");

            Set<String> baseNames = messageSource.getBaseNames();

            assertEquals("Base names contains expected base names", new LinkedHashSet<>(Arrays.asList("foo", "bar")), baseNames);
            assertSame("Base names reference is not changed", originalBaseNames, baseNames);
        }

        @Test
        public void testSetBaseNamesWithVarArgsWhenVarArgsIsEmpty() {
            Set<String> originalBaseNames = messageSource.getBaseNames();

            assertEquals("Has expected initial base names", new LinkedHashSet<>(Arrays.asList(TEST_BASE_NAMES)), originalBaseNames);

            messageSource.setBaseNames();

            Set<String> baseNames = messageSource.getBaseNames();

            assertNotNull("Base names is never null", baseNames);
            assertTrue("Base names is empty", baseNames.isEmpty());
            assertSame("Base names reference is not changed", originalBaseNames, baseNames);
        }

        @Test
        public void testSetBaseNamesWithVarArgsWhenVarArgsIsNull() {
            Set<String> originalBaseNames = messageSource.getBaseNames();

            assertEquals("Has expected initial base names", new LinkedHashSet<>(Arrays.asList(TEST_BASE_NAMES)), originalBaseNames);

            messageSource.setBaseNames((String[]) null);

            Set<String> baseNames = messageSource.getBaseNames();

            assertNotNull("Base names is never null", baseNames);
            assertTrue("Base names is empty", baseNames.isEmpty());
            assertSame("Base names reference is not changed", originalBaseNames, baseNames);
        }
    }

    private enum TestMessageKeys implements MessageKey {

        MESSAGE_1("io.skelp.verifier.message.ResourceBundleMessageSource.test.message1"),
        MESSAGE_2("io.skelp.verifier.message.ResourceBundleMessageSource.test.message2"),
        MESSAGE_INVALID("io.skelp.verifier.message.ResourceBundleMessageSource.test.message3"),
        MESSAGE_MISSING("io.skelp.verifier.message.ResourceBundleMessageSource.test.missing");

        private final String code;

        TestMessageKeys(String code) {
            this.code = code;
        }

        @Override
        public String code() {
            return code;
        }
    }
}
