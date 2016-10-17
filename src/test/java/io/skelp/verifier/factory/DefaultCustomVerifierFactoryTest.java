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

import io.skelp.verifier.AbstractCustomVerifier;
import io.skelp.verifier.CustomVerifier;
import io.skelp.verifier.verification.Verification;

/**
 * Tests for the {@link DefaultCustomVerifierFactory} class.
 *
 * @author Alasdair Mercer
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultCustomVerifierFactoryTest {

    @Mock
    private Verification<Object> mockVerification;

    private DefaultCustomVerifierFactory factory;

    @Before
    public void setUp() {
        factory = new DefaultCustomVerifierFactory();
    }

    @Test
    public void testCreate() {
        TestCustomVerifierImpl instance = factory.create(TestCustomVerifierImpl.class, mockVerification);

        assertNotNull("Never null", instance);
        assertSame("Passed verification", mockVerification, instance.verification);
    }

    @Test(expected = VerifierFactoryException.class)
    public void testCreateThrowsIfClassCannotBeInstantiated() {
        factory.create(TestCustomVerifier.class, mockVerification);
    }

    @Test(expected = VerifierFactoryException.class)
    public void testCreateThrowsIfClassIsNull() {
        factory.create(null, mockVerification);
    }

    private interface TestCustomVerifier<T, V extends TestCustomVerifier<T, V>> extends CustomVerifier<T, V> {
    }

    private static class TestCustomVerifierImpl extends AbstractCustomVerifier<Object, TestCustomVerifierImpl> implements TestCustomVerifier<Object, TestCustomVerifierImpl> {

        final Verification<Object> verification;

        public TestCustomVerifierImpl(Verification<Object> verification) {
            super(verification);

            this.verification = verification;
        }
    }
}
