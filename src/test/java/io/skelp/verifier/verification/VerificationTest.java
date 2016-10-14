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
package io.skelp.verifier.verification;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import io.skelp.verifier.VerifierException;
import io.skelp.verifier.message.MessageFormatter;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Tests for the {@link Verification} class.
 *
 * @author Alasdair Mercer
 */
@RunWith(MockitoJUnitRunner.class)
public class VerificationTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Mock
  private MessageFormatter mockMessageFormatter;

  @Test
  public void testCheckWithBadResult() {
    thrown.expect(VerifierException.class);
    thrown.expectMessage("i am expected");

    Verification<?> verification = new Verification<>(mockMessageFormatter, null, null);

    when(mockMessageFormatter.format(same(verification), eq("test"), Matchers.anyVararg())).thenReturn("i am expected");

    verification.check(false, "test", "foo", "bar");
  }

  @Test
  public void testCheckWithBadResultWhenNegated() {
    Verification<?> verification = new Verification<>(mockMessageFormatter, null, null);
    verification.setNegated(true);

    assertSame("Chains reference", verification, verification.check(false, "test", "foo", "bar"));
    assertFalse("No longer negated", verification.isNegated());
  }

  @Test
  public void testCheckWithGoodResult() {
    Verification<?> verification = new Verification<>(mockMessageFormatter, null, null);

    assertSame("Chains reference", verification, verification.check(true, "test", "foo", "bar"));
    assertFalse("Still not negated", verification.isNegated());
  }

  @Test
  public void testCheckWithGoodResultWhenNegated() {
    thrown.expect(VerifierException.class);
    thrown.expectMessage("i am expected");

    Verification<?> verification = new Verification<>(mockMessageFormatter, null, null);
    verification.setNegated(true);

    when(mockMessageFormatter.format(same(verification), eq("test"), Matchers.anyVararg())).thenReturn("i am expected");

    verification.check(true, "test", "foo", "bar");
  }

  @Test
  public void testMessageFormatter() {
    Verification<?> verification = new Verification<>(mockMessageFormatter, null, null);

    assertEquals("Message formatter property is readable", mockMessageFormatter, verification.getMessageFormatter());
  }

  @Test
  public void testName() {
    Verification<?> verification = new Verification<>(null, null, "foo");

    assertEquals("Name property is readable", "foo", verification.getName());
  }

  @Test
  public void testNegated() {
    Verification<?> verification = new Verification<>(null, null, null);

    assertFalse("Negated property is readable", verification.isNegated());

    verification.setNegated(true);

    assertTrue("Negated property is writable", verification.isNegated());
  }

  @Test
  public void testValue() {
    Verification<?> verification = new Verification<>(null, 123, null);

    assertEquals("Value property is readable", 123, verification.getValue());
  }
}
