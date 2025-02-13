package main

import (
	"errors"
	"flag"
	"fmt"
	"os"
	"path/filepath"

	"github.com/yanicksenn/its-almost-like-at-google/go/com/yanicksenn/taste/shared"
	"github.com/yanicksenn/its-almost-like-at-google/go/com/yanicksenn/taste/taste_parser"
	"github.com/yanicksenn/its-almost-like-at-google/go/com/yanicksenn/taste/taste_tokenizer"
	"github.com/yanicksenn/its-almost-like-at-google/go/com/yanicksenn/taste/taste_writer"
)

var tasteFileFlag string
var recipeFileFlag string

func main() {
	flag.StringVar(&tasteFileFlag, "taste", "", "Path to the taste file.")
	flag.StringVar(&recipeFileFlag, "recipe", "", "Path to the recipe file.")
	flag.Parse()

	if len(tasteFileFlag) == 0 {
		fmt.Fprintln(os.Stderr, errors.New("Flag taste not set"))
		os.Exit(1)
	}

	tasteFile, err := read(tasteFileFlag)
	if err != nil {
		fmt.Fprintln(os.Stderr, err)
		os.Exit(2)
	}

	if len(recipeFileFlag) == 0 {
		fmt.Fprintln(os.Stderr, errors.New("Flag recipe not set"))
		os.Exit(3)
	}

	recipeFile, err := read(recipeFileFlag)
	if err != nil {
		fmt.Fprintln(os.Stderr, err)
		os.Exit(4)
	}

	tokens := taste_tokenizer.Tokenize(tasteFile)
	file, err := taste_parser.Parse(tokens)
	if err != nil {
		fmt.Fprintln(os.Stderr, err)
		os.Exit(5)
	}

	recipe := shared.Recipe{
		Content: recipeFile,
	}
	err = taste_writer.Write(file, &recipe, "bogus")
	if err != nil {
		fmt.Fprintln(os.Stderr, err)
		os.Exit(6)
	}
}

func read(path string) (string, error) {
	content, err := os.ReadFile(path)
	if err != nil {
		return "", err
	}

	return string(content), nil
}

func getRecipeFileContent(cwd string) (string, error) {
	recipeFilePath := filepath.Join(cwd, os.Args[2])
	recipeFileBytes, err := os.ReadFile(recipeFilePath)
	if err != nil {
		return "", nil
	}

	return string(recipeFileBytes), nil

}

