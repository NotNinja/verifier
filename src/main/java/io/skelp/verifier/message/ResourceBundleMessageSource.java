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
package io.skelp.verifier.message;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;

import io.skelp.verifier.verification.Verification;

/**
 * <p>
 * An implementation of {@link MessageSource} that resolves {@link MessageKey MessageKeys} by looking up their code in
 * configurable {@code ResourceBundles} with support for multiple base names.
 * </p>
 * <p>
 * All created {@code MessageFormats} are cached based on the message and {@code Locale} (provided by the current
 * {@link Verification}) and all loaded {@code ResourceBundles} are cached based on the base name and {@code Locale},
 * which this class allows, as well as anything else cached by child implementations, to be cleared easily using
 * {@link #clearCache()}.
 * </p>
 *
 * @author Alasdair Mercer
 * @since 0.2.0
 */
public class ResourceBundleMessageSource extends AbstractMessageSource {

    /** The default resource bundle base names. */
    public static final String[] DEFAULT_BASE_NAMES = {"Verifier"};

    private final Set<String> baseNames;
    private final Map<ResourceBundle, Map<MessageKey, Map<Locale, MessageFormat>>> messageFormatsPerResourceBundle = new HashMap<>();
    private final Map<String, Map<Locale, ResourceBundle>> resourceBundlesPerBaseName = new HashMap<>();

    /**
     * <p>
     * Creates an instance of {@link ResourceBundleMessageSource} with only the default resource bundle base names.
     * </p>
     *
     * @see #DEFAULT_BASE_NAMES
     */
    public ResourceBundleMessageSource() {
        this(Arrays.asList(DEFAULT_BASE_NAMES));
    }

    /**
     * <p>
     * Creates an instance of {@link ResourceBundleMessageSource} with the specified resource bundle {@code baseNames}.
     * </p>
     *
     * @param baseNames
     *         the {@code Collection} of base names to be used (may be {@literal null})
     */
    public ResourceBundleMessageSource(final Collection<String> baseNames) {
        this.baseNames = new LinkedHashSet<>();
        if (baseNames != null) {
            this.baseNames.addAll(baseNames);
        }
    }

    /**
     * <p>
     * Creates an instance of {@link ResourceBundleMessageSource} with the specified resource bundle {@code baseNames}.
     * </p>
     *
     * @param baseNames
     *         the base names to be used (may be {@literal null})
     */
    public ResourceBundleMessageSource(final String... baseNames) {
        this(baseNames != null ? Arrays.asList(baseNames) : null);
    }

    @Override
    protected String buildMessage(final String message, final Object name, final Object value, final Verification<?> verification) {
        final MessageKey key = verification.isNegated() ? MessageKeys.MESSAGE_NEGATED : MessageKeys.MESSAGE;
        final String finalMessage = getMessageInternal(key, new Object[]{name, message, value}, verification);
        if (finalMessage == null) {
            throw new NoSuchMessageException(key, verification.getLocale());
        }

        return finalMessage;
    }

    @Override
    public void clearCache() {
        super.clearCache();

        synchronized (messageFormatsPerResourceBundle) {
            messageFormatsPerResourceBundle.clear();
        }
        synchronized (resourceBundlesPerBaseName) {
            resourceBundlesPerBaseName.clear();
        }
    }

    @Override
    protected String getDefaultMessage(final Verification<?> verification) {
        final MessageKey key = verification.isNegated() ? MessageKeys.DEFAULT_MESSAGE_NEGATED : MessageKeys.DEFAULT_MESSAGE;
        final String defaultMessage = getMessageInternal(key, null, verification);
        if (defaultMessage == null) {
            throw new NoSuchMessageException(key, verification.getLocale());
        }

        return defaultMessage;
    }

    @Override
    protected Object getDefaultName(final Verification<?> verification) {
        final MessageKey key = MessageKeys.DEFAULT_NAME;
        final String defaultName = getMessageInternal(key, null, verification);
        if (defaultName == null) {
            throw new NoSuchMessageException(key, verification.getLocale());
        }

        return defaultName;
    }

