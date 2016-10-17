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

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import org.junit.Test;

import io.skelp.verifier.message.ArrayFormatter;
import io.skelp.verifier.verification.Verification;

/**
 * Tests for the {@link AbstractCustomVerifier} class.
 *
 * @author Alasdair Mercer
 */
public class AbstractCustomVerifierTest extends AbstractCustomVerifierTestBase<AbstractCustomVerifierTest.Child, AbstractCustomVerifierTest.AbstractCustomVerifierTestImpl> {

    @Test
    public void testEqualToWithDifferentInstance() {
        testEqualToHelper(new Child(123), new Child(321), false);
    }

    @Test
    public void testEqualToWithEqualInstance() {
        testEqualToHelper(new Child(123), new Child(123), true);
    }

    @Test
    public void testEqualToWithNullOther() {
        testEqualToHelper(new Child(), null, false);
    }

    @Test
    public void testEqualToWithNullValue() {
        testEqualToHelper(null, new Child(), false);
    }

    @Test
    public void testEqualToWithNullValueAndNullOther() {
        testEqualToHelper(null, null, true);
    }

    @Test
    public void testEqualToWithSameInstance() {
        Child value = new Child();

        testEqualToHelper(value, value, true);
    }

    private void testEqualToHelper(Child value, Object other, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().equalTo(other));

        verify(getMockVerification()).check(eq(expected), eq("be equal to '%s'"), getArgsCaptor().capture());

