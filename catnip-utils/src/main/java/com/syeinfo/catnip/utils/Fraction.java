package com.syeinfo.catnip.utils;

public class Fraction {

    // 分子
    private int numerator;

    // 分母
    private int denominator;


    public Fraction(int numerator, int denominator) {

        // 分母为零
        if (denominator == 0) {
            throw new RuntimeException("denominator is not permission 'zero'");
        }

        this.numerator = numerator;
        this.denominator = denominator;

        reduction();

    }

    /**
     * 约分
     */
    private void reduction() {
        int gcdi = gcdi(numerator, denominator);
        numerator = numerator / gcdi;
        denominator = denominator / gcdi;
    }

    /**
     * 分数相加
     * @param fraction
     */
    public void addition(Fraction fraction) {

        numerator = fraction.denominator * numerator + fraction.numerator * denominator;
        denominator *= fraction.denominator;

        reduction();

    }

    /**
     * 分数相减
     * @param fraction
     */
    public void subtraction(Fraction fraction) {

        numerator = fraction.denominator * numerator - fraction.numerator * denominator;
        denominator *= fraction.denominator;

        reduction();

    }

    /**
     * 分数相乘
     * @param fraction
     */
    public void multiplication(Fraction fraction) {

        denominator *= fraction.denominator;
        numerator *= fraction.numerator;

        reduction();

    }

    /**
     * 分数相除
     * @param fraction
     */
    public void division(Fraction fraction) {

        denominator *= fraction.numerator;
        numerator *= fraction.denominator;

        reduction();

    }

    public int getNumerator() {
        return numerator;
    }

    public int getDenominator() {
        return denominator;
    }

    @Override
    public String toString() {

        if (denominator == 1) {
            return Integer.toString(numerator);
        }

        return numerator + "/" + denominator;

    }

    /**
     * 最大公约数
     */
    private static int gcdi(int a, int b) {

        while (b > 0) {
            int c = a;
            a = b;
            b = c % b;
        }

        return a;

    }

}
