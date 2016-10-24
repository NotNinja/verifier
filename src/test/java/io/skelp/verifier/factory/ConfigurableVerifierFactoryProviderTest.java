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
package io.skelp.verifier.factory;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import io.skelp.verifier.message.factory.MessageFormatterFactory;
import io.skelp.verifier.verification.factory.VerificationFactory;

/**
 * Tests for the {@link ConfigurableVerifierFactoryProvider} class.
 *
 * @author Alasdair Mercer
 */
@RunWith(MockitoJUnitRunner.class)
public class ConfigurableVerifierFactoryProviderTest {

    @Mock
    private CustomVerifierFactory mockCustomVerifierFactory;
    @Mock
    private MessageFormatterFactory mockMessageFormatterFactory;
    @Mock
    private VerificationFactory mockVerificationFactory;

    private ConfigurableVerifierFactoryProvider provider;

    @Before
    public void setUp() {
        provider = new ConfigurableVerifierFactoryProvider();
    }

    @Test
    public void testCustomVerifierFactory() {
        assertNull("Custom verifier factory is null by default", provider.getCustomVerifierFactory());

        provider.setCustomVerifierFactory(mockCustomVerifierFactory);

        assertSame("Custom verifier factory can be changed", mockCustomVerifierFactory, provider.getCustomVerifierFactory());

        provider.setCustomVerifierFactory(null);

        assertNull("Custom verifier factory can be set to null", provider.getCustomVerifierFactory());
    }

    @Test
    public void testMessageFormatterFactory() {
        assertNull("Message formatter factory is null by default", provider.getMessageFormatterFactory());

        provider.setMessageFormatterFactory(mockMessageFormatterFactory);

        assertSame("Message formatter factory can be changed", mockMessageFormatterFactory, provider.getMessageFormatterFactory());

        provider.setMessageFormatterFactory(null);

        assertNull("Message formatter factory can be set to null", provider.getMessageFormatterFactory());
    }

    @Test
    public void testVerificationFactory() {
        assertNull("Verification factory is null by default", provider.getVerificationFactory());

        provider.setVerificationFactory(mockVerificationFactory);

        assertSame("Verification factory can be changed", mockVerificationFactory, provider.getVerificationFactory());

        provider.setVerificationFactory(null);

        assertNull("Verification factory can be set to null", provider.getVerificationFactory());
    }
}
