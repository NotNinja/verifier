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

import java.util.List;

import org.notninja.verifier.VerifierException;
import org.notninja.verifier.verification.Verification;

/**
 * <p>
 * Responsible for executing a chain of {@link Reporter Reporters} one after the other.
 * </p>
 *
 * @author Alasdair Mercer
 * @since 0.2.0
 */
public interface ReportExecutor {

    /**
     * <p>
     * Executes all of the {@link Reporter Reporters} for this {@link ReportExecutor} sequentially with the information
     * provided.
     * </p>
     * <p>
     * Any of the {@link Reporter Reporters} may throw a {@link VerifierException} with a suitable message held in the
     * {@code messageHolder} provided should they deem that {@code result} does not pass verification, breaking the
     * chain, meaning none of the remaining {@link Reporter Reporters} within the chain will be executed. The chain can
     * also be broken should {@link Reporter#report(Verification, boolean, MessageHolder)} return {@literal false} at
     * any time.
     * </p>
     *
     * @param verification
     *         the current {@link Verification}
     * @param result
     *         the result of the verification to be reported
     * @param messageHolder
     *         the {@link MessageHolder} containing the optional message which provides a more detailed explanation of
     *         what was verified
     * @throws VerifierException
     *         If any of the {@link Reporter Reporters} deem that {@code result} should not pass for verification.
     */
    void execute(Verification<?> verification, boolean result, MessageHolder messageHolder);

    /**
     * <p>
     * Returns the reporters to be executed by this {@link ReportExecutor}.
     * </p>
     *
     * @return The {@code List} of {@link Reporter Reporters} to be executed.
     */
    List<Reporter> getReporters();
}
