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
package io.skelp.verifier.type.base;

import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import io.skelp.verifier.CustomVerifierTestCaseBase;
import io.skelp.verifier.VerifierAssertion;
import io.skelp.verifier.VerifierException;

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
    public void testContainWhenItemIsNotPresentInValue() {
        testContainHelper(createFullValue(), getMissingItem(), false);
    }

    @Test
    public void testContainWhenItemIsPresentInValue() {
        testContainHelper(createFullValue(), getExistingItem(), true);
    }

    @Test
    public void testContainWithNullItem() {
        testContainHelper(createValueWithNull(), null, true);
    }

    @Test
    public void testContainWithNullValue() {
        testContainHelper(null, getExistingItem(), false);
    }

    private void testContainHelper(T value, E item, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().contain(item));

        verify(getMockVerification()).check(eq(expected), eq("contain '%s'"), getArgsCaptor().capture());

        assertSame("Passes item for message formatting", item, getArgsCaptor().getValue());
    }

    @Test
    public void testContainAllWhenNoItems() {
        testContainAllHelper(createFullValue(), createEmptyArray(getItemClass()), true);
    }

    @Test
    public void testContainAllWhenNoItemsAndValueIsEmpty() {
        testContainAllHelper(createEmptyValue(), createEmptyArray(getItemClass()), true);
    }

    @Test
    public void testContainAllWhenItemIsNull() {
        testContainAllHelper(createFullValue(), createArray((E) null), false);
    }

    @Test
    public void testContainAllWhenItemsIsNull() {
        testContainAllHelper(createFullValue(), null, true);
    }

    @Test
    public void testContainAllWhenValueContainsAllItems() {
        testContainAllHelper(createFullValue(), createArray(getExistingItem(), getExistingItem(), getExistingItem()), true);
    }

    @Test
    public void testContainAllWhenValueContainsSomeItems() {
        testContainAllHelper(createFullValue(), createArray(getExistingItem(), getMissingItem(), getMissingItem()), false);
    }

    @Test
    public void testContainAllWhenValueDoesNotContainItem() {
        testContainAllHelper(createFullValue(), createArray(getMissingItem(), getMissingItem(), getMissingItem()), false);
    }

    @Test
    public void testContainAllWhenValueIsEmpty() {
        testContainAllHelper(createEmptyValue(), createArray(getExistingItem()), false);
    }

    @Test
    public void testContainAllWhenValueIsNull() {
        testContainAllHelper(null, createEmptyArray(getItemClass()), false);
    }

    private void testContainAllHelper(T value, E[] items, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().containAll(items));

        verify(getMockVerification()).check(eq(expected), eq("contain all %s"), getArgsCaptor().capture());

        assertArrayFormatter(getArgsCaptor().getValue(), items);
    }

    @Test
    public void testContainAnyWhenNoItems() {
        testContainAnyHelper(createFullValue(), createEmptyArray(getItemClass()), false);
    }

    @Test
    public void testContainAnyWhenNoItemsAndValueIsEmpty() {
        testContainAnyHelper(createEmptyValue(), createEmptyArray(getItemClass()), false);
    }

    @Test
    public void testContainAnyWhenItemIsNull() {
        testContainAnyHelper(createFullValue(), createArray((E) null), false);
    }

    @Test
    public void testContainAnyWhenItemsIsNull() {
        testContainAnyHelper(createFullValue(), null, false);
    }

    @Test
    public void testContainAnyWhenValueContainsAllItems() {
        testContainAnyHelper(createFullValue(), createArray(getExistingItem(), getExistingItem(), getExistingItem()), true);
    }

    @Test
    public void testContainAnyWhenValueContainsSomeItems() {
        testContainAnyHelper(createFullValue(), createArray(getMissingItem(), getMissingItem(), getExistingItem()), true);
    }

    @Test
    public void testContainAnyWhenValueDoesNotContainItem() {
        testContainAnyHelper(createFullValue(), createArray(getMissingItem(), getMissingItem(), getMissingItem()), false);
    }

    @Test
    public void testContainAnyWhenValueIsEmpty() {
        testContainAnyHelper(createEmptyValue(), createArray(getExistingItem()), false);
    }

    @Test
    public void testContainAnyWhenValueIsNull() {
        testContainAnyHelper(null, createEmptyArray(getItemClass()), false);
    }

    private void testContainAnyHelper(T value, E[] items, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().containAny(items));

        verify(getMockVerification()).check(eq(expected), eq("contain any %s"), getArgsCaptor().capture());

        assertArrayFormatter(getArgsCaptor().getValue(), items);
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

        verify(getMockVerification()).check(expected, "be empty");
    }

    @Test
    public void testSizeWithEmptyValue() {
        testSizeHelper(createEmptyValue(), 0, true);
    }

    @Test
    public void testSizeWithEmptyValueAndIncorrectSize() {
        testSizeHelper(createEmptyValue(), 1, false);
    }

    @Test
    public void testSizeWithNonEmptyValue() {
        testSizeHelper(createFullValue(), getFullValueSize(), true);
    }

    @Test
    public void testSizeWithNonEmptyValueAndIncorrectSize() {
        testSizeHelper(createFullValue(), getFullValueSize() + 1, false);
    }

    @Test
    public void testSizeWithNullValue() {
        testSizeHelper(null, 0, true);
    }

    @Test
    public void testSizeWithNullValueAndIncorrectSize() {
        testSizeHelper(null, 1, false);
    }

    private void testSizeHelper(T value, int size, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().size(size));

        verify(getMockVerification()).check(eq(expected), eq("have size of '%d'"), getArgsCaptor().capture());

        assertSame("Passes size for message formatting", size, getArgsCaptor().getValue());
    }

    @Test
    public void testThatAllThrowsWhenAssertionIsNull() {
        thrown.expect(VerifierException.class);
        thrown.expectMessage("assertion must not be null");

        getCustomVerifier().thatAll(null);
    }

    @Test
    public void testThatAllWhenAssertionFailsForAllItems() {
        when(mockAssertion.verify(any(getItemClass()))).thenReturn(false);

        testThatAllHelper(createFullValue(), 1, false);
    }

    @Test
    public void testThatAllWhenAssertionFailsForSomeItems() {
        when(mockAssertion.verify(any(getItemClass()))).thenReturn(true);
        when(mockAssertion.verify(getExistingItem())).thenReturn(false);

        testThatAllHelper(createFullValue(), getFullValueSize(), false);
    }

    @Test
    public void testThatAllWhenAssertionPassesForAllItems() {
        when(mockAssertion.verify(any(getItemClass()))).thenReturn(true);

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

        verify(getMockVerification()).check(expected, null);
        verify(mockAssertion, times(assertionCalls)).verify(any(getItemClass()));
    }

    @Test
    public void testThatAllWithMessageThrowsWhenAssertionIsNull() {
        thrown.expect(VerifierException.class);
        thrown.expectMessage("assertion must not be null");

        getCustomVerifier().thatAll(null, "foo %s", "bar");
    }

    @Test
    public void testThatAllWithMessageWhenAssertionFailsForAllItems() {
        when(mockAssertion.verify(any(getItemClass()))).thenReturn(false);

        testThatAllHelper(createFullValue(), 1, false, "foo %s", new Object[]{"bar"});
    }

    @Test
    public void testThatAllWithMessageWhenAssertionFailsForSomeItems() {
        when(mockAssertion.verify(any(getItemClass()))).thenReturn(true);
        when(mockAssertion.verify(getExistingItem())).thenReturn(false);

        testThatAllHelper(createFullValue(), getFullValueSize(), false, "foo %s", new Object[]{"bar"});
    }

    @Test
    public void testThatAllWithMessageWhenAssertionPassesForAllItems() {
        when(mockAssertion.verify(any(getItemClass()))).thenReturn(true);

        testThatAllHelper(createFullValue(), getFullValueSize(), true, "foo %s", new Object[]{"bar"});
    }

    @Test
    public void testThatAllWithMessageWhenValueIsEmpty() {
        testThatAllHelper(createEmptyValue(), 0, true, "foo %s", new Object[]{"bar"});
    }

    @Test
    public void testThatAllWithMessageWhenValueIsNull() {
        testThatAllHelper(null, 0, true, "foo %s", new Object[]{"bar"});
    }

    private void testThatAllHelper(T value, int assertionCalls, boolean expected, String message, Object[] args) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().thatAll(mockAssertion, message, args));

        verify(getMockVerification()).check(eq(expected), eq(message), getArgsCaptor().capture());
        verify(mockAssertion, times(assertionCalls)).verify(any(getItemClass()));

        assertEquals("Passes args for message formatting", Arrays.asList(args), getArgsCaptor().getAllValues());
    }

    @Test
    public void testThatAnyThrowsWhenAssertionIsNull() {
        thrown.expect(VerifierException.class);
        thrown.expectMessage("assertion must not be null");

        getCustomVerifier().thatAny(null);
    }

    @Test
    public void testThatAnyWhenAssertionFailsForAllItems() {
        when(mockAssertion.verify(any(getItemClass()))).thenReturn(false);

        testThatAnyHelper(createFullValue(), getFullValueSize(), false);
    }

    @Test
    public void testThatAnyWhenAssertionFailsForSomeItems() {
        when(mockAssertion.verify(any(getItemClass()))).thenReturn(false);
        when(mockAssertion.verify(getExistingItem())).thenReturn(true);

        testThatAnyHelper(createFullValue(), getFullValueSize(), true);
    }

    @Test
    public void testThatAnyWhenAssertionPassesForAllItems() {
        when(mockAssertion.verify(any(getItemClass()))).thenReturn(true);

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

        verify(getMockVerification()).check(expected, null);
        verify(mockAssertion, times(assertionCalls)).verify(any(getItemClass()));
    }

    @Test
    public void testThatAnyWithMessageThrowsWhenAssertionIsNull() {
        thrown.expect(VerifierException.class);
        thrown.expectMessage("assertion must not be null");

        getCustomVerifier().thatAny(null, "foo %s", "bar");
    }

    @Test
    public void testThatAnyWithMessageWhenAssertionFailsForAllItems() {
        when(mockAssertion.verify(any(getItemClass()))).thenReturn(false);

        testThatAnyHelper(createFullValue(), getFullValueSize(), false, "foo %s", new Object[]{"bar"});
    }

    @Test
    public void testThatAnyWithMessageWhenAssertionFailsForSomeItems() {
        when(mockAssertion.verify(any(getItemClass()))).thenReturn(false);
        when(mockAssertion.verify(getExistingItem())).thenReturn(true);

        testThatAnyHelper(createFullValue(), getFullValueSize(), true, "foo %s", new Object[]{"bar"});
    }

    @Test
    public void testThatAnyWithMessageWhenAssertionPassesForAllItems() {
        when(mockAssertion.verify(any(getItemClass()))).thenReturn(true);

        testThatAnyHelper(createFullValue(), 1, true, "foo %s", new Object[]{"bar"});
    }

    @Test
    public void testThatAnyWithMessageWhenValueIsEmpty() {
        testThatAnyHelper(createEmptyValue(), 0, false, "foo %s", new Object[]{"bar"});
    }

    @Test
    public void testThatAnyWithMessageWhenValueIsNull() {
        testThatAnyHelper(null, 0, false, "foo %s", new Object[]{"bar"});
    }

    private void testThatAnyHelper(T value, int assertionCalls, boolean expected, String message, Object[] args) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().thatAny(mockAssertion, message, args));

        verify(getMockVerification()).check(eq(expected), eq(message), getArgsCaptor().capture());
        verify(mockAssertion, times(assertionCalls)).verify(any(getItemClass()));

        assertEquals("Passes args for message formatting", Arrays.asList(args), getArgsCaptor().getAllValues());
    }

    /**
     * <p>
     * Creates a value containing no items.
     * </p>
     *
     * @return An empty value.
     */
    protected abstract T createEmptyValue();

    /**
     * <p>
     * Creates a value containing multiple items.
     * </p>
     * <p>
     * It is recommended that this value contains at least 3 items for thorough testing and the number of items
     * <b>must</b> exactly match that which is returned by {@link #getFullValueSize()}.
     * </p>
     *
     * @return A full value.
     */
    protected abstract T createFullValue();

    /**
     * <p>
     * Creates a value containing at least one {@literal null} item.
     * </p>
     * <p>
     * It is recommended that this value contains at least 3 items for thorough testing.
     * </p>
     *
     * @return A value containing a {@literal null} item.
     */
    protected abstract T createValueWithNull();

    /**
     * <p>
     * Returns an item that exists within the value that is returned by {@link #createFullValue()}.
     * </p>
     * <p>
     * Where possible, it is recommended that this value be the last item within the value for the most thorough
     * testing.
     * </p>
     *
     * @return An item that exists within the full value.
     */
    protected abstract E getExistingItem();

    /**
     * <p>
     * Returns the number of items within the value that is returned by {@link #createFullValue()}.
     * </p>
     *
     * @return The size of the full value.
     */
    protected abstract int getFullValueSize();

    /**
     * <p>
     * Returns the class for the type of items contained within the values.
     * </p>
     *
     * @return The {@code Class} for the items.
     */
    protected abstract Class<E> getItemClass();

    /**
     * <p>
     * Returns an item that <b>does not</b> exist within the value that is returned by {@link #createFullValue()}.
     * </p>
     *
     * @return An item that is missing from the full value.
     */
    protected abstract E getMissingItem();
}
