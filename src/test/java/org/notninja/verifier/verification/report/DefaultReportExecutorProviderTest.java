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

import static org.junit.Assert.*;

import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.notninja.verifier.service.Weighted;

/**
 * <p>
 * Tests for the {@link DefaultReportExecutorProvider} class.
 * </p>
 *
 * @author Alasdair Mercer
 */
public class DefaultReportExecutorProviderTest {

    private static void assertReporters(ReportExecutor reportExecutor, Class<?>[] reporterClasses) {
        List<Reporter> reporters = reportExecutor.getReporters();

        assertNotNull("Has reporters", reporters);
        assertEquals("Has correct number of reporters", reporterClasses.length, reporters.size());

        for (int i = 0; i < reporterClasses.length; i++) {
            Reporter reporter = reporters.get(i);
            Class<?> reporterClass = reporterClasses[i];

            assertNotNull("Reporter[" + i + "] is never null", reporter);
            assertTrue("Reporter[" + i + "] is of expected type", reporterClass.isInstance(reporter));
        }
    }

    private DefaultReportExecutorProvider provider;

    @Before
    public void setUp() {
        provider = new DefaultReportExecutorProvider();
    }

    @Test
    public void testGetReportExecutor() {
        ReportExecutor reportExecutor = provider.getReportExecutor();

        assertNotNull("Never returns null", reportExecutor);
        assertTrue("Returns instance of SimpleReportExecutor", reportExecutor instanceof SimpleReportExecutor);

        assertReporters(reportExecutor, new Class<?>[]{AssertionReporter.class});
    }

    @Test
    public void testGetWeight() {
        Assert.assertEquals("Has default implementation weight", Weighted.DEFAULT_IMPLEMENTATION_WEIGHT, provider.getWeight());
    }
}
