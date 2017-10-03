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
package org.notninja.verifier;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * <p>
 * Tests for the {@link VerifierException} class.
 * </p>
 *
 * @author Alasdair Mercer
 */
public class VerifierExceptionTest {

    private static final Throwable TEST_CAUSE = new Exception("test-cause");
    private static final String TEST_MESSAGE = "test-message";

    private static void assertVerifierException(VerifierException exception, String message, Throwable cause) {
        assertEquals("Has correct detail message", message, exception.getMessage());
        assertEquals("Has correct cause", cause, exception.getCause());
    }

    @Test
    public void testConstructorWithMessage() {
        assertVerifierException(new VerifierException(TEST_MESSAGE), TEST_MESSAGE, null);
    }

    @Test
    public void testConstructorWithMessageAndCause() {
        assertVerifierException(new VerifierException(TEST_MESSAGE, TEST_CAUSE), TEST_MESSAGE, TEST_CAUSE);
    }

    @Test
    public void testConstructorWithNoArgs() {
        assertVerifierException(new VerifierException(), null, null);
    }

    @Test
    public void testConstructorWithCause() {
        assertVerifierException(new VerifierException(TEST_CAUSE), TEST_CAUSE.toString(), TEST_CAUSE);
    }

    @Test(expected = VerifierException.class)
    public void testThrowable() {
        throw new VerifierException();
    }
}