    /**
     * <p>
     * Resolves the message for the specified {@code key} by looking up its string value from the resource
     * {@code bundle} provided and then creating a message format for it.
     * </p>
     * <p>
     * All {@code MessageFormats} that are created by this method are cached based on {@code key} and the {@code Locale}
     * contained within {@code verification} to optimize subsequent calls to this method for the same key.
     * </p>
     * <p>
     * This method will return {@literal null} if no message could be found for {@code key} in the resource
     * {@code bundle}.
     * </p>
     *
     * @param bundle
     *         the {@code ResourceBundle} from which the message is to be retrieved
     * @param key
     *         the {@link MessageKey} of the message to be retrieved from {@code bundle} and formatted
     * @param verification
     *         the current {@link Verification}
     * @return A {@code MessageFormat} for the message looked up for {@code key} from the resource {@code bundle} or
     * {@literal null} if no message could be found for {@code key}.
     * @throws IllegalArgumentException
     *         If the resolved message is formatted but is an invalid format pattern or any of the format {@code args}
     *         are invalid for their placeholders.
     */
    protected MessageFormat getMessageFormat(final ResourceBundle bundle, final MessageKey key, final Verification<?> verification) {
        final Locale locale = verification.getLocale();
        MessageFormat messageFormat = null;

        synchronized (messageFormatsPerResourceBundle) {
            Map<MessageKey, Map<Locale, MessageFormat>> messageFormatsPerMessageKey = messageFormatsPerResourceBundle.get(bundle);
            Map<Locale, MessageFormat> messageFormatsPerLocale = null;

            if (messageFormatsPerMessageKey != null) {
                messageFormatsPerLocale = messageFormatsPerMessageKey.get(key);
                if (messageFormatsPerLocale != null) {
                    messageFormat = messageFormatsPerLocale.get(locale);
                    if (messageFormat != null) {
                        return messageFormat;
                    }
                }
            }

            final String message = getStringOrNull(bundle, key);

            if (message != null) {
                if (messageFormatsPerMessageKey == null) {
                    messageFormatsPerMessageKey = new HashMap<>();
                    messageFormatsPerResourceBundle.put(bundle, messageFormatsPerMessageKey);
                }
                if (messageFormatsPerLocale == null) {
                    messageFormatsPerLocale = new HashMap<>();
                    messageFormatsPerMessageKey.put(key, messageFormatsPerLocale);
                }

                messageFormat = createMessageFormat(message, verification);
                messageFormatsPerLocale.put(locale, messageFormat);
            }
        }

        return messageFormat;
    }

    /**
     * <p>
     * Returns a resource bundle for the specified {@code baseName} using the {@code verification} provided.
     * </p>
     * <p>
     * All {@code ResourceBundles} that are loaded by this method are cached based on {@code base} and the
     * {@code Locale} contained within {@code verification} to optimize subsequent calls to this method for the same
     * base name.
     * </p>
     * <p>
     * This method will return {@literal null} if no resource bundle could be found for {@code baseName} for the current
     * {@code Locale}.
     * </p>
     *
     * @param baseName
     *         the base name of the resource bundle
     * @param verification
     *         the current {@link Verification}
     * @return The {@code ResourceBundle} for {@code baseName} and the current {@code Locale} or {@literal null} if none
     * could be found.
     */
    protected ResourceBundle getResourceBundle(final String baseName, final Verification<?> verification) {
        final Locale locale = verification.getLocale();
        ResourceBundle bundle;

        synchronized (resourceBundlesPerBaseName) {
            Map<Locale, ResourceBundle> resourceBundlesPerLocale = resourceBundlesPerBaseName.get(baseName);

            if (resourceBundlesPerLocale != null) {
                bundle = resourceBundlesPerLocale.get(locale);
                if (bundle != null) {
                    return bundle;
                }
            }

            try {
                bundle = getResourceBundleInternal(baseName, locale);

                if (resourceBundlesPerLocale == null) {
                    resourceBundlesPerLocale = new HashMap<>();
                    resourceBundlesPerBaseName.put(baseName, resourceBundlesPerLocale);
                }

                resourceBundlesPerLocale.put(locale, bundle);

                return bundle;
            } catch (MissingResourceException e) {
                return null;
            }
        }
    }

