package com.gregortorrence.kagemusha.translators;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import java.text.Normalizer;
import java.util.regex.Pattern;

/**
 * Class for translating ASCII into Unicode 0xFFnn characters. The only public method is
 *      public static String translate(String source)
 */
public class Kagemusha {

    /**
     * Class has no non-static methods. Make it impossible to instantiate.
     */
    private Kagemusha() {
    }

    private static final String halfWidth = "\r\n\t 0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!\"'’#$%&()*+,-./:;<=>?@[\\]^_`{|}~";
    private static final String fullWidth = "\r\n\t　０１２３４５６７８９ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ！゛＇＇＃＄％＆（）＊＋、ー。／：；〈＝〉？＠［\\］＾＿‘｛｜｝～";

    private static final Pattern diacriticsPattern = Pattern.compile("[\\p{InCombiningDiacriticalMarks}\\p{IsLm}\\p{IsSk}]+");

    /**
     * Makes a rudimentary guess about the format of the supplied string: URL, HTML, Java Format, or plain text,
     * and then performs the appropriate translation.
     */
    public static String translate(String source) {
        if (isUrl(source)) {
            return source;
        } else if (containsHtmlTags(source)) {
            return translateHtmlSnippet(source);
        } else if (containsSubstitutions(source)) {
            return translateSubstitutions(source);
        } else if (containsFormatArguments(source)) {
            return translateFormat(source);
        } else {
            return translateText(source);
        }
    }

    /**
     * Plain text translation.
     */
    private static String translateText(String source) {
        source = stripDiacritics(source);
        StringBuilder builder = new StringBuilder(source.length());
        for (char ch: source.toCharArray()) {
            int index = halfWidth.indexOf(ch);
            if (index >= 0) {
                builder.append(fullWidth.charAt(index));
            } else {
                builder.append('？');
            }
        }
        return builder.toString();
    }

    private static String stripDiacritics(String str) {
        str = Normalizer.normalize(str, Normalizer.Form.NFD);
        str = diacriticsPattern.matcher(str).replaceAll("");
        return str;
    }

    /**
     * Translates HTML, preserves tags. Uses JSoup to parse the supplied HTML snippet as XML.
     * If we parsed it as HTML, JSoup would add html, head, and body tags.
     */
    private static String translateHtmlSnippet(String html) {
        // Translate the txt, but leave the HTML unchanged
        Document document = Jsoup.parse(html, "", Parser.xmlParser());
        document.getAllElements().forEach(element ->
                element.textNodes().forEach(textNode ->
                        textNode.text(translateText(textNode.text()))
                ));
        document.outputSettings().prettyPrint(false);
        return document.toString();
    }

    /**
     * Translates a Java Format String. For example, "Hello, {0}"
     */
    private static String translateFormat(String source) {
        StringBuilder builder = new StringBuilder();
        StringBuilder segment = new StringBuilder();

        for (char ch : source.toCharArray()) {
            if (ch == '{') {
                builder.append(translateText(segment.toString()));
                segment.setLength(0);
                segment.append(ch);
            } else if (ch == '}') {
                segment.append(ch);
                builder.append(segment);
                segment.setLength(0);
            } else {
                segment.append(ch);
            }
        }
        builder.append(translateText(segment.toString()));

        return builder.toString();
    }

    /**
     * Translates a String with substitutions. For example, "Hello, ${name}"
     */
    private static String translateSubstitutions(String source) {
        StringBuilder builder = new StringBuilder();
        StringBuilder segment = new StringBuilder();
        char ch, nextCh;

        for (int i=0; i<source.length(); i++) {
            ch = source.charAt(i);
            nextCh = i+1 < source.length() ? source.charAt(i+1) : 0;
            if (ch == '$' && nextCh == '{') {
                builder.append(translateText(segment.toString()));
                segment.setLength(0);
                segment.append("${");
                i++;
            } else if (ch == '}') {
                segment.append(ch);
                builder.append(segment);
                segment.setLength(0);
            } else {
                segment.append(ch);
            }
        }
        builder.append(translateText(segment.toString()));

        return builder.toString();
    }

    private static boolean isUrl(String string) {
        String value = string.toLowerCase().trim();
        return value.startsWith("http://") || value.startsWith("https://");
    }

    private static boolean containsHtmlTags(String source) {
        return source.contains("<") && source.contains(">");
    }

    private static boolean containsFormatArguments(String source) {
        return source.contains("{") && source.contains("}");
    }

    private static boolean containsSubstitutions(String source) {
        int start = source.indexOf("${");
        int end   = source.indexOf("}");
        return (start >= 0) && (end > 1) && (start < end);
    }

}
