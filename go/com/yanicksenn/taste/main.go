package main

import (
	"fmt"
	"github.com/yanicksenn/its-almost-like-at-google/go/com/yanicksenn/taste/parser"
	"github.com/yanicksenn/its-almost-like-at-google/go/com/yanicksenn/taste/tokenizer"
	"os"
	"path/filepath"
)

func main() {
	cwd, err := os.Getwd()
	if err != nil {
		fmt.Fprintln(os.Stderr, err)
		os.Exit(1)
	}

	tasteFilePath := filepath.Join(cwd, os.Args[1])
	fmt.Println(tasteFilePath)

	contentBytes, err := os.ReadFile(tasteFilePath)
	if err != nil {
		fmt.Fprintln(os.Stderr, err)
		os.Exit(1)
	}

	contentString := string(contentBytes)
	tokens := tokenizer.Tokenize(contentString)
	fmt.Printf("%+v\n", tokens)

	tasteStructure := parser.Parse(contentString)
	fmt.Printf("%+v\n", tasteStructure)
}
