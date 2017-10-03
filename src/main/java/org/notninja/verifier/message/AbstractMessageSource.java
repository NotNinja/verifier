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

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.notninja.verifier.message.formatter.Formatter;
import org.notninja.verifier.util.ArrayUtils;
import org.notninja.verifier.verification.Verification;

/**
 * <p>
 * An abstract implementation of {@link MessageSource} that provides common handling of message variants.
 * </p>
 * <p>
 * While implementations are required to implement {@link #resolveKey(MessageKey, Verification)}, they are also
 * encouraged to override {@link #resolveKeyWithoutArguments(MessageKey, Verification)} to optimize the resolution of
 * messages from {@link MessageKey MessageKeys} without the use of {@code MessageFormat}.
 * </p>
 * <p>
 * By default, messages are only parsed through {@code MessageFormat} if format arguments have also been passed in and,
 * in the case of no arguments, the messages are returned as-is. As a consequence, {@code MessageFormat} escaping should
 * only be used for messages with actual arguments and all others should be unescaped. If having all messages escaped is
 * preferred, then the {@code alwaysUseMessageFormat} flag should be enabled.
 * </p>
 * <p>
 * All created {@code MessageFormats} are cached based on the message and {@code Locale} (provided by the current
 * {@link Verification}) and this class allows these, and anything else cached by child implementations, to be cleared
 * easily using {@link #clearCache()}.
 * </p>
 *
 * @author Alasdair Mercer
 * @since 0.2.0
 */
public abstract class AbstractMessageSource implements MessageSource {

    /**
     * <p>
     * The {@code MessageFormat} used when messages have invalid format patterns and the {@code alwaysUseMessageFormat}
     * flag is disabled.
     * </p>
     */
    protected static final MessageFormat INVALID_MESSAGE_FORMAT = new MessageFormat("", new Locale(""));

    private static final Object[] EMPTY_ARRAY = new Object[0];

    private boolean alwaysUseMessageFormat;
    private final Map<String, Map<Locale, MessageFormat>> messageFormatsPerMessage = new HashMap<>();
    private boolean useKeyAsDefaultMessage;

    /**
     * <p>
     * Builds the final message to be returned by either of the {@link #getMessage} methods that should contain the
     * prepared {@code message} provided as well as the specified resolved {@code name} and {@code value} to provide
     * context to the message.
     * </p>
     * <p>
     * It is also expected that implementations return different messages depending on whether or not the given
     * {@code verification} has been negated.
     * </p>
     *
     * @param message
     *         the main message that is to be incorporated into the full contextual message
     * @param name
     *         the resolved name used to represent {@code value}
     * @param value
     *         the resolved value that is being verified
     * @param verification
     *         the current {@link Verification}
     * @return The full contextual message built from the information provided.
     * @throws NoSuchMessageException
     *         If no message could be found for any keys that are required to build the full message.
     */
    protected abstract String buildMessage(final String message, final Object name, final Object value, final Verification<?> verification);

    /**
     * <p>
     * Clears anything that has been cached by this {@link MessageSource}.
     * </p>
     */
    public void clearCache() {
        synchronized (messageFormatsPerMessage) {
            messageFormatsPerMessage.clear();
        }
    }

    /**
     * <p>
     * Creates a message format for the specified {@code message} based on the {@code verification} provided.
     * </p>
     * <p>
     * If {@code message} is {@literal null}, then a message format for an empty string will be returned.
     * </p>
     *
     * @param message
     *         the message for which the {@code MessageFormat} is to be created (may be {@literal null})
     * @param verification
     *         the current {@link Verification}
     * @return A {@code MessageFormat} for {@code message}.
     * @throws IllegalArgumentException
     *         If {@code message} is an invalid format pattern.
     */
    protected MessageFormat createMessageFormat(final String message, final Verification<?> verification) {
        return new MessageFormat(message != null ? message : "", verification.getLocale());
    }

