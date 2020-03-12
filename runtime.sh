#!/bin/bash

# Variables for setting timeout on test calls.
timeoutOccuredExitCode=124
timeoutLength=100

# Construct the output file path.
mkdir -p outputs
fileName=outputs/result
currentTime=$(date "+%s")
outputFileName=$fileName-$currentTime.csv

# Run the script with timeout.
function runScript {

    timeout $timeoutLength ./sorting_benchmarks.out $1 $2 data/output.txt $3 -v
    if [ $? -eq $timeoutOccuredExitCode ]
    then
        echo $3,$1,"TIMEOUT"
    fi
}

# Number of elements and algorithms arrays.
files=(data/asc_input.txt data/desc_input.txt data/rand_input.txt)
elementCount=(10 100 1000 10000, 50000, 100000, 1000000)
algorithms=(nqs qs)

# Loop over the elements and algorithms and run the tests for every combination.

echo "data-source,data-size,algorithm,runtime" | tee -a $outputFileName
for fileName in "${files[@]}"
do
    for numberOfElements in "${elementCount[@]}"
    do
        for algorithm in "${algorithms[@]}"
        do
            for count in {1..3}
            do
                runScript $numberOfElements $fileName $algorithm | tee -a $outputFileName
            done
        done
    done
done
