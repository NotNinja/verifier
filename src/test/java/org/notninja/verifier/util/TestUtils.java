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
package org.notninja.verifier.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * <p>
 * Contains utility methods for test classes.
 * </p>
 *
 * @author Alasdair Mercer
 */
public final class TestUtils {

    /**
     * <p>
     * A convenient shorthand for {@link #createInstance(Class, Class[], Object[], boolean)} where the parameter types
     * for the target constructor are derived from {@code parameters} and the target constructor must not be declared.
     * </p>
     * <p>
     * This method will throw a {@code NullPointerException} if {@code parameters} is not {@literal null} but contains a
     * {@literal null} element. Also, if you do not use this method unless the parameter types on the target constructor
     * <b>exactly</b> match the types of {@code parameters} it will fail or may invoke another constructor unknowingly.
     * </p>
     *
     * @param cls
     *         the class to be instantiated
     * @param parameters
     *         the parameters to be passed to the target constructor (may be {@literal null} if it has no parameters but
     *         cannot contain a {@literal null} element)
     * @param <T>
     *         the type of instance to be returned
     * @return An instance of {@code cls} created by calling the {@code Constructor} with types matching {@code
     * parameters}.
     * @throws NullPointerException
     *         If {@code cls} is {@literal null} or {@code parameters} is <b>not</b> {@literal null} but contains a
     *         {@literal null} element.
     * @throws ReflectiveOperationException
     *         If a reflection error occurs while trying to instantiate {@code cls}.
     */
    public static <T> T createInstance(final Class<T> cls, final Object[] parameters) throws ReflectiveOperationException {
        return createInstance(cls, deriveParameterTypes(parameters), parameters, false);
    }

    /**
     * <p>
     * A convenient shorthand for {@link #createInstance(Class, Class[], Object[], boolean)} where the parameter types
     * for the target constructor are derived from {@code parameters}.
     * </p>
     * <p>
     * This method will throw a {@code NullPointerException} if {@code parameters} is not {@literal null} but contains a
     * {@literal null} element. Also, if you do not use this method unless the parameter types on the target constructor
     * <b>exactly</b> match the types of {@code parameters} it will fail or may invoke another constructor unknowingly.
     * </p>
     *
     * @param cls
     *         the class to be instantiated
     * @param parameters
     *         the parameters to be passed to the target constructor (may be {@literal null} if it has no parameters but
     *         cannot contain a {@literal null} element)
     * @param declared
     *         {@literal true} if the target constructor is declared; otherwise {@literal false}
     * @param <T>
     *         the type of instance to be returned
     * @return An instance of {@code cls} created by calling the {@code Constructor} with types matching {@code
     * parameters}.
     * @throws NullPointerException
     *         If {@code cls} is {@literal null} or {@code parameters} is <b>not</b> {@literal null} but contains a
     *         {@literal null} element.
     * @throws ReflectiveOperationException
     *         If a reflection error occurs while trying to instantiate {@code cls}.
     */
    public static <T> T createInstance(final Class<T> cls, final Object[] parameters, final boolean declared) throws ReflectiveOperationException {
        return createInstance(cls, deriveParameterTypes(parameters), parameters, declared);
    }

    /**
     * <p>
     * A convenient shorthand for {@link #createInstance(Class, Class[], Object[], boolean)} where the target
     * constructor must not be declared.
     * </p>
     *
     * @param cls
     *         the class to be instantiated
     * @param parameterTypes
     *         the types of parameters used by the target constructor (may be {@literal null} if it has no parameters)
     * @param parameters
     *         the parameters to be passed to the target constructor (may be {@literal null} if it has no parameters)
     * @param <T>
     *         the type of instance to be returned
     * @return An instance of {@code cls} created by calling the {@code Constructor} with matching {@code
     * parameterTypes}.
     * @throws IllegalArgumentException
     *         If the lengths of {@code parameterTypes} and {@code parameters} do not match.
     * @throws NullPointerException
     *         If {@code cls} is {@literal null}.
     * @throws ReflectiveOperationException
     *         If a reflection error occurs while trying to instantiate {@code cls}.
     */
    public static <T> T createInstance(final Class<T> cls, final Class<?>[] parameterTypes, final Object[] parameters) throws ReflectiveOperationException {
        return createInstance(cls, parameterTypes, parameters, false);
    }

    /**
     * <p>
     * Creates an instance of the specified {@code cls} using the constructor that matches the parameter types provided.
     * </p>
     *
     * @param cls
     *         the class to be instantiated
     * @param parameterTypes
     *         the types of parameters used by the target constructor (may be {@literal null} if it has no parameters)
     * @param parameters
     *         the parameters to be passed to the target constructor (may be {@literal null} if it has no parameters)
     * @param declared
     *         {@literal true} if the target constructor is declared; otherwise {@literal false}
     * @param <T>
     *         the type of instance to be returned
     * @return An instance of {@code cls} created by calling the {@code Constructor} with matching {@code
     * parameterTypes}.
     * @throws IllegalArgumentException
     *         If the lengths of {@code parameterTypes} and {@code parameters} do not match.
     * @throws NullPointerException
     *         If {@code cls} is {@literal null}.
     * @throws ReflectiveOperationException
     *         If a reflection error occurs while trying to instantiate {@code cls}.
     */
    public static <T> T createInstance(final Class<T> cls, final Class<?>[] parameterTypes, final Object[] parameters, final boolean declared) throws ReflectiveOperationException {
        final Object[] safeParameters = parameters != null ? parameters : new Object[0];

        if (parameterTypes.length != safeParameters.length) {
            throw new IllegalArgumentException("Number of parameterTypes must match number of parameters");
        }

        Constructor<T> constructor = getConstructor(cls, parameterTypes, declared);
        constructor.setAccessible(true);

        return constructor.newInstance(safeParameters);
    }