    /**
     * <p>
     * Attempts to format the specified {@code message} with the optional format {@code args} provided.
     * </p>
     * <p>
     * All {@code MessageFormats} that are created by this method are cached based on {@code message} and the
     * {@code Locale} contained within {@code verification} to optimize subsequent calls to this method for the same
     * message.
     * </p>
     * <p>
     * This method will return {@literal null} if {@code message} is {@literal null} and will call
     * {@link #formatMessageWithoutArguments(String, Verification)}, where appropriate.
     * </p>
     *
     * @param message
     *         the message to be formatted with {@code args} (may be {@literal null})
     * @param args
     *         the optional format arguments which are used to format {@code message} (may be {@literal null})
     * @param verification
     *         the current {@link Verification}
     * @return Then potentially formatted {@code message} or {@literal null} if {@code message} is {@literal null}.
     * @throws IllegalArgumentException
     *         If {@code message} is formatted but is an invalid format pattern or any of the format {@code args} are
     *         invalid for their placeholders.
     * @see #formatMessageWithoutArguments(String, Verification)
     */
    protected String formatMessage(final String message, final Object[] args, final Verification<?> verification) {
        if (message == null) {
            return null;
        }
        if (!isAlwaysUseMessageFormat() && ArrayUtils.isEmpty(args)) {
            return formatMessageWithoutArguments(message, verification);
        }

        final Locale locale = verification.getLocale();
        MessageFormat messageFormat = null;

        synchronized (messageFormatsPerMessage) {
            Map<Locale, MessageFormat> messageFormatsPerLocale = messageFormatsPerMessage.get(message);

            if (messageFormatsPerLocale != null) {
                messageFormat = messageFormatsPerLocale.get(locale);
            } else {
                messageFormatsPerLocale = new HashMap<>();
                messageFormatsPerMessage.put(message, messageFormatsPerLocale);
            }

            if (messageFormat == null) {
                try {
                    messageFormat = createMessageFormat(message, verification);
                } catch (IllegalArgumentException e) {
                    if (isAlwaysUseMessageFormat()) {
                        throw e;
                    }

                    messageFormat = INVALID_MESSAGE_FORMAT;
                }

                messageFormatsPerLocale.put(locale, messageFormat);
            }
        }

        if (messageFormat == INVALID_MESSAGE_FORMAT) {
            return message;
        }

        synchronized (messageFormat) {
            return messageFormat.format(resolveArguments(args, verification));
        }
    }

    /**
     * <p>
     * Formats the specified {@code message} without any arguments using the {@code verification} provided.
     * </p>
     * <p>
     * By default, this method simply returns {@code message} but implementations are free to override this, if
     * required.
     * </p>
     *
     * @param message
     *         the message to be formatted (may be {@literal null})
     * @param verification
     *         the current {@link Verification}
     * @return The {@code message} formatted without any arguments or {@literal null} if {@code message} is {@literal
     * null}.
     * @see #formatMessage(String, Object[], Verification)
     */
    protected String formatMessageWithoutArguments(final String message, final Verification<?> verification) {
        return message;
    }

    /**
     * <p>
     * Returns the default message to be used when none is given using the {@link Verification} provided.
     * </p>
     * <p>
     * Implementations should ensure that the default message fits well when {@code verification} is negated and when
     * it's not as {@link #buildMessage(String, Object, Object, Verification)} is encouraged to use different base
     * messages depending on the negated state of {@code verification}.
     * </p>
     *
     * @param verification
     *         the current {@link Verification}
     * @return The default message.
     * @throws NoSuchMessageException
     *         If the default message is looked up using a message key that could not be found.
     */
    protected abstract String getDefaultMessage(Verification<?> verification);

    /**
     * <p>
     * Returns the default message to be used when no message could be found for the specified {@code key} using the
     * {@code verification} provided.
     * </p>
     * <p>
     * By default, this method will only return the code for {@code key} if {@link #isUseKeyAsDefaultMessage()} returns
     * {@literal true} and {@code key} is not {@literal null}.
     * </p>
     *
     * @param key
     *         the {@link MessageKey} of the message that could not be found (may be {@literal null})
     * @param verification
     *         the current {@link Verification}
     * @return The code of {@code key} if configured to use it and {@code key} is not {@literal null}; otherwise
     * {@literal null}.
     * @throws NoSuchMessageException
     *         If the default message is looked up using a message key that could not be found.
     */
    protected String getDefaultMessage(final MessageKey key, final Verification<?> verification) {
        return isUseKeyAsDefaultMessage() && key != null ? key.code() : null;
    }

