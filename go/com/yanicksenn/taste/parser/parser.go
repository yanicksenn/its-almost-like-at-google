package parser

import (
	"errors"
	"fmt"

	"github.com/yanicksenn/its-almost-like-at-google/go/com/yanicksenn/taste/shared"
)

func Parse(tokens []shared.Token) (*shared.File, error) {
	iterator := createTokenQueue(tokens)

	file := shared.File{}
	for iterator.hasNext() {
		token := iterator.peek()

		switch (token.Token) {
			case "namespace":
				parseNamespace(iterator, &file)
			case "type":
				parseType(iterator, &file)
			default:
				return nil, errors.New(fmt.Sprintf("Unknown token %s", token.Token))

		}
	}

	return &file, nil
}

func parseNamespace(iterator *tokenIterator, file *shared.File) {
	iterator.next()
}

func parseType(iterator *tokenIterator, file *shared.File) {
	iterator.next()
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

func (q *tokenIterator) peek() shared.Token {
	return q.tokens[q.pos]
}

func (q *tokenIterator) next() shared.Token {
	if !q.hasNext() {
		return q.peek()
	}
	
	q.pos ++
	return q.tokens[q.pos]
}

func (q *tokenIterator) reset() {
	q.pos = 0
}

