package tokenizer

import (
	"strings"
	"github.com/yanicksenn/its-almost-like-at-google/go/com/yanicksenn/taste/shared"
	"unicode"
)

func Tokenize(contentString string) ([]shared.Token) {
	tokens := []shared.Token{}
	contentRunes := []rune(contentString)
	currentToken := strings.Builder{}
	isValidIdentifier := true

	x, y := -1, 0

	startLocation := shared.Location{
		X: x,
		Y: y,
	}

	for i := 0; i < len(contentRunes); i++ {
		char := contentRunes[i] 
		x ++

		if unicode.IsSpace(char) {
			// We found the end of the token. Append it to the buffer
			// and proceed to the next one.
			if currentToken.Len() > 0 {
				tokens = append(tokens, shared.Token{
					Token: currentToken.String(),
					Len: currentToken.Len(),
					IsValidIdentifier: isValidIdentifier,
					StartLocation: startLocation,
				})
				currentToken.Reset()
			}
			
			// Reset the x counter and increment the line of code.
			if char == '\n' {
				y ++
				x = -1
			}
		} else if char == '.' {
			if currentToken.Len() > 0 {
				tokens = append(tokens, shared.Token{
					Token: currentToken.String(),
					Len: currentToken.Len(),
					IsValidIdentifier: isValidIdentifier,
					StartLocation: startLocation,
				})
				currentToken.Reset()
			}

			tokens = append(tokens, shared.Token{
				Token: string('.'),
				Len: 1,
				IsValidIdentifier: false,
				StartLocation: shared.Location{
					X: x,
					Y: y,
				},
			})
		} else if char == '{' {
			if currentToken.Len() > 0 {
				tokens = append(tokens, shared.Token{
					Token: currentToken.String(),
					Len: currentToken.Len(),
					IsValidIdentifier: isValidIdentifier,
					StartLocation: startLocation,
				})
				currentToken.Reset()
			}

			tokens = append(tokens, shared.Token{
				Token: string('{'),
				Len: 1,
				IsValidIdentifier: false,
				StartLocation: shared.Location{
					X: x,
					Y: y,
				},
			})
		} else if char == '}' {
			if currentToken.Len() > 0 {
				tokens = append(tokens, shared.Token{
					Token: currentToken.String(),
					Len: currentToken.Len(),
					IsValidIdentifier: isValidIdentifier,
					StartLocation: startLocation,
				})
				currentToken.Reset()
			}

			tokens = append(tokens, shared.Token{
				Token: string('}'),	
				Len: 1,
				IsValidIdentifier: false,
				StartLocation: shared.Location{
					X: x,
					Y: y,
				},
			})
		} else if char == ';' {
			if currentToken.Len() > 0 {
				tokens = append(tokens, shared.Token{
					Token: currentToken.String(),
					Len: currentToken.Len(),
					IsValidIdentifier: isValidIdentifier,
					StartLocation: startLocation,
				})
				currentToken.Reset()
			}

			tokens = append(tokens, shared.Token{
				Token: string(';'),	
				Len: 1,
				IsValidIdentifier: false,
				StartLocation: shared.Location{
					X: x,
					Y: y,
				}, 
			})
		} else {
			if currentToken.Len() == 0 {
				isValidIdentifier = unicode.IsLetter(char)
				startLocation = shared.Location{
					X: x,
					Y: y,
				}
			} else if isValidIdentifier {
				isValidIdentifier = unicode.IsLetter(char) || unicode.IsNumber(char) || char == '_'	
			}

			currentToken.WriteRune(char)
		}
	}

	if currentToken.Len() > 0 {
		tokens = append(tokens, shared.Token{
			Token: currentToken.String(),
			Len: currentToken.Len(),
			IsValidIdentifier: isValidIdentifier,
			StartLocation: shared.Location{
				X: x,
				Y: y,
			},
		})
		currentToken.Reset()
	
	}
	return tokens
}