    /**
     * <p>
     * A convenient shorthand for {@link #getConstructor(Class, Class[], boolean)} where the constructor must not be
     * declared.
     * </p>
     *
     * @param cls
     *         the class whose constructor is to be returned
     * @param parameterTypes
     *         the types of parameters used by the constructor to be returned (may be {@literal null} if constructor has
     *         no parameters)
     * @param <T>
     *         the type of the class
     * @return The {@code Constructor} for {@code cls} with matching {@code parameterTypes}.
     * @throws NullPointerException
     *         If {@code cls} is {@literal null}.
     * @throws ReflectiveOperationException
     *         If a reflection error occurs while trying to get the constructor.
     */
    public static <T> Constructor<T> getConstructor(final Class<T> cls, final Class<?>[] parameterTypes) throws ReflectiveOperationException {
        return getConstructor(cls, parameterTypes, false);
    }

    /**
     * <p>
     * Returns the constructor on the specified {@code cls} that matches the parameter types provided.
     * </p>
     *
     * @param cls
     *         the class whose constructor is to be returned
     * @param parameterTypes
     *         the types of parameters used by the constructor to be returned (may be {@literal null} if constructor has
     *         no parameters)
     * @param declared
     *         {@literal true} if the constructor is declared; otherwise {@literal false}
     * @param <T>
     *         the type of the class
     * @return The {@code Constructor} for {@code cls} with matching {@code parameterTypes}.
     * @throws NullPointerException
     *         If {@code cls} is {@literal null}.
     * @throws ReflectiveOperationException
     *         If a reflection error occurs while trying to get the constructor.
     */
    public static <T> Constructor<T> getConstructor(final Class<T> cls, final Class<?>[] parameterTypes, final boolean declared) throws ReflectiveOperationException {
        if (cls == null) {
            throw new NullPointerException("cls must not be null");
        }

        final Class<?>[] safeParameterTypes = parameterTypes != null ? parameterTypes : new Class<?>[0];

        return declared ? cls.getDeclaredConstructor(safeParameterTypes) : cls.getConstructor(safeParameterTypes);
    }

    /**
     * <p>
     * Returns the value of the field with the given {@code name} on the specified {@code cls}. Optionally, an
     * {@code instance} can be provided which results in the value of an instance field being returned instead of a
     * static field.
     * </p>
     * <p>
     * If no {@code cls} is provided, the class of the specified {@code instance} is used. If no {@code instance} is
     * provided as well, then this method will throw a {@code NullPointerException}.
     * </p>
     * <p>
     * If the desired field is on a superclass of the {@code instance} class, then that superclass should be passed as
     * {@code cls}. If the field has the {@code final} modifier, this method will remove this modifier to prevent access
     * issues for any future attempts to modify the field value.
     * </p>
     *
     * @param cls
     *         the class containing the field whose value is to be returned (derived from {@code instance} if {@literal
     *         null})
     * @param instance
     *         the object instance whose field value is to be returned (may be {@literal null} if the field is static)
     * @param name
     *         the name of the field whose value is to be returned
     * @param declared
     *         {@literal true} if the field is declared; otherwise {@literal false}
     * @return The value of the field with the given {@code name} or {@literal null} if the field is {@literal null}.
     * @throws NullPointerException
     *         If both {@code cls} <b>and</b> {@code instance} are {@literal null} or {@code name} is {@literal null}.
     * @throws ReflectiveOperationException
     *         If a reflection error occurs while trying to get the field value.
     */
    public static Object getField(final Class<?> cls, final Object instance, final String name, final boolean declared) throws ReflectiveOperationException {
        final Class<?> safeClass = cls != null ? cls : instance.getClass();
        final Field field = declared ? safeClass.getDeclaredField(name) : safeClass.getField(name);
        field.setAccessible(true);

        removeFinalModifier(field);

        return field.get(instance);
    }

    /**
     * <p>
     * A convenient shorthand for {@link #getField(Class, Object, String, boolean)} where the class is derived from
     * {@code instance} and the field must not be declared.
     * </p>
     *
     * @param instance
     *         the object instance whose field value is to be returned
     * @param name
     *         the name of the field whose value is to be returned
     * @return The value of the field with the given {@code name} or {@literal null} if the field is {@literal null}.
     * @throws NullPointerException
     *         If either {@code instance} or {@code name} are {@literal null}.
     * @throws ReflectiveOperationException
     *         If a reflection error occurs while trying to get the field value.
     */
    public static Object getInstanceField(final Object instance, final String name) throws ReflectiveOperationException {
        return getField(null, instance, name, false);
    }

    /**
     * <p>
     * A convenient shorthand for {@link #getField(Class, Object, String, boolean)} where the class is derived from
     * {@code instance}.
     * </p>
     *
     * @param instance
     *         the object instance whose field value is to be returned
     * @param name
     *         the name of the field whose value is to be returned
     * @return The value of the field with the given {@code name} or {@literal null} if the field is {@literal null}.
     * @throws NullPointerException
     *         If either {@code instance} or {@code name} are {@literal null}.
     * @throws ReflectiveOperationException
     *         If a reflection error occurs while trying to get the field value.
     */
    public static Object getInstanceField(final Object instance, final String name, final boolean declared) throws ReflectiveOperationException {
        return getField(null, instance, name, declared);
    }

