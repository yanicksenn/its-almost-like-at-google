import sys
from python.com.yanicksenn.flags import flags
from python.com.yanicksenn.proof.gamblers_fallacy.lib import run

def main():
    flags.parse(sys.argv)

    runs = flags.getAsInt("runs")
    min_runs = 3
    if runs is None or runs < min_runs: 
        print(f"WARNING: Runs was less than {min_runs}. Using {min_runs} instead.")
        runs = min_runs

    run(runs)

if __name__ == '__main__':
    main()