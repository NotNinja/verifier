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
package org.notninja.verifier.message.formatter;

import java.util.Arrays;
import java.util.Collection;

import org.notninja.verifier.util.ArrayUtils;

/**
 * <p>
 * An implementation of {@link Formatter} which transforms the elements within a {@code Collection} or an array into a
 * string. The collection of elements is formatted recursively while preventing infinite loops by highlighting circular
 * references.
 * </p>
 *
 * @author Alasdair Mercer
 * @since 0.2.0
 */
public final class CollectionFormatter extends HierarchicalFormatter {

    @Override
    protected Collection<Object> getChildren(final Object parent) {
        return ArrayUtils.isArray(parent) ? Arrays.asList((Object[]) parent) : (Collection) parent;
    }

    @Override
    public boolean supports(final Class<?> cls) {
        return Collection.class.isAssignableFrom(cls) || cls.isArray();
    }

    @Override
    protected String getEndTag() {
        return "]";
    }

    @Override
    protected String getStartTag() {
        return "[";
    }
}
