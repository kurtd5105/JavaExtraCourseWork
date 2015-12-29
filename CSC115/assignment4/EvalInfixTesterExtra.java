/**
* @author Kurt Dorflinger
*/
public class EvalInfixTesterExtra {
	public static void main(String[] args) {
		EvalInfix test = new EvalInfix();
		//tests[][] stores test cases, each containing an expression and the expected result
		String[][] tests = {
			{"10", "10"},
			{"10+2", "12.0"},
			{"10-2", "8.0"},
			{"2-10", "-8.0"},
			{"5*7", "35.0"},
			{"10/2", "5.0"},
			{"10/3", "3.3333333"},
			{"100+2*300", "700.0"},
			{"((81 *5) + (33 /2)) * (5 - 1)", "1686.0"},
			{"(33 * 33 * 33) + (2 * 55 * 55) - 77", "41910.0"},
			{"((2 - 10) * (3 - 6))", "24.0"},
			{"((((((5))))))", "5"},
			{"10 + 2 -", "<syntax error>"},
			{"31 * 5 / + 10", "<syntax error>"},
			{"(2 - 10) * (3 - 6))", "<unbalanced ()>"},
			{"((2 - 10) * (3 - 6)", "<unbalanced ()>"},
			{"a + b", "<nonFloat>"},
			{"3.1415926 * 12 * 12", "452.3893"},
			{"89 / 0", "<divide by zero>"},
			{"(10 - b))", "<unbalanced ()>"}, 
			{"2^5", "32.0"}, 
			{"2^(-1)", "0.5"}, 
			{"2^-1", "0.5"}, 
			{"-8^2", "64.0"}, 
			{"-4^-2", "0.0625"}, 
			{"-8^(-1/3)", "<fractional root of negative number>"}
		};
		//For each test case in tests we run testEvalInfix and compare to the expected result
		for(String[] testCase : tests) {
			System.out.println("Testing expression: " + testCase[0]);
			if(EvalInfixExtra.testEvalInfix(testCase[0]).equals(testCase[1])) {
				System.out.println("TEST PASSED.");
			} else {
				System.out.println("TEST FAILED.");
			}
		}		
	}
}