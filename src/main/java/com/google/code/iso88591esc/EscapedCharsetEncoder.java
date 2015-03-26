package com.google.code.iso88591esc;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;

public class EscapedCharsetEncoder extends CharsetEncoder {

	private final boolean upperCase;

	protected EscapedCharsetEncoder(Charset cs, boolean upperCase) {
		super(cs, 1, 6);
		this.upperCase = upperCase;
	}

	@Override
	protected CoderResult encodeLoop(CharBuffer in, ByteBuffer out) {

		while(in.remaining() > 0 && out.remaining() > 0) {
			char c = in.get(in.position());
			if(c >= 0 && c <= 127) {
				out.put((byte) in.get());
				continue;
			}

			if(out.remaining() < 6) {
				return CoderResult.OVERFLOW;
			}
			int codepoint;
			if(Character.isHighSurrogate(c)) {
				if(in.remaining() < 2) {
					return CoderResult.UNDERFLOW;
				}
				if(Character.isLowSurrogate(in.get(in.position() + 1))) {
					char c1 = in.get();
					char c2 = in.get();

					codepoint = Character.toCodePoint(c1, c2);
				} else {
					codepoint = in.get();
				}
			} else {
				codepoint = in.get();
			}

			out.put((byte) '\\');
			out.put((byte) 'u');
			out.put(digitFor(codepoint >>> 12));
			out.put(digitFor(codepoint >>>  8));
			out.put(digitFor(codepoint >>>  4));
			out.put(digitFor(codepoint));
		}
		return in.remaining() == 0 ? CoderResult.UNDERFLOW : CoderResult.OVERFLOW;
	}

	private byte digitFor(int codepointShifted) {
		final char c = Character.forDigit(codepointShifted & 0xF, 16);
		return (byte) (upperCase ? Character.toUpperCase(c) : c);
	}
}
