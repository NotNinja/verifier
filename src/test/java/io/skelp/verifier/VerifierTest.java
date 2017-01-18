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
package io.skelp.verifier;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import io.skelp.verifier.factory.CustomVerifierFactory;
import io.skelp.verifier.factory.DefaultVerifierFactoryProvider;
import io.skelp.verifier.factory.VerifierFactoryProvider;
import io.skelp.verifier.message.factory.MessageFormatterFactory;
import io.skelp.verifier.type.ArrayVerifier;
import io.skelp.verifier.type.BigDecimalVerifier;
import io.skelp.verifier.type.BigIntegerVerifier;
import io.skelp.verifier.type.BooleanVerifier;
import io.skelp.verifier.type.ByteVerifier;
import io.skelp.verifier.type.CalendarVerifier;
import io.skelp.verifier.type.CharacterVerifier;
import io.skelp.verifier.type.ClassVerifier;
import io.skelp.verifier.type.CollectionVerifier;
import io.skelp.verifier.type.ComparableVerifier;
import io.skelp.verifier.type.DateVerifier;
import io.skelp.verifier.type.DoubleVerifier;
import io.skelp.verifier.type.FloatVerifier;
import io.skelp.verifier.type.IntegerVerifier;
import io.skelp.verifier.type.LocaleVerifier;
import io.skelp.verifier.type.LongVerifier;
import io.skelp.verifier.type.MapVerifier;
import io.skelp.verifier.type.ObjectVerifier;
import io.skelp.verifier.type.ShortVerifier;
import io.skelp.verifier.type.StringVerifier;
import io.skelp.verifier.type.ThrowableVerifier;
import io.skelp.verifier.verification.Verification;
import io.skelp.verifier.verification.factory.VerificationFactory;

/**
 * <p>
 * Tests for the {@link Verifier} class.
 * </p>
 *
 * @author Alasdair Mercer
 */
@RunWith(MockitoJUnitRunner.class)
public class VerifierTest {

    private static VerifierFactoryProvider originalFactoryProvider;

    @BeforeClass
    public static void setUpClass() {
        originalFactoryProvider = Verifier.getFactoryProvider();
    }

    @Mock
    private CustomVerifierFactory mockCustomVerifierFactory;
    @Mock
    private MessageFormatterFactory mockMessageFormatterFactory;
    @Mock
    private Verification<?> mockVerification;
    @Mock
    private VerificationFactory mockVerificationFactory;
    @Mock
    private VerifierFactoryProvider mockVerifierFactoryProvider;

    @Before
    public void setUp() {
        when(mockVerifierFactoryProvider.getCustomVerifierFactory()).thenReturn(mockCustomVerifierFactory);
        when(mockVerifierFactoryProvider.getMessageFormatterFactory()).thenReturn(mockMessageFormatterFactory);
        when(mockVerifierFactoryProvider.getVerificationFactory()).thenReturn(mockVerificationFactory);

        when(mockVerificationFactory.create(isA(MessageFormatterFactory.class), any(), any())).thenAnswer(invocation -> mockVerification);

        Verifier.setFactoryProvider(mockVerifierFactoryProvider);
    }

    @After
    public void tearDown() {
        Verifier.setFactoryProvider(originalFactoryProvider);
    }

    @Test
    public void testConstructor() {
        // Ensure that Verifier can be instantiated, if required
        new Verifier();
    }

    @Test
    public void testFactoryProvider() {
        assertTrue("DefaultVerifierFactoryProvider instance is used by default", originalFactoryProvider instanceof DefaultVerifierFactoryProvider);

        Verifier.setFactoryProvider(mockVerifierFactoryProvider);

        assertSame("Factory provider can be changed", mockVerifierFactoryProvider, Verifier.getFactoryProvider());

        Verifier.setFactoryProvider(null);

        assertTrue("DefaultVerifierFactoryProvider instance is used as fallback", Verifier.getFactoryProvider() instanceof DefaultVerifierFactoryProvider);
    }

    @Test
    public void testVerifyWithArray() {
        Integer[] value = new Integer[]{123, 456, 789};
        ArrayVerifier<Integer> result = Verifier.verify(value);

        testVerifyHelper(result, value, null);
    }

    @Test
    public void testVerifyWithArrayAndName() {
        Integer[] value = new Integer[]{123, 456, 789};
        ArrayVerifier<Integer> result = Verifier.verify(value, "foo");

        testVerifyHelper(result, value, "foo");
    }

