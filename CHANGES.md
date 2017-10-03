## Version 0.2.0, 2017.02.11

* Drop support for Java 7 [#9](https://github.com/NotNinja/verifier/issues/9) (**breaking change**)
* Support nested arrays and circular references in `DefaultArrayFormatter` [#10](https://github.com/NotNinja/verifier/issues/10)
* Support different locales [#11](https://github.com/NotNinja/verifier/issues/11) (**breaking change**)
* Support multiple verification reporters [#14](https://github.com/NotNinja/verifier/issues/14) (**breaking change**)
* Add `ClassVerifier#assignableFromAll` & `ClassVerifier#assignableFromAny` [#15](https://github.com/NotNinja/verifier/issues/15)
* Rename `ClassVerifier#interfacing` to `ClassVerifier#interfaced` [#16](https://github.com/NotNinja/verifier/issues/16) (**breaking change**)
* Rename `LocaleVerifier#defaulting` to `LocaleVerifier#defaulted` [#17](https://github.com/NotNinja/verifier/issues/17) (**breaking change**)
* Add `StringVerifier#matchAll` & `StringVerifier#matchAny` [#18](https://github.com/NotNinja/verifier/issues/18)
* Add `ThrowableVerifier#causedByAll` & `ThrowableVerifier#causedByAny` [#19](https://github.com/NotNinja/verifier/issues/19)
* Add more formatters [#21](https://github.com/NotNinja/verifier/issues/21)
* Add CustomVerifier#and & CustomVerifier#andComparable methods to allow even more fluid chaining [#22](https://github.com/NotNinja/verifier/issues/22)
* Add translations badge to README via [Crowdin](https://crowdin.com)
* Add JavaDoc badge to README via [JavaDoc](https://www.javadoc.io)
* Add code coverage badge to README via [Codecov](https://codecov.io)
* Add changelog
