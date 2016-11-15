# Kagemusha - The pseudo-Japanese translator #
------

This is a Java class that performs a sort of automatic translation of English into Japanese. It takes regular ASCII characters and converts them into Japanese characters that just happen to look like wide English. This will not be useful for any sort of real localization, but if you temporarily use is to process your UI strings before they are displayed, you will get the following benefits:
  
  * You will verify that unicode Strings are correctly handled.
  * You will verify that your fonts rendering can handle unicode.
  * Converted Strings are wider, so you will verify your layout can handle variation in rendered String width.
  * You will not need to learn another language, since the text still reads as English.
  
A Kagemusha unit test is provided, written in Spock.

If you don't use Java, copy the contents of the fullWidth and halfWidth Strings from Kagamusha.java and perforn the look-up-and-substitute in your preferred programming language.

The name is taken from the Kurosawa film where a feudal Japanese lord is replaced with an imposter.
