PostOffice {
	classvar <lines, <>addLines = false;
	classvar <>postfunc;

	*initClass {
		lines = List[];
	}
	*add { |string|
		if (addLines) { lines.add(string) };
		postfunc.value(string);
	}
	*clear { lines.clear }
}
