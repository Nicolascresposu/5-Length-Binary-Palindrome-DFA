[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT) <br>
# Binary Palindrome DFA (5-Length)
A Java implementation of a Deterministic Finite Automaton (DFA) that recognizes binary palindromes of length 1-5

-------------

## Overview

This project implements a DFA that specifically recognizes 1 through 5-digit binary palindromes (strings that read the same forwards and backwards). While DFAs cannot recognize arbitrary-length palindromes (which require a pushdown automaton), they can recognize fixed-length palindromes with a carefully designed state machine.

![DFA Visualization](https://raw.githubusercontent.com/Nicolascresposu/Nicolascresposu.github.io/refs/heads/main/other_repos_images/5lengthbinary/newDFAillust.png)

## Features

- Validates binary strings of lengths 1-5
- Specialized state machine for 5-digit palindrome recognition
- Builder pattern for clean DFA construction
- Comprehensive test cases for all binary permutations
- Dot-terminated string support (e.g., "01010.")

## DFA Design

### State Naming Convention
States follow a pattern that tracks the progress of palindrome validation:
- `"inicio"`: Initial state
- `"0"`, `"1"`: After first symbol
- `"00"`, `"01"`, `"10"`, `"11"`: After two symbols
- `"000"`, `"010"`, etc.: Tracking palindrome progress
- `"fin"`: Accepting state
- `"invalid"`: Rejecting state

### Alphabet
- `0`, `1`, `.` (terminator symbol)

### Accepted Palindromes
| Length | Valid Patterns |
|--------|----------------|
| 1      | "0.", "1." |
| 2      | "00.", "11." |
| 3      | "000.", "010.", "101.", "111." |
| 4      | "0110.", "1001." |
| 5      | "00000.", "00100.", "01010.", "01110.", "10001.", "10101.", "11011.", "11111." |

## Implementation

The DFA uses:
- States represented as strings tracking symbol history
- Nested HashMaps for transition table
- Builder pattern for declarative construction

```java
// Example transition definition
.addTransition("010",'0',"0100")  // Here you can continue building palindromes, we use these as the states, so it's a simpler naming convention.
.addTransition("010",'1',"0101")
.addTransition("010",'.',"fin")   // Accept if terminating here
```
