package parser

import (
	"reflect"
	"testing"

	"github.com/yanicksenn/its-almost-like-at-google/go/com/yanicksenn/taste/shared"
	"github.com/yanicksenn/its-almost-like-at-google/go/com/yanicksenn/taste/taste_parser"
	"github.com/yanicksenn/its-almost-like-at-google/go/com/yanicksenn/taste/taste_tokenizer"
)

func TestParse(t *testing.T) {
	content := `

namespace Tokenizer.Test;

type Token {
	string value;
	int counter;
}

	`
	actualFile, err := taste_parser.Parse(taste_tokenizer.Tokenize(content))
	if err != nil {
		t.Fatalf("%w", err)
	}
	expectedFile := shared.File{
		Namespace: "Tokenizer.Test",
		Types: []shared.Type{
			{
				Name: "Token",
				Fields: []shared.Field{
					{
						Name: "value",
						Type: "string",
						IsArray: false,
					},
					{
						Name: "counter",
						Type: "int",
						IsArray: false,
					},
				},
			},
		},

	}
	if reflect.DeepEqual(expectedFile, actualFile) {
		
	}		
}
