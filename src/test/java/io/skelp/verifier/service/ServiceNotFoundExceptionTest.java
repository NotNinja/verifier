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
package io.skelp.verifier.service;

import static org.junit.Assert.*;

import org.junit.Test;

import io.skelp.verifier.CustomVerifierProvider;

/**
 * <p>
 * Tests for the {@link ServiceNotFoundException} class.
 * </p>
 *
 * @author Alasdair Mercer
 */
public class ServiceNotFoundExceptionTest {

    @Test
    public void testConstructor() {
        ServiceNotFoundException exception = new ServiceNotFoundException(CustomVerifierProvider.class);

        assertEquals("Has correct detail message", "Could not find implementation for service: io.skelp.verifier.CustomVerifierProvider", exception.getMessage());
    }

    @Test(expected = ServiceNotFoundException.class)
    public void testThrowable() {
        throw new ServiceNotFoundException(CustomVerifierProvider.class);
    }
}