        assertSame("Passes other for message formatting", other, getArgsCaptor().getValue());
    }

    @Test
    public void testEqualToWithNameAndDifferentInstance() {
        testEqualToHelper(new Child(123), new Child(321), "other", false);
    }

    @Test
    public void testEqualToWithNameAndEqualInstance() {
        testEqualToHelper(new Child(123), new Child(123), "other", true);
    }

    @Test
    public void testEqualToWithNameAndNullOther() {
        testEqualToHelper(new Child(), null, "other", false);
    }

    @Test
    public void testEqualToWithNameAndNullValue() {
        testEqualToHelper(null, new Child(), "other", false);
    }

    @Test
    public void testEqualToWithNameAndNullValueAndNullOther() {
        testEqualToHelper(null, null, "other", true);
    }

    @Test
    public void testEqualToWithNameAndSameInstance() {
        Child value = new Child();

        testEqualToHelper(value, value, "other", true);
    }

    private void testEqualToHelper(Child value, Object other, Object name, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().equalTo(other, name));

        verify(getMockVerification()).check(eq(expected), eq("be equal to '%s'"), getArgsCaptor().capture());

        assertSame("Passes name for message formatting", name, getArgsCaptor().getValue());
    }

    @Test
    public void testEqualToAnyWithDifferentInstance() {
        testEqualToAnyHelper(new Child(123), new Object[]{new Child(987), new Child(654), new Child(321)}, false);
    }

    @Test
    public void testEqualToAnyWithEqualInstance() {
        testEqualToAnyHelper(new Child(123), new Object[]{new Child(456), new Child(789), new Child(123)}, true);
    }

    @Test
    public void testEqualToAnyWithNullOther() {
        testEqualToAnyHelper(new Child(), new Object[]{null}, false);
    }

    @Test
    public void testEqualToAnyWithNullOthers() {
        testEqualToAnyHelper(new Child(), null, false);
    }

    @Test
    public void testEqualToAnyWithNullValue() {
        testEqualToAnyHelper(null, new Object[]{new Child(), new Child(), new Child()}, false);
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
        Child value = new Child();

        testEqualToAnyHelper(value, new Object[]{new Child(), new Child(), value}, true);
    }

    private void testEqualToAnyHelper(Child value, Object[] others, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().equalToAny(others));

        verify(getMockVerification()).check(eq(expected), eq("be equal to any %s"), getArgsCaptor().capture());

        Object capturedArg = getArgsCaptor().getValue();
        assertTrue("Passes array formatter for message formatting", capturedArg instanceof ArrayFormatter);
        @SuppressWarnings("unchecked")
        ArrayFormatter<Class<?>> arrayFormatter = (ArrayFormatter<Class<?>>) capturedArg;
        assertArrayEquals("Array formatter contains passed classes", others, arrayFormatter.getArray());
    }

    @Test
    public void testHashedAsWithMatch() {
        testHashedAsHelper(new Child(123), 123, true);
    }

    @Test
    public void testHashedAsWithNoMatch() {
        testHashedAsHelper(new Child(123), 321, false);
    }

    @Test
    public void testHashedAsWithNullValue() {
        testHashedAsHelper(null, 123, false);
    }

    private void testHashedAsHelper(Child value, int hashCode, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().hashedAs(hashCode));

        verify(getMockVerification()).check(eq(expected), eq("have hash code '%d'"), getArgsCaptor().capture());

        assertEquals("Passes hash code for message formatting", hashCode, getArgsCaptor().getValue());
    }

    @Test
    public void testInstanceOfWithObjectClass() {
        testInstanceOfHelper(new Child(), Object.class, true);
    }

    @Test
    public void testInstanceOfWithNoMatch() {
        testInstanceOfHelper(new Child(), Collection.class, false);
    }

    @Test
    public void testInstanceOfWithSameClass() {
        testInstanceOfHelper(new Child(), Child.class, true);
    }

    @Test
    public void testInstanceOfWithSuperClass() {
        testInstanceOfHelper(new Child(), Parent.class, true);
    }

    @Test
    public void testInstanceOfWithNullClass() {
        testInstanceOfHelper(new Child(), null, false);
    }

    @Test
    public void testInstanceOfWithNullValue() {
        testInstanceOfHelper(null, Child.class, false);
    }

    private void testInstanceOfHelper(Child value, Class<?> cls, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().instanceOf(cls));

        verify(getMockVerification()).check(eq(expected), eq("be an instance of '%s'"), getArgsCaptor().capture());

        assertEquals("Passes class for message formatting", cls, getArgsCaptor().getValue());
    }

    @Test
    public void testInstanceAnyOfWithObjectClass() {
        testInstanceOfAnyHelper(new Child(), new Class<?>[]{Collection.class, Map.class, Object.class}, true);
    }

    @Test
    public void testInstanceOfAnyWithNoMatches() {
        testInstanceOfAnyHelper(null, new Class<?>[]{Collection.class, Map.class}, false);
    }

    @Test
    public void testInstanceOfAnyWithSameClass() {
        testInstanceOfAnyHelper(new Child(), new Class<?>[]{Collection.class, Map.class, Child.class}, true);
    }

    @Test
    public void testInstanceOfAnyWithSuperClass() {
        testInstanceOfAnyHelper(new Child(), new Class<?>[]{Collection.class, Map.class, Parent.class}, true);
    }

    @Test
    public void testInstanceOfAnyWithNullClass() {
        testInstanceOfAnyHelper(new Child(), new Class<?>[]{null, Child.class}, true);
    }

    @Test
    public void testInstanceOfAnyWithNullClassAndNoMatches() {
        testInstanceOfAnyHelper(new Child(), new Class<?>[]{null, Collection.class}, false);
    }

    @Test
    public void testInstanceOfAnyWithNullClasses() {
        testInstanceOfAnyHelper(new Child(), null, false);
    }

    @Test
    public void testInstanceOfAnyWithNullValue() {
        testInstanceOfAnyHelper(null, new Class<?>[]{Child.class, Parent.class, Object.class}, false);
    }

    private void testInstanceOfAnyHelper(Child value, Class<?>[] classes, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().instanceOfAny(classes));

        verify(getMockVerification()).check(eq(expected), eq("be an instance of any %s"), getArgsCaptor().capture());

        Object capturedArg = getArgsCaptor().getValue();
        assertTrue("Passes array formatter for message formatting", capturedArg instanceof ArrayFormatter);
        @SuppressWarnings("unchecked")
        ArrayFormatter<Class<?>> arrayFormatter = (ArrayFormatter<Class<?>>) capturedArg;
        assertArrayEquals("Array formatter contains passed classes", classes, arrayFormatter.getArray());
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
        testNulledHelper(new Child(), false);
    }

    @Test
    public void testNulledWithNullValue() {
        testNulledHelper(null, true);
    }

    private void testNulledHelper(Child value, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().nulled());

        verify(getMockVerification()).check(expected, "be null");
    }

    @Test
    public void testSameAsWithDifferentInstance() {
        testSameAsHelper(new Child(), new Child(), false);
    }

    @Test
    public void testSameAsWithEqualInstance() {
        testSameAsHelper(new Child(123), new Child(123), false);
    }

    @Test
    public void testSameAsWithNullOther() {
        testSameAsHelper(new Child(), null, false);
    }

    @Test
    public void testSameAsWithNullValue() {
        testSameAsHelper(null, new Child(), false);
    }

    @Test
    public void testSameAsWithNullValueAndNullOther() {
        testSameAsHelper(null, null, true);
    }

    @Test
    public void testSameAsWithSameInstance() {
        Child value = new Child();

        testSameAsHelper(value, value, true);
    }

    private void testSameAsHelper(Child value, Object other, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().sameAs(other));

        verify(getMockVerification()).check(eq(expected), eq("be same as '%s'"), getArgsCaptor().capture());

        assertSame("Passes other for message formatting", other, getArgsCaptor().getValue());
    }

    @Test
    public void testSameAsWithNameAndDifferentInstance() {
        testSameAsHelper(new Child(), new Child(), "other", false);
    }

    @Test
    public void testSameAsWithNameAndEqualInstance() {
        testSameAsHelper(new Child(123), new Child(123), "other", false);
    }

    @Test
    public void testSameAsWithNameAndNullOther() {
        testSameAsHelper(new Child(), null, "other", false);
    }

    @Test
    public void testSameAsWithNameAndNullValue() {
        testSameAsHelper(null, new Child(), "other", false);
    }

    @Test
    public void testSameAsWithNameAndNullValueAndNullOther() {
        testSameAsHelper(null, null, "other", true);
    }

    @Test
    public void testSameAsWithNameAndSameInstance() {
        Child value = new Child();

        testSameAsHelper(value, value, "other", true);
    }

    private void testSameAsHelper(Child value, Object other, Object name, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().sameAs(other, name));

        verify(getMockVerification()).check(eq(expected), eq("be same as '%s'"), getArgsCaptor().capture());

        assertSame("Passes name for message formatting", name, getArgsCaptor().getValue());
    }

    @Test
    public void testSameAsAnyWithDifferentInstance() {
        testSameAsAnyHelper(new Child(), new Object[]{new Child(), new Child(), new Child()}, false);
    }

    @Test
    public void testSameAsAnyWithEqualInstance() {
        testSameAsAnyHelper(new Child(123), new Object[]{new Child(456), new Child(789), new Child(123)}, false);
    }

    @Test
    public void testSameAsAnyWithNullOther() {
        testSameAsAnyHelper(new Child(), new Object[]{null}, false);
    }

    @Test
    public void testSameAsAnyWithNullOthers() {
        testSameAsAnyHelper(new Child(), null, false);
    }

    @Test
    public void testSameAsAnyWithNullValue() {
        testSameAsAnyHelper(null, new Object[]{new Child(), new Child(), new Child()}, false);
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
        Child value = new Child();

        testSameAsAnyHelper(value, new Object[]{new Child(), new Child(), value}, true);
    }

    private void testSameAsAnyHelper(Child value, Object[] others, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().sameAsAny(others));

        verify(getMockVerification()).check(eq(expected), eq("be same as any %s"), getArgsCaptor().capture());

        Object capturedArg = getArgsCaptor().getValue();
        assertTrue("Passes array formatter for message formatting", capturedArg instanceof ArrayFormatter);
        @SuppressWarnings("unchecked")
        ArrayFormatter<Class<?>> arrayFormatter = (ArrayFormatter<Class<?>>) capturedArg;
        assertArrayEquals("Array formatter contains passed classes", others, arrayFormatter.getArray());
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
        Child value = new Child();
        setValue(value);

        @SuppressWarnings("unchecked")
        VerifierAssertion<Child> mockAssertion = (VerifierAssertion<Child>) mock(VerifierAssertion.class);
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
        Child value = new Child();
        setValue(value);

        @SuppressWarnings("unchecked")
        VerifierAssertion<Child> mockAssertion = (VerifierAssertion<Child>) mock(VerifierAssertion.class);
        when(mockAssertion.verify(value)).thenReturn(expected);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().that(mockAssertion, message, args));

        verify(mockAssertion).verify(value);
        verify(getMockVerification()).check(eq(expected), eq(message), getArgsCaptor().capture());

        assertEquals("Passes args for message formatting", Arrays.asList(args), getArgsCaptor().getAllValues());
    }

    @Override
    protected AbstractCustomVerifierTestImpl createCustomVerifier() {
        return new AbstractCustomVerifierTestImpl(getMockVerification());
    }

    static class AbstractCustomVerifierTestImpl extends AbstractCustomVerifier<Child, AbstractCustomVerifierTestImpl> {

        AbstractCustomVerifierTestImpl(Verification<Child> verification) {
            super(verification);
        }
    }

    static class Parent {
    }

    static class Child extends Parent {

        final Integer id;

        Child() {
            this(null);
        }

        Child(Integer id) {
            this.id = id;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (obj == this) {
                return true;
            }
            if (obj.getClass() != getClass()) {
                return false;
            }

            Child other = (Child) obj;
            return id == null ? other.id == null : id.equals(other.id);
        }

        @Override
        public int hashCode() {
            return id == null ? super.hashCode() : id.hashCode();
        }

        @Override
        public String toString() {
            return id == null ? super.toString() : id.toString();
        }
    }
}
