package co.q64.jstx;

import java.util.Arrays;
import java.util.List;

public class Test {
	public static final List<String> PROGRAM = Arrays.asList(
			// @formatter:off
			"load 1",
			"load 10",
			"list.range",
			"iterate stack",
			"load 11",
			"list.range",
			"iterate stack",
			"println",
			"end",
			"end",
			"string.format",
			"terminate"
			// @formatter:on
			);
	
	public static final List<String> STRING_COST = Arrays.asList(
			// @formatter:off
			"iterate",
			"ldr a",
			"ldr o",
			"list.get",
			"load 1",
			"+",
			"ldr a",
			"swp",
			"ldr o",
			"list.set",
			"sdr a",
			"end",
			"clr",
			"ldr a",
			"iterate stack",
			"math.triangular",
			"+",
			"end"
			// @formatter:on
			);
	public static final List<String> PROGRAM3 = Arrays.asList(
	// @formatter:off
		"load 1",
		"load 100",
		"list.range",
		"iterate",	
		"load 0",
		"sdr a",
		"ldr o",
		"load 3",
		"%",
		"load 0",
		"if =",
		"load Fizz",
		"load 1",
		"sdr a",
		"endif",
		"ldr o",
		"load 5",
		"%",
		"load 0",
		"if =",
		"load Buzz",
		"load 1",
		"sdr a",
		"endif",
		"ldr a",
		"load 0",
		"if =",
		"ldr o",
		"endif",
		"println",
		"end"
	// @formatter:on
	);

	public static final List<String> PROGRAM1 = Arrays.asList(
	// @formatter:off
	"load 1",
	"load 9",
	"list.range",
	"iterate",	
	"load 0",
	"sdr a",
	"ldr o",
	"load 3",
	"%",
	"load 0",
	"if =",
	"load Fizz",
	"load 1",
	"sdr a",
	"endif",
	"ldr o",
	"load 5",
	"%",
	"load 0",
	"if =",
	"load Buzz",
	"load 1",
	"sdr a",
	"endif",
	"ldr a",
	"load 0",
	"if =",
	"ldr o",
	"endif",
	"println",
	"end"
	// @formatter:on
	);

	public static final String[] ARGS = { "aaab" };
}