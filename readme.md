# Kagemusha #

Command line tool for automatically translating English into "Japanese". It takes regular
ASCII characters and translates them into Japanese characters that happen to look like
widened English. For example, the plain ascii string `the quick brown fox` would be translated into 
`ｔｈｅ　ｑｕｉｃｋ　ｂｒｏｗｎ　ｆｏｘ`,  which looks like English, but from a Unicode perspective 
is actually Japanese. All resulting characters are from [this CJKV code page](http://www.unicode.org/charts/PDF/UFF00.pdf).

This will not be useful for any sort of real localization, but if you 
use it in your development or test environment to process your UI strings before 
they are displayed, you will simplify verifying your application's localization process:
   
  * You will verify that unicode Strings are correctly handled.
  * You will verify that your font rendering can handle unicode.
  * You will verify your layout can handle a wider variation of rendered String width.
  * You will not need to learn another language, since the text still reads as the same. 
    
Unicode only supports wide character variants of plain ASCII characters. Despite that, the input strings can be from any 
language that uses mostly Roman letters. This includes nearly every European language as well as Vietnamese. These are 
supported by stripping off the diacritical marks before translation. The results can be described as serviceable, but 
certainly not lossless. 
  
The name is taken from the [1980 Kurosawa film](http://www.imdb.com/title/tt0080979/) in which a feudal Japanese lord is replaced with an impostor.
  
### Building the Executable ###
  
1. Be certain you have git and a JDK 8 or later available on the command line.
1. Clone this repository and `cd` to the root of the checkout
1. Run `./gradlew clean test jar`. This will create the executable `build/lib/kagemusha.jar`.
1. Copy the jar to where you would like to use it, or simply reference its full path when invoking java. 
Note that this is a "fat jar" that contains the application and all of its dependencies.
  
### Usage ###

```
java -jar kagamusha.jar inputFile outputFile
```
In order to play well with being invoked from a shell script, a zero exit code indicates a successful translation. 
Non-zero indicates a failure. Error messages are written to stderr. Nothing is written to stdout.

### Supported file types ###
```
.txt         Entire file is translated.
.properties  Property values are translated, keys are unchanged. Format arguments are preserved.
.json        Fields with a String value are translated. Suitable for translating React localization files.
.xlf         Display string values are translated. Used for translating Angular 2+ localization files.
```

The correct file handling is executed given the extension of the input file's name.

*TODO: The full [XLIFF](https://wiki.oasis-open.org/xliff/) is not yet supported. The model package and its tests need to be completed to support all possible elements.*


### Developer notes ###

* XML and JSON are parsed with [Jackson](https://github.com/FasterXML).
* HTML is parsed with [JSoup](https://jsoup.org/).
* POJO models are manipulated using [Lombok](https://projectlombok.org/).
* [Guava](https://github.com/google/guava/wiki) is used because it's just so damned useful.
* [Spock](http://spockframework.org/) unit tests cover 100% of classes, 100% of methods, and 99.4% of lines according to Emma.
* The secret sauce to make the fat jar is in `build.gradle`.
* If you open this project in [IntelliJ IDEA](https://www.jetbrains.com/idea/), don't forget to enable annotation processing in preferences. That will make it happy with Lombok.
