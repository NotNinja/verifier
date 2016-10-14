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

import java.lang.reflect.Constructor;
import io.skelp.verifier.message.factory.DefaultMessageFormatterFactory;
import io.skelp.verifier.message.factory.MessageFormatterFactory;
import io.skelp.verifier.verification.factory.DefaultVerificationFactory;
import io.skelp.verifier.verification.factory.VerificationFactory;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the {@link DefaultVerifierFactoryProvider} class.
 *
 * @author Alasdair Mercer
 */
public class DefaultVerifierFactoryProviderTest {

  private DefaultVerifierFactoryProvider provider;

  @Before
  public void setup() {
    provider = new DefaultVerifierFactoryProvider();
  }

  @Test
  public void hackCoverage() throws Exception {
    // TODO: Determine how to avoid this
    Class<?> cls = Class.forName(DefaultVerifierFactoryProvider.class.getName() + "$LazyHolder");
    Constructor<?> constructor = cls.getDeclaredConstructor();
    constructor.setAccessible(true);
    constructor.newInstance();
  }

  @Test
  public void testGetCustomVerifierFactory() {
    CustomVerifierFactory factory = provider.getCustomVerifierFactory();

    assertNotNull("Never null", factory);
    assertTrue("Instance is a DefaultCustomVerifierFactory", factory instanceof DefaultCustomVerifierFactory);
    assertSame("Instance is singleton", factory, provider.getCustomVerifierFactory());
  }

  @Test
  public void testGetMessageFormatterFactory() {
    MessageFormatterFactory factory = provider.getMessageFormatterFactory();

    assertNotNull("Never null", factory);
    assertTrue("Instance is a DefaultMessageFormatterFactory", factory instanceof DefaultMessageFormatterFactory);
    assertSame("Instance is singleton", factory, provider.getMessageFormatterFactory());
  }

  @Test
  public void testGetVerificationFactory() {
    VerificationFactory factory = provider.getVerificationFactory();

    assertNotNull("Never null", factory);
    assertTrue("Instance is a DefaultVerificationFactory", factory instanceof DefaultVerificationFactory);
    assertSame("Instance is singleton", factory, provider.getVerificationFactory());
  }
}
