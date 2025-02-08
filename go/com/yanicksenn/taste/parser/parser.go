package parser

import (
	"errors"
	"fmt"
	"strings"

	"github.com/yanicksenn/its-almost-like-at-google/go/com/yanicksenn/taste/shared"
)

func Parse(tokens []shared.Token) (*shared.File, error) {
	iterator := createTokenQueue(tokens)

	file := shared.File{}
	
	namespaceOrTypeToken := iterator.peek()

	switch (namespaceOrTypeToken.Token) {
		case "namespace":
			parseNamespace(iterator, &file)
		case "type":
			parseType(iterator, &file)
		default:
			return nil, errors.New(fmt.Sprintf("Expected namespace or type but got %s", namespaceOrTypeToken.Token))

	}

	for iterator.hasNext() {
		typeToken := iterator.peek()
		if typeToken.Token != "type" {
			return nil, errors.New(fmt.Sprintf("Expected type but got %s", typeToken.Token))
		}

		parseType(iterator, &file)
	}

	return &file, nil
}

func parseNamespace(iterator *tokenIterator, file *shared.File) (error) {
	keyword, err := iterator.next()
	if err != nil {
		return errors.New("Unexpected EOF")
	}
	if keyword.Token != "namespace" {
		return errors.New(fmt.Sprintf("Expected keyword namespace but got %s", keyword.Token))
	}

	namespaceBuilder := strings.Builder{}

	firstNamespace, err := iterator.next()
	if err != nil {
		return errors.New("Unexpected EOF")
	}
	if !firstNamespace.IsValidIdentifier {
		return errors.New(fmt.Sprintf("Invalid namespace %s", firstNamespace.Token))
	}

	namespaceBuilder.WriteString(firstNamespace.Token)

	for iterator.peek().Token != ";" {
		namespaceSeparatorToken, err := iterator.next()
		if err != nil {
			return errors.New("Unexpected EOF")
		}
		if namespaceSeparatorToken.Token != "." {
			return errors.New(fmt.Sprintf("Expected namespace separator . but got %s", namespaceSeparatorToken.Token))
		}
		namespaceBuilder.WriteString(".")

		additionalNamespace, err := iterator.next()
		if err != nil {
			return errors.New("Unexpected EOF")
		}
		if !additionalNamespace.IsValidIdentifier {
			return errors.New(fmt.Sprintf("Invalid namespace %s", additionalNamespace.Token))
		}
		namespaceBuilder.WriteString(additionalNamespace.Token)
	}

	// Consume semicolon.
	_, err = iterator.next()
	if err != nil {
		return errors.New("Unexpected EOF")
	}

	file.Namespace = namespaceBuilder.String()

	return nil
}

func parseType(iterator *tokenIterator, file *shared.File) error {
	keywordToken, err := iterator.next()
	if err != nil {
		return errors.New("Unexpected EOF")
	}
	if keywordToken.Token != "type" {
		return errors.New(fmt.Sprintf("Expected type but got %s", keywordToken.Token))
	}

	typeNameToken, err  := iterator.next()
	if err != nil {
		return errors.New("Unexpected EOF")
	}
	if !typeNameToken.IsValidIdentifier {
		return errors.New(fmt.Sprintf("Invalid type name %s", typeNameToken.Token))
	}

	newType := shared.Type{
		Name: typeNameToken.Token,
	}

	blockStartToken, err := iterator.next()
	if err != nil {
		return errors.New("Unexpected EOF")
	}
	if blockStartToken.Token != "{" {
		return errors.New(fmt.Sprintf("Expected { but got %s", blockStartToken.Token))
	}

	for iterator.peek().Token != "}" {
		parseField(iterator, &newType)
	}

	// Consume closing braces.
	iterator.next()

	file.Types = append(file.Types, newType)

	return nil
}

func parseField(iterator *tokenIterator, newType *shared.Type) error {
	fieldTypeToken, err := iterator.next()
	if err != nil {
		return errors.New("Unexpected EOF")
	}
	if !fieldTypeToken.IsValidIdentifier {
		return errors.New(fmt.Sprintf("Invalid field type %s", fieldTypeToken.Token))
	}

	fieldNameToken, err := iterator.next()
	if err != nil {
		return errors.New("Unexpected EOF")
	}
	if !fieldNameToken.IsValidIdentifier {
		return errors.New(fmt.Sprintf("Invalid field name %s", fieldNameToken.Token))
	}

	semicolonToken, err := iterator.next()
	if err != nil {
		return errors.New("Unexpected EOF")
	}
	if semicolonToken.Token != ";" {
		return errors.New(fmt.Sprintf("Expected ; but got %s", semicolonToken.Token))
	}

	newType.Fields = append(newType.Fields, shared.Field{
		Type: fieldTypeToken.Token,
		Name: fieldNameToken.Token,
	})

	return nil
}

type tokenIterator struct {
	tokens []shared.Token
	len int
	pos int
}

func createTokenQueue(tokens []shared.Token) (*tokenIterator) {
	return &tokenIterator{
		tokens: tokens,
		len: len(tokens),
		pos: 0,
	}
}

func (q *tokenIterator) hasNext() bool {
	return q.pos < q.len - 1
}

func (q *tokenIterator) peek() *shared.Token {
	return &q.tokens[q.pos]
}

func (q *tokenIterator) next() (*shared.Token, error) {
	if !q.hasNext() {
		return nil, errors.New("No more tokens left")
	}
	
	current := q.peek()
	q.pos ++
	return current, nil
}

func (q *tokenIterator) reset() {
	q.pos = 0
}

