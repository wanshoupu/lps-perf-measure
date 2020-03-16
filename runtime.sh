#!/bin/bash

# Variables for setting timeout on test calls.
timeoutOccuredExitCode=124
timeoutLength=100

# Construct the output file path.
mkdir -p outputs
fileName=outputs/result
currentTime=$(date "+%s")
outputFileName=$fileName-$currentTime.csv
inputFileName='tmpInput.txt'

# Run the script with timeout.
function runScript {
    times=$(2>&1 time java -Xmx12g -jar src/java/main/lps.jar $1 $2 $3)
    echo $times|cut -d ' ' -f 1,2,4,6|sed -e 's/ /,/g'
}

# Number of elements and algorithms arrays.
uniqChars=(5) #
elementCount=(100000 1000000 10000000 100000000) #
algorithms=(0 1 2) # 

# Loop over the elements and algorithms and run the tests for every combination.

echo "uniqChars,length,algorithm,plen,real,user,sys" | tee -a $outputFileName
for uc in "${uniqChars[@]}"
do
    for numberOfElements in "${elementCount[@]}"
    do
        ./rand_str.py $uc $numberOfElements 'tmpInput.txt'
        for algorithm in "${algorithms[@]}"
        do
            for count in {1..3}
            do
                times=$(runScript $algorithm 'tmpInput.txt')
                echo $uc,$numberOfElements,$algorithm,$times | tee -a $outputFileName
            done
        done
    done
done
echo "runtime data is stored in $outputFileName"
