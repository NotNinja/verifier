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
package org.notninja.verifier.message;

import org.notninja.verifier.VerifierException;
import org.notninja.verifier.service.Weighted;

/**
 * <p>
 * Provides {@link MessageSource} instances to be used to retrieve localized and/or formatted messages.
 * </p>
 * <p>
 * {@code MessageSourceProviders} are registered via Java's SPI, so in order to register a custom
 * {@code MessageSourceProvider} projects should contain should create a
 * {@code org.notninja.verifier.verification.message.MessageSourceProvider} file within {@code META-INF/services}
 * listing the class reference for each custom {@code MessageSourceProvider} (e.g.
 * {@code com.example.verifier.MyCustomMessageSourceProvider}) on separate lines. {@code MessageSourceProviders} are
 * also {@link Weighted}, which means that they are loaded in priority order (the lower the weight, the higher the
 * priority). Verifier has a built-in default {@code MessageSourceProvider} which is given a low priority (i.e.
 * {@value #DEFAULT_IMPLEMENTATION_WEIGHT}) to allow custom implementations to be easily ordered around it.
 * </p>
 *
 * @author Alasdair Mercer
 * @since 0.2.0
 */
public interface MessageSourceProvider extends Weighted {

    /**
     * <p>
     * Returns an instance of {@link MessageSource}.
     * </p>
     *
     * @return The {@link MessageSource} instance.
     * @throws VerifierException
     *         If a problem occurs while providing the {@link MessageSource} instance.
     */
    MessageSource getMessageSource();
}
