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
package io.skelp.verifier.message.factory;

import io.skelp.verifier.factory.VerifierFactoryException;
import io.skelp.verifier.message.MessageFormatter;

/**
 * <p>
 * A factory for creating instances of {@link MessageFormatter}.
 * </p>
 *
 * @author Alasdair Mercer
 */
public interface MessageFormatterFactory {

    /**
     * <p>
     * Creates an instance of a {@link MessageFormatter} to be used to format messages.
     * </p>
     *
     * @return The newly created {@link MessageFormatter}
     * @throws VerifierFactoryException
     *         If a problem occurs while creating the {@link MessageFormatter} instance.
     */
    MessageFormatter create() throws VerifierFactoryException;
}
