package org.shoupu.string;

public class LongestPalindromeIndexMapping implements LongestPalindrome {
    @Override
    public String longestPalindrome(String input) {
        int[] pss = new int[input.length() * 2 + 1];
        for (int i = 1, refCenter = 0; i < pss.length; ++i) {
            // refCenter = center reaching to the right farthest
            if (refCenter + pss[refCenter] <= i) {
                pss[i] = palength(input, i, i);
                refCenter = i;
            } else {
                int im = refCenter * 2 - i;
                //assert im >= 0
                if (im - pss[im] > refCenter - pss[refCenter]) {
                            pss[i] = pss[im];
                } else {
                    pss[i] = palength(input, i, refCenter + pss[refCenter]);
                    refCenter = i;
                }
            }
        }

        for (int i = 0, refCenter = 0; ; ++i) {
            if (i == pss.length) {
                return substring(input, refCenter, pss[refCenter]);
            }
            if (pss[i] > pss[refCenter]) {
                refCenter = i;
            }
        }
    }

    String substring(String str, int c, int len) {
        //if c is even, len can only be even
        //if c is odd, len can only be odd
        int s = (c - len) / 2;
        int e = (c + len) / 2;
        return str.substring(s, e);
    }

    int palength(String s, int c, int i) {
        final int n = 2 * s.length() + 1;
        for (int mi = 2 * c - i; ; ++i, --mi) {
            if (mi < 0 || i >= n || (i & 1) == 1 && s.charAt(i / 2) != s.charAt(mi / 2)) {
                return i - c - 1;
            }
        }
    }

}