    /**
     * <p>
     * A convenient shorthand for {@link #getField(Class, Object, String, boolean)} where no instance is provided and
     * the field must not be declared.
     * </p>
     *
     * @param cls
     *         the class containing the field whose value is to be returned
     * @param name
     *         the name of the field whose value is to be returned
     * @return The value of the field with the given {@code name} or {@literal null} if the field is {@literal null}.
     * @throws NullPointerException
     *         If either {@code cls} or {@code name} are {@literal null}.
     * @throws ReflectiveOperationException
     *         If a reflection error occurs while trying to get the field value.
     */
    public static Object getStaticField(final Class<?> cls, final String name) throws ReflectiveOperationException {
        return getField(cls, null, name, false);
    }

    /**
     * <p>
     * A convenient shorthand for {@link #getField(Class, Object, String, boolean)} where no instance is provided.
     * </p>
     *
     * @param cls
     *         the class containing the field whose value is to be returned
     * @param name
     *         the name of the field whose value is to be returned
     * @param declared
     *         {@literal true} if the field is declared; otherwise {@literal false}
     * @return The value of the field with the given {@code name} or {@literal null} if the field is {@literal null}.
     * @throws NullPointerException
     *         If either {@code cls} or {@code name} are {@literal null}.
     * @throws ReflectiveOperationException
     *         If a reflection error occurs while trying to get the field value.
     */
    public static Object getStaticField(final Class<?> cls, final String name, final boolean declared) throws ReflectiveOperationException {
        return getField(cls, null, name, declared);
    }

    /**
     * <p>
     * A convenient shorthand for {@link #invokeMethod(Class, Object, String, Class[], Object[], boolean)} where the
     * class is derived from {@code instance} and the parameter types for the target method are derived from
     * {@code parameters} and the target method must not be declared.
     * </p>
     * <p>
     * This method will throw a {@code NullPointerException} if {@code parameters} is not {@literal null} but contains a
     * {@literal null} element. Also, if you do not use this method unless the parameter types on the target method
     * <b>exactly</b> match the types of {@code parameters} it will fail or may invoke another method unknowingly.
     * </p>
     *
     * @param instance
     *         the object instance on which the method is to be invoked
     * @param name
     *         the name of the method to be invoked
     * @param parameters
     *         the parameters to be passed to the method (may be {@literal null} if method has no parameters)
     * @throws NullPointerException
     *         If either {@code instance} or {@code name} are {@literal null} or {@code parameters} is <b>not</b>
     *         {@literal null} but contains a {@literal null} element.
     * @throws ReflectiveOperationException
     *         If a reflection error occurs while trying to invoke the method.
     */
    public static void invokeInstanceMethod(final Object instance, final String name, final Object[] parameters) throws ReflectiveOperationException {
        invokeMethodInternal(null, instance, name, deriveParameterTypes(parameters), parameters, false, false);
    }

    /**
     * <p>
     * A convenient shorthand for {@link #invokeMethod(Class, Object, String, Class[], Object[], boolean)} where the
     * class is derived from {@code instance} and the parameter types for the target method are derived from
     * {@code parameters}.
     * </p>
     * <p>
     * This method will throw a {@code NullPointerException} if {@code parameters} is not {@literal null} but contains a
     * {@literal null} element. Also, if you do not use this method unless the parameter types on the target method
     * <b>exactly</b> match the types of {@code parameters} it will fail or may invoke another method unknowingly.
     * </p>
     *
     * @param instance
     *         the object instance on which the method is to be invoked
     * @param name
     *         the name of the method to be invoked
     * @param parameters
     *         the parameters to be passed to the method (may be {@literal null} if method has no parameters)
     * @param declared
     *         {@literal true} if the method is declared; otherwise {@literal false}
     * @throws NullPointerException
     *         If either {@code instance} or {@code name} are {@literal null} or {@code parameters} is <b>not</b>
     *         {@literal null} but contains a {@literal null} element.
     * @throws ReflectiveOperationException
     *         If a reflection error occurs while trying to invoke the method.
     */
    public static void invokeInstanceMethod(final Object instance, final String name, final Object[] parameters, final boolean declared) throws ReflectiveOperationException {
        invokeMethodInternal(null, instance, name, deriveParameterTypes(parameters), parameters, declared, false);
    }

    /**
     * <p>
     * A convenient shorthand for {@link #invokeMethod(Class, Object, String, Class[], Object[], boolean)} where the
     * class is derived from {@code instance} and the method must not be declared.
     * </p>
     *
     * @param instance
     *         the object instance on which the method is to be invoked
     * @param name
     *         the name of the method to be invoked
     * @param parameterTypes
     *         the types of parameters used by the method (may be {@literal null} if method has no parameters)
     * @param parameters
     *         the parameters to be passed to the method (may be {@literal null} if method has no parameters)
     * @throws IllegalArgumentException
     *         If the lengths of {@code parameterTypes} and {@code parameters} do not match.
     * @throws NullPointerException
     *         If either {@code instance} or {@code name} are {@literal null}.
     * @throws ReflectiveOperationException
     *         If a reflection error occurs while trying to invoke the method.
     */
    public static void invokeInstanceMethod(final Object instance, final String name, final Class<?>[] parameterTypes, final Object[] parameters) throws ReflectiveOperationException {
        invokeMethodInternal(null, instance, name, parameterTypes, parameters, false, false);
    }

