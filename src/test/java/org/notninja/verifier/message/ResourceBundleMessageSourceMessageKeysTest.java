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

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * Tests for the {@link ResourceBundleMessageSource.MessageKeys} enum.
 * </p>
 *
 * @author Alasdair Mercer
 */
public class ResourceBundleMessageSourceMessageKeysTest extends MessageKeyEnumTestCase<ResourceBundleMessageSource.MessageKeys> {

    @Override
    protected Class<? extends Enum> getEnumClass() {
        return ResourceBundleMessageSource.MessageKeys.class;
    }

    @Override
    protected Map<String, String> getMessageKeys() {
        Map<String, String> messageKeys = new HashMap<>();
        messageKeys.put("DEFAULT_MESSAGE", "org.notninja.verifier.message.ResourceBundleMessageSource.default.message.normal");
        messageKeys.put("DEFAULT_MESSAGE_NEGATED", "org.notninja.verifier.message.ResourceBundleMessageSource.default.message.negated");
        messageKeys.put("DEFAULT_NAME", "org.notninja.verifier.message.ResourceBundleMessageSource.default.name");
        messageKeys.put("MESSAGE", "org.notninja.verifier.message.ResourceBundleMessageSource.message.normal");
        messageKeys.put("MESSAGE_NEGATED", "org.notninja.verifier.message.ResourceBundleMessageSource.message.negated");

        return messageKeys;
    }
}
