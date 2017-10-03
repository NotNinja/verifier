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
package org.notninja.verifier.verification.report;

import org.notninja.verifier.VerifierException;
import org.notninja.verifier.verification.Verification;
import org.notninja.verifier.service.Weighted;

/**
 * <p>
 * An implementation of {@link Reporter} that simply asserts that the given result passes verification.
 * </p>
 * <p>
 * Basically, a {@link VerifierException} will be thrown in the following cases:
 * </p>
 * <ul>
 * <li>Result is {@literal true} and the {@link Verification} is negated</li>
 * <li>Result is {@literal false} and the {@link Verification} is <b>not</b> negated</li>
 * </ul>
 * <p>
 * For this reason, this is the ideal {@link Reporter} to be last in the chain as it does what Verifier was designed to
 * do.
 * </p>
 *
 * @author Alasdair Mercer
 * @since 0.2.0
 */
public final class AssertionReporter implements Reporter {

    @Override
    public boolean report(final Verification<?> verification, final boolean result, final MessageHolder messageHolder) {
        if (result && verification.isNegated() || !result && !verification.isNegated()) {
            throw new VerifierException(messageHolder.getMessage(verification));
        }

        return true;
    }

    @Override
    public int getWeight() {
        return Weighted.DEFAULT_IMPLEMENTATION_WEIGHT;
    }
}
