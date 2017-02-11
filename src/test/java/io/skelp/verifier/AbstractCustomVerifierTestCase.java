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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import io.skelp.verifier.message.MessageKey;
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

/**
 * <p>
 * Test case for {@link AbstractCustomVerifier} implementation classes.
 * </p>
 *
 * @param <T>
 *         the value type for the {@link AbstractCustomVerifier} being tested
 * @param <V>
 *         the type of the {@link AbstractCustomVerifier} being tested
 * @author Alasdair Mercer
 */
@RunWith(MockitoJUnitRunner.class)
public abstract class AbstractCustomVerifierTestCase<T, V extends AbstractCustomVerifier<T, V>> extends CustomVerifierTestCaseBase<T, V> {

    @Mock
    private VerifierAssertion<T> mockAssertion;

    @Test
    public void testAndWithArray() {
        Integer[] value = new Integer[]{123, 456, 789};
        ArrayVerifier<Integer> result = getCustomVerifier().and(value);

        testAndHelper(result, value, null);
    }

    @Test
    public void testAndWithArrayAndName() {
        Integer[] value = new Integer[]{123, 456, 789};
        ArrayVerifier<Integer> result = getCustomVerifier().and(value, "foo");

        testAndHelper(result, value, "foo");
    }

    @Test
    public void testAndWithBigDecimal() {
        BigDecimal value = BigDecimal.ONE;
        BigDecimalVerifier result = getCustomVerifier().and(value);

        testAndHelper(result, value, null);
    }

    @Test
    public void testAndWithBigDecimalAndName() {
        BigDecimal value = BigDecimal.ONE;
        BigDecimalVerifier result = getCustomVerifier().and(value, "foo");

        testAndHelper(result, value, "foo");
    }

    @Test
    public void testAndWithBigInteger() {
        BigInteger value = BigInteger.ONE;
        BigIntegerVerifier result = getCustomVerifier().and(value);

        testAndHelper(result, value, null);
    }

    @Test
    public void testAndWithBigIntegerAndName() {
        BigInteger value = BigInteger.ONE;
        BigIntegerVerifier result = getCustomVerifier().and(value, "foo");

        testAndHelper(result, value, "foo");
    }

    @Test
    public void testAndWithBoolean() {
        BooleanVerifier result = getCustomVerifier().and(true);

        testAndHelper(result, true, null);
    }

    @Test
    public void testAndWithBooleanAndName() {
        BooleanVerifier result = getCustomVerifier().and(true, "foo");

        testAndHelper(result, true, "foo");
    }

    @Test
    public void testAndWithByte() {
        byte value = 123;
        ByteVerifier result = getCustomVerifier().and(value);

        testAndHelper(result, value, null);
    }

    @Test
    public void testAndWithByteAndName() {
        byte value = 123;
        ByteVerifier result = getCustomVerifier().and(value, "foo");

        testAndHelper(result, value, "foo");
    }

    @Test
    public void testAndWithCalendar() {
        Calendar value = Calendar.getInstance();
        CalendarVerifier result = getCustomVerifier().and(value);

        testAndHelper(result, value, null);
    }

    @Test
    public void testAndWithCalendarAndName() {
        Calendar value = Calendar.getInstance();
        CalendarVerifier result = getCustomVerifier().and(value, "foo");

        testAndHelper(result, value, "foo");
    }

    @Test
    public void testAndWithCharacter() {
        char value = 'a';
        CharacterVerifier result = getCustomVerifier().and(value);

        testAndHelper(result, value, null);
    }

    @Test
    public void testAndWithCharacterAndName() {
        char value = 'a';
        CharacterVerifier result = getCustomVerifier().and(value, "foo");

        testAndHelper(result, value, "foo");
    }

    @Test
    public void testAndWithClass() {
        ClassVerifier result = getCustomVerifier().and(VerifierTest.class);

        testAndHelper(result, VerifierTest.class, null);
    }

    @Test
    public void testAndWithClassAndName() {
        ClassVerifier result = getCustomVerifier().and(VerifierTest.class, "foo");

        testAndHelper(result, VerifierTest.class, "foo");
    }

