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

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.junit.Test;

import io.skelp.verifier.service.Weighted.WeightedComparator;

/**
 * <p>
 * Tests for the {@link Weighted} interface.
 * </p>
 *
 * @author Alasdair Mercer
 */
public class WeightedTest {

    private static List<Weighted> createList(Integer... weights) {
        return Arrays.stream(weights)
            .map(TestWeightedImpl::new)
            .collect(Collectors.toList());
    }

    @Test
    public void testWeightedComparator() {
        List<Weighted> expected = createList(0, 25, 50, 75, 100);
        List<Weighted> actual = createList(100, 25, 0, 75, 50);
        actual.sort(new WeightedComparator());

        assertEquals("Sorts based on weight", expected, actual);
    }

    private static class TestWeightedImpl implements Weighted {

        private final int weight;

        TestWeightedImpl(int weight) {
            this.weight = weight;
        }

        @Override
        public int getWeight() {
            return weight;
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof Weighted && Objects.equals(weight, ((Weighted) obj).getWeight());
        }

        @Override
        public int hashCode() {
            return Objects.hash(weight);
        }
    }
}
