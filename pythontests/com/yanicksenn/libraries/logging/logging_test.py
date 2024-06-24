import unittest
from python.com.yanicksenn.libraries.logging import logging
from python.com.yanicksenn.libraries.logging.logging import LoggingLevel
from python.com.yanicksenn.libraries.logging.logging import LoggingMessage

class LoggingTest(unittest.TestCase):

    logging_messages: list[logging.LoggingMessage] = []

    def setUp(self):
        logging.set_minimal_logging_level(LoggingLevel.INFO)
        logging.add_peeker(lambda e: self.logging_messages.append(e))

    def tearDown(self):
        logging.clear_peekers()
        self.logging_messages.clear()

    def test_info_returnsLoggingLevelInfo(self):
        logging.info("Message")

        self.assertEqual(len(self.logging_messages), 1)
        self.assertEqual(self.logging_messages[0], LoggingMessage(LoggingLevel.INFO, "Message"))

    def test_warning_returnsLoggingLevelWarning(self):
        logging.warning("Message")

        self.assertEqual(len(self.logging_messages), 1)
        self.assertEqual(self.logging_messages[0], LoggingMessage(LoggingLevel.WARNING, "Message"))

    def test_error_returnsLoggingLevelError(self):
        logging.error("Message")

        self.assertEqual(len(self.logging_messages), 1)
        self.assertEqual(self.logging_messages[0], LoggingMessage(LoggingLevel.ERROR, "Message"))
    
    def test_minimalLoggingLevel_returnsOnlyLoggingLevelError(self):
        logging.set_minimal_logging_level(LoggingLevel.ERROR)
        logging.info("Message")
        logging.warning("Message")
        logging.error("Message")

        self.assertEqual(len(self.logging_messages), 1)
        self.assertEqual(self.logging_messages[0], LoggingMessage(LoggingLevel.ERROR, "Message"))
    
    def test_togglingMute_returnsNoLoggingMessages(self):
        logging.make_mute()
        logging.info("Message")
        logging.warning("Message")

        logging.make_noisy()
        logging.error("Message")

        self.assertEqual(len(self.logging_messages), 1)
        self.assertEqual(self.logging_messages[0], LoggingMessage(LoggingLevel.ERROR, "Message"))

if __name__ == "__main__":
    unittest.main()