    @Test
    public void testVerifyWithBigDecimal() {
        BigDecimal value = BigDecimal.ONE;
        BigDecimalVerifier result = Verifier.verify(value);

        testVerifyHelper(result, value, null);
    }

    @Test
    public void testVerifyWithBigDecimalAndName() {
        BigDecimal value = BigDecimal.ONE;
        BigDecimalVerifier result = Verifier.verify(value, "foo");

        testVerifyHelper(result, value, "foo");
    }

    @Test
    public void testVerifyWithBigInteger() {
        BigInteger value = BigInteger.ONE;
        BigIntegerVerifier result = Verifier.verify(value);

        testVerifyHelper(result, value, null);
    }

    @Test
    public void testVerifyWithBigIntegerAndName() {
        BigInteger value = BigInteger.ONE;
        BigIntegerVerifier result = Verifier.verify(value, "foo");

        testVerifyHelper(result, value, "foo");
    }

    @Test
    public void testVerifyWithBoolean() {
        BooleanVerifier result = Verifier.verify(true);

        testVerifyHelper(result, true, null);
    }

    @Test
    public void testVerifyWithBooleanAndName() {
        BooleanVerifier result = Verifier.verify(true, "foo");

        testVerifyHelper(result, true, "foo");
    }

    @Test
    public void testVerifyWithByte() {
        byte value = 123;
        ByteVerifier result = Verifier.verify(value);

        testVerifyHelper(result, value, null);
    }

    @Test
    public void testVerifyWithByteAndName() {
        byte value = 123;
        ByteVerifier result = Verifier.verify(value, "foo");

        testVerifyHelper(result, value, "foo");
    }

    @Test
    public void testVerifyWithCalendar() {
        Calendar value = Calendar.getInstance();
        CalendarVerifier result = Verifier.verify(value);

        testVerifyHelper(result, value, null);
    }

    @Test
    public void testVerifyWithCalendarAndName() {
        Calendar value = Calendar.getInstance();
        CalendarVerifier result = Verifier.verify(value, "foo");

        testVerifyHelper(result, value, "foo");
    }

    @Test
    public void testVerifyWithCharacter() {
        char value = 'a';
        CharacterVerifier result = Verifier.verify(value);

        testVerifyHelper(result, value, null);
    }

    @Test
    public void testVerifyWithCharacterAndName() {
        char value = 'a';
        CharacterVerifier result = Verifier.verify(value, "foo");

        testVerifyHelper(result, value, "foo");
    }

    @Test
    public void testVerifyWithClass() {
        ClassVerifier result = Verifier.verify(VerifierTest.class);

        testVerifyHelper(result, VerifierTest.class, null);
    }

    @Test
    public void testVerifyWithClassAndName() {
        ClassVerifier result = Verifier.verify(VerifierTest.class, "foo");

        testVerifyHelper(result, VerifierTest.class, "foo");
    }

    @Test
    public void testVerifyWithCollection() {
        Collection<Integer> value = Arrays.asList(123, 456, 789);
        CollectionVerifier<Integer> result = Verifier.verify(value);

        testVerifyHelper(result, value, null);
    }

    @Test
    public void testVerifyWithCollectionAndName() {
        Collection<Integer> value = Arrays.asList(123, 456, 789);
        CollectionVerifier<Integer> result = Verifier.verify(value, "foo");

        testVerifyHelper(result, value, "foo");
    }

    @Test
    public void testVerifyWithDate() {
        Date value = new Date();
        DateVerifier result = Verifier.verify(value);

        testVerifyHelper(result, value, null);
    }

    @Test
    public void testVerifyWithDateAndName() {
        Date value = new Date();
        DateVerifier result = Verifier.verify(value, "foo");

        testVerifyHelper(result, value, "foo");
    }

    @Test
    public void testVerifyWithDouble() {
        double value = 123D;
        DoubleVerifier result = Verifier.verify(value);

        testVerifyHelper(result, value, null);
    }

    @Test
    public void testVerifyWithDoubleAndName() {
        double value = 123D;
        DoubleVerifier result = Verifier.verify(value, "foo");

        testVerifyHelper(result, value, "foo");
    }

    @Test
    public void testVerifyWithFloat() {
        float value = 123F;
        FloatVerifier result = Verifier.verify(value);

        testVerifyHelper(result, value, null);
    }

    @Test
    public void testVerifyWithFloatAndName() {
        float value = 123F;
        FloatVerifier result = Verifier.verify(value, "foo");

        testVerifyHelper(result, value, "foo");
    }