    /**
     * <p>
     * A convenient shorthand for {@link #invokeMethod(Class, Object, String, Class[], Object[], boolean)} where the
     * class is derived from {@code instance}.
     * </p>
     *
     * @param instance
     *         the object instance on which the method is to be invoked
     * @param name
     *         the name of the method to be invoked
     * @param parameterTypes
     *         the types of parameters used by the method (may be {@literal null} if method has no parameters)
     * @param parameters
     *         the parameters to be passed to the method (may be {@literal null} if method has no parameters)
     * @param declared
     *         {@literal true} if the method is declared; otherwise {@literal false}
     * @throws IllegalArgumentException
     *         If the lengths of {@code parameterTypes} and {@code parameters} do not match.
     * @throws NullPointerException
     *         If either {@code instance} or {@code name} are {@literal null}.
     * @throws ReflectiveOperationException
     *         If a reflection error occurs while trying to invoke the method.
     */
    public static void invokeInstanceMethod(final Object instance, final String name, final Class<?>[] parameterTypes, final Object[] parameters, final boolean declared) throws ReflectiveOperationException {
        invokeMethodInternal(null, instance, name, parameterTypes, parameters, declared, false);
    }

    /**
     * <p>
     * A convenient shorthand for {@link #invokeMethodAndReturn(Class, Object, String, Class[], Object[], boolean)}
     * where the class is derived from {@code instance} and the parameter types for the target method are derived from
     * {@code parameters} and the target method must not be declared.
     * </p>
     * <p>
     * This method will throw a {@code NullPointerException} if {@code parameters} is not {@literal null} but contains a
     * {@literal null} element. Also, if you do not use this method unless the parameter types on the target method
     * <b>exactly</b> match the types of {@code parameters} it will fail or may invoke another method unknowingly.
     * </p>
     *
     * @param instance
     *         the object instance on which the method is to be invoked
     * @param name
     *         the name of the method to be invoked
     * @param parameters
     *         the parameters to be passed to the method (may be {@literal null} if method has no parameters)
     * @throws IllegalStateException
     *         If the target method is {@code void}.
     * @throws NullPointerException
     *         If either {@code instance} or {@code name} are {@literal null} or {@code parameters} is <b>not</b>
     *         {@literal null} but contains a {@literal null} element.
     * @throws ReflectiveOperationException
     *         If a reflection error occurs while trying to invoke the method.
     */
    public static Object invokeInstanceMethodAndReturn(final Object instance, final String name, final Object[] parameters) throws ReflectiveOperationException {
        return invokeMethodInternal(null, instance, name, deriveParameterTypes(parameters), parameters, false, true);
    }

    /**
     * <p>
     * A convenient shorthand for {@link #invokeMethodAndReturn(Class, Object, String, Class[], Object[], boolean)}
     * where the class is derived from {@code instance} and the parameter types for the target method are derived from
     * {@code parameters}.
     * </p>
     * <p>
     * This method will throw a {@code NullPointerException} if {@code parameters} is not {@literal null} but contains a
     * {@literal null} element. Also, if you do not use this method unless the parameter types on the target method
     * <b>exactly</b> match the types of {@code parameters} it will fail or may invoke another method unknowingly.
     * </p>
     *
     * @param instance
     *         the object instance on which the method is to be invoked
     * @param name
     *         the name of the method to be invoked
     * @param parameters
     *         the parameters to be passed to the method (may be {@literal null} if method has no parameters)
     * @param declared
     *         {@literal true} if the method is declared; otherwise {@literal false}
     * @throws IllegalStateException
     *         If the target method is {@code void}.
     * @throws NullPointerException
     *         If either {@code instance} or {@code name} are {@literal null} or {@code parameters} is <b>not</b>
     *         {@literal null} but contains a {@literal null} element.
     * @throws ReflectiveOperationException
     *         If a reflection error occurs while trying to invoke the method.
     */
    public static Object invokeInstanceMethodAndReturn(final Object instance, final String name, final Object[] parameters, final boolean declared) throws ReflectiveOperationException {
        return invokeMethodInternal(null, instance, name, deriveParameterTypes(parameters), parameters, declared, true);
    }

    /**
     * <p>
     * A convenient shorthand for {@link #invokeMethodAndReturn(Class, Object, String, Class[], Object[], boolean)}
     * where the class is derived from {@code instance} and the target method must not be declared.s
     * </p>
     *
     * @param instance
     *         the object instance on which the method is to be invoked
     * @param name
     *         the name of the method to be invoked
     * @param parameterTypes
     *         the types of parameters used by the method (may be {@literal null} if method has no parameters)
     * @param parameters
     *         the parameters to be passed to the method (may be {@literal null} if method has no parameters)
     * @return The return value of the method invoked with the given {@code name} or {@literal null} if the method
     * returns {@literal null}.
     * @throws IllegalArgumentException
     *         If the lengths of {@code parameterTypes} and {@code parameters} do not match.
     * @throws IllegalStateException
     *         If the target method is {@code void}.
     * @throws NullPointerException
     *         If either {@code instance} or {@code name} are {@literal null}.
     * @throws ReflectiveOperationException
     *         If a reflection error occurs while trying to invoke the method.
     */
    public static Object invokeInstanceMethodAndReturn(final Object instance, final String name, final Class<?>[] parameterTypes, final Object[] parameters) throws ReflectiveOperationException {
        return invokeMethodInternal(null, instance, name, parameterTypes, parameters, false, true);
    }

