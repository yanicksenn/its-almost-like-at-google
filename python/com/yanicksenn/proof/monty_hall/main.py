import sys
from python.com.yanicksenn.flags import flags
from python.com.yanicksenn.proof.monty_hall.lib import run

def main():
    flags.parse(sys.argv)

    runs = flags.getAsInt("runs")
    min_runs = 1
    if runs is None or runs < min_runs: 
        runs = min_runs

    run(runs)

if __name__ == '__main__':
    main()