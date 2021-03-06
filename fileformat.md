# Format of sudoku file.
## Types: 
    * number, number (-1, [1-9])
    * locked, boolean number ([0,1]), 0 == false.

## File format: 
<number> <locked>,..., <number> <locked>,[9 times total]
...[9 times total]
<number> <locked>,..., <number> <locked>,[9 times total]

## Example (with extra spaces to show locked and numbers): 
 5  1,  3  1,  4  0, -1  0,  7  1, -1  0, -1  0, -1  0, -1  0,
 6  1, -1  0, -1  0,  1  1,  9  1,  5  1, -1  0, -1  0, -1  0,
-1  0,  9  1,  8  1, -1  0, -1  0, -1  0, -1  0,  6  1, -1  0,
 8  1, -1  0, -1  0, -1  0,  6  1, -1  0, -1  0, -1  0,  3  1,
 4  1, -1  0, -1  0,  8  1, -1  0,  3  1, -1  0, -1  0,  1  1,
 7  1, -1  0, -1  0, -1  0,  2  1, -1  0, -1  0, -1  0,  6  1,
-1  0,  6  1, -1  0, -1  0, -1  0, -1  0,  2  1,  8  1, -1  0,
-1  0, -1  0, -1  0,  4  1,  1  1,  9  1, -1  0, -1  0,  5  1,
-1  0, -1  0, -1  0, -1  0,  8  1, -1  0, -1  0,  7  1,  9  1,

### Real example of file.
5 1, 3 1, 4 0, -1 0, 7 1, -1 0, -1 0, -1 0, -1 0,
6 1, -1 0, -1 0, 1 1, 9 1, 5 1, -1 0, -1 0, -1 0,
-1 0, 9 1, 8 1, -1 0, -1 0, -1 0, -1 0, 6 1, -1 0,
8 1, -1 0, -1 0, -1 0, 6 1, -1 0, -1 0, -1 0, 3 1,
4 1, -1 0, -1 0, 8 1, -1 0, 3 1, -1 0, -1 0, 1 1,
7 1, -1 0, -1 0, -1 0, 2 1, -1 0, -1 0, -1 0, 6 1,
-1 0, 6 1, -1 0, -1 0, -1 0, -1 0, 2 1, 8 1, -1 0,
-1 0, -1 0, -1 0, 4 1, 1 1, 9 1, -1 0, -1 0, 5 1,
-1 0, -1 0, -1 0, -1 0, 8 1, -1 0, -1 0, 7 1, 9 1,
