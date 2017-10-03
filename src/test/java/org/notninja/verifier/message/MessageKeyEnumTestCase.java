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

import static org.junit.Assert.*;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import org.junit.Test;

import org.notninja.verifier.util.TestUtils;

/**
 * <p>
 * Test case for {@link MessageKey} implementation enums.
 * </p>
 *
 * @param <T>
 *         the enum implementation of {@link MessageKey}
 * @author Alasdair Mercer
 */
public abstract class MessageKeyEnumTestCase<T extends MessageKey> {

    @Test
    public void hackCoverage() throws Exception {
        // TODO: Determine how to avoid this
        T value = getEnumValues()[0];
        assertEquals(value, getEnumValueOf(getEnumName(value)));
    }

    @Test
    public void testMessageKeys() {
        ResourceBundle bundle = ResourceBundle.getBundle("Verifier", new Locale(""));
        Map<String, String> messageKeys = getMessageKeys();
        T[] values = getEnumValues();

        assertEquals("Has correct number of values", messageKeys.size(), values.length);

        for (T key : values) {
            testMessageKeysHelper(bundle, key, messageKeys);
        }
    }

    private void testMessageKeysHelper(ResourceBundle bundle, T key, Map<String, String> messageKeys) {
        String name = getEnumName(key);
        assertTrue("Enum is expected with name '" + name + "'", messageKeys.containsKey(name));
        assertEquals("Enum has correct code", messageKeys.get(name), key.code());

        String message = bundle.getString(key.code());
        assertNotNull("Message exists for code in resource bundle", message);
        assertFalse("Message is not empty", message.isEmpty());
        assertNotNull("Message can be formatted", new MessageFormat(message));
    }

    /**
     * <p>
     * Returns the enum class for testing the enum names and values.
     * </p>
     *
     * @return The enum class.
     */
    protected abstract Class<? extends Enum> getEnumClass();

    /**
     * <p>
     * Returns a mapping of expected enum names and message key codes.
     * </p>
     *
     * @return A {@code Map} of enum names and {@link MessageKey} codes.
     */
    protected abstract Map<String, String> getMessageKeys();

    private String getEnumName(T value) {
        return value.toString();
    }

    @SuppressWarnings("unchecked")
    private T getEnumValueOf(String name) throws ReflectiveOperationException {
        return (T) TestUtils.invokeStaticMethodAndReturn(getEnumClass(), "valueOf", new Object[]{name});
    }

    @SuppressWarnings("unchecked")
    private T[] getEnumValues() {
        return (T[]) getEnumClass().getEnumConstants();
    }
}
