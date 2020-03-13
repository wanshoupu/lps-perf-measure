#!/bin/sh
# Remove working files from last run.
#
USAGE="
# USAGE ############## USAGE ############## USAGE ############## USAGE ##############
# Clean up all the outputs of latex commands.
# Two scenarios: given a dir or given a file                                        #
# If a dir is given, all the outputs with certain extension will be cleaned out     #
# If a file name is given, all the files with certain extensions will be cleaned out#
# LIMITATIONS: the file names cannot contain space and must has extension names     #
#####################################################################################
"
clean(){
    input=$1
    if [ -d "$input" ]; then
        mainsrc="$input/*"
    elif [ -f "$input" ]; then
        mainsrc=${input%.*}
    else
        echo "Specify valid source file or directory: $input"
        exit 1
    fi

    echo "Cleaning java outputs '$mainsrc...'"
    rm -f $mainsrc.jar \
           $mainsrc.class   2>/dev/null
}

cleanall(){
  input=$1
  clean "$input"
  if [ -d "$input" ] && ! [ -z "$(ls -A "$input")" ]; then
    for f in $input/*/; do
      cleanall ${f%/}
    done
  fi
}

if [ $# -gt 0 ]; then
  input="${BASH_ARGV[0]}"
  if [ ${input: -1} == / ]; then
    input=${input%?}
  fi
else
  input='.'
fi
cleanall "$input"
