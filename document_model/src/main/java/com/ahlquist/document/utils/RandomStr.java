package com.ahlquist.document.utils;

import java.util.*;

public class RandomStr {

	public static String generateUUID(final int length) {
		RandomStr randStr = new RandomStr();
		return randStr.get(length);
	}

	private final char[] alphanumeric = alphanumeric();
	private final Random rand;

	public RandomStr() {
		this(null);
	}

	public RandomStr(final Random rand) {
		this.rand = (rand != null) ? rand : new Random();
	}

	public String get(final int len) {
		StringBuffer out = new StringBuffer();

		while (out.length() < len) {
			int idx = Math.abs((rand.nextInt() % alphanumeric.length));
			out.append(alphanumeric[idx]);
		}
		return out.toString();
	}

	// create alphanumeric char array
	// TODO(dahlquist) : optimize array construction
	private char[] alphanumeric() {
		StringBuffer buf = new StringBuffer(128);
		// 0-9
		for (int i = 48; i <= 57; i++)
			buf.append((char) i);
		// A-Z
		for (int i = 65; i <= 90; i++)
			buf.append((char) i);
		// a-z
		for (int i = 97; i <= 122; i++)
			buf.append((char) i);
		return buf.toString().toCharArray();
	}

	public static void main(String[] args) {
		RandomStr rand = new RandomStr();
		System.out.println(" 10 chars random string=" + rand.get(10));
		System.out.println(" 20 chars random string=" + rand.get(20));
		System.out.println("128 chars random string=" + rand.get(128));
	}
}