    @Test
    public void testAndWithCollection() {
        Collection<Integer> value = Arrays.asList(123, 456, 789);
        CollectionVerifier<Integer> result = getCustomVerifier().and(value);

        testAndHelper(result, value, null);
    }

    @Test
    public void testAndWithCollectionAndName() {
        Collection<Integer> value = Arrays.asList(123, 456, 789);
        CollectionVerifier<Integer> result = getCustomVerifier().and(value, "foo");

        testAndHelper(result, value, "foo");
    }

    @Test
    public void testAndWithDate() {
        Date value = new Date();
        DateVerifier result = getCustomVerifier().and(value);

        testAndHelper(result, value, null);
    }

    @Test
    public void testAndWithDateAndName() {
        Date value = new Date();
        DateVerifier result = getCustomVerifier().and(value, "foo");

        testAndHelper(result, value, "foo");
    }

    @Test
    public void testAndWithDouble() {
        double value = 123D;
        DoubleVerifier result = getCustomVerifier().and(value);

        testAndHelper(result, value, null);
    }

    @Test
    public void testAndWithDoubleAndName() {
        double value = 123D;
        DoubleVerifier result = getCustomVerifier().and(value, "foo");

        testAndHelper(result, value, "foo");
    }

    @Test
    public void testAndWithFloat() {
        float value = 123F;
        FloatVerifier result = getCustomVerifier().and(value);

        testAndHelper(result, value, null);
    }

    @Test
    public void testAndWithFloatAndName() {
        float value = 123F;
        FloatVerifier result = getCustomVerifier().and(value, "foo");

        testAndHelper(result, value, "foo");
    }

    @Test
    public void testAndWithInteger() {
        int value = 123;
        IntegerVerifier result = getCustomVerifier().and(value);

        testAndHelper(result, value, null);
    }

    @Test
    public void testAndWithIntegerAndName() {
        int value = 123;
        IntegerVerifier result = getCustomVerifier().and(value, "foo");

        testAndHelper(result, value, "foo");
    }

    @Test
    public void testAndWithLocale() {
        Locale value = Locale.US;
        LocaleVerifier result = getCustomVerifier().and(value);

        testAndHelper(result, value, null);
    }

    @Test
    public void testAndWithLocaleAndName() {
        Locale value = Locale.US;
        LocaleVerifier result = getCustomVerifier().and(value, "foo");

        testAndHelper(result, value, "foo");
    }

    @Test
    public void testAndWithLong() {
        long value = 123L;
        LongVerifier result = getCustomVerifier().and(value);

        testAndHelper(result, value, null);
    }

    @Test
    public void testAndWithLongAndName() {
        long value = 123L;
        LongVerifier result = getCustomVerifier().and(value, "foo");

        testAndHelper(result, value, "foo");
    }

    @Test
    public void testAndWithMap() {
        Map<String, Integer> value = new HashMap<>();
        value.put("abc", 123);
        MapVerifier<String, Integer> result = getCustomVerifier().and(value);

        testAndHelper(result, value, null);
    }

    @Test
    public void testAndWithMapAndName() {
        Map<String, Integer> value = new HashMap<>();
        value.put("abc", 123);
        MapVerifier<String, Integer> result = getCustomVerifier().and(value, "foo");

        testAndHelper(result, value, "foo");
    }

    @Test
    public void testAndWithObject() {
        Object value = new Verifier();
        ObjectVerifier result = getCustomVerifier().and(value);

        testAndHelper(result, value, null);
    }

    @Test
    public void testAndWithObjectAndName() {
        Object value = new Verifier();
        ObjectVerifier result = getCustomVerifier().and(value, "foo");

        testAndHelper(result, value, "foo");
    }

    @Test
    public void testAndWithShort() {
        short value = 123;
        ShortVerifier result = getCustomVerifier().and(value);

        testAndHelper(result, value, null);
    }

    @Test
    public void testAndWithShortAndName() {
        short value = 123;
        ShortVerifier result = getCustomVerifier().and(value, "foo");

        testAndHelper(result, value, "foo");
    }

    @Test
    public void testAndWithString() {
        StringVerifier result = getCustomVerifier().and("foo");

        testAndHelper(result, "foo", null);
    }

    @Test
    public void testAndWithStringAndName() {
        StringVerifier result = getCustomVerifier().and("foo", "bar");

        testAndHelper(result, "foo", "bar");
    }