    /**
     * <p>
     * Returns the default name to be used to represent the value being verified using the {@code verification}
     * provided.
     * </p>
     *
     * @param verification
     *         the current {@link Verification}
     * @return The default name.
     * @throws NoSuchMessageException
     *         If the default name is looked up using a message key that could not be found.
     */
    protected abstract Object getDefaultName(Verification<?> verification);

    @Override
    public String getMessage(final Verification<?> verification, final MessageKey key, final Object[] args) {
        final String formattedMessage = getMessageInternal(key, args, verification);
        if (formattedMessage != null) {
            return buildMessage(formattedMessage, verification);
        }

        final String defaultMessage = getDefaultMessage(key, verification);
        if (defaultMessage != null) {
            return buildMessage(defaultMessage, verification);
        }

        throw new NoSuchMessageException(key, verification.getLocale());
    }

    @Override
    public String getMessage(final Verification<?> verification, final String message, final Object[] args) {
        final String formattedMessage = getMessageInternal(message, args, verification);
        if (formattedMessage != null) {
            return buildMessage(formattedMessage, verification);
        }

        final String defaultMessage = getDefaultMessage(verification);
        return buildMessage(defaultMessage, verification);
    }

    /**
     * <p>
     * Called internally by {@link #getMessage(Verification, MessageKey, Object[])} to handle the actual logic of
     * looking up a message based on the specified {@code key} and potentially formatting it using the optional format
     * {@code args} provided.
     * </p>
     * <p>
     * This method can be used in other cases where only the resolved message is desired without all of the other
     * trimmings.
     * </p>
     * <p>
     * This method will return {@literal null} if {@code key} is {@literal null} or no message could be found for
     * {@code key} and will call {@link #resolveKeyWithoutArguments(MessageKey, Verification)}, where appropriate.
     * </p>
     *
     * @param key
     *         the {@link MessageKey} of the message to be looked up, potentially formatted, and returned (may be
     *         {@literal null})
     * @param args
     *         the optional format arguments which are used to format the resolved message (may be {@literal null})
     * @param verification
     *         the current {@link Verification}
     * @return The potentially formatted message for {@code key} or {@literal null} if {@code key} is {@literal null} or
     * no message could be found for {@code key}.
     * @throws IllegalArgumentException
     *         If the resolved message is formatted but is an invalid format pattern or any of the format {@code args}
     *         are invalid for their placeholders.
     * @see #getMessage(Verification, MessageKey, Object[])
     */
    protected String getMessageInternal(final MessageKey key, final Object[] args, final Verification<?> verification) {
        if (key == null) {
            return null;
        }

        if (!isAlwaysUseMessageFormat() && ArrayUtils.isEmpty(args)) {
            return resolveKeyWithoutArguments(key, verification);
        }

        final Object[] resolvedArgs = resolveArguments(args, verification);
        final MessageFormat messageFormat = resolveKey(key, verification);
        if (messageFormat != null) {
            synchronized (messageFormat) {
                return messageFormat.format(resolvedArgs);
            }
        }

        return null;
    }

    /**
     * <p>
     * Called internally by {@link #getMessage(Verification, String, Object[])} to handle the actual logic of
     * potentially formatting {@code message} using the optional format {@code args} provided.
     * </p>
     * <p>
     * This method can be used in other cases where only the resolved message is desired without all of the other
     * trimmings.
     * </p>
     * <p>
     * This method will return {@literal null} if {@code message} is {@literal null} and will call
     * {@link #formatMessageWithoutArguments(String, Verification)}, where appropriate.
     * </p>
     *
     * @param message
     *         the message to be potentially formatted and returned (may be {@literal null})
     * @param args
     *         the optional format arguments which are used to format {@code message} (may be {@literal null})
     * @param verification
     *         the current {@link Verification}
     * @return The potentially formatted {@code message} or {@literal null} if {@code message} is {@literal null}.
     * @throws IllegalArgumentException
     *         If {@code message} is formatted but is an invalid format pattern or any of the format {@code args} are
     *         invalid for their placeholders.
     * @see #getMessage(Verification, String, Object[])
     */
    protected String getMessageInternal(final String message, final Object[] args, final Verification<?> verification) {
        if (message == null) {
            return null;
        }

        if (!isAlwaysUseMessageFormat() && ArrayUtils.isEmpty(args)) {
            return formatMessageWithoutArguments(message, verification);
        }

        final Object[] resolvedArgs = resolveArguments(args, verification);

        return formatMessage(message, resolvedArgs, verification);
    }

