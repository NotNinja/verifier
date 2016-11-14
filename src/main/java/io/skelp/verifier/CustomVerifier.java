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
package io.skelp.verifier;

import io.skelp.verifier.verification.Verification;

/**
 * <p>
 * A custom verifier provides a chain of handy verification methods which can be specific to a data type (e.g. {@code
 * String}) and/or a use case (e.g. email address).
 * </p>
 *
 * @param <T>
 *         the type of the value being verified
 * @param <V>
 *         the type of the {@link CustomVerifier} for chaining purposes
 * @author Alasdair Mercer
 */
public interface CustomVerifier<T, V extends CustomVerifier<T, V>> {

    /**
     * <p>
     * Verifies that the value is equal to the {@code other} provided.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify(null).equalTo(null)   => PASS
     * Verifier.verify(null).equalTo("abc")  => FAIL
     * Verifier.verify("abc").equalTo(null)  => FAIL
     * Verifier.verify("abc").equalTo("abc") => PASS
     * Verifier.verify("abc").equalTo("ABC") => FAIL
     * Verifier.verify("abc").equalTo("def") => FAIL
     * </pre>
     *
     * @param other
     *         the object to compare against the value (may be {@literal null})
     * @return A reference to this {@link CustomVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #equalTo(Object, Object)
     * @see Object#equals(Object)
     */
    V equalTo(Object other) throws VerifierException;

    /**
     * <p>
     * Verifies that the value is equal to the {@code other} provided while allowing {@code other} to be given an
     * optional friendlier {@code name} for the {@link VerifierException} message in the event that one is thrown.
     * </p>
     * <p>
     * While {@code name} can be {@literal null} it is recommended instead to either use {@link #equalTo(Object)} or
     * pass {@code other} as {@code name} instead to get a better verification error message.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify(null, *).equalTo(null)   => PASS
     * Verifier.verify(null, *).equalTo("abc")  => FAIL
     * Verifier.verify("abc", *).equalTo(null)  => FAIL
     * Verifier.verify("abc", *).equalTo("abc") => PASS
     * Verifier.verify("abc", *).equalTo("ABC") => FAIL
     * Verifier.verify("abc", *).equalTo("def") => FAIL
     * </pre>
     *
     * @param other
     *         the object to compare against the value (may be {@literal null})
     * @param name
     *         the optional name used to represent {@code other} (may be {@literal null})
     * @return A reference to this {@link CustomVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #equalTo(Object)
     * @see Object#equals(Object)
     */
    V equalTo(Object other, Object name) throws VerifierException;

    /**
     * <p>
     * Verifies that the value is equal to any of the {@code others} provided.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify(*).equalToAny()                         => FAIL
     * Verifier.verify(*).equalToAny((Object[]) null)          => FAIL
     * Verifier.verify(null).equalToAny("ghi", "def", null)    => PASS
     * Verifier.verify(null).equalToAny("ghi", "def", "abc")   => FAIL
     * Verifier.verify("abc").equalToAny("ghi", "def", null)   => FAIL
     * Verifier.verify("abc").equalToAny("ghi", "def", "abc")  => PASS
     * Verifier.verify("abc").equalToAny("GHI", "DEF", "ABC")  => FAIL
     * </pre>
     *
     * @param others
     *         the objects to compare against the value (may be {@literal null} or contain {@literal null} references)
     * @return A reference to this {@link CustomVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see Object#equals(Object)
     */
    V equalToAny(Object... others) throws VerifierException;

    /**
     * <p>
     * Verifies that the value has the {@code hashCode} provided.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify(null).hashedAs(*)   => FAIL
     * Verifier.verify(123).hashedAs(0)    => FAIL
     * Verifier.verify(123).hashedAs(321)  => FAIL
     * Verifier.verify(123).hashedAs(123)  => PASS
     * Verifier.verify(123).hashedAs(-123) => FAIL
     * </pre>
     *
     * @param hashCode
     *         the hash code to be compared against that of the value
     * @return A reference to this {@link CustomVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see Object#hashCode()
     */
    V hashedAs(int hashCode) throws VerifierException;

