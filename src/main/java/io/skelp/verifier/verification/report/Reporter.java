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

import io.skelp.verifier.VerifierException;
import io.skelp.verifier.service.Weighted;
import io.skelp.verifier.verification.Verification;

/**
 * <p>
 * Reports a given result, which may determine whether it passes verification, which are generally executed via a
 * {@link ReportExecutor}. It should not modify the state of the {@link Verification} in any way.
 * </p>
 * <p>
 * If a {@code Reporter} determines that the result has not passed verification, then it should throw a
 * {@link VerifierException} with a suitable message provided by a {@link MessageHolder}. However they are not required
 * to determine whether the verification has passed and can report differently instead (e.g. logging, profiling).
 * </p>
 * <p>
 * When a {@code Reporter} is executed by a {@link ReportExecutor}, it can control whether the next {@code Reporter} in
 * the chain gets executed.
 * </p>
 * <p>
 * {@code Reporters} are registered via Java's SPI, so in order to register a custom {@code Reporter} projects should
 * contain should create a {@code io.skelp.verifier.verification.report.Reporter} file within {@code META-INF/services}
 * listing the class reference for each custom {@code Reporter} (e.g. {@code com.example.verifier.MyCustomReporter}) on
 * separate lines. {@code Reporters} are also {@link Weighted}, which means that they are loaded in priority order (the
 * lower the weight, the higher the priority). Verifier has a built-in default {@code Reporter} which is given a low
 * priority (i.e. {@value #DEFAULT_IMPLEMENTATION_WEIGHT}) to allow custom implementations to be easily ordered around
 * it.
 * </p>
 *
 * @author Alasdair Mercer
 * @since 0.2.0
 */
public interface Reporter extends Weighted {

    /**
     * <p>
     * Reports the specified {@code result}, which may determine whether it passes for the given verification.
     * </p>
     * <p>
     * If this {@link Reporter} determines that {@code result} has not passed verification, a {@link VerifierException}
     * will be thrown with a suitable message held in the {@code messageHolder} provided.
     * </p>
     * <p>
     * When this method is invoked by a {@link ReportExecutor}, the value returned by this method can be used to control
     * whether the next {@link Reporter} in the chain is executed.
     * </p>
     *
     * @param verification
     *         the current {@link Verification}
     * @param result
     *         the result of the verification
     * @param messageHolder
     *         the {@link MessageHolder} containing the optional message which provides a more detailed explanation of
     *         what was verified
     * @return {@literal true} if the next {@link Reporter} in the chain of the {@link ReportExecutor} should be
     * executed; otherwise {@literal false}.
     * @throws VerifierException
     *         If deemed that {@code result} should not pass for verification.
     */
    boolean report(Verification<?> verification, boolean result, MessageHolder messageHolder);
}
