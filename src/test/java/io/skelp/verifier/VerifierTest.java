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
import io.skelp.verifier.verification.factory.VerificationFactory;

/**
 * Tests for the {@link Verifier} class.
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
    private VerificationFactory mockVerificationFactory;
    @Mock
    private VerifierFactoryProvider mockVerifierFactoryProvider;

    @Before
    public void setUp() {
        when(mockVerifierFactoryProvider.getCustomVerifierFactory()).thenReturn(mockCustomVerifierFactory);
        when(mockVerifierFactoryProvider.getMessageFormatterFactory()).thenReturn(mockMessageFormatterFactory);
        when(mockVerifierFactoryProvider.getVerificationFactory()).thenReturn(mockVerificationFactory);

        Verifier.setFactoryProvider(mockVerifierFactoryProvider);
    }

    @After
    public void tearDown() {
        Verifier.setFactoryProvider(originalFactoryProvider);
    }

    @Test
    public void hackCoverage() {
        // TODO: Determine how to avoid this
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
}