    /**
     * <p>
     * Verifies that the value is an instance of the class provided.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify(*).instanceOf(null)                           => FAIL
     * Verifier.verify(*).instanceOf(Object.class)                   => PASS
     * Verifier.verify(null).instanceOf(*)                           => FAIL
     * Verifier.verify(new ArrayList()).instanceOf(Collection.class) => PASS
     * Verifier.verify(new ArrayList()).instanceOf(Map.class)        => FAIL
     * </pre>
     *
     * @param cls
     *         the {@code Class} to be checked as a parent of the value (may be {@literal null})
     * @return A reference to this {@link CustomVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see Class#isInstance(Object)
     */
    V instanceOf(Class<?> cls) throws VerifierException;

    /**
     * <p>
     * Verifies that the value is an instance of <b>all</b> of the {@code classes} provided.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify(*).instanceOfAll()                                                            => PASS
     * Verifier.verify(*).instanceOfAll((Object[]) null)                                             => PASS
     * Verifier.verify(*).instanceOfAll(Object.class)                                                => PASS
     * Verifier.verify(*).instanceOfAll(*, null)                                                     => FAIL
     * Verifier.verify(null).instanceOfAll(*)                                                        => FAIL
     * Verifier.verify(new ArrayList()).instanceOfAll(ArrayList.class, List.class, Collection.class) => PASS
     * Verifier.verify(new ArrayList()).instanceOfAll(ArrayList.class, Map.class, Collection.class)  => FAIL
     * </pre>
     *
     * @param classes
     *         the classes to be checked as parents of the value (may be {@literal null} or contain {@literal null}
     *         references)
     * @return A reference to this {@link CustomVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see Class#isInstance(Object)
     */
    V instanceOfAll(Class<?>... classes) throws VerifierException;

    /**
     * <p>
     * Verifies that the value is an instance of <b>any</b> of the {@code classes} provided.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify(*).instanceOfAny()                                                         => FAIL
     * Verifier.verify(*).instanceOfAny((Object[]) null)                                          => FAIL
     * Verifier.verify(*).instanceOfAny(*, Object.class)                                          => PASS
     * Verifier.verify(null).instanceOfAny(*)                                                     => FAIL
     * Verifier.verify(new ArrayList()).instanceOfAny(Boolean.class, Map.class, Collection.class) => PASS
     * Verifier.verify(new ArrayList()).instanceOfAny(Boolean.class, Map.class, URI.class)        => FAIL
     * </pre>
     *
     * @param classes
     *         the classes to be checked as parents of the value (may be {@literal null} or contain {@literal null}
     *         references)
     * @return A reference to this {@link CustomVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see Class#isInstance(Object)
     */
    V instanceOfAny(Class<?>... classes) throws VerifierException;

    /**
     * <p>
     * Negates the result of the next verification to be checked.
     * </p>
     * <p>
     * There is no limit to how many times this method can be called sequentially and it will simply keep negating
     * itself.
     * </p>
     * <pre>
     * Verifier.verify(null).not().nulled()               => FAIL
     * Verifier.verify(new Object()).not().nulled()       => PASS
     * Verifier.verify(null).not().not().nulled()         => PASS
     * Verifier.verify(new Object()).not().not().nulled() => FAIL
     * </pre>
     *
     * @return A reference to this {@link CustomVerifier} for chaining purposes.
     */
    V not();

    /**
     * <p>
     * Verifies that the value {@literal null}.
     * </p>
     * <pre>
     * Verifier.verify(null).nulled()         => PASS
     * Verifier.verify(new Object()).nulled() => FAIL
     * </pre>
     *
     * @return A reference to this {@link CustomVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     */
    V nulled() throws VerifierException;

    /**
     * <p>
     * Verifies that the value is the same the {@code other} provided.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify(null).sameAs(null)            => PASS
     * Verifier.verify(null).sameAs(123)             => FAIL
     * Verifier.verify(123).sameAs(null)             => FAIL
     * Verifier.verify(123).sameAs(123)              => PASS
     * Verifier.verify(213).sameAs(new Integer(123)) => FAIL
     * </pre>
     *
     * @param other
     *         the object to compare against the value (may be {@literal null})
     * @return A reference to this {@link CustomVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #sameAs(Object, Object)
     */
    V sameAs(Object other) throws VerifierException;

