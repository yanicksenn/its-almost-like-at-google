import sys
from python.com.yanicksenn.flags import flags
from python.com.yanicksenn.templating.templating_engine import TemplatePreconditionException
from python.com.yanicksenn.templating.templating_engine import run

def main():
    flags.parse(sys.argv)

    # TODO - yanicksenn: Allow templating over multiple files in a single execution.
    template_path = flags.get("template_path")
    if template_path is None: 
        raise AssertionError("template must be set")

    target_path = flags.get("target_path")
    if target_path is None: 
        raise AssertionError("target must be set")
    
    rules_path = flags.get("rules_path")
    if rules_path is None: 
        rules_path = template_path + ".rules"
    
    # TODO - anyone: Ensure --interactive=true also enables the interactive mode.
    interactive = flags.is_set("interactive") and not flags.has_value("interactive")

    known_flag_keys = ["template_path", "target_path", "interactive"]
    custom_flags = {k: v for k, v in flags.all_flags().items() if k not in known_flag_keys } if not interactive else None
    
    try:
        run(template_path, rules_path, target_path, interactive, custom_flags)
    except TemplatePreconditionException as err:
        print(f"ERROR: {str(err)}")
        sys.exit(1)

if __name__ == '__main__':
    main()