    /**
     * <p>
     * A convenient shorthand for {@link #invokeMethodAndReturn(Class, Object, String, Class[], Object[], boolean)}
     * where the class is derived from {@code instance}.
     * </p>
     *
     * @param instance
     *         the object instance on which the method is to be invoked
     * @param name
     *         the name of the method to be invoked
     * @param parameterTypes
     *         the types of parameters used by the method (may be {@literal null} if method has no parameters)
     * @param parameters
     *         the parameters to be passed to the method (may be {@literal null} if method has no parameters)
     * @param declared
     *         {@literal true} if the method is declared; otherwise {@literal false}
     * @return The return value of the method invoked with the given {@code name} or {@literal null} if the method
     * returns {@literal null}.
     * @throws IllegalArgumentException
     *         If the lengths of {@code parameterTypes} and {@code parameters} do not match.
     * @throws IllegalStateException
     *         If the target method is {@code void}.
     * @throws NullPointerException
     *         If either {@code instance} or {@code name} are {@literal null}.
     * @throws ReflectiveOperationException
     *         If a reflection error occurs while trying to invoke the method.
     */
    public static Object invokeInstanceMethodAndReturn(final Object instance, final String name, final Class<?>[] parameterTypes, final Object[] parameters, final boolean declared) throws ReflectiveOperationException {
        return invokeMethodInternal(null, instance, name, parameterTypes, parameters, declared, true);
    }

    /**
     * <p>
     * Invokes the method with the given {@code name} on the specified {@code cls}. Optionally, an {@code instance} can
     * be provided which results in an instance method being invoked instead of a static method.
     * </p>
     * <p>
     * If no {@code cls} is provided, the class of the specified {@code instance} is used. If no {@code instance} is
     * provided as well, then this method will throw a {@code NullPointerException}.
     * </p>
     * <p>
     * If the desired method is on a superclass of the {@code instance} class, then that superclass should be passed as
     * {@code cls}.
     * </p>
     * <p>
     * This method ignores the return value of the target method, if any.
     * </p>
     *
     * @param cls
     *         the class containing the method to be invoked (derived from {@code instance} if {@literal null})
     * @param instance
     *         the object instance on which the method is to be invoked (may be {@literal null} if the method is
     *         static)
     * @param name
     *         the name of the method to be invoked
     * @param parameterTypes
     *         the types of parameters used by the method (may be {@literal null} if method has no parameters)
     * @param parameters
     *         the parameters to be passed to the method (may be {@literal null} if method has no parameters)
     * @param declared
     *         {@literal true} if the method is declared; otherwise {@literal false}
     * @throws IllegalArgumentException
     *         If the lengths of {@code parameterTypes} and {@code parameters} do not match.
     * @throws NullPointerException
     *         If both {@code cls} <b>and</b> {@code instance} are {@literal null} or {@code name} is {@literal null}.
     * @throws ReflectiveOperationException
     *         If a reflection error occurs while trying to invoke the method.
     */
    public static void invokeMethod(final Class<?> cls, final Object instance, final String name, final Class<?>[] parameterTypes, final Object[] parameters, final boolean declared) throws ReflectiveOperationException {
        invokeMethodInternal(cls, instance, name, parameterTypes, parameters, declared, false);
    }

    /**
     * <p>
     * Invokes the method with the given {@code name} on the specified {@code cls} and returns the result of that
     * invocation. Optionally, an {@code instance} can be provided which results in the value of an instance method
     * being returned instead of a static method.
     * </p>
     * <p>
     * If no {@code cls} is provided, the class of the specified {@code instance} is used. If no {@code instance} is
     * provided as well, then this method will throw a {@code NullPointerException}.
     * </p>
     * <p>
     * If the desired method is on a superclass of the {@code instance} class, then that superclass should be passed as
     * {@code cls}.
     * </p>
     * <p>
     * This method will throw an {@code IllegalStateException} if the target method does not have a return type (i.e. is
     * {@code void}). To avoid this, consider calling {@link
     * #invokeMethod(Class, Object, String, Class[], Object[], boolean)} instead as it ignores the return type and
     * value.
     * </p>
     *
     * @param cls
     *         the class containing the method to be invoked (derived from {@code instance} if {@literal null})
     * @param instance
     *         the object instance on which the method is to be invoked (may be {@literal null} if the method is
     *         static)
     * @param name
     *         the name of the method to be invoked
     * @param parameterTypes
     *         the types of parameters used by the method (may be {@literal null} if method has no parameters)
     * @param parameters
     *         the parameters to be passed to the method (may be {@literal null} if method has no parameters)
     * @param declared
     *         {@literal true} if the method is declared; otherwise {@literal false}
     * @return The return value of the method invoked with the given {@code name} or {@literal null} if the method
     * returns {@literal null}.
     * @throws IllegalArgumentException
     *         If the lengths of {@code parameterTypes} and {@code parameters} do not match.
     * @throws IllegalStateException
     *         If the target method is {@code void}.
     * @throws NullPointerException
     *         If both {@code cls} <b>and</b> {@code instance} are {@literal null} or {@code name} is {@literal null}.
     * @throws ReflectiveOperationException
     *         If a reflection error occurs while trying to invoke the method.
     */
    public static Object invokeMethodAndReturn(final Class<?> cls, final Object instance, final String name, final Class<?>[] parameterTypes, final Object[] parameters, final boolean declared) throws ReflectiveOperationException {
        return invokeMethodInternal(cls, instance, name, parameterTypes, parameters, declared, true);
    }

    /**
     * <p>
     * A convenient shorthand for {@link #invokeMethod(Class, Object, String, Class[], Object[], boolean)} where no
     * no instance is provided and the parameter types for the target method are derived from {@code parameters} and the
     * target method must not be declared.
     * </p>
     * <p>
     * This method will throw a {@code NullPointerException} if {@code parameters} is not {@literal null} but contains a
     * {@literal null} element. Also, if you do not use this method unless the parameter types on the target method
     * <b>exactly</b> match the types of {@code parameters} it will fail or may invoke another method unknowingly.
     * </p>
     *
     * @param cls
     *         the class containing the method to be invoked
     * @param name
     *         the name of the method to be invoked
     * @param parameters
     *         the parameters to be passed to the method (may be {@literal null} if method has no parameters)
     * @throws NullPointerException
     *         If either {@code cls} or {@code name} are {@literal null} or {@code parameters} is <b>not</b> {@literal
     *         null} but contains a {@literal null} element.
     * @throws ReflectiveOperationException
     *         If a reflection error occurs while trying to invoke the method.
     */
    public static void invokeStaticMethod(final Class<?> cls, final String name, final Object[] parameters) throws ReflectiveOperationException {
        invokeMethodInternal(cls, null, name, deriveParameterTypes(parameters), parameters, false, false);
    }

