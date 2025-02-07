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
	for i := 0; i < len(contentRunes); i++ {
		char := contentRunes[i] 
		if unicode.IsSpace(char) {
			// We found the end of the token. Append it to the buffer
			// and proceed to the next one.
			if currentToken.Len() > 0 {
				tokens = append(tokens, shared.Token{
					Token: currentToken.String(),
				})
				currentToken.Reset()
			}
			continue
		}

		if char == '.' {
			if currentToken.Len() > 0 {
				tokens = append(tokens, shared.Token{
					Token: currentToken.String(),
				})
				currentToken.Reset()
			}

			tokens = append(tokens, shared.Token{
				Token: string('.'),	
			})
			continue
		}


		if char == '{' {
			if currentToken.Len() > 0 {
				tokens = append(tokens, shared.Token{
					Token: currentToken.String(),
				})
				currentToken.Reset()
			}

			tokens = append(tokens, shared.Token{
				Token: string('{'),	
			})
			continue
		}
		
		if char == '}' {
			if currentToken.Len() > 0 {
				tokens = append(tokens, shared.Token{
					Token: currentToken.String(),
				})
				currentToken.Reset()
			}

			tokens = append(tokens, shared.Token{
				Token: string('}'),	
			})
			continue
		}

		if char == ';' {
			if currentToken.Len() > 0 {
				tokens = append(tokens, shared.Token{
					Token: currentToken.String(),
				})
				currentToken.Reset()
			}

			tokens = append(tokens, shared.Token{
				Token: string(';'),	
			})
			continue
		}

		currentToken.WriteRune(char)
	}

	if currentToken.Len() > 0 {
		tokens = append(tokens, shared.Token{
			Token: currentToken.String(),
		})
		currentToken.Reset()
	
	}
	return tokens
}

