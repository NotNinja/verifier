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

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import io.skelp.verifier.verification.Verification;

/**
 * Base for tests of {@link AbstractCustomVerifier} implementation classes.
 *
 * @param <T>
 *         the value type for the {@link AbstractCustomVerifier} being tested
 * @param <V>
 *         the type of the {@link AbstractCustomVerifier} being tested
 * @author Alasdair Mercer
 */
@RunWith(MockitoJUnitRunner.class)
public abstract class AbstractCustomVerifierTestBase<T, V extends AbstractCustomVerifier<T, V>> {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Captor
    private ArgumentCaptor<Object> argsCaptor;
    @Mock
    private Verification<T> mockVerification;

    private T value;

    private V customVerifier;

    @Before
    public void setUp() {
        when(mockVerification.getValue()).thenAnswer(new Answer<T>() {
            @Override
            public T answer(InvocationOnMock invocation) throws Throwable {
                return value;
            }
        });

        value = null;

        customVerifier = createCustomVerifier();
    }

    @Test
    public void testVerification() {
        assertSame("Verification field is correct", mockVerification, customVerifier.verification);
    }

    /**
     * Creates an instance of the custom verifier test subject to be tested.
     *
     * @return The {@link AbstractCustomVerifier} to be tested.
     */
    protected abstract V createCustomVerifier();

    /**
     * Returns an argument captor to be be used to capture any varargs that are passed to {@link
     * Verification#check(boolean, String, Object...)}.
     *
     * @return An {@code ArgumentCaptor} to be used to capture optional format arguments.
     */
    protected ArgumentCaptor<Object> getArgsCaptor() {
        return argsCaptor;
    }

    /**
     * Returns the custom verifier test subject that is being tested.
     *
     * @return The {@link AbstractCustomVerifier} being tested.
     */
    protected V getCustomVerifier() {
        return customVerifier;
    }

    /**
     * Returns the mock verification being used being used to test the subject.
     *
     * @return The mock {@link Verification}.
     */
    protected Verification<T> getMockVerification() {
        return mockVerification;
    }

    /**
     * Returns the value being used to test the subject.
     *
     * @return The value.
     */
    protected T getValue() {
        return value;
    }

    /**
     * Sets the value being used to test the subject to {@code value}.
     *
     * @param value
     *         the value to be set
     */
    protected void setValue(T value) {
        this.value = value;
    }
}
