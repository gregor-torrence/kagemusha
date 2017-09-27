package com.gregortorrence.kagemusha

import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

class CommandLineExecutorSpec extends Specification {

    @Shared def xlfFilePath = new File(getClass().getClassLoader().getResource('messages.xlf').getFile()).getPath()
    @Shared def txtFilePath = new File(getClass().getClassLoader().getResource('LocalizedStrings.properties').getFile()).getPath()
    @Shared def propertiesFilePath = new File(getClass().getClassLoader().getResource('hamlet.txt').getFile()).getPath()
    @Shared def tempFilePath = File.createTempFile('tmp', '.tmp').getPath()

    @Unroll
    def 'should return expected zero/non-zero exit code for #args'() {
        expect:
        (new CommandLineExecutor().execute(args) == 0) == isZero

        cleanup:
        new File(tempFilePath).delete()

        where:
        isZero | args
        false  | []
        false  | [ 'foo' ]
        false  | [ 'foo','foo' ]
        false  | [ 'foo','foo','foo' ]
        false  | [ 'foo1.xlf',tempFilePath ]
        false  | [ { throw new IOException() }, tempFilePath ]
        false  | [ xlfFilePath, { throw new IOException() } ]
        true   | [ xlfFilePath, tempFilePath ]
        true   | [ txtFilePath, tempFilePath ]
        true   | [ propertiesFilePath, tempFilePath ]
    }

}
