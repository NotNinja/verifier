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
package org.notninja.verifier.type.base;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import org.notninja.verifier.CustomVerifierTestCaseBase;
import org.notninja.verifier.VerifierAssertion;
import org.notninja.verifier.VerifierException;
import org.notninja.verifier.message.MessageKey;

/**
 * <p>
 * Test case for {@link BaseCollectionVerifier} implementation classes.
 * </p>
 *
 * @param <E>
 *         the element type for the {@link BaseCollectionVerifier} being tested
 * @param <T>
 *         the value type for the {@link BaseCollectionVerifier} being tested
 * @param <V>
 *         the type of the {@link BaseCollectionVerifier} being tested
 * @author Alasdair Mercer
 */
@RunWith(MockitoJUnitRunner.class)
public abstract class BaseCollectionVerifierTestCase<E, T, V extends BaseCollectionVerifier<E, T, V>> extends CustomVerifierTestCaseBase<T, V> {

    @Mock
    private VerifierAssertion<E> mockAssertion;

    @Test
    public void testContainWhenElementIsNotPresentInValue() {
        testContainHelper(createFullValue(), getMissingElement(), false);
    }

    @Test
    public void testContainWhenElementIsPresentInValue() {
        testContainHelper(createFullValue(), getExistingElement(), true);
    }

    @Test
    public void testContainWithNullElement() {
        testContainHelper(createValueWithNull(), null, true);
    }

    @Test
    public void testContainWithNullValue() {
        testContainHelper(null, getExistingElement(), false);
    }

    private void testContainHelper(T value, E element, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().contain(element));

        verify(getMockVerification()).report(eq(expected), eq(BaseCollectionVerifier.MessageKeys.CONTAIN), getArgsCaptor().capture());

