package jars;

import text.TextFormatter;

public class Main {
    public static void main(String[] args) {
        TextFormatter textf = new TextFormatter("HAllo Welt");
        textf.spaceOutText();
        textf.displayText();
        textf.translateToLeet();
        textf.displayText();
    }
}
