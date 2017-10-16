package com.gregortorrence.kagemusha

import spock.lang.Specification

import java.security.Permission

class MainSpec extends Specification {

    def 'no meaningful way to test instantiating the Main class'() {
        when:
        new Main()

        then:
        noExceptionThrown()
    }

    // Slight overkill to test the exit behavior. Installs a security manager to prevent the System.exit from actually happening.
    def 'main should return expected zero or non-zero code'() {
        given:
        def defaultSecurityManager = System.getSecurityManager()
        System.setSecurityManager(new NoExitSecurityManager())

        and:
        def xlfFilePath = new File(getClass().getClassLoader().getResource('messages.xlf').getFile()).getPath()
        def tempFilePath = File.createTempFile('tmp', '.tmp').getPath()

        when:
        Main.main(xlfFilePath, tempFilePath)
        then:
        def e1 = thrown(ExpectedExitCodeException)
        e1.getExitCode() == 0

        when:
        Main.main(xlfFilePath)
        then:
        def e2 = thrown(ExpectedExitCodeException)
        e2.getExitCode() != 0

        cleanup:
        System.setSecurityManager(defaultSecurityManager)
        new File(tempFilePath).delete()
    }

    private class NoExitSecurityManager extends SecurityManager
    {
        void checkExit(int status)
        {
            throw new ExpectedExitCodeException(status)
        }

        void checkPermission(Permission perm)
        {
        }
    }

    private class ExpectedExitCodeException extends Throwable {
        private int exitCode;

        ExpectedExitCodeException(int exitCode) {
            this.exitCode = exitCode
        }

        int getExitCode() {
            return exitCode
        }
    }

}