    @Test
    public void testAndWithThrowable() {
        Throwable value = new Throwable();
        ThrowableVerifier result = getCustomVerifier().and(value);

        testAndHelper(result, value, null);
    }

    @Test
    public void testAndWithThrowableAndName() {
        Throwable value = new Throwable();
        ThrowableVerifier result = getCustomVerifier().and(value, "foo");

        testAndHelper(result, value, "foo");
    }

    @Test
    public void testAndWithCustomVerifierClass() {
        @SuppressWarnings("unchecked")
        Verification<String> mockVerification = (Verification<String>) mock(Verification.class);
        StringVerifier expected = new StringVerifier(mockVerification);

        when(getMockVerification().copy("foo", "bar")).thenReturn(mockVerification);

        when(getMockCustomVerifierProvider().getCustomVerifier(StringVerifier.class, mockVerification)).thenReturn(expected);

        StringVerifier actual = getCustomVerifier().and("foo", "bar", StringVerifier.class);

        assertSame("Uses CustomVerifier created by factory", expected, actual);
        testAndHelper(actual, "foo", "bar");
    }

    private <U, C extends CustomVerifier<U, C>> void testAndHelper(CustomVerifier<U, C> verifier, U value, Object name) {
        assertNotNull("Never returns null", verifier);

        verify(getMockVerification()).copy(value, name);
    }

    @Test
    public void testAndComparable() {
        URI value = URI.create("foo");
        ComparableVerifier<URI> result = getCustomVerifier().andComparable(value);

        testAndHelper(result, value, null);
    }

    @Test
    public void testAndComparableWithName() {
        URI value = URI.create("foo");
        ComparableVerifier<URI> result = getCustomVerifier().andComparable(value, "bar");

        testAndHelper(result, value, "bar");
    }

    @Test
    public void testEqualToWithDifferentInstance() {
        testEqualToHelper(createValueOne(), createValueTwo(), false);
    }

    @Test
    public void testEqualToWithEqualInstance() {
        testEqualToHelper(createValueOne(), createValueOne(), true);
    }

    @Test
    public void testEqualToWithNullOther() {
        testEqualToHelper(createValueOne(), null, false);
    }

    @Test
    public void testEqualToWithNullValue() {
        testEqualToHelper(null, createValueOne(), false);
    }

    @Test
    public void testEqualToWithNullValueAndNullOther() {
        testEqualToHelper(null, null, true);
    }

    @Test
    public void testEqualToWithSameInstance() {
        T value = createValueOne();

        testEqualToHelper(value, value, true);
    }

    private void testEqualToHelper(T value, Object other, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().equalTo(other));

        verify(getMockVerification()).report(eq(expected), eq(AbstractCustomVerifier.MessageKeys.EQUAL_TO), getArgsCaptor().capture());

