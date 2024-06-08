import sys
from python.com.yanicksenn.flags import flags
from python.com.yanicksenn.templating import templating_engine

def main():
    flags.parse(sys.argv)

    template_path = flags.get("template_path")
    if template_path is None: 
        raise AssertionError("template must be set")

    target_path = flags.get("target_path")
    if target_path is None: 
        raise AssertionError("target must be set")
    
    templating_engine.run(template_path, target_path)

if __name__ == '__main__':
    main()