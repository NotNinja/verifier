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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;
import java.util.function.Function;

import io.skelp.verifier.message.locale.LocaleContextProvider;
import io.skelp.verifier.service.Weighted.WeightedComparator;

/**
 * <p>
 * Used throughout Verifier to lookup service implementations using Java's Service Provider Interfaces (SPI) mechanism,
 * while supporting looking up both standard services (returned in order in which registered) and {@link Weighted}
 * services (returned in order based on weight/importance - most important first).
 * </p>
 * <p>
 * Verifier itself will generally only register a single implementation per service which is intended to be used as the
 * default implementation. For {@link Weighted} services, these will even be given a very low importance (i.e. a weight
 * of {@value Weighted#DEFAULT_IMPLEMENTATION_WEIGHT}) to encourage custom implementations to be discovered. If Verifier
 * does not provide have a default implementation and one has not been provided, it will fail.
 * </p>
 * <p>
 * Since this class uses SPI, all that is required to register a new service is to create a corresponding file in a
 * {@code META-INF/services} directory. For example; in order to register a custom implementation of
 * {@link LocaleContextProvider}, the following file should be created in the application code:
 * </p>
 * <pre>
 * META-INF/services/io.skelp.verifier.message.locale.LocaleContextProvider
 * </pre>
 * <p>
 * This file should contain an entry for each custom implementation, consisting of the class reference:
 * </p>
 * <pre>
 * com.example.verifier.message.locale.ExampleLocaleContextProvider
 * </pre>
 *
 * @author Alasdair Mercer
 * @since 0.2.0
 */
public final class Services {

    private static final WeightedComparator WEIGHTED_COMPARATOR = new WeightedComparator();

    /**
     * <p>
     * Calls the specified {@code mapper} function for each registered implementation of the specified {@code service}
     * in the order in which they were registered until one returns a non-null value, which is then returned by this
     * method.
     * </p>
     * <p>
     * {@code mapper} will be called for each implementation in the order in which they were registered.
     * </p>
     * <p>
     * This method will no longer call {@code mapper} once a non-null value is found and it will return {@literal null}
     * if either no registered implementations were found for {@code service} or none of them returned a non-null value
     * for {@code mapper}.
     * </p>
     *
     * @param service
     *         the service class whose registered implementations are to be passed to {@code mapper}
     * @param mapper
     *         the {@code Function} to be called for each registered implementation of {@code service} and whose first
     *         non-null value is to be returned
     * @param <T>
     *         the type of the service
     * @param <R>
     *         the type of the return value
     * @return The first non-null value returned by {@code mapper} or {@literal null} if no registered implementations
     * of {@code service} were found or {@code mapper} did not return a non-null value for any of them.
     * @see #findFirstNonNullForWeightedService(Class, Function)
     */
    public static <T, R> R findFirstNonNullForService(final Class<T> service, final Function<T, R> mapper) {
        for (final T instance : ServiceLoader.load(service)) {
            final R result = mapper.apply(instance);
            if (result != null) {
                return result;
            }
        }

        return null;
    }

    /**
     * <p>
     * Calls the specified {@code mapper} function for each registered implementation of the specified {@link Weighted}
     * {@code service} in order of their importance (most important first) until one returns a non-null value, which is
     * then returned by this method.
     * </p>
     * <p>
     * This method will no longer call {@code mapper} once a non-null value is found and it will return {@literal null}
     * if either no registered implementations were found for {@code service} or none of them returned a non-null value
     * for {@code mapper}.
     * </p>
     *
     * @param service
     *         the service class whose registered implementations are to be passed to {@code mapper}
     * @param mapper
     *         the {@code Function} to be called for each registered implementation of {@code service} and whose first
     *         non-null value is to be returned
     * @param <T>
     *         the type of the {@link Weighted} service
     * @param <R>
     *         the type of the return value
     * @return The first non-null value returned by {@code mapper} or {@literal null} if no registered implementations
     * of {@code service} were found or {@code mapper} did not return a non-null value for any of them.
     * @see #findFirstNonNullForService(Class, Function)
     */
    public static <T extends Weighted, R> R findFirstNonNullForWeightedService(final Class<T> service, final Function<T, R> mapper) {
        for (final T instance : getWeightedServices(service)) {
            final R result = mapper.apply(instance);
            if (result != null) {
                return result;
            }
        }

        return null;
    }

    /**
     * <p>
     * Returns the first registered implementation of the specified {@code service}.
     * </p>
     * <p>
     * If no implementation has been registered for {@code service}, a {@link ServiceNotFoundException} will be thrown.
     * </p>
     *
     * @param service
     *         the service class for which the first registered implementation is to be returned
     * @param <T>
     *         the type of the service
     * @return The first registered implementation of {@code service}.
     * @throws ServiceNotFoundException
     *         If no registered implementation could be found for {@code service}.
     * @see #getServices(Class)
     * @see #getWeightedService(Class)
     */
    public static <T> T getService(final Class<T> service) {
        final Iterator<T> iterator = ServiceLoader.load(service).iterator();
        if (iterator.hasNext()) {
            return iterator.next();
        }

        throw new ServiceNotFoundException(service);
    }

    /**
     * <p>
     * Returns all of the registered implementations for the specified {@code service} in the order in which they were
     * registered.
     * </p>
     *
     * @param service
     *         the service class for which the implementations are to be returned
     * @param <T>
     *         the type of the service
     * @return All registered implementations of {@code service} which may be empty if none were found but never
     * {@literal null}.
     * @see #getService(Class)
     * @see #getWeightedServices(Class)
     */
    public static <T> List<T> getServices(final Class<T> service) {
        final List<T> instances = new ArrayList<>();
        for (final T instance : ServiceLoader.load(service)) {
            instances.add(instance);
        }

        return instances;
    }

    /**
     * <p>
     * Returns the most important implementation of the specified {@link Weighted} {@code service}.
     * </p>
     * <p>
     * If no implementation has been registered for {@code service}, a {@link ServiceNotFoundException} will be thrown.
     * </p>
     *
     * @param service
     *         the service class for which the most important implementation is to be returned
     * @param <T>
     *         the type of the {@link Weighted} service
     * @return The most important implementation of {@code service}.
     * @throws ServiceNotFoundException
     *         If no registered implementation could be found for {@code service}.
     * @see #getService(Class)
     * @see #getWeightedServices(Class)
     */
    public static <T extends Weighted> T getWeightedService(final Class<T> service) {
        final List<T> instances = getWeightedServices(service);
        if (!instances.isEmpty()) {
            return instances.get(0);
        }

        throw new ServiceNotFoundException(service);
    }

    /**
     * <p>
     * Returns all of the registered implementations for the specified {@link Weighted} {@code service} ordered by their
     * importance (most important first).
     * </p>
     *
     * @param service
     *         the service class for which the implementations are to be returned
     * @param <T>
     *         the type of the {@link Weighted} service
     * @return All registered implementations of {@code service} which may be empty if none were found but never
     * {@literal null}.
     * @see #getServices(Class)
     * @see #getWeightedService(Class)
     */
    public static <T extends Weighted> List<T> getWeightedServices(final Class<T> service) {
        final List<T> instances = getServices(service);
        instances.sort(WEIGHTED_COMPARATOR);

        return instances;
    }

    /**
     * <p>
     * Creates an instance of {@link Services}.
     * </p>
     * <p>
     * This should <b>not</b> be used for standard programming but is available for cases where an instance is needed
     * for a Java Bean etc.
     * </p>
     */
    public Services() {
    }
}
