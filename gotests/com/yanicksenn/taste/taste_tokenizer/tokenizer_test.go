package tokenizer

import (
	"reflect"
	"testing"

	"github.com/yanicksenn/its-almost-like-at-google/go/com/yanicksenn/taste/shared"
	"github.com/yanicksenn/its-almost-like-at-google/go/com/yanicksenn/taste/taste_tokenizer"
)

func TestTokenize(t *testing.T) {
	actualTokens := taste_tokenizer.Tokenize(`

namespace Tokenizer.Test;

type Token {
	string value;
	int counter;
}
	
	`)

	expectedTokens := []shared.Token {
		{ 
			Token: "namespace", 
			Len: 9,
			IsValidIdentifier: true,
			StartLocation: shared.Location{ 
				X: 0, 
				Y: 2, 
			},
		},
		{
			Token: "Tokenizer", 
			Len: 9,
			IsValidIdentifier: true,
			StartLocation: shared.Location{ 
				X: 10, 
				Y: 2, 
			},

		},
		{ 
			Token: ".", 
			Len: 1,
			IsValidIdentifier: false,
			StartLocation: shared.Location{ 
				X: 19, 
				Y: 2, 
			},

		},
		{ 
			Token: "Test", 
			Len: 4,
			IsValidIdentifier: true,
			StartLocation: shared.Location{ 
				X: 20, 
				Y: 2, 
			},

		},
		{ 
			Token: ";", 
			Len: 1,
			IsValidIdentifier: false,
			StartLocation: shared.Location{ 
				X: 24, 
				Y: 2, 
			},

		},
		{ 
			Token: "type", 
			Len: 4,
			IsValidIdentifier: true,
			StartLocation: shared.Location{ 
				X: 0, 
				Y: 4, 
			},
 
		},
		{ 
			Token: "Token", 
			Len: 5,
			IsValidIdentifier: true,
			StartLocation: shared.Location{ 
				X: 5, 
				Y: 4, 
			},
 
		},
		{ 
			Token: "{", 
			Len: 1,
			IsValidIdentifier: false,
			StartLocation: shared.Location{ 
				X: 11, 
				Y: 4, 
			},
 
		},
		{ 
			Token: "string", 
			Len: 6,
			IsValidIdentifier: true,
			StartLocation: shared.Location{ 
				X: 1, 
				Y: 5, 
			},

		},
		{ 
			Token: "value", 
			Len: 5,
			IsValidIdentifier: true,
			StartLocation: shared.Location{ 
				X: 8, 
				Y: 5, 
			},

		},
		{ 
			Token: ";", 
			Len: 1,
			IsValidIdentifier: false,
			StartLocation: shared.Location{ 
				X: 13, 
				Y: 5, 
			},

		},
		{ 

			Token: "int", 
			Len: 3,
			IsValidIdentifier: true,
			StartLocation: shared.Location{ 
				X: 1, 
				Y: 6, 
			},

		},
		{ 
			Token: "counter", 
			Len: 7,
			IsValidIdentifier: true,
			StartLocation: shared.Location{ 
				X: 5, 
				Y: 6, 
			},
 
		},
		{ 
			Token: ";", 
			Len: 1,
			IsValidIdentifier: false,
			StartLocation: shared.Location{ 
				X: 12, 
				Y: 6, 
			},

		},
		{ 
			Token: "}", 
			Len: 1,
			IsValidIdentifier: false,
			StartLocation: shared.Location{ 
				X: 0, 
				Y: 7, 
			},

		},

	}

	if len(actualTokens) != len(expectedTokens) {
		t.Fatalf("Expected: %d but got: %d\n", len(expectedTokens), len(actualTokens))	
	}

	if !reflect.DeepEqual(actualTokens, expectedTokens) {
		t.Fatalf("Expected: %+v but got: %+v\n", expectedTokens, actualTokens)	
	}
}
