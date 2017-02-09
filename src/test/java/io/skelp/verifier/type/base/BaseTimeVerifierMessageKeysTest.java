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
package io.skelp.verifier.type.base;

import java.util.HashMap;
import java.util.Map;

import io.skelp.verifier.message.MessageKeyEnumTestCase;

/**
 * <p>
 * Tests for the {@link BaseTimeVerifier.MessageKeys} enum.
 * </p>
 *
 * @author Alasdair Mercer
 */
public class BaseTimeVerifierMessageKeysTest extends MessageKeyEnumTestCase<BaseTimeVerifier.MessageKeys> {

    @Override
    protected Class<? extends Enum> getEnumClass() {
        return BaseTimeVerifier.MessageKeys.class;
    }

    @Override
    protected Map<String, String> getMessageKeys() {
        Map<String, String> messageKeys = new HashMap<>();
        messageKeys.put("SAME_DAY_AS", "io.skelp.verifier.type.base.BaseTimeVerifier.sameDayAs");
        messageKeys.put("SAME_ERA_AS", "io.skelp.verifier.type.base.BaseTimeVerifier.sameEraAs");
        messageKeys.put("SAME_HOUR_AS", "io.skelp.verifier.type.base.BaseTimeVerifier.sameHourAs");
        messageKeys.put("SAME_MINUTE_AS", "io.skelp.verifier.type.base.BaseTimeVerifier.sameMinuteAs");
        messageKeys.put("SAME_MONTH_AS", "io.skelp.verifier.type.base.BaseTimeVerifier.sameMonthAs");
        messageKeys.put("SAME_SECOND_AS", "io.skelp.verifier.type.base.BaseTimeVerifier.sameSecondAs");
        messageKeys.put("SAME_TIME_AS", "io.skelp.verifier.type.base.BaseTimeVerifier.sameTimeAs");
        messageKeys.put("SAME_WEEK_AS", "io.skelp.verifier.type.base.BaseTimeVerifier.sameWeekAs");
        messageKeys.put("SAME_YEAR_AS", "io.skelp.verifier.type.base.BaseTimeVerifier.sameYearAs");

        return messageKeys;
    }
}
