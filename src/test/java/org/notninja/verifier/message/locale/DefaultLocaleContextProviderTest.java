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
package org.notninja.verifier.message.locale;

import static org.junit.Assert.*;

import java.util.Locale;

import org.junit.*;

import org.notninja.verifier.service.Weighted;

/**
 * <p>
 * Tests for the {@link DefaultLocaleContextProvider} class.
 * </p>
 *
 * @author Alasdair Mercer
 */
public class DefaultLocaleContextProviderTest {

    private static Locale originalDefaultLocale;

    @BeforeClass
    public static void setUpClass() {
        originalDefaultLocale = Locale.getDefault();
    }

    private DefaultLocaleContextProvider provider;

    @Before
    public void setUp() {
        provider = new DefaultLocaleContextProvider();
    }

    @After
    public void tearDown() {
        Locale.setDefault(originalDefaultLocale);
    }

    @Test
    public void testGetLocaleContext() {
        Locale.setDefault(Locale.ENGLISH);

        LocaleContext context = provider.getLocaleContext();

        assertNotNull("Never returns null", context);
        assertTrue("Returns an instance of SimpleLocaleContext", context instanceof SimpleLocaleContext);
        assertEquals("Context uses default locale", Locale.ENGLISH, context.getLocale());
    }

    @Test
    public void testGetWeight() {
        Assert.assertEquals("Has default implementation weight", Weighted.DEFAULT_IMPLEMENTATION_WEIGHT, provider.getWeight());
    }
}
