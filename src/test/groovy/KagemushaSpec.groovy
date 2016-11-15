package main.java

import spock.lang.Specification

/**
 * Created by gtorr3 on 1/30/15.
 */
class KagemushaSpec extends Specification {

    def "Kagemusha should convert half width character to full width"() {
        when: "Look up tables should be the same length"
            assert Kagemusha.fullWidth.length() == Kagemusha.halfWidth.length()

        then: "ASCII characters convert correctly"
            Kagemusha.convert('the quick brown fox jumped over the lazy dog') == 'ｔｈｅ　ｑｕｉｃｋ　ｂｒｏｗｎ　ｆｏｘ　ｊｕｍｐｅｄ　ｏｖｅｒ　ｔｈｅ　ｌａｚｙ　ｄｏｇ'
            Kagemusha.convert('THE QUICK BROWN FOR JUMPED OVER THE LAZY DOG') == 'ＴＨＥ　ＱＵＩＣＫ　ＢＲＯＷＮ　ＦＯＲ　ＪＵＭＰＥＤ　ＯＶＥＲ　ＴＨＥ　ＬＡＺＹ　ＤＯＧ'

        and: "Other white space is preserved"
            Kagemusha.convert('The\nQuick\nBrown\tFox') == 'Ｔｈｅ\nＱｕｉｃｋ\nＢｒｏｗｎ\tＦｏｘ'

        and: "Punctuation converts correctly"
            Kagemusha.convert('!@#$%^&*()_+-={}[]:";\'<>?/.,') == '！＠＃＄％＾＆＊（）＿＋ー＝｛｝［］：゛；？〈〉？／。、'

        and: "Unknown characters become full width question marks"
            Kagemusha.convert("\u00ee\u0eee\ueeee") == '？？？'
    }

}
