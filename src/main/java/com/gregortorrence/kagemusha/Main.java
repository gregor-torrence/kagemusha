package com.gregortorrence.kagemusha;

import com.google.common.collect.Lists;

public class Main {

    public static void main(String[] args) {
        System.exit(new CommandLineExecutor().execute(Lists.newArrayList(args)));
    }

}
