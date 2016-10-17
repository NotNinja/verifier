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
package io.skelp.verifier.verification.factory;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import io.skelp.verifier.message.MessageFormatter;
import io.skelp.verifier.message.factory.MessageFormatterFactory;
import io.skelp.verifier.verification.DefaultVerification;

/**
 * Tests for the {@link DefaultVerificationFactory} class.
 *
 * @author Alasdair Mercer
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultVerificationFactoryTest {

    @Mock
    private MessageFormatter mockMessageFormatter;
    @Mock
    private MessageFormatterFactory mockMessageFormatterFactory;

    private DefaultVerificationFactory factory;

    @Before
    public void setUp() {
        factory = new DefaultVerificationFactory();

        when(mockMessageFormatterFactory.create()).thenReturn(mockMessageFormatter);
    }

    @Test
    public void testCreate() {
        DefaultVerification<Integer> instance = factory.create(mockMessageFormatterFactory, 123, "foo");

        assertNotNull("Never null", instance);
        assertSame("Passed message formatter factory", mockMessageFormatter, instance.getMessageFormatter());
        assertEquals("Passed name", "foo", instance.getName());
        assertEquals("Passed value", Integer.valueOf(123), instance.getValue());
    }
}