    /**
     * <p>
     * A convenient shorthand for {@link #invokeMethod(Class, Object, String, Class[], Object[], boolean)} where no
     * no instance is provided and the parameter types for the target method are derived from {@code parameters}.
     * </p>
     * <p>
     * This method will throw a {@code NullPointerException} if {@code parameters} is not {@literal null} but contains a
     * {@literal null} element. Also, if you do not use this method unless the parameter types on the target method
     * <b>exactly</b> match the types of {@code parameters} it will fail or may invoke another method unknowingly.
     * </p>
     *
     * @param cls
     *         the class containing the method to be invoked
     * @param name
     *         the name of the method to be invoked
     * @param parameters
     *         the parameters to be passed to the method (may be {@literal null} if method has no parameters)
     * @param declared
     *         {@literal true} if the method is declared; otherwise {@literal false}
     * @throws NullPointerException
     *         If either {@code cls} or {@code name} are {@literal null} or {@code parameters} is <b>not</b> {@literal
     *         null} but contains a {@literal null} element.
     * @throws ReflectiveOperationException
     *         If a reflection error occurs while trying to invoke the method.
     */
    public static void invokeStaticMethod(final Class<?> cls, final String name, final Object[] parameters, final boolean declared) throws ReflectiveOperationException {
        invokeMethodInternal(cls, null, name, deriveParameterTypes(parameters), parameters, declared, false);
    }

    /**
     * <p>
     * A convenient shorthand for {@link #invokeMethod(Class, Object, String, Class[], Object[], boolean)} where no
     * instance is provided and the method must not be declared.
     * </p>
     *
     * @param cls
     *         the class containing the method to be invoked
     * @param name
     *         the name of the method to be invoked
     * @param parameterTypes
     *         the types of parameters used by the method (may be {@literal null} if method has no parameters)
     * @param parameters
     *         the parameters to be passed to the method (may be {@literal null} if method has no parameters)
     * @throws IllegalArgumentException
     *         If the lengths of {@code parameterTypes} and {@code parameters} do not match.
     * @throws NullPointerException
     *         If either {@code cls} or {@code name} are {@literal null}.
     * @throws ReflectiveOperationException
     *         If a reflection error occurs while trying to invoke the method.
     */
    public static void invokeStaticMethod(final Class<?> cls, final String name, final Class<?>[] parameterTypes, final Object[] parameters) throws ReflectiveOperationException {
        invokeMethodInternal(cls, null, name, parameterTypes, parameters, false, false);
    }

    /**
     * <p>
     * A convenient shorthand for {@link #invokeMethod(Class, Object, String, Class[], Object[], boolean)} where no
     * instance is provided.
     * </p>
     *
     * @param cls
     *         the class containing the method to be invoked
     * @param name
     *         the name of the method to be invoked
     * @param parameterTypes
     *         the types of parameters used by the method (may be {@literal null} if method has no parameters)
     * @param parameters
     *         the parameters to be passed to the method (may be {@literal null} if method has no parameters)
     * @param declared
     *         {@literal true} if the method is declared; otherwise {@literal false}
     * @throws IllegalArgumentException
     *         If the lengths of {@code parameterTypes} and {@code parameters} do not match.
     * @throws NullPointerException
     *         If either {@code cls} or {@code name} are {@literal null}.
     * @throws ReflectiveOperationException
     *         If a reflection error occurs while trying to invoke the method.
     */
    public static void invokeStaticMethod(final Class<?> cls, final String name, final Class<?>[] parameterTypes, final Object[] parameters, final boolean declared) throws ReflectiveOperationException {
        invokeMethodInternal(cls, null, name, parameterTypes, parameters, declared, false);
    }

    /**
     * <p>
     * A convenient shorthand for {@link #invokeMethodAndReturn(Class, Object, String, Class[], Object[], boolean)}
     * where no instance is provided and the parameter types for the target method are derived from {@code parameters}
     * and the target method must not be declared.
     * </p>
     * <p>
     * This method will throw a {@code NullPointerException} if {@code parameters} is not {@literal null} but contains a
     * {@literal null} element. Also, if you do not use this method unless the parameter types on the target method
     * <b>exactly</b> match the types of {@code parameters} it will fail or may invoke another method unknowingly.
     * </p>
     *
     * @param cls
     *         the class containing the method to be invoked
     * @param name
     *         the name of the method to be invoked
     * @param parameters
     *         the parameters to be passed to the method (may be {@literal null} if method has no parameters)
     * @return The return value of the method invoked with the given {@code name} or {@literal null} if the method
     * returns {@literal null}.
     * @throws IllegalStateException
     *         If the target method is {@code void}.
     * @throws NullPointerException
     *         If either {@code cls} or {@code name} are {@literal null} or {@code parameters} is <b>not</b> {@literal
     *         null} but contains a {@literal null} element.
     * @throws ReflectiveOperationException
     *         If a reflection error occurs while trying to invoke the method.
     */
    public static Object invokeStaticMethodAndReturn(final Class<?> cls, final String name, final Object[] parameters) throws ReflectiveOperationException {
        return invokeMethodInternal(cls, null, name, deriveParameterTypes(parameters), parameters, false, true);
    }

