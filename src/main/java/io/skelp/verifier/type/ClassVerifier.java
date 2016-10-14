/*
 * Copyright (C) 2016 Alasdair Mercer, Skelp
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
package io.skelp.verifier.type;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import io.skelp.verifier.Verification;
import io.skelp.verifier.VerifierException;

/**
 * TODO: Document
 *
 * @param <V>
 * @author Alasdair Mercer
 */
public class ClassVerifier<V extends ClassVerifier<V>> extends BaseTypeVerifier<Class<?>, V> {

  private static final Set<Class<?>> PRIMITIVE_WRAPPERS;

  static {
    final List<Class<?>> primitiveWrappers = Arrays.asList(Boolean.class, Byte.class, Character.class, Double.class, Float.class, Integer.class, Long.class, Short.class, Void.TYPE);
    PRIMITIVE_WRAPPERS = Collections.unmodifiableSet(new HashSet<>(primitiveWrappers));
  }

  /**
   * TODO: Document
   *
   * @param verification
   */
  public ClassVerifier(final Verification<Class<?>> verification) {
    super(verification);
  }

  /**
   * TODO: Document
   *
   * @param type
   * @return
   * @throws VerifierException
   */
  public V annotated(final Class<? extends Annotation> type) {
    final Class<?> value = verification.getValue();
    final boolean result = value != null && value.isAnnotationPresent(type);

    verification.check(result, "be annotated with '%s'", type);

    return chain();
  }

  /**
   * TODO: Document
   *
   * @return
   * @throws VerifierException
   */
  public V annotation() {
    final Class<?> value = verification.getValue();
    final boolean result = value != null && value.isAnnotation();

    verification.check(result, "be an annotation");

    return chain();
  }

  /**
   * TODO: Document
   *
   * @return
   * @throws VerifierException
   */
  public V anonymous() {
    final Class<?> value = verification.getValue();
    final boolean result = value != null && value.isAnonymousClass();

    verification.check(result, "be anonymous");

    return chain();
  }

  /**
   * TODO: Document
   *
   * @return
   * @throws VerifierException
   */
  public V array() {
    final Class<?> value = verification.getValue();
    final boolean result = value != null && value.isArray();

    verification.check(result, "be an array");

    return chain();
  }

  /**
   * TODO: Document
   *
   * @param type
   * @return
   * @throws VerifierException
   */
  public V assignableFrom(final Class<?> type) {
    final Class<?> value = verification.getValue();
    final boolean result = value != null && value.isAssignableFrom(type);

    verification.check(result, "be assignable from '%s'", type);

    return chain();
  }

  /**
   * TODO: Document
   *
   * @return
   * @throws VerifierException
   */
  public V enumeration() {
    final Class<?> value = verification.getValue();
    final boolean result = value != null && value.isEnum();

    verification.check(result, "be an enum");

    return chain();
  }

  /**
   * TODO: Document
   *
   * @return
   * @throws VerifierException
   */
  public V interfacing() {
    final Class<?> value = verification.getValue();
    final boolean result = value != null && value.isInterface();

    verification.check(result, "be an interface");

    return chain();
  }

  /**
   * TODO: Document
   *
   * @return
   * @throws VerifierException
   */
  public V nested() {
    final Class<?> value = verification.getValue();
    final boolean result = value != null && value.getEnclosingClass() != null;

    verification.check(result, "be nested");

    return chain();
  }

  /**
   * TODO: Document
   *
   * @return
   * @throws VerifierException
   */
  public V primitive() {
    final Class<?> value = verification.getValue();
    final boolean result = value != null && value.isPrimitive();

    verification.check(result, "be a primitive");

    return chain();
  }

  /**
   * TODO: Document
   *
   * @return
   * @throws VerifierException
   */
  public V primitiveOrWrapper() {
    final Class<?> value = verification.getValue();
    final boolean result = value != null && (value.isPrimitive() || PRIMITIVE_WRAPPERS.contains(value));

    verification.check(result, "be a primitive or primitive wrapper");

    return chain();
  }

  /**
   * TODO: Document
   *
   * @return
   * @throws VerifierException
   */
  public V primitiveWrapper() {
    final Class<?> value = verification.getValue();
    final boolean result = PRIMITIVE_WRAPPERS.contains(value);

    verification.check(result, "be a primitive wrapper");

    return chain();
  }

  /**
   * TODO: Document
   *
   * @return
   * @throws VerifierException
   */
  public V synthetic() {
    final Class<?> value = verification.getValue();
    final boolean result = value != null && value.isSynthetic();

    verification.check(result, "be synthetic");

    return chain();
  }
}
