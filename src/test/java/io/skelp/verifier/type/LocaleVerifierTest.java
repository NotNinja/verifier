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

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import io.skelp.verifier.AbstractCustomVerifierTestCase;
import io.skelp.verifier.CustomVerifierTestCaseBase;
import io.skelp.verifier.message.MessageKeyEnumTestCase;
import io.skelp.verifier.util.TestUtils;

/**
 * <p>
 * Tests for the {@link LocaleVerifier} class.
 * </p>
 *
 * @author Alasdair Mercer
 */
@RunWith(Enclosed.class)
public class LocaleVerifierTest {

    public static class LocaleVerifierAbstractCustomVerifierTest extends AbstractCustomVerifierTestCase<Locale, LocaleVerifier> {

        @Override
        protected LocaleVerifier createCustomVerifier() {
            return new LocaleVerifier(getMockVerification());
        }

        @Override
        protected Locale createValueOne() {
            return new Locale("foo", "bar");
        }

        @Override
        protected Locale createValueTwo() {
            return new Locale("fu", "baz");
        }

        @Override
        protected Class<?> getParentClass() {
            return Object.class;
        }

        @Override
        protected Class<?> getValueClass() {
            return Locale.class;
        }
    }

    public static class LocaleVerifierMiscTest extends CustomVerifierTestCaseBase<Locale, LocaleVerifier> {

        private static Set<Locale> originalAvailableLocales;
        private static Locale originalDefaultLocale;

        @BeforeClass
        public static void setUpClass() throws Exception {
            originalAvailableLocales = Collections.unmodifiableSet(new HashSet<>(getAvailableLocales()));
            originalDefaultLocale = Locale.getDefault();
        }

        @After
        @Override
        public void tearDown() throws Exception {
            super.tearDown();

            setAvailableLocales(originalAvailableLocales);
            Locale.setDefault(originalDefaultLocale);
        }

        private static Locale findAvailableLocaleWithScript() {
            Locale locale = null;
            for (Locale availableLocale : originalAvailableLocales) {
                if (!availableLocale.getScript().isEmpty()) {
                    locale = availableLocale;
                    break;
                }
            }

            if (locale == null) {
                fail("Could not find available locale with script");
            }

            return locale;
        }

        @SuppressWarnings("unchecked")
        private static Set<Locale> getAvailableLocales() throws ReflectiveOperationException {
            return (Set<Locale>) TestUtils.getStaticField(getLazyHolderClass(), "AVAILABLE_LOCALES", true);
        }

        private static void setAvailableLocales(Set<Locale> availableLocales) throws ReflectiveOperationException {
            TestUtils.setStaticField(getLazyHolderClass(), "AVAILABLE_LOCALES", availableLocales, true);
        }

        private static Class<?> getLazyHolderClass() throws ReflectiveOperationException {
            return Class.forName(LocaleVerifier.class.getName() + "$LazyHolder");
        }

        @Test
        public void hackCoverage() throws Exception {
            // TODO: Determine how to avoid this
            TestUtils.createInstance(getLazyHolderClass(), null, true);
        }

        @Test
        public void testAvailable() throws Exception {
            testAvailableHelper(Locale.ENGLISH, getAvailableLocales().toArray(new Locale[0]), true);
        }

        @Test
        public void testAvailableWithAvailableValue() throws Exception {
            testAvailableHelper(Locale.ENGLISH, new Locale[]{Locale.FRENCH, Locale.ENGLISH}, true);
        }

        @Test
        public void testAvailableWithNullValue() throws Exception {
            testAvailableHelper(null, new Locale[]{Locale.ENGLISH}, false);
        }

        @Test
        public void testAvailableWithUnavailableValue() throws Exception {
            testAvailableHelper(Locale.GERMAN, new Locale[]{Locale.FRENCH, Locale.ENGLISH}, false);
        }

        private void testAvailableHelper(Locale value, Locale[] availableLocales, boolean expected) throws ReflectiveOperationException {
            setValue(value);
            setAvailableLocales(new HashSet<>(Arrays.asList(availableLocales)));

            assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().available());

            verify(getMockVerification()).check(expected, LocaleVerifier.MessageKeys.AVAILABLE);
        }

        @Test
        public void testDefaultingWithDefaultValue() {
            testDefaultingHelper(Locale.ENGLISH, Locale.ENGLISH, true);
        }

        @Test
        public void testDefaultingWithNonDefaultValue() {
            testDefaultingHelper(Locale.GERMAN, Locale.ENGLISH, false);
        }

        @Test
        public void testDefaultingWithNullValue() {
            testDefaultingHelper(null, Locale.ENGLISH, false);
        }

