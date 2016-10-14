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
package io.skelp.verifier.message;

import static org.junit.Assert.*;

import io.skelp.verifier.Verification;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests for the {@link DefaultMessageFormatter} class.
 *
 * @author Alasdair Mercer
 */
public class DefaultMessageFormatterTest {

  private static DefaultMessageFormatter formatter;

  @BeforeClass
  public static void setupClass() {
    formatter = new DefaultMessageFormatter();
  }

  private static <T> Verification<T> createVerification(T value, Object name, boolean negated) {
    Verification<T> verification = new Verification<>(formatter, value, name);
    verification.setNegated(negated);

    return verification;
  }

  @Test
  public void testFormat() {
    String expected = "foo must be a test: 123";
    String actual = formatter.format(createVerification(123, "foo", false), "be a %s", "test");

    assertEquals("Formats verification", expected, actual);
  }

  @Test
  public void testFormatWhenNegated() {
    String expected = "foo must not be a test: 123";
    String actual = formatter.format(createVerification(123, "foo", true), "be a %s", "test");

    assertEquals("Formats negated verification", expected, actual);
  }

  @Test
  public void testFormatWithNoName() {
    String expected = "Value must be a test: 123";
    String actual = formatter.format(createVerification(123, null, false), "be a %s", "test");

    assertEquals("Formats verification with no name", expected, actual);
  }

  @Test
  public void testFormatWithNoMessage() {
    String expected = "foo must match: 123";
    String actual = formatter.format(createVerification(123, "foo", false), null);

    assertEquals("Formats verification with no message", expected, actual);
  }

  @Test
  public void testFormatWithNoMessageWhenNegated() {
    String expected = "foo must not match: 123";
    String actual = formatter.format(createVerification(123, "foo", true), null);

    assertEquals("Formats verification", expected, actual);
  }

  @Test
  public void testFormatWithNullValue() {
    String expected = "foo must be a test: null";
    String actual = formatter.format(createVerification(null, "foo", false), "be a %s", "test");

    assertEquals("Formats verification with null value", expected, actual);
  }
}
