#!/usr/bin/env python

import random
import sys


chars = list(' abcdefghijklmnopqrstuvwxyz')

num_chars = int(sys.argv[1])
length = int(sys.argv[2])
file_name = sys.argv[3] if len(sys.argv) > 3 else "inputString.txt"

result = []
for i in range(int(length)):
    result.append(random.choice(chars[:num_chars]))

with open(file_name, "w") as fp:
    fp.write(''.join(result))
    fp.write('\n')
