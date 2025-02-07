package parser

import (
	"testing"
	"github.com/yanicksenn/its-almost-like-at-google/go/com/yanicksenn/taste/parser"
)

func TestParse(t *testing.T) {
	t.Fatalf("%+v\n", parser.Parse("abc"))
}

