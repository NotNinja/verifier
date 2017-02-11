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
package io.skelp.verifier.verification.report;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import io.skelp.verifier.verification.Verification;

/**
 * <p>
 * Tests for the {@link StringMessageHolder} class.
 * </p>
 *
 * @author Alasdair Mercer
 */
@RunWith(MockitoJUnitRunner.class)
public class StringMessageHolderTest {

    @Mock
    private Verification<?> mockVerification;

    @Test
    public void testGetMessage() {
        String expected = "i am expected";
        String message = "test";
        Object[] args = new Object[]{"foo", "bar"};

        when(mockVerification.getMessage(message, args)).thenReturn(expected);

        StringMessageHolder holder = new StringMessageHolder(message, args);
        assertEquals("Uses message source to return message", expected, holder.getMessage(mockVerification));
    }
}
