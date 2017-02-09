/*
 * Copyright (C) 2017 Alasdair Mercer, Skelp
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
package io.skelp.verifier.message;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import io.skelp.verifier.service.Weighted;

/**
 * <p>
 * Tests for the {@link DefaultMessageSourceProvider} class.
 * </p>
 *
 * @author Alasdair Mercer
 */
public class DefaultMessageSourceProviderTest {

    private DefaultMessageSourceProvider provider;

    @Before
    public void setUp() {
        provider = new DefaultMessageSourceProvider();
    }

    @Test
    public void testGetMessageSource() {
        MessageSource messageSource = provider.getMessageSource();

        assertNotNull("Never returns null", messageSource);
        assertTrue("Returns instance of ResourceBundleMessageSource", messageSource instanceof ResourceBundleMessageSource);
        assertSame("Singleton instance is returned", messageSource, provider.getMessageSource());
    }

    @Test
    public void testGetWeight() {
        assertEquals("Has default implementation weight", Weighted.DEFAULT_IMPLEMENTATION_WEIGHT, provider.getWeight());
    }
}
