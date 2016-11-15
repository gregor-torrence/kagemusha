package main.java;

/**
 * Utility method for performing a pseudo-translation of English text. All characters are converted to wider
 * representations which are technically Japanese characters, but still read as English. See this link for more
 * information: http://www.unicode.org/charts/PDF/UFF00.pdf
 *
 * The result of this process proves that character encoding is preserved by an application.
 * The distinct visual appearance proves that a string is translated, while keeping it legible for an English speaker.
 * The wider converted strings prove than an application can handle translated strings longer than the English.
 *
 * Since this class performs a sort of a Japanese translation, it is named after Kurosawa's classic 1980 film.
 *
 * Created by gtorr3 on 1/30/15.
 */
public class Kagemusha {

    private static final String halfWidth = "\r\n\t 0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!\"#$%&()*+,-./:;<=>?@[\\]^_`{|}~";
    private static final String fullWidth = "\r\n\t　０１２３４５６７８９ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ！゛＃＄％＆（）＊＋、ー。／：；〈＝〉？＠［\\］＾＿‘｛｜｝～";

    public static String convert(String source) {
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

}
