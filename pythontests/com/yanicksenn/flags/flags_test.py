import unittest
from python.com.yanicksenn.flags import flags


class FlagsTest(unittest.TestCase):
    """Unittest class for one module"""

    def __parse_flags(self, args: str):
        flags.parse(args.split())

    def test_get(self):
        """Test get"""
        self.__parse_flags("--value=abc")
        self.assertTrue(flags.get("value") == "abc")

    # TODO: yanicksenn - Add remaining test cases.


if __name__ == "__main__":
    unittest.main()