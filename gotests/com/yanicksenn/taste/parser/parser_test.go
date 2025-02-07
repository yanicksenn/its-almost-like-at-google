package parser

import (
	"testing"
	"github.com/yanicksenn/its-almost-like-at-google/go/com/yanicksenn/taste/parser"
	"github.com/yanicksenn/its-almost-like-at-google/go/com/yanicksenn/taste/shared"
)

func TestParse(t *testing.T) {
	tokens := []shared.Token {
		{ Token: "namespace" },
		{ Token: "Tokenizer" },
		{ Token: "." },
		{ Token: "Test" },
		{ Token: ";" },
		{ Token: "type" },
		{ Token: "Token" },
		{ Token: "{" },
		{ Token: "string" },
		{ Token: "value" },
		{ Token: ";" },
		{ Token: "int" },
		{ Token: "counter" },
		{ Token: ";" },
		{ Token: "}" },
	}

	file, err := parser.Parse(tokens)
	if err != nil {
		t.Fatalf("%w", err)
	}
	t.Fatalf("%+v\n", file)
}
