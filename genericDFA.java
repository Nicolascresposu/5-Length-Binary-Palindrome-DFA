import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
public class genericDFA {
    private Set<String> states;
    private Set<Character> alphabet;
    private Map<String, Map<Character, String>> transitionTable;
    private String initialState;
    private Set<String> acceptingStates;
    private String currentState;

    private genericDFA(Set<String> states, Set<Character> alphabet,
                Map<String, Map<Character, String>> transitionTable,
                       String initialState, Set<String> acceptingStates) {
        this.states = states;
        this.alphabet = alphabet;
        this.transitionTable = transitionTable;
        this.initialState = initialState;
        this.acceptingStates = acceptingStates;
        this.currentState = initialState;
    }

    public void processInput(String input) {
        reset();
        for (char symbol : input.toCharArray()) {
            if (!alphabet.contains(symbol)) {
                throw new IllegalArgumentException("Symbol '" + symbol + "' not in alphabet");
            }
            currentState = transitionTable.get(currentState).get(symbol);
        }
    }

    public boolean isAccepting() {
        return acceptingStates.contains(currentState);
    }

    public void reset() {
        currentState = initialState;
    }

    // Builder pattern for easy DFA construction
    public static class Builder {
        private final Set<String> states = new HashSet<>();
        private final Set<Character> alphabet = new HashSet<>();
        private final Map<String, Map<Character, String>> transitionTable = new HashMap<>();
        private String initialState;
        private final Set<String> acceptingStates = new HashSet<>();

        public Builder addState(String state) {
            states.add(state);
            return this;
        }

        public Builder addSymbol(char symbol) {
            alphabet.add(symbol);
            return this;
        }

        public Builder setInitialState(String state) {
            initialState = state;
            return this;
        }

        public Builder addAcceptingState(String state) {
            acceptingStates.add(state);
            return this;
        }

        public Builder addTransition(String fromState, char symbol, String toState) {
            if (!transitionTable.containsKey(fromState)) {
                transitionTable.put(fromState, new HashMap<>());
            }
            transitionTable.get(fromState).put(symbol, toState);
            return this;
        }

        public genericDFA build() {
            if (initialState == null) {
                throw new IllegalStateException("Initial state not set");
            }
            return new genericDFA(states, alphabet, transitionTable, initialState, acceptingStates);
        }
    }

