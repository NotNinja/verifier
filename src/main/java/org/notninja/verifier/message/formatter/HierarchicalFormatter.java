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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.notninja.verifier.verification.Verification;

/**
 * <p>
 * An abstract implementation of {@link Formatter} which transforms the children within a given hierarchical object into
 * a formatted string. The hierarchy is formatted recursively while preventing infinite loops by highlighting circular
 * references.
 * </p>
 *
 * @author Alasdair Mercer
 * @since 0.2.0
 */
public abstract class HierarchicalFormatter implements Formatter {

    /**
     * <p>
     * Appends specified {@code child} to the {@code buffer} provided.
     * </p>
     * <p>
     * If {@code child} is {@literal null}, {@code "null"} is appended, and if {@code child} already exists within the
     * {@code hierarchy} (i.e. is a circular reference), then {@code "circular"} is appended. If a {@link Formatter}
     * exists that supports {@code child}, it will be used to format it and the result will be appended to
     * {@code buffer}. Otherwise, {@code child} is appended directly.
     * </p>
     * <p>
     * If {@code child} itself is hierarchical, then {@link #format(Verification, Object, List)} will be called on the
     * supporting {@link HierarchicalFormatter} to format it and its own children recursively.
     * </p>
     *
     * @param verification
     *         the current {@link Verification}
     * @param child
     *         the child object to be potentially formatted and appended to {@code buffer}
     * @param hierarchy
     *         a {@code List} containing all parents of {@code child}
     * @param buffer
     *         the {@code StringBuilder} to which the potentially formatted {@code child} is to be appended
     */
    protected void appendChild(final Verification<?> verification, final Object child, final List<Object> hierarchy, final StringBuilder buffer) {
        if (child == null) {
            buffer.append("null");
        } else if (hierarchy.contains(child)) {
            buffer.append("circular");
        } else {
            final Formatter formatter = verification.getFormatter(child);

            if (formatter instanceof HierarchicalFormatter) {
                buffer.append(((HierarchicalFormatter) formatter).format(verification, child, hierarchy));
            } else {
                buffer.append(getChildrenWrapper());
                buffer.append(formatter != null ? formatter.format(verification, child) : child);
                buffer.append(getChildrenWrapper());
            }
        }
    }

    @Override
    public String format(final Verification<?> verification, final Object obj) {
        return format(verification, obj, Collections.singletonList(obj));
    }

    /**
     * <p>
     * Formats the specified {@code parent} and all of its children into a string.
     * </p>
     *
     * @param verification
     *         the current {@link Verification}
     * @param parent
     *         the parent object to be formatted along with its children
     * @param hierarchy
     *         a {@code List} containing all parents of {@code parent}
     * @return A formatted string based on {@code obj}.
     */
    protected String format(final Verification<?> verification, final Object parent, final List<Object> hierarchy) {
        final Iterator<Object> iterator = getChildren(parent).iterator();
        final StringBuilder buffer = new StringBuilder(getStartTag());
        final List<Object> currentHierarchy = new ArrayList<>(hierarchy);
        currentHierarchy.add(parent);

        while (iterator.hasNext()) {
            final Object child = iterator.next();

            appendChild(verification, child, currentHierarchy, buffer);

            if (iterator.hasNext()) {
                buffer.append(getChildrenSeparator());
            }
        }

        return buffer.append(getEndTag()).toString();
    }

    /**
     * <p>
     * Returns the children for the specified {@code parent} to be formatted by this {@link HierarchicalFormatter}.
     * </p>
     *
     * @param parent
     *         the hierarchical object whose children are to be returned
     * @return A {@code Collection} of children within {@code parent}.
     */
    protected abstract Collection<Object> getChildren(Object parent);

    /**
     * <p>
     * Returns the string used to separate potentially formatted children in the formatted output.
     * </p>
     *
     * @return The separator inserted between children.
     */
    protected String getChildrenSeparator() {
        return ", ";
    }

    /**
     * <p>
     * Returns the string used to wrap potentially formatted children in the formatted output.
     * </p>
     *
     * @return The wrapper inserted before and after each child.
     */
    protected String getChildrenWrapper() {
        return "'";
    }

    /**
     * <p>
     * Returns the string used to terminate the formatted output.
     * </p>
     *
     * @return The tag inserted at the end.
     * @see #getStartTag()
     */
    protected String getEndTag() {
        return "}";
    }

    /**
     * <p>
     * Returns the string used to begin the formatted output.
     * </p>
     *
     * @return The tag inserted at the start.
     * @see #getEndTag()
     */
    protected String getStartTag() {
        return "{";
    }
}
