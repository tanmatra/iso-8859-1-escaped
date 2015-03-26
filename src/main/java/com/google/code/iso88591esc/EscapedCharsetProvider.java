package com.google.code.iso88591esc;

import java.nio.charset.Charset;
import java.nio.charset.spi.CharsetProvider;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class EscapedCharsetProvider extends CharsetProvider {

	private static final Map<String, Charset> CHARSETS = new HashMap<>();

	private static void add(Charset charset) {
		CHARSETS.put(charset.name(), charset);
		for (String alias : charset.aliases()) {
			CHARSETS.put(alias, charset);
		}
	}

	static {
		add(new EscapedCharset(EscapedCharset.NAME, false));
		add(new EscapedCharset(EscapedCharset.NAME_UPPERCASE, true));
	}

	@Override
	public Charset charsetForName(String charsetName) {
		return CHARSETS.get(charsetName);
	}

	@Override
	public Iterator<Charset> charsets() {
		return CHARSETS.values().iterator();
	}
}
