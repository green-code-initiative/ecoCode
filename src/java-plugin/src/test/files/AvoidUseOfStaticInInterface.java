package fr.cnumr.java.utils;

public class AvoidUseOfStaticInInterface {

    public interface Color {
        public static final int RED = 0xff0000;// Noncompliant
        public static final int BLACK = 0x000000;// Noncompliant
        public static final int WHITE = 0xffffff;// Noncompliant
    }

}
