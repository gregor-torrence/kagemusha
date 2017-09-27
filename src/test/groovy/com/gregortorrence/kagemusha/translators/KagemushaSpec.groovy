package com.gregortorrence.kagemusha.translators

import spock.lang.Specification
import spock.lang.Unroll

import java.lang.reflect.Modifier

class KagemushaSpec extends Specification {

    def 'should only have private constructor'() {
        when:
        def constructors = new Kagemusha().class.getDeclaredConstructors()

        then:
        constructors.length == 1
        constructors[0].getModifiers() == Modifier.PRIVATE
    }

    def 'translateText should convert half width character to full width'() {
        expect:
        Kagemusha.fullWidth.length() == Kagemusha.halfWidth.length()

        Kagemusha.translateText('the quick brown fox jumped over the lazy dog') == 'ｔｈｅ　ｑｕｉｃｋ　ｂｒｏｗｎ　ｆｏｘ　ｊｕｍｐｅｄ　ｏｖｅｒ　ｔｈｅ　ｌａｚｙ　ｄｏｇ'
        Kagemusha.translateText('THE QUICK BROWN FOR JUMPED OVER THE LAZY DOG') == 'ＴＨＥ　ＱＵＩＣＫ　ＢＲＯＷＮ　ＦＯＲ　ＪＵＭＰＥＤ　ＯＶＥＲ　ＴＨＥ　ＬＡＺＹ　ＤＯＧ'

        Kagemusha.translateText('The\nQuick\rBrown\tFox') == 'Ｔｈｅ\nＱｕｉｃｋ\rＢｒｏｗｎ\tＦｏｘ'

        Kagemusha.translateText('!@#$%^&*()_+-={}[]:";\'<>?/.,') == '！＠＃＄％＾＆＊（）＿＋ー＝｛｝［］：゛；＇〈〉？／。、'

        Kagemusha.translateText('\u00ee\u0eee\ueeee') == '？？？'
    }

    def 'translateFormat should preserve format arguments'() {
        expect:
        Kagemusha.translateFormat('{0}') == '{0}'
        Kagemusha.translateFormat('the {0} brown fox') == 'ｔｈｅ　{0}　ｂｒｏｗｎ　ｆｏｘ'
        Kagemusha.translateFormat('the {0} brown {1}') == 'ｔｈｅ　{0}　ｂｒｏｗｎ　{1}'
        Kagemusha.translateFormat('{the} quick {brown} fox') == '{the}　ｑｕｉｃｋ　{brown}　ｆｏｘ'
        Kagemusha.translateFormat('the {quick} brown {fox}') == 'ｔｈｅ　{quick}　ｂｒｏｗｎ　{fox}'
        Kagemusha.translateFormat('{the} {quick} {brown} {fox}') == '{the}　{quick}　{brown}　{fox}'
    }

    def 'translateFormat should preserve substitution arguments'() {
        expect:
        Kagemusha.translateSubstitutions('${the} quick ${brown} fox') == '${the}　ｑｕｉｃｋ　${brown}　ｆｏｘ'
        Kagemusha.translateSubstitutions('the ${quick} brown ${fox}') == 'ｔｈｅ　${quick}　ｂｒｏｗｎ　${fox}'
        Kagemusha.translateSubstitutions('${the} ${quick} ${brown} ${fox}') == '${the}　${quick}　${brown}　${fox}'
    }

    def 'translateHtmlSnippet should convert HTML text while preserving mark up'() {
        when:
        def html1 = '<span>the lazy dog</span>'
        def translated1 = '<span>ｔｈｅ　ｌａｚｙ　ｄｏｇ</span>'

        and:
        def html2 = '<a href="http://www.thelazydog.com">click here</a> to go to the lazy dog'
        def translated2 = '<a href="http://www.thelazydog.com">ｃｌｉｃｋ　ｈｅｒｅ</a>　ｔｏ　ｇｏ　ｔｏ　ｔｈｅ　ｌａｚｙ　ｄｏｇ'

        and:
        def html3 = 'You should <a href="http://www.google.com/">click here</a> to find stuff.'
        def translated3 = 'Ｙｏｕ　ｓｈｏｕｌｄ　<a href="http://www.google.com/">ｃｌｉｃｋ　ｈｅｒｅ</a>　ｔｏ　ｆｉｎｄ　ｓｔｕｆｆ。'

        and:
        def html4 = 'the quick brown fox jumped over the lazy dog'
        def translated4 = 'ｔｈｅ　ｑｕｉｃｋ　ｂｒｏｗｎ　ｆｏｘ　ｊｕｍｐｅｄ　ｏｖｅｒ　ｔｈｅ　ｌａｚｙ　ｄｏｇ'

        then:
        Kagemusha.translateHtmlSnippet(html1) == translated1
        Kagemusha.translateHtmlSnippet(html2) == translated2
        Kagemusha.translateHtmlSnippet(html3) == translated3
        Kagemusha.translateHtmlSnippet(html4) == translated4
    }

