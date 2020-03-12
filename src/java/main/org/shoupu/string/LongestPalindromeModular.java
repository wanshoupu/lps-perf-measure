package org.shoupu.string;

public class LongestPalindromeModular implements LongestPalindrome {

    private String input;
    // The i-th element in array 'lsp' records the max length of palindrome centered
    // on a char or between two adjacent chars of the input string depends on the parity of i:
    // if i is even, it is centered between the (i/2)th char and the (i/2 + 1)th char;
    // if i is odd, it is centered on the (i-1)/2th char in string input
    // pss[0] = 0 by definition
    private int[] pss;

    @Override
    public String longestPalindrome(String input) {
        this.input = input;
        pss = new int[input.length() * 2 + 1];
        solve();
        return substring(argmax());
    }

    /*
     * Solve the longest palindrome problem and cache the result
     */
    void solve() {
        for (int i = 1, refCenter = 0; i < pss.length; ++i) {
            // refCenter = center reaching to the right farthest
            if (getRightBound(refCenter) <= i) {
                pss[i] = palength(i, i);
                refCenter = i; // i becomes the rightmost palindrome
                continue;
            }
            int mi = toMirrorImage(refCenter, i);
            //assert mi >= 0
            if (getLeftBound(mi) > getLeftBound(refCenter)) {
                // palindrome is wrapped
                pss[i] = pss[mi];
            } else {
                //calculate the part outside
                pss[i] = palength(i, getRightBound(refCenter));
                refCenter = i;
            }
        }
    }

    /*
     * Find the index of maximum palindromic substring
     */
    int argmax() {
        int maxi = 0;
        for (int i = 0; i < pss.length; ++i) {
            if (pss[i] > pss[maxi]) {
                maxi = i;
            }
        }
        return maxi;
    }

    int getLeftBound(int i) {
        return i - pss[i];
    }

    int getRightBound(int i) {
        return i + pss[i];
    }

    String substring(int center) {
        int left = getLeftBound(center) / 2;
        int right = getRightBound(center) / 2;
        return input.substring(left, right);
    }

    /*
     * Find the palindrome in string input centered at center.
     */
    int palength(int center, int index) {
        for (int mi = toMirrorImage(center, index); ; ++index, --mi) {
            // if one of the conditions: reaching the left end, reaching the right end, or
            // encountered char mismatch
            if (mi < 0 || index >= pss.length || isMismatch(index, mi)) {
                return index - center - 1;
            }
        }
    }

    /*
     * Determine if there is a mismatch between virtual char at i and j
     * A mismatch happens if both indexes are even and the charAt not equal
     */
    boolean isMismatch(int i, int j) {
        return (i & 1) == 1 && input.charAt(i / 2) != input.charAt(j / 2);
    }

    int toMirrorImage(int axis, int x) {
        return 2 * axis - x;
    }
}
