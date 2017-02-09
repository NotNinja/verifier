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
package io.skelp.verifier;

import java.util.HashMap;
import java.util.Map;

import io.skelp.verifier.message.MessageKeyEnumTestCase;

/**
 * <p>
 * Tests for the {@link AbstractCustomVerifier.MessageKeys} enum.
 * </p>
 *
 * @author Alasdair Mercer
 */
public class AbstractCustomVerifierMessageKeysTest extends MessageKeyEnumTestCase<AbstractCustomVerifier.MessageKeys> {

    @Override
    protected Class<? extends Enum> getEnumClass() {
        return AbstractCustomVerifier.MessageKeys.class;
    }

    @Override
    protected Map<String, String> getMessageKeys() {
        Map<String, String> messageKeys = new HashMap<>();
        messageKeys.put("EQUAL_TO", "io.skelp.verifier.AbstractCustomVerifier.equalTo");
        messageKeys.put("EQUAL_TO_ANY", "io.skelp.verifier.AbstractCustomVerifier.equalToAny");
        messageKeys.put("HASHED_AS", "io.skelp.verifier.AbstractCustomVerifier.hashedAs");
        messageKeys.put("INSTANCE_OF", "io.skelp.verifier.AbstractCustomVerifier.instanceOf");
        messageKeys.put("INSTANCE_OF_ALL", "io.skelp.verifier.AbstractCustomVerifier.instanceOfAll");
        messageKeys.put("INSTANCE_OF_ANY", "io.skelp.verifier.AbstractCustomVerifier.instanceOfAny");
        messageKeys.put("NULLED", "io.skelp.verifier.AbstractCustomVerifier.nulled");
        messageKeys.put("SAME_AS", "io.skelp.verifier.AbstractCustomVerifier.sameAs");
        messageKeys.put("SAME_AS_ANY", "io.skelp.verifier.AbstractCustomVerifier.sameAsAny");

        return messageKeys;
    }
}
