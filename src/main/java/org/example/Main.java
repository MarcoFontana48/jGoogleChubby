package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {

        logger.info("cosa vede il client quando si connette:");
        System.out.println("/ls/cell_0/> ");
        System.out.println("/ls/cell_0/> ls");
        System.out.println("dir_1\ndir_2\nfile.txt");
        System.out.println("/ls/cell_0/> cd dir_1");
        System.out.println("/ls/cell_0/dir_1/> ");
        System.out.println("/ls/cell_0/dir_1/> ls");
        System.out.println("dir_A\nfileA.txt");
        System.out.println("/ls/cell_0/dir_1/> write fileA.txt");
        System.out.println("*prende la lock / caching / e fa le modifiche*");
    }
}