        assertSame("Passes other for message formatting", other, getArgsCaptor().getValue());
    }

    @Test
    public void testEqualToWithNameAndDifferentInstance() {
        testEqualToHelper(createValueOne(), createValueTwo(), "other", false);
    }

    @Test
    public void testEqualToWithNameAndEqualInstance() {
        testEqualToHelper(createValueOne(), createValueOne(), "other", true);
    }

    @Test
    public void testEqualToWithNameAndNullOther() {
        testEqualToHelper(createValueOne(), null, "other", false);
    }

    @Test
    public void testEqualToWithNameAndNullValue() {
        testEqualToHelper(null, createValueOne(), "other", false);
    }

    @Test
    public void testEqualToWithNameAndNullValueAndNullOther() {
        testEqualToHelper(null, null, "other", true);
    }

    @Test
    public void testEqualToWithNameAndSameInstance() {
        T value = createValueOne();

        testEqualToHelper(value, value, "other", true);
    }

    private void testEqualToHelper(T value, Object other, Object name, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().equalTo(other, name));

        verify(getMockVerification()).report(eq(expected), eq(AbstractCustomVerifier.MessageKeys.EQUAL_TO), getArgsCaptor().capture());

        assertSame("Passes name for message formatting", name, getArgsCaptor().getValue());
    }

    @Test
    public void testEqualToAnyWithDifferentInstance() {
        testEqualToAnyHelper(createValueOne(), new Object[]{createValueTwo(), new Object()}, false);
    }

    @Test
    public void testEqualToAnyWithEqualInstance() {
        testEqualToAnyHelper(createValueOne(), new Object[]{createValueTwo(), new Object(), createValueOne()}, true);
    }

    @Test
    public void testEqualToAnyWithNoOthers() {
        testEqualToAnyHelper(createValueOne(), new Object[0], false);
    }

    @Test
    public void testEqualToAnyWithNullOther() {
        testEqualToAnyHelper(createValueOne(), new Object[]{null}, false);
    }

    @Test
    public void testEqualToAnyWithNullOthers() {
        testEqualToAnyHelper(createValueOne(), null, false);
    }

    @Test
    public void testEqualToAnyWithNullValue() {
        testEqualToAnyHelper(null, new Object[]{createValueOne(), createValueTwo(), new Object()}, false);
    }

    @Test
    public void testEqualToAnyWithNullValueAndNullOther() {
        testEqualToAnyHelper(null, new Object[]{null}, true);
    }

    @Test
    public void testEqualToAnyWithNullValueAndNullOthers() {
        testEqualToAnyHelper(null, null, false);
    }

    @Test
    public void testEqualToAnyWithSameInstance() {
        T value = createValueOne();

        testEqualToAnyHelper(value, new Object[]{createValueTwo(), new Object(), value}, true);
    }

    private void testEqualToAnyHelper(T value, Object[] others, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().equalToAny(others));

        verify(getMockVerification()).report(expected, AbstractCustomVerifier.MessageKeys.EQUAL_TO_ANY, (Object) others);
    }

    @Test
    public void testHashedAsWithMatch() {
        T value = createValueOne();

        testHashedAsHelper(value, value.hashCode(), true);
    }

    @Test
    public void testHashedAsWithNoMatch() {
        T value = createValueOne();

        testHashedAsHelper(value, value.hashCode() + 1, false);
    }

    @Test
    public void testHashedAsWithNullValue() {
        testHashedAsHelper(null, 123, false);
    }

    private void testHashedAsHelper(T value, int hashCode, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().hashedAs(hashCode));

        verify(getMockVerification()).report(eq(expected), eq(AbstractCustomVerifier.MessageKeys.HASHED_AS), getArgsCaptor().capture());

        assertEquals("Passes hash code for message formatting", hashCode, getArgsCaptor().getValue());
    }

    @Test
    public void testInstanceOfWithObjectClass() {
        testInstanceOfHelper(createValueOne(), Object.class, true);
    }

    @Test
    public void testInstanceOfWithNoMatch() {
        testInstanceOfHelper(createValueOne(), UnrelatedClass.class, false);
    }

    @Test
    public void testInstanceOfWithSameClass() {
        testInstanceOfHelper(createValueOne(), getValueClass(), true);
    }

    @Test
    public void testInstanceOfWithSuperClass() {
        testInstanceOfHelper(createValueOne(), getParentClass(), true);
    }

    @Test
    public void testInstanceOfWithNullClass() {
        testInstanceOfHelper(createValueOne(), null, false);
    }

    @Test
    public void testInstanceOfWithNullValue() {
        testInstanceOfHelper(null, getValueClass(), false);
    }

    private void testInstanceOfHelper(T value, Class<?> cls, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().instanceOf(cls));

        verify(getMockVerification()).report(eq(expected), eq(AbstractCustomVerifier.MessageKeys.INSTANCE_OF), getArgsCaptor().capture());

        assertEquals("Passes class for message formatting", cls, getArgsCaptor().getValue());
    }

    @Test
    public void testInstanceOfAllWithObjectClass() {
        testInstanceOfAllHelper(createValueOne(), new Class<?>[]{Object.class}, true);
    }

    @Test
    public void testInstanceOfAllWithNoClasses() {
        testInstanceOfAllHelper(createValueOne(), new Class<?>[0], true);
    }

    @Test
    public void testInstanceOfAllWithNoMatches() {
        testInstanceOfAllHelper(createValueOne(), new Class<?>[]{UnrelatedClass.class}, false);
    }

    @Test
    public void testInstanceOfAllWithNullClass() {
        testInstanceOfAllHelper(createValueOne(), new Class<?>[]{getValueClass(), null}, false);
    }

    @Test
    public void testInstanceOfAllWithNullClasses() {
        testInstanceOfAllHelper(createValueOne(), null, true);
    }

    @Test
    public void testInstanceOfAllWithNullValue() {
        testInstanceOfAllHelper(null, new Class<?>[0], false);
    }

    @Test
    public void testInstanceOfAllWithSameClass() {
        testInstanceOfAllHelper(createValueOne(), new Class<?>[]{getValueClass(), Object.class}, true);
    }

    @Test
    public void testInstanceOfAllWithSomeMatches() {
        testInstanceOfAllHelper(createValueOne(), new Class<?>[]{getValueClass(), UnrelatedClass.class}, false);
    }

    @Test
    public void testInstanceOfAllWithSuperClass() {
        testInstanceOfAllHelper(createValueOne(), new Class<?>[]{getParentClass(), Object.class}, true);
    }

    private void testInstanceOfAllHelper(T value, Class<?>[] classes, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().instanceOfAll(classes));

        verify(getMockVerification()).report(expected, AbstractCustomVerifier.MessageKeys.INSTANCE_OF_ALL, (Object) classes);
    }

    @Test
    public void testInstanceOfAnyWithObjectClass() {
        testInstanceOfAnyHelper(createValueOne(), new Class<?>[]{UnrelatedClass.class, Object.class}, true);
    }

    @Test
    public void testInstanceOfAnyWithNoClasses() {
        testInstanceOfAnyHelper(createValueOne(), new Class<?>[0], false);
    }

    @Test
    public void testInstanceOfAnyWithNoMatches() {
        testInstanceOfAnyHelper(createValueOne(), new Class<?>[]{UnrelatedClass.class}, false);
    }

    @Test
    public void testInstanceOfAnyWithNullClass() {
        testInstanceOfAnyHelper(createValueOne(), new Class<?>[]{null, getValueClass()}, true);
    }

    @Test
    public void testInstanceOfAnyWithNullClassAndNoMatches() {
        testInstanceOfAnyHelper(createValueOne(), new Class<?>[]{null, UnrelatedClass.class}, false);
    }

    @Test
    public void testInstanceOfAnyWithNullClasses() {
        testInstanceOfAnyHelper(createValueOne(), null, false);
    }

    @Test
    public void testInstanceOfAnyWithNullValue() {
        testInstanceOfAnyHelper(null, new Class<?>[]{getValueClass(), getParentClass(), Object.class}, false);
    }

    @Test
    public void testInstanceOfAnyWithSameClass() {
        testInstanceOfAnyHelper(createValueOne(), new Class<?>[]{UnrelatedClass.class, getValueClass()}, true);
    }

    @Test
    public void testInstanceOfAnyWithSuperClass() {
        testInstanceOfAnyHelper(createValueOne(), new Class<?>[]{UnrelatedClass.class, getParentClass()}, true);
    }

    private void testInstanceOfAnyHelper(T value, Class<?>[] classes, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().instanceOfAny(classes));

        verify(getMockVerification()).report(expected, AbstractCustomVerifier.MessageKeys.INSTANCE_OF_ANY, (Object) classes);
    }

    @Test
    public void testNot() {
        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().not());

        verify(getMockVerification()).setNegated(true);
    }

    @Test
    public void testNotWhenAlreadyNegated() {
        when(getMockVerification().isNegated()).thenReturn(true);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().not());

        verify(getMockVerification()).setNegated(false);
    }

    @Test
    public void testNulledWithNonNullValue() {
        testNulledHelper(createValueOne(), false);
    }

    @Test
    public void testNulledWithNullValue() {
        testNulledHelper(null, true);
    }

    private void testNulledHelper(T value, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().nulled());

        verify(getMockVerification()).report(expected, AbstractCustomVerifier.MessageKeys.NULLED);
    }

    @Test
    public void testSameAsWithDifferentInstance() {
        testSameAsHelper(createValueOne(), createValueTwo(), false);
    }

    @Test
    public void testSameAsWithEqualInstance() {
        testSameAsHelper(createValueOne(), createValueOne(), isEqualValueSame());
    }

    @Test
    public void testSameAsWithNullOther() {
        testSameAsHelper(createValueOne(), null, false);
    }

    @Test
    public void testSameAsWithNullValue() {
        testSameAsHelper(null, createValueOne(), false);
    }

    @Test
    public void testSameAsWithNullValueAndNullOther() {
        testSameAsHelper(null, null, true);
    }

    @Test
    public void testSameAsWithSameInstance() {
        T value = createValueOne();

        testSameAsHelper(value, value, true);
    }

    private void testSameAsHelper(T value, Object other, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().sameAs(other));

        verify(getMockVerification()).report(eq(expected), eq(AbstractCustomVerifier.MessageKeys.SAME_AS), getArgsCaptor().capture());

        assertSame("Passes other for message formatting", other, getArgsCaptor().getValue());
    }

    @Test
    public void testSameAsWithNameAndDifferentInstance() {
        testSameAsHelper(createValueOne(), createValueTwo(), "other", false);
    }

    @Test
    public void testSameAsWithNameAndEqualInstance() {
        testSameAsHelper(createValueOne(), createValueOne(), "other", isEqualValueSame());
    }

    @Test
    public void testSameAsWithNameAndNullOther() {
        testSameAsHelper(createValueOne(), null, "other", false);
    }

    @Test
    public void testSameAsWithNameAndNullValue() {
        testSameAsHelper(null, createValueOne(), "other", false);
    }

    @Test
    public void testSameAsWithNameAndNullValueAndNullOther() {
        testSameAsHelper(null, null, "other", true);
    }

    @Test
    public void testSameAsWithNameAndSameInstance() {
        T value = createValueOne();

        testSameAsHelper(value, value, "other", true);
    }

    private void testSameAsHelper(T value, Object other, Object name, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().sameAs(other, name));

        verify(getMockVerification()).report(eq(expected), eq(AbstractCustomVerifier.MessageKeys.SAME_AS), getArgsCaptor().capture());

        assertSame("Passes name for message formatting", name, getArgsCaptor().getValue());
    }

    @Test
    public void testSameAsAnyWithDifferentInstance() {
        testSameAsAnyHelper(createValueOne(), new Object[]{createValueTwo(), new Object()}, false);
    }

    @Test
    public void testSameAsAnyWithEqualInstance() {
        testSameAsAnyHelper(createValueOne(), new Object[]{createValueTwo(), new Object(), createValueOne()}, isEqualValueSame());
    }

    @Test
    public void testSameAsAnyWithNoOthers() {
        testSameAsAnyHelper(createValueOne(), new Object[0], false);
    }

    @Test
    public void testSameAsAnyWithNullOther() {
        testSameAsAnyHelper(createValueOne(), new Object[]{null}, false);
    }

    @Test
    public void testSameAsAnyWithNullOthers() {
        testSameAsAnyHelper(createValueOne(), null, false);
    }

    @Test
    public void testSameAsAnyWithNullValue() {
        testSameAsAnyHelper(null, new Object[]{createValueOne(), createValueTwo(), new Object()}, false);
    }

    @Test
    public void testSameAsAnyWithNullValueAndNullOther() {
        testSameAsAnyHelper(null, new Object[]{null}, true);
    }

    @Test
    public void testSameAsAnyWithNullValueAndNullOthers() {
        testSameAsAnyHelper(null, null, false);
    }

    @Test
    public void testSameAsAnyWithSameInstance() {
        T value = createValueOne();

        testSameAsAnyHelper(value, new Object[]{createValueTwo(), new Object(), value}, true);
    }

    private void testSameAsAnyHelper(T value, Object[] others, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().sameAsAny(others));

        verify(getMockVerification()).report(expected, AbstractCustomVerifier.MessageKeys.SAME_AS_ANY, (Object) others);
    }

    @Test
    public void testThatWhenFalse() {
        testThatHelper(false);
    }

    @Test
    public void testThatWhenTrue() {
        testThatHelper(true);
    }

    @Test
    public void testThatThrowsIfAssertionIsNull() {
        thrown.expect(VerifierException.class);
        thrown.expectMessage("assertion must not be null: null");

        getCustomVerifier().that(null);
    }

    private void testThatHelper(boolean expected) {
        T value = createValueOne();
        setValue(value);

        when(mockAssertion.verify(value)).thenReturn(expected);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().that(mockAssertion));

        verify(mockAssertion).verify(value);
        verify(getMockVerification()).report(eq(expected), isNull(String.class));
    }

    @Test
    public void testThatWithMessageWhenFalse() {
        testThatHelper(false, "foo {0}", new Object[]{"bar"});
    }

    @Test
    public void testThatWithMessageWhenTrue() {
        testThatHelper(true, "foo {0}", new Object[]{"bar"});
    }

    @Test
    public void testThatWithMessageThrowsIfAssertionIsNull() {
        thrown.expect(VerifierException.class);
        thrown.expectMessage("assertion must not be null: null");

        getCustomVerifier().that(null, "foo {0}", "bar");
    }

    private void testThatHelper(boolean expected, String message, Object[] args) {
        T value = createValueOne();
        setValue(value);

        when(mockAssertion.verify(value)).thenReturn(expected);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().that(mockAssertion, message, args));

        verify(mockAssertion).verify(value);
        verify(getMockVerification()).report(eq(expected), eq(message), getArgsCaptor().capture());

        assertEquals("Passes args for message formatting", Arrays.asList(args), getArgsCaptor().getAllValues());
    }

    @Test
    public void testThatWithMessageKeyWhenFalse() {
        testThatHelper(false, () -> "foo", new Object[]{"bar"});
    }

    @Test
    public void testThatWithMessageKeyWhenTrue() {
        testThatHelper(true, () -> "foo", new Object[]{"bar"});
    }

    @Test
    public void testThatWithMessageKeyThrowsIfAssertionIsNull() {
        thrown.expect(VerifierException.class);
        thrown.expectMessage("assertion must not be null: null");

        getCustomVerifier().that(null, () -> "foo", "bar");
    }

    private void testThatHelper(boolean expected, MessageKey key, Object[] args) {
        T value = createValueOne();
        setValue(value);

        when(mockAssertion.verify(value)).thenReturn(expected);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().that(mockAssertion, key, args));

        verify(mockAssertion).verify(value);
        verify(getMockVerification()).report(eq(expected), eq(key), getArgsCaptor().capture());

        assertEquals("Passes args for message formatting", Arrays.asList(args), getArgsCaptor().getAllValues());
    }

    @Test
    public void testValue() {
        T value = createValueOne();

        setValue(value);

        assertSame("Verification value is returned", value, getCustomVerifier().value());
    }

    @Test
    public void testVerification() {
        assertSame("Verification field is correct", getMockVerification(), getCustomVerifier().verification());
    }

    /**
     * <p>
     * Creates a value to be used for testing.
     * </p>
     * <p>
     * This should always be different from {@link #createValueTwo()} to enable proper testing of equality methods.
     * </p>
     *
     * @return A value.
     */
    protected abstract T createValueOne();

    /**
     * <p>
     * Creates another value to be used for testing.
     * </p>
     * <p>
     * This should always be different from {@link #createValueOne()} to enable proper testing of equality methods.
     * </p>
     *
     * @return Another value.
     */
    protected abstract T createValueTwo();

    /**
     * <p>
     * Returns whether any values that are considered equal (i.e. {@link Object#equals(Object)}) will also be the same
     * (i.e. {@code ==}).
     * </p>
     * <p>
     * By default this method returns {@literal false}, which is appropriate in most cases, however, it can be
     * overridden where required (e.g. classes, enums, primitives).
     * </p>
     *
     * @return {@literal true} if equal values are expected to be the same; otherwise {@literal false}.
     */
    protected boolean isEqualValueSame() {
        return false;
    }

    /**
     * <p>
     * Returns a parent class of the value class for testing inheritance methods.
     * </p>
     * <p>
     * If the value class does not have a parent class, simply use {@code Object.class}.
     * </p>
     *
     * @return The parent class.
     */
    protected abstract Class<?> getParentClass();

    /**
     * <p>
     * Returns the class of the value for testing inheritance methods.
     * </p>
     *
     * @return The value class.
     */
    protected abstract Class<?> getValueClass();

    private static class UnrelatedClass {
    }
}
