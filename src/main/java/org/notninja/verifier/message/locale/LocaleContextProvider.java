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

import org.notninja.verifier.VerifierException;
import org.notninja.verifier.service.Weighted;

/**
 * <p>
 * Provides {@link LocaleContext} instances to be used to localize messages.
 * </p>
 * <p>
 * {@code LocaleContextProviders} are registered via Java's SPI, so in order to register a custom
 * {@code LocaleContextProvider} projects should contain should create a
 * {@code org.notninja.verifier.verification.message.locale.LocaleContextProvider} file within {@code META-INF/services}
 * listing the class reference for each custom {@code LocaleContextProvider} (e.g.
 * {@code com.example.verifier.MyCustomLocaleContextProvider}) on separate lines. {@code LocaleContextProviders} are
 * also {@link Weighted}, which means that they are loaded in priority order (the lower the weight, the higher the
 * priority). Verifier has a built-in default {@code LocaleContextProvider} which is given a low priority (i.e.
 * {@value #DEFAULT_IMPLEMENTATION_WEIGHT}) to allow custom implementations to be easily ordered around it.
 * </p>
 *
 * @author Alasdair Mercer
 * @since 0.2.0
 */
public interface LocaleContextProvider extends Weighted {

    /**
     * <p>
     * Returns an instance of {@link LocaleContext}.
     * </p>
     *
     * @return The {@link LocaleContext} instance.
     * @throws VerifierException
     *         If a problem occurs while providing the {@link LocaleContext} instance.
     */
    LocaleContext getLocaleContext();
}