    /**
     * <p>
     * Resolves all of the format arguments provided using the specified {@code verification} by attempting to looking
     * up a {@link Formatter} for each argument and, where found, formatting the argument.
     * </p>
     * <p>
     * This method will return a new array copy of {@code args} where any argument that can potentially be formatted, is
     * replaced by its formatted representation. If {@code args} is {@literal null}, an empty array will be returned.
     * </p>
     *
     * @param args
     *         the format arguments to be resolved (may be {@literal null})
     * @param verification
     *         the current {@link Verification}
     * @return A new array containing all resolved format {@code args}.
     */
    protected Object[] resolveArguments(final Object[] args, final Verification<?> verification) {
        if (args == null) {
            return EMPTY_ARRAY;
        }

        return Arrays.stream(args)
            .map(arg -> tryFormat(arg, verification))
            .toArray();
    }

    /**
     * <p>
     * Resolves the message for the specified {@code key} and returns a {@code MessageFormat} for that message to be
     * formatted.
     * </p>
     *
     * @param key
     *         the {@link MessageKey} of the message to be resolved
     * @param verification
     *         the current {@link Verification}
     * @return A {@code MessageFormat} for the message resolved for {@code key} or {@literal null} if no message could
     * be found for {@code key}.
     * @throws IllegalArgumentException
     *         If the resolved message is formatted but is an invalid format pattern or any of the format {@code args}
     *         are invalid for their placeholders.
     * @see #resolveKeyWithoutArguments(MessageKey, Verification)
     */
    protected abstract MessageFormat resolveKey(MessageKey key, Verification<?> verification);

    /**
     * <p>
     * Resolves the message for the specified {@code key} without any arguments using the {@code verification} provided.
     * </p>
     * <p>
     * By default, this method still uses a {@code MessageFormat} instance (obtained via {@link
     * #resolveKey(MessageKey, Verification)}) to format the resolved message, however, implementations are encouraged
     * to override this behavior to avoid using the {@code MessageFormat} instance and optimize performance.
     * </p>
     *
     * @param key
     *         the {@link MessageKey} of the message to be resolved without using any format arguments
     * @param verification
     *         the current {@link Verification}
     * @return The message resolved for {@code key} without using any format arguments or {@literal null} if no message
     * could be found for {@code key}.
     * @throws IllegalArgumentException
     *         If resolved message is formatted but is an invalid format pattern or any of the format {@code args} are
     *         invalid for their placeholders.
     * @see #resolveKey(MessageKey, Verification)
     */
    protected String resolveKeyWithoutArguments(final MessageKey key, final Verification<?> verification) {
        final MessageFormat messageFormat = resolveKey(key, verification);
        if (messageFormat != null) {
            synchronized (messageFormat) {
                return messageFormat.format(EMPTY_ARRAY);
            }
        }

        return null;
    }

    /**
     * <p>
     * Resolves the specified {@code name} which is used to represent the value being verified using the
     * {@code verification} provided.
     * </p>
     * <p>
     * If {@code name} is {@literal null}, the default name will be used instead. Otherwise, this method will attempt to
     * lookup a {@link Formatter} for {@code name} and, where found, will return the formatted representation instead of
     * {@code name}.
     * </p>
     *
     * @param name
     *         the name used to represent the value being verified to be resolved (may be {@literal null})
     * @param verification
     *         the current {@link Verification}
     * @return The potentially formatted {@code name} or the default name if {@code name} is {@literal null}.
     * @see #getDefaultName(Verification)
     */
    protected Object resolveName(final Object name, final Verification<?> verification) {
        return name != null ? tryFormat(name, verification) : getDefaultName(verification);
    }

