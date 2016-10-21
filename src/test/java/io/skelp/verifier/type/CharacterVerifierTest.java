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
package io.skelp.verifier.type;

import org.junit.Ignore;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import io.skelp.verifier.AbstractCustomVerifierTestCase;
import io.skelp.verifier.CustomVerifierTestCaseBase;
import io.skelp.verifier.type.base.BaseComparableVerifierTestCase;
import io.skelp.verifier.type.base.BaseTruthVerifierTestCase;

/**
 * Tests for the {@link CharacterVerifier} class.
 *
 * @author Alasdair Mercer
 */
@RunWith(Enclosed.class)
public class CharacterVerifierTest {

    public static class CharacterVerifierAbstractCustomVerifierTest extends AbstractCustomVerifierTestCase<Character, CharacterVerifier> {

        @Override
        protected CharacterVerifier createCustomVerifier() {
            return new CharacterVerifier(getMockVerification());
        }

        @Override
        protected Character createValueOne() {
            return new Character('a');
        }

        @Override
        protected Character createValueTwo() {
            return new Character('z');
        }

        @Override
        protected Class<?> getParentClass() {
            return Object.class;
        }

        @Override
        protected Class<?> getValueClass() {
            return Character.class;
        }
    }

    public static class CharacterVerifierBaseComparableVerifierTest extends BaseComparableVerifierTestCase<Character, CharacterVerifier> {

        @Override
        protected CharacterVerifier createCustomVerifier() {
            return new CharacterVerifier(getMockVerification());
        }

        @Override
        protected Character getBaseValue() {
            return 'm';
        }

        @Override
        protected Character getHigherValue() {
            return 't';
        }

        @Override
        protected Character getHighestValue() {
            return 'z';
        }

        @Override
        protected Character getLowerValue() {
            return 'f';
        }

        @Override
        protected Character getLowestValue() {
            return 'a';
        }
    }

    public static class CharacterVerifierBaseTruthVerifierTest extends BaseTruthVerifierTestCase<Character, CharacterVerifier> {

        @Override
        protected CharacterVerifier createCustomVerifier() {
            return new CharacterVerifier(getMockVerification());
        }

        @Override
        protected Character[] getFalsehoodValues() {
            return new Character[]{'0'};
        }

        @Override
        protected Character[] getTruthValues() {
            return new Character[]{'1'};
        }
    }

    @Ignore
    public static class CharacterVerifierMiscTest extends CustomVerifierTestCaseBase<Character, CharacterVerifier> {

        // TODO: Complete

        @Override
        protected CharacterVerifier createCustomVerifier() {
            return new CharacterVerifier(getMockVerification());
        }
    }
}