    /**
     * <p>
     * Verifies that the value is the same as the {@code other} provided while allowing {@code other} to be given an
     * optional friendlier {@code name} for the {@link VerifierException} message in the event that one is thrown.
     * </p>
     * <p>
     * While {@code name} can be {@literal null} it is recommended instead to either use {@link #sameAs(Object)} or
     * pass {@code other} as {@code name} instead to get a better verification error message.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify(null, *).sameAs(null)            => PASS
     * Verifier.verify(null, *).sameAs(123)             => FAIL
     * Verifier.verify(123, *).sameAs(null)             => FAIL
     * Verifier.verify(123, *).sameAs(123)              => PASS
     * Verifier.verify(213, *).sameAs(new Integer(123)) => FAIL
     * </pre>
     *
     * @param other
     *         the object to compare against the value (may be {@literal null})
     * @param name
     *         the optional name used to represent {@code other} (may be {@literal null})
     * @return A reference to this {@link CustomVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #sameAs(Object)
     */
    V sameAs(Object other, Object name) throws VerifierException;

    /**
     * <p>
     * Verifies that the value is the same as any of the {@code others} provided.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify(*).sameAsAny()                                                       => FAIL
     * Verifier.verify(*).sameAsAny((Object[]) null)                                        => FAIL
     * Verifier.verify(null).sameAsAny(789, 456, null)                                      => PASS
     * Verifier.verify(null).sameAsAny(789, 456, 123)                                       => FAIL
     * Verifier.verify(123).sameAsAny(789, 456, null)                                       => FAIL
     * Verifier.verify(123).sameAsAny(789, 456, 123)                                        => PASS
     * Verifier.verify(123).sameAsAny(new Integer(789), new Integer(456), new Integer(123)) => FAIL
     * </pre>
     *
     * @param others
     *         the objects to compare against the value (may be {@literal null} or contain {@literal null} references)
     * @return A reference to this {@link CustomVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     */
    V sameAsAny(Object... others) throws VerifierException;

    /**
     * <p>
     * Verifies that the value passes the {@code assertion} provided.
     * </p>
     * <pre>
     * Verifier.verify(*).that(null)                                                  => FAIL
     * Verifier.verify(*).not().that(null)                                            => FAIL
     * Verifier.verify(null).that(value -> "Mork".equals(value))                      => FAIL
     * Verifier.verify(new User("Mork")).that(user -> "Mork".equals(user.getName()))  => PASS
     * Verifier.verify(new User("Mindy")).that(user -> "Mork".equals(user.getName())) => FAIL
     * </pre>
     *
     * @param assertion
     *         the {@link VerifierAssertion} to be used to verify the value
     * @return A reference to this {@link CustomVerifier} for chaining purposes.
     * @throws VerifierException
     *         If {@code assertion} is {@literal null} or the verification fails while not negated or passes while
     *         negated.
     * @see #that(VerifierAssertion, String, Object...)
     */
    V that(VerifierAssertion<T> assertion) throws VerifierException;

    /**
     * <p>
     * Verifies that the value passes the {@code assertion} provided while allowing an optional {@code message} and
     * format {@code args} to be specified to enhance the {@link VerifierException} message in the event that one is
     * thrown.
     * </p>
     *
     * @param assertion
     *         the {@link VerifierAssertion} to be used to verify the value
     * @param message
     *         the optional message which provides a (slightly) more detailed explanation of what is being verified
     * @param args
     *         the optional format arguments which are only used to format {@code message}
     * @return A reference to this {@link CustomVerifier} for chaining purposes.
     * @throws VerifierException
     *         If {@code assertion} is {@literal null} or the verification fails while not negated or passes while
     *         negated.
     * @see #that(VerifierAssertion)
     */
    V that(VerifierAssertion<T> assertion, String message, Object... args) throws VerifierException;

    /**
     * <p>
     * Returns the value being verified by this {@link CustomVerifier}.
     * </p>
     * <pre>
     * Verifier.verify(null).nulled().value()        = null
     * Verifier.verify("abc").not().nulled().value() = "abc"
     * </pre>
     *
     * @return The value.
     */
    T value();

    /**
     * <p>
     * Returns the verification information for this {@link CustomVerifier}.
     * </p>
     * <p>
     * This is not generally useful outside of the library but it is part of the API for extensions and easier
     * testability.
     * </p>
     *
     * @return The {@link Verification}.
     */
    Verification<T> verification();
}
