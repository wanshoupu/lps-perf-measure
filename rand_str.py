#!/usr/bin/env python

import random
import sys


chars = list(' abcdefghijklmnopqrstuvwxyz')

print(chars)

num_chars = int(sys.argv[1])
length = int(sys.argv[2])

result = []
for i in range(int(length)):
    result.append(random.choice(chars[:num_chars]))

with open("inputString{}.txt".format(num_chars), "w") as fp:
    fp.write(''.join(result))
    fp.write('\n')