    // Example usage
    public static void main(String[] args) {
        // Build a DFA that accepts strings with even number of 'a's and at least one 'b'
        genericDFA dfa = new genericDFA.Builder()
                .addState("inicio")
                .addState("fin")
                .addState("invalid")
                .addState("1")
                .addState("0")

                .addSymbol('1')
                .addSymbol('0')
                .addSymbol('.')

                .setInitialState("inicio")
                .addAcceptingState("fin")
                .addTransition("inicio",'0',"0")
                .addTransition("inicio",'1',"1")
                .addTransition("inicio",'.',"fin")

                    .addTransition("0",'0',"00") //Y
                    .addTransition("0",'1',"01") //Y
                    .addTransition("0",'.',"fin")

                    .addTransition("00",'0',"000") //Y
                    .addTransition("00",'1',"001") //Y
                    .addTransition("00",'.',"fin")

                        .addTransition("000",'0',"0000")
                        .addTransition("000",'1',"invalid") //No hay palindrome de 4 o 5 que se pueda armar con la forma "0001X"
                        .addTransition("000",'.',"fin")

                            .addTransition("0000",'0',"00000")
                            .addTransition("0000",'1',"invalid")
                            .addTransition("0000",'.',"fin")

                                .addTransition("00000",'0',"invalid")
                                .addTransition("00000",'1',"invalid")
                                .addTransition("00000",'.',"fin")

                        .addTransition("001",'0',"0010")
                        .addTransition("001",'1',"invalid")
                        .addTransition("001",'.',"invalid")

                            .addTransition("0010",'0',"00100")
                            .addTransition("0010",'1',"invalid")
                            .addTransition("0010",'.',"invalid")

                                .addTransition("00100",'0',"invalid")
                                .addTransition("00100",'1',"invalid")
                                .addTransition("00100",'.',"fin")

                    .addTransition("01",'0',"010") //Y
                    .addTransition("01",'1',"011") //Y
                    .addTransition("01",'.',"invalid")

                        .addTransition("010",'0',"0100") //Y
                        .addTransition("010",'1',"0101") //Y
                        .addTransition("010",'.',"fin")

                            .addTransition("0100",'0',"invalid")
                            .addTransition("0100",'1',"invalid")
                            .addTransition("0100",'.',"invalid")

                            .addTransition("0101",'0',"01010")
                            .addTransition("0101",'1',"invalid")
                            .addTransition("0101",'.',"invalid")

                                .addTransition("01010",'0',"invalid")
                                .addTransition("01010",'1',"invalid")
                                .addTransition("01010",'.',"fin")

                        .addTransition("011",'0',"0110") //Y
                        .addTransition("011",'1',"0111") //Y
                        .addTransition("011",'.',"invalid")

                            .addTransition("0110",'0',"invalid") //
                            .addTransition("0110",'1',"invalid") //
                            .addTransition("0110",'.',"fin")

                            .addTransition("0111",'0',"01110") //
                            .addTransition("0111",'1',"invalid")
                            .addTransition("0111",'.',"invalid")

                                .addTransition("01110",'0',"invalid") //
                                .addTransition("01110",'1',"invalid")
                                .addTransition("01110",'.',"fin")

                .addTransition("invalid",'0',"invalid")
                .addTransition("invalid",'1',"invalid")
                .addTransition("invalid",'.',"invalid")

                .addTransition("1",'0',"10") //Y
                .addTransition("1",'1',"11") //Y
                .addTransition("1",'.',"fin")

                    .addTransition("10",'0',"100") //Y
                    .addTransition("10",'1',"101")
                    .addTransition("10",'.',"invalid")

                        .addTransition("100",'0',"1000")
                        .addTransition("100",'1',"invalid")
                        .addTransition("100",'.',"invalid")

                            .addTransition("1000",'0',"invalid")
                            .addTransition("1000",'1',"10001")
                            .addTransition("1000",'.',"invalid")

                                .addTransition("10001",'0',"invalid")
                                .addTransition("10001",'1',"invalid")
                                .addTransition("10001",'.',"fin")

                        .addTransition("101",'0',"1010")
                        .addTransition("101",'1',"invalid")
                        .addTransition("101",'.',"fin")

                            .addTransition("1010",'0',"invalid")
                            .addTransition("1010",'1',"10101")
                            .addTransition("1010",'.',"invalid")

                                .addTransition("10101",'0',"invalid")
                                .addTransition("10101",'1',"invalid")
                                .addTransition("10101",'.',"fin")

                    .addTransition("11",'0',"110")
                    .addTransition("11",'1',"111")
                    .addTransition("11",'.',"fin")

                        .addTransition("110",'0',"invalid")
                        .addTransition("110",'1',"1101")
                        .addTransition("110",'.',"invalid")

                            .addTransition("1101",'0',"invalid")
                            .addTransition("1101",'1',"11011")
                            .addTransition("1101",'.',"invalid")

                                .addTransition("11011",'0',"invalid")
                                .addTransition("11011",'1',"invalid")
                                .addTransition("11011",'.',"fin")

                        .addTransition("111",'0',"invalid")
                        .addTransition("111",'1',"1111")
                        .addTransition("111",'.',"fin")

                            .addTransition("1111",'0',"invalid")
                            .addTransition("1111",'1',"11111")
                            .addTransition("1111",'.',"fin")

                                .addTransition("11111",'0',"invalid")
                                .addTransition("11111",'1',"invalid")
                                .addTransition("11111",'.',"fin")

                .build();
//                .addTransition(0, 'a', 1)
//                .addTransition(0, 'b', 2)
//                .addTransition(1, 'a', 0)
//                .addTransition(1, 'b', 3)
//                .addTransition(2, 'a', 3)
//                .addTransition(2, 'b', 2)
//                .addTransition(3, 'a', 2)
//                .addTransition(3, 'b', 3)

//        String[] testStrings = {"0.", "01.", "010.", "000.", "0011.", "00100."};
        String[] testStrings = {
                // 1-digito
                "0.", "1.",

                // 2-digito
                "00.", "01.", "10.", "11.",

                // 3-digito
                "000.", "001.", "010.", "011.",
                "100.", "101.", "110.", "111.",

                // 4-digito
                "0000.", "0001.", "0010.", "0011.",
                "0100.", "0101.", "0110.", "0111.",
                "1000.", "1001.", "1010.", "1011.",
                "1100.", "1101.", "1110.", "1111.",

                // 5-digito
                "00000.", "00001.", "00010.", "00011.",
                "00100.", "00101.", "00110.", "00111.",
                "01000.", "01001.", "01010.", "01011.",
                "01100.", "01101.", "01110.", "01111.",
                "10000.", "10001.", "10010.", "10011.",
                "10100.", "10101.", "10110.", "10111.",
                "11000.", "11001.", "11010.", "11011.",
                "11100.", "11101.", "11110.", "11111.",

                // Unos ejemplos
                "0.", "01.", "010.", "000.", "0011.", "00100."
        };
        for (String s : testStrings) {
            dfa.processInput(s);
            System.out.println("String \"" + s + "\" accepted: " + dfa.isAccepting());
        }
    }
}
//No olvides colaborar al repositorio