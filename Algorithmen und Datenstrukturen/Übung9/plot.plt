#!/usr/bin/gnuplot -c 

# Output W3C Scalable Vector Graphics
set terminal jpeg
set output "results.jpeg"
# Read comma-delimited data from file
set datafile separator comma

# Set graph title
set title 'Sorting algorithms comparison'

# Set label of x-axis
set xlabel 'n'

# Set label of y-axis
set ylabel 'Avg. CPU time (ns)'

# Use a line graph
set style data line

# Plot data from a file, with extra notes below:
#
# for [i=2:5]         Loop for values of i between 2 and 5 (inclusive)
# using i:xtic(1)     Plot column i using tick labels from column 1
# title columnheader  Use the column headers (first row) as titles
# linewidth 3         Use a wider line width
#
plot for [i=2:5] "results.dat" using i:xtic(1) smooth bezier title columnheader linewidth 3
