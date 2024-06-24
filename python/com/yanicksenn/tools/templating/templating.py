import sys
from python.com.yanicksenn.libraries.flags import flags
from python.com.yanicksenn.libraries.logging import logging
from python.com.yanicksenn.libraries.logging.logging import LoggingLevel
from python.com.yanicksenn.tools.templating.templating_engine import TemplatePreconditionException
from python.com.yanicksenn.tools.templating.templating_engine import TemplateRequest
from python.com.yanicksenn.tools.templating.templating_engine import run

def main():
    flags.parse(sys.argv)

    template_path = flags.get("template_path")
    if template_path is None: 
        raise AssertionError("template must be set")
    
    rules_path = flags.get("rules_path")
    if rules_path is None: 
        rules_path = template_path + ".rules"

    target_root = flags.get("target_root")
    if target_root is None: 
        raise AssertionError("target must be set")
    
    interactive = flags.is_toggled("interactive")
    override_output = flags.is_toggled("override_output")
    logging.set_minimal_logging_level(LoggingLevel.DEBUG if flags.is_toggled("debug") else LoggingLevel.INFO)

    known_flag_keys = ["template_path", "target_root", "interactive", "override_output", "debug"]
    custom_flags = {k: v for k, v in flags.all_flags().items() if k not in known_flag_keys } if not interactive else None
    
    try:
        run(TemplateRequest(template_path, rules_path, target_root, interactive, custom_flags, override_output))
    except TemplatePreconditionException as err:
        logging.error(str(err))
        sys.exit(1)

if __name__ == '__main__':
    main()