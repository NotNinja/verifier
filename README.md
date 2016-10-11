# Verifier

[Verifier](https://github.com/Skelp/verifier) is a Java library for validation.

[![Build Status](https://img.shields.io/travis/Skelp/verifier/develop.svg?style=flat-square)](https://travis-ci.org/Skelp/verifier)
[![License](https://img.shields.io/github/license/Skelp/verifier.svg?style=flat-square)](https://github.com/Skelp/verifier/blob/master/LICENSE.md)
[![Release](https://img.shields.io/maven-central/v/io.skelp/verifier.svg?style=flat-square)](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22io.skelp%22%20AND%20a%3A%22verifier%22)

> This project is currently very much a work in progress and is not yet functional

## Install

TODO: Document

## API

TODO: Document

## Dream Code

Some dream code to drive design and development:

``` java
Verifier.verify(num)
  .even(optionalMessage)
  .odd(optionalMessage)
  .positive(optionalMessage)
  .negative(optionalMessage)
  .lessThan(otherValue, optionalMessage) // alias: lt
  .lessThanOrEqualTo(otherValue, optionalMessage) // alias: lte
  .greaterThan(otherValue, optionalMessage) // alias: gt
  .greaterThanOrEqualTo(otherValue, optionalMessage) // alias: gte
  .equalTo(otherValue, optionalMessage) // alias: eq
  .that(VerifierAssertion, optionalMessage);

Verifier.verify(num, NumberVerifier.class)
  .not().even(optionalMessage)
  .not().odd(optionalMessage)
  .not().positive(optionalMessage)
  .not().negative(optionalMessage)
  .not().lessThan(otherValue, optionalMessage)
  .not().lessThanOrEqualTo(otherValue, optionalMessage)
  .not().greaterThan(otherValue, optionalMessage)
  .not().greaterThanOrEqualTo(otherValue, optionalMessage)
  .not().equalTo(otherValue, optionalMessage)
  .not().that(VerifierAssertion, optionalMessage);

Verifier.verify(post, MyCustomVerifier.class)
  .owned(myUser, optionalMessage)
  .not().owned(otherUser, optionalMessage);
```

Right now, that's what we want to achieve with this library.

## Bugs

If you have any problems with Verifier or would like to see changes currently in development you can do so
[here](https://github.com/Skelp/verifier/issues).

## Contributors

If you want to contribute, you're a legend! Information on how you can do so can be found in
[CONTRIBUTING.md](https://github.com/Skelp/verifier/blob/master/CONTRIBUTING.md). We want your suggestions and pull
requests!

A list of Verifier contributors can be found in [AUTHORS.md](https://github.com/Skelp/verifier/blob/master/AUTHORS.md).

## License

See [LICENSE.md](https://github.com/Skelp/verifier/raw/master/LICENSE.md) for more information on our MIT license.

© 2016 [Skelp](https://skelp.io)
<img align="right" width="16" height="16" src="https://cdn.rawgit.com/Skelp/skelp-branding/master/assets/logo/base/skelp-logo-16x16.png">
