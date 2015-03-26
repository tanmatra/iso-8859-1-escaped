package com.google.code.iso88591esc;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

import org.junit.Assert;
import org.junit.Test;

public class TestCharset {

	private final static String NATIVE = "test=\u0422\u0435\u0441\u0442\u043e\u0432\u0430\u044f " +
		"\u0441\u0442\u0440\u043e\u043a\u0430\u2122!";

	private final static String ENCODED = "test=\\u0422\\u0435\\u0441\\u0442\\u043e\\u0432\\u0430\\u044f " +
		"\\u0441\\u0442\\u0440\\u043e\\u043a\\u0430\\u2122!";

	private final static String ENCODED_UPPERCASE = "test=\\u0422\\u0435\\u0441\\u0442\\u043E\\u0432\\u0430\\u044F " +
		"\\u0441\\u0442\\u0440\\u043E\\u043A\\u0430\\u2122!";

	@Test
	public void testDecode() throws Exception {

		Charset charset = Charset.forName("ISO-8859-1-ESCAPED");

		byte[] buf = ENCODED.getBytes();
		CharBuffer cbuf = charset.decode(ByteBuffer.wrap(buf));

		Assert.assertEquals(NATIVE, cbuf.toString());
	}

	@Test
	public void testDecodeFromUppercase() throws Exception {

		Charset charset = Charset.forName("ISO-8859-1-ESCAPED");

		byte[] buf = ENCODED_UPPERCASE.getBytes();
		CharBuffer cbuf = charset.decode(ByteBuffer.wrap(buf));

		Assert.assertEquals(NATIVE, cbuf.toString());
	}

	@Test
	public void testUppercaseDecode() throws Exception {

		Charset charset = Charset.forName("ISO-8859-1-ESCAPED-UPPERCASE");

		byte[] buf = ENCODED.getBytes();
		CharBuffer cbuf = charset.decode(ByteBuffer.wrap(buf));

		Assert.assertEquals(NATIVE, cbuf.toString());
	}

	@Test
	public void testUppsercaseDecodeFromUppercase() throws Exception {

		Charset charset = Charset.forName("ISO-8859-1-ESCAPED-UPPERCASE");

		byte[] buf = ENCODED_UPPERCASE.getBytes();
		CharBuffer cbuf = charset.decode(ByteBuffer.wrap(buf));

		Assert.assertEquals(NATIVE, cbuf.toString());
	}

	@Test
	public void testEncode() throws Exception {

		Charset charset = Charset.forName("ISO-8859-1-ESCAPED");

		ByteBuffer bbuf = charset.encode(CharBuffer.wrap(NATIVE));
		byte[] expected = ENCODED.getBytes("ISO-8859-1");

		byte[] got = new byte[bbuf.remaining()];
		bbuf.get(got);

		Assert.assertArrayEquals(expected, got);
	}

	@Test
	public void testUppsercaseEncode() throws Exception {

		Charset charset = Charset.forName("ISO-8859-1-ESCAPED-UPPERCASE");

		ByteBuffer bbuf = charset.encode(CharBuffer.wrap(NATIVE));
		byte[] expected = ENCODED_UPPERCASE.getBytes("ISO-8859-1");

		byte[] got = new byte[bbuf.remaining()];
		bbuf.get(got);

		Assert.assertArrayEquals(expected, got);
	}
}