    /**
     * <p>
     * A convenient shorthand for {@link #invokeMethodAndReturn(Class, Object, String, Class[], Object[], boolean)}
     * where no instance is provided and the parameter types for the target method are derived from {@code parameters}.
     * </p>
     * <p>
     * This method will throw a {@code NullPointerException} if {@code parameters} is not {@literal null} but contains a
     * {@literal null} element. Also, if you do not use this method unless the parameter types on the target method
     * <b>exactly</b> match the types of {@code parameters} it will fail or may invoke another method unknowingly.
     * </p>
     *
     * @param cls
     *         the class containing the method to be invoked
     * @param name
     *         the name of the method to be invoked
     * @param parameters
     *         the parameters to be passed to the method (may be {@literal null} if method has no parameters)
     * @param declared
     *         {@literal true} if the method is declared; otherwise {@literal false}
     * @return The return value of the method invoked with the given {@code name} or {@literal null} if the method
     * returns {@literal null}.
     * @throws IllegalStateException
     *         If the target method is {@code void}.
     * @throws NullPointerException
     *         If either {@code cls} or {@code name} are {@literal null} or {@code parameters} is <b>not</b> {@literal
     *         null} but contains a {@literal null} element.
     * @throws ReflectiveOperationException
     *         If a reflection error occurs while trying to invoke the method.
     */
    public static Object invokeStaticMethodAndReturn(final Class<?> cls, final String name, final Object[] parameters, final boolean declared) throws ReflectiveOperationException {
        return invokeMethodInternal(cls, null, name, deriveParameterTypes(parameters), parameters, declared, true);
    }

    /**
     * <p>
     * A convenient shorthand for {@link #invokeMethodAndReturn(Class, Object, String, Class[], Object[], boolean)}
     * where no instance is provided and the target method must not be declared.
     * </p>
     *
     * @param cls
     *         the class containing the method to be invoked
     * @param name
     *         the name of the method to be invoked
     * @param parameterTypes
     *         the types of parameters used by the method (may be {@literal null} if method has no parameters)
     * @param parameters
     *         the parameters to be passed to the method (may be {@literal null} if method has no parameters)
     * @return The return value of the method invoked with the given {@code name} or {@literal null} if the method
     * returns {@literal null}.
     * @throws IllegalArgumentException
     *         If the lengths of {@code parameterTypes} and {@code parameters} do not match.
     * @throws IllegalStateException
     *         If the target method is {@code void}.
     * @throws NullPointerException
     *         If either {@code cls} or {@code name} are {@literal null}.
     * @throws ReflectiveOperationException
     *         If a reflection error occurs while trying to invoke the method.
     */
    public static Object invokeStaticMethodAndReturn(final Class<?> cls, final String name, final Class<?>[] parameterTypes, final Object[] parameters) throws ReflectiveOperationException {
        return invokeMethodInternal(cls, null, name, parameterTypes, parameters, false, true);
    }

    /**
     * <p>
     * A convenient shorthand for {@link #invokeMethodAndReturn(Class, Object, String, Class[], Object[], boolean)}
     * where no instance is provided.
     * </p>
     *
     * @param cls
     *         the class containing the method to be invoked
     * @param name
     *         the name of the method to be invoked
     * @param parameterTypes
     *         the types of parameters used by the method (may be {@literal null} if method has no parameters)
     * @param parameters
     *         the parameters to be passed to the method (may be {@literal null} if method has no parameters)
     * @param declared
     *         {@literal true} if the method is declared; otherwise {@literal false}
     * @return The return value of the method invoked with the given {@code name} or {@literal null} if the method
     * returns {@literal null}.
     * @throws IllegalArgumentException
     *         If the lengths of {@code parameterTypes} and {@code parameters} do not match.
     * @throws IllegalStateException
     *         If the target method is {@code void}.
     * @throws NullPointerException
     *         If either {@code cls} or {@code name} are {@literal null}.
     * @throws ReflectiveOperationException
     *         If a reflection error occurs while trying to invoke the method.
     */
    public static Object invokeStaticMethodAndReturn(final Class<?> cls, final String name, final Class<?>[] parameterTypes, final Object[] parameters, final boolean declared) throws ReflectiveOperationException {
        return invokeMethodInternal(cls, null, name, parameterTypes, parameters, declared, true);
    }

    /**
     * <p>
     * Sets the value of the field with the given {@code name} on the specified {@code cls} to {@code value}.
     * Optionally, an {@code instance} can be provided which results in {@code value} being set on an instance field
     * instead of a static field.
     * </p>
     * <p>
     * If no {@code cls} is provided, the class of the specified {@code instance} is used. If no {@code instance} is
     * provided as well, then this method will throw a {@code NullPointerException}.
     * </p>
     * <p>
     * If the desired field is on a superclass of the {@code instance} class, then that superclass should be passed as
     * {@code cls}. If the field has the {@code final} modifier, this method will remove this modifier to allow access
     * to modify the field value.
     * </p>
     *
     * @param cls
     *         the class containing the field whose value is to be set to {@code value} (derived from {@code instance}
     *         if {@literal null})
     * @param instance
     *         the object instance whose field value is to be set to {@code value} (may be {@literal null} if the field
     *         is static)
     * @param name
     *         the name of the field whose value is to be set to {@code value}
     * @param value
     *         the value to be set on the field (may be {@literal null})
     * @param declared
     *         {@literal true} if the field is declared; otherwise {@literal false}
     * @throws NullPointerException
     *         If both {@code cls} <b>and</b> {@code instance} are {@literal null} or {@code name} is {@literal null}.
     * @throws ReflectiveOperationException
     *         If a reflection error occurs while trying to set the field value.
     */
    public static void setField(final Class<?> cls, final Object instance, final String name, final Object value, final boolean declared) throws ReflectiveOperationException {
        final Class<?> safeClass = cls != null ? cls : instance.getClass();
        final Field field = declared ? safeClass.getDeclaredField(name) : safeClass.getField(name);
        field.setAccessible(true);

        removeFinalModifier(field);

        field.set(instance, value);
    }

