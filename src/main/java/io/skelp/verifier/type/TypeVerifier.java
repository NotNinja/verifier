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

import io.skelp.verifier.VerifierAssertion;
import io.skelp.verifier.VerifierException;

/**
 * TODO: Document
 *
 * @param <T>
 * @param <V>
 * @author Alasdair Mercer
 */
public interface TypeVerifier<T, V extends TypeVerifier<T, V>> {

  /**
   * TODO: Document
   *
   * @param other
   * @return
   * @throws VerifierException
   */
  V equalTo(Object other);

  /**
   * TODO: Document
   *
   * @param other
   * @param name
   * @return
   * @throws VerifierException
   */
  V equalTo(Object other, Object name);

  /**
   * TODO: Document
   *
   * @param others
   * @return
   * @throws VerifierException
   */
  V equalToAny(Object... others);

  /**
   * TODO: Document
   *
   * @param cls
   * @return
   * @throws VerifierException
   */
  V instanceOf(Class<?> cls);

  /**
   * TODO: Document
   *
   * @param classes
   * @return
   * @throws VerifierException
   */
  V instanceOfAny(Class<?>... classes);

  /**
   * TODO: Document
   *
   * @return
   */
  V not();

  /**
   * TODO: Document
   *
   * @return
   * @throws VerifierException
   */
  V nulled();

  /**
   * TODO: Document
   *
   * @param other
   * @return
   * @throws VerifierException
   */
  V sameAs(Object other);

  /**
   * TODO: Document
   *
   * @param other
   * @param name
   * @return
   * @throws VerifierException
   */
  V sameAs(Object other, Object name);

  /**
   * TODO: Document
   *
   * @param others
   * @return
   * @throws VerifierException
   */
  V sameAsAny(Object... others);

  /**
   * TODO: Document
   *
   * @param assertion
   * @return
   * @throws VerifierException
   */
  V that(VerifierAssertion<T> assertion);

  /**
   * TODO: Document
   *
   * @param assertion
   * @param message
   * @param args
   * @return
   * @throws VerifierException
   */
  V that(VerifierAssertion<T> assertion, String message, Object... args);
}