    @Test
    public void testVerifyWithInteger() {
        int value = 123;
        IntegerVerifier result = Verifier.verify(value);

        testVerifyHelper(result, value, null);
    }

    @Test
    public void testVerifyWithIntegerAndName() {
        int value = 123;
        IntegerVerifier result = Verifier.verify(value, "foo");

        testVerifyHelper(result, value, "foo");
    }

    @Test
    public void testVerifyWithLocale() {
        Locale value = Locale.US;
        LocaleVerifier result = Verifier.verify(value);

        testVerifyHelper(result, value, null);
    }

    @Test
    public void testVerifyWithLocaleAndName() {
        Locale value = Locale.US;
        LocaleVerifier result = Verifier.verify(value, "foo");

        testVerifyHelper(result, value, "foo");
    }

    @Test
    public void testVerifyWithLong() {
        long value = 123L;
        LongVerifier result = Verifier.verify(value);

        testVerifyHelper(result, value, null);
    }

    @Test
    public void testVerifyWithLongAndName() {
        long value = 123L;
        LongVerifier result = Verifier.verify(value, "foo");

        testVerifyHelper(result, value, "foo");
    }

    @Test
    public void testVerifyWithMap() {
        Map<String, Integer> value = new HashMap<>();
        value.put("abc", 123);
        MapVerifier<String, Integer> result = Verifier.verify(value);

        testVerifyHelper(result, value, null);
    }

    @Test
    public void testVerifyWithMapAndName() {
        Map<String, Integer> value = new HashMap<>();
        value.put("abc", 123);
        MapVerifier<String, Integer> result = Verifier.verify(value, "foo");

        testVerifyHelper(result, value, "foo");
    }

    @Test
    public void testVerifyWithObject() {
        Object value = new Verifier();
        ObjectVerifier result = Verifier.verify(value);

        testVerifyHelper(result, value, null);
    }

    @Test
    public void testVerifyWithObjectAndName() {
        Object value = new Verifier();
        ObjectVerifier result = Verifier.verify(value, "foo");

        testVerifyHelper(result, value, "foo");
    }

    @Test
    public void testVerifyWithShort() {
        short value = 123;
        ShortVerifier result = Verifier.verify(value);

        testVerifyHelper(result, value, null);
    }

    @Test
    public void testVerifyWithShortAndName() {
        short value = 123;
        ShortVerifier result = Verifier.verify(value, "foo");

        testVerifyHelper(result, value, "foo");
    }

    @Test
    public void testVerifyWithString() {
        StringVerifier result = Verifier.verify("foo");

        testVerifyHelper(result, "foo", null);
    }

    @Test
    public void testVerifyWithStringAndName() {
        StringVerifier result = Verifier.verify("foo", "bar");

        testVerifyHelper(result, "foo", "bar");
    }

    @Test
    public void testVerifyWithThrowable() {
        Throwable value = new Throwable();
        ThrowableVerifier result = Verifier.verify(value);

        testVerifyHelper(result, value, null);
    }

    @Test
    public void testVerifyWithThrowableAndName() {
        Throwable value = new Throwable();
        ThrowableVerifier result = Verifier.verify(value, "foo");

        testVerifyHelper(result, value, "foo");
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testVerifyWithCustomVerifierClass() {
        StringVerifier expected = new StringVerifier((Verification<String>) mockVerification);

        when(mockCustomVerifierFactory.create(StringVerifier.class, (Verification<String>) mockVerification)).thenReturn(expected);

        StringVerifier actual = Verifier.verify("foo", "bar", StringVerifier.class);

        assertSame("Uses CustomVerifier created by factory", expected, actual);
        testVerifyHelper(actual, "foo", "bar");
    }

    private <T, V extends CustomVerifier<T, V>> void testVerifyHelper(CustomVerifier<T, V> verifier, T value, Object name) {
        assertNotNull("Never returns null", verifier);
        assertSame("Uses Verification created by factory", mockVerification, verifier.verification());

        verify(mockVerificationFactory).create(mockMessageFormatterFactory, value, name);
    }

    @Test
    public void testVerifyComparable() {
        URI value = URI.create("foo");
        ComparableVerifier<URI> result = Verifier.verifyComparable(value);

        testVerifyHelper(result, value, null);
    }

    @Test
    public void testVerifyComparableWithName() {
        URI value = URI.create("foo");
        ComparableVerifier<URI> result = Verifier.verifyComparable(value, "bar");

        testVerifyHelper(result, value, "bar");
    }
}
