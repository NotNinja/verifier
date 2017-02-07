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
package io.skelp.verifier.message.locale;

import static org.junit.Assert.*;

import java.util.Locale;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * <p>
 * Tests for the {@link SimpleLocaleContext} class.
 * </p>
 *
 * @author Alasdair Mercer
 */
public class SimpleLocaleContextTest {

    private static Locale originalDefaultLocale;

    @BeforeClass
    public static void setUpClass() {
        originalDefaultLocale = Locale.getDefault();
    }

    @After
    public void tearDown() {
        Locale.setDefault(originalDefaultLocale);
    }

    @Test
    public void testConstructorWithLocale() {
        SimpleLocaleContext context = new SimpleLocaleContext(Locale.FRENCH);

        assertEquals("Uses locale passed to constructor", Locale.FRENCH, context.getLocale());
    }

    @Test
    public void testConstructorWithLocaleWhenNull() {
        Locale.setDefault(Locale.ENGLISH);

        SimpleLocaleContext context = new SimpleLocaleContext(null);

        assertEquals("Uses default locale", Locale.ENGLISH, context.getLocale());
    }

    @Test
    public void testConstructorWithNoArgs() {
        Locale.setDefault(Locale.ENGLISH);

        SimpleLocaleContext context = new SimpleLocaleContext();

        assertEquals("Uses default locale", Locale.ENGLISH, context.getLocale());
    }

    @Test
    public void testGetLocale() {
        SimpleLocaleContext context = new SimpleLocaleContext(Locale.FRENCH);

        assertEquals("Returns correct locale", Locale.FRENCH, context.getLocale());
    }
}
