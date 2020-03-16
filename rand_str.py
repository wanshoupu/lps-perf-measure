#!/usr/bin/env python

import random
import sys


chars = list('abcdefghijklmnopqrstuvwxyz')
batch_size = 1000000

def rand_append(size):
    result = []
    for i in range((size+1)//2):
        result.append(random.choice(chars[:num_chars]))
    with open(file_name, "a") as fp:
        s = ''.join(result)
        rs = s[:0:-1] if size & 1 else s[::-1]
        fp.write(rs+s)
        fp.close()

if __name__ == '__main__':
    num_chars = int(sys.argv[1])
    length = int(sys.argv[2])
    file_name = sys.argv[3] if len(sys.argv) > 3 else "inputString.txt"

    open(file_name, "w").close()
    while length > batch_size:
        rand_append(batch_size)
        length -= batch_size
    else:
        rand_append(length)
        with open(file_name, "a") as fp:
            fp.write('\n')
            fp.close()
