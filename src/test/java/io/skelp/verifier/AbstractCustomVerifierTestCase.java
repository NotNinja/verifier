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
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import org.junit.Test;

/**
 * Test case for {@link AbstractCustomVerifier} implementation classes.
 *
 * @param <T>
 *         the value type for the {@link AbstractCustomVerifier} being tested
 * @param <V>
 *         the type of the {@link AbstractCustomVerifier} being tested
 * @author Alasdair Mercer
 */
public abstract class AbstractCustomVerifierTestCase<T, V extends AbstractCustomVerifier<T, V>> extends CustomVerifierTestCaseBase<T, V> {

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

        verify(getMockVerification()).check(eq(expected), eq("be equal to '%s'"), getArgsCaptor().capture());

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

        verify(getMockVerification()).check(eq(expected), eq("be equal to '%s'"), getArgsCaptor().capture());

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

        verify(getMockVerification()).check(eq(expected), eq("be equal to any %s"), getArgsCaptor().capture());

        assertArrayFormatter(getArgsCaptor().getValue(), others);
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

        verify(getMockVerification()).check(eq(expected), eq("have hash code '%d'"), getArgsCaptor().capture());

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

        verify(getMockVerification()).check(eq(expected), eq("be an instance of '%s'"), getArgsCaptor().capture());

        assertEquals("Passes class for message formatting", cls, getArgsCaptor().getValue());
    }

    @Test
    public void testInstanceAnyOfWithObjectClass() {
        testInstanceOfAnyHelper(createValueOne(), new Class<?>[]{UnrelatedClass.class, Object.class}, true);
    }

    @Test
    public void testInstanceOfAnyWithNoMatches() {
        testInstanceOfAnyHelper(createValueOne(), new Class<?>[]{UnrelatedClass.class}, false);
    }

    @Test
    public void testInstanceOfAnyWithSameClass() {
        testInstanceOfAnyHelper(createValueOne(), new Class<?>[]{UnrelatedClass.class, getValueClass()}, true);
    }

    @Test
    public void testInstanceOfAnyWithSuperClass() {
        testInstanceOfAnyHelper(createValueOne(), new Class<?>[]{UnrelatedClass.class, getParentClass()}, true);
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

    private void testInstanceOfAnyHelper(T value, Class<?>[] classes, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().instanceOfAny(classes));

        verify(getMockVerification()).check(eq(expected), eq("be an instance of any %s"), getArgsCaptor().capture());

        assertArrayFormatter(getArgsCaptor().getValue(), classes);
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

        verify(getMockVerification()).check(expected, "be null");
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

        verify(getMockVerification()).check(eq(expected), eq("be same as '%s'"), getArgsCaptor().capture());

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

        verify(getMockVerification()).check(eq(expected), eq("be same as '%s'"), getArgsCaptor().capture());

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

        verify(getMockVerification()).check(eq(expected), eq("be same as any %s"), getArgsCaptor().capture());

        assertArrayFormatter(getArgsCaptor().getValue(), others);
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
        thrown.expectMessage("assertion must not be null");

        getCustomVerifier().that(null);
    }

    private void testThatHelper(boolean expected) {
        T value = createValueOne();
        setValue(value);

        @SuppressWarnings("unchecked")
        VerifierAssertion<T> mockAssertion = (VerifierAssertion<T>) mock(VerifierAssertion.class);
        when(mockAssertion.verify(value)).thenReturn(expected);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().that(mockAssertion));

        verify(mockAssertion).verify(value);
        verify(getMockVerification()).check(eq(expected), isNull(String.class));
    }

    @Test
    public void testThatWithMessageWhenFalse() {
        testThatHelper(false, "foo %s", new Object[]{"bar"});
    }

    @Test
    public void testThatWithMessageWhenTrue() {
        testThatHelper(true, "foo %s", new Object[]{"bar"});
    }

    @Test
    public void testThatWithMessageThrowsIfAssertionIsNull() {
        thrown.expect(VerifierException.class);
        thrown.expectMessage("assertion must not be null");

        getCustomVerifier().that(null, "foo %s", "bar");
    }

    private void testThatHelper(boolean expected, String message, Object[] args) {
        T value = createValueOne();
        setValue(value);

        @SuppressWarnings("unchecked")
        VerifierAssertion<T> mockAssertion = (VerifierAssertion<T>) mock(VerifierAssertion.class);
        when(mockAssertion.verify(value)).thenReturn(expected);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().that(mockAssertion, message, args));

        verify(mockAssertion).verify(value);
        verify(getMockVerification()).check(eq(expected), eq(message), getArgsCaptor().capture());

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
     * Creates a value to be used for testing.
     * <p>
     * This should always be different from {@link #createValueTwo()} to enable proper testing of equality methods.
     *
     * @return A value.
     */
    protected abstract T createValueOne();

    /**
     * Creates another value to be used for testing.
     * <p>
     * This should always be different from {@link #createValueOne()} to enable proper testing of equality methods.
     *
     * @return Another value.
     */
    protected abstract T createValueTwo();

    /**
     * Returns whether any values that are considered equal (i.e. {@link Object#equals(Object)}) will also be the same
     * (i.e. {@code ==}).
     * <p>
     * By default this method returns {@literal false}, which is appropriate in most cases, however, it can be
     * overridden where required (e.g. classes, enums, primitives).
     *
     * @return {@literal true} if equal values are expected to be the same; otherwise {@literal false}.
     */
    protected boolean isEqualValueSame() {
        return false;
    }

    /**
     * Returns a parent class of the value class for testing inheritance methods.
     * <p>
     * If the value class does not have a parent class, simply use {@code Object.class}.
     *
     * @return The parent class.
     */
    protected abstract Class<?> getParentClass();

    /**
     * Returns the class of the value for testing inheritance methods.
     *
     * @return The value class.
     */
    protected abstract Class<?> getValueClass();

    private static class UnrelatedClass {
    }
}