        assertSame("Passes element for message formatting", element, getArgsCaptor().getValue());
    }

    @Test
    public void testContainAllWhenNoElements() {
        testContainAllHelper(createFullValue(), createEmptyArray(getElementClass()), true);
    }

    @Test
    public void testContainAllWhenNoElementsAndValueIsEmpty() {
        testContainAllHelper(createEmptyValue(), createEmptyArray(getElementClass()), true);
    }

    @Test
    public void testContainAllWhenElementIsNull() {
        testContainAllHelper(createFullValue(), createArray((E) null), false);
    }

    @Test
    public void testContainAllWhenElementsIsNull() {
        testContainAllHelper(createFullValue(), null, true);
    }

    @Test
    public void testContainAllWhenValueContainsAllElements() {
        testContainAllHelper(createFullValue(), createArray(getExistingElement(), getExistingElement(), getExistingElement()), true);
    }

    @Test
    public void testContainAllWhenValueContainsSomeElements() {
        testContainAllHelper(createFullValue(), createArray(getExistingElement(), getMissingElement(), getMissingElement()), false);
    }

    @Test
    public void testContainAllWhenValueDoesNotContainElement() {
        testContainAllHelper(createFullValue(), createArray(getMissingElement(), getMissingElement(), getMissingElement()), false);
    }

    @Test
    public void testContainAllWhenValueIsEmpty() {
        testContainAllHelper(createEmptyValue(), createArray(getExistingElement()), false);
    }

    @Test
    public void testContainAllWhenValueIsNull() {
        testContainAllHelper(null, createEmptyArray(getElementClass()), false);
    }

    private void testContainAllHelper(T value, E[] elements, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().containAll(elements));

        verify(getMockVerification()).report(expected, BaseCollectionVerifier.MessageKeys.CONTAIN_ALL, (Object) elements);
    }

    @Test
    public void testContainAnyWhenNoElements() {
        testContainAnyHelper(createFullValue(), createEmptyArray(getElementClass()), false);
    }

    @Test
    public void testContainAnyWhenNoElementsAndValueIsEmpty() {
        testContainAnyHelper(createEmptyValue(), createEmptyArray(getElementClass()), false);
    }

    @Test
    public void testContainAnyWhenElementIsNull() {
        testContainAnyHelper(createFullValue(), createArray((E) null), false);
    }

    @Test
    public void testContainAnyWhenElementsIsNull() {
        testContainAnyHelper(createFullValue(), null, false);
    }

    @Test
    public void testContainAnyWhenValueContainsAllElements() {
        testContainAnyHelper(createFullValue(), createArray(getExistingElement(), getExistingElement(), getExistingElement()), true);
    }

    @Test
    public void testContainAnyWhenValueContainsSomeElements() {
        testContainAnyHelper(createFullValue(), createArray(getMissingElement(), getMissingElement(), getExistingElement()), true);
    }

    @Test
    public void testContainAnyWhenValueDoesNotContainElement() {
        testContainAnyHelper(createFullValue(), createArray(getMissingElement(), getMissingElement(), getMissingElement()), false);
    }

    @Test
    public void testContainAnyWhenValueIsEmpty() {
        testContainAnyHelper(createEmptyValue(), createArray(getExistingElement()), false);
    }

    @Test
    public void testContainAnyWhenValueIsNull() {
        testContainAnyHelper(null, createEmptyArray(getElementClass()), false);
    }

    private void testContainAnyHelper(T value, E[] elements, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().containAny(elements));

        verify(getMockVerification()).report(expected, BaseCollectionVerifier.MessageKeys.CONTAIN_ANY, (Object) elements);
    }

    @Test
    public void testEmptyWithEmptyValue() {
        testEmptyHelper(createEmptyValue(), true);
    }

    @Test
    public void testEmptyWithNonEmptyValue() {
        testEmptyHelper(createFullValue(), false);
    }

    @Test
    public void testEmptyWithNullValue() {
        testEmptyHelper(null, true);
    }

    private void testEmptyHelper(T value, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().empty());

        verify(getMockVerification()).report(expected, BaseCollectionVerifier.MessageKeys.EMPTY);
    }

    @Test
    public void testSizeOfWithEmptyValue() {
        testSizeOfHelper(createEmptyValue(), 0, true);
    }

    @Test
    public void testSizeOfWithEmptyValueAndIncorrectSize() {
        testSizeOfHelper(createEmptyValue(), 1, false);
    }

    @Test
    public void testSizeOfWithNonEmptyValue() {
        testSizeOfHelper(createFullValue(), getFullValueSize(), true);
    }

    @Test
    public void testSizeOfWithNonEmptyValueAndIncorrectSize() {
        testSizeOfHelper(createFullValue(), getFullValueSize() + 1, false);
    }

    @Test
    public void testSizeOfWithNullValue() {
        testSizeOfHelper(null, 0, true);
    }

    @Test
    public void testSizeOfWithNullValueAndIncorrectSize() {
        testSizeOfHelper(null, 1, false);
    }

    private void testSizeOfHelper(T value, int size, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().sizeOf(size));

        verify(getMockVerification()).report(eq(expected), eq(BaseCollectionVerifier.MessageKeys.SIZE_OF), getArgsCaptor().capture());

        assertSame("Passes size for message formatting", size, getArgsCaptor().getValue());
    }

    @Test
    public void testThatAllThrowsWhenAssertionIsNull() {
        thrown.expect(VerifierException.class);
        thrown.expectMessage("assertion must not be null: null");

        getCustomVerifier().thatAll(null);
    }

    @Test
    public void testThatAllWhenAssertionFailsForAllElements() {
        when(mockAssertion.verify(any(getElementClass()))).thenReturn(false);

        testThatAllHelper(createFullValue(), 1, false);
    }

    @Test
    public void testThatAllWhenAssertionFailsForSomeElements() {
        when(mockAssertion.verify(any(getElementClass()))).thenReturn(true);
        when(mockAssertion.verify(getExistingElement())).thenReturn(false);

        testThatAllHelper(createFullValue(), getFullValueSize(), false);
    }

    @Test
    public void testThatAllWhenAssertionPassesForAllElements() {
        when(mockAssertion.verify(any(getElementClass()))).thenReturn(true);

        testThatAllHelper(createFullValue(), getFullValueSize(), true);
    }

    @Test
    public void testThatAllWhenValueIsEmpty() {
        testThatAllHelper(createEmptyValue(), 0, true);
    }

    @Test
    public void testThatAllWhenValueIsNull() {
        testThatAllHelper(null, 0, true);
    }

    private void testThatAllHelper(T value, int assertionCalls, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().thatAll(mockAssertion));

        verify(getMockVerification()).report(expected, (String) null);
        verify(mockAssertion, times(assertionCalls)).verify(any(getElementClass()));
    }

    @Test
    public void testThatAllInternalThrowsWhenAssertionIsNull() {
        thrown.expect(VerifierException.class);
        thrown.expectMessage("assertion must not be null: null");

        getCustomVerifier().thatAllInternal(null);
    }

    @Test
    public void testThatAllInternalWhenAssertionFailsForAllElements() {
        when(mockAssertion.verify(any(getElementClass()))).thenReturn(false);

        testThatAllInternalHelper(createFullValue(), 1, false);
    }

    @Test
    public void testThatAllInternalWhenAssertionFailsForSomeElements() {
        when(mockAssertion.verify(any(getElementClass()))).thenReturn(true);
        when(mockAssertion.verify(getExistingElement())).thenReturn(false);

        testThatAllInternalHelper(createFullValue(), getFullValueSize(), false);
    }

    @Test
    public void testThatAllInternalWhenAssertionPassesForAllElements() {
        when(mockAssertion.verify(any(getElementClass()))).thenReturn(true);

        testThatAllInternalHelper(createFullValue(), getFullValueSize(), true);
    }

    @Test
    public void testThatAllInternalWhenValueIsEmpty() {
        testThatAllInternalHelper(createEmptyValue(), 0, true);
    }

    @Test
    public void testThatAllInternalWhenValueIsNull() {
        testThatAllInternalHelper(null, 0, true);
    }

    private void testThatAllInternalHelper(T value, int assertionCalls, boolean expected) {
        setValue(value);

        assertEquals("Matches elements correctly", expected, getCustomVerifier().thatAllInternal(mockAssertion));

        verify(getMockVerification(), never()).report(anyBoolean(), any(MessageKey.class), anyVararg());
        verify(getMockVerification(), never()).report(anyBoolean(), any(String.class), anyVararg());
        verify(mockAssertion, times(assertionCalls)).verify(any(getElementClass()));
    }

    @Test
    public void testThatAllWithMessageThrowsWhenAssertionIsNull() {
        thrown.expect(VerifierException.class);
        thrown.expectMessage("assertion must not be null: null");

        getCustomVerifier().thatAll(null, "foo {0}", "bar");
    }

    @Test
    public void testThatAllWithMessageWhenAssertionFailsForAllElements() {
        when(mockAssertion.verify(any(getElementClass()))).thenReturn(false);

        testThatAllHelper(createFullValue(), 1, false, "foo {0}", new Object[]{"bar"});
    }

    @Test
    public void testThatAllWithMessageWhenAssertionFailsForSomeElements() {
        when(mockAssertion.verify(any(getElementClass()))).thenReturn(true);
        when(mockAssertion.verify(getExistingElement())).thenReturn(false);

        testThatAllHelper(createFullValue(), getFullValueSize(), false, "foo {0}", new Object[]{"bar"});
    }

    @Test
    public void testThatAllWithMessageWhenAssertionPassesForAllElements() {
        when(mockAssertion.verify(any(getElementClass()))).thenReturn(true);

        testThatAllHelper(createFullValue(), getFullValueSize(), true, "foo {0}", new Object[]{"bar"});
    }

    @Test
    public void testThatAllWithMessageWhenValueIsEmpty() {
        testThatAllHelper(createEmptyValue(), 0, true, "foo {0}", new Object[]{"bar"});
    }

    @Test
    public void testThatAllWithMessageWhenValueIsNull() {
        testThatAllHelper(null, 0, true, "foo {0}", new Object[]{"bar"});
    }

    private void testThatAllHelper(T value, int assertionCalls, boolean expected, String message, Object[] args) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().thatAll(mockAssertion, message, args));

        verify(getMockVerification()).report(eq(expected), eq(message), getArgsCaptor().capture());
        verify(mockAssertion, times(assertionCalls)).verify(any(getElementClass()));

        assertEquals("Passes args for message formatting", Arrays.asList(args), getArgsCaptor().getAllValues());
    }

    @Test
    public void testThatAllWithMessageKeyThrowsWhenAssertionIsNull() {
        thrown.expect(VerifierException.class);
        thrown.expectMessage("assertion must not be null: null");

        getCustomVerifier().thatAll(null, () -> "foo", "bar");
    }

    @Test
    public void testThatAllWithMessageKeyWhenAssertionFailsForAllElements() {
        when(mockAssertion.verify(any(getElementClass()))).thenReturn(false);

        testThatAllHelper(createFullValue(), 1, false, () -> "foo", new Object[]{"bar"});
    }

    @Test
    public void testThatAllWithMessageKeyWhenAssertionFailsForSomeElements() {
        when(mockAssertion.verify(any(getElementClass()))).thenReturn(true);
        when(mockAssertion.verify(getExistingElement())).thenReturn(false);

        testThatAllHelper(createFullValue(), getFullValueSize(), false, () -> "foo", new Object[]{"bar"});
    }

    @Test
    public void testThatAllWithMessageKeyWhenAssertionPassesForAllElements() {
        when(mockAssertion.verify(any(getElementClass()))).thenReturn(true);

        testThatAllHelper(createFullValue(), getFullValueSize(), true, () -> "foo", new Object[]{"bar"});
    }

    @Test
    public void testThatAllWithMessageKeyWhenValueIsEmpty() {
        testThatAllHelper(createEmptyValue(), 0, true, () -> "foo", new Object[]{"bar"});
    }

    @Test
    public void testThatAllWithMessageKeyWhenValueIsNull() {
        testThatAllHelper(null, 0, true, () -> "foo", new Object[]{"bar"});
    }

    private void testThatAllHelper(T value, int assertionCalls, boolean expected, MessageKey key, Object[] args) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().thatAll(mockAssertion, key, args));

        verify(getMockVerification()).report(eq(expected), eq(key), getArgsCaptor().capture());
        verify(mockAssertion, times(assertionCalls)).verify(any(getElementClass()));

        assertEquals("Passes args for message formatting", Arrays.asList(args), getArgsCaptor().getAllValues());
    }

    @Test
    public void testThatAnyThrowsWhenAssertionIsNull() {
        thrown.expect(VerifierException.class);
        thrown.expectMessage("assertion must not be null: null");

        getCustomVerifier().thatAny(null);
    }

    @Test
    public void testThatAnyWhenAssertionFailsForAllElements() {
        when(mockAssertion.verify(any(getElementClass()))).thenReturn(false);

        testThatAnyHelper(createFullValue(), getFullValueSize(), false);
    }

    @Test
    public void testThatAnyWhenAssertionFailsForSomeElements() {
        when(mockAssertion.verify(any(getElementClass()))).thenReturn(false);
        when(mockAssertion.verify(getExistingElement())).thenReturn(true);

        testThatAnyHelper(createFullValue(), getFullValueSize(), true);
    }

    @Test
    public void testThatAnyWhenAssertionPassesForAllElements() {
        when(mockAssertion.verify(any(getElementClass()))).thenReturn(true);

        testThatAnyHelper(createFullValue(), 1, true);
    }

    @Test
    public void testThatAnyWhenValueIsEmpty() {
        testThatAnyHelper(createEmptyValue(), 0, false);
    }

    @Test
    public void testThatAnyWhenValueIsNull() {
        testThatAnyHelper(null, 0, false);
    }

    private void testThatAnyHelper(T value, int assertionCalls, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().thatAny(mockAssertion));

        verify(getMockVerification()).report(expected, (String) null);
        verify(mockAssertion, times(assertionCalls)).verify(any(getElementClass()));
    }

    @Test
    public void testThatAnyInternalThrowsWhenAssertionIsNull() {
        thrown.expect(VerifierException.class);
        thrown.expectMessage("assertion must not be null: null");

        getCustomVerifier().thatAnyInternal(null);
    }

    @Test
    public void testThatAnyInternalWhenAssertionFailsForAllElements() {
        when(mockAssertion.verify(any(getElementClass()))).thenReturn(false);

        testThatAnyInternalHelper(createFullValue(), getFullValueSize(), false);
    }

    @Test
    public void testThatAnyInternalWhenAssertionFailsForSomeElements() {
        when(mockAssertion.verify(any(getElementClass()))).thenReturn(false);
        when(mockAssertion.verify(getExistingElement())).thenReturn(true);

        testThatAnyInternalHelper(createFullValue(), getFullValueSize(), true);
    }

    @Test
    public void testThatAnyInternalWhenAssertionPassesForAllElements() {
        when(mockAssertion.verify(any(getElementClass()))).thenReturn(true);

        testThatAnyInternalHelper(createFullValue(), 1, true);
    }

    @Test
    public void testThatAnyInternalWhenValueIsEmpty() {
        testThatAnyInternalHelper(createEmptyValue(), 0, false);
    }

    @Test
    public void testThatAnyInternalWhenValueIsNull() {
        testThatAnyInternalHelper(null, 0, false);
    }

    private void testThatAnyInternalHelper(T value, int assertionCalls, boolean expected) {
        setValue(value);

        assertEquals("Matches elements correctly", expected, getCustomVerifier().thatAnyInternal(mockAssertion));

        verify(getMockVerification(), never()).report(anyBoolean(), any(MessageKey.class), anyVararg());
        verify(getMockVerification(), never()).report(anyBoolean(), any(String.class), anyVararg());
        verify(mockAssertion, times(assertionCalls)).verify(any(getElementClass()));
    }

    @Test
    public void testThatAnyWithMessageThrowsWhenAssertionIsNull() {
        thrown.expect(VerifierException.class);
        thrown.expectMessage("assertion must not be null: null");

        getCustomVerifier().thatAny(null, "foo {0}", "bar");
    }

    @Test
    public void testThatAnyWithMessageWhenAssertionFailsForAllElements() {
        when(mockAssertion.verify(any(getElementClass()))).thenReturn(false);

        testThatAnyHelper(createFullValue(), getFullValueSize(), false, "foo {0}", new Object[]{"bar"});
    }

    @Test
    public void testThatAnyWithMessageWhenAssertionFailsForSomeElements() {
        when(mockAssertion.verify(any(getElementClass()))).thenReturn(false);
        when(mockAssertion.verify(getExistingElement())).thenReturn(true);

        testThatAnyHelper(createFullValue(), getFullValueSize(), true, "foo {0}", new Object[]{"bar"});
    }

    @Test
    public void testThatAnyWithMessageWhenAssertionPassesForAllElements() {
        when(mockAssertion.verify(any(getElementClass()))).thenReturn(true);

        testThatAnyHelper(createFullValue(), 1, true, "foo {0}", new Object[]{"bar"});
    }

    @Test
    public void testThatAnyWithMessageWhenValueIsEmpty() {
        testThatAnyHelper(createEmptyValue(), 0, false, "foo {0}", new Object[]{"bar"});
    }

    @Test
    public void testThatAnyWithMessageWhenValueIsNull() {
        testThatAnyHelper(null, 0, false, "foo {0}", new Object[]{"bar"});
    }

    private void testThatAnyHelper(T value, int assertionCalls, boolean expected, String message, Object[] args) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().thatAny(mockAssertion, message, args));

        verify(getMockVerification()).report(eq(expected), eq(message), getArgsCaptor().capture());
        verify(mockAssertion, times(assertionCalls)).verify(any(getElementClass()));

        assertEquals("Passes args for message formatting", Arrays.asList(args), getArgsCaptor().getAllValues());
    }

    @Test
    public void testThatAnyWithMessageKeyThrowsWhenAssertionIsNull() {
        thrown.expect(VerifierException.class);
        thrown.expectMessage("assertion must not be null: null");

        getCustomVerifier().thatAny(null, () -> "foo", "bar");
    }

    @Test
    public void testThatAnyWithMessageKeyWhenAssertionFailsForAllElements() {
        when(mockAssertion.verify(any(getElementClass()))).thenReturn(false);

        testThatAnyHelper(createFullValue(), getFullValueSize(), false, () -> "foo", new Object[]{"bar"});
    }

    @Test
    public void testThatAnyWithMessageKeyWhenAssertionFailsForSomeElements() {
        when(mockAssertion.verify(any(getElementClass()))).thenReturn(false);
        when(mockAssertion.verify(getExistingElement())).thenReturn(true);

        testThatAnyHelper(createFullValue(), getFullValueSize(), true, () -> "foo", new Object[]{"bar"});
    }

    @Test
    public void testThatAnyWithMessageKeyWhenAssertionPassesForAllElements() {
        when(mockAssertion.verify(any(getElementClass()))).thenReturn(true);

        testThatAnyHelper(createFullValue(), 1, true, () -> "foo", new Object[]{"bar"});
    }

    @Test
    public void testThatAnyWithMessageKeyWhenValueIsEmpty() {
        testThatAnyHelper(createEmptyValue(), 0, false, () -> "foo", new Object[]{"bar"});
    }

    @Test
    public void testThatAnyWithMessageKeyWhenValueIsNull() {
        testThatAnyHelper(null, 0, false, () -> "foo", new Object[]{"bar"});
    }

    private void testThatAnyHelper(T value, int assertionCalls, boolean expected, MessageKey key, Object[] args) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().thatAny(mockAssertion, key, args));

        verify(getMockVerification()).report(eq(expected), eq(key), getArgsCaptor().capture());
        verify(mockAssertion, times(assertionCalls)).verify(any(getElementClass()));

        assertEquals("Passes args for message formatting", Arrays.asList(args), getArgsCaptor().getAllValues());
    }

    /**
     * <p>
     * Creates a value containing no elements.
     * </p>
     *
     * @return An empty value.
     */
    protected abstract T createEmptyValue();

    /**
     * <p>
     * Creates a value containing multiple elements.
     * </p>
     * <p>
     * It is recommended that this value contains at least 3 elements for thorough testing and the number of elements
     * <b>must</b> exactly match that which is returned by {@link #getFullValueSize()}.
     * </p>
     *
     * @return A full value.
     */
    protected abstract T createFullValue();

    /**
     * <p>
     * Creates a value containing at least one {@literal null} element.
     * </p>
     * <p>
     * It is recommended that this value contains at least 3 elements for thorough testing.
     * </p>
     *
     * @return A value containing a {@literal null} element.
     */
    protected abstract T createValueWithNull();

    /**
     * <p>
     * Returns the class for the type of elements contained within the values.
     * </p>
     *
     * @return The {@code Class} for the elements.
     */
    protected abstract Class<E> getElementClass();

    /**
     * <p>
     * Returns an element that exists within the value that is returned by {@link #createFullValue()}.
     * </p>
     * <p>
     * Where possible, it is recommended that this value be the last element within the value for the most thorough
     * testing.
     * </p>
     *
     * @return An element that exists within the full value.
     */
    protected abstract E getExistingElement();

    /**
     * <p>
     * Returns the number of elements within the value that is returned by {@link #createFullValue()}.
     * </p>
     *
     * @return The size of the full value.
     */
    protected abstract int getFullValueSize();

    /**
     * <p>
     * Returns an element that <b>does not</b> exist within the value that is returned by {@link #createFullValue()}.
     * </p>
     *
     * @return An element that is missing from the full value.
     */
    protected abstract E getMissingElement();
}
