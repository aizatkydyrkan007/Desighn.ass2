
---

# Max-Heap with Increase-Key and Merge Operations

This repository contains a Java implementation of the **Max-Heap** data structure, developed as part of the "Design and Analysis of Algorithms" course.

The project focuses on a clean, tested, and performant implementation of the heap, including standard and extended functionalities.

## Authors

This project was developed by students of group **SE-2432**:

*   Yan Kudashov
*   Aizat Kydyrkan

## Features

*   **Max-Heap**: Core implementation based on a dynamic array.
*   **Standard Operations**: `insert`, `extractMax`, `peekMax`.
*   **Extended Operations**: `increaseKey` and `merge`.
*   **Performance Tracking**: Built-in metrics for analyzing operations.
*   **Command-Line Interface (CLI)**: For interactive testing and demonstration.

## How to Run

### Prerequisites
*   Java JDK 11 (or newer)
*   Apache Maven

### 1. Build the project
```bash
mvn clean install
```

### 2. Run the tests
```bash
mvn test
```

### 3. Run the interactive CLI
```bash
mvn exec:java -Dexec.mainClass="cli.BenchmarkRunner"
```

## Algorithm Analysis

For a detailed theoretical analysis of the algorithms, complexity, and implementation choices, please refer to the report located in the `docs` directory:

`docs/analysis-report.pdf`