        private void testDefaultingHelper(Locale value, Locale defaultLocale, boolean expected) {
            setValue(value);
            Locale.setDefault(defaultLocale);

            assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().defaulting());

            verify(getMockVerification()).check(expected, LocaleVerifier.MessageKeys.DEFAULTING);
        }

        @Test
        public void testCountryWithDifferentCountry() {
            testCountryHelper(new Locale("foo", "BAR"), "BAZ", false);
        }

        @Test
        public void testCountryWithNullValue() {
            testCountryHelper(null, "BAR", false);
        }

        @Test
        public void testCountryWithSameCountry() {
            testCountryHelper(new Locale("foo", "BAR"), "BAR", true);
        }

        private void testCountryHelper(Locale value, String country, boolean expected) {
            setValue(value);

            assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().country(country));

            verify(getMockVerification()).check(eq(expected), eq(LocaleVerifier.MessageKeys.COUNTRY), getArgsCaptor().capture());

            assertSame("Passes country for message formatting", country, getArgsCaptor().getValue());
        }

        @Test
        public void testLanguageWithDifferentLanguage() {
            testLanguageHelper(new Locale("foo", "BAR"), "fu", false);
        }

        @Test
        public void testLanguageWithNullValue() {
            testLanguageHelper(null, "foo", false);
        }

        @Test
        public void testLanguageWithSameLanguage() {
            testLanguageHelper(new Locale("foo", "BAR"), "foo", true);
        }

        private void testLanguageHelper(Locale value, String language, boolean expected) {
            setValue(value);

            assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().language(language));

            verify(getMockVerification()).check(eq(expected), eq(LocaleVerifier.MessageKeys.LANGUAGE), getArgsCaptor().capture());

            assertSame("Passes language for message formatting", language, getArgsCaptor().getValue());
        }

        @Test
        public void testScriptWithDifferentScript() {
            Locale value = findAvailableLocaleWithScript();

            testScriptHelper(value, UUID.randomUUID().toString(), false);
        }

        @Test
        public void testScriptWithNullValue() {
            testScriptHelper(null, "fizz", false);
        }

        @Test
        public void testScriptWithSameScript() {
            Locale value = findAvailableLocaleWithScript();

            testScriptHelper(value, value.getScript(), true);
        }

        private void testScriptHelper(Locale value, String script, boolean expected) {
            setValue(value);

            assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().script(script));

            verify(getMockVerification()).check(eq(expected), eq(LocaleVerifier.MessageKeys.SCRIPT), getArgsCaptor().capture());

            assertSame("Passes script for message formatting", script, getArgsCaptor().getValue());
        }

        @Test
        public void testVariantWithDifferentVariant() {
            testVariantHelper(new Locale("foo", "BAR", "fizz"), "buzz", false);
        }

        @Test
        public void testVariantWithNullValue() {
            testVariantHelper(null, "fizz", false);
        }

        @Test
        public void testVariantWithSameVariant() {
            testVariantHelper(new Locale("foo", "BAR", "fizz"), "fizz", true);
        }

        private void testVariantHelper(Locale value, String variant, boolean expected) {
            setValue(value);

            assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().variant(variant));

            verify(getMockVerification()).check(eq(expected), eq(LocaleVerifier.MessageKeys.VARIANT), getArgsCaptor().capture());

            assertSame("Passes variant for message formatting", variant, getArgsCaptor().getValue());
        }

        @Override
        protected LocaleVerifier createCustomVerifier() {
            return new LocaleVerifier(getMockVerification());
        }
    }

    public static class LocaleVerifierMessageKeysTest extends MessageKeyEnumTestCase<LocaleVerifier.MessageKeys> {

        @Override
        protected Class<? extends Enum> getEnumClass() {
            return LocaleVerifier.MessageKeys.class;
        }

        @Override
        protected Map<String, String> getMessageKeys() {
            Map<String, String> messageKeys = new HashMap<>();
            messageKeys.put("AVAILABLE", "io.skelp.verifier.type.LocaleVerifier.available");
            messageKeys.put("COUNTRY", "io.skelp.verifier.type.LocaleVerifier.country");
            messageKeys.put("DEFAULTING", "io.skelp.verifier.type.LocaleVerifier.defaulting");
            messageKeys.put("LANGUAGE", "io.skelp.verifier.type.LocaleVerifier.language");
            messageKeys.put("SCRIPT", "io.skelp.verifier.type.LocaleVerifier.script");
            messageKeys.put("VARIANT", "io.skelp.verifier.type.LocaleVerifier.variant");

            return messageKeys;
        }
    }
}
