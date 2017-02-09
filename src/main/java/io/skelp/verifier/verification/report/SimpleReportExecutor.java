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

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * A simple immutable implementation of {@link ReportExecutor}.
 * </p>
 *
 * @author Alasdair Mercer
 * @since 0.2.0
 */
public final class SimpleReportExecutor extends AbstractReportExecutor {

    private final List<Reporter> reporters;

    /**
     * <p>
     * Creates an instance of {@link SimpleReportExecutor} for the {@code reporters} provided.
     * </p>
     *
     * @param reporters
     *         the {@code List} of {@link Reporter Reporters} to be used
     */
    public SimpleReportExecutor(final List<Reporter> reporters) {
        this.reporters = new ArrayList<>(reporters);
    }

    @Override
    public List<Reporter> getReporters() {
        return reporters;
    }
}