    /**
     * <p>
     * Resolves the specified {@code value} using the {@code verification} provided by attempting to lookup a
     * {@link Formatter} for {@code value}, where found, return the formatted representation instead of {@code value}.
     * </p>
     *
     * @param value
     *         the value being verified to be resolved (may be {@literal null})
     * @param verification
     *         the current {@link Verification}
     * @return The potentially formatted {@code value} or {@literal null} if {@code value} is {@literal null}.
     */
    protected Object resolveValue(final Object value, final Verification<?> verification) {
        return tryFormat(value, verification);
    }

    /**
     * <p>
     * Attempts to lookup a {@link Formatter} for the specified object and, where found, will format the object using
     * the {@code verification} provided.
     * </p>
     * <p>
     * This method will return {@code obj} if no {@link Formatter} could be found or {@code obj} is {@literal null}.
     * </p>
     *
     * @param obj
     *         the object to be potentially formatted (may be {@literal null})
     * @param verification
     *         the current {@link Verification}
     * @return The potentially formatted {@code obj} or {@literal null} if {@code obj} is {@literal null}.
     * @see Verification#getFormatter(Object)
     */
    protected Object tryFormat(final Object obj, final Verification<?> verification) {
        final Formatter formatter = verification.getFormatter(obj);
        return formatter != null ? formatter.format(verification, obj) : obj;
    }

    private String buildMessage(final String message, final Verification<?> verification) {
        final Object name = resolveName(verification.getName(), verification);
        final Object value = resolveValue(verification.getValue(), verification);

        return buildMessage(message, name, value, verification);
    }

    /**
     * <p>
     * Returns whether to always use {@code MessageFormats}, even when no format arguments are provided.
     * </p>
     * <p>
     * While having this disabled (which it is by default) can boost performance, it does have consequences. When
     * enabled you must ensure that all messages are written with {@code MessageFormat} escaping but, when disabled, you
     * have to ensure that only messages with format argument placeholders are written this way. For example;
     * {@code MessageFormat} expects a single quote to be escaped as {@code "''"}.
     * </p>
     *
     * @return {@literal true} if {@code MessageFormats} should always be used to format messages; otherwise {@literal
     * false}.
     */
    public boolean isAlwaysUseMessageFormat() {
        return alwaysUseMessageFormat;
    }

    /**
     * <p>
     * Sets whether to always use {@code MessageFormats}, even when no format arguments are provided, to
     * {@code alwaysUseMessageFormat}.
     * </p>
     * <p>
     * While having this disabled (which it is by default) can boost performance, it does have consequences. When
     * enabled you must ensure that all messages are written with {@code MessageFormat} escaping but, when disabled, you
     * have to ensure that only messages with format argument placeholders are written this way. For example;
     * {@code MessageFormat} expects a single quote to be escaped as {@code "''"}.
     * </p>
     *
     * @param alwaysUseMessageFormat
     *         {@literal true} if the {@link MessageKey} should be used as the default message; otherwise {@literal
     *         false}
     */
    public void setAlwaysUseMessageFormat(final boolean alwaysUseMessageFormat) {
        this.alwaysUseMessageFormat = alwaysUseMessageFormat;
    }

    /**
     * <p>
     * Returns whether the code of the {@link MessageKey} (where applicable) should be used as the default message
     * instead of throwing a {@link NoSuchMessageException}, which can be useful for developing and/or debugging
     * Verifier itself.
     * </p>
     *
     * @return {@literal true} if the {@link MessageKey} should be used as the default message; otherwise {@literal
     * false}.
     */
    public boolean isUseKeyAsDefaultMessage() {
        return useKeyAsDefaultMessage;
    }

    /**
     * <p>
     * Sets whether the code of the {@link MessageKey} (where applicable) should be used as the default message
     * instead of throwing a {@link NoSuchMessageException}, which can be useful for developing and/or debugging
     * Verifier itself, to {@code useKeyAsDefaultMessage}.
     * </p>
     *
     * @param useKeyAsDefaultMessage
     *         {@literal true} if the {@link MessageKey} should be used as the default message; otherwise {@literal
     *         false}
     */
    public void setUseKeyAsDefaultMessage(final boolean useKeyAsDefaultMessage) {
        this.useKeyAsDefaultMessage = useKeyAsDefaultMessage;
    }
}