    def 'translate should determine string format and translate accordingly'() {
        expect:
        Kagemusha.translate('the quick brown fox jumped over the lazy dog') == 'ｔｈｅ　ｑｕｉｃｋ　ｂｒｏｗｎ　ｆｏｘ　ｊｕｍｐｅｄ　ｏｖｅｒ　ｔｈｅ　ｌａｚｙ　ｄｏｇ'
        Kagemusha.translate('{0}') == '{0}'
        Kagemusha.translate('the {0} brown fox') == 'ｔｈｅ　{0}　ｂｒｏｗｎ　ｆｏｘ'
        Kagemusha.translate('the {0} brown {1}') == 'ｔｈｅ　{0}　ｂｒｏｗｎ　{1}'
        Kagemusha.translate('{the} quick {brown} fox') == '{the}　ｑｕｉｃｋ　{brown}　ｆｏｘ'
        Kagemusha.translate('the {quick} brown {fox}') == 'ｔｈｅ　{quick}　ｂｒｏｗｎ　{fox}'
        Kagemusha.translate('{the} {quick} {brown} {fox}') == '{the}　{quick}　{brown}　{fox}'
        Kagemusha.translate('${the} quick ${brown} fox') == '${the}　ｑｕｉｃｋ　${brown}　ｆｏｘ'
        Kagemusha.translate('the ${quick} brown ${fox}') == 'ｔｈｅ　${quick}　ｂｒｏｗｎ　${fox}'
        Kagemusha.translate('${the} ${quick} ${brown} ${fox}') == '${the}　${quick}　${brown}　${fox}'
        Kagemusha.translate('<span>the lazy dog</span>') == '<span>ｔｈｅ　ｌａｚｙ　ｄｏｇ</span>'
        Kagemusha.translate('<a href="http://www.thelazydog.com">click here</a> to go to the lazy dog') == '<a href="http://www.thelazydog.com">ｃｌｉｃｋ　ｈｅｒｅ</a>　ｔｏ　ｇｏ　ｔｏ　ｔｈｅ　ｌａｚｙ　ｄｏｇ'
        Kagemusha.translate('http://www.whatever.com') == 'http://www.whatever.com'
        Kagemusha.translate(' https://www.whatever.com') == ' https://www.whatever.com'
        Kagemusha.translate('HTTP://WWW.WHATEVER.COM') == 'HTTP://WWW.WHATEVER.COM'
        Kagemusha.translate(' HTTPS://WWW.WHATEVER.COM') == ' HTTPS://WWW.WHATEVER.COM'
    }

    @Unroll
    def 'should guess string format of #string correctly'() {
        expect:
        Kagemusha.isUrl(string) == isUrl
        Kagemusha.containsHtmlTags(string) == isHtml
        Kagemusha.containsFormatArguments(string) == isFormat
        Kagemusha.containsSubstitutions(string) == isSub

        // Five obvious cases, and Five unrealistic ones
        where:
        isUrl | isHtml | isFormat | isSub    | string

        false | false  | false    | false    | 'Hello, World!'
        true  | false  | false    | false    | 'http://hello-world.com'
        false | true   | false    | false    | '<span>Hello, World!</span>'
        false | false  | true     | false    | 'Hello, {0}!'
        false | false  | true     | true     | 'Hello, ${foo}!'

        false | true   | true     | false    | '<span>Hello, {0}!</span>'
        true  | false  | true     | false    | 'http://hello-{0}.com'
        true  | true   | false    | false    | 'http://hello-world.<span>com</span>'
        true  | true   | true     | false    | 'http://<div>Hello-{0}.com</div>'
        true  | true   | true     | true     | 'http://<div>{${foo}-{0}.com</div>'
    }

}