    /**
     * <p>
     * A convenient shorthand for {@link #setField(Class, Object, String, Object, boolean)} where the class is derived
     * from {@code instance} and the field must not be declared.
     * </p>
     *
     * @param instance
     *         the object instance whose field value is to be set to {@code value}
     * @param name
     *         the name of the field whose value is to be set to {@code value}
     * @param value
     *         the value to be set on the field (may be {@literal null})
     * @throws NullPointerException
     *         If either {@code instance} or {@code name} are {@literal null}.
     * @throws ReflectiveOperationException
     *         If a reflection error occurs while trying to set the field value.
     */
    public static void setInstanceField(final Object instance, final String name, final Object value) throws ReflectiveOperationException {
        setField(null, instance, name, value, false);
    }

    /**
     * <p>
     * A convenient shorthand for {@link #setField(Class, Object, String, Object, boolean)} where the class is derived
     * from {@code instance}.
     * </p>
     *
     * @param instance
     *         the object instance whose field value is to be set to {@code value}
     * @param name
     *         the name of the field whose value is to be set to {@code value}
     * @param value
     *         the value to be set on the field (may be {@literal null})
     * @param declared
     *         {@literal true} if the field is declared; otherwise {@literal false}
     * @throws NullPointerException
     *         If either {@code instance} or {@code name} are {@literal null}.
     * @throws ReflectiveOperationException
     *         If a reflection error occurs while trying to set the field value.
     */
    public static void setInstanceField(final Object instance, final String name, final Object value, final boolean declared) throws ReflectiveOperationException {
        setField(null, instance, name, value, declared);
    }

    /**
     * <p>
     * A convenient shorthand for {@link #setField(Class, Object, String, Object, boolean)} where no instance is
     * provided and the field must not be declared.
     * </p>
     *
     * @param cls
     *         the class containing the field whose value is to be set to {@code value}
     * @param name
     *         the name of the field whose value is to be set to {@code value}
     * @param value
     *         the value to be set on the field (may be {@literal null})
     * @throws NullPointerException
     *         If either {@code cls} or {@code name} are {@literal null}.
     * @throws ReflectiveOperationException
     *         If a reflection error occurs while trying to set the field value.
     */
    public static void setStaticField(final Class<?> cls, final String name, final Object value) throws ReflectiveOperationException {
        setField(cls, null, name, value, false);
    }

    /**
     * <p>
     * A convenient shorthand for {@link #setField(Class, Object, String, Object, boolean)} where no instance is
     * provided.
     * </p>
     *
     * @param cls
     *         the class containing the field whose value is to be set to {@code value}
     * @param name
     *         the name of the field whose value is to be set to {@code value}
     * @param value
     *         the value to be set on the field (may be {@literal null})
     * @param declared
     *         {@literal true} if the field is declared; otherwise {@literal false}
     * @throws NullPointerException
     *         If either {@code cls} or {@code name} are {@literal null}.
     * @throws ReflectiveOperationException
     *         If a reflection error occurs while trying to set the field value.
     */
    public static void setStaticField(final Class<?> cls, final String name, final Object value, final boolean declared) throws ReflectiveOperationException {
        setField(cls, null, name, value, declared);
    }

    private static Class<?>[] deriveParameterTypes(final Object[] parameters) {
        if (parameters == null) {
            return new Class<?>[0];
        }

        final Class<?>[] parameterTypes = new Class<?>[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            final Object parameter = parameters[i];
            if (parameter == null) {
                throw new NullPointerException("parameter[" + i + "] must not be null");
            }

            parameterTypes[i] = parameter.getClass();
        }

        return parameterTypes;
    }

    private static Object invokeMethodInternal(final Class<?> cls, final Object instance, final String name, final Class<?>[] parameterTypes, final Object[] parameters, final boolean declared, final boolean returnable) throws ReflectiveOperationException {
        final Class<?> safeClass = cls != null ? cls : instance.getClass();
        final Class<?>[] safeParameterTypes = parameterTypes != null ? parameterTypes : new Class<?>[0];
        final Object[] safeParameters = parameters != null ? parameters : new Object[0];

        if (safeParameterTypes.length != safeParameters.length) {
            throw new IllegalArgumentException("Number of parameterTypes must match number of parameters");
        }

        final Method method = declared ? safeClass.getDeclaredMethod(name, safeParameterTypes) : safeClass.getMethod(name, safeParameterTypes);
        method.setAccessible(true);

        if (returnable && method.getReturnType().equals(Void.TYPE)) {
            throw new IllegalStateException("Cannot return value for void method");
        }

        return method.invoke(instance, safeParameters);
    }

    private static void removeFinalModifier(final Field field) throws ReflectiveOperationException {
        final int modifiers = field.getModifiers();
        if (Modifier.isFinal(modifiers)) {
            final Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(field, modifiers & ~Modifier.FINAL);
        }
    }

    /**
     * <p>
     * Creates an instance of {@link TestUtils}.
     * </p>
     * <p>
     * This should <b>not</b> be used for standard programming but is available for cases where an instance is needed
     * for a Java Bean etc.
     * </p>
     */
    public TestUtils() {
    }
}
