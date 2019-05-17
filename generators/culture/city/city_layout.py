#!/usr/bin/env python
import sys
import math
import random
import logging
logging.basicConfig(format='%(message)s')

city_output_file = 'city_data.ps'

power = 6
block_ring = 6 * 2**power
powers = [2**i for i in range(power + 1)]
sector_blocks = 2**power


section_area = 3.14159
output_list = []


def choose_option(options):
    choice = None
    for option in options:
        if choice is None:
            choice = option
        else:
            if option[2] < choice[2]:
                choice = option
    return choice

multiplier = 3
def choose_option_rand(options, rand_power):
    # sys.stderr.write("new slots!\n")
    choices = []
    for option in options:
        slots = multiplier / option[2]**rand_power
        # sys.stderr.write("slots: %f\n" % slots)
        choices += [option] * int(slots)
    if len(choices) == 0:
        return choose_option(options)
    return random.choice(choices)


option_cache = {}
def score_sector(true_i, width, true_h, sector_blocks, wall_h = None):
    if (true_i, width, wall_h) not in option_cache:
        height = sector_blocks / width
        if true_i+height >= len(true_h):
            option_cache[(true_i, width, wall_h)] = False
        elif wall_h != 0 and true_i + height > wall_h:
            # logging.warning('past wall height of %d' % wall_h)
            option_cache[(true_i, width, wall_h)] = False
        else:
            # calculate real height
            true_height = true_h[true_i + height] - true_h[true_i]
            # calculate pseudowidth
            true_width = section_area/true_height
            aspect_ratio = true_width / true_height
            aspect_ratio = max(aspect_ratio, 1/aspect_ratio)
            score = aspect_ratio
            option_cache[(true_i, width, wall_h)] = [height, width, score]
    return option_cache[(true_i, width, wall_h)]

def add_sectors(wedge_count, ring_start, ring_end, rand_power, wall, large_chance):
    wedge_w = block_ring / wedge_count
    def heightify(i):
        return math.sqrt(1 + i * wedge_count)
    def antiheightify(h):
        return (h**2 - 1) / wedge_count
    wedge_h = ring_end - ring_start
    true_h = [heightify(i) for i in range(wedge_h * 2 )]
    if wall:
        wall_h = ring_end
    else:
        wall_h = 0

    for wedge_i in range(wedge_count):
        wedge_offset = wedge_i * wedge_w
        # allocate 2^n x radius grid
        block_grid = [[0 for j in range(wedge_w)] for i in range(wedge_h)]
        for i in range(wedge_h):
            true_i = i + ring_start
            for j in range(wedge_w):
                if block_grid[i][j] == 0:
                    # find options
                    width_options = []
                    k = 1
                    while j+k <= wedge_w:
                        if block_grid[i][j+k-1] != 0:
                            break
                        if k in powers:
                            width_options.append(k)
                        k += 1
                    # rate options
                    options = []
                    local_sector_blocks = sector_blocks
                    if random.random() < large_chance:
                        local_sector_blocks *= 4
                    for width in width_options:
                        option = score_sector(true_i, width, true_h, local_sector_blocks, wall_h)
                        if option:
                            options.append(option)
                    if len(options) > 0:
                        # choose option
                        choice = choose_option_rand(options, rand_power)
                        # output option
                        output_list.append([true_i, wedge_offset + j, choice[0], choice[1]])
                        # mark used points
                        for bi in range (min(choice[0], wedge_h-i)):
                            for bj in range (choice[1]):
                                block_grid[i+bi][j+bj] = 1

# wedge_h = antiheightify(ring_end)
add_sectors(6, 0, 3, 2, True, 0)
add_sectors(12, 3, 9, 1.1, True, 0.005)
add_sectors(12, 10, 33, 0.75, True, 0.01)
add_sectors(12, 34, 101, 0.75, False, 0.02)

with open(city_output_file, 'w') as f:
    f.write("/block_ring %f def\n" % (block_ring,))
    f.write("/city_data [\n")
    for entry in output_list:
        f.write("  [%d %d %d %d]\n" % (entry[0], entry[1], entry[2], entry[3]))
    f.write("] def\n")
