package tokenizer

import (
	"reflect"
	"testing"

	"github.com/yanicksenn/its-almost-like-at-google/go/com/yanicksenn/taste/shared"
	"github.com/yanicksenn/its-almost-like-at-google/go/com/yanicksenn/taste/tokenizer"
)

func TestTokenize(t *testing.T) {
	actualTokens := tokenizer.Tokenize(`

namespace Tokenizer.Test;

type Token {
	string value;
	int counter;
}
	
	`)

	expectedTokens := []shared.Token {
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

	if len(actualTokens) != len(expectedTokens) {
		t.Fatalf("Expected: %d but got: %d\n", len(expectedTokens), len(actualTokens))	
	}

	if !reflect.DeepEqual(actualTokens, expectedTokens) {
		t.Fatalf("Expected: %+v but got: %+v\n", expectedTokens, actualTokens)	
	}
}
