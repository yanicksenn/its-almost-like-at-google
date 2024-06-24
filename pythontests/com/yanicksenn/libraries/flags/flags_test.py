import unittest
from python.com.yanicksenn.flags import flags

class FlagsTest(unittest.TestCase):

    def __parse_flags(self, args: str):
        flags.parse(args.split())

    def test_get_whenUnset_returnsNone(self):
        self.__parse_flags("")
        self.assertTrue(flags.get("value") == None)

    def test_get_whenSetWithNoValue_returnsNone(self):
        self.__parse_flags("--value")
        self.assertTrue(flags.get("value") == None)

    def test_get_whenSetWithEmptyString_returnsValue(self):
        self.__parse_flags("--value=")
        self.assertTrue(flags.get("value") == "")

    def test_get_whenSetWithAnyString_returnsValue(self):
        self.__parse_flags("--value=ABC")
        self.assertTrue(flags.get("value") == "ABC")

    def test_get_as_int_whenUnset_returnsNone(self):
        self.__parse_flags("")
        self.assertTrue(flags.getAsInt("value") == None)

    def test_get_as_int_whenSetWithNoValue_returnNone(self):
        self.__parse_flags("--value")
        self.assertTrue(flags.getAsInt("value") == None)

    def test_get_as_int_whenSetWithEmptyString_returnsNone(self):
        self.__parse_flags("--value=")
        self.assertTrue(flags.getAsInt("value") == None)

    def test_get_as_int_whenSetWithInvalidString_returnsNone(self):
        self.__parse_flags("--value=ABC")
        self.assertTrue(flags.getAsInt("value") == None)

    def test_get_as_int_whenSetWithNumericString_returnsValue(self):
        self.__parse_flags("--value=123")
        self.assertTrue(flags.getAsInt("value") == 123)

    def test_is_toggled_whenUnset_returnsFalse(self):
        self.__parse_flags("")
        self.assertFalse(flags.is_toggled("value"))

    def test_is_toggled_whenSetWithNoValue_returnTrue(self):
        self.__parse_flags("--value")
        self.assertTrue(flags.is_toggled("value"))

    def test_is_toggled_whenSetWithEmptyString_returnsFalse(self):
        self.__parse_flags("--value=")
        self.assertFalse(flags.is_toggled("value"))

    def test_is_toggled_whenSetWithInvalidString_returnsFalse(self):
        self.__parse_flags("--value=ABC")
        self.assertFalse(flags.is_toggled("value"))

    def test_is_toggled_whenSetWithFalseString_returnsFalse(self):
        self.__parse_flags("--value=false")
        self.assertFalse(flags.is_toggled("value"))

    def test_is_toggled_whenSetWithTrueString_returnsTrue(self):
        self.__parse_flags("--value=true")
        self.assertTrue(flags.is_toggled("value"))

    def test_is_set_whenUnset_returnsFalse(self):
        self.__parse_flags("")
        self.assertFalse(flags.is_set("value"))

    def test_is_set_whenSetWithValue_returnsTrue(self):
        self.__parse_flags("--value")
        self.assertTrue(flags.is_set("value"))

    def test_is_set_whenSetWithEmptyString_returnsTrue(self):
        self.__parse_flags("--value=")
        self.assertTrue(flags.is_set("value"))

    def test_is_set_whenSetWithAnyString_returnsTrue(self):
        self.__parse_flags("--value=ABC")
        self.assertTrue(flags.is_set("value"))

    def test_has_value_whenUnset_returnsFalse(self):
        self.__parse_flags("")
        self.assertFalse(flags.has_value("value"))

    def test_has_value_whenSetWithValue_returnsFalse(self):
        self.__parse_flags("--value")
        self.assertFalse(flags.has_value("value"))

    def test_has_value_whenSetWithEmptyString_returnsTrue(self):
        self.__parse_flags("--value=")
        self.assertTrue(flags.has_value("value"))

    def test_has_value_whenSetWithAnyString_returnsTrue(self):
        self.__parse_flags("--value=ABC")
        self.assertTrue(flags.has_value("value"))

    # TODO - yanicksenn: Add remaining test cases.

if __name__ == "__main__":
    unittest.main()