    /**
     * <p>
     * Called internally by {@link #getResourceBundle(String, Verification)} to handle the actual logic of loading a
     * {@code ResourceBundle} based on the specified {@code baseName} and {@code locale}.
     * </p>
     * <p>
     * This method can be used in other cases where only the {@code ResourceBundle} is desired without all of the other
     * trimmings.
     * </p>
     *
     * @param baseName
     *         the base name of the resource bundle
     * @param locale
     *         the {@code Locale} of the resource bundle
     * @return The {@code ResourceBundle} for {@code baseName} and {@code locale}.
     * @throws MissingResourceException
     *         If no resource bundle could be found for {@code baseName} and {@code locale}.
     */
    protected ResourceBundle getResourceBundleInternal(final String baseName, final Locale locale) {
        return ResourceBundle.getBundle(baseName, locale);
    }

    /**
     * <p>
     * Returns the string value of the specified {@code key} in the resource {@code bundle} provided.
     * </p>
     * <p>
     * If no entry is found for the code of the {@code key}, this method will return {@literal null} instead of throwing
     * a {@code MissingResourceException}.
     * </p>
     *
     * @param bundle
     *         the {@code ResourceBundle} from which the value is to be retrieved
     * @param key
     *         the {@link MessageKey} for which the value is to be returned
     * @return The string value for {@code key} within the resource {@code bundle} or {@literal null} if none was found.
     */
    protected String getStringOrNull(final ResourceBundle bundle, final MessageKey key) {
        try {
            return bundle.getString(key.code());
        } catch (MissingResourceException e) {
            return null;
        }
    }

    @Override
    protected MessageFormat resolveKey(final MessageKey key, final Verification<?> verification) {
        for (final String baseName : getBaseNames()) {
            final ResourceBundle bundle = getResourceBundle(baseName, verification);
            if (bundle != null) {
                final MessageFormat messageFormat = getMessageFormat(bundle, key, verification);
                if (messageFormat != null) {
                    return messageFormat;
                }
            }
        }

        return null;
    }

    @Override
    protected String resolveKeyWithoutArguments(final MessageKey key, final Verification<?> verification) {
        for (final String baseName : getBaseNames()) {
            final ResourceBundle bundle = getResourceBundle(baseName, verification);
            if (bundle != null) {
                final String message = getStringOrNull(bundle, key);
                if (message != null) {
                    return message;
                }
            }
        }

        return null;
    }

    /**
     * <p>
     * Returns the resource bundle base names for this {@link ResourceBundleMessageSource}.
     * </p>
     *
     * @return The {@code Set} of base names.
     */
    public Set<String> getBaseNames() {
        return baseNames;
    }

    /**
     * <p>
     * Sets the resource bundle base names for this {@link ResourceBundleMessageSource} to {@code baseNames}.
     * </p>
     *
     * @param baseNames
     *         the {@code Collection} of base names to be set (may be {@literal null})
     */
    public void setBaseNames(final Collection<String> baseNames) {
        this.baseNames.clear();
        if (baseNames != null) {
            this.baseNames.addAll(baseNames);
        }
    }

    /**
     * <p>
     * Sets the resource bundle base names for this {@link ResourceBundleMessageSource} to {@code baseNames}.
     * </p>
     *
     * @param baseNames
     *         the base names to be set (may be {@literal null})
     */
    public void setBaseNames(final String... baseNames) {
        setBaseNames(baseNames != null ? Arrays.asList(baseNames) : null);
    }

    /**
     * <p>
     * The {@link MessageKey MessageKeys} that are used by {@link ResourceBundleMessageSource}.
     * </p>
     */
    public enum MessageKeys implements MessageKey {

        DEFAULT_MESSAGE("io.skelp.verifier.message.ResourceBundleMessageSource.default.message.normal"),
        DEFAULT_MESSAGE_NEGATED("io.skelp.verifier.message.ResourceBundleMessageSource.default.message.negated"),
        DEFAULT_NAME("io.skelp.verifier.message.ResourceBundleMessageSource.default.name"),
        MESSAGE("io.skelp.verifier.message.ResourceBundleMessageSource.message.normal"),
        MESSAGE_NEGATED("io.skelp.verifier.message.ResourceBundleMessageSource.message.negated");

        private final String code;

        MessageKeys(final String code) {
            this.code = code;
        }

        @Override
        public String code() {
            return code;
        }
